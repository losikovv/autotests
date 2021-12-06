package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class OrdersV1Request extends ApiV1RequestBase {
    @Step("{method} /" + ApiV1Endpoints.ORDERS)
    public static Response GET(Integer pageNumber) {
        return givenWithAuth()
                .get(ApiV1Endpoints.ORDERS ,pageNumber);
    }

    @Step("{method} /" + ApiV1Endpoints.Orders.NUMBER)
    public static Response GET(String orderNumber) {
        return givenWithAuth()
                .get(ApiV1Endpoints.Orders.NUMBER, orderNumber);
    }
}
