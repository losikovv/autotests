package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

import java.util.Objects;

public class HdeV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.Admin.Hde.TICKETS)
    public static Response GET(String query, String page) {
        RequestSpecification req = givenWithAuth();
        if (Objects.nonNull(query)) req.queryParam("search", query);
        if (Objects.nonNull(page)) req.queryParam("page", page);
        return req.get(ApiV1Endpoints.Admin.Hde.TICKETS);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.Hde.TICKETS)
    public static Response GET() {
        return GET(null, null);
    }
}
