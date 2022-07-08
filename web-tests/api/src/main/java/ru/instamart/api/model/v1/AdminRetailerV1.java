package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class AdminRetailerV1 extends BaseObject {

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    @JsonProperty("short_name")
    private String shortName;

    @JsonSchema(required = true)
    private AppearanceV1 appearance;

    @JsonSchema(required = true)
    private String description;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("secondary_color")
    private String secondaryColor;

    @JsonSchema(required = true)
    private String slug;

    @JsonSchema(required = true)
    private String available;

    @JsonSchema(required = true)
    private String key;

    @JsonSchema(required = true)
    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("key_account_manager")
    private AdminUserSuggestV1 keyAccountManager;
}
