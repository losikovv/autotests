package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public final class OnboardingPagesV2Request extends ApiV2RequestBase {

    /**
     * Получаем экраны онбординга
     */
    @Step("{method} /" + ApiV2EndPoints.ONBOARDING_PAGES)
    public static Response GET() {
        return givenWithSpec().get(ApiV2EndPoints.ONBOARDING_PAGES);
    }
}
