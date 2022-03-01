package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import ru.instamart.api.enums.shopper.ItemStateSHP;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.UserV2;
import ru.instamart.api.request.v2.ProfileV2Request;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.v2.ProfileV2Response;
import ru.instamart.kraken.data.Generate;

import java.util.UUID;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.kraken.helper.PhoneNumberHelper.getHumanPhoneNumber;
import static ru.instamart.kraken.helper.UUIDHelper.isValidUUID;

@Epic("ApiV2")
@Feature("Профиль пользователя")
public class ProfileV2Test extends RestBase {

    @AfterClass(alwaysRun = true)
    public void after(){
        SessionFactory.clearSession(SessionType.API_V2);
    }

    @BeforeClass(alwaysRun = true)
    public void before(){
        SessionFactory.makeSession(SessionType.API_V2, SessionProvider.PHONE);
    }

    @CaseId(159)
    @Test(description = "Получение данных профиля пользователя. Запрос с токеном",
            groups = {"api-instamart-smoke"})
    public void getProfile200() {
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.API_V2);
        final Response response = ProfileV2Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProfileV2Response.class);
        UserV2 user = response.as(ProfileV2Response.class).getUser();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(user.getCurrentPhone(), getHumanPhoneNumber(session.getUserData().getPhone()),
                "Current phone не совпадает с введенным");

        softAssert.assertTrue(isValidUUID(user.getId()), "id is not valid");
        softAssert.assertTrue(user.getPrivacyTerms(), "privacy_terms is not TRUE");
        softAssert.assertFalse(user.getB2b(), "b2b is not FALSE");

        softAssert.assertTrue(user.getConfig().getSendEmails(), "send emails is not TRUE");
        softAssert.assertTrue(user.getConfig().getSendSms(), "send sms is not TRUE");
        softAssert.assertTrue(user.getConfig().getSendPush(), "send push is not TRUE");

        softAssert.assertAll();
    }

    @CaseId(150)
    @Test(description = "Обновление профиля пользователя",
            groups = {"api-instamart-regress"})
    public void putProfile200() {
        SessionFactory.makeSession(SessionType.API_V2, SessionProvider.PHONE);
        final String newEmail = UUID.randomUUID() + "@autotestmail.dev";
        final String newFirstName = Generate.literalString(10);
        final String newLastName = Generate.literalString(10);

        ProfileV2Request.Profile buildProfile = ProfileV2Request.Profile.builder()
                .email(newEmail)
                .firstName(newFirstName)
                .lastName(newLastName)
                .build();
        final Response response = ProfileV2Request.PUT(buildProfile);
        checkStatusCode200(response);
        ProfileV2Response profile = response.as(ProfileV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(profile.getUser().getFirstName(), newFirstName, "new first name not accepted");
        softAssert.assertEquals(profile.getUser().getLastName(), newLastName, "new first name not accepted");
        softAssert.assertEquals(profile.getUser().getEmail(), newEmail, "new first name not accepted");
        softAssert.assertEquals(profile.getUser().getDisplayEmail(), newEmail, "new first name not accepted");
        softAssert.assertAll();
    }

    @CaseId(151)
    @Test(description = "Обновление профиля пользователя",
            groups = {"api-instamart-regress"})
    public void putProfile422() {
        final String newEmail = "test###autotestmail.dev";
        final String newFirstName = "!@#$%";
        final String newLastName = "9&%^&%#@&^#@*&^*&@";

        ProfileV2Request.Profile buildProfile = ProfileV2Request.Profile.builder()
                .email(newEmail)
                .firstName(newFirstName)
                .lastName(newLastName)
                .build();
        final Response response = ProfileV2Request.PUT(buildProfile);
        checkStatusCode422(response);
        final SoftAssert softAssert = new SoftAssert();
        ErrorResponse error = response.as(ErrorResponse.class);
        softAssert.assertEquals(error.getErrors().getEmail(), "Неверный формат email", "Невалидная ошибка");
        softAssert.assertEquals(error.getErrorMessages().get(0).getField(), "email", "Невалидный тип ошибки");
        softAssert.assertEquals(error.getErrorMessages().get(0).getMessage(), "Неверный формат email", "Невалидная ошибка");
        softAssert.assertEquals(error.getErrorMessages().get(0).getHumanMessage(), "Неверный формат email", "Невалидная ошибка");
        softAssert.assertAll();
    }
}
