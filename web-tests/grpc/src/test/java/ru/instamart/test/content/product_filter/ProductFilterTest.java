package ru.instamart.test.content.product_filter;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import ru.sbermarket.qase.annotation.CaseId;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import product_filter.ProductFilterGrpc;
import product_filter.ProductFilterOuterClass;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcContentHosts;

import static org.testng.Assert.assertEquals;

@Epic("Product Filter Microservice")
@Slf4j
public class ProductFilterTest extends GrpcBase {
    private ProductFilterGrpc.ProductFilterBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_PRODUCT_FILTER);
        client = ProductFilterGrpc.newBlockingStub(channel);
    }

    @Story("Фасеты")
    @CaseId(170)
    @Test(description = "Построение фасетов по id категории",
            groups = "grpc-product-hub")
    public void getCategoryFacetsByCategoryIds() {
        var request = ProductFilterOuterClass
                .GetCategoryFacetsByCategoryIDsRequest
                .newBuilder()
                .setStoreId("1")
                .setTenantId("sbermarket")
                .addCategoryIds("6120")
                .setAvailable(true)
                .build();

        var response = client.getCategoryFacetsByCategoryIDs(request);

        response.getFacetsList().forEach(facet ->
                assertEquals(facet.getCategoryId(), "6120", "Вернулась категория с другим ID"));
    }

    @Story("Фасеты")
    @CaseId(171)
    @Test(description = "Построение фасетов по id категории с отсутствующим store id",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty store_id")
    public void getCategoryFacetsByCategoryIdsWithoutStoreId() {
        var request = ProductFilterOuterClass
                .GetCategoryFacetsByCategoryIDsRequest
                .newBuilder()
                .setTenantId("sbermarket")
                .addCategoryIds("6120")
                .setAvailable(true)
                .build();

       client.getCategoryFacetsByCategoryIDs(request);
    }

    @Story("SKU")
    @CaseId(172)
    @Test(description = "Получение популярных sku продуктов по ID категорий",
            groups = "grpc-product-hub")
    public void getPopularProductsSKUByCategoryIds() {
        var request = ProductFilterOuterClass
                .GetPopularProductsSKUByCategoryIDsBatchesRequest
                .newBuilder()
                .addCategoryIdsBatches(ProductFilterOuterClass.CategoryIDsBatch.newBuilder()
                        .addCategoryIds("6120")
                        .build())
                .setStoreId("1")
                .setTenantId("sbermarket")
                .setAvailable(true)
                .setProductsSkuLimit(10)
                .build();

        var response = client.getPopularProductsSKUByCategoryIDsBatches(request);
        Assert.assertFalse(response.getProductsSkuBatches(0).getSkuList().isEmpty(), "Не вернулись SKU");
    }
}
