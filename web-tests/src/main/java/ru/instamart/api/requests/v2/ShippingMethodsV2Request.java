package ru.instamart.api.requests.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV2EndPoints;

import static ru.instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class ShippingMethodsV2Request {

    /**
     * Получаем доступные способы доставки
     */
    @Step("{method} /" + ApiV2EndPoints.SHIPPING_METHODS)
    public static Response GET(int sid) {
        return givenCatch().get(ApiV2EndPoints.SHIPPING_METHODS, sid);
    }
}
