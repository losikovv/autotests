
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class AdminPromotionV1 extends BaseObject {

    @Null
    @JsonSchema(required = true)
    private String description;

    @JsonSchema(required = true)
    @JsonProperty("expires_at")
    private String expiresAt;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    @JsonProperty("service_comment")
    private String serviceComment;

    @JsonSchema(required = true)
    @JsonProperty("starts_at")
    private String startsAt;
}
