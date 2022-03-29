package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public class CurrentTimeV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.CURRENT_TIME)
    public static Response GET() {
        return givenWithSpec()
                .get(ApiV2EndPoints.CURRENT_TIME);
    }
}
