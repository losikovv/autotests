package ru.instamart.test.content.catalog;

import catalog_api_v2.CatalogAPIV2ServiceGrpc;
import catalog_api_v2.CatalogApiV2;
import io.grpc.StatusRuntimeException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.grpc.data_provider.GrpcDataProvider;
import ru.instamart.jdbc.dao.OffersDao;
import ru.instamart.jdbc.entity.OffersEntity;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Epic("Catalog Microservice")
@Feature("Catalog Api V2")
public class CatalogApiV2Test extends GrpcBase {
    private CatalogAPIV2ServiceGrpc.CatalogAPIV2ServiceBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_CATALOG);
        client = CatalogAPIV2ServiceGrpc.newBlockingStub(channel);
    }

    @Story("Продукты")
    @CaseId(176)
    @Test(description = "Получение карточки товара",
            groups = {"grpc-product-hub"})
    public void getProduct() {
        OffersEntity offer = OffersDao.INSTANCE.getOfferByStoreId(1);
        var request = CatalogApiV2
                .GetProductRequest.newBuilder()
                .setProductId(offer.getId().toString())
                .setTenantId("sbermarket")
                .build();

        var response = client.getProduct(request);

        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getProduct().getSku(), Long.parseLong(offer.getProductSku()), "SKU продуктов не совпадают");
        softAssert.assertEquals(response.getProduct().getRetailerSku(), offer.getRetailerSku(), "SKU ретейлеров не совпадают");
        softAssert.assertAll();
    }

    @Story("Продукты")
    @CaseId(177)
    @Test(description = "Получение карточки товара без product_id",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty product_id")
    public void getProductWithoutProductId() {
        var request = CatalogApiV2
                .GetProductRequest.newBuilder()
                .setTenantId("sbermarket")
                .build();

        client.getProduct(request);
    }

    @Story("Продукты")
    @CaseId(241)
    @Test(description = "Получение карточки товара без tenant_id",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty tenant_id")
    public void getProductWithoutTenantId() {
        var request = CatalogApiV2
                .GetProductRequest.newBuilder()
                .setProductId("241454")
                .build();

        client.getProduct(request);
    }

    @Story("Продукты")
    @CaseId(242)
    @Test(description = "Получение карточки товара с пустым product_id",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty product_id")
    public void getProductWitEmptyProductId() {
        var request = CatalogApiV2
                .GetProductRequest.newBuilder()
                .setTenantId("sbermarket")
                .setProductId("")
                .build();

        client.getProduct(request);
    }

    @Story("Продукты")
    @CaseId(244)
    @Test(description = "Получение карточки товара с пустым tenant_id",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty tenant_id")
    public void getProductWitEmptyTenantId() {
        var request = CatalogApiV2
                .GetProductRequest.newBuilder()
                .setTenantId("")
                .setProductId("241454")
                .build();

        client.getProduct(request);
    }

    @Story("Продукты")
    @CaseIDs(value = {@CaseId(178), @CaseId(248), @CaseId(249)})
    @Test(description = "Получение списка товаров",
            groups = {"grpc-product-hub"},
            dataProvider = "catalogProductListData",
            dataProviderClass = GrpcDataProvider.class)
    public void getProductList(CatalogApiV2.GetProductListRequest request) {
        var response = client.getProductList(request);

        Assert.assertFalse(response.getProductsList().isEmpty(), "Не вернулись продукты");
    }

    @Story("Продукты")
    @CaseId(179)
    @Test(description = "Получение списка товаров с пустым sid",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty sid\\(store_id\\)")
    public void getProductListWithEmptySid() {
        var request = CatalogApiV2
                .GetProductListRequest.newBuilder()
                .setSid("")
                .setTid("22238")
                .setPage(1)
                .setPage(24)
                .setSort("0")
                .setTenantId("sbermarket")
                .build();

        client.getProductList(request);
    }

    @Story("Продукты")
    @CaseId(245)
    @Test(description = "Получение списка товаров без sid",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty sid\\(store_id\\)")
    public void getProductListWithoutSid() {
        var request = CatalogApiV2
                .GetProductListRequest.newBuilder()
                .setTid("22238")
                .setPage(1)
                .setPage(24)
                .setSort("0")
                .setTenantId("sbermarket")
                .build();

        client.getProductList(request);
    }

    @Story("Продукты")
    @CaseId(246)
    @Test(description = "Получение списка товаров c пустым tid",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty tid")
    public void getProductListWithEmptyTid() {
        var request = CatalogApiV2
                .GetProductListRequest.newBuilder()
                .setSid("1")
                .setTid("")
                .setPage(1)
                .setPage(24)
                .setSort("0")
                .setTenantId("sbermarket")
                .build();

        client.getProductList(request);
    }

    @Story("Продукты")
    @CaseId(247)
    @Test(description = "Получение списка товаров без tid",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty tid")
    public void getProductListWithoutTid() {
        var request = CatalogApiV2
                .GetProductListRequest.newBuilder()
                .setSid("1")
                .setPage(1)
                .setPage(24)
                .setSort("0")
                .setTenantId("sbermarket")
                .build();

        client.getProductList(request);
    }

    @Story("Продукты")
    @CaseId(250)
    @Test(description = "Получение списка товаров с пустым tenant_d",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty tenant_id")
    public void getProductListWithEmptyTenantId() {
        var request = CatalogApiV2
                .GetProductListRequest.newBuilder()
                .setSid("1")
                .setPage(1)
                .setPage(24)
                .setSort("0")
                .setTenantId("")
                .build();

        client.getProductList(request);
    }

    @Story("Продукты")
    @CaseId(251)
    @Test(description = "Получение списка товаров без tenant_d",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty tenant_id")
    public void getProductListWithoutTenantId() {
        var request = CatalogApiV2
                .GetProductListRequest.newBuilder()
                .setSid("1")
                .setPage(1)
                .setPage(24)
                .setSort("0")
                .build();

        client.getProductList(request);
    }
}