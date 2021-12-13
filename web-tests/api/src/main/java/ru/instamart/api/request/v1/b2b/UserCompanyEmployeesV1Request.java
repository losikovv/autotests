package ru.instamart.api.request.v1.b2b;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class UserCompanyEmployeesV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.User.COMPANIES_EMPLOYEES)
    public static Response DELETE(String companyID) {
        return givenWithAuth()
                .delete(ApiV1Endpoints.User.COMPANIES_EMPLOYEES, companyID);
    }

}
