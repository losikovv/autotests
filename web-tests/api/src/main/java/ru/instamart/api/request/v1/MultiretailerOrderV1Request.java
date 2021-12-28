package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class MultiretailerOrderV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.MULTIRETAILER_ORDER)
    public static Response GET() {
        return givenWithAuth().get(ApiV1Endpoints.MULTIRETAILER_ORDER);
    }
}
