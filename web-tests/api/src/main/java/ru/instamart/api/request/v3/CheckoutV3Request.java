package ru.instamart.api.request.v3;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV3Endpoints;
import ru.instamart.api.enums.v3.ClientV3;
import ru.instamart.api.request.ApiV3RequestBase;

import java.util.List;

public class CheckoutV3Request extends ApiV3RequestBase {

    @Step("{method} /" + ApiV3Endpoints.Checkout.Orders.INITIALIZATION)
    public static Response POST(String orderNumber, List<String> shipmentNumbers) {
        JSONObject body = new JSONObject();
        body.put("shipment_numbers", shipmentNumbers);
        return givenWithAuth(ClientV3.SBERMARKET_WEB)
                .contentType(ContentType.JSON)
                .body(body)
                .post(ApiV3Endpoints.Checkout.Orders.INITIALIZATION, orderNumber);
    }

    @Step("{method} /" + ApiV3Endpoints.Checkout.ORDER)
    public static Response GET(String orderNumber) {
        return givenWithAuth(ClientV3.SBERMARKET_WEB)
                .get(ApiV3Endpoints.Checkout.ORDER, orderNumber);
    }

    public static class Validation {
        @Step("{method} /" + ApiV3Endpoints.Checkout.Orders.VALIDATION)
        public static Response GET(String orderNumber) {
            return givenWithAuth(ClientV3.SBERMARKET_WEB)
                    .get(ApiV3Endpoints.Checkout.Orders.VALIDATION, orderNumber);
        }
    }
}
