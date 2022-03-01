package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public final class ShippingMethodsV2Request extends ApiV2RequestBase {

    /**
     * Получаем доступные способы доставки
     */
    @Step("{method} /" + ApiV2EndPoints.SHIPPING_METHODS)
    public static Response GET(int sid) {
        return givenWithSpec()
                .queryParam("sid", sid)
                .get(ApiV2EndPoints.SHIPPING_METHODS, sid);
    }
}
