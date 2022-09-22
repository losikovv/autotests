package ru.instamart.test.api.authorization_service;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.helper.AuthorizationServiceHelper;
import ru.instamart.api.request.authorization_service.AuthorizationRequest;
import ru.instamart.api.request.authorization_service.PolicyRequest;
import ru.instamart.api.response.authorization_service.AuthorizationResponse;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("Сервис авторизации")
@Feature("Авторизация")
public class AuthorizationTest {

    @BeforeClass(alwaysRun = true)
    public void precondition() {
        PolicyRequest.PUT("core-services", AuthorizationServiceHelper.getInitialPolicy());
    }

    @CaseId(17)
    @Test(groups = {"api-authorization-service"},
            description = "Авторизация")
    public void postAuthorization200() {

        JSONObject body = new JSONObject();
        JSONArray permissions = new JSONArray();
        permissions.add("example-service/core-services/retailers:write");
        permissions.add("example-service/core-services/retailers:read");
        body.put("permissions", permissions);
        Response response = AuthorizationRequest.POST("core-services", body);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, AuthorizationResponse.class);
        Allure.step("Авторизация прошла успешно", () -> {
            assertFalse(response.as(AuthorizationResponse.class).getData().isEmpty(), "Не получилось авторизоваться с верными правами доступа");
        });

    }

    @CaseId(18)
    @Test(groups = {"api-authorization-service"},
            description = "Авторизация с неверными правами доступа")
    public void postAuthorizationWrongPermissons() {

        JSONObject body = new JSONObject();
        JSONArray permissions = new JSONArray();
        permissions.add("stf/admin/retailers:test");
        permissions.add("stf/admin/retailers:wrong");
        body.put("permissions", permissions);
        Response response = AuthorizationRequest.POST("core-services", body);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, AuthorizationResponse.class);
        Allure.step("Пользователя не авторизует с неверными правами доступа", () -> {
            assertTrue(response.as(AuthorizationResponse.class).getData().isEmpty(), "Получилось авторизоваться с неверными правами доступа");
        });
    }
}


