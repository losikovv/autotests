package ru.instamart.api.request.v1.b2b;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class UserCompaniesV1Request extends ApiV1RequestBase {
    @Step("{method} /" + ApiV1Endpoints.User.COMPANIES)
    public static Response GET() {
        return givenWithAuth()
                .get(ApiV1Endpoints.User.COMPANIES);
    }
}
