package ru.instamart.ab.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Objects;

public interface IRequest {

    Logger log = LoggerFactory.getLogger(IRequest.class);

    default String getQuery() {
        final StringBuilder query = new StringBuilder("?");
        try {
            for (final Field field : this.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                final JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
                if (Objects.nonNull(jsonProperty)) {
                    fieldName = jsonProperty.value();
                }
                final String fieldValue = String.valueOf(field.get(this));
                if (fieldValue.equals("null")) continue;
                query.append(fieldName).append("=").append(fieldValue).append("&");
            }
        } catch (IllegalAccessException e) {
            log.error("Can't obtain query from class={}", this.getClass());
        }
        return query.toString();
    }
}
