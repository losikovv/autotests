package ru.instamart.tests.api.v2.e2e;

import instamart.api.checkpoints.InstamartApiCheckpoints;
import instamart.api.common.RestBase;
import instamart.api.helpers.RegistrationHelper;
import instamart.api.objects.v2.Product;
import instamart.api.objects.v2.Store;
import instamart.api.objects.v2.Taxon;
import instamart.api.requests.v2.ProductsRequest;
import instamart.core.testdata.UserManager;
import instamart.core.testdata.dataprovider.RestDataProvider;
import instamart.ui.common.pagesdata.UserData;
import io.qase.api.annotation.CaseId;
import io.restassured.RestAssured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class StoreE2ETests extends RestBase {

    private static final Logger log = LoggerFactory.getLogger(StoreE2ETests.class);

    @BeforeClass(description = "Проверка самих провайдеров")
    public void selfTest() {
        RestDataProvider.getAvailableStores();
    }

    @CaseId(105)
    @Test(  dataProvider = "stores-parallel",
            dataProviderClass = RestDataProvider.class,
            description = "Тест категорий на главных страницах всех магазинов",
            groups = {})
    public void departmentsOnMainPages(Store store) {
        log.info(store.toString());

        SoftAssert softAssert = new SoftAssert();
        apiV2.getProductsFromEachDepartmentInStore(store.getId(), softAssert);
        softAssert.assertAll();
    }

    @CaseId(106)
    @Test(  dataProvider = "stores-parallel",
            dataProviderClass = RestDataProvider.class,
            description = "Тест продуктов на главных страницах всех магазинов",
            groups = {})
    public void productsOnMainPages(Store store) {
        log.info("Магазин {} url={}/api/v2/departments?sid={}", store, RestAssured.baseURI, store.getId());

        final SoftAssert softAssert = new SoftAssert();
        final List<Product> products = apiV2.getProductsFromEachDepartmentInStore(store.getId());
        for (final Product product : products) {
            softAssert.assertEquals(ProductsRequest.GET(product.getId()).getStatusCode(),200,
                    "\n" + product + " " + RestAssured.baseURI + "/api/v2/products/" + product.getId());
        }
        softAssert.assertAll();
    }

    @CaseId(107)
    @Test(  dataProvider = "stores-parallel",
            dataProviderClass = RestDataProvider.class,
            description = "Тест количества товаров в таксонах",
            groups = {})
    public void categoriesProductsCount(Store store) {
        log.info(store.toString());

        final SoftAssert softAssert = new SoftAssert();
        final List<Taxon> taxons = apiV2.getTaxons(store.getId());
        taxons.forEach(taxon -> InstamartApiCheckpoints.assertProductsCountEqualsChildrenSum(taxon, softAssert));
        softAssert.assertAll();
    }

    @Test(  dataProvider = "stores",
            dataProviderClass = RestDataProvider.class,
            description = "Тест заказов во всех магазинах",
            groups = {})
    public void orderByStore(Store store) {
        log.info("Оформляем заказ в {}", store);

        apiV2.order(UserManager.getDefaultUser(), store.getId());
        apiV2.cancelCurrentOrder();
    }

    @CaseId(108)
    @Test(  dataProvider = "stores-parallel",
            dataProviderClass = RestDataProvider.class,
            description = "Тест первых заказов во всех магазинах",
            groups = {})
    public void firstOrderByStore(Store store) {
        log.info("Оформляем первый заказ в {}", store);

        final UserData userData = UserManager.getUser();
        RegistrationHelper.registration(userData);
        apiV2.order(userData, store.getId());
        apiV2.cancelCurrentOrder();
    }
}
