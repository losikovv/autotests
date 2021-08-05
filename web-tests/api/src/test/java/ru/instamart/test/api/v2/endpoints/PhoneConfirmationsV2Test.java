package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v2.PhoneTokenV2;
import ru.instamart.api.request.v2.PhoneConfirmationsV2Request;
import ru.instamart.api.response.v2.SessionsV2Response;
import ru.instamart.kraken.util.PhoneCrypt;

import static org.junit.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

@Slf4j
@Epic("ApiV2")
@Feature("Авторизация")
public class PhoneConfirmationsV2Test extends RestBase {
    String phoneNumber = "9871234123";

    @CaseId(626)
    @Story("Авторизация по номеру телефона")
    @Test(description = "Отправляем запрос на получение смс с кодом",
            groups = {"api-instamart-smoke"})
    public void postPhoneConfirmations() {
        PhoneTokenV2 phoneToken = apiV2.sendSMS(PhoneCrypt.INSTANCE.encryptPhone(phoneNumber));
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(phoneToken.getResendLimit(), Integer.valueOf(60), "resend_limit mismatch");
        softAssert.assertEquals(phoneToken.getCodeLength(), Integer.valueOf(6), "code_length mismatch");
        softAssert.assertAll();
    }

    @CaseId(627)
    @Story("Авторизация по номеру телефона")
    @Test(  description = "Получение токена авторизации по номеру телефона и коду из смс",
            groups = {"api-instamart-smoke"},
            dependsOnMethods = "postPhoneConfirmations")
    public void putPhoneConfirmations() {
        Response response = PhoneConfirmationsV2Request.PUT(phoneNumber, "111111", true);
        checkStatusCode200(response);
        assertNotNull(response.as(SessionsV2Response.class).getSession().getAccessToken());
    }
}
