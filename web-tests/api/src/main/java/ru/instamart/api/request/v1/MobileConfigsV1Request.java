package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class MobileConfigsV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.Admin.MOBILE_CONFIGS)
    public static Response GET() {
        return givenWithAuth()
                .get(ApiV1Endpoints.Admin.MOBILE_CONFIGS);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.MOBILE_CONFIGS)
    public static Response PATCH(int mobileExtendId, String property) {
        JSONObject body = new JSONObject();
        JSONObject mobileExtend = new JSONObject();
        mobileExtend.put("id", mobileExtendId);
        mobileExtend.put("prop", property);
        body.put("mobile_extend", mobileExtend);
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(body)
                .patch(ApiV1Endpoints.Admin.MOBILE_CONFIGS);
    }
}
