package ru.instamart.tests.api.v2.endpoints;

import instamart.api.SessionFactory;
import instamart.api.action.Users;
import instamart.api.common.RestBase;
import instamart.api.objects.v2.User;
import instamart.api.responses.v2.UserDataResponse;
import io.qameta.allure.*;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.assertStatusCode200;
import static instamart.api.checkpoints.InstamartApiCheckpoints.assertStatusCode404;

@Epic("ApiV2")
@Feature("Данные пользователя")
public final class UserTest extends RestBase {

    @BeforeClass(alwaysRun = true)
    public void precondition() {
        SessionFactory.makeSession();
    }

    @CaseId(150)
    @Test(groups = {"api-instamart-regress"})
    @Story("Изменение данных пользователя")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateUserDataAllField() {
        final SessionFactory.Session session = SessionFactory.getSession();
        final Response response = Users.PUT(
                session.getToken(),
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

    @Test(groups = {"api-instamart-regress"})
    @Story("Изменение одного поля пользователя")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateUserDataOneField() {
        final SessionFactory.Session session = SessionFactory.getSession();
        final Response response = Users.PUT(
                session.getToken(),
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
    @Test(groups = {"api-instamart-regress"})
    @Story("Попытка изменить данные для несуществующего email")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateUserDataWithIncorrectEmail() {
        final SessionFactory.Session session = SessionFactory.getSession();
        final Response response = Users.PUT(
                session.getToken(),
                "fake@mail.com",
                "FirstName",
                null,
                false
        );
        assertStatusCode404(response);
    }

    @CaseId(159)
    @Test(groups = {"api-instamart-regress"})
    @Story("Получение данных пользователя")
    @Severity(SeverityLevel.NORMAL)
    public void testGetUserData() {
        final SessionFactory.Session session = SessionFactory.getSession();
        final Response response = Users.GET(
                session.getToken(),
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
        final Response response = Users.GET(
                "fake@mail.com"
        );
        assertStatusCode404(response);
    }

    @CaseId(160)
    @Test(groups = {"api-instamart-regress"})
    @Story("Попытка получение расширенных данных пользователя с невалидным token")
    @Severity(SeverityLevel.NORMAL)
    public void testGetUserDataWithInvalidToken() {
        final SessionFactory.Session session = SessionFactory.getSession();
        final Response response = Users.GET(
               "123",
                session.getLogin()
        );
        assertStatusCode404(response);
    }
}
