package ru.instamart.tests.api.v2.endpoints;

import instamart.api.checkpoints.ApiV2Checkpoints;
import instamart.api.common.RestBase;
import instamart.api.requests.ApiV2Requests;
import instamart.api.responses.v2.ReferralProgramResponse;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class Promotions extends RestBase {

    @CaseId(17)
    @Test(  description = "Получаем инфу о реферальной программе",
            groups = {"rest-smoke","rest-v2-smoke"})
    public void getReferralProgram() {
        response = ApiV2Requests.getReferralProgram();
        ApiV2Checkpoints.assertStatusCode200(response);
        assertNotNull(response.as(ReferralProgramResponse.class).getReferral_program(),
                "Не вернулась инфа о реферальной программе");
    }
}
