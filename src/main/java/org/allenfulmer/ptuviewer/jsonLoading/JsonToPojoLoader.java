package org.allenfulmer.ptuviewer.jsonLoading;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.allenfulmer.ptuviewer.jsonLoading.pojo.ability.AbilityPojo;
import org.allenfulmer.ptuviewer.jsonLoading.pojo.move.MovePojo;
import org.allenfulmer.ptuviewer.jsonLoading.pojo.pokemon.PokemonSpeciesPojo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;


public class JsonToPojoLoader {
    private static final String POKEDEX_FILEPATH = "src/main/resources/static/json/pokedex.json";
    private static final String MOVES_FILEPATH = "src/main/resources/static/json/moves.json";
    private static final String ABILITIES_FILEPATH = "src/main/resources/static/json/abilities.json";

    private JsonToPojoLoader(){}

    private static Gson gson = new GsonBuilder().setLenient().create();

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
}
