package ru.instamart.api.request.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;
import ru.sbermarket.common.Mapper;

public class ProfileV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.PROFILE)
    public static Response GET(){
        return givenWithAuth()
                .get(ApiV2EndPoints.PROFILE);
    }

    @Step("{method} /" + ApiV2EndPoints.PROFILE)
    public static Response PUT(Profile profile){
        return givenWithAuth()
                .log()
                .all()
                .formParams(Mapper.INSTANCE.objectToMap(profile))
                .put(ApiV2EndPoints.PROFILE);
    }

    /**
     * Параметр                Обязательный	    Описание
     * user[email]	                Да*	        email пользователя
     * user[first_name]             -	        Имя пользователя
     * user[last_name]              -	        Фамилия пользователя
     * user[privacy_terms]	        -	        Согласие на обработку персональных данных
     * user[promo_terms_accepted]	-	        Согласие на рекламную рассылку
     * user[config][send_emails]	-	        Согласие на получение сообщений по email
     * user[config][send_sms]	    -	        Согласие на получение сообщений по SMS
     * user[config][send_push]	    -	        Согласие на получение PUSH-уведомлений
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Getter
    @EqualsAndHashCode
    @ToString
    public static final class Profile{
        @JsonProperty(value = "user[email]")
        private final String email;
        @JsonProperty(value = "user[first_name]")
        private final String firstName;
        @JsonProperty(value = "user[last_name]")
        private final String lastName;
        @JsonProperty(value = "user[privacy_terms]")
        private final String privacyTerms;
        @JsonProperty(value = "user[promo_terms_accepted]")
        private final String promoTermsAccepted;
        @JsonProperty(value = "user[config][send_emails]")
        private final String sendEmail;
        @JsonProperty(value = "user[config][send_sms]")
        private final String sendSms;
        @JsonProperty(value = "user[config][send_push]")
        private final String sendPush;
    }
}
