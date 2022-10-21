package ru.instamart.test.authorization_service;

import authorization.AuthorizationGrpc;
import authorization.AuthorizationOuterClass;
import io.grpc.StatusRuntimeException;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Epic("Сервис авторизации")
@Feature("Авторизация GRPC")
public class AuthorizationTest extends GrpcBase {

    private AuthorizationGrpc.AuthorizationBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        channel = grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_CORE_SERVICES_AUTHORIZATION);

        client = AuthorizationGrpc.newBlockingStub(channel);
    }

    @CaseId(29)
    @Test(description = "Авторизация через GRPC",
            groups = {"grpc-authorization-service"})
    public void authorizationGRPC() {
        var request = AuthorizationOuterClass.AuthorizedPermissionsRequest.newBuilder()
                .addPermissions("example-service/core-services/retailers:write")
                .addPermissions("example-service/core-services/retailers:read")
                .setSbmAuthIdentity("1")
                .addSbmAuthRoles("BizdevDept")
                .setSbmAuthType("core-services")
                .setPolicyVersion("test")
                .build();

        var response = client.authorizedPermissions(request);

        Allure.step("Авторизация прошла успешно", () -> {
            assertFalse(response.toString().isEmpty(),"Не получилось авторизоваться с верными правами доступа");
        });
    }

    @CaseId(30)
    @Test(description = "Авторизация через GRPC с неверными правами доступа",
            groups = {"grpc-authorization-service"})
    public void authorizationWrongPermissonsGRPC() {
        var request = AuthorizationOuterClass.AuthorizedPermissionsRequest.newBuilder()
                .addPermissions("example-service/core-services/retailers:wrong")
                .addPermissions("example-service/core-services/wrong:read")
                .setSbmAuthIdentity("1")
                .addSbmAuthRoles("BizdevDept")
                .setSbmAuthType("core-services")
                .setPolicyVersion("test")
                .build();

        var response = client.authorizedPermissions(request);

        Allure.step("Пользователя не авторизует с неверными правами доступа", () -> {
            assertTrue(response.toString().isEmpty(),"Получилось авторизоваться с неверными правами доступа");
        });
    }

    @CaseId(37)
    @Test(description = "Получение condition через GRPC DataFilters",
            groups = {"grpc-authorization-service"})
    public void dataFiltersGRPC() {
        var sbmAuth = AuthorizationOuterClass.SbmAuth.newBuilder()
                .setType("core-services")
                .setIdentity("1")
                .addRoles("BizdevDept")
                .build();

        var request = AuthorizationOuterClass.DataFiltersRequest.newBuilder()
                .setPermission("example-service/core-services/retailers:write")
                .setPermission("example-service/core-services/retailers:read")
                .setAuth(sbmAuth)
                .setPolicyVersion("test")
                .build();

        var response = client.dataFilters(request);

        final SoftAssert softAssert = new SoftAssert();
        Allure.step("Возвращается не пустое условие", () -> {
            softAssert.assertFalse(response.toString().isEmpty(),"Условие вернулось пустым");
        });
    }

    @CaseId(37)
    @Test(description = "Получение condition через GRPC DataFilters с пустым списком ролей",
            groups = {"grpc-authorization-service"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "PERMISSION_DENIED: пустой список ролей")
    public void dataFiltersGRPCnoRoles() {
        var sbmAuth = AuthorizationOuterClass.SbmAuth.newBuilder()
                .setType("core-services")
                .setIdentity("1")
                .build();

        var request = AuthorizationOuterClass.DataFiltersRequest.newBuilder()
                .setPermission("example-service/core-services/retailers:write")
                .setPermission("example-service/core-services/retailers:read")
                .setAuth(sbmAuth)
                .setPolicyVersion("test")
                .build();

        client.dataFilters(request);
    }
}
