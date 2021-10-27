package ru.instamart.test.content.product_hub;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import product_hub_front_data.ProductHubFrontDataGrpc;
import product_hub_front_data.ProductHubFrontDataOuterClass;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcHosts;

import static org.testng.Assert.assertEquals;

@Epic("Product Hub Microservice")
@Feature("Product Hub Front Data")
@Slf4j
public class ProductHubFrontDataTest extends GrpcBase {
    private ProductHubFrontDataGrpc.ProductHubFrontDataBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcHosts.PAAS_CONTENT_PRODUCT_HUB_FRONT);
        client = ProductHubFrontDataGrpc.newBlockingStub(channel);
    }

    @Test(  description = "Get products by SKU",
            groups = "grpc-product-hub")
    public void getProductsBySKU() {
        var request = ProductHubFrontDataOuterClass
                .GetProductsBySKURequest.newBuilder()
                .addSku(36250)
                .build();
        allure.showRequest(request);

        var response = client.getProductsBySKU(request);
        allure.showResponse(response);

        response.getProductsList().forEach(product ->
                assertEquals(product.getSku(), 36250, "Вернулся продукт с другим SKU"));
    }
}
