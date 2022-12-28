package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v1.UserPermissionsV1Request;
import ru.instamart.api.response.v1.UserPermissionsV1Response;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.Group.API_INSTAMART_PROD;
import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkErrorText;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode401;

@Epic("ApiV1")
@Feature("Доступы пользователя")
public class UserPermissionsV1Test extends RestBase {

    @CaseId(1832)
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"},
            description = "Получение информации о страницах в админке")
    public void getUserPermissions() {
        admin.authApi();
        final Response response = UserPermissionsV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, UserPermissionsV1Response.class);
    }

    @CaseId(1833)
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"},
            description = "Получение информации о страницах в админке")
    public void getUserPermissionsWithoutAuth() {
        SessionFactory.clearSession(SessionType.API_V1);
        final Response response = UserPermissionsV1Request.GET();
        checkStatusCode401(response);
        checkErrorText(response, "Вам требуется войти или зарегистрироваться перед тем, как продолжить.");
    }
}
