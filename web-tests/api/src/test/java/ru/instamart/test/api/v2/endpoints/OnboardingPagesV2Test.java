package ru.instamart.test.api.v2.endpoints;

import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v2.OnboardingPagesV2Request;
import ru.instamart.api.response.v2.OnboardingPagesV2Response;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

public class OnboardingPagesV2Test extends RestBase {

    @CaseId(15)
    @Test(  description = "Получаем экраны онбординга",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getOnboardingPages() {
        response = OnboardingPagesV2Request.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(OnboardingPagesV2Response.class).getOnboardingPages(),
                "Не вернулись экраны онборлдинга");
    }
}
