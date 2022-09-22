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
public class PolicyModel extends BaseObject {

    @JsonProperty("service_specs")
    @Singular private List<ServiceSpec> serviceSpecs;
    @JsonProperty("roles")
    @Singular private List<Role> roles;

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Builder
    public static class Permission extends BaseObject {
        private String name;
        private String description;
        @JsonProperty("access")
        @Singular private List<String> accesses;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Builder
    public static class RolePermission extends BaseObject {
        private String permission;
        private String condition;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Builder
    public static class Role extends BaseObject {
        private String name;
        @JsonProperty("permissions")
        @Singular private List<RolePermission> permissions;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Builder
    public static class ServiceSpec extends BaseObject {
        @JsonProperty("service_id")
        private String serviceId;
        @Singular private List<Permission> permissions;
    }

}
