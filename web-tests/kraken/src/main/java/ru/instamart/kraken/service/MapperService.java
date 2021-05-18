package ru.instamart.kraken.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public enum MapperService {

    INSTANCE;

    @Getter
    private final ObjectMapper objectMapper;

    MapperService() {
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
            return "";
        }
    }
}
