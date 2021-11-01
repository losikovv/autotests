package ru.instamart.test.content.product_hub;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import product_hub_front_meta.ProductHubFrontMetaGrpc;
import product_hub_front_meta.ProductHubFrontMetaOuterClass;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcHosts;

import static org.testng.Assert.assertTrue;

@Epic("Product Hub Microservice")
@Feature("Product Hub Front Meta")
@Slf4j
public class ProductHubFrontMetaTest extends GrpcBase {
    private ProductHubFrontMetaGrpc.ProductHubFrontMetaBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcHosts.PAAS_CONTENT_PRODUCT_HUB_FRONT);
        client = ProductHubFrontMetaGrpc.newBlockingStub(channel);
    }

    @Test(  description = "Get all categories",
            groups = "grpc-product-hub")
    public void getAllCategories() {
        var request = ProductHubFrontMetaOuterClass
                .GetAllCategoriesRequest.newBuilder()
                .setLimit(100)
                .setOffset(20)
                .build();

        var response = client.getAllCategories(request);

        assertTrue(response.getCategoriesList().size() <= 100,
                "Количество категорий вернулось больше лимита");
    }
}
