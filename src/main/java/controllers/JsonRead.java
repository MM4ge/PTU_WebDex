package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import lombok.extern.java.Log;
import models.*;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Log
public class JsonRead {
    private static final String POKEDEX_FILEPATH = "src/main/resources/pokedex.json";
    private static final String MOVES_FILEPATH = "src/main/resources/moves.json";
    private static final String TYPES_FILEPATH = "src/main/resources/types.json";
    private static final String ABILITIES_FILEPATH = "src/main/resources/abilities.json";

    private static Gson gson = new GsonBuilder().setLenient().registerTypeAdapter(TypeToken.get(models.Type.class).getType(), new TypeAdapter()).create();

    public static Map<String, PokemonSpecies> deserializePokemonSpecies() {
        return gson.fromJson(readFromFile(POKEDEX_FILEPATH), new TypeToken<Map<String, PokemonSpecies>>() {}.getType());
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(PokemonSpecies.class, new PokemonSpeciesDeserializer())
//                .create();
//        Type moveMapType = new TypeToken<Map<String, PokemonSpecies>>() {
//        }.getType();
//        try (JsonReader reader = new JsonReader(new FileReader(POKEDEX_FILEPATH))){
//            Map<String, PokemonSpecies> pokeMap = gson.fromJson(reader, moveMapType);
////            for (Map.Entry<?, ?> entry : pokeMap.entrySet()) {
////                log.info(entry.getKey() + "=" + entry.getValue());
////            }
//            return pokeMap;
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        throw new RuntimeException("Json hit an error; program cannot continue.");
    }

    public static Map<models.Type, Map<models.Type, Double>> deserializeTypes()
    {
        return gson.fromJson(readFromFile(TYPES_FILEPATH), new TypeToken<Map<models.Type, Map<models.Type, Double>>>() {}.getType());
    }

    public static String readFromFile(String path)
    {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Map<String, Move> deserializeMoves()
    {
        return gson.fromJson(readFromFile(MOVES_FILEPATH), new TypeToken<Map<String, Move>>() {}.getType());
    }

    public static Map<String, Ability> deserializeAbilities()
    {
        return gson.fromJson(readFromFile(ABILITIES_FILEPATH), new TypeToken<Map<String, Ability>>() {}.getType());
    }
}
