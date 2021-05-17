package ru.instamart.tests.api.v2.e2e;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.RestAssured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.checkpoint.InstamartApiCheckpoints;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.helper.RegistrationHelper;
import ru.instamart.api.model.v2.ProductV2;
import ru.instamart.api.model.v2.StoreV2;
import ru.instamart.api.model.v2.TaxonV2;
import ru.instamart.api.request.v2.ProductsV2Request;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.core.dataprovider.RestDataProvider;
import ru.instamart.ui.data.pagesdata.UserData;

import java.util.List;

@Epic("ApiV2")
@Feature("E2E тесты")
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
    public void departmentsOnMainPages(StoreV2 store) {
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
    public void productsOnMainPages(StoreV2 store) {
        log.info("Магазин {} url={}/api/v2/departments?sid={}", store, RestAssured.baseURI, store.getId());

        final SoftAssert softAssert = new SoftAssert();
        final List<ProductV2> products = apiV2.getProductsFromEachDepartmentInStore(store.getId());
        for (final ProductV2 product : products) {
            softAssert.assertEquals(ProductsV2Request.GET(product.getId()).getStatusCode(),200,
                    "\n" + product + " " + RestAssured.baseURI + "/api/v2/products/" + product.getId());
        }
        softAssert.assertAll();
    }

    @CaseId(107)
    @Test(  dataProvider = "stores-parallel",
            dataProviderClass = RestDataProvider.class,
            description = "Тест количества товаров в таксонах",
            groups = {})
    public void categoriesProductsCount(StoreV2 store) {
        log.info(store.toString());

        final SoftAssert softAssert = new SoftAssert();
        final List<TaxonV2> taxons = apiV2.getTaxons(store.getId());
        taxons.forEach(taxon -> InstamartApiCheckpoints.checkProductsCountEqualsChildrenSum(taxon, softAssert));
        softAssert.assertAll();
    }

    @Test(  dataProvider = "stores",
            dataProviderClass = RestDataProvider.class,
            description = "Тест заказов во всех магазинах",
            groups = {})
    public void orderByStore(StoreV2 store) {
        log.info("Оформляем заказ в {}", store);

        apiV2.order(UserManager.getDefaultUser(), store.getId());
        apiV2.cancelCurrentOrder();
    }

    @CaseId(108)
    @Test(  dataProvider = "stores-parallel",
            dataProviderClass = RestDataProvider.class,
            description = "Тест первых заказов во всех магазинах",
            groups = {})
    public void firstOrderByStore(StoreV2 store) {
        log.info("Оформляем первый заказ в {}", store);

        final UserData userData = UserManager.getUser();
        RegistrationHelper.registration(userData);
        apiV2.order(userData, store.getId());
        apiV2.cancelCurrentOrder();
    }
}
