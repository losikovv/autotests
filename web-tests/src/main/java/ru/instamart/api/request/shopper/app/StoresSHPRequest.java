package ru.instamart.api.request.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ShopperAppEndpoints;
import ru.instamart.api.request.ShopperAppRequestBase;

public final class StoresSHPRequest extends ShopperAppRequestBase {

    public static class Offers {
        /**
         * Ищем товары
         */
        @Step("{method} /" + ShopperAppEndpoints.Stores.OFFERS)
        public static Response GET(int shopperSid, String query) {
            return givenWithAuth()
                    .get(ShopperAppEndpoints.Stores.OFFERS, shopperSid, query);
        }
    }
}
