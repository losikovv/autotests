package ru.instamart.api.requests.v1;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV1Endpoints;
import ru.instamart.api.requests.ApiV1RequestBase;

public class OrdersV1Request extends ApiV1RequestBase {
    @Step("{method} /" + ApiV1Endpoints.ORDERS)
    public static Response GET() {
        return givenWithAuth()
                .get(ApiV1Endpoints.ORDERS);
    }

    @Step("{method} /" + ApiV1Endpoints.Orders.NUMBER)
    public static Response GET(String orderNumber) {
        return givenWithAuth()
                .get(ApiV1Endpoints.Orders.NUMBER, orderNumber);
    }
}
