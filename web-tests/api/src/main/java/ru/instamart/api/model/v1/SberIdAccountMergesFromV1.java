package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper = false)
public class SberIdAccountMergesFromV1 extends BaseObject {

    @JsonSchema(required = true)
    private boolean processed;

    @JsonProperty("rollback_author_name")
    private String rollbackAuthorName;

    @Null
    @JsonProperty("from_user_email")
    private String fromUserEmail;

    @Null
    @JsonProperty("rollback_author_id")
    private int rollbackAuthorId;

    @JsonSchema(required = true)
    @JsonProperty("created_at")
    private String createdAt;

    @JsonSchema(required = true)
    private boolean rolledback;

    @JsonSchema(required = true)
    private int id;

    @Null
    @JsonSchema(required = true)
    private String state;
}