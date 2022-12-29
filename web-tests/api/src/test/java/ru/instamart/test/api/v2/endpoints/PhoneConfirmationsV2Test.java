package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v2.PhoneTokenV2;
import ru.instamart.api.request.v2.PhoneConfirmationsV2Request;
import ru.instamart.api.response.v2.PhoneTokenV2Response;
import ru.instamart.api.response.v2.SessionsV2Response;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.util.PhoneCrypt;

import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode422;

@Epic("ApiV2")
@Feature("Авторизация")
public class PhoneConfirmationsV2Test extends RestBase {
    private static final String phoneNumber = Generate.phoneNumber();

    @TmsLink("451")
    @Story("Авторизация по номеру телефона")
    @Test(description = "Отправляем запрос на получение смс с кодом",
            groups = {"api-instamart-smoke", "MRAutoCheck", "api-v2"})
    public void postPhoneConfirmations() {
        PhoneTokenV2 phoneToken = apiV2.sendSMS(PhoneCrypt.INSTANCE.encryptPhone(phoneNumber));
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(phoneToken.getResendLimit(), Integer.valueOf(60), "resend_limit mismatch");
        softAssert.assertEquals(phoneToken.getCodeLength(), Integer.valueOf(6), "code_length mismatch");
        softAssert.assertAll();
    }

    @TmsLink("456")
    @Story("Авторизация по номеру телефона")
    @Test(description = "Получение токена авторизации по номеру телефона и коду из смс",
            groups = {"api-instamart-smoke", "MRAutoCheck", "api-v2"},
            dependsOnMethods = "postPhoneConfirmations")
    public void putPhoneConfirmations() {
        final Response response = PhoneConfirmationsV2Request.PUT(phoneNumber, CoreProperties.DEFAULT_UI_SMS, true);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, SessionsV2Response.class);
    }

    @TmsLink("452")
    @Story("Инициировать отправку кода подтверждения")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Инициировать отправку кода подтверждения  с невалидным значением для phone")
    public void postPhoneConfirmations404() {
        final Response response = PhoneConfirmationsV2Request.POST(PhoneCrypt.INSTANCE.encryptPhone("invalidPhoneNumber"));
        checkStatusCode422(response);
        checkError(response, "PhoneToken: Value не может быть пустым");
    }

    @TmsLink("453")
    @Story("Инициировать отправку кода подтверждения")
    @Test(groups = {"api-instamart-smoke", "api-v2"},
            description = "Инициировать отправку кода подтверждения. Пользователь существует с указанным phone")
    public void postPhoneConfirmationsPhoneNotExist200() {
        final Response response = PhoneConfirmationsV2Request.POST(PhoneCrypt.INSTANCE.encryptPhone(Generate.phoneNumber()));
        checkStatusCode200(response);
        PhoneTokenV2Response phoneToken = response.as(PhoneTokenV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(phoneToken.getPhoneToken().getResendLimit(), Integer.valueOf(60), "resend_limit mismatch");
        softAssert.assertEquals(phoneToken.getPhoneToken().getCodeLength(), Integer.valueOf(6), "code_length mismatch");
        softAssert.assertAll();
    }

    @TmsLink("457")
    @Story("Подтверждение телефона кодом")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Подтверждение телефона кодом с невалидным номером")
    public void confirmPhonesWithInvalidNumber() {
        final Response response = PhoneConfirmationsV2Request.PUT("invalidPhoneNumber");
        checkStatusCode422(response);
        checkError(response, "PhoneToken: Value не может быть пустым");
    }

    @TmsLink("459")
    @Story("Подтверждение телефона кодом")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Подтверждение телефона кодом с валидным номером без запроса")
    public void confirmPhonesWithValidPhone() {
        final Response response = PhoneConfirmationsV2Request.PUT(Generate.phoneNumber(), CoreProperties.DEFAULT_UI_SMS, false);
        checkStatusCode422(response);
        checkError(response, "Неверный код");
    }
}
