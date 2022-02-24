package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v1.MainNavigationV1Request;
import ru.instamart.api.response.v1.MainNavigationV1Response;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkErrorText;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode401;

@Epic("ApiV1")
@Feature("Навигация админки")
public class MainNavigationV1Test extends RestBase {

    @CaseId(1830)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получение информации о страницах в админке")
    public void getMainNavigation() {
        admin.authApi();
        final Response response = MainNavigationV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, MainNavigationV1Response.class);
    }

    @CaseId(1831)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получение информации о страницах в админке")
    public void getMainNavigationWithoutAuth() {
        SessionFactory.clearSession(SessionType.API_V1);
        final Response response = MainNavigationV1Request.GET();
        checkStatusCode401(response);
        checkErrorText(response, "Должен быть указан ключ API");
    }
}
