package ru.instamart.test.content.product_hub;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import product_hub_back.ProductHubBackGrpc;
import product_hub_back.ProductHubBackOuterClass;
import ru.instamart.grpc.common.GrpcBase;

import static org.testng.Assert.assertEquals;

@Epic("Product Hub Microservice")
@Feature("Product Hub Back")
@Slf4j
public class ProductHubBackTest extends GrpcBase {
    private ProductHubBackGrpc.ProductHubBackBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpcStep.createChannel("paas-content-product-hub-back.k-stage.sbmt.io", 443);
        client = ProductHubBackGrpc.newBlockingStub(channel);
    }

    @Test(  description = "Save categories",
            groups = "grpc-product-hub")
    public void saveCategories() {

        var request = ProductHubBackOuterClass
                .SaveCategoriesRequest
                .newBuilder()
                .addCategories(ProductHubBackOuterClass.Category.newBuilder()
                        .setId("7751")
                        .setName("Testo1")
                        .setParentId("7765")
                        .addRetailerIds("7766")
                        .addCategoryData(ProductHubBackOuterClass.Data.newBuilder()
                                .setKey("Testo2")
                                .setType(ProductHubBackOuterClass.ValueType.STRING)
                                .setIsMultiValue(true)
                                .addValues("Hello")
                                .build()
                        ).build()
                ).build();

        grpcStep.showRequestInAllure(request);

        var response = client.saveCategories(request);

        grpcStep.showResponseInAllure(response);

        assertEquals(response.getSaveCategoriesCount(), 1,
                "Вернулось другое количество созданных категорий");
    }
}
