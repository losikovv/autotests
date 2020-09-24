package ru.instamart.tests.api.v2.endpoints;

import instamart.api.common.Requests;
import instamart.api.common.RestBase;
import instamart.api.objects.responses.OnboardingPagesResponse;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class OnboardingPages extends RestBase {

    @Test(  description = "Получаем экраны онбординга",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 15)
    public void getOnboardingPages() {
        response = Requests.getOnboardingPages();

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(OnboardingPagesResponse.class).getOnboarding_pages(),
                "Не вернулись экраны онборлдинга");
    }
}
