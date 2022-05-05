package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class PromotionDetailsV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.Admin.PromotionDetails.PromotionCode.PROMOTION_CODE_DETAILS)
    public static Response POST(long promoCodeId, String value) {
        JSONObject body = new JSONObject();
        JSONObject promotionCode = new JSONObject();
        promotionCode.put("id", promoCodeId);
        promotionCode.put("value", value);
        body.put("promotion_code", promotionCode);
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(body)
                .post(ApiV1Endpoints.Admin.PromotionDetails.PromotionCode.PROMOTION_CODE_DETAILS);
    }


}
