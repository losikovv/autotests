package ru.instamart.test.content.catalog;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import navigation.Navigation;
import navigation.NavigationServiceGrpc;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.grpc.common.GrpcBase;

@Epic("Catalog Microservice")
@Feature("Navigation")
@Slf4j
public class NavigationTest extends GrpcBase {
    private NavigationServiceGrpc.NavigationServiceBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpcStep.createChannel("paas-content-catalog-navigation.k-stage.sbmt.io", 443);
        client = NavigationServiceGrpc.newBlockingStub(channel);
    }

    @Test(  description = "Get menu tree",
            groups = "grpc-product-hub")
    public void getMenuTree() {

        var request = Navigation
                .GetMenuTreeRequest
                .newBuilder()
                .setStoreId("1")
                .setTenantId("sbermarket")
                .setTreeDepth(20)
                .build();

        grpcStep.showRequestInAllure(request);

        var response = client.getMenuTree(request);

        grpcStep.showResponseInAllure(response);
    }
}
