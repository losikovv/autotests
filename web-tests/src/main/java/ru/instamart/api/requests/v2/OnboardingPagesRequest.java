package instamart.api.requests.v2;

import instamart.api.endpoints.ApiV2EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class OnboardingPagesRequest {

    /**
     * Получаем экраны онбординга
     */
    @Step("{method} /" + ApiV2EndPoints.ONBOARDING_PAGES)
    public static Response GET() {
        return givenCatch().get(ApiV2EndPoints.ONBOARDING_PAGES);
    }
}
