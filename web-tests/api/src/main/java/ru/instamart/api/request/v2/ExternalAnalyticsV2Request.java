package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV2EndPoints;

import java.util.UUID;

import static ru.instamart.api.request.ApiV2RequestBase.givenWithAuth;

public final class ExternalAnalyticsV2Request {

    @Step("{method} /" + ApiV2EndPoints.ExternalAnalytics.DEVICE_IDENTIFICATION)
    public static Response POST() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("device_id", UUID.randomUUID().toString());
        return givenWithAuth()
                .body(requestParams)
                .contentType(ContentType.JSON)
                .post(ApiV2EndPoints.ExternalAnalytics.DEVICE_IDENTIFICATION);
    }
}
