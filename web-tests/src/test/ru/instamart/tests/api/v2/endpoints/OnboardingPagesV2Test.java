package ru.instamart.tests.api.v2.endpoints;

import instamart.api.common.RestBase;
import instamart.api.requests.v2.OnboardingPagesRequest;
import instamart.api.responses.v2.OnboardingPagesResponse;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;
import static org.testng.Assert.assertNotNull;

public class OnboardingPagesV2Test extends RestBase {

    @CaseId(15)
    @Test(  description = "Получаем экраны онбординга",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getOnboardingPages() {
        response = OnboardingPagesRequest.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(OnboardingPagesResponse.class).getOnboardingPages(),
                "Не вернулись экраны онборлдинга");
    }
}
