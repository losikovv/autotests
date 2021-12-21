package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.model.v1.EtaV1;
import ru.instamart.api.request.ApiV1RequestBase;

public class ProxyV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.Proxy.Eta.Retailers.ID)
    public static Response GET(Integer retailerId) {
        return givenWithAuth()
                .get(ApiV1Endpoints.Proxy.Eta.Retailers.ID, retailerId);
    }

    @Step("{method} /" + ApiV1Endpoints.Proxy.Eta.Retailers.ID)
    public static Response PUT(Integer retailerId) {
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(EtaV1.builder()
                        .id(retailerId.toString())
                        .courierSpeed(197)
                        .deliveryTimeSigma(3840)
                        .window(1020)
                        .build())
                .put(ApiV1Endpoints.Proxy.Eta.Retailers.ID, retailerId);
    }
}