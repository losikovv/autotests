package ru.instamart.api.requests.v1;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV1Endpoints;
import ru.instamart.api.requests.ApiV1RequestBase;

public class LineItemsV1Request extends ApiV1RequestBase {
    @Step("{method} /" + ApiV1Endpoints.LINE_ITEMS)
    public static Response GET(String shipmentNumber) {
        return givenWithAuth()
                .get(ApiV1Endpoints.LINE_ITEMS, shipmentNumber);
    }
}
