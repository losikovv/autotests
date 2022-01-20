package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class PhoneConfirmationsV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.PHONE_CONFIRMATIONS)
    public static Response POST(String encryptedPhoneNumber) {
        JSONObject body = new JSONObject();
        body.put("phone", encryptedPhoneNumber);
        return givenWithSpec()
                .contentType(ContentType.JSON)
                .body(body)
                .post(ApiV1Endpoints.PHONE_CONFIRMATIONS);
    }

    @Step("{method} /" + ApiV1Endpoints.PhoneConfirmations.BY_NUMBER)
    public static Response PUT(String phoneNumber, Integer phoneConfirmationCode) {
        JSONObject body = new JSONObject();
        body.put("phone_confirmation_code", phoneConfirmationCode);
        body.put("promo_terms_accepted", true);
        body.put("b2b", false);
        return givenWithSpec()
                .contentType(ContentType.JSON)
                .body(body)
                .put(ApiV1Endpoints.PhoneConfirmations.BY_NUMBER, phoneNumber);
    }
}
