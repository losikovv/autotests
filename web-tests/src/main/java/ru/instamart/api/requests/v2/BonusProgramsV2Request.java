package ru.instamart.api.requests.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV2EndPoints;
import ru.instamart.api.requests.ApiV2RequestBase;

public final class BonusProgramsV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.BONUS_PROGRAMS)
    public static Response GET() {
        return givenWithAuth()
                .get(ApiV2EndPoints.BONUS_PROGRAMS);
    }
}
