package ru.instamart.tests.api.v2.endpoints;

import instamart.api.v2.ApiV2Requests;
import instamart.api.common.RestBase;
import instamart.api.v2.responses.ReferralProgramResponse;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class Promotions extends RestBase {

    @Test(  description = "Получаем инфу о реферальной программе",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 18)
    public void getReferralProgram() {
        response = ApiV2Requests.getReferralProgram();

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(ReferralProgramResponse.class).getReferral_program(),
                "Не вернулась инфа о реферальной программе");
    }
}
