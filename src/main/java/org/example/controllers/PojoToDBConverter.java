package org.example.controllers;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.jsonLoading.PokedexLoader;
import org.example.jsonLoading.db.ability.AbilityPojo;
import org.example.jsonLoading.db.move.MovePojo;
import org.example.jsonLoading.db.pokemon.BaseStats;
import org.example.jsonLoading.db.pokemon.ImperialHeightRange;
import org.example.jsonLoading.db.pokemon.LevelUpmove;
import org.example.jsonLoading.db.pokemon.PokemonSpeciesPojo;
import org.example.models.*;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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

    private static Map<String, Ability> convertedAbilities = null;
    private static Map<String, Move> convertedMoves = null;
    private static Map<String, PokemonSpecies> convertedPokemonSpecies = null;

    public static Ability getAbility(String name) {
        if(convertedAbilities == null)
            convertedAbilities = abilityMapBuilder(PokedexLoader.parsePojoAbilities());
        Ability mapAbility = convertedAbilities.get(name);
        if(mapAbility == null)
            throw new NullPointerException("Ability named " + name + " not found in abilityMap");
        return mapAbility;
    }

    public static Move getMove(String name){
        if(convertedMoves == null)
            convertedMoves = moveMapBuilder(PokedexLoader.parsePojoMoves());
        Move mapMove = convertedMoves.get(name);
        if(mapMove == null)
            throw new NullPointerException("Move named " + name + " not found in moveMap");
        return mapMove;
    }

    private static PojoFrequency convertFrequency(String freqText)
    {
        Frequency freq = Frequency.getWithName(freqText);
        int uses = (freq == Frequency.DAILY || freq == Frequency.SCENE) ? 1 : 0;
        if(freq == null) // The frequency must have a usage count on it (i.e. Scene x2)
        {
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
            priority = ActionType.Priority.getWithName(split[1]);
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

    private static EnumMap<Stat.StatName, Integer> convertBaseStats(BaseStats baseStats)
    {
        EnumMap<Stat.StatName, Integer> stats = new EnumMap<>(Stat.StatName.class);
        stats.put(Stat.StatName.HP, baseStats.getHp());
        stats.put(Stat.StatName.ATTACK, baseStats.getAttack());
        stats.put(Stat.StatName.DEFENSE, baseStats.getDefense());
        stats.put(Stat.StatName.SPECIAL_ATTACK, baseStats.getSpecialAttack());
        stats.put(Stat.StatName.SPECIAL_DEFENSE, baseStats.getSpecialDefense());
        stats.put(Stat.StatName.SPEED, baseStats.getSpeed());
        return stats;
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
                connection = getMove(effect.substring(CONNECTION_HEADER.length(), effect.indexOf(". ")));
            }
            abilities.put(name, new Ability(name, pojoFreq.getFreq(), pojoFreq.getUses(), pojoActionType.getActionType(),
                    pojoActionType.getPriority(), a.getTrigger(), a.getTarget(), a.getEffect(), connection));
        });
        if(convertedAbilities == null)
            convertedAbilities = abilities;
        return abilities;
    }

    public static Map<String, Move> moveMapBuilder(Map<String, MovePojo> pojoMap)
    {
//        Map<String, Move> moves = new HashMap<>(pojoMap.size());
        Map<String, Move> moves = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        pojoMap.forEach((name, m) -> {
            Type type = convertType(m.getType());
            PojoFrequency pojoFreq = convertFrequency(m.getFreq());

            // All moves have a Move Class, so we don't catch the exception and let it throw if we're missing it
            Move.MoveClass moveClass = Move.MoveClass.valueOf(m.getDamageClass().toUpperCase());

            Move.ContestType contestType = null;
            try {
                contestType = Move.ContestType.valueOf(m.getContestType().toUpperCase());
            }
            catch (IllegalArgumentException | NullPointerException ignored) {}

            Move.ContestEffect contestEffect = Move.ContestEffect.getContestEffect(m.getContestEffect());

            moves.put(name, new Move(name, type, pojoFreq.getFreq(), pojoFreq.getUses(), m.getAc(), m.getDb(),
                    moveClass, m.getRange(), m.getEffect(), contestType, contestEffect, m.getCritsOn()));
        });
        if(convertedMoves == null)
            convertedMoves = moves;
        return moves;
    }

    public static Map<String, PokemonSpecies> pokemonMapBuilder(Map<String, PokemonSpeciesPojo> pojoMap)
    {
        Map<String, PokemonSpecies> pokemon = new HashMap<>(pojoMap.size());
//        Map<String, PokemonSpecies> pokemon = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
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
            newPoke.setInchesHeightMin((int)(inchRange.getMinimum().getInches()
                    + (inchRange.getMinimum().getFeet() * 12)));
            newPoke.setInchesHeightMax((int)(inchRange.getMaximum().getInches()
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

            // Abilities - Map<Ability.AbilityType, Ability>
//            newPoke.setBaseAbilities(p.getAbilities().stream().collect(Collectors.toMap(
//                    a1 -> Ability.AbilityType.valueOf(a1.getType().toUpperCase()),
//                    a2 -> Stream.of(a2.getName()).map(PojoToDBConverter::getAbility).collect(Collectors.toList()), (l1, l2) -> {
//                            l1.addAll(l2);
//                            return l1;
//            })));
            p.getAbilities().stream().forEach(a ->
                    newPoke.addBaseAbility(BaseAbility.AbilityType.valueOf(a.getType().toUpperCase()),
                            PojoToDBConverter.getAbility(a.getName()))
            );

            // Evolution Stagess
//            newPoke.setEvolutionStages(p.getEvolutionStages().stream().map(e ->
//                new PokemonSpecies.EvolutionStage((int) e.getStage(), e.getSpecies(), e.getCriteria())
//            ).collect(Collectors.toList()));
//
//            // Mega Evolution
//            newPoke.setMegaEvolutions(convertMegaEvolutions(p.getMegaEvolutions()));

            // Skills - Map<Skill, String>
            newPoke.setSkills(new EnumMap<Skill, String>(p.getSkills().stream().collect(Collectors.toMap(
                    s -> Skill.getWithName(s.getSkillName()),
                    org.example.jsonLoading.db.pokemon.Skill::getDiceRank))));

            // Capabilities - Map<String, String>
//            newPoke.setCapabilities(p.getCapabilities().stream().collect(Collectors.toMap(
//                    Capability::getCapabilityName, Capability::getValue)));
//            newPoke.setCapabilities(p.getCapabilities().stream().map(c -> {
//                String ret = c.getCapabilityName();
//                if(c.getValue() != null)
//                    ret += ": " + c.getValue();
//                return ret;
//            }).collect(Collectors.toList()));

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
        if(convertedPokemonSpecies == null)
            convertedPokemonSpecies = pokemon;
        return pokemon;
    }

    public static void main(String[] args)
    {
        Map<String, MovePojo> pojoMoves = PokedexLoader.parsePojoMoves();
        Map<String, Move> moves = moveMapBuilder(pojoMoves);
        // moves.values().forEach(System.out::println);

        Map<String, AbilityPojo> pojoAbility = PokedexLoader.parsePojoAbilities();
        Map<String, Ability> abilities = abilityMapBuilder(pojoAbility);
//        abilities.values().forEach(System.out::println);

        Map<String, PokemonSpeciesPojo> pojoPokes = PokedexLoader.parsePojoPokemon();
        Map<String, PokemonSpecies> pokes = pokemonMapBuilder(pojoPokes);
        //pokes.values().forEach(System.out::println);
        System.out.println("Done");
    }
}
