package Model;

import com.google.gson.*;
import java.lang.reflect.Type;

public class ReminderAdapter implements JsonSerializer<Reminder>, JsonDeserializer<Reminder> {
        @Override
        public JsonElement serialize(Reminder src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();
            result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
            result.add("properties", context.serialize(src, src.getClass()));

            return result;
        }

        @Override
        public Reminder deserialize(JsonElement json, Type typeOfSrc, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String type = jsonObject.get("type").getAsString();
            JsonElement element = jsonObject.get("properties");

            try {
                switch (type){
                    case "Event": return context.deserialize(element, Event.class);
                    case "OcurrencesEvent": return context.deserialize(element, OcurrencesEvent.class);
                    case "InfiniteEvent":  return context.deserialize(element, InfiniteEvent.class);
                    case "ByDateEvent":  return context.deserialize(element, ByDateEvent.class);
                    case "Task": return context.deserialize(element, Task.class);
                    default: throw new RuntimeException("tipo inválido");
                }
            } catch (RuntimeException cnfe) {
                throw new JsonParseException("Unknown element type: " + type, cnfe);
            }
        }
}

