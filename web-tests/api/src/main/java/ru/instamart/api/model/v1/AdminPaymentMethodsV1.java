package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper = false)
public class AdminPaymentMethodsV1 extends BaseObject {

    @JsonSchema(required = true)
    private int id;

    @JsonSchema(required = true)
    private String type;

    @JsonSchema(required = true)
    private String name;

    @Null
    private String description;

    @JsonSchema(required = true)
    private boolean active;

    @JsonSchema(required = true)
    private String environment;

    @Null
    @JsonProperty("display_on")
    private String displayOn;

    @JsonSchema(required = true)
    private int position;

    @JsonProperty("created_at")
    private String createdAt;

    @Null
    @JsonProperty("updated_at")
    private String updatedAt;

    @Null
    @JsonProperty("deleted_at")
    private Object deletedAt;
}