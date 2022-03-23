package ru.instamart.api.request.v3;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV3Endpoints;
import ru.instamart.api.enums.v3.ClientV3;
import ru.instamart.api.request.ApiV3RequestBase;

public class AppConfigurationsV3Request extends ApiV3RequestBase {

    public static class CompanyDetails {

        @Step("{method} /" + ApiV3Endpoints.AppConfigurations.COMPANY_DETAILS)
        public static Response GET(ClientV3 client) {
            return givenWithAuth(client)
                    .get(ApiV3Endpoints.AppConfigurations.COMPANY_DETAILS);
        }
    }
}
