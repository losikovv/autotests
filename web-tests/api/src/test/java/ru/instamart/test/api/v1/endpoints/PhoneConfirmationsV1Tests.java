package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v1.PhoneConfirmationsV1Request;
import ru.instamart.api.response.v1.PhoneConfirmationsV1Response;
import ru.instamart.api.response.v1.PhoneTokenV1Response;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.PhoneCrypt;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode422;

@Epic("ApiV1")
@Feature("Авторизация")
public class PhoneConfirmationsV1Tests extends RestBase {

    private static final String phoneNumber = Generate.phoneNumber();

    @CaseId(1512)
    @Story("Авторизация по номеру телефона")
    @Test(description = "Отправляем запрос на получение смс с кодом",
            groups = {"api-instamart-smoke"})
    public void postPhoneConfirmations() {
        final Response response = PhoneConfirmationsV1Request.POST(PhoneCrypt.INSTANCE.encryptPhone(phoneNumber));
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PhoneTokenV1Response.class);
        compareTwoObjects(response.as(PhoneTokenV1Response.class).getCodeLength(), 6);
    }

    @CaseIDs(value = {@CaseId(1516), @CaseId(1518)})
    @Story("Авторизация по номеру телефона")
    @Test(description = "Получение токена авторизации по номеру телефона и коду из смс",
            groups = {"api-instamart-smoke"},
            dependsOnMethods = "postPhoneConfirmations")
    public void putPhoneConfirmations() {
        final Response response = PhoneConfirmationsV1Request.PUT(phoneNumber, Integer.valueOf(CoreProperties.DEFAULT_SMS));
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PhoneConfirmationsV1Response.class);
    }

    @CaseId(1514)
    @Story("Инициация отправки кода подтверждения")
    @Test(groups = {"api-instamart-smoke"},
            description = "Инициировать отправку кода подтверждения. Пользователь существует с указанным phone")
    public void postPhoneConfirmationsWithExistentPhone() {
        final Response response = PhoneConfirmationsV1Request.POST(UserManager.getQaUser().getEncryptedPhone());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PhoneTokenV1Response.class);
        compareTwoObjects(response.as(PhoneTokenV1Response.class).getCodeLength(), 6);
    }

    @CaseId(1513)
    @Story("Инициация отправки кода подтверждения")
    @Test(groups = {"api-instamart-regress"},
            description = "Инициировать отправку кода подтверждения  с невалидным значением для phone")
    public void postPhoneConfirmationsWithInvalidPhone() {
        final Response response = PhoneConfirmationsV1Request.POST(PhoneCrypt.INSTANCE.encryptPhone("invalidPhoneNumber"));
        checkStatusCode422(response);
    }

    @CaseId(1517)
    @Story("Подтверждение телефона кодом")
    @Test(groups = {"api-instamart-regress"},
            description = "Подтверждение телефона кодом с невалидным номером")
    public void confirmPhonesWithInvalidNumber() {
        final Response response = PhoneConfirmationsV1Request.PUT("invalidPhoneNumber", 111);
        checkStatusCode422(response);
    }

    @CaseId(1519)
    @Story("Подтверждение телефона кодом")
    @Test(groups = {"api-instamart-regress"},
            description = "Подтверждение телефона кодом с валидным номером без запроса")
    public void confirmPhonesWithValidPhone() {
        final Response response = PhoneConfirmationsV1Request.PUT(Generate.phoneNumber(), Integer.valueOf(CoreProperties.DEFAULT_SMS));
        checkStatusCode422(response);
    }
}
