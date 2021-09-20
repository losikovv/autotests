package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.UserV2;
import ru.instamart.api.request.v2.UsersV2Request;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.v2.UserDataV2Response;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.*;

@Epic("ApiV2")
@Feature("Данные пользователя")
@Deprecated
public final class UserV2Test extends RestBase {

    @BeforeClass(alwaysRun = true)
    public void precondition() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
    }

    @Deprecated
    @Test(groups = {})
    @Story("Изменение данных пользователя")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateUserDataAllField() {
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.API_V2_FB);
        final Response response = UsersV2Request.PUT(
                session.getLogin(),
                "FirstName",
                "LastName",
                true
        );
        checkStatusCode200(response);
        final UserV2 user = response.as(UserDataV2Response.class).getUser();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(user.getEmail(), session.getLogin(), "Некорректная почта");
        softAssert.assertEquals(user.getFirstName(), "FirstName", "Некорректное имя");
        softAssert.assertEquals(user.getLastName(), "LastName", "Некорректная фамилия");
        softAssert.assertTrue(user.getPromoTermsAccepted(), "Некорректное значение promo");
        softAssert.assertAll();
    }

    @Deprecated
    @Test(groups = {})
    @Story("Изменение данных пользователя на невалидные ФИ")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateUserDataWithInvalidFirstAndLastName() {
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.API_V2_FB);
        final Response response = UsersV2Request.PUT(
                session.getLogin(),
                "",
                "",
                true
        );
        checkStatusCode200(response);
        final UserV2 user = response.as(UserDataV2Response.class).getUser();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(user.getEmail(), session.getLogin(), "Некорректная почта");
        softAssert.assertEquals(user.getFirstName(), "FirstName", "Некорректное имя");
        softAssert.assertEquals(user.getLastName(), "LastName", "Некорректная фамилия");
        softAssert.assertTrue(user.getPromoTermsAccepted(), "Некорректное значение promo");
        softAssert.assertAll();
    }

    @Deprecated
    @Test(groups = {})
    @Story("Изменение пароля с невалидным новым")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdatePasswordWithInvalidNew() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.API_V2_FB);
        final Response response = UsersV2Request.PUT(
                session.getLogin(),
                session.getPassword(),
                "a",
                "a"
        );

        checkStatusCode422(response);
        final ErrorResponse errorResponse = response.as(ErrorResponse.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(errorResponse.getErrorMessages().get(0).getField(), "password", "Неверная ошибка");
        softAssert.assertEquals(errorResponse.getErrorMessages().get(0).getMessage(), "Пароль не должен совпадать с вашим старым паролем", "Неверный текст ошибки");
        softAssert.assertEquals(errorResponse.getErrorMessages().get(0).getHumanMessage(), "Пароль не должен совпадать с вашим старым паролем", "Неверный текст ошибки");
        softAssert.assertAll();
    }

    @Deprecated
    @Test(groups = {})
    @Story("Изменение пароля с невалидным старым")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdatePasswordWithInvalidOld() {
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.API_V2_FB);
        final Response response = UsersV2Request.PUT(
                session.getLogin(),
                "invalid",
                "passw0rd",
                "passw0rd"
        );

        checkStatusCode200(response);
    }

    @Deprecated
    @Test(groups = {})
    @Story("Изменение пароля с невалидным проверочным")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdatePasswordWithInvalidConformation() {
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.API_V2_FB);
        final Response response = UsersV2Request.PUT(
                session.getLogin(),
                session.getPassword(),
                "password",
                "password"
        );

        checkStatusCode200(response);
    }

    @Deprecated
    @Test(groups ={})
    @Story("Изменение пароля")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdatePassword() {
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.API_V2_FB);
        final Response response = UsersV2Request.PUT(
                session.getLogin(),
                session.getPassword(),
                "passw0rd",
                "passw0rd"
        );
        checkStatusCode200(response);
        final UserV2 user = response.as(UserDataV2Response.class).getUser();
        assertEquals(user.getEmail(), session.getLogin(), "Некорректная почта");
    }

    @Deprecated
    @Test(groups = {})
    @Story("Изменение одного поля пользователя")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateUserDataOneField() {
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.API_V2_FB);
        final Response response = UsersV2Request.PUT(
                session.getLogin(),
                "FirstName",
                null,
                false
        );
        checkStatusCode200(response);
        final UserV2 user = response.as(UserDataV2Response.class).getUser();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(user.getEmail(), session.getLogin(), "Некорректная почта");
        softAssert.assertEquals(user.getFirstName(), "FirstName", "Некорректное имя");
        softAssert.assertFalse(user.getPromoTermsAccepted(), "Некорректное значение promo");
        softAssert.assertAll();
    }

    @Deprecated
    @Issue("STF-7288")
    @Test(groups = {})
    @Story("Попытка изменить данные для несуществующего email")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateUserDataWithIncorrectEmail() {
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.API_V2_FB);
        final Response response = UsersV2Request.PUT(
                "fake@mail.com",
                "FirstName",
                null,
                false
        );
        checkStatusCode404(response);
    }

    @Deprecated
    @Test(groups = {})
    @Story("Изменить данные для с подтверждением promo")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateUserDataWithPromoAccept() {
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.API_V2_FB);
        final Response response = UsersV2Request.PUT(
                session.getLogin(),
                "FirstName",
                null,
                true
        );
        checkStatusCode200(response);
    }

    @Deprecated
    @Test(groups = {})
    @Story("Получение данных пользователя")
    @Severity(SeverityLevel.NORMAL)
    public void testGetUserData() {
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.API_V2_FB);
        final Response response = UsersV2Request.GET(
                "InstamartApp",
                session.getLogin()
        );
        checkStatusCode200(response);
        final UserV2 user = response.as(UserDataV2Response.class).getUser();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(user.getEmail(), session.getLogin(), "Некорректная почта");
        softAssert.assertEquals(user.getFirstName(), "autotest-user", "Некорректное имя");
        softAssert.assertAll();
    }

    @Deprecated
    @Test(groups = {})
    @Story("Попытка получить данные для несуществующего email")
    @Severity(SeverityLevel.NORMAL)
    public void testGetUserDataWithIncorrectEmail() {
        final Response response = UsersV2Request.GET(
                "fake@mail.com",
                false
        );
        checkStatusCode200or404(response);
    }

    @Deprecated
    @Test(groups = {})
    @Story("Попытка получение расширенных данных пользователя с невалидным token")
    @Severity(SeverityLevel.NORMAL)
    public void testGetUserDataWithInvalidToken() {
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.API_V2_FB);
        final Response response = UsersV2Request.GET(
                session.getLogin(),
                true,
                "123"

        );
        checkStatusCode404(response);
    }
}
