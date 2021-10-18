package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.ProfileV2Request;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.v2.ProfileV2Response;
import ru.instamart.kraken.testdata.Generate;

import java.util.UUID;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.errorAssert;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.*;
import static ru.instamart.kraken.helper.PhoneNumberHelper.getHumanPhoneNumber;
import static ru.instamart.kraken.helper.UUIDHelper.isValidUUID;

@Epic("ApiV2")
@Feature("Profile")
public class ProfileV2Test extends RestBase {

    @AfterMethod(alwaysRun = true)
    public void after(){
        SessionFactory.clearSession(SessionType.API_V2_FB);
        SessionFactory.clearSession(SessionType.API_V2_PHONE);
    }

    @CaseId(159)
    @Test(description = "Получение данных профиля пользователя. Запрос с токеном",
            groups = {"api-instamart-regress"})
    public void getProfile200() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.API_V2_FB);
        final Response response = ProfileV2Request.GET();
        checkStatusCode200(response);
        ProfileV2Response profile = response.as(ProfileV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(profile.getUser().getLastName(), session.getUserData().getLastName(),
                "Last name не совпадает с введенным");
        softAssert.assertEquals(profile.getUser().getFirstName(), session.getUserData().getFirstName(),
                "First name не совпадает с введенным");
        softAssert.assertEquals(profile.getUser().getDisplayEmail(), session.getUserData().getEmail(),
                "Email не совпадает с введенным");
        softAssert.assertEquals(profile.getUser().getCurrentPhone(), getHumanPhoneNumber(session.getUserData().getPhone()),
                "Current phone не совпадает с введенным");

        softAssert.assertTrue(isValidUUID(profile.getUser().getId()), "id is not valid");
        softAssert.assertFalse(profile.getUser().getHasConfirmedPhone(), "has_confirmed_phone is not FALSE");
        softAssert.assertTrue(profile.getUser().getPrivacyTerms(), "privacy_terms is not TRUE");
        softAssert.assertFalse(profile.getUser().getPromoTermsAccepted(), "promo_terms_accepted is not FALSE");
        softAssert.assertFalse(profile.getUser().getB2b(), "b2b is not FALSE");

        softAssert.assertEquals(profile.getUser().getAttachedServices().get(0), "facebook",
                "attachment service element don't equals \"facebook\"");
        softAssert.assertEquals(profile.getUser().getAttachedServices().size(), 1,
                "User attachment service contains more service");

        softAssert.assertTrue(profile.getUser().getConfig().getSendEmails(), "send emails is not TRUE");
        softAssert.assertTrue(profile.getUser().getConfig().getSendSms(), "send sms is not TRUE");
        softAssert.assertTrue(profile.getUser().getConfig().getSendPush(), "send push is not TRUE");

        softAssert.assertAll();
    }

    @CaseId(160)
    @Test(description = "Получение данных профиля пользователя. Запрос без токена",
            groups = {"api-instamart-regress"})
    public void getProfile401() {
        final Response response = ProfileV2Request.GET();
        checkStatusCode401(response);
        errorAssert(response, "Ключ доступа невалиден или отсутствует");
    }

    @CaseId(150)
    @Test(description = "Обновление профиля пользователя",
            groups = {"api-instamart-regress"})
    public void putProfile200() {
        SessionFactory.makeSession(SessionType.API_V2_PHONE);
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
        SessionFactory.makeSession(SessionType.API_V2_PHONE);
        final String newEmail = "test###autotestmail.dev";
        final String newFirstName = "!@#$%";
        final String newLastName = "9&%^&%#@&^#@*&^*&@";

        ProfileV2Request.Profile buildProfile = ProfileV2Request.Profile.builder()
                .email(newEmail)
                .firstName(newFirstName)
                .lastName(newLastName)
                .build();
        final Response response = ProfileV2Request.PUT(buildProfile);
        response.prettyPeek();
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
