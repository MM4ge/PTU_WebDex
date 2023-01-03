package org.allenfulmer.ptuviewer.jsonLoading;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.allenfulmer.ptuviewer.jsonLoading.pojo.ability.AbilityPojo;
import org.allenfulmer.ptuviewer.jsonLoading.pojo.move.MovePojo;
import org.allenfulmer.ptuviewer.jsonLoading.pojo.pokemon.PokemonSpeciesPojo;
import org.allenfulmer.ptuviewer.models.Capability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class JsonToPojoLoader {
    private static final String POKEDEX_FILEPATH = "src/main/resources/static/json/pokedex.json";
    private static final String MOVES_FILEPATH = "src/main/resources/static/json/moves.json";
    private static final String ABILITIES_FILEPATH = "src/main/resources/static/json/abilities.json";
    private static final String CAPABILITIES_FILEPATH = "src/main/resources/static/json/capabilities.json";

    private JsonToPojoLoader() {
    }

    private static Gson gson = new GsonBuilder().setLenient().create();

    public static Map<String, Capability> parseCapabilities() {
        Map<String, String> jsonCapabilities = gson.fromJson(readFromFile(CAPABILITIES_FILEPATH), new TypeToken<Map<String, String>>() {
        }.getType());
        return jsonCapabilities.entrySet().stream().map(e -> new Capability(e.getKey(), e.getValue()))
                .collect(Collectors.toMap(Capability::getName, c -> c));
    }

    public static Map<String, PokemonSpeciesPojo> parsePojoPokemon() {
        return gson.fromJson(readFromFile(POKEDEX_FILEPATH), new TypeToken<Map<String, PokemonSpeciesPojo>>() {
        }.getType());
    }

    public static Map<String, MovePojo> parsePojoMoves() {
        return gson.fromJson(readFromFile(MOVES_FILEPATH), new TypeToken<Map<String, MovePojo>>() {
        }.getType());
    }

    public static Map<String, AbilityPojo> parsePojoAbilities() {
        return gson.fromJson(readFromFile(ABILITIES_FILEPATH), new TypeToken<Map<String, AbilityPojo>>() {
        }.getType());
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
        List<String> skills = pokes.values().stream().flatMap(p -> p.getSkills().stream()).map(s -> s.getSkillName()).distinct().collect(Collectors.toList());
        skills.forEach(System.out::println);
        List<String> skillPowers = pokes.values().stream().flatMap(p -> p.getSkills().stream().map(s -> s.getDiceRank())).distinct().collect(Collectors.toList());
        skillPowers.forEach(System.out::println);

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
