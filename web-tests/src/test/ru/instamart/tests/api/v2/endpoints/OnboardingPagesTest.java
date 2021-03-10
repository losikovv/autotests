package ru.instamart.tests.api.v2.endpoints;

import instamart.api.requests.v2.OnboardingPagesRequest;
import instamart.api.common.RestBase;
import instamart.api.responses.v2.OnboardingPagesResponse;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.assertStatusCode200;
import static org.testng.Assert.assertNotNull;

public class OnboardingPagesTest extends RestBase {

    @CaseId(15)
    @Test(  description = "Получаем экраны онбординга",
            groups = {"api-instamart-smoke"})
    public void getOnboardingPages() {
        response = OnboardingPagesRequest.GET();
        assertStatusCode200(response);
        assertNotNull(response.as(OnboardingPagesResponse.class).getOnboarding_pages(),
                "Не вернулись экраны онборлдинга");
    }
}
