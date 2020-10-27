package ru.instamart.tests.api.v2.endpoints;

import instamart.api.checkpoints.ApiV2Checkpoints;
import instamart.api.common.RestBase;
import instamart.api.requests.ApiV2Requests;
import instamart.api.responses.v2.ReferralProgramResponse;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class Promotions extends RestBase {

    @Test(  description = "Получаем инфу о реферальной программе",
            groups = {"rest-smoke","rest-v2-smoke"})
    public void getReferralProgram() {
        response = ApiV2Requests.getReferralProgram();
        ApiV2Checkpoints.assertStatusCode200(response);
        assertNotNull(response.as(ReferralProgramResponse.class).getReferral_program(),
                "Не вернулась инфа о реферальной программе");
    }
}
