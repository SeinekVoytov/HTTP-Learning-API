package com.example.httplearningapi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class JsonSerializationUtil {

    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
    }

    private JsonSerializationUtil() {}

    public static <T> String serializeObjectToJsonString(T obj) throws JsonProcessingException {
        return MAPPER.writeValueAsString(obj);
    }

    public static <T> void serializeObjectToJsonStream(T obj, Writer writer) throws IOException {
        String serializationResult = serializeObjectToJsonString(obj);
        writer.write(serializationResult);
    }

    public static <T> T deserializeObjectFromJson(Reader reader, Class<T> clazz) throws IOException {
        return MAPPER.readValue(reader, clazz);
    }
}
