package instamart.api.action;

import instamart.api.endpoints.ApiV2EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class ShippingMethods {

    /**
     * Получаем доступные способы доставки
     */
    @Step("{method} /" + ApiV2EndPoints.SHIPPING_METHODS)
    public static Response GET(int sid) {
        return givenCatch().get(ApiV2EndPoints.SHIPPING_METHODS, sid);
    }
}
