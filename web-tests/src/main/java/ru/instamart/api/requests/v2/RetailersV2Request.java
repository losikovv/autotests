package ru.instamart.api.requests.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV2EndPoints;
import ru.instamart.api.requests.ApiV2RequestBase;

public final class RetailersV2Request extends ApiV2RequestBase {

    /**
     * Получаем список всех доступных ритейлеров
     */
    @Step("{method} /" + ApiV2EndPoints.RETAILERS)
    public static Response GET() {
        return givenWithSpec().get(ApiV2EndPoints.RETAILERS);
    }

    @Step("{method} /" + ApiV2EndPoints.Retailers.BY_ID)
    public static Response GET(final int retailerId) {
        return givenWithSpec().get(ApiV2EndPoints.Retailers.BY_ID, retailerId);
    }
}
