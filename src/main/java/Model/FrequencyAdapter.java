package Model;

import com.google.gson.*;
import java.lang.reflect.Type;

public class FrequencyAdapter implements JsonSerializer<FrequencyStrategy>, JsonDeserializer<FrequencyStrategy> {
    @Override
    public JsonElement serialize(FrequencyStrategy src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src, src.getClass()));

        return result;
    }

    @Override
    public FrequencyStrategy deserialize(JsonElement json, Type typeOfSrc, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        try {
            switch (type){
                case "DailyStrategy": return context.deserialize(element, DailyStrategy.class);
                case "WeeklyStrategy":  return context.deserialize(element, WeeklyStrategy.class);
                case "MonthlyStrategy": return context.deserialize(element, MonthlyStrategy.class);
                case "YearlyStrategy": return context.deserialize(element, YearlyStrategy.class);
                default: throw new RuntimeException("tipo inválido");
            }
        } catch (RuntimeException cnfe) {
            throw new JsonParseException("Unknown element type: " + type, cnfe);
        }
    }
}
