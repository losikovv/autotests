package ru.instamart.api.requests.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV2EndPoints;
import ru.instamart.api.requests.ApiV2RequestBase;

public final class ShippingMethodsV2Request extends ApiV2RequestBase {

    /**
     * Получаем доступные способы доставки
     */
    @Step("{method} /" + ApiV2EndPoints.SHIPPING_METHODS)
    public static Response GET(int sid) {
        return givenWithSpec().get(ApiV2EndPoints.SHIPPING_METHODS, sid);
    }
}
