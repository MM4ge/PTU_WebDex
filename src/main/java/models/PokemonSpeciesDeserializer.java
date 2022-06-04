package models;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class PokemonSpeciesDeserializer implements JsonDeserializer<PokemonSpecies> {
    @Override
    public PokemonSpecies deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject pokeJson = json.getAsJsonObject();

        String speciesName = pokeJson.get("Species").getAsString();
        String form = pokeJson.get("Form").getAsString();

        JsonObject statObj = pokeJson.get("BaseStats").getAsJsonObject();
        int[] stats = statObj.entrySet().stream().map(e -> e.getValue().getAsInt()).mapToInt(Integer::intValue).toArray();

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

        Set<Move> eggMoves = new LinkedHashSet<>();
        JsonArray eggAry = pokeJson.get("EggMoves").getAsJsonArray();
        eggAry.forEach(je -> {
            JsonObject obj = je.getAsJsonObject();
            eggMoves.add(Move.allMoves.get(obj.get("Name").getAsString()));
        });

        /* Abilities after moves- Ability's Connection requires them */
        Map<Ability, Ability.AbilityType> baseAbilities = new LinkedHashMap<>();
        JsonArray abilityAry = pokeJson.get("Abilities").getAsJsonArray();
        abilityAry.forEach(je -> {
            JsonObject obj = je.getAsJsonObject();
            baseAbilities.put(Ability.allAbilities.get(obj.get("Name").getAsString()),
                    Ability.AbilityType.getAbilityType(obj.get("Type").getAsString()));
        });

        return new PokemonSpecies(speciesName, form, stats, baseAbilities, levelMoves, tutorMoves, eggMoves);
    }
}

