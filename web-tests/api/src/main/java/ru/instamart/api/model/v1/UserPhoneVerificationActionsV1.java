package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserPhoneVerificationActionsV1 extends BaseObject {

    @Null
    @JsonProperty("cancelled_at")
    private String cancelledAt;

    @Null
    @JsonProperty("cancellation_source")
    private String cancellationSource;

    @Null
    @JsonProperty("phone_token_number")
    private String phoneTokenNumber;

    @Null
    @JsonProperty("verification_source")
    private String verificationSource;

    @JsonSchema(required = true)
    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonSchema(required = true)
    @JsonProperty("created_at")
    private String createdAt;

    @JsonSchema(required = true)
    private int id;

    @JsonSchema(required = true)
    private String state;
}
