package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public class PhoneConfirmationsV2Request extends ApiV2RequestBase {

    /**
     * @param encryptedPhoneNumber получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("{method} /" + ApiV2EndPoints.PHONE_CONFIRMATIONS)
    public static Response POST(String encryptedPhoneNumber) {
        JSONObject body = new JSONObject();
        body.put("phone", encryptedPhoneNumber);
        return givenWithSpec()
                .contentType(ContentType.JSON)
                .body(body)
                .post(ApiV2EndPoints.PHONE_CONFIRMATIONS);
    }

    public static Response PUT(String phoneNumber) {
        return PUT(phoneNumber, "111111", true);
    }

    @Step("{method} /" + ApiV2EndPoints.PhoneConfirmations.PHONE_NUMBER)
    public static Response PUT(String phoneNumber,
                               String phoneConfirmationCode,
                               boolean promoTermsAccepted) {
        return givenWithSpec()
                .put(ApiV2EndPoints.PhoneConfirmations.PHONE_NUMBER,
                        phoneNumber,
                        phoneConfirmationCode,
                        promoTermsAccepted);
    }
}
