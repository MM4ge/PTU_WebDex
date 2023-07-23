package org.allenfulmer.ptuviewer.fileLoading;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.allenfulmer.ptuviewer.fileLoading.pojo.ability.AbilityPojo;
import org.allenfulmer.ptuviewer.fileLoading.pojo.move.MovePojo;
import org.allenfulmer.ptuviewer.fileLoading.pojo.pokemon.Ability;
import org.allenfulmer.ptuviewer.fileLoading.pojo.pokemon.PokemonSpeciesPojo;
import org.allenfulmer.ptuviewer.fileLoading.pojo.pokemon.Skill;
import org.allenfulmer.ptuviewer.models.Capability;
import org.allenfulmer.ptuviewer.models.Type;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class JsonToPojoLoader {
    private static final String POKEDEX_FILEPATH = "src/main/resources/static/json/preAlola/pokedex.json";
    private static final String FULL_POKEDEX_FILEPATH = "src/main/resources/static/json/full_pokedex.json";
    private static final String MOVES_FILEPATH = "src/main/resources/static/json/preAlola/moves.json";
    private static final String FULL_MOVES_FILEPATH = "src/main/resources/static/json/full_moves.json";
    private static final String ABILITIES_FILEPATH = "src/main/resources/static/json/preAlola/abilities.json";
    private static final String FULL_ABILITIES_FILEPATH = "src/main/resources/static/json/full_abilities.json";
    private static final String CAPABILITIES_FILEPATH = "src/main/resources/static/json/capabilities.json";
    private static final String EXPERIENCE_FILEPATH = "src/main/resources/static/json/experience.json";

    private JsonToPojoLoader() {
    }

    private static final Gson gson = new GsonBuilder().setLenient().create();

    // FC: Experience system for loading pokemon with their base exp total instead of 0?
    public static Map<Integer, Integer> parseExperience() {
        Map<String, Integer> jsonExperience = gson.fromJson(readFromFile(EXPERIENCE_FILEPATH), new TypeToken<Map<String, Integer>>() {
        }.getType());
        return jsonExperience.entrySet().stream().collect(Collectors.toMap(
                e -> Integer.parseInt(e.getKey()), Map.Entry::getValue));
    }

    public static Map<String, Capability> parseCapabilities() {
        Map<String, String> jsonCapabilities = gson.fromJson(readFromFile(CAPABILITIES_FILEPATH), new TypeToken<Map<String, String>>() {
        }.getType());
        return jsonCapabilities.entrySet().stream().map(e -> new Capability(e.getKey(), e.getValue()))
                .collect(Collectors.toMap(Capability::getName, c -> c));
    }

    public static Map<String, PokemonSpeciesPojo> parsePojoPokemon() {
        if (Files.exists(Paths.get(FULL_POKEDEX_FILEPATH)))
            return gson.fromJson(readFromFile(FULL_POKEDEX_FILEPATH), new TypeToken<Map<String, PokemonSpeciesPojo>>() {
            }.getType());

        Map<String, PokemonSpeciesPojo> altPokemon = TxtToPojoLoader.getAltPokemon();
        Map<String, PokemonSpeciesPojo> ptuToolsPokemon = gson.fromJson(readFromFile(POKEDEX_FILEPATH),
                new TypeToken<Map<String, PokemonSpeciesPojo>>() {
                }.getType());
        ptuToolsPokemon.putAll(altPokemon);
        TreeMap<String, PokemonSpeciesPojo> sortedPokemon = new TreeMap<>(ptuToolsPokemon);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FULL_POKEDEX_FILEPATH))) {
            gson.toJson(sortedPokemon, writer);
        } catch (IOException ignored) {
            // Shouldn't ever happen, but it'll just re-create the files with the same process on the next run if it does
        }
        return ptuToolsPokemon;
    }

    public static Map<String, MovePojo> parsePojoMoves() {
        if (Files.exists(Paths.get(FULL_MOVES_FILEPATH)))
            return gson.fromJson(readFromFile(FULL_MOVES_FILEPATH), new TypeToken<Map<String, MovePojo>>() {
            }.getType());

        Map<String, MovePojo> altMoves = TxtToPojoLoader.getAltMoves();
        Map<String, MovePojo> ptuToolsMoves = gson.fromJson(readFromFile(MOVES_FILEPATH),
                new TypeToken<Map<String, MovePojo>>() {
                }.getType());
        ptuToolsMoves.putAll(altMoves);
        TreeMap<String, MovePojo> sortedMoves = new TreeMap<>(ptuToolsMoves);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FULL_MOVES_FILEPATH))) {
            gson.toJson(sortedMoves, writer);
        } catch (IOException ignored) {
            // Shouldn't ever happen, but it'll just re-create the files with the same process on the next run if it does
        }
        return ptuToolsMoves;
    }

    public static Map<String, AbilityPojo> parsePojoAbilities() {
        if (Files.exists(Paths.get(FULL_ABILITIES_FILEPATH)))
            return gson.fromJson(readFromFile(FULL_ABILITIES_FILEPATH), new TypeToken<Map<String, AbilityPojo>>() {
            }.getType());

        Map<String, AbilityPojo> altAbilities = TxtToPojoLoader.getAltAbilities();
        Map<String, AbilityPojo> ptuToolsAbilities = gson.fromJson(readFromFile(ABILITIES_FILEPATH),
                new TypeToken<Map<String, AbilityPojo>>() {
                }.getType());
        ptuToolsAbilities.putAll(altAbilities);

        // Type Aura hack
        if (ptuToolsAbilities.containsKey("Type Aura")) {
            AbilityPojo typeAura = ptuToolsAbilities.get("Type Aura");
            for (Type curr : Type.values()) {
                if (!(curr.equals(Type.TYPES) || curr.equals(Type.TYPELESS)))
                    ptuToolsAbilities.put("Type Aura (" + curr.getDisplayName() + ")", typeAura);
            }
        }

        TreeMap<String, AbilityPojo> sortedAbilities = new TreeMap<>(ptuToolsAbilities);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FULL_ABILITIES_FILEPATH))) {
            gson.toJson(sortedAbilities, writer);
        } catch (IOException ignored) {
            // Shouldn't ever happen, but it'll just re-create the files with the same process on the next run if it does
        }
        return ptuToolsAbilities;
    }


    public static String readFromFile(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) {
        // Local variables left here for easy debugging in the debugger if necessary
        Map<String, MovePojo> moves = parsePojoMoves();
        Map<String, AbilityPojo> abilities = parsePojoAbilities();
        Map<String, PokemonSpeciesPojo> pokes = parsePojoPokemon();
        List<String> skills = pokes.values().stream().flatMap(p -> p.getSkills().stream()).map(Skill::getSkillName).distinct().toList();
        skills.forEach(log::info);

        String longy = moves.values().stream().map(MovePojo::getEffect).filter(Objects::nonNull).max(Comparator.comparingInt(String::length)).orElse("Nothing");
        log.info("Len: " + longy.length() + " Content: " + longy);

        Set<String> acs = moves.values().stream().map(MovePojo::getAc).collect(Collectors.toSet());
        acs.forEach(log::info);

        Set<String> lunch = pokes.values().stream().filter(p ->
        {
            List<String> abilityNames = p.getAbilities().stream().map(Ability::getName).toList();
            return abilityNames.contains("Lunchbox");
        }).map(PokemonSpeciesPojo::getSpecies).collect(Collectors.toSet());
        lunch.forEach(log::info);

//        List<List<String>> habitats = pokes.values().stream().map(p -> p.getEnvironment().getHabitats()).collect(Collectors.toList());
//        int largest = -1;
//        for(List<String> curr : habitats)
//            largest = Math.max(largest, curr.size());
//        System.out.println("Largest # of habitats on one poke: " + largest);
//        List<String> sortedHabitats = habitats.stream().flatMap(List::stream).distinct().sorted().collect(Collectors.toList());
//        sortedHabitats.forEach(System.out::println);
//
//        List<List<String>> eggGroups = pokes.values().stream().map(p -> p.getBreedingData().getEggGroups()).collect(Collectors.toList());
//        largest = -1;
//        for(List<String> curr : eggGroups)
//            largest = Math.max(largest, curr.size());
//        System.out.println("Largest # of eggGroups on one poke: " + largest);
//        List<String> sortedEggGroups = eggGroups.stream().flatMap(List::stream).distinct().sorted().collect(Collectors.toList());
//        sortedEggGroups.forEach(System.out::println);
//        List<String> skillPowers = pokes.values().stream().flatMap(p -> p.getSkills().stream().map(s -> s.getDiceRank())).distinct().collect(Collectors.toList());
//        skillPowers.forEach(System.out::println);

//        Set<String> capabilities = pokes.values().stream().flatMap(p -> p.getCapabilities().stream())
//                .map(c -> c.getCapabilityName()).collect(Collectors.toCollection(TreeSet::new));
//        capabilities.forEach(System.out::println);
//        System.out.println("---------------------------");
//        capabilities = pokes.values().stream().flatMap(p -> p.getCapabilities().stream())
//                .map(c -> {
//                    return c.getCapabilityName() + " " + c.getValue();
//                }).collect(Collectors.toCollection(TreeSet::new));
//        capabilities.forEach(System.out::println);
    }
}
