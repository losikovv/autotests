package ru.instamart.api.model.authorization_service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class RealmModel extends BaseObject {
    private String description;
    private String name;
    @JsonProperty("repository_url")
    private String repositoryUrl;
    @Singular private List<Service> services;
    @JsonProperty("user_type")
    private String userType;

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Builder
    public static class Service extends BaseObject {
        private String description;
        private String name;
        @JsonProperty("remote_roles")
        private boolean remoteRoles;
        @JsonProperty("repository_url")
        private String repositoryUrl;
    }
}
