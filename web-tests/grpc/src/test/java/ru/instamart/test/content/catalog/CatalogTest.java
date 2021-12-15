package ru.instamart.test.content.catalog;

import catalog.Catalog;
import catalog.CatalogServiceGrpc;
import io.grpc.StatusRuntimeException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.sbermarket.qase.annotation.CaseId;

@Epic("Catalog Microservice")
@Feature("Catalog")
@Slf4j
public class CatalogTest extends GrpcBase {
    private CatalogServiceGrpc.CatalogServiceBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_CATALOG);
        client = CatalogServiceGrpc.newBlockingStub(channel);
    }

    @Story("Каталог продуктов")
    @CaseId(180)
    @Test(description = "Получение продуктов",
            groups = {"grpc-product-hub"})
    public void getProducts() {
        var request = Catalog
                .GetProductsRequest
                .newBuilder()
                .setStoreId("1")
                .setTenantId("sbermarket")
                .setCategoryId("22238")
                .setLimit(20)
                .setOffset(20)
                .setAvailable(true)
                .setSort(Catalog.Sort.POPULARITY)
                .build();

        var response = client.getProducts(request);

        Assert.assertFalse(response.getProductsList().isEmpty(), "Не вернулись продукты");
    }

    @Story("Каталог продуктов")
    @CaseId(181)
    @Test(description = "Получение продуктов без store_id",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty store_id")
    public void getProductsWithoutStoreId() {
        var request = Catalog
                .GetProductsRequest
                .newBuilder()
                .setTenantId("sbermarket")
                .setCategoryId("6120")
                .setLimit(20)
                .setOffset(20)
                .setAvailable(true)
                .setSort(Catalog.Sort.POPULARITY)
                .build();

        client.getProducts(request);
    }

    @Story("Каталог продуктов")
    @CaseId(252)
    @Test(description = "Получение продуктов с пустым store_id",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty store_id")
    public void getProductsWithEmptyStoreId() {
        var request = Catalog
                .GetProductsRequest
                .newBuilder()
                .setStoreId("")
                .setTenantId("sbermarket")
                .setCategoryId("6120")
                .setLimit(20)
                .setOffset(20)
                .setAvailable(true)
                .setSort(Catalog.Sort.POPULARITY)
                .build();

        client.getProducts(request);
    }

    @Story("Каталог продуктов")
    @CaseId(253)
    @Test(description = "Получение продуктов с пустым tenant_id",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty tenant_id")
    public void getProductsWithEmptyTenantId() {
        var request = Catalog
                .GetProductsRequest
                .newBuilder()
                .setStoreId("1")
                .setTenantId("")
                .setCategoryId("6120")
                .setLimit(20)
                .setOffset(20)
                .setAvailable(true)
                .setSort(Catalog.Sort.POPULARITY)
                .build();

        client.getProducts(request);
    }

    @Story("Каталог продуктов")
    @CaseId(254)
    @Test(description = "Получение продуктов без tenant_id",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty tenant_id")
    public void getProductsWithoutTenantId() {
        var request = Catalog
                .GetProductsRequest
                .newBuilder()
                .setStoreId("1")
                .setCategoryId("6120")
                .setLimit(20)
                .setOffset(20)
                .setAvailable(true)
                .setSort(Catalog.Sort.POPULARITY)
                .build();

        client.getProducts(request);
    }

    @Story("Каталог продуктов")
    @CaseId(255)
    @Test(description = "Получение продуктов с пустым category_id",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty category_id")
    public void getProductsWithEmptyCategoryId() {
        var request = Catalog
                .GetProductsRequest
                .newBuilder()
                .setStoreId("1")
                .setTenantId("sbermarket")
                .setCategoryId("")
                .setLimit(20)
                .setOffset(20)
                .setAvailable(true)
                .setSort(Catalog.Sort.POPULARITY)
                .build();

        client.getProducts(request);
    }

    @Story("Каталог продуктов")
    @CaseId(256)
    @Test(description = "Получение продуктов без category_id",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty category_id")
    public void getProductsWithoutCategoryId() {
        var request = Catalog
                .GetProductsRequest
                .newBuilder()
                .setStoreId("1")
                .setTenantId("sbermarket")
                .setLimit(20)
                .setOffset(20)
                .setAvailable(true)
                .setSort(Catalog.Sort.POPULARITY)
                .build();

        client.getProducts(request);
    }
}
