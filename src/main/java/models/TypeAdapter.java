package models;

import com.google.gson.*;
import lombok.extern.java.Log;

@Log
public class TypeAdapter implements JsonSerializer<Type>, JsonDeserializer<Type> {
    @Override
    public Type deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return Type.valueOf(json.getAsString().toUpperCase());
        }
        catch (IllegalArgumentException e)
        {
            //System.err.println(json.getAsString() + " not a valid Type. Assigned to Typeless.");
            return Type.TYPELESS;
        }
        }

    @Override
    public JsonElement serialize(Type src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.name().toLowerCase());
    }
}
