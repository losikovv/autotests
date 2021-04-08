package ru.instamart.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public enum MapperService {

    INSTANCE;

    private final ObjectMapper objectMapper;

    MapperService() {
        this.objectMapper = new ObjectMapper();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> objectToMap(final Object object) {
        return objectMapper.convertValue(object, Map.class);
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
