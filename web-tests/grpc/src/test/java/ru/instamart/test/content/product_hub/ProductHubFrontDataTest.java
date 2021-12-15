package ru.instamart.test.content.product_hub;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import product_hub_front_data.ProductHubFrontDataGrpc;
import product_hub_front_data.ProductHubFrontDataOuterClass;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;

@Epic("Product Hub Microservice")
@Feature("Product Hub Front Data")
@Slf4j
public class ProductHubFrontDataTest extends GrpcBase {
    private ProductHubFrontDataGrpc.ProductHubFrontDataBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_PRODUCT_HUB_FRONT);
        client = ProductHubFrontDataGrpc.newBlockingStub(channel);
    }

    @Story("Продукты")
    @CaseId(204)
    @Test(description = "Получение продуктов по SKU",
            groups = {"grpc-product-hub"})
    public void getProductsBySKU() {
        var request = ProductHubFrontDataOuterClass
                .GetProductsBySKURequest.newBuilder()
                .addSku(144540)
                .build();

        var response = client.getProductsBySKU(request);

        response.getProductsList().forEach(product ->
                assertEquals(product.getSku(), 144540, "Вернулся продукт с другим SKU"));
    }

    @Story("Продукты")
    @CaseId(205)
    @Test(description = "Получение продуктов по SKU без SKU",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: sku array is empty")
    public void getProductsBySKUWithoutSKU() {
        var request = ProductHubFrontDataOuterClass
                .GetProductsBySKURequest.newBuilder()
                .build();

        client.getProductsBySKU(request);
    }

    @Story("Продукты с офферами")
    @CaseId(206)
    @Test(description = "Получение продуктов с офферами по SKU",
            groups = {"grpc-product-hub"})
    public void getProductsWithOfferBySKU() {
        var request = ProductHubFrontDataOuterClass
                .GetProductsWithOfferBySKURequest.newBuilder()
                .setStoreId("1")
                .setTenantId("sbermarket")
                .addSku(144540)
                .build();

        var response = client.getProductsWithOfferBySKU(request);

        response.getProductsWithOfferList().forEach(product ->
                assertEquals(product.getSku(), 144540, "Вернулся продукт с другим SKU"));
    }

    @Story("Продукты с офферами")
    @CaseId(207)
    @Test(description = "Получение продуктов с офферами по SKU без store id",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: store_id is empty")
    public void getProductsWithOfferBySKUWithourStoreId() {
        var request = ProductHubFrontDataOuterClass
                .GetProductsWithOfferBySKURequest.newBuilder()
                .setTenantId("sbermarket")
                .addSku(144540)
                .build();

        client.getProductsWithOfferBySKU(request);
    }

    @Story("Продукты")
    @CaseId(208)
    @Test(description = "Получение продуктов по EAN",
            groups = {"grpc-product-hub"})
    public void getProductsByEan() {
        var request = ProductHubFrontDataOuterClass
                .GetProductsByEANRequest.newBuilder()
                .addEan("4680019170145")
                .build();

        var response = client.getProductsByEAN(request);

        response.getEanToSkuList().forEach(product ->
                assertEquals(product.getEan(), "4680019170145", "Вернулся продукт с другим EAN"));
    }

    @Story("Продукты")
    @CaseId(209)
    @Test(description = "Получение продуктов по EAN с пустым EAN",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: ean array is empty")
    public void getProductsByEanWithEmptyEan() {
        var request = ProductHubFrontDataOuterClass
                .GetProductsByEANRequest.newBuilder()
                .build();

        client.getProductsByEAN(request);
    }

    @Story("Продукты с офферами")
    @CaseId(210)
    @Test(description = "Получение продуктов с офферами по EAN",
            groups = {"grpc-product-hub"})
    public void getProductsWithOffersByEan() {
        var request = ProductHubFrontDataOuterClass
                .GetProductsWithOfferByEANRequest.newBuilder()
                .setStoreId("1")
                .setTenantId("sbermarket")
                .addEan("4680019170145")
                .build();

        var response = client.getProductsWithOfferByEAN(request);

        response.getEanToSkuList().forEach(product ->
                assertEquals(product.getEan(), "4680019170145", "Вернулся продукт с другим EAN"));
    }

    @Story("Продукты с офферами")
    @CaseId(211)
    @Test(description = "Получение продуктов с офферами по EAN без store id",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: store_id is empty")
    public void getProductsWithOffersByEanWithoutStoreId() {
        var request = ProductHubFrontDataOuterClass
                .GetProductsWithOfferByEANRequest.newBuilder()
                .setTenantId("sbermarket")
                .addEan("4680019170145")
                .build();

        client.getProductsWithOfferByEAN(request);
    }

    @Story("Продукты")
    @CaseId(210)
    @Test(description = "Получение продуктов по permalink",
            groups = {"grpc-product-hub"})
    public void getProductsByPermalinks() {
        var request = ProductHubFrontDataOuterClass
                .GetProductsByPermalinkRequest.newBuilder()
                .addPermalink("value")
                .build();

        var response = client.getProductsByPermalink(request);

        response.getProductsList().forEach(product ->
                assertEquals(product.getAttributesList().stream().filter(a -> a.getKey().equals("permalink")).collect(Collectors.toList()).get(0).getValues(0).getValue(), "value", "Вернулся продукт с другим permalink"));
    }

    @Story("Продукты")
    @CaseId(214)
    @Test(description = "Получение продуктов по permalink без permalink",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: permalink array is empty")
    public void getProductsByPermalinksWithoutPermalink() {
        var request = ProductHubFrontDataOuterClass
                .GetProductsByPermalinkRequest.newBuilder()
                .build();

        client.getProductsByPermalink(request);

    }

    @Story("Продукты c офферами")
    @CaseId(215)
    @Test(description = "Получение продуктов с офферами по permalink",
            groups = {"grpc-product-hub"})
    public void getProductsWithOfferByPermalinks() {
        var request = ProductHubFrontDataOuterClass
                .GetProductsWithOfferByPermalinkRequest.newBuilder()
                .setStoreId("1")
                .setTenantId("sbermarket")
                .addPermalink("value")
                .build();

        var response = client.getProductsWithOfferByPermalink(request);

        response.getProductsWithOfferList().forEach(product ->
                assertEquals(product.getAttributesList().stream().filter(a -> a.getKey().equals("permalink")).collect(Collectors.toList()).get(0).getValues(0).getValue(), "value", "Вернулся продукт с другим permalink"));
    }

    @Story("Продукты c офферами")
    @CaseId(213)
    @Test(description = "Получение продуктов с офферами по permalink без store_id",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: store_id is empty")
    public void getProductsWithOfferByPermalinksWithoutStoreId() {
        var request = ProductHubFrontDataOuterClass
                .GetProductsWithOfferByPermalinkRequest.newBuilder()
                .setTenantId("sbermarket")
                .addPermalink("value")
                .build();

        client.getProductsWithOfferByPermalink(request);
    }
}
