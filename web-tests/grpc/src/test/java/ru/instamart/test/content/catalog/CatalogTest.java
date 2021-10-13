package ru.instamart.test.content.catalog;

import catalog.Catalog;
import catalog.CatalogServiceGrpc;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.grpc.common.GrpcBase;

@Epic("Catalog Microservice")
@Feature("Catalog")
@Slf4j
public class CatalogTest extends GrpcBase {
    private CatalogServiceGrpc.CatalogServiceBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpcStep.createChannel("paas-content-catalog.k-stage.sbmt.io", 443);
        client = CatalogServiceGrpc.newBlockingStub(channel);
    }

    @Test(  description = "Get products",
            groups = "grpc-product-hub")
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

        grpcStep.showRequestInAllure(request);

        var response = client.getProducts(request);

        grpcStep.showResponseInAllure(response);
    }
}
