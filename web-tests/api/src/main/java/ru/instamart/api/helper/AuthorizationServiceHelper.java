package ru.instamart.api.helper;

import io.qameta.allure.Step;
import ru.instamart.api.model.authorization_service.PolicyModel;

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
                        .permission(PolicyModel.RolePermission
                                .builder()
                                .permission("example-service/core-services/retailers.stores:read")
                                .build())
                        .permission(PolicyModel.RolePermission
                                .builder()
                                .permission("example-service/core-services/retailers.stores:write")
                                .build())
                        .build())
                .role(PolicyModel.Role
                        .builder()
                        .name("CallcenterDept")
                        .permission(PolicyModel.RolePermission
                                .builder()
                                .permission("example-service/core-services/shipments:read")
                                .condition("")
                                .build())
                        .build())
                .role(PolicyModel.Role
                        .builder()
                        .name("OperationsDept")
                        .permission(PolicyModel.RolePermission
                                .builder()
                                .permission("example-service/core-services/shipments:read")
                                .condition("")
                                .build())
                        .build())
                .build();

        return policy;
    }
}
