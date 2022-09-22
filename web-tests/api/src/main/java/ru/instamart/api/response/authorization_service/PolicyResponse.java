package ru.instamart.api.response.authorization_service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.response.BaseResponseObject;

import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper=false)
public class PolicyResponse extends BaseResponseObject {

    private Data data;

    @lombok.Data
    @EqualsAndHashCode(callSuper=false)
    public static class Data extends BaseObject{
        @JsonSchema(required = true)
        private ArrayList<Role> roles;
        @JsonSchema(required = true)
        @JsonProperty("service_specs")
        private ArrayList<ServiceSpec> serviceSpecs;
    }

    @lombok.Data
    @EqualsAndHashCode(callSuper=false)
    public static class Permission extends BaseObject{
        private String condition;
        private String permission;
        private ArrayList<String> access;
        private String description;
        private String name;
    }

    @lombok.Data
    @EqualsAndHashCode(callSuper=false)
    public static class Role extends BaseObject{
        @JsonSchema(required = true)
        private String name;
        @JsonSchema(required = true)
        private ArrayList<Permission> permissions;
    }

    @lombok.Data
    @EqualsAndHashCode(callSuper=false)
    public static class ServiceSpec extends BaseObject{
        private ArrayList<Permission> permissions;
        @JsonProperty("service_id")
        private String serviceId;
    }

}
