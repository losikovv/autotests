package ru.instamart.api.request.shopper.app;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
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
                    .queryParam("shipment_id", shipmentId)
                    .get(ShopperAppEndpoints.Helpdesk.TICKETS);
        }

        /**
         * Создаем новый тикет хелпдеска
         */
        @Step("{method} /" + ShopperAppEndpoints.Helpdesk.TICKETS)
        public static Response POST(String shipmentId) {
            JSONObject ticket = new JSONObject();
            ticket.put("shipment_id", shipmentId);
            ticket.put("type_id", "1");
            ticket.put("title", "АПИ-АВТОТЕСТ");
            ticket.put("description", "АПИ-АВТОТЕСТ");
            return givenWithAuth()
                    .body(ticket)
                    .contentType(ContentType.JSON)
                    .post(ShopperAppEndpoints.Helpdesk.TICKETS);
        }
    }
}
