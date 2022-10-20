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
public class RealmResponse extends BaseResponseObject {

    @JsonSchema(required = true)
    private ArrayList<Data> data;

    @lombok.Data
    @EqualsAndHashCode(callSuper=false)
    public static class Data extends BaseObject{
        private String description;
        @JsonSchema(required = true)
        private String name;
        @JsonSchema(required = true)
        @JsonProperty("repository_url")
        private String repositoryUrl;
        private ArrayList<Service> services;
        @JsonProperty("user_type")
        private String userType;
    }

    @lombok.Data
    @EqualsAndHashCode(callSuper=false)
    private static class Service extends BaseObject{
        private String name;
        private String description;
        @JsonProperty("remote_roles")
        private boolean remoteRoles;
        @JsonProperty("repository_url")
        private String repositoryUrl;
    }

}
