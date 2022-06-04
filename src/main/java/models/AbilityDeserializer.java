package models;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.*;

public class AbilityDeserializer implements JsonDeserializer<Ability> {
    private static final String CONNECTION_HEADER = "Connection - ";
    @Override
    public Ability deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject pokeJson = json.getAsJsonObject();

        String freq = pokeJson.get("freq").getAsString();
        JsonElement trig = pokeJson.get("trigger");
        String trigger = Objects.isNull(trig) ? null : trig.getAsString();
        JsonElement trgt = pokeJson.get("target");
        String target = Objects.isNull(trgt) ? null : trgt.getAsString();
        String effect = pokeJson.get("effect").getAsString();

        Move connection = null;
        if(effect.startsWith(CONNECTION_HEADER)) {
            String move = effect.substring(CONNECTION_HEADER.length(), effect.indexOf(". "));
            connection = Move.allMoves.get(move);
        }

        return new Ability(freq, trigger, target, effect, connection);
    }
}

