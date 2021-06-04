package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public class PhoneConfirmationsV2Request extends ApiV2RequestBase {

    /**
     * @param encryptedPhoneNumber получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("{method} /" + ApiV2EndPoints.PHONE_CONFIRMATIONS)
    public static Response POST(String encryptedPhoneNumber) {
        return givenWithSpec()
                .post(ApiV2EndPoints.PHONE_CONFIRMATIONS, encryptedPhoneNumber);
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
