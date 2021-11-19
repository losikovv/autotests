package ru.instamart.test.api.v2.deprecated;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.ResetPasswordV2Request;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Восстановление пароля")
@Deprecated
public final class ResetPasswordV2Test extends RestBase {

    @BeforeClass(alwaysRun = true)
    @Story("Создание сессии")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
    }

    @Deprecated
    @Test(groups = {}, description = "Подстановка невалидного токена")
    public void testRestWithInvalidToken() {
        final Response response = ResetPasswordV2Request.POST(
                "token",
                "password",
                "password"
        );
        checkStatusCode200(response);
    }

    @Deprecated
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Восстановление несуществующего аккаунта")
    public void testRestWithInvalidAccount() {
        final Response response = ResetPasswordV2Request.POST(
                "fake@mail.com"
        );

        checkStatusCode200(response);
    }

    // not implemented endpoint
    @Deprecated
    @Test(groups = {}, description = "Предупреждение при вводе старого пароля")
    public void testRestWithInvalidNewPassword() {
        final Response response = ResetPasswordV2Request.POST(
                "token",
                "  ",
                "password"
        );
        checkStatusCode200(response);
    }

    // not implemented endpoint
    @Deprecated
    @Test(groups = {}, description = "Предупреждение при вводе невалидного пароля")
    public void testRestWithInvalidPassword() {
        final Response response = ResetPasswordV2Request.POST(
                "token",
                "   ~",
                "   ~"
        );
        checkStatusCode200(response);
    }

    // not implemented endpoint
    @Deprecated
    @Test(groups = {}, description = "Предупреждение при вводе невалидного проверочного пароля")
    public void testRestWithInvalidConformationPassword() {
        final Response response = ResetPasswordV2Request.POST(
                "token",
                "password",
                ""
        );
        checkStatusCode200(response);
    }

    @Deprecated
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Сброс пароля")
    public void testRestPassword() {
        final Response response = ResetPasswordV2Request.POST(
                SessionFactory.getSession(SessionType.API_V2).getLogin()
        );
        checkStatusCode200(response);
    }
}
