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
public class PermissionsResponse extends BaseResponseObject {
    @JsonSchema(required = true)
    private ArrayList<Data> data;

    @lombok.Data
    @EqualsAndHashCode(callSuper = false)
    public static class Data extends BaseObject {
        @JsonSchema(required = true)
        private ArrayList<String> access;
        @JsonSchema(required = true)
        private String permission;
        @JsonSchema(required = true)
        private String resource;
        @JsonSchema(required = true)
        @JsonProperty("service_id")
        private String serviceId;
    }

}
