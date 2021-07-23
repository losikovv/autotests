package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public class PhonesV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.PHONES)
    public static Response GET() {
        return givenWithAuth()
                .get(ApiV2EndPoints.PHONES);
    }
}