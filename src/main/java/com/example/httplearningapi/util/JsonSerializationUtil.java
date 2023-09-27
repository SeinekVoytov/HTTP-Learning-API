package com.example.httplearningapi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;

public class JsonSerializationUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonSerializationUtil() {}

    public static <T> String serializeObjectToJsonString(T obj) throws JsonProcessingException {
        return MAPPER.writeValueAsString(obj);
    }

    public static <T> void serializeObjectToJsonStream(T obj, OutputStream os) throws IOException {
        String serializationResult = serializeObjectToJsonString(obj);
        os.write(serializationResult.getBytes());
    }


}
