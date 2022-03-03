package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class DocumentsV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.Admin.Shipments.DOCUMENTS)
    public static Response GET(String shipmentNumber, String document) {
        return givenWithAuth()
                .get(ApiV1Endpoints.Admin.Shipments.DOCUMENTS, shipmentNumber, document);
    }
}
