package ru.instamart.test.api.authorization_service;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.helper.AuthorizationServiceHelper;
import ru.instamart.api.model.authorization_service.PolicyModel;
import ru.instamart.api.request.authorization_service.PolicyRequest;
import ru.instamart.api.response.authorization_service.PolicyErrorResponse;
import ru.instamart.api.response.authorization_service.PolicyResponse;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("Сервис авторизации")
@Feature("Политики")
public class PolicyTest extends RestBase {

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.createSessionToken(SessionType.AUTHORIZATION_SERVICE, UserManager.getAuthorizationServiceKeycloakClient());
    }

    @CaseId(1)
    @Test(groups = {"api-authorization-service"},
            description = "Создание политики")
    public void putPolicy200() {

        Response response = PolicyRequest.PUT("core-services", AuthorizationServiceHelper.getInitialPolicy());

        checkStatusCode(response, 200);

        Response responseGet = PolicyRequest.GET("core-services");

        Allure.step("Проверяем, что политика обновилась", () -> {
            assertEquals(responseGet.as(PolicyResponse.class).getData().getRoles().get(0).getName(),
                    "BizdevDept", "Список ролей не обновился");
            assertEquals(responseGet.as(PolicyResponse.class).getData().getServiceSpecs().get(0).getServiceId(),
                    "example-service", "Список разрешений не обновился");
        });
    }

    @CaseId(3)
    @Test(groups = {"api-authorization-service"},
            description = "Создание политики. DryRun = true")
    public void getPolicyDryRunTrue200() {

        PolicyModel policy = PolicyModel
                .builder()
                .serviceSpec(PolicyModel.ServiceSpec
                        .builder()
                        .serviceId("example-service")
                        .permission(PolicyModel.Permission
                                .builder()
                                .name("core-services/retailers")
                                .description("test - dryRun - true")
                                .access("read")
                                .access("write")
                                .build())
                        .build())
                .role(PolicyModel.Role
                        .builder()
                        .name("BizdevDept")
                        .permission(PolicyModel.RolePermission
                                .builder()
                                .permission("example-service/core-services/retailers:read")
                                .build())
                        .permission(PolicyModel.RolePermission
                                .builder()
                                .permission("example-service/core-services/retailers:write")
                                .build())
                        .build())
                .build();

        Response response = PolicyRequest.PUT("core-services", true, policy);

        checkStatusCode(response, 200);

        Response responseGet = PolicyRequest.GET("core-services");

        Allure.step("Проверяем, что политика не обновилась", () -> {
            assertNotEquals(responseGet.as(PolicyResponse.class).getData().getServiceSpecs().get(0)
                    .getPermissions().get(0).getDescription(), "test - dryRun - true", "Политика обновилась при dryRun = true");
        });
    }

    @CaseId(4)
    @Test(groups = {"api-authorization-service"},
            description = "Создание политики. DryRun = false")
    public void getPolicyDryRunFalse200() {
        PolicyModel policy = PolicyModel
                .builder()
                .serviceSpec(PolicyModel.ServiceSpec
                        .builder()
                        .serviceId("example-service")
                        .permission(PolicyModel.Permission
                                .builder()
                                .name("core-services/retailers")
                                .description("test - dryRun - false")
                                .access("read")
                                .access("write")
                                .build())
                        .build())
                .role(PolicyModel.Role
                        .builder()
                        .name("BizdevDept")
                        .permission(PolicyModel.RolePermission
                                .builder()
                                .permission("example-service/core-services/retailers:read")
                                .build())
                        .permission(PolicyModel.RolePermission
                                .builder()
                                .permission("example-service/core-services/retailers:write")
                                .build())
                        .build())
                .build();
        Response response = PolicyRequest.PUT("core-services", false, policy);

        checkStatusCode(response, 200);

        Response responseGet = PolicyRequest.GET("core-services");

        Allure.step("Проверяем, что политика обновилась", () -> {
            assertEquals(responseGet.as(PolicyResponse.class).getData().getServiceSpecs().get(0)
                    .getPermissions().get(0).getDescription(), "test - dryRun - false", "Политика не обновилась при dryRun = false");
        });
    }

    @CaseId(5)
    @Test(groups = {"api-authorization-service"},
            description = "Создание политики с неверным сервисом")
    public void getPolicyWithWrongService422() {

        PolicyModel policy = PolicyModel
                .builder()
                .serviceSpec(PolicyModel.ServiceSpec
                        .builder()
                        .serviceId("wrong")
                        .permission(PolicyModel.Permission
                                .builder()
                                .name("admin/retailers")
                                .description("negative test")
                                .access("read")
                                .access("write")
                                .build())
                        .build())
                .role(PolicyModel.Role
                        .builder()
                        .name("wrong")
                        .permission(PolicyModel.RolePermission
                                .builder()
                                .permission("stf/admin/retailers:read")
                                .build())
                        .build())
                .build();

        Response response = PolicyRequest.PUT("core-services", policy);

        checkStatusCode422(response);
        checkResponseJsonSchema(response, PolicyErrorResponse.class);

        final SoftAssert softAssert = new SoftAssert();
        Allure.step("Проверяем, что политика не добавилась по соответствующей причине", () -> {
            softAssert.assertEquals(response.as(PolicyErrorResponse.class).getError().getDetail(), "\\\"wrong\\\" service not registered", "Сообщение об ошибке не соответствует тесту");
        });

    }

    @CaseId(35)
    @Test(groups = {"api-authorization-service"},
            description = "Создание политики с некорректным access type")
    public void getPolicyWithWrongAccessType422() {

        PolicyModel policy = PolicyModel
                .builder()
                .serviceSpec(PolicyModel.ServiceSpec
                        .builder()
                        .serviceId("example-service")
                        .permission(PolicyModel.Permission
                                .builder()
                                .name("core-services/retailers")
                                .description("negative test")
                                .access("asd")
                                .build())
                        .build())
                .role(PolicyModel.Role
                        .builder()
                        .name("BizdevDept")
                        .permission(PolicyModel.RolePermission
                                .builder()
                                .permission("example-service/core-services/retailers:read")
                                .build())
                        .build())
                .build();

        Response response = PolicyRequest.PUT("core-services", policy);

        checkStatusCode422(response);
        checkResponseJsonSchema(response, PolicyErrorResponse.class);

        final SoftAssert softAssert = new SoftAssert();
        Allure.step("Проверяем, что политика не добавилась по соответствующей причине", () -> {
            softAssert.assertEquals(response.as(PolicyErrorResponse.class).getError().getTitle(), "unexpected permission example-service/core-services/retailers:read", "Сообщение об ошибке не соответствует тесту");
        });
    }

    @CaseId(36)
    @Test(groups = {"api-authorization-service"},
            description = "Создание политики с дублирующимися ролями")
    public void getPolicyWithDuplicatedRoles422() {

        PolicyModel policy = PolicyModel
                .builder()
                .serviceSpec(PolicyModel.ServiceSpec
                        .builder()
                        .serviceId("example-service")
                        .permission(PolicyModel.Permission
                                .builder()
                                .name("core-services/retailers")
                                .description("negative test")
                                .access("read")
                                .build())
                        .build())
                .role(PolicyModel.Role
                        .builder()
                        .name("Test")
                        .permission(PolicyModel.RolePermission
                                .builder()
                                .permission("example-service/core-services/retailers:read")
                                .build())
                        .build())
                .role(PolicyModel.Role
                        .builder()
                        .name("Test")
                        .permission(PolicyModel.RolePermission
                                .builder()
                                .permission("example-service/core-services/retailers:read")
                                .build())
                        .build())
                .build();

        Response response = PolicyRequest.PUT("core-services", policy);

        checkStatusCode422(response);
        checkResponseJsonSchema(response, PolicyErrorResponse.class);

        final SoftAssert softAssert = new SoftAssert();
        Allure.step("Проверяем, что политика не добавилась по соответствующей причине", () -> {
            softAssert.assertEquals(response.as(PolicyErrorResponse.class).getError().getDetail(), "дублирующиеся имена ролей Test", "Сообщение об ошибке не соответствует тесту");
        });
    }

    @CaseId(6)
    @Test(groups = {"api-authorization-service"},
            description = "Получение списка политик")
    public void getPolicy200() {
        Response response = PolicyRequest.GET("core-services");

        checkStatusCode200(response);
        checkResponseJsonSchema(response, PolicyResponse.class);
    }

    @CaseId(7)
    @Test(groups = {"api-authorization-service"},
            description = "Получение списка политик с неверным realm")
    public void getPolicyWithWrongRealm200() {
        Response response = PolicyRequest.GET("wrong");

        checkStatusCode422(response);
        checkResponseJsonSchema(response, PolicyErrorResponse.class);

        final SoftAssert softAssert = new SoftAssert();
        Allure.step("Проверяем сообщение об ошибке", () -> {
            softAssert.assertEquals(response.as(PolicyErrorResponse.class).getError().getTitle(), "\\\"wrong\\\" realm not registered", "Сообщение об ошибке не соответствует тесту");
        });
    }

    @CaseId(8)
    @Test(groups = {"api-authorization-service"},
            description = "Обновление политики с пустыми списками ролей и прав доступа")
    public void updatePolicyWithEmptyRolesAndPermissions200() {

        PolicyModel policy = PolicyModel
                .builder()
                .serviceSpec(PolicyModel.ServiceSpec
                        .builder()
                        .serviceId("example-service")
                        .build())
                .build();

        Response response = PolicyRequest.PUT("core-services", policy);
        checkStatusCode(response, 200);

        PolicyResponse responseGet = PolicyRequest.GET("core-services").as(PolicyResponse.class);

        Allure.step("Проверяем, что политика обновилась, роли и разрешения пустые", () -> {
            assertTrue(responseGet.getData().getRoles().isEmpty(),
                    "Список ролей не пустой, а значит не обновился");
            assertTrue(responseGet.getData().getServiceSpecs().isEmpty(),
                    "Список разрешений не пустой, а значит не обновился");
        });

    }

    @CaseId(9)
    @Test(groups = {"api-authorization-service"},
            description = "Обновление политики с пустым списком ролей")
    public void updatePolicyWithEmptyRoles200() {

        PolicyModel policy = PolicyModel
                .builder()
                .serviceSpec(PolicyModel.ServiceSpec
                        .builder()
                        .serviceId("example-service")
                        .permission(PolicyModel.Permission
                                .builder()
                                .name("core-services/retailers")
                                .description("test")
                                .access("read")
                                .access("write")
                                .build())
                        .permission(PolicyModel.Permission
                                .builder()
                                .name("core-services/retailers.stores")
                                .description("test")
                                .access("read")
                                .access("write")
                                .build())
                        .permission(PolicyModel.Permission
                                .builder()
                                .name("core-services/shipments")
                                .description("test")
                                .access("read")
                                .build())
                        .build())
                .build();

        Response response = PolicyRequest.PUT("core-services", policy);
        checkStatusCode(response, 200);

        PolicyResponse responseGet = PolicyRequest.GET("core-services").as(PolicyResponse.class);

        Allure.step("Проверяем, что политика обновилась. Роли пустые а разрешения нет", () -> {
            assertTrue(responseGet.getData().getRoles().isEmpty(),
                    "Список ролей не пустой, а значит не обновился");
            assertFalse(responseGet.getData().getServiceSpecs().isEmpty(),
                    "Список разрешений пустой, а значит не обновился");
        });

    }

    @CaseId(10)
    @Test(groups = {"api-authorization-service"},
            description = "Обновление политики с пустым списком прав доступа")
    public void updatePolicyWithEmptyPermissions200() {

        PolicyModel policy = PolicyModel
                .builder()
                .serviceSpec(PolicyModel.ServiceSpec
                        .builder()
                        .serviceId("example-service")
                        .build())
                .role(PolicyModel.Role
                        .builder()
                        .name("BizdevDept")
                        .build())
                .role(PolicyModel.Role
                        .builder()
                        .name("CallcenterDept")
                        .build())
                .role(PolicyModel.Role
                        .builder()
                        .name("OperationsDept")
                        .build())
                .build();

        Response response = PolicyRequest.PUT("core-services", policy);
        checkStatusCode(response, 200);

        PolicyResponse responseGet = PolicyRequest.GET("core-services").as(PolicyResponse.class);

        Allure.step("Проверяем, что политика обновилась. Разрешения пустые, а роли нет", () -> {
            assertTrue(responseGet.getData().getServiceSpecs().isEmpty(),
                    "Список разрешений не пустой, а значит не обновился");
            assertFalse(responseGet.getData().getRoles().get(0).getName().isEmpty(),
                    "Список ролей пустой, а значит не обновился");
        });

    }

    @CaseId(39)
    @Test(groups = {"api-authorization-service"},
            description = "Добавление условия с типом Date")
    public void updatePolicyWithDateCondition200() {

        PolicyModel policy = PolicyModel
                .builder()
                .serviceSpec(PolicyModel.ServiceSpec
                        .builder()
                        .serviceId("test")
                        .permission(PolicyModel.Permission
                                .builder()
                                .name("core-services/shipments")
                                .description("date test")
                                .access("read")
                                .access("write")
                                .schema(PolicyModel.Schema
                                        .builder()
                                        .properties(PolicyModel.Properties
                                                .builder()
                                                .additionalProp(PolicyModel.AdditionalProp
                                                        .builder()
                                                        .type("date")
                                                        .build())
                                                .build())
                                        .build())
                                .build())
                        .build())
                .role(PolicyModel.Role
                        .builder()
                        .name("CallcenterDept")
                        .permission(PolicyModel.RolePermission
                                .builder()
                                .permission("test/core-services/shipments:read")
                                .condition("{\"shipments.additionalProp\":{\"$eq\":\"359.days.ago\"}}")
                                .build())
                        .build())
                .build();

        Response response = PolicyRequest.PUT("core-services", policy);
        checkStatusCode(response, 200);

    }

    @CaseId(40)
    @Test(groups = {"api-authorization-service"},
            description = "Проверка валидации соответствия типов атрибутов при сохранении условий")
    public void checkAttributeTypeValidation() {

        PolicyModel policy = PolicyModel
                .builder()
                .serviceSpec(PolicyModel.ServiceSpec
                        .builder()
                        .serviceId("test")
                        .permission(PolicyModel.Permission
                                .builder()
                                .name("core-services/shipments")
                                .description("date test")
                                .access("read")
                                .access("write")
                                .schema(PolicyModel.Schema
                                        .builder()
                                        .properties(PolicyModel.Properties
                                                .builder()
                                                .additionalProp(PolicyModel.AdditionalProp
                                                        .builder()
                                                        .type("integer")
                                                        .build())
                                                .build())
                                        .build())
                                .build())
                        .build())
                .role(PolicyModel.Role
                        .builder()
                        .name("CallcenterDept")
                        .permission(PolicyModel.RolePermission
                                .builder()
                                .permission("test/core-services/shipments:read")
                                .condition("{\"shipments.additionalProp\":{\"$eq\":\"123string\"}}")
                                .build())
                        .build())
                .build();

        Response response = PolicyRequest.PUT("core-services", policy);
        checkStatusCode(response, 422);

    }
}
