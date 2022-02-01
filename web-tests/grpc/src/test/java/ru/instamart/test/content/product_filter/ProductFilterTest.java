package ru.instamart.test.content.product_filter;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import ru.instamart.grpc.data_provider.GrpcDataProvider;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import product_filter.ProductFilterGrpc;
import product_filter.ProductFilterOuterClass;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcContentHosts;

import static org.testng.Assert.assertEquals;
import static product_filter.ProductFilterOuterClass.Sort.PRICE_ASC;

@Epic("Product Filter Microservice")
@Slf4j
public class ProductFilterTest extends GrpcBase {
    private ProductFilterGrpc.ProductFilterBlockingStub client;
    private final String categoryId = "175";

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
                .addCategoryIds(categoryId)
                .setAvailable(true)
                .build();

        var response = client.getCategoryFacetsByCategoryIDs(request);

        response.getFacetsList().forEach(facet ->
                assertEquals(facet.getCategoryId(), categoryId, "Вернулась категория с другим ID"));
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
                .addCategoryIds(categoryId)
                .setAvailable(true)
                .build();

       client.getCategoryFacetsByCategoryIDs(request);
    }

    @Story("Фасеты")
    @CaseId(228)
    @Test(description = "Построение фасетов по id категории с пустым store id",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty store_id")
    public void getCategoryFacetsByCategoryIdsWitEmptyStoreId() {
        var request = ProductFilterOuterClass
                .GetCategoryFacetsByCategoryIDsRequest
                .newBuilder()
                .setStoreId("")
                .setTenantId("sbermarket")
                .addCategoryIds(categoryId)
                .setAvailable(true)
                .build();

        client.getCategoryFacetsByCategoryIDs(request);
    }

    @Story("Фасеты")
    @CaseId(229)
    @Test(description = "Построение фасетов по id категории с пустым tenant id",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty tenant_id")
    public void getCategoryFacetsByCategoryIdsWithEmptyTenantId() {
        var request = ProductFilterOuterClass
                .GetCategoryFacetsByCategoryIDsRequest
                .newBuilder()
                .setStoreId("1")
                .setTenantId("")
                .addCategoryIds(categoryId)
                .setAvailable(true)
                .build();

        client.getCategoryFacetsByCategoryIDs(request);
    }

    @Story("Фасеты")
    @CaseId(230)
    @Test(description = "Построение фасетов по id категории без tenant id",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty tenant_id")
    public void getCategoryFacetsByCategoryIdsWithoutTenantId() {
        var request = ProductFilterOuterClass
                .GetCategoryFacetsByCategoryIDsRequest
                .newBuilder()
                .setStoreId("1")
                .addCategoryIds(categoryId)
                .setAvailable(true)
                .build();

        client.getCategoryFacetsByCategoryIDs(request);
    }

    @Story("Фасеты")
    @CaseId(231)
    @Test(description = "Построение фасетов по id категории с пустым category_ids",
            groups = "grpc-product-hub")
    public void getCategoryFacetsByCategoryIdsWithEmptyCategories() {
        var request = ProductFilterOuterClass
                .GetCategoryFacetsByCategoryIDsRequest
                .newBuilder()
                .setStoreId("1")
                .addCategoryIds("")
                .setTenantId("sbermarket")
                .setAvailable(true)
                .build();

        var response = client.getCategoryFacetsByCategoryIDs(request);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getFacets(0).getCategoryId(), "", "Фасеты пришли не пустыми");
        softAssert.assertEquals(response.getFacets(0).getProductCount(), 0, "Фасеты пришли не пустыми");
        softAssert.assertAll();
    }

    @Story("Фасеты")
    @CaseId(232)
    @Test(description = "Построение фасетов по id категории без category_ids",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty category_ids")
    public void getCategoryFacetsByCategoryIdsWithoutCategories() {
        var request = ProductFilterOuterClass
                .GetCategoryFacetsByCategoryIDsRequest
                .newBuilder()
                .setStoreId("1")
                .setTenantId("sbermarket")
                .setAvailable(true)
                .build();

        client.getCategoryFacetsByCategoryIDs(request);
    }

    @Story("Фасеты")
    @CaseId(233)
    @Test(description = "Построение недоступных фасетов по id категории",
            groups = "grpc-product-hub")
    public void getUnavailableCategoryFacetsByCategoryIds() {
        var request = ProductFilterOuterClass
                .GetCategoryFacetsByCategoryIDsRequest
                .newBuilder()
                .setStoreId("1")
                .addCategoryIds(categoryId)
                .setTenantId("sbermarket")
                .setAvailable(false)
                .build();

        var response = client.getCategoryFacetsByCategoryIDs(request);
        Assert.assertEquals(response.getFacets(0).getProductCount(), 0, "Вернулись продукты");
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
                        .addCategoryIds(categoryId)
                        .build())
                .setStoreId("1")
                .setTenantId("sbermarket")
                .setAvailable(true)
                .setProductsSkuLimit(10)
                .build();

        var response = client.getPopularProductsSKUByCategoryIDsBatches(request);
        Assert.assertFalse(response.getProductsSkuBatches(0).getSkuList().isEmpty(), "Не вернулись SKU");
    }

    @Story("SKU")
    @CaseId(173)
    @Test(description = "Получение популярных sku продуктов по ID категорий без tenant_id",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty tenant_id")
    public void getPopularProductsSKUByCategoryIdsWithoutTenantId() {
        var request = ProductFilterOuterClass
                .GetPopularProductsSKUByCategoryIDsBatchesRequest
                .newBuilder()
                .addCategoryIdsBatches(ProductFilterOuterClass.CategoryIDsBatch.newBuilder()
                        .addCategoryIds(categoryId)
                        .build())
                .setStoreId("1")
                .setAvailable(true)
                .setProductsSkuLimit(10)
                .build();

        client.getPopularProductsSKUByCategoryIDsBatches(request);
    }

    @Story("SKU")
    @CaseIDs(value = {@CaseId(234), @CaseId(235), @CaseId(238)})
    @Test(description = "Получение популярных sku продуктов c пустым массивом SKU",
            groups = "grpc-product-hub",
            dataProvider = "categorySKUData",
            dataProviderClass = GrpcDataProvider.class)
    public void getPopularProductsSKUByCategoryIdsWithEmptyCategory(ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesRequest request) {
        var response = client.getPopularProductsSKUByCategoryIDsBatches(request);
        Assert.assertTrue(response.getProductsSkuBatches(0).getSkuList().isEmpty(), "Вернулись SKU");
    }

    @Story("SKU")
    @CaseId(236)
    @Test(description = "Получение популярных sku продуктов по ID категорий с пустым tenant_id",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty tenant_id")
    public void getPopularProductsSKUByCategoryIdsWithEmptyTenantId() {
        var request = ProductFilterOuterClass
                .GetPopularProductsSKUByCategoryIDsBatchesRequest
                .newBuilder()
                .addCategoryIdsBatches(ProductFilterOuterClass.CategoryIDsBatch.newBuilder()
                        .addCategoryIds(categoryId)
                        .build())
                .setStoreId("1")
                .setTenantId("")
                .setAvailable(true)
                .setProductsSkuLimit(10)
                .build();

        client.getPopularProductsSKUByCategoryIDsBatches(request);
    }

    @Story("SKU")
    @CaseId(237)
    @Test(description = "Получение популярных sku продуктов по ID категорий с пустым store_id",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty store_id")
    public void getPopularProductsSKUByCategoryIdsWithEmptyStoreId() {
        var request = ProductFilterOuterClass
                .GetPopularProductsSKUByCategoryIDsBatchesRequest
                .newBuilder()
                .addCategoryIdsBatches(ProductFilterOuterClass.CategoryIDsBatch.newBuilder()
                        .addCategoryIds(categoryId)
                        .build())
                .setStoreId("")
                .setTenantId("sbermarket")
                .setAvailable(true)
                .setProductsSkuLimit(10)
                .build();

        client.getPopularProductsSKUByCategoryIDsBatches(request);
    }

    @Story("SKU и атрибуты")
    @CaseId(174)
    @Test(description = "Получение sku продуктов и атрибутов",
            groups = "grpc-product-hub")
    public void getProductsSKUByAttributes() {
        var request = ProductFilterOuterClass
                .GetProductsSKUByAttributesRequest
                .newBuilder()
                .addCategoryIds(categoryId)
                .setStoreId("1")
                .setTenantId("sbermarket")
                .setAvailable(true)
                .setLimit(10)
                .setOffset(0)
                .setSort(PRICE_ASC)
                .build();

        var response = client.getProductsSKUByAttributes(request);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(response.getSkuList().isEmpty(), "Не вернулись SKU");
        softAssert.assertFalse(response.getFilterFacetsList().isEmpty(), "Не вернулись атрибуты");
        softAssert.assertAll();
    }

    @Story("SKU и атрибуты")
    @CaseId(175)
    @Test(description = "Получение sku продуктов и атрибутов без category_ids",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty category_ids")
    public void getProductsSKUByAttributesWithoutCategories() {
        var request = ProductFilterOuterClass
                .GetProductsSKUByAttributesRequest
                .newBuilder()
                .setStoreId("1")
                .setTenantId("sbermarket")
                .setAvailable(true)
                .setLimit(10)
                .setOffset(0)
                .setSort(PRICE_ASC)
                .build();

        client.getProductsSKUByAttributes(request);
    }

    @Story("SKU и атрибуты")
    @CaseId(239)
    @Test(description = "Получение sku продуктов и атрибутов с пустыми category_ids",
            groups = "grpc-product-hub")
    public void getProductsSKUByAttributesWithEmptyCategory() {
        var request = ProductFilterOuterClass
                .GetProductsSKUByAttributesRequest
                .newBuilder()
                .addCategoryIds("")
                .setStoreId("1")
                .setTenantId("sbermarket")
                .setAvailable(true)
                .setLimit(10)
                .setOffset(0)
                .setSort(PRICE_ASC)
                .build();

        var response = client.getProductsSKUByAttributes(request);
        Assert.assertTrue(response.getSkuList().isEmpty());
    }
}
