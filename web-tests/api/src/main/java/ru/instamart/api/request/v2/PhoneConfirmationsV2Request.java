package ru.instamart.api.request.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;
import ru.instamart.kraken.config.CoreProperties;
import ru.sbermarket.common.Mapper;

import java.util.UUID;

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
        var phoneConfirmations = PhoneConfirmations.builder()
                .phoneConfirmationCode(CoreProperties.DEFAULT_SMS)
                .promoTermsAccepted(true)
                .anonymousId(UUID.randomUUID().toString())
                .build();
        return PUT(phoneNumber, phoneConfirmations);
    }

    @Step("{method} /" + ApiV2EndPoints.PhoneConfirmations.PHONE_NUMBER_WITH_PARAM)
    public static Response PUT(String phoneNumber,
                               String phoneConfirmationCode,
                               boolean promoTermsAccepted) {
        return givenWithSpec()
                .put(ApiV2EndPoints.PhoneConfirmations.PHONE_NUMBER_WITH_PARAM,
                        phoneNumber,
                        phoneConfirmationCode,
                        promoTermsAccepted);
    }

    @Step("{method} /" + ApiV2EndPoints.PhoneConfirmations.PHONE_NUMBER)
    public static Response PUT(String phoneNumber, PhoneConfirmations phoneConfirmations) {
        return givenWithSpec()
                .formParams(Mapper.INSTANCE.objectToMap(phoneConfirmations))
                .put(ApiV2EndPoints.PhoneConfirmations.PHONE_NUMBER, phoneNumber);

    }

    /**
     * Параметр	Обязательный	Описание
     * phone_confirmation_code	Да	Код подтверждения из смс
     * promo_terms_accepted	Нет	Согласие на рекламную рассылк
     * "anonymous_id": "9302f72a-e67c-443e-b366-04b0f432f3ac"
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Getter
    @EqualsAndHashCode
    @ToString
    public static final class PhoneConfirmations {
        @JsonProperty("phone_confirmation_code")
        private final String phoneConfirmationCode;
        @JsonProperty("promo_terms_accepted")
        private final Boolean promoTermsAccepted;
        @JsonProperty("anonymous_id")
        private final String anonymousId;
    }
}
