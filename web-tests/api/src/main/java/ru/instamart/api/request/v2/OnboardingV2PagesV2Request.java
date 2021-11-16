package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public class OnboardingV2PagesV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.Onboarding.ONBOARDING_V2_PAGES)
    public static Response GET() {
        return givenWithSpec()
                .header("Client-Id", "InstamartApp")
                .header("Client-Ver", "5.0")
                .get(ApiV2EndPoints.Onboarding.ONBOARDING_V2_PAGES);
    }
}
