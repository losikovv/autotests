package ru.instamart.tests.api.v2.endpoints;

import instamart.api.requests.v2.PromotionsRequest;
import instamart.api.common.RestBase;
import instamart.api.responses.v2.ReferralProgramResponse;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.assertStatusCode200;
import static org.testng.Assert.assertNotNull;

public class PromotionsTest extends RestBase {

    @CaseId(17)
    @Test(  description = "Получаем инфу о реферальной программе",
            groups = {"api-instamart-smoke"})
    public void getReferralProgram() {
        response = PromotionsRequest.ReferralProgram.GET();
        assertStatusCode200(response);
        assertNotNull(response.as(ReferralProgramResponse.class).getReferral_program(),
                "Не вернулась инфа о реферальной программе");
    }
}
