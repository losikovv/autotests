package ru.instamart.tests.api.v2.endpoints;

import instamart.api.SessionFactory;
import instamart.api.common.RestBase;
import instamart.api.enums.SessionType;
import instamart.api.objects.v2.User;
import instamart.api.requests.v2.UsersRequest;
import instamart.api.responses.v2.ErrorResponse;
import instamart.api.responses.v2.UserDataResponse;
import io.qameta.allure.*;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.*;

@Epic("ApiV2")
@Feature("Данные пользователя")
public final class UserV2Test extends RestBase {

    @BeforeClass(alwaysRun = true)
    public void precondition() {
        SessionFactory.makeSession(SessionType.APIV2);
    }

    @CaseId(150)
    @Test(groups = {"api-instamart-regress"})
    @Story("Изменение данных пользователя")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateUserDataAllField() {
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.APIV2);
        final Response response = UsersRequest.PUT(
                session.getLogin(),
                "FirstName",
                "LastName",
                true
        );
        assertStatusCode200(response);
        final User user = response.as(UserDataResponse.class).getUser();
        Assert.assertEquals(user.getEmail(), session.getLogin(), "Некорректная почта");
        Assert.assertEquals(user.getFirst_name(), "FirstName", "Некорректное имя");
        Assert.assertEquals(user.getLast_name(), "LastName", "Некорректная фамилия");
        Assert.assertTrue(user.getPromo_terms_accepted(), "Некорректное значение promo");
    }

    @CaseId(151)
    @Test(groups = {"api-instamart-regress"})
    @Story("Изменение данных пользователя на невалидные ФИ")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateUserDataWithInvalidFirstAndLastName() {
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.APIV2);
        final Response response = UsersRequest.PUT(
                session.getLogin(),
                "",
                "",
                true
        );
        assertStatusCode200(response);
        final User user = response.as(UserDataResponse.class).getUser();
        Assert.assertEquals(user.getEmail(), session.getLogin(), "Некорректная почта");
        Assert.assertEquals(user.getFirst_name(), "FirstName", "Некорректное имя");
        Assert.assertEquals(user.getLast_name(), "LastName", "Некорректная фамилия");
        Assert.assertTrue(user.getPromo_terms_accepted(), "Некорректное значение promo");
    }

    @CaseId(154)
    @Test(groups = {"api-instamart-regress"})
    @Story("Изменение пароля с невалидным новым")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdatePasswordWithInvalidNew() {
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.APIV2);
        final Response response = UsersRequest.PUT(
                session.getLogin(),
                session.getPassword(),
                "password",
                "password"
        );

        assertStatusCode422(response);
        final ErrorResponse errorResponse = response.as(ErrorResponse.class);
        Assert.assertEquals(errorResponse.getError_messages().get(0).getField(), "password", "Неверная ошибка");
        Assert.assertEquals(errorResponse.getError_messages().get(0).getMessage(), "Пароль не должен совпадать с вашим старым паролем", "Неверный текст ошибки");
        Assert.assertEquals(errorResponse.getError_messages().get(0).getHuman_message(), "Пароль не должен совпадать с вашим старым паролем", "Неверный текст ошибки");
    }

    @CaseId(153)
    @Test(groups = {"api-instamart-regress"})
    @Story("Изменение пароля с невалидным старым")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdatePasswordWithInvalidOld() {
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.APIV2);
        final Response response = UsersRequest.PUT(
                session.getLogin(),
                "invalid",
                "passw0rd",
                "passw0rd"
        );

        assertStatusCode200(response);
    }

    @CaseId(155)
    @Test(groups = {"api-instamart-regress"})
    @Story("Изменение пароля с невалидным проверочным")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdatePasswordWithInvalidConformation() {
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.APIV2);
        final Response response = UsersRequest.PUT(
                session.getLogin(),
                session.getPassword(),
                "password",
                "password"
        );

        assertStatusCode200(response);
    }

    @CaseId(152)
    @Test(groups = {"api-instamart-regress"})
    @Story("Изменение пароля")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdatePassword() {
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.APIV2);
        final Response response = UsersRequest.PUT(
                session.getLogin(),
                session.getPassword(),
                "passw0rd",
                "passw0rd"
        );
        assertStatusCode200(response);
        final User user = response.as(UserDataResponse.class).getUser();
        Assert.assertEquals(user.getEmail(), session.getLogin(), "Некорректная почта");
    }

    @Test(groups = {"api-instamart-regress"})
    @Story("Изменение одного поля пользователя")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateUserDataOneField() {
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.APIV2);
        final Response response = UsersRequest.PUT(
                session.getLogin(),
                "FirstName",
                null,
                false
        );
        assertStatusCode200(response);
        final User user = response.as(UserDataResponse.class).getUser();
        Assert.assertEquals(user.getEmail(), session.getLogin(), "Некорректная почта");
        Assert.assertEquals(user.getFirst_name(), "FirstName", "Некорректное имя");
        Assert.assertFalse(user.getPromo_terms_accepted(), "Некорректное значение promo");
    }

    @CaseId(157)
    @Issue("STF-7288")
    @Test(groups = {"api-instamart-regress"}, enabled = false)
    @Story("Попытка изменить данные для несуществующего email")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateUserDataWithIncorrectEmail() {
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.APIV2);
        final Response response = UsersRequest.PUT(
                "fake@mail.com",
                "FirstName",
                null,
                false
        );
        assertStatusCode404(response);
    }

    @CaseId(158)
    @Test(groups = {"api-instamart-regress"})
    @Story("Изменить данные для с подтверждением promo")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateUserDataWithPromoAccept() {
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.APIV2);
        final Response response = UsersRequest.PUT(
                session.getLogin(),
                "FirstName",
                null,
                true
        );
        assertStatusCode200(response);
    }

    @CaseId(159)
    @Test(groups = {"api-instamart-regress"})
    @Story("Получение данных пользователя")
    @Severity(SeverityLevel.NORMAL)
    public void testGetUserData() {
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.APIV2);
        final Response response = UsersRequest.GET(
                "InstamartApp",
                session.getLogin()
        );
        assertStatusCode200(response);
        final User user = response.as(UserDataResponse.class).getUser();
        Assert.assertEquals(user.getEmail(), session.getLogin(), "Некорректная почта");
        Assert.assertEquals(user.getFirst_name(), "autotest-user", "Некорректное имя");
    }

    @CaseId(558)
    @Test(groups = {"api-instamart-regress"})
    @Story("Попытка получить данные для несуществующего email")
    @Severity(SeverityLevel.NORMAL)
    public void testGetUserDataWithIncorrectEmail() {
        final Response response = UsersRequest.GET(
                "fake@mail.com",
                false
        );
        assertStatusCode404(response);
    }

    @CaseId(160)
    @Test(groups = {"api-instamart-regress"})
    @Story("Попытка получение расширенных данных пользователя с невалидным token")
    @Severity(SeverityLevel.NORMAL)
    public void testGetUserDataWithInvalidToken() {
        final SessionFactory.SessionInfo session = SessionFactory.getSession(SessionType.APIV2);
        final Response response = UsersRequest.GET(
                session.getLogin(),
                true,
                "123"

        );
        assertStatusCode404(response);
    }
}
