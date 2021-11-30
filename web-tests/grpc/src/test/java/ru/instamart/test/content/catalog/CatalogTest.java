package ru.instamart.test.content.catalog;

import catalog.Catalog;
import catalog.CatalogServiceGrpc;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcHosts;

@Epic("Catalog Microservice")
@Feature("Catalog")
@Slf4j
public class CatalogTest extends GrpcBase {
    private CatalogServiceGrpc.CatalogServiceBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcHosts.PAAS_CONTENT_PRODUCT_CATALOG);
        client = CatalogServiceGrpc.newBlockingStub(channel);
    }

    @Test(  description = "Get products",
            groups = {})
    public void getProducts() {
        var request = Catalog
                .GetProductsRequest
                .newBuilder()
                .setStoreId("1")
                .setTenantId("sbermarket")
                .setCategoryId("175")
                .setLimit(20)
                .setOffset(20)
                .setAvailable(true)
                .setSort(Catalog.Sort.POPULARITY)
                .build();

        var response = client.getProducts(request);
    }
}
