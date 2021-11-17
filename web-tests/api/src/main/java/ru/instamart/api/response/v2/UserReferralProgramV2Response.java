
package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.UserReferralProgramV2;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserReferralProgramV2Response extends BaseResponseObject {

    @JsonProperty("short_tutorial")
    private String shortTutorial;
    @JsonProperty("user_referral_program")
    private UserReferralProgramV2 userReferralProgram;
}
