package ru.instamart.tests.api.v2.endpoints;

import instamart.api.SessionFactory;
import instamart.api.common.RestBase;
import instamart.api.enums.SessionType;
import instamart.api.requests.v2.ResetPasswordRequest;
import io.qameta.allure.*;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Восстановление пароля")
public final class ResetPasswordV2Test extends RestBase {

    @BeforeClass(alwaysRun = true)
    @Story("Создание сессии")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.APIV2);
    }

    // not implemented endpoint
    @CaseId(183)
    @Test(groups = {"api-instamart-regress"}, enabled = false)
    @Story("Подстановка невалидного токена")
    @Severity(SeverityLevel.NORMAL)
    public void testRestWithInvalidToken() {
        final Response response = ResetPasswordRequest.POST(
                "token",
                "password",
                "password"
        );
        checkStatusCode200(response);
    }

    @CaseId(561)
    @Test(groups = {"api-instamart-regress"})
    @Story("Восстановление несуществующего аккаунта")
    @Severity(SeverityLevel.NORMAL)
    public void testRestWithInvalidAccount() {
        final Response response = ResetPasswordRequest.POST(
                "fake@mail.com"
        );

        checkStatusCode200(response);
    }

    // not implemented endpoint
    @CaseId(184)
    @Test(groups = {"api-instamart-regress"}, enabled = false)
    @Story("Предупреждение при вводе старого пароля")
    @Severity(SeverityLevel.NORMAL)
    public void testRestWithInvalidNewPassword() {
        final Response response = ResetPasswordRequest.POST(
                "token",
                "  ",
                "password"
        );
        checkStatusCode200(response);
    }

    // not implemented endpoint
    @CaseId(185)
    @Test(groups = {"api-instamart-regress"}, enabled = false)
    @Story("Предупреждение при вводе невалидного пароля")
    @Severity(SeverityLevel.NORMAL)
    public void testRestWithInvalidPassword() {
        final Response response = ResetPasswordRequest.POST(
                "token",
                "   ~",
                "   ~"
        );
        checkStatusCode200(response);
    }

    // not implemented endpoint
    @CaseId(186)
    @Test(groups = {"api-instamart-regress"}, enabled = false)
    @Story("Предупреждение при вводе невалидного проверочного пароля")
    @Severity(SeverityLevel.NORMAL)
    public void testRestWithInvalidConformationPassword() {
        final Response response = ResetPasswordRequest.POST(
                "token",
                "password",
                ""
        );
        checkStatusCode200(response);
    }

    @CaseId(187)
    @Test(groups = {"api-instamart-regress"})
    @Story("Сброс пароля")
    @Severity(SeverityLevel.NORMAL)
    public void testRestPassword() {
        final Response response = ResetPasswordRequest.POST(
                SessionFactory.getSession(SessionType.APIV2).getLogin()
        );
        checkStatusCode200(response);
    }
}
