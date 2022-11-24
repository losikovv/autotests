package ru.instamart.api.helper;

import io.qameta.allure.Step;
import ru.instamart.api.model.authorization_service.PolicyModel;
import ru.instamart.api.model.authorization_service.RealmModel;

public class AuthorizationServiceHelper {

    @Step("Подготавливаем запрос для приведения политики в исходное состояние")
    public static PolicyModel getInitialPolicy() {
        PolicyModel policy = PolicyModel
                .builder()
                .serviceSpec(PolicyModel.ServiceSpec
                        .builder()
                        .serviceId("example-service")
                        .permission(PolicyModel.Permission
                                .builder()
                                .name("kraken-api-tests/retailers")
                                .description("test")
                                .access("read")
                                .access("write")
                                .build())
                        .permission(PolicyModel.Permission
                                .builder()
                                .name("kraken-api-tests/retailers.stores")
                                .description("test")
                                .access("read")
                                .access("write")
                                .build())
                        .permission(PolicyModel.Permission
                                .builder()
                                .name("kraken-api-tests/shipments")
                                .description("test")
                                .access("read")
                                .build())
                        .build())
                .role(PolicyModel.Role
                        .builder()
                        .name("bizdev_dept")
                        .permission(PolicyModel.RolePermission
                                .builder()
                                .permission("example-service/kraken-api-tests/retailers:read")
                                .build())
                        .permission(PolicyModel.RolePermission
                                .builder()
                                .permission("example-service/kraken-api-tests/retailers:write")
                                .build())
                        .permission(PolicyModel.RolePermission
                                .builder()
                                .permission("example-service/kraken-api-tests/retailers.stores:read")
                                .build())
                        .permission(PolicyModel.RolePermission
                                .builder()
                                .permission("example-service/kraken-api-tests/retailers.stores:write")
                                .build())
                        .build())
                .role(PolicyModel.Role
                        .builder()
                        .name("callcenter_dept")
                        .permission(PolicyModel.RolePermission
                                .builder()
                                .permission("example-service/kraken-api-tests/shipments:read")
                                .condition("")
                                .build())
                        .build())
                .role(PolicyModel.Role
                        .builder()
                        .name("operations_dept")
                        .permission(PolicyModel.RolePermission
                                .builder()
                                .permission("example-service/kraken-api-tests/shipments:read")
                                .condition("")
                                .build())
                        .build())
                .build();

        return policy;
    }

    @Step("Подготавливаем запрос для создания рилма")
    public static RealmModel getInitialRealm() {
        RealmModel realm = RealmModel
                .builder()
                .name("kraken-api-tests")
                .repositoryUrl("gitlab.sbmt.io/paas/content/kraken-api-tests/example-service")
                .service(RealmModel.Service
                        .builder()
                        .name("example-service")
                        .description("example-service description")
                        .build())
                .build();

        return realm;
    }
}
