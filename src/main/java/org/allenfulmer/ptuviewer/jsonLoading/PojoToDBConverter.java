package org.allenfulmer.ptuviewer.jsonLoading;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.allenfulmer.ptuviewer.jsonLoading.pojo.ability.AbilityPojo;
import org.allenfulmer.ptuviewer.jsonLoading.pojo.move.MovePojo;
import org.allenfulmer.ptuviewer.jsonLoading.pojo.pokemon.BaseStats;
import org.allenfulmer.ptuviewer.jsonLoading.pojo.pokemon.ImperialHeightRange;
import org.allenfulmer.ptuviewer.jsonLoading.pojo.pokemon.PokemonSpeciesPojo;
import org.allenfulmer.ptuviewer.models.*;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class PojoToDBConverter {
    private static final String ABILITY_DIVIDER = " - ";
    private static final String CONNECTION_HEADER = "Connection - ";
    private static Map<String, Ability> convertedAbilities = null;
    private static Map<String, Move> convertedMoves = null;
    private static Map<String, PokemonSpecies> convertedPokemonSpecies = null;

    public static Ability getAbility(String name) {
        if (convertedAbilities == null)
            convertedAbilities = abilityMapBuilder(JsonToPojoLoader.parsePojoAbilities());
        Ability mapAbility = convertedAbilities.get(name);
        if (mapAbility == null)
            throw new NullPointerException("Ability named " + name + " not found in abilityMap");
        return mapAbility;
    }

    public static Move getMove(String name) {
        if (convertedMoves == null)
            convertedMoves = moveMapBuilder(JsonToPojoLoader.parsePojoMoves());
        Move mapMove = convertedMoves.get(name);
        if (mapMove == null)
            throw new NullPointerException("Move named " + name + " not found in moveMap");
        return mapMove;
    }

    private static PojoFrequency convertFrequency(String freqText) {
        Frequency freq = Frequency.getWithName(freqText);
        int uses = (freq == Frequency.DAILY || freq == Frequency.SCENE) ? 1 : 0;
        if (freq == null) // The frequency must have a usage count on it (i.e. Scene x2)
        {
            String[] split = freqText.split(" x");
            freq = Frequency.getWithName(split[0]);
            uses = Integer.parseInt(split[1]);
        }

        return new PojoFrequency(freq, uses);
    }

    private static PojoActionType convertActionType(String actionText) {
        ActionType actionType = ActionType.getWithName(actionText);
        ActionType.Priority priority = null;
        if (actionType == null) // It must have a priority; split via ", "
        {
            String[] split = actionText.split(", ");
            actionType = ActionType.getWithName(split[0]);
            priority = ActionType.Priority.getWithName(split[1]);
        }
        return new PojoActionType(actionType, priority);
    }

    private static Type convertType(String typeText) {
        try {
            return Type.valueOf(typeText.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Type.TYPELESS;
        }
    }

    private static EnumMap<Stat.StatName, Integer> convertBaseStats(BaseStats baseStats) {
        EnumMap<Stat.StatName, Integer> stats = new EnumMap<>(Stat.StatName.class);
        stats.put(Stat.StatName.HP, baseStats.getHp());
        stats.put(Stat.StatName.ATTACK, baseStats.getAttack());
        stats.put(Stat.StatName.DEFENSE, baseStats.getDefense());
        stats.put(Stat.StatName.SPECIAL_ATTACK, baseStats.getSpecialAttack());
        stats.put(Stat.StatName.SPECIAL_DEFENSE, baseStats.getSpecialDefense());
        stats.put(Stat.StatName.SPEED, baseStats.getSpeed());
        return stats;
    }

    public static Map<String, Ability> abilityMapBuilder(Map<String, AbilityPojo> pojoMap) {
        // Frequency and ActionType are split with a -
        // Check if one exists to see if we need to split- otherwise it's just Static/Special
        Map<String, Ability> abilities = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        pojoMap.forEach((name, a) -> {
            PojoFrequency pojoFreq;
            PojoActionType pojoActionType = new PojoActionType();
            if (a.getFreq().contains(ABILITY_DIVIDER)) // Needs to be split
            {
                String[] split = a.getFreq().split(ABILITY_DIVIDER);
                pojoFreq = convertFrequency(split[0]);
                pojoActionType = convertActionType(split[1]);
            } else {
                pojoFreq = convertFrequency(a.getFreq());
            }

            Move connection = null;
            String effect = a.getEffect();
            if (effect.startsWith(CONNECTION_HEADER)) {
                connection = getMove(effect.substring(CONNECTION_HEADER.length(), effect.indexOf(". ")));
            }
            abilities.put(name, new Ability(name, pojoFreq.getFreq(), pojoFreq.getUses(), pojoActionType.getActionType(),
                    pojoActionType.getPriority(), a.getTrigger(), a.getTarget(), a.getEffect(), connection));
        });
        if (convertedAbilities == null)
            convertedAbilities = abilities;
        return abilities;
    }

    public static Map<String, Move> moveMapBuilder(Map<String, MovePojo> pojoMap) {
        Map<String, Move> moves = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        pojoMap.forEach((name, m) -> {
            Type type = convertType(m.getType());
            PojoFrequency pojoFreq = convertFrequency(m.getFreq());

            // All moves have a Move Class, so we don't catch the exception and let it throw if we're missing it
            Move.MoveClass moveClass = Move.MoveClass.valueOf(m.getDamageClass().toUpperCase());

            Move.ContestType contestType = null;
            try {
                contestType = Move.ContestType.valueOf(m.getContestType().toUpperCase());
            } catch (IllegalArgumentException | NullPointerException ignored) { // Empty on purpose
            }

            Move.ContestEffect contestEffect = Move.ContestEffect.getContestEffect(m.getContestEffect());

            moves.put(name, new Move(name, type, pojoFreq.getFreq(), pojoFreq.getUses(), m.getAc(), m.getDb(),
                    moveClass, m.getRange(), m.getEffect(), contestType, contestEffect, m.getCritsOn()));
        });
        if (convertedMoves == null)
            convertedMoves = moves;
        return moves;
    }

    public static Map<String, PokemonSpecies> pokemonMapBuilder(Map<String, PokemonSpeciesPojo> pojoMap) {
        Map<String, PokemonSpecies> pokemon = new HashMap<>(pojoMap.size());
        pojoMap.forEach((id, p) -> {
            PokemonSpecies newPoke = new PokemonSpecies();

            // Basic info: Pokedex #, Name, Form
            newPoke.setPokedexID(id);
            newPoke.setSpeciesName(p.getSpecies());
            newPoke.setForm(p.getForm());

            // Base Stats - EnumMap<Stat.StatName, Integer>
            newPoke.setBaseStatsFromMap(convertBaseStats(p.getBaseStats()));

            // Height
            ImperialHeightRange inchRange = p.getHeight().getImperial();
            newPoke.setInchesHeightMin((int) (inchRange.getMinimum().getInches()
                    + (inchRange.getMinimum().getFeet() * 12)));
            newPoke.setInchesHeightMax((int) (inchRange.getMaximum().getInches()
                    + (inchRange.getMaximum().getFeet() * 12)));
            newPoke.setHeightCategoryMin(p.getHeight().getCategory().getMinimum());
            newPoke.setHeightCategoryMax(p.getHeight().getCategory().getMaximum());

            // Weight
            newPoke.setPoundsWeightMin(p.getWeight().getImperial().getMinimum().getPounds());
            newPoke.setPoundsWeightMax(p.getWeight().getImperial().getMaximum().getPounds());
            newPoke.setWeightClassMin((int) p.getWeight().getWeightClass().getMinimum());
            newPoke.setWeightClassMax((int) p.getWeight().getWeightClass().getMaximum());

            // Types - List<Type>
            newPoke.setTypesFromList(p.getTypes().stream().map(PojoToDBConverter::convertType).collect(Collectors.toList()));

            // BaseAbilities - List<BaseAbility>
            p.getAbilities().stream().forEach(a ->
                    newPoke.addBaseAbility(BaseAbility.AbilityType.valueOf(a.getType().toUpperCase()),
                            PojoToDBConverter.getAbility(a.getName()))
            );

            // Evolution Stages - String
            newPoke.setEvolutions(p.getEvolutionStages().stream().map(e ->
            {
                String ret = e.getStage() + " - " + e.getSpecies();
                if (!e.getCriteria().isEmpty())
                    ret += " (" + e.getCriteria() + ")";
                return ret;
            }).collect(Collectors.joining(", ")));

            // LevelUpMoves - List<LevelUpMove>
            p.getLevelUpMoves().stream().forEach(m ->
                    newPoke.addLevelMove(m.getLevelLearned(), PojoToDBConverter.getMove(m.getName()))
            );

            // TM/HM Moves - Set<Move>
            newPoke.setTmHmMoves(p.getTmHmMoves().stream().map(m -> PojoToDBConverter.getMove(m.getName()))
                    .collect(Collectors.toSet()));

            // Tutor Moves - Set<Move>
            newPoke.setTutorMoves(p.getTutorMoves().stream().map(m -> getMove(m.getName())).collect(Collectors.toSet()));

            // Egg Moves - Set<Move>
            newPoke.setEggMoves(p.getEggMoves().stream().map(m -> getMove(m.getName())).collect(Collectors.toSet()));

            pokemon.put(id, newPoke);
        });
        if (convertedPokemonSpecies == null)
            convertedPokemonSpecies = pokemon;
        return pokemon;
    }

    public static void main(String[] args) {
        // Local variables left here for easy debugging in the debugger if necessary
        Map<String, MovePojo> pojoMoves = JsonToPojoLoader.parsePojoMoves();
        Map<String, Move> moves = moveMapBuilder(pojoMoves);

        Map<String, AbilityPojo> pojoAbility = JsonToPojoLoader.parsePojoAbilities();
        Map<String, Ability> abilities = abilityMapBuilder(pojoAbility);

        Map<String, PokemonSpeciesPojo> pojoPokes = JsonToPojoLoader.parsePojoPokemon();
        Map<String, PokemonSpecies> pokes = pokemonMapBuilder(pojoPokes);
    }

    @AllArgsConstructor
    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    private static class PojoFrequency {
        @NonNull
        Frequency freq;
        int uses = 0;

        public Frequency getFreq() {
            return freq;
        }

        public int getUses() {
            return uses;
        }
    }

    @AllArgsConstructor
    @RequiredArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    private static class PojoActionType {
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
}
