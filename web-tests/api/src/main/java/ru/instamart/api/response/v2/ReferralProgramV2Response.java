package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.ReferralProgramV2;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReferralProgramV2Response extends BaseResponseObject {
    @JsonSchema(required = true)
    @JsonProperty(value = "referral_program")
    private ReferralProgramV2 referralProgram;
}
