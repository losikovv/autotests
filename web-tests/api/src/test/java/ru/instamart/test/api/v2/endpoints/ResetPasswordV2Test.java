package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.request.v2.ResetPasswordV2Request;

import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Восстановление пароля")
public final class ResetPasswordV2Test extends RestBase {

    @BeforeClass(alwaysRun = true)
    @Story("Создание сессии")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
    }

    // not implemented endpoint
    @CaseId(183)
    @Test(groups = {"api-instamart-regress"}, description = "Подстановка невалидного токена", enabled = false)
    public void testRestWithInvalidToken() {
        final Response response = ResetPasswordV2Request.POST(
                "token",
                "password",
                "password"
        );
        checkStatusCode200(response);
    }

    @CaseId(561)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Восстановление несуществующего аккаунта")
    public void testRestWithInvalidAccount() {
        final Response response = ResetPasswordV2Request.POST(
                "fake@mail.com"
        );

        checkStatusCode200(response);
    }

    // not implemented endpoint
    @CaseId(184)
    @Test(groups = {"api-instamart-regress"}, description = "Предупреждение при вводе старого пароля", enabled = false)
    public void testRestWithInvalidNewPassword() {
        final Response response = ResetPasswordV2Request.POST(
                "token",
                "  ",
                "password"
        );
        checkStatusCode200(response);
    }

    // not implemented endpoint
    @CaseId(185)
    @Test(groups = {"api-instamart-regress"}, description = "Предупреждение при вводе невалидного пароля", enabled = false)
    public void testRestWithInvalidPassword() {
        final Response response = ResetPasswordV2Request.POST(
                "token",
                "   ~",
                "   ~"
        );
        checkStatusCode200(response);
    }

    // not implemented endpoint
    @CaseId(186)
    @Test(groups = {"api-instamart-regress"}, description = "Предупреждение при вводе невалидного проверочного пароля", enabled = false)
    public void testRestWithInvalidConformationPassword() {
        final Response response = ResetPasswordV2Request.POST(
                "token",
                "password",
                ""
        );
        checkStatusCode200(response);
    }

    @CaseId(187)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Сброс пароля")
    public void testRestPassword() {
        final Response response = ResetPasswordV2Request.POST(
                SessionFactory.getSession(SessionType.API_V2).getLogin()
        );
        checkStatusCode200(response);
    }
}
