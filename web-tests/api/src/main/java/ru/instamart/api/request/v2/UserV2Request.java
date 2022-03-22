package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public final class UserV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.User.COMPANIES)
    public static Response GET() {
        return givenWithAuth()
                .get(ApiV2EndPoints.User.COMPANIES);
    }

    @Step("{method} /" + ApiV2EndPoints.User.COMPANIES)
    public static Response POST(final String inn, final String name) {
        return givenWithAuth()
                .formParam("company[inn]", inn)
                .formParams("company[name]", name)
                .post(ApiV2EndPoints.User.COMPANIES);
    }

    public static class CompanyEmployees {

        @Step("{method} /" + ApiV2EndPoints.User.COMPANY_EMPLOYEES)
        public static Response POST(final String inn, final String code) {
            return givenWithAuth()
                    .formParam("company_inn", inn)
                    .formParam("company_security_code", code)
                    .post(ApiV2EndPoints.User.COMPANY_EMPLOYEES);
        }
    }

    public static class Exist {

        @Step("{method} /" + ApiV2EndPoints.User.EXIST)
        public static Response GET() {
            return givenWithAuth()
                    .get(ApiV2EndPoints.User.EXIST);
        }
    }
}
