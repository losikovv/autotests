package ru.instamart.tests.api.v2.endpoints;

import instamart.api.common.Requests;
import instamart.api.common.RestBase;
import instamart.api.objects.responses.ReferralProgramResponse;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class Promotions extends RestBase {

    @Test(  description = "Получаем инфу о реферальной программе",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 18)
    public void getReferralProgram() {
        response = Requests.getReferralProgram();

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(ReferralProgramResponse.class).getReferral_program(),
                "Не вернулась инфа о реферальной программе");
    }
}
