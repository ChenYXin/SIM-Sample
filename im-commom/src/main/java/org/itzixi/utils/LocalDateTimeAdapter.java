// package org.itzixi.utils;
//
// import com.google.gson.*;
//
// import java.lang.reflect.Type;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
//
// public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
//
//     private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
//
//     @Override
//     public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
//         return new JsonPrimitive(formatter.format(src));
//     }
//
//     @Override
//     public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//         return LocalDateTime.parse(json.getAsString(), formatter);
//     }
// }
//
