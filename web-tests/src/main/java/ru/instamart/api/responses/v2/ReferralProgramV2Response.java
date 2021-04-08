package ru.instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.v2.ReferralProgramV2;
import ru.instamart.api.responses.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReferralProgramV2Response extends BaseResponseObject {
    @JsonProperty(value = "referral_program")
    private ReferralProgramV2 referralProgram;
}
