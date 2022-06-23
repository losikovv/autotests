package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserLocksV1 extends BaseObject {

    @Null
    @JsonProperty("lock_reason")
    private String lockReason;

    @JsonSchema(required = true)
    @JsonProperty("created_at")
    private String createdAt;

    @Null
    private String action;

    @JsonSchema(required = true)
    @JsonProperty("author_email")
    private String authorEmail;

    @JsonSchema(required = true)
    private int id;
}