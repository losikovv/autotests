package ru.instamart.test.content.catalog;

import catalog.Catalog;
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
import ru.instamart.jdbc.dao.stf.OffersDao;
import ru.instamart.jdbc.entity.stf.OffersEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.enums.Tenant;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Epic("Catalog Microservice")
@Feature("Catalog Api V2")
public class CatalogApiV2Test extends GrpcBase {
    private CatalogAPIV2ServiceGrpc.CatalogAPIV2ServiceBlockingStub client;
    private final String sid = "57";
    private String tid;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_CATALOG);
        client = CatalogAPIV2ServiceGrpc.newBlockingStub(channel);

        tid = grpc.getCategories(sid, Tenant.SBERMARKET.getId()).get(0).getOriginalCategoryId();
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
                .setTenantId(Tenant.SBERMARKET.getId())
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
                .setTenantId(Tenant.SBERMARKET.getId())
                .build();

        client.getProduct(request);
    }

    @Story("Продукты")
    @CaseId(241)
    @Test(description = "Получение карточки товара без tenant_id",
            groups = {"grpc-product-hub"})
    //       expectedExceptions = StatusRuntimeException.class,
    //       expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty tenant_id")
    public void getProductWithoutTenantId() {
       OffersEntity offer = OffersDao.INSTANCE.getOfferByStoreId(1);
        var request = CatalogApiV2
                .GetProductRequest.newBuilder()
                .setProductId(offer.getId().toString())
                .build();

        var response = client.getProduct(request);

        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getProduct().getSku(), Long.parseLong(offer.getProductSku()), "SKU продуктов не совпадают");
        softAssert.assertEquals(response.getProduct().getRetailerSku(), offer.getRetailerSku(), "SKU ретейлеров не совпадают");
        softAssert.assertAll();
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
                .setTenantId(Tenant.SBERMARKET.getId())
                .setProductId("")
                .build();

        client.getProduct(request);
    }

    @Story("Продукты")
    @CaseId(244)
    @Test(description = "Получение карточки товара с пустой строкой tenant_id",
            groups = {"grpc-product-hub"})
    //        expectedExceptions = StatusRuntimeException.class,
    //        expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty tenant_id")
    public void getProductWitEmptyTenantId() {
        OffersEntity offer = OffersDao.INSTANCE.getOfferByStoreId(1);
        var request = CatalogApiV2
                .GetProductRequest.newBuilder()
                .setTenantId("")
                .setProductId(offer.getId().toString())
                .build();

        var response = client.getProduct(request);

        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getProduct().getSku(), Long.parseLong(offer.getProductSku()), "SKU продуктов не совпадают");
        softAssert.assertEquals(response.getProduct().getRetailerSku(), offer.getRetailerSku(), "SKU ретейлеров не совпадают");
        softAssert.assertAll();
    }

    @Story("Продукты")
    @CaseIDs(value = {@CaseId(248), @CaseId(249)})
    @Test(description = "Получение списка товаров", enabled = false, //ждет обновления от Дмитрия Дьячкова после изменения логики
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
                .setTid(tid)
                .setPage(1)
                .setPage(24)
                .setSort(Catalog.Sort.POPULARITY.name())
                .setTenantId(Tenant.SBERMARKET.getId())
                .build();

        client.getProductList(request);
    }

    @Story("Продукты")
    @CaseId(178)
    @Test(description = "Проверка ручки листинга продуктов",
            groups = {"grpc-product-hub"})
    public void getProductList() {
        var request = CatalogApiV2
                .GetProductListRequest.newBuilder()
                .setSid(sid)
                .setTid(tid)
                .setPage(1)
                .setPerPage(24)
                .setSort(Catalog.Sort.POPULARITY.name())
                .setTenantId(Tenant.SBERMARKET.getId())
                .build();

        var response = client.getProductList(request);

        Assert.assertFalse(response.getProductsList().isEmpty(), "Не вернулся список продуктов");
        Assert.assertFalse(response.getSortList().isEmpty(), "Не вернулся список сортировок продуктов");
        Assert.assertFalse(response.getFacetsList().isEmpty(), "Не вернулся список аспектов продуктов");
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
                .setTid(tid)
                .setPage(1)
                .setPage(24)
                .setSort(Catalog.Sort.POPULARITY.name())
                .setTenantId(Tenant.SBERMARKET.getId())
                .build();

        client.getProductList(request);
    }

    @Story("Продукты")
    @CaseId(246)
    @Test(description = "Проверка ручки листинга продуктов с пустой строкой поля tid",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty tid")
    public void getProductListWithEmptyTid() {
        var request = CatalogApiV2
                .GetProductListRequest.newBuilder()
                .setSid(String.valueOf(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID))
                .setTid("")
                .setPage(1)
                .setPage(24)
                .setSort(Catalog.Sort.POPULARITY.name())
                .setTenantId(Tenant.SBERMARKET.getId())
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
                .setSid(String.valueOf(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID))
                .setPage(1)
                .setPage(24)
                .setSort(Catalog.Sort.POPULARITY.name())
                .setTenantId(Tenant.SBERMARKET.getId())
                .build();

        client.getProductList(request);
    }

    @Story("Продукты")
    @CaseId(250)
    @Test(description = "Проверка ручки листинга продуктов с пустым значением поля \"tenant_id\"",
            groups = {"grpc-product-hub"})
    public void getProductListWithEmptyTenantId() {
        var request = CatalogApiV2
                .GetProductListRequest.newBuilder()
                .setSid(sid)
                .setTid(tid)
                .setPage(1)
                .setPerPage(24)
                .setSort(Catalog.Sort.POPULARITY.name())
                .setTenantId("")
                .build();

        var response = client.getProductList(request);

        Assert.assertFalse(response.getProductsList().isEmpty(), "Не вернулся список продуктов");
        Assert.assertFalse(response.getSortList().isEmpty(), "Не вернулся список сортировок продуктов");
        Assert.assertFalse(response.getFacetsList().isEmpty(), "Не вернулся список аспектов продуктов");
    }

    @Story("Продукты")
    @CaseId(251)
    @Test(description = "Проверка ручки листинга продуктов без передачи поля \"tenant_id\"",
            groups = {"grpc-product-hub"})
    public void getProductListWithoutTenantId() {
        var request = CatalogApiV2
                .GetProductListRequest.newBuilder()
                .setSid(sid)
                .setTid(tid)
                .setPage(1)
                .setPerPage(24)
                .setSort(Catalog.Sort.POPULARITY.name())
                .build();

        var response = client.getProductList(request);

        Assert.assertFalse(response.getProductsList().isEmpty(), "Не вернулся список продуктов");
        Assert.assertFalse(response.getSortList().isEmpty(), "Не вернулся список сортировок продуктов");
        Assert.assertFalse(response.getFacetsList().isEmpty(), "Не вернулся список аспектов продуктов");    }
}
