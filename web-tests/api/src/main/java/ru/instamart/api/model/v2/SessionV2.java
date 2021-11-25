package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SessionV2 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonSchema(required = true)
    @JsonProperty(value = "expires_at")
    private String expiresAt;

    @JsonSchema(required = true)
    @JsonProperty(value = "is_valid")
    private Boolean isValid;
}