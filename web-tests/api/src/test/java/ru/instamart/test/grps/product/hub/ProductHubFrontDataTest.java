package ru.instamart.test.grps.product.hub;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import product_hub_front_data.ProductHubFrontDataGrpc;
import product_hub_front_data.ProductHubFrontDataOuterClass;
import ru.instamart.api.common.GrpcBase;

import static org.testng.Assert.assertEquals;

@Epic("Product Hub Microservice")
@Feature("Product Hub Front Data")
@Slf4j
public class ProductHubFrontDataTest extends GrpcBase {
    private ProductHubFrontDataGrpc.ProductHubFrontDataBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpcStep.createChannel("paas-content-product-hub.k-stage.sbmt.io", 443);
        client = ProductHubFrontDataGrpc.newBlockingStub(channel);
    }

    @Test(  description = "Get products by SKU",
            groups = "grpc-product-hub")
    public void getProductsBySKU() {

        var request = ProductHubFrontDataOuterClass
                .GetProductsBySKURequest.newBuilder()
                .addSku(36250)
                .build();

        grpcStep.showRequestInAllure(request);

        var response = client.getProductsBySKU(request);

        grpcStep.showResponseInAllure(response);

        response.getProductsList().forEach(product ->
                assertEquals(product.getSku(), 36250, "Вернулся продукт с другим SKU"));
    }
}
