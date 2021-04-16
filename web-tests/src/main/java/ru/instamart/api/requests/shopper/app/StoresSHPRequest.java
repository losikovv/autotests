package ru.instamart.api.requests.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ShopperAppEndpoints;
import ru.instamart.api.requests.ShopperAppRequestBase;

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
