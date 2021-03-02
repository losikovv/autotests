package ru.instamart.tests.api.v2.endpoints;

import instamart.api.SessionFactory;
import instamart.api.action.ResetPassword;
import instamart.api.common.RestBase;
import io.qameta.allure.*;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.assertStatusCode200;

@Epic("ApiV2")
@Feature("Восстановление пароля")
public final class ResetPasswordTest extends RestBase {

    @BeforeClass(alwaysRun = true)
    @Story("Создание сессии")
    public void preconditions() {
        SessionFactory.makeSession();
    }

    // not implemented endpoint
    @CaseId(183)
    @Test(groups = {"api-instamart-regress"}, enabled = false)
    @Story("Подстановка невалидного токена")
    @Severity(SeverityLevel.NORMAL)
    public void testRestWithInvalidToken() {
        final Response response = ResetPassword.POST(
                "token",
                "password",
                "password"
        );
        assertStatusCode200(response);
    }

    @CaseId(561)
    @Test(groups = {"api-instamart-regress"})
    @Story("Восстановление несуществующего аккаунта")
    @Severity(SeverityLevel.NORMAL)
    public void testRestWithInvalidAccount() {
        final Response response = ResetPassword.POST(
                "fake@mail.com"
        );

        assertStatusCode200(response);
    }

    // not implemented endpoint
    @CaseId(184)
    @Test(groups = {"api-instamart-regress"}, enabled = false)
    @Story("Предупреждение при вводе старого пароля")
    @Severity(SeverityLevel.NORMAL)
    public void testRestWithInvalidNewPassword() {
        final Response response = ResetPassword.POST(
                "token",
                "  ",
                "password"
        );
        assertStatusCode200(response);
    }

    // not implemented endpoint
    @CaseId(185)
    @Test(groups = {"api-instamart-regress"}, enabled = false)
    @Story("Предупреждение при вводе невалидного пароля")
    @Severity(SeverityLevel.NORMAL)
    public void testRestWithInvalidPassword() {
        final Response response = ResetPassword.POST(
                "token",
                "   ~",
                "   ~"
        );
        assertStatusCode200(response);
    }

    // not implemented endpoint
    @CaseId(186)
    @Test(groups = {"api-instamart-regress"}, enabled = false)
    @Story("Предупреждение при вводе невалидного проверочного пароля")
    @Severity(SeverityLevel.NORMAL)
    public void testRestWithInvalidConformationPassword() {
        final Response response = ResetPassword.POST(
                "token",
                "password",
                ""
        );
        assertStatusCode200(response);
    }

    @CaseId(187)
    @Test(groups = {"api-instamart-regress"})
    @Story("Сброс пароля")
    @Severity(SeverityLevel.NORMAL)
    public void testRestPassword() {
        final Response response = ResetPassword.POST(
                SessionFactory.getSession().getLogin()
        );
        assertStatusCode200(response);
    }
}
