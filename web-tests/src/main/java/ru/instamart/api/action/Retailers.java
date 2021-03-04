package instamart.api.action;

import instamart.api.endpoints.ApiV2EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class Retailers {

    /**
     * Получаем список всех доступных ритейлеров
     */
    @Step("{method} /" + ApiV2EndPoints.RETAILERS)
    public static Response GET() {
        return givenCatch().get(ApiV2EndPoints.RETAILERS);
    }
}
