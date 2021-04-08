package ru.instamart.api.requests.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV2EndPoints;

import static ru.instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class RetailersV2Request {

    /**
     * Получаем список всех доступных ритейлеров
     */
    @Step("{method} /" + ApiV2EndPoints.RETAILERS)
    public static Response GET() {
        return givenCatch().get(ApiV2EndPoints.RETAILERS);
    }

    @Step("{method} /" + ApiV2EndPoints.Retailers.ID)
    public static Response GET(final int retailerId) {
        return givenCatch().get(ApiV2EndPoints.Retailers.ID, retailerId);
    }
}
