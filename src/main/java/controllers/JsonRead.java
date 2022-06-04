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
import java.lang.reflect.Type;
import java.util.Map;

@Log
public class JsonRead {
    private static final String POKEDEX_FILEPATH = "src/main/resources/pokedex.json";
    private static final String MOVES_FILEPATH = "src/main/resources/moves.json";
    private static final String ABILITIES_FILEPATH = "src/main/resources/abilities.json";

    public static Map<String, PokemonSpecies> deserializePokemonSpecies() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(PokemonSpecies.class, new PokemonSpeciesDeserializer())
                .create();
        Type moveMapType = new TypeToken<Map<String, PokemonSpecies>>() {
        }.getType();
        try (JsonReader reader = new JsonReader(new FileReader(POKEDEX_FILEPATH))){
            Map<String, PokemonSpecies> pokeMap = gson.fromJson(reader, moveMapType);
//            for (Map.Entry<?, ?> entry : pokeMap.entrySet()) {
//                log.info(entry.getKey() + "=" + entry.getValue());
//            }
            return pokeMap;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        throw new RuntimeException("Json hit an error; program cannot continue.");
    }


    public static Map<String, Move> deserializeMoves()
    {
        Gson gson = new Gson();
        Type moveMapType = new TypeToken<Map<String, Move>>() {
        }.getType();
        try (JsonReader reader = new JsonReader(new FileReader(MOVES_FILEPATH))){
            Map<String, Move> movesMap = gson.fromJson(reader, moveMapType);
//            for (Map.Entry<?, ?> entry : movesMap.entrySet()) {
//                log.info(entry.getKey() + "=" + entry.getValue());
//            }
            return movesMap;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        throw new RuntimeException("Json hit an error; program cannot continue.");
    }

    public static Map<String, Ability> deserializeAbilities()
    {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Ability.class, new AbilityDeserializer())
                .create();
        Type abilityMapType = new TypeToken<Map<String, Ability>>() {
        }.getType();
        try (JsonReader reader = new JsonReader(new FileReader(ABILITIES_FILEPATH))){
            Map<String, Ability> abilityMap = gson.fromJson(reader, abilityMapType);
            //for (Map.Entry<?, ?> entry : abilityMap.entrySet()) {
            //    log.info(entry.getKey() + "=" + entry.getValue());
            //}
            return abilityMap;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        throw new RuntimeException("Json hit an error; program cannot continue.");
    }
}
