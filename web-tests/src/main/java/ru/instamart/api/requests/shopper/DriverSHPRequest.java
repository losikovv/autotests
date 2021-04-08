package ru.instamart.api.requests.shopper;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ShopperApiEndpoints;

import static ru.instamart.api.requests.ShopperRequestBase.givenWithAuth;

public final class DriverSHPRequest {

    public static class Shipments {
        /**
         * Получаем список заказов для водителя
         */
        @Step("{method} /" + ShopperApiEndpoints.Driver.SHIPMENTS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperApiEndpoints.Driver.SHIPMENTS);
        }
    }
}
