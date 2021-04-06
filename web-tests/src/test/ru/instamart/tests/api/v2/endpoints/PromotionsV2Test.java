package ru.instamart.tests.api.v2.endpoints;

import instamart.api.common.RestBase;
import instamart.api.requests.v2.PromotionsRequest;
import instamart.api.responses.v2.ReferralProgramResponse;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;
import static org.testng.Assert.assertNotNull;

public class PromotionsV2Test extends RestBase {

    @CaseId(17)
    @Test(  description = "Получаем инфу о реферальной программе",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getReferralProgram() {
        response = PromotionsRequest.ReferralProgram.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(ReferralProgramResponse.class).getReferralProgram(),
                "Не вернулась инфа о реферальной программе");
    }
}
