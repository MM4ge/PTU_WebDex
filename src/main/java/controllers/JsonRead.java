package controllers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import lombok.extern.java.Log;
import models.Ability;
import models.Move;
import models.PokemonSpecies;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Map;

@Log
public class JsonRead {
    private static final String POKEDEX_FILEPATH = "src/main/resources/pokedex.json";
    private static final String MOVES_FILEPATH = "src/main/resources/moves.json";
    private static final String ABILITIES_FILEPATH = "src/main/resources/abilities.json";

    public static Map<String, PokemonSpecies> deserializePokemonSpecies() {
        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            JsonReader reader = new JsonReader(new FileReader(POKEDEX_FILEPATH));
            JsonElement e = JsonParser.parseReader(reader);

            // convert JSON file to map
            Map<?, ?> map = gson.fromJson(reader, Map.class);

            // print map entries
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }
            e.getAsJsonObject().get("");

            // close reader
            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static Map<String, Move> deserializeMoves()
    {
        Gson gson = new Gson();
        Type moveMapType = new TypeToken<Map<String, Move>>() {
        }.getType();
        try (JsonReader reader = new JsonReader(new FileReader(MOVES_FILEPATH))){
            Map<String, Move> movesMap = gson.fromJson(reader, moveMapType);
            for (Map.Entry<?, ?> entry : movesMap.entrySet()) {
                log.info(entry.getKey() + "=" + entry.getValue());
            }
            log.info("" + movesMap.getClass());
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
        Gson gson = new Gson();
        Type abilityMapType = new TypeToken<Map<String, Ability>>() {
        }.getType();
        try (JsonReader reader = new JsonReader(new FileReader(ABILITIES_FILEPATH))){
            Map<String, Ability> abilityMap = gson.fromJson(reader, abilityMapType);
            for (Map.Entry<?, ?> entry : abilityMap.entrySet()) {
                log.info(entry.getKey() + "=" + entry.getValue());
            };
            return abilityMap;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        throw new RuntimeException("Json hit an error; program cannot continue.");
    }
}
