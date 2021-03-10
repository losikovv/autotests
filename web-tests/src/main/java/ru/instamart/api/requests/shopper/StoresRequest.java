package instamart.api.requests.shopper;

import instamart.api.endpoints.ShopperApiEndpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static instamart.api.requests.ShopperRequestBase.givenWithAuth;

public final class StoresRequest {

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
