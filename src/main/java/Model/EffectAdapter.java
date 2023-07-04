package Model;

import com.google.gson.*;
import java.lang.reflect.Type;

public class EffectAdapter implements JsonSerializer<Effect>, JsonDeserializer<Effect> {
    @Override
    public JsonElement serialize(Effect src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src, src.getClass()));

        return result;
    }

    @Override
    public Effect deserialize(JsonElement json, Type typeOfSrc, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        try {
            switch (type){
                case "Sound": return context.deserialize(element, Sound.class);
                case "EMail":  return context.deserialize(element, EMail.class);
                case "Notification": return context.deserialize(element, Notification.class);
                default: throw new RuntimeException("tipo inválido");
            }
        } catch (RuntimeException cnfe) {
            throw new JsonParseException("Unknown element type: " + type, cnfe);
        }
    }
}
