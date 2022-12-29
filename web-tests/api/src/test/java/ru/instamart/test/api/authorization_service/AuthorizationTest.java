package ru.instamart.test.api.authorization_service;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.helper.AuthorizationServiceHelper;
import ru.instamart.api.request.authorization_service.AuthorizationRequest;
import ru.instamart.api.request.authorization_service.PolicyRequest;
import ru.instamart.api.request.authorization_service.RealmRequest;
import ru.instamart.api.response.authorization_service.AuthorizationResponse;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("Сервис авторизации")
@Feature("Авторизация")
public class AuthorizationTest {

    @BeforeClass(alwaysRun = true)
    public void precondition() {
        PolicyRequest.PUT("kraken-api-tests", AuthorizationServiceHelper.getInitialPolicy());
        Response response = RealmRequest.POST(AuthorizationServiceHelper.getInitialRealm());
    }

    @TmsLink("17")
    @Test(groups = {"api-authorization-service"},
            description = "Авторизация")
    public void postAuthorization200() {

        JSONObject body = new JSONObject();
        JSONArray permissions = new JSONArray();
        permissions.add("example-service/kraken-api-tests/retailers:write");
        permissions.add("example-service/kraken-api-tests/retailers:read");
        body.put("permissions", permissions);
        Response response = AuthorizationRequest.POST("kraken-api-tests", body);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, AuthorizationResponse.class);
        Allure.step("Авторизация прошла успешно", () -> {
            assertFalse(response.as(AuthorizationResponse.class).getData().isEmpty(), "Не получилось авторизоваться с верными правами доступа");
        });

    }

    @TmsLink("18")
    @Test(groups = {"api-authorization-service"},
            description = "Авторизация с неверными правами доступа")
    public void postAuthorizationWrongPermissons() {

        JSONObject body = new JSONObject();
        JSONArray permissions = new JSONArray();
        permissions.add("stf/admin/retailers:test");
        permissions.add("stf/admin/retailers:wrong");
        body.put("permissions", permissions);
        Response response = AuthorizationRequest.POST("kraken-api-tests", body);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, AuthorizationResponse.class);
        Allure.step("Пользователя не авторизует с неверными правами доступа", () -> {
            assertTrue(response.as(AuthorizationResponse.class).getData().isEmpty(), "Получилось авторизоваться с неверными правами доступа");
        });
    }
}


