package instamart.api.objects.responses;

import instamart.api.objects.ReferralProgram;

public class ReferralProgramResponse extends BaseResponseObject {

    private ReferralProgram referral_program;

    public ReferralProgram getReferral_program() {
        return referral_program;
    }

    public void setReferral_program(ReferralProgram referral_program) {
        this.referral_program = referral_program;
    }

}
