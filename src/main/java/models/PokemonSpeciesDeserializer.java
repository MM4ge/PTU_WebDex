package models;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public class PokemonSpeciesDeserializer implements JsonDeserializer<PokemonSpecies> {
    @Override
    public PokemonSpecies deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject pokeJson = json.getAsJsonObject();

        String speciesName = pokeJson.get("Species").getAsString();
        String form = pokeJson.get("Form").getAsString();

        int[] stats = new int[6];
        JsonArray statAry = pokeJson.get("BaseStats").getAsJsonArray();
        for(int i = 0; i < 6; i++) {
            stats[i] = statAry.get(i).getAsInt();
        }

        Map<Ability, Ability.AbilityType> baseAbilities = new LinkedHashMap<>();
        JsonArray abilityAry = pokeJson.get("Abilities").getAsJsonArray();
        abilityAry.forEach(je -> {
            JsonObject obj = je.getAsJsonObject();
            baseAbilities.put(Ability.allAbilities.get(obj.get("Name").getAsString()),
                    Ability.AbilityType.getAbilityType(obj.get("Type").getAsString()));
        });

        Map<Move, Integer> levelMoves = new LinkedHashMap<>();
        JsonArray levelAry = pokeJson.get("LevelUpMoves").getAsJsonArray();
        levelAry.forEach(je -> {
            JsonObject obj = je.getAsJsonObject();
            levelMoves.put(Move.allMoves.get(obj.get("Name").getAsString()), obj.get("LevelLearned").getAsInt());
        });

        Map<Move, Boolean> tutorMoves = new LinkedHashMap<>();
        JsonArray tutorAry = pokeJson.get("TutorMoves").getAsJsonArray();
        tutorAry.forEach(je -> {
            JsonObject obj = je.getAsJsonObject();
            tutorMoves.put(Move.allMoves.get(obj.get("Name").getAsString()), obj.get("Natural").getAsBoolean());
        });

        Map<Move, Boolean> eggMoves = new LinkedHashMap<>();
        JsonArray eggAry = pokeJson.get("EggMoves").getAsJsonArray();
        eggAry.forEach(je -> {
            JsonObject obj = je.getAsJsonObject();
            eggMoves.put(Move.allMoves.get(obj.get("Name").getAsString()), obj.get("Natural").getAsBoolean());
        });

        return new PokemonSpecies(speciesName, form, stats, baseAbilities, levelMoves, tutorMoves, eggMoves);
    }
}

