package ru.instamart.api.requests.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ShopperAppEndpoints;
import ru.instamart.api.requests.ShopperAppRequestBase;

public final class DriverSHPRequest extends ShopperAppRequestBase {

    public static class Shipments {
        /**
         * Получаем список заказов для водителя
         */
        @Step("{method} /" + ShopperAppEndpoints.Driver.SHIPMENTS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperAppEndpoints.Driver.SHIPMENTS);
        }
    }
}
