package ru.instamart.api.request.v1.b2b;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class CompanyManagerV1Request extends ApiV1RequestBase {
    @Step("{method} /" + ApiV1Endpoints.User.Company.MANAGER)
    public static Response GET(String companyID) {
        return givenWithAuth()
                .get(ApiV1Endpoints.User.Company.MANAGER, companyID);
    }
}
