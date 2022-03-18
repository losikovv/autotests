package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
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
}
