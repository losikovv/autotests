package ru.instamart.api.request.v1.b2b;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class CompanyPresenceV1Request extends ApiV1RequestBase {
    @Step("{method} /" + ApiV1Endpoints.COMPANY_PRESENCE)
    public static Response GET(String inn) {
        return givenWithAuth()
                .get(ApiV1Endpoints.COMPANY_PRESENCE, inn);
    }
}
