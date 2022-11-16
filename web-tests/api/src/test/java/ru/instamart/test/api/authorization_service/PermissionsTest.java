package ru.instamart.test.api.authorization_service;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.helper.AuthorizationServiceHelper;
import ru.instamart.api.request.authorization_service.PermissionsRequest;
import ru.instamart.api.request.authorization_service.RealmRequest;
import ru.instamart.api.response.authorization_service.PermissionsResponse;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("Сервис авторизации")
@Feature("Пермишены")
public class PermissionsTest {

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        Response response = RealmRequest.POST(AuthorizationServiceHelper.getInitialRealm());
    }

    @CaseId(11)
    @Test(groups = {"api-authorization-service"},
            description = "Получение списка прав доступа")
    public void getPermissions200() {

        Response response = PermissionsRequest.GET("kraken-api-tests", "retailers");

        checkStatusCode200(response);
        checkResponseJsonSchema(response, PermissionsResponse.class);

    }

    @CaseId(12)
    @Test(groups = {"api-authorization-service"},
            description = "Получение списка прав доступа для неверного ресурса")
    public void getPermissionsWithWrongResource200() {

        Response response = PermissionsRequest.GET("kraken-api-tests", "wrong");

        checkStatusCode200(response);
        checkResponseJsonSchema(response, PermissionsResponse.class);

        Allure.step("У несуществующего ресурса не будет прав", () -> {
            assertTrue(response.as(PermissionsResponse.class).getData().isEmpty(), "Ответ не пустой. Предоставлены права для несуществующего ресурса");
        });
    }

    @CaseId(13)
    @Test(groups = {"api-authorization-service"},
            description = "Получение списка прав доступа для неверной роли")
    public void getPermissionsWithWrongRole200() {

        Response response = PermissionsRequest.GET("kraken-api-tests", "retailers", "wrong");

        checkStatusCode200(response);
        checkResponseJsonSchema(response, PermissionsResponse.class);

        Allure.step("У несуществующей роли не будет прав", () -> {
            assertTrue(response.as(PermissionsResponse.class).getData().isEmpty(), "Ответ не пустой. Предоставлены права для несуществующей роли");
        });
    }

}
