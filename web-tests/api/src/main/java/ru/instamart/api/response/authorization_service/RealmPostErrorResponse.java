package ru.instamart.api.response.authorization_service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.response.BaseResponseObject;

import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper = false)
public class RealmPostErrorResponse extends BaseResponseObject {

    private Error error;

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class Error extends BaseObject {
        @JsonSchema(required = true)
        private String detail;
        @JsonProperty("invalid-params")
        private ArrayList<InvalidParam> invalidParams;
        @JsonSchema(required = true)
        private int status;
        @JsonSchema(required = true)
        private String title;
        @JsonSchema(required = true)
        private String type;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class InvalidParam extends BaseObject {
        @JsonSchema(required = true)
        private String name;
        @JsonSchema(required = true)
        private String reason;
    }

}
