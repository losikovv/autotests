
package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.UserReferralProgramV2;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserReferralProgramV2Response extends BaseResponseObject {

    @JsonSchema(required = true)
    @JsonProperty("short_tutorial")
    private String shortTutorial;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("user_referral_program")
    private UserReferralProgramV2 userReferralProgram;
}
