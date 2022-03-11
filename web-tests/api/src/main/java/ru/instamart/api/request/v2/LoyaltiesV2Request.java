package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public final class LoyaltiesV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.LOYALTIES)
    public static Response GET() {
        return givenWithAuth()
                .get(ApiV2EndPoints.LOYALTIES);
    }
}
