package ru.instamart.test.content.product_hub;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import product_hub_back_stream.ProductHubBackStreamGrpc;
import product_hub_back_stream.ProductHubBackStreamOuterClass;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertEquals;

@Epic("Product Hub Microservice")
@Feature("Product Hub Back Stream")
@Slf4j
public class ProductHubBackStreamTest extends GrpcBase {
    private ProductHubBackStreamGrpc.ProductHubBackStreamBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_PRODUCT_HUB_BACK);
        client = ProductHubBackStreamGrpc.newBlockingStub(channel);
    }

    @CaseId(185)
    @Test(description = "Получение данных по продуктам",
            groups = "grpc-product-hub")
    public void getProducts() {
        var request = ProductHubBackStreamOuterClass
                .GetProductsRequest.newBuilder()
                .setCursorId(1)
                .setDisplayAttributes(
                        ProductHubBackStreamOuterClass.DisplayAttributes.newBuilder()
                                .addKeys("brand")
                                .build()
                ).build();

        var responses = client.getProducts(request);

        assertEquals(responses.next().getCursorId(), 1,
                "Вернулся другой cursor ID");
    }

    @CaseId(184)
    @Test(description = "Получение продуктов с пустым массивом запрашиваемых данных",
            groups = "grpc-product-hub")
    public void getProductsWithEmptyData() {
        var request = ProductHubBackStreamOuterClass
                .GetProductsRequest.newBuilder().build();

        var responses = client.getProducts(request);
        Assert.assertTrue(responses.hasNext());
    }
}
