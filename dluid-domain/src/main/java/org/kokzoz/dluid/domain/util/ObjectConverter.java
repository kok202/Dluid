package org.kokzoz.dluid.domain.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectConverter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Failed to write object as string ";
    }

    public static <T> T convert(String json,  Class<T> clazz){
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
