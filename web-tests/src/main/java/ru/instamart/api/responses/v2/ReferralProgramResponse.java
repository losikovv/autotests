package ru.instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.responses.BaseResponseObject;
import ru.instamart.api.objects.v2.ReferralProgram;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReferralProgramResponse extends BaseResponseObject {
    @JsonProperty(value = "referral_program")
    private ReferralProgram referralProgram;
}
