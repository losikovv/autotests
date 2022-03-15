package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

import java.util.Objects;

public class CompanyPresenceV2Request extends ApiV2RequestBase {
    @Step("{method} /" + ApiV2EndPoints.COMPANY_PRESENCE)
    public static Response GET(String inn) {

        RequestSpecification requestSpecification = givenWithAuth();
        if (Objects.nonNull(inn)) {
            requestSpecification
                    .queryParam("inn", inn);
        }
        return requestSpecification
                .get(ApiV2EndPoints.COMPANY_PRESENCE);
    }
}
