package ru.instamart.api.request.v1.admin;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

import java.util.Objects;

public class UsersV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.Admin.USERS)
    public static Response GET(String query) {
        RequestSpecification requestSpecification = givenWithAuth();
        if(Objects.nonNull(query)) {
            requestSpecification.queryParam("search", query);
        }
        return requestSpecification.get(ApiV1Endpoints.Admin.USERS);
    }
}
