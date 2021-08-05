package ru.instamart.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public enum Mapper {

    INSTANCE;

    @Getter
    private final ObjectMapper objectMapper;

    Mapper() {
        this.objectMapper = new ObjectMapper();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> objectToMap(final Object object) {
        return objectMapper.convertValue(object, Map.class);
    }

    public String objectToString(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("FATAL: write object={} as string", object);
        }
        return "empty";
    }

    public <T> T jsonToObject(final String json, final Class<T> response) {
        if (json == null || json.isEmpty()) {
            log.warn("Json is empty or null='{}'", json);
            return null;
        }
        try {
            return objectMapper.readValue(json, response);
        } catch (JsonProcessingException e) {
            log.error("FATAL: read response={} or convert to object={}", json, response.getSimpleName(), e);
        }
        return null;
    }
}
