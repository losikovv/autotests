package ru.instamart.api.requests.shopper;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ShopperApiEndpoints;

import static ru.instamart.api.requests.ShopperRequestBase.givenWithAuth;

public final class StoresSHPRequest {

    public static class Offers {
        /**
         * Ищем товары
         */
        @Step("{method} /" + ShopperApiEndpoints.Stores.OFFERS)
        public static Response GET(int shopperSid, String query) {
            return givenWithAuth()
                    .get(ShopperApiEndpoints.Stores.OFFERS, shopperSid, query);
        }
    }
}
