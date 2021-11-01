package ru.instamart.test.content.catalog;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcHosts;
import shelf.ShelfOuterClass;
import shelf.ShelfServiceGrpc;

@Epic("Catalog Microservice")
@Feature("Shelf")
@Slf4j
public class ShelfTest extends GrpcBase {
    private ShelfServiceGrpc.ShelfServiceBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcHosts.PAAS_CONTENT_PRODUCT_SHELF);
        client = ShelfServiceGrpc.newBlockingStub(channel);
    }

    @Test(  description = "Get shelf by category id",
            groups = "grpc-product-hub")
    public void getShelfByCategoryId() {
        var request = ShelfOuterClass.GetShelfByCategoryIDRequest
                .newBuilder()
                .setCategoryId("116")
                .setProductsLimit(20)
                .setStoreId("11")
                .setTenantId("sbermarket")
                .build();

        var response = client.getShelfByCategoryID(request);
    }
}
