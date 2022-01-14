package ru.instamart.grpc.data_provider;

import catalog_api_v2.CatalogApiV2;
import org.testng.annotations.DataProvider;
import product_filter.ProductFilterOuterClass;

public class GrpcDataProvider {

    @DataProvider(name = "catalogProductListData")
    public static Object[][] getCatalogProductListData() {
        return new Object[][]{
                {CatalogApiV2
                        .GetProductListRequest.newBuilder()
                        .setSid("1")
                        .setTid("22238")
                        .setPage(1)
                        .setPerPage(24)
                        .setSort("0")
                        .setTenantId("sbermarket")
                        .build()},
                {CatalogApiV2
                        .GetProductListRequest.newBuilder()
                        .setSid("1")
                        .setTid("22238")
                        .setPage(1)
                        .setPerPage(24)
                        .setSort("")
                        .setTenantId("sbermarket")
                        .build()},
                {CatalogApiV2
                        .GetProductListRequest.newBuilder()
                        .setSid("1")
                        .setTid("22238")
                        .setPage(1)
                        .setPerPage(24)
                        .setTenantId("sbermarket")
                        .build()}
        };
    }

    @DataProvider(name = "categorySKUData")
    public static Object[][] getCategorySKUData() {
        return new Object[][]{
                {ProductFilterOuterClass
                        .GetPopularProductsSKUByCategoryIDsBatchesRequest
                        .newBuilder()
                        .addCategoryIdsBatches(ProductFilterOuterClass.CategoryIDsBatch.newBuilder()
                                .addCategoryIds("")
                                .build())
                        .setStoreId("1")
                        .setTenantId("sbermarket")
                        .setAvailable(true)
                        .setProductsSkuLimit(10)
                        .build()},
                {ProductFilterOuterClass
                        .GetPopularProductsSKUByCategoryIDsBatchesRequest
                        .newBuilder()
                        .setStoreId("1")
                        .addCategoryIdsBatches(ProductFilterOuterClass.CategoryIDsBatch.newBuilder()
                                .build())
                        .setTenantId("sbermarket")
                        .setAvailable(true)
                        .setProductsSkuLimit(10)
                        .build()}
//                , {ProductFilterOuterClass
//                        .GetPopularProductsSKUByCategoryIDsBatchesRequest
//                        .newBuilder()
//                        .setStoreId("1")
//                        .addCategoryIdsBatches(ProductFilterOuterClass.CategoryIDsBatch.newBuilder()
//                                .addCategoryIds("6120") //todo выбрать категорию без SKU, либо чистить SKU перед тестом
//                                .build())
//                        .setTenantId("sbermarket")
//                        .setAvailable(false)
//                        .setProductsSkuLimit(10)
//                        .build()}
        };
    }
}
