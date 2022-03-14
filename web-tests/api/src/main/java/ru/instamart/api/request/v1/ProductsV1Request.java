package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.model.common.OfferForRequest;
import ru.instamart.api.request.ApiV1RequestBase;

public class ProductsV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.Products.OFFERS)
    public static Response GET(String permalink) {
        return givenWithAuth()
                .get(ApiV1Endpoints.Products.OFFERS, permalink);
    }

    @Step("{method} /" + ApiV1Endpoints.Products.OFFERS)
    public static Response POST(String permalink, OfferForRequest offer) {
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(offer)
                .post(ApiV1Endpoints.Products.OFFERS, permalink);
    }
}
