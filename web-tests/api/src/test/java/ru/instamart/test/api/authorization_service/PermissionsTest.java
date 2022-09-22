package ru.instamart.test.api.authorization_service;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.request.authorization_service.PermissionsRequest;
import ru.instamart.api.response.authorization_service.PermissionsResponse;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("Сервис авторизации")
@Feature("Пермишены")
public class PermissionsTest {
    @CaseId(11)
    @Test(groups = {"api-authorization-service"},
            description = "Получение списка прав доступа")
    public void getPermissions200() {

        Response response = PermissionsRequest.GET("core-services", "retailers");

        checkStatusCode200(response);
        checkResponseJsonSchema(response, PermissionsResponse.class);

    }

    @CaseId(12)
    @Test(groups = {"api-authorization-service"},
            description = "Получение списка прав доступа для неверного ресурса")
    public void getPermissionsWithWrongResource200() {

        Response response = PermissionsRequest.GET("core-services", "wrong");

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

        Response response = PermissionsRequest.GET("core-services", "retailers", "wrong");

        checkStatusCode200(response);
        checkResponseJsonSchema(response, PermissionsResponse.class);

        Allure.step("У несуществующей роли не будет прав", () -> {
            assertTrue(response.as(PermissionsResponse.class).getData().isEmpty(), "Ответ не пустой. Предоставлены права для несуществующей роли");
        });
    }

}
