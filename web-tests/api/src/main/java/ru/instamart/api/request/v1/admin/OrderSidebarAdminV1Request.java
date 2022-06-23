package ru.instamart.api.request.v1.admin;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class OrderSidebarAdminV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.Admin.Orders.Number.SIDEBAR)
    public static Response GET(String orderNumber) {
        return givenWithAuth()
                .get(ApiV1Endpoints.Admin.Orders.Number.SIDEBAR, orderNumber);
    }
}
