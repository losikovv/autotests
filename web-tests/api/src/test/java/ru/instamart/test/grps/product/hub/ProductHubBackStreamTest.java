package ru.instamart.test.grps.product.hub;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import product_hub_back_stream.ProductHubBackStreamGrpc;
import product_hub_back_stream.ProductHubBackStreamOuterClass;
import ru.instamart.api.common.GrpcBase;

import static org.testng.Assert.assertEquals;

@Epic("Product Hub Microservice")
@Feature("Product Hub Back Stream")
@Slf4j
public class ProductHubBackStreamTest extends GrpcBase {
    private ProductHubBackStreamGrpc.ProductHubBackStreamBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpcStep.createChannel("paas-content-product-hub-back.k-stage.sbmt.io", 443);
        client = ProductHubBackStreamGrpc.newBlockingStub(channel);
    }

    @Test(  description = "Get products",
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

        grpcStep.showRequestInAllure(request);

        var responses = client.getProducts(request);

        grpcStep.showResponseInAllure(responses.next());

        assertEquals(responses.next().getCursorId(), 1,
                "Вернулся другой cursor ID");
    }
}
