package com.sample.swingapp.yahoofinacne.json;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtils {
public static <T> T parseJson(String json, Class<T> resultType) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
        return objectMapper.readValue(json, resultType);
    } catch (Exception e) {
        System.err.println(e.getMessage());
    }
    return null;
}
}