package ru.instamart.api.requests.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV2EndPoints;
import ru.instamart.api.requests.ApiV2RequestBase;

public final class OnboardingPagesV2Request extends ApiV2RequestBase {

    /**
     * Получаем экраны онбординга
     */
    @Step("{method} /" + ApiV2EndPoints.ONBOARDING_PAGES)
    public static Response GET() {
        return givenWithSpec().get(ApiV2EndPoints.ONBOARDING_PAGES);
    }
}
