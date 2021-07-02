package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v2.PhoneTokenV2;
import ru.instamart.api.request.v2.PhoneConfirmationsV2Request;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.v2.PhoneTokenV2Response;
import ru.instamart.api.response.v2.SessionsV2Response;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.kraken.util.ThreadUtil;

import static org.junit.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

@Slf4j
@Epic("ApiV2")
@Feature("Авторизация")
public class PhoneConfirmationsV2Test extends RestBase {

    @CaseId(626)
    @Story("Авторизация по номеру телефона")
    @Test(  description = "Отправляем запрос на получение смс с кодом",
            groups = {"api-instamart-smoke"})
    public void postPhoneConfirmations() {
        Response response = PhoneConfirmationsV2Request.POST("bjg8q2s53S057R4rWgL9PHDhF6UOdFIPGwzzhMH+BYE=");

        if (response.statusCode() == 422) {
            String errorMessage = response.as(ErrorResponse.class).getErrors().getBase();
            if (errorMessage.startsWith("До повторной отправки:")) {
                log.error(errorMessage);
                ThreadUtil.simplyAwait(StringUtil.extractNumberFromString(errorMessage) + 1);
                response = PhoneConfirmationsV2Request.POST("bjg8q2s53S057R4rWgL9PHDhF6UOdFIPGwzzhMH+BYE=");
            }
        }

        checkStatusCode200(response);
        PhoneTokenV2 phoneToken = response.as(PhoneTokenV2Response.class).getPhoneToken();
        assertNotNull(phoneToken.getResendLimit());
        assertNotNull(phoneToken.getCodeLength());
    }

    @CaseId(627)
    @Story("Авторизация по номеру телефона")
    @Test(  description = "Получение токена авторизации по номеру телефона и коду из смс",
            groups = {"api-instamart-smoke"},
            dependsOnMethods = "postPhoneConfirmations")
    public void putPhoneConfirmations() {
        Response response = PhoneConfirmationsV2Request.PUT(
                "79999999966",
                "111111",
                true);
        checkStatusCode200(response);
        assertNotNull(response.as(SessionsV2Response.class).getSession().getAccessToken());
    }
}
