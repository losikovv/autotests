package ru.instamart.tests.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.requests.v2.PromotionsV2Request;
import ru.instamart.api.responses.v2.ReferralProgramV2Response;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Промо-акции")
public class PromotionsV2Test extends RestBase {

    @CaseId(17)
    @Test(  description = "Получаем инфу о реферальной программе",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getReferralProgram() {
        response = PromotionsV2Request.ReferralProgram.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(ReferralProgramV2Response.class).getReferralProgram(),
                "Не вернулась инфа о реферальной программе");
    }
}
