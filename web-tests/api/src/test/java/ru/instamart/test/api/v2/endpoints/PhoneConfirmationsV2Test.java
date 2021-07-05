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
import ru.instamart.api.response.v2.SessionsV2Response;

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
        PhoneTokenV2 phoneToken = apiV2.sendSMS("bjg8q2s53S057R4rWgL9PHDhF6UOdFIPGwzzhMH+BYE=");
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
