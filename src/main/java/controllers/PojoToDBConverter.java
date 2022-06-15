package controllers;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import jsonLoading.PokedexLoader;
import jsonLoading.db.ability.AbilityPojo;
import jsonLoading.db.move.MovePojo;
import lombok.*;
import lombok.experimental.FieldDefaults;
import models.*;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class PojoToDBConverter {
    @AllArgsConstructor
    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    private static class PojoFrequency
    {
        @NonNull
        Frequency freq;
        int uses = 0;

        public Frequency getFreq()
        {
            return freq;
        }

        public int getUses()
        {
            return uses;
        }
    }

    @AllArgsConstructor
    @RequiredArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    private static class PojoActionType
    {
        @NonNull
        ActionType actionType;
        ActionType.Priority priority = null;

        public ActionType getActionType() {
            return actionType;
        }

        public ActionType.Priority getPriority() {
            return priority;
        }
    }

    private static PojoFrequency convertFrequency(String freqText)
    {
        Frequency freq = Frequency.getWithName(freqText);
        int uses = (freq == Frequency.DAILY || freq == Frequency.SCENE) ? 1 : 0;
        if(freq == null) // The frequency must have a usage count on it (i.e. Scene x2)
        {
            System.out.println(freqText);
            String[] split = freqText.split(" x");
            freq = Frequency.getWithName(split[0]);
            uses = Integer.parseInt(split[1]);
        }

        return new PojoFrequency(freq, uses);
    }

    private static PojoActionType convertActionType(String actionText)
    {
        ActionType actionType = ActionType.getWithName(actionText);
        ActionType.Priority priority = null;
        if(actionType == null) // It must have a priority; split via ", "
        {
            String[] split = actionText.split(", ");
            actionType = ActionType.getWithName(split[0]);
            priority = ActionType.Priority.valueOf(split[1].toUpperCase());
        }
        return new PojoActionType(actionType, priority);
    }

    private static Type convertType(String typeText)
    {
        try {
            return Type.valueOf(typeText.toUpperCase());
        }
        catch (IllegalArgumentException e)
        {
            return Type.TYPELESS;
        }
    }

    private static final String ABILITY_DIVIDER = " - ";
    private static final String CONNECTION_HEADER = "Connection - ";

    public static Map<String, Ability> abilityMapBuilder(Map<String, AbilityPojo> pojoMap)
    {
        // Frequency and ActionType are split with a -
        // Check if one exists to see if we need to split- otherwise it's just Static/Special
//        Map<String, Ability> abilities = new HashMap<>(pojoMap.size());
        Map<String, Ability> abilities = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        pojoMap.forEach((name, a) -> {
            PojoFrequency pojoFreq;
            PojoActionType pojoActionType = new PojoActionType();
            if(a.getFreq().contains(ABILITY_DIVIDER)) // Needs to be split
            {
                String[] split = a.getFreq().split(ABILITY_DIVIDER);
                pojoFreq = convertFrequency(split[0]);
                pojoActionType = convertActionType(split[1]);
            }
            else {
                pojoFreq = convertFrequency(a.getFreq());
            }

            Move connection = null;
            String effect = a.getEffect();
            if(effect.startsWith(CONNECTION_HEADER)) {
                connection = Move.getMove(effect.substring(CONNECTION_HEADER.length(), effect.indexOf(". ")));
            }
            abilities.put(name, new Ability(name, pojoFreq.getFreq(), pojoFreq.getUses(), pojoActionType.getActionType(),
                    pojoActionType.getPriority(), a.getTrigger(), a.getTarget(), a.getEffect(), connection));
        });
        return abilities;
    }

    public static Map<String, Move> moveMapBuilder(Map<String, MovePojo> pojoMap)
    {
        Map<String, Move> moves = new HashMap<>(pojoMap.size());
//        Map<String, Move> moves = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        pojoMap.forEach((name, m) -> {
            Type type = convertType(m.getType());
            PojoFrequency pojoFreq = convertFrequency(m.getFreq());

            Move.MoveClass moveClass = null;
            for(Move.MoveClass currMoveClass : Move.MoveClass.values())
                if(currMoveClass.name().equalsIgnoreCase(m.getDamageClass())) {
                    moveClass = currMoveClass;
                    break;
                }
            // All moves have a Move Class, throw if there wasn't one
            if(moveClass == null)
                throw new IllegalArgumentException("Move named " + name + " is missing a move class. " +
                        "Conversion aborted.");

            Move.ContestType contestType = null;
            for(Move.ContestType currContestType : Move.ContestType.values())
                if(currContestType.name().equalsIgnoreCase(m.getContestType())) {
                    contestType = currContestType;
                    break;
                }

            Move.ContestEffect contestEffect = null;
            for(Move.ContestEffect currContestEffect : Move.ContestEffect.values())
                if(currContestEffect.name().equalsIgnoreCase(m.getContestEffect())) {
                    contestEffect = currContestEffect;
                    break;
                }

            moves.put(name, new Move(name, type, pojoFreq.getFreq(), pojoFreq.getUses(), m.getAc(), m.getDb(),
                    moveClass, m.getRange(), m.getEffect(), contestType, contestEffect, m.getCritsOn()));
        });
        return moves;
    }

    public static void main(String[] args)
    {
        Map<String, MovePojo> pojoMoves = PokedexLoader.parsePojoMoves();
        Map<String, Move> moves = moveMapBuilder(pojoMoves);
        moves.values().forEach(System.out::println);

        Map<String, AbilityPojo> pojoAbility = PokedexLoader.parsePojoAbilities();
        Map<String, Ability> abilities = abilityMapBuilder(pojoAbility);
        abilities.values().forEach(System.out::println);
    }

    /*
    public class AbilityDeserializer implements JsonDeserializer<Ability> {
    private static final String CONNECTION_HEADER = "Connection - ";
    @Override
    public Ability deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject pokeJson = json.getAsJsonObject();

        String freq = pokeJson.get("freq").getAsString();
        JsonElement trig = pokeJson.get("trigger");
        String trigger = Objects.isNull(trig) ? null : trig.getAsString();
        JsonElement trgt = pokeJson.get("target");
        String target = Objects.isNull(trgt) ? null : trgt.getAsString();
        String effect = pokeJson.get("effect").getAsString();

        Move connection = null;
        if(effect.startsWith(CONNECTION_HEADER)) {
            String move = effect.substring(CONNECTION_HEADER.length(), effect.indexOf(". "));
            connection = Move.allMoves.get(move);
        }

        return new Ability(freq, trigger, target, effect, connection);
    }
}
     */
}
