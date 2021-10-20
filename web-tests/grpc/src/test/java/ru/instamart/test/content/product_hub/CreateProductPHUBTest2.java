package ru.instamart.test.content.product_hub;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import product_hub_back.ProductHubBackGrpc;
import product_hub_back.ProductHubBackOuterClass;
import product_hub_front_data.ProductHubFrontDataGrpc;
import product_hub_front_data.ProductHubFrontDataOuterClass;
import ru.instamart.grpc.common.GrpcBase;

@Epic("Product Hub Microservice")
@Feature("Создание продукта")
public class CreateProductPHUBTest2 extends GrpcBase {
    private ProductHubBackGrpc.ProductHubBackBlockingStub backClient;
    private ProductHubFrontDataGrpc.ProductHubFrontDataBlockingStub frontClient;
    private long productSku = (long) (Math.random() * 1000000);

    @CaseId(114)
    @Test(description = "Создание продукта",
            groups = {"grpc-product-hub"})
    public void createProduct() {
        channel = grpcStep.createChannel("paas-content-product-hub-back.k-stage.sbmt.io", 443);
        backClient = ProductHubBackGrpc.newBlockingStub(channel);

        var request = ProductHubBackOuterClass
                .SaveProductsRequest
                .newBuilder()
                .addProducts(ProductHubBackOuterClass.Product
                        .newBuilder()
                        .setSku(productSku)
                        .setName("Тестовый продукт")
                        .addCategoryIds("99959999")
                        .addAttributeValues(ProductHubBackOuterClass.AttributeValue.newBuilder()
                                .setAttributeKey("Test_retest_9")
                                .setDictionaryValueId("test_retest_key_9")
                                .build())
                        .setStatus(ProductHubBackOuterClass.Status.ENABLE)
                        .build())
                .build();
        grpcStep.showRequestInAllure(request);
        var response = backClient.saveProducts(request);
        grpcStep.showResponseInAllure(response);
    }

    @CaseId(114)
    @Test(description = "Получение продукта по SKU",
            groups = {"grpc-product-hub"},
            dependsOnMethods = {"createProduct"})
    public void getProductsBySKU() {
        channel = grpcStep.createChannel("paas-content-product-hub.k-stage.sbmt.io", 443);
        frontClient = ProductHubFrontDataGrpc.newBlockingStub(channel);

        var request = ProductHubFrontDataOuterClass
                .GetProductsBySKURequest
                .newBuilder()
                .addSku(productSku)
                .build();
        grpcStep.showRequestInAllure(request);
        var response = frontClient.getProductsBySKU(request);
        grpcStep.showResponseInAllure(response);
    }
}
