package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class MainNavigationV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.Admin.MAIN_NAVIGATION)
    public static Response GET() {
        return givenWithAuth()
                .get(ApiV1Endpoints.Admin.MAIN_NAVIGATION);
    }
}
