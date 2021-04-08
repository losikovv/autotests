package ru.instamart.api.requests.shopper;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ShopperApiEndpoints;

import static ru.instamart.api.requests.ShopperRequestBase.givenWithAuth;

public final class HelpdeskSHPRequest {

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
