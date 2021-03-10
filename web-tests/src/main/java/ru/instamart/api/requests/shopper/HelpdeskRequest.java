package instamart.api.requests.shopper;

import instamart.api.endpoints.ShopperApiEndpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static instamart.api.requests.ShopperRequestBase.givenWithAuth;

public final class HelpdeskRequest {

    public static class Tickets {
        /**
         * Получаем тикеты хелпдеска
         */
        @Step("{method} /" + ShopperApiEndpoints.Helpdesk.TICKETS)
        public static Response GET(String shipmentId) {
            return givenWithAuth()
                    .get(ShopperApiEndpoints.Helpdesk.TICKETS, shipmentId);
        }
    }
}
