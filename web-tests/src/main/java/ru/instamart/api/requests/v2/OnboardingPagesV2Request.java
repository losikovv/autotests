package ru.instamart.api.requests.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV2EndPoints;

import static ru.instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class OnboardingPagesV2Request {

    /**
     * Получаем экраны онбординга
     */
    @Step("{method} /" + ApiV2EndPoints.ONBOARDING_PAGES)
    public static Response GET() {
        return givenCatch().get(ApiV2EndPoints.ONBOARDING_PAGES);
    }
}
