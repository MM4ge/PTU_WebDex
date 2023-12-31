package org.allenfulmer.ptuviewer.fileLoading;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.allenfulmer.ptuviewer.fileLoading.pojo.ability.AbilityPojo;
import org.allenfulmer.ptuviewer.fileLoading.pojo.move.MovePojo;
import org.allenfulmer.ptuviewer.fileLoading.pojo.pokemon.BaseStats;
import org.allenfulmer.ptuviewer.fileLoading.pojo.pokemon.EvolutionStage;
import org.allenfulmer.ptuviewer.fileLoading.pojo.pokemon.ImperialHeightRange;
import org.allenfulmer.ptuviewer.fileLoading.pojo.pokemon.PokemonSpeciesPojo;
import org.allenfulmer.ptuviewer.models.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class PojoToDBConverter {
    public static final String ABILITY_DIVIDER = " - ";
    private static final String CONNECTION_HEADER = "Connection - ";
    private static Map<String, Capability> convertedCapabilities = null;
    private static Map<Integer, Integer> convertedExperience = null;
    private static Map<String, Ability> convertedAbilities = null;
    private static Map<String, Move> convertedMoves = null;
    private static Map<String, PokemonSpecies> convertedPokemonSpecies = null;

    public static Map<Integer, Integer> getConvertedExperience() {
        if (convertedExperience == null)
            convertedExperience = JsonToPojoLoader.parseExperience();
        return Collections.unmodifiableMap(convertedExperience);
    }

    /**
     * Small wrapper function to ensure Capabilities aren't parsed twice; once for the DB and once for Pokemon
     */
    public static Map<String, Capability> getConvertedCapabilities() {
        if (convertedCapabilities == null)
            convertedCapabilities = JsonToPojoLoader.parseCapabilities();
        return Collections.unmodifiableMap(convertedCapabilities);
    }

    // TODO: (maybe feature creep) pokemon map that's also by name+form as unique key instead of just pokedex ID
    private static Map<String, PokemonSpecies> getConvertedPokemonSpecies() {
        if (convertedPokemonSpecies == null)
            convertedPokemonSpecies = pokemonMapBuilder(JsonToPojoLoader.parsePojoPokemon());
        return convertedPokemonSpecies;
    }
    //TODO: all 4 of the aboves, move functionality out of command line runner and ensure null saftey for maps so they
    // don't have to be done in order or else everything explodes

    public static Map<String, PokemonSpecies> getConvertedPokemonSpeciesMap() {
        return Collections.unmodifiableMap(getConvertedPokemonSpecies());
    }

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

    public static Capability getCapability(String name) {
        Capability mapCapability = getConvertedCapabilities().get(name);
        if (mapCapability == null)
            throw new NullPointerException("Capability named " + name + " not found in capabilityMap");
        return mapCapability;
    }

    public static PokemonSpecies getPokemonSpecies(String dexID) {
        PokemonSpecies mapPoke = getConvertedPokemonSpecies().get(dexID);
        if (mapPoke == null)
            throw new NullPointerException("Pokemon Species with dexID " + dexID + " not found in pokemonSpeciesMap");
        return mapPoke;
    }

    /**
     * @param name The species name of the Pokemon.
     * @param form The form of the Pokemon. Only used if the name matches more than 1 Pokemon.
     * @return The matching PokemonSpecies with the given name or given name and form
     * @throws NullPointerException Thrown if no Pokemon with the supplied name exist, or there isn't just 1 Pokemon
     *                              with the given name and form.
     */
    public static PokemonSpecies getPokemonSpecies(String name, String form) {
        List<PokemonSpecies> mapPokes = new ArrayList<>(getConvertedPokemonSpecies().values().stream().filter(
                p -> name.equalsIgnoreCase(p.getSpeciesName())).toList());
        if (mapPokes.size() > 1)
            mapPokes.removeIf(p -> !form.equalsIgnoreCase(p.getForm()));

        if (mapPokes.size() != 1)
            throw new NullPointerException("Pokemon Species with name " + name + " and form " + form +
                    " not found in pokemonSpeciesMap");
        return mapPokes.get(0);
    }

    /*
    "EvolutionStages": [
      {
        "Stage": 1,
        "Species": "Bulbasaur",
        "Criteria": ""
      },
      {
        "Stage": 2,
        "Species": "Ivysaur",
        "Criteria": "Minimum 15"
      },
      {
        "Stage": 3,
        "Species": "Venusaur",
        "Criteria": "Minimum 30"
      }
    */
    /*
    "EvolutionStages": [
      {
        "Stage": 1,
        "Species": "Dialga",
        "Criteria": ""
      }
    */
    /*
    "050:A": {
    "Species": "Diglett",
    "Form": "Alola",
    ...
    "EvolutionStages": [
      {
        "Stage": 1,
        "Species": "Diglett (A)",
        "Criteria": ""
      },
      {
        "Stage": 2,
        "Species": "Dugtrio (A)",
        "Criteria": "Minimum 25"
      }
    */
    /*
    Pokemon Species --
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)//, mappedBy = "evoTargetSpecies")
    Evolution pastEvolution = null;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evoSourceSpecies", fetch = FetchType.EAGER)
    List<Evolution> futureEvolutions = null;
     */
    /*
    Evolution --
    int stage;
    int level;
    String criteria;
    PokemonSpecies evoSourceSpecies;
    PokemonSpecies evoTargetSpecies;
     */
//    private static void convertEvolutions(Map<PokemonSpecies, List<EvolutionStage>> evoMap) {
//        List<String> validForms = Arrays.asList(PokeConstants.NON_REGIONAL_FORM, "Alola", "Galar", "Hisuian");
//        // Add unique exceptions to the list, like Wormadam doesn't have a Standard form
//        Map<String, String> formExceptions = new HashMap<>();
//        formExceptions.put("413", PokeConstants.NON_REGIONAL_FORM);
//        formExceptions.put("678", PokeConstants.NON_REGIONAL_FORM);
//        formExceptions.put("711", PokeConstants.NON_REGIONAL_FORM);
//        formExceptions.put("745", PokeConstants.NON_REGIONAL_FORM);
//        formExceptions.put("555:G", "Galar");
//        formExceptions.put("745:D", "Dusk");
//        formExceptions.put("745:M", "Midnight");
//
//
//        Map<String, PokemonSpecies> nameShortFormMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
//        nameShortFormMap.putAll(getConvertedPokemonSpeciesMap().entrySet().stream().filter(e ->
//                        validForms.contains(e.getValue().getForm()) || formExceptions.containsKey(e.getValue().getPokedexID()))
//                .collect(Collectors.toMap(e -> {
//                    String name = e.getValue().getSpeciesName();
//                    String form = e.getValue().getForm();
//                    if(formExceptions.containsKey(e.getValue().getPokedexID()))
//                        form = formExceptions.get(e.getValue().getPokedexID());
//                    if (PokeConstants.NON_REGIONAL_FORM.equalsIgnoreCase(form))
//                        return name;
//                    return name + " (" + form.charAt(0) + ")";
//                }, Map.Entry::getValue)));
//        // Flabebe has a stronk - "FlabÉBÉ" id: 669 -- fix by having treeMap - caseInsensitiveSorter?
//        // More exclusions
//        nameShortFormMap.put("Lycanrock (Midday)", getConvertedPokemonSpeciesMap().get("745"));
//        nameShortFormMap.put("Lycanrock (Midnight)", getConvertedPokemonSpeciesMap().get("745:M"));
//        nameShortFormMap.put("Lycanrock (Dusk)", getConvertedPokemonSpeciesMap().get("745:D"));
//
//        Map<PokemonSpecies, Evolution> prevEvo = new HashMap<>();
//        evoMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(e -> {
//            PokemonSpecies poke = e.getKey();
//            List<EvolutionStage> stages = e.getValue();
//            if (e.getValue().size() <= 1)
//                return;
//
//            /*
//            Pokemon are always in the Dex in evolution order - take advantage of it
//            Each poke checks if it's in the prevEvo map with itself as key - if so, it adds the result of it as its
//                previous Evo and ends there.
//            Otherwise, it parses its evo and upwards - adding its immediate evolution(s) to its futureEvos list, adding
//                the same Evolutions to the prevEvo map with themselves as the key, and recursively causing the same
//                things to happen to them
//            Thus, all pokemon have all of their future Evos already linked once the first member of their evo family
//                is parsed, and can link their prev evo by simply checking the Map for it.
//            There is one issue; when Pokemon are added into evolution families in different Gens, like Hisuian Decidueye
//                To solve this, when a pokemon checks the prevMap and finds nothing, it should check its own evoStage
//                and see if it's a first form. If so, continue as normal. If not, then it needs to parse backwards
//                and add itself to the previous pokemon's futureEvo list and then act as if it's starting parsing
//                a new evolution family
//            Ensure that everything adds itself to the prev evo list, even 1st stage evos with a prev of null, since
//                non-regional forms will treat themselves just as the basic pokemon for evo family purposes
//             */
//            if (prevEvo.containsKey(poke)) {
//                poke.setPastEvolution(prevEvo.get(poke));
//                return;
//            }
//
//            // Figure out what stage the Pokemon is
//            int currEvoStgIndex = -1;
//            for (int i = 0; i < stages.size(); i++) {
//                if (stages.get(i).getSpecies().toLowerCase().startsWith(poke.getSpeciesName().toLowerCase())) {
//                    currEvoStgIndex = i;
//                    break;
//                }
//            }
//            if(currEvoStgIndex < 0)
//                log.info("Here");
//            parseEvolutionHelper(poke, null, stages, currEvoStgIndex, prevEvo, nameShortFormMap, Pattern.compile("\\d+"));
//        });
//        log.info("Done parsing evolutions");
//    }
//
//    private static void parseEvolutionHelper(PokemonSpecies poke, Evolution prevEvo, List<EvolutionStage> stages, int currEvoStgIndex,
//                                             Map<PokemonSpecies, Evolution> evoMap, Map<String, PokemonSpecies> nameMap,
//                                             Pattern levelRegex) {
//        if(!evoMap.containsValue(prevEvo))
//            evoMap.put(poke, prevEvo);
////        else {
////            return;
////        }
//        EvolutionStage currStg = stages.get(currEvoStgIndex);
//        long evoNumStg = currStg.getStage();
//        // TODO: code for getting evolved form FIRST cuz mr. mime is before mime.jr and it dont have pre evo
//
//        for (int i = currEvoStgIndex + 1; i < stages.size(); i++) {
//            EvolutionStage stg = stages.get(i);
//            // If it isn't an evolution of the current stage
//            if (stg.getStage() != evoNumStg + 1)
//                continue;
//            // Get curr poke
//            PokemonSpecies evolvedPoke = nameMap.get(stg.getSpecies());
//            if(evolvedPoke == null)
//                log.info("Broken Poke: " + stg.getSpecies());
//
//            // parse into Evolution
//            Evolution evo = new Evolution();
//            evo.setEvoSourceSpecies(poke);
//            evo.setEvoTargetSpecies(evolvedPoke);
//            evo.setStage((int) stg.getStage());
//
//            Matcher matcher = levelRegex.matcher(stg.getCriteria());
//            if (matcher.find())
//                evo.setLevel(Integer.parseInt(matcher.group()));
//            evo.setCriteria(stg.getCriteria());
//
//            // Recurse
//            poke.addFutureEvolution(evo);
////            parseEvolutionHelper(evolvedPoke, evo, stages, i, );
//            parseEvolutionHelper(evolvedPoke, evo, stages, i, evoMap, nameMap, levelRegex);
//        }
//    }

    private static Set<BaseCapability> convertCapabilities(PokemonSpecies poke,
                                                           List<org.allenfulmer.ptuviewer.fileLoading.pojo.pokemon.Capability> pojoCapabilities) {
        Set<BaseCapability> capabilities = new TreeSet<>();
        for (org.allenfulmer.ptuviewer.fileLoading.pojo.pokemon.Capability curr : pojoCapabilities) {
            String name = curr.getCapabilityName();
            String value = curr.getValue();
            Capability capability = null;

            /*
            Check for special cases
            some capabilites like Alluring and Planter can have additional criteria that are part of the name instead
                of in the value
            Jump needs to be split into High and Long Jump
            Naturewalk needs parentheses around its environments
             */

            // Check for Jump and Naturewalk
            if (name.equalsIgnoreCase("Jump")) // Jump X/Y -- X is Long, Y is High
            {
                String[] ranks = value.split("/");
                capabilities.add(new BaseCapability(Integer.parseInt(ranks[0].trim()), getCapability("Long Jump"), poke));
                capabilities.add(new BaseCapability(Integer.parseInt(ranks[1].trim()), getCapability("High Jump"), poke));
                continue;
            } else if (name.equalsIgnoreCase("Naturewalk")) // Naturewalk Tundra,Urban
            {
                String environments;
                String[] envAry = value.split(",");

                if (envAry[0].startsWith("("))
                    environments = Arrays.stream(envAry).map(String::trim)
                            .collect(Collectors.joining(", "));
                else
                    environments = Arrays.stream(envAry).map(String::trim)
                            .collect(Collectors.joining(", ", "(", ")"));

                capabilities.add(new BaseCapability(environments, getCapability(curr.getCapabilityName()), poke));
                continue;
            }
            // Capabilities with values in the pojo name section
            try {
                capability = getCapability(curr.getCapabilityName());
            } catch (NullPointerException ex) {
                int firstSpace = name.indexOf(" ");
                if (firstSpace == -1) // If we can't have a special case, throw the missing capability exception
                    throw ex;

                // If it can be a special case, try to get it directly then continue as normal
                value = name.substring(firstSpace + 1, name.length());
                name = name.substring(0, firstSpace);
                capability = getCapability(name);
            }

            // Check for name-only capabilities
            if (value == null || value.isEmpty()) {
                capabilities.add(new BaseCapability(capability, poke));
                continue;
            }

            // Now for rank capabilities - if the value is a #, then it's a rank, otherwise it's a criteria
            try {
                capabilities.add(new BaseCapability(Integer.parseInt(value), capability, poke));
                continue;
            } catch (NumberFormatException ignored) { // Ignored on purpose, see above
            }

            // If we're here, the value is a criteria, just add it
            capabilities.add(new BaseCapability(value, capability, poke));

        }
        return capabilities;
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

    private static Set<Skill> convertSkills(PokemonSpecies species,
                                            List<org.allenfulmer.ptuviewer.fileLoading.pojo.pokemon.Skill> pojoSkills) {
        return pojoSkills.stream().map(s -> {
            // Convert the die rank and bonuses -- 3d6+4 : rank = 3, bonus = 4
            // Skills can have formatting errors (2d6 instead of 2d6+0) or negative bonuses (3d6-1)
            String dice = s.getDiceRank();
            int rank = Integer.parseInt(dice.substring(0, dice.indexOf('d')).trim());
            int bonus = 0;
            // Only change bonus if we have one
            if (dice.lastIndexOf("d6") + 2 < dice.length()) {
                bonus = Integer.parseInt(dice.substring(dice.indexOf("d6") + 2).trim());
            }
            return new Skill(s.getSkillName(), rank, bonus, species);
        }).collect(Collectors.toCollection(TreeSet::new));
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
                pojoFreq = convertFrequency(split[0].trim());
                pojoActionType = convertActionType(split[1].trim());
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
            Move.MoveClass moveClass = Move.MoveClass.getWithName(m.getDamageClass());

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
        Map<PokemonSpecies, List<EvolutionStage>> evoMap = new HashMap<>(pojoMap.size());
        pojoMap.forEach((id, p) -> {
            PokemonSpecies newPoke = new PokemonSpecies();

            // Basic info: Pokedex #, Name, Form
            newPoke.setPokedexID(id);
            newPoke.setSpeciesName(p.getSpecies());
            newPoke.setForm(p.getForm());

            // Capabilities - Set<BaseCapability>
            newPoke.setBaseCapabilities(convertCapabilities(newPoke, p.getCapabilities()));

            // Skills - Set<Skill>
            newPoke.setSkills(convertSkills(newPoke, p.getSkills()));

            // Base Stats - EnumMap<Stat.StatName, Integer>
            newPoke.setBaseStatsFromMap(convertBaseStats(p.getBaseStats()));

            // Height
            if (p.getHeight() != null) {
                ImperialHeightRange inchRange = p.getHeight().getImperial();
                newPoke.setInchesHeightMin((int) (inchRange.getMinimum().getInches()
                        + (inchRange.getMinimum().getFeet() * 12)));
                newPoke.setInchesHeightMax((int) (inchRange.getMaximum().getInches()
                        + (inchRange.getMaximum().getFeet() * 12)));
                newPoke.setHeightCategoryMin(p.getHeight().getCategory().getMinimum());
                newPoke.setHeightCategoryMax(p.getHeight().getCategory().getMaximum());
            }

            // Weight
            if (p.getWeight() != null) {
                newPoke.setPoundsWeightMin(p.getWeight().getImperial().getMinimum().getPounds());
                newPoke.setPoundsWeightMax(p.getWeight().getImperial().getMaximum().getPounds());
                newPoke.setWeightClassMin((int) p.getWeight().getWeightClass().getMinimum());
                newPoke.setWeightClassMax((int) p.getWeight().getWeightClass().getMaximum());
            }

            // Breeding Data + Habitats
            newPoke.setMaleChance((!p.getBreedingData().isHasGender()) ? null : p.getBreedingData().getMaleChance());
            newPoke.setEggGroups(p.getBreedingData().getEggGroups().stream().map(EggGroup::getWithName).toList());
            newPoke.setHabitats(p.getEnvironment().getHabitats().stream().map(Habitat::getWithName).toList());

            // Types - List<Type>
            newPoke.setTypesFromList(p.getTypes().stream().map(PojoToDBConverter::convertType).toList());

            // BaseAbilities - List<BaseAbility>
            p.getAbilities().forEach(a ->
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

            // Prev Evo Map for parsing after all Pokemon are made
            if (p.getEvolutionStages().size() > 1)
                evoMap.put(newPoke, p.getEvolutionStages());

            // LevelUpMoves - List<LevelUpMove>
            p.getLevelUpMoves().forEach(m ->
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

        // Evo Map parsing
        evoMapParsing(pokemon, evoMap);

        return pokemon;
    }

    // FC: Get EggMoves from first tier pokemon with past evo, get level moves from potential past pokemon - have
    //  settings in generatedPokemon for choosing to do these things (maybe not egg moves, that's already as the
    //  unique moves option)
    private static void evoMapParsing(Map<String, PokemonSpecies> pokeMap, Map<PokemonSpecies, List<EvolutionStage>> evoMap) {
        Map<String, PokemonSpecies> nameMap = generateNameMap(pokeMap);
//        Pattern levelRegex = Pattern.compile("\\d+");
        Pattern levelRegex = Pattern.compile("[0-9](?<![0-9][0-9]|[0-9]\\.[0-9])[0-9]*+(?!\\.[0-9])");
        for (Map.Entry<PokemonSpecies, List<EvolutionStage>> currEntry : evoMap.entrySet()) {
            int currEvoNumStage = 0, currEvoIndex = 0;
            for(int i = 0; i < currEntry.getValue().size(); i++) {
                EvolutionStage stageIter = currEntry.getValue().get(i);
                if(stageIter.getSpecies().startsWith(currEntry.getKey().getSpeciesName())) {
                    currEvoNumStage = (int) stageIter.getStage();
                    currEvoIndex = i;
                    break;
                }
            }
            if (currEvoNumStage == 0)
                log.info("Here");
            if (currEvoNumStage <= 1) // First stage poke doesn't have a prev
                continue;

            EvolutionStage prevEvoStage = currEntry.getValue().get(currEvoIndex - 1);
            PokemonSpecies currPoke = currEntry.getKey();
            Matcher matcher = levelRegex.matcher(currEntry.getValue().get(currEvoIndex).getCriteria());
            if (!matcher.find()) {
                if(currEntry.getValue().get(currEvoIndex).getCriteria().isBlank())
                    log.info("Here");
                // If it's a 3rd stage and didn't have a level, see if 2nd stage did and use it
                if(currEvoNumStage == 3)
                {
                    Matcher matcher2 = levelRegex.matcher(currEntry.getValue().get(1).getCriteria());
                    if(matcher2.find())
                        currPoke.setPrevEvoLevel(Integer.parseInt(matcher2.group()));
                }
                else
                    currPoke.setPrevEvoLevel(1);
            }
            else
                currPoke.setPrevEvoLevel(Integer.parseInt(matcher.group()));
            currPoke.setPrevEvo(nameMap.get(prevEvoStage.getSpecies()));
        }
    }

    private static Map<String, PokemonSpecies> generateNameMap(Map<String, PokemonSpecies> pokeMap) {
        List<String> validForms = Arrays.asList(PokeConstants.NON_REGIONAL_FORM, "Alola", "Galar", "Hisuian");
//        // Add unique exceptions to the list, like Wormadam doesn't have a Standard form
        Map<String, String> formExceptions = new HashMap<>();
        formExceptions.put("413", PokeConstants.NON_REGIONAL_FORM);
        formExceptions.put("678", PokeConstants.NON_REGIONAL_FORM);
        formExceptions.put("711", PokeConstants.NON_REGIONAL_FORM);
        formExceptions.put("745", PokeConstants.NON_REGIONAL_FORM);
        formExceptions.put("555:G", "Galar");
        formExceptions.put("745:D", "Dusk");
        formExceptions.put("745:M", "Midnight");


        Map<String, PokemonSpecies> nameShortFormMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        nameShortFormMap.putAll(pokeMap.entrySet().stream().filter(e ->
                        validForms.contains(e.getValue().getForm()) || formExceptions.containsKey(e.getValue().getPokedexID()))
                .collect(Collectors.toMap(e -> {
                    String name = e.getValue().getSpeciesName();
                    String form = e.getValue().getForm();
                    if(formExceptions.containsKey(e.getValue().getPokedexID()))
                        form = formExceptions.get(e.getValue().getPokedexID());
                    if (PokeConstants.NON_REGIONAL_FORM.equalsIgnoreCase(form))
                        return name;
                    return name + " (" + form.charAt(0) + ")";
                }, Map.Entry::getValue)));
        // Flabebe has a stronk - "FlabÉBÉ" id: 669 -- fix by having treeMap - caseInsensitiveSorter?
        // More exclusions
        nameShortFormMap.put("Lycanrock (Midday)", getConvertedPokemonSpeciesMap().get("745"));
        nameShortFormMap.put("Lycanrock (Midnight)", getConvertedPokemonSpeciesMap().get("745:M"));
        nameShortFormMap.put("Lycanrock (Dusk)", getConvertedPokemonSpeciesMap().get("745:D"));

        return nameShortFormMap;
    }

    public static void main(String[] args) {
        populatePokemonMaps();
        log.info("Total count:" + convertedMoves.size() + convertedAbilities.size() + convertedPokemonSpecies.size());
        testCode();
    }

    public static void testCode() {
        convertedPokemonSpecies.values().stream().sorted((p1, p2) -> {
            Integer total1 = p1.getBaseStats().values().stream().mapToInt(Integer::intValue).sum();
            return total1.compareTo(p2.getBaseStats().values().stream().mapToInt(Integer::intValue).sum());
        }).forEach(p -> System.out.println(p.getSpeciesName() + ": " +
                p.getBaseStats().values().stream().mapToInt(Integer::intValue).sum()));

        int[] stats = new int[6];
        for (int i = 0; i < 6; i++)
            stats[i] = 1;
        for (int i = 0; i < 57; i++) {
            stats[PokeConstants.RANDOM_GEN.nextInt(6)]++;
        }
        log.info("   HP: " + stats[0]);
        log.info("  Atk: " + stats[1]);
        log.info("  Def: " + stats[2]);
        log.info("SpAtk: " + stats[3]);
        log.info("SpDef: " + stats[4]);
        log.info("Speed: " + stats[5]);

        for (int i = 0; i < 7; i++) {
            log.info(convertedMoves.values().stream().toList().get(PokeConstants.RANDOM_GEN.nextInt(convertedMoves.size())).toString());
        }
        for (int i = 0; i < 4; i++) {
            log.info(convertedAbilities.values().stream().toList().get(PokeConstants.RANDOM_GEN.nextInt(convertedAbilities.size())).toString());
        }
    }

    public static Map<String, PokemonSpecies> populatePokemonMaps() {
        Map<String, MovePojo> pojoMoves = JsonToPojoLoader.parsePojoMoves();
        Map<String, Move> moves = moveMapBuilder(pojoMoves);
        log.info("Num of Moves: " + moves.size());

        Map<String, AbilityPojo> pojoAbility = JsonToPojoLoader.parsePojoAbilities();
        Map<String, Ability> abilities = abilityMapBuilder(pojoAbility);

        Map<String, PokemonSpeciesPojo> pojoPokes = JsonToPojoLoader.parsePojoPokemon();
        TreeMap<String, PokemonSpecies> pokes = new TreeMap<>(pokemonMapBuilder(pojoPokes));

        Set<String> connections = abilities.values().stream().map(a -> {
            Move connection = a.getConnection();
            return (connection == null) ? null : connection.getDisplayName();
        }).filter(Objects::nonNull).collect(Collectors.toSet());
        log.info(connections.stream().sorted().collect(Collectors.joining("\n")));

        log.info("---Done---");

        return pokes;
    }

    @AllArgsConstructor
    @RequiredArgsConstructor
    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    private static class PojoFrequency {
        @NonNull
        Frequency freq;
        int uses = 0;
    }

    @AllArgsConstructor
    @RequiredArgsConstructor
    @NoArgsConstructor
    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    private static class PojoActionType {
        @NonNull
        ActionType actionType;
        ActionType.Priority priority = null;
    }
}
