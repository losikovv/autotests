package instamart.api.action;

import instamart.api.endpoints.ApiV2EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class OnboardingPages {

    /**
     * Получаем экраны онбординга
     */
    @Step("{method} /" + ApiV2EndPoints.ONBOARDING_PAGES)
    public static Response GET() {
        return givenCatch().get(ApiV2EndPoints.ONBOARDING_PAGES);
    }
}
