package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class OffersV1Request extends ApiV1RequestBase {
    @Step("{method} /" + ApiV1Endpoints.Offers.UUID)
    public static Response GET(String offerUuid) {
        return givenWithSpec().get(ApiV1Endpoints.Offers.UUID, offerUuid);
    }
}
