package ru.instamart.api.request.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ShopperAppEndpoints;
import ru.instamart.api.request.ShopperAppRequestBase;

public final class HelpdeskSHPRequest extends ShopperAppRequestBase {

    public static class Tickets {
        /**
         * Получаем тикеты хелпдеска
         */
        @Step("{method} /" + ShopperAppEndpoints.Helpdesk.TICKETS)
        public static Response GET(String shipmentId) {
            return givenWithAuth()
                    .get(ShopperAppEndpoints.Helpdesk.TICKETS, shipmentId);
        }
    }
}
