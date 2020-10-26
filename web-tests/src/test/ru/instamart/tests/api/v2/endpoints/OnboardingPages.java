package ru.instamart.tests.api.v2.endpoints;

import instamart.api.checkpoints.ApiV2Checkpoints;
import instamart.api.common.RestBase;
import instamart.api.requests.ApiV2Requests;
import instamart.api.responses.v2.OnboardingPagesResponse;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class OnboardingPages extends RestBase {

    @Test(  description = "Получаем экраны онбординга",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 15)
    public void getOnboardingPages() {
        response = ApiV2Requests.getOnboardingPages();
        ApiV2Checkpoints.assertStatusCode200(response);
        assertNotNull(response.as(OnboardingPagesResponse.class).getOnboarding_pages(),
                "Не вернулись экраны онборлдинга");
    }
}
