package ru.instamart.tests.api.v2.e2e;

import instamart.api.checkpoints.InstamartApiCheckpoints;
import instamart.api.common.RestBase;
import instamart.api.objects.v2.Product;
import instamart.api.objects.v2.Store;
import instamart.api.objects.v2.Taxon;
import instamart.api.requests.ApiV2Requests;
import instamart.core.testdata.Users;
import instamart.core.testdata.dataprovider.RestDataProvider;
import instamart.ui.common.pagesdata.UserData;
import io.qase.api.annotation.CaseId;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static instamart.core.helpers.HelperBase.verboseMessage;

public class StoreTests extends RestBase {

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
        verboseMessage(store + "\n");

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
        verboseMessage(store + "\n" + RestAssured.baseURI + "/api/v2/departments?sid=" + store.getId() + "\n");

        SoftAssert softAssert = new SoftAssert();
        List<Product> products = apiV2.getProductsFromEachDepartmentInStore(store.getId());
        for (Product product : products) {
            softAssert.assertEquals(ApiV2Requests.Products.GET(product.getId()).getStatusCode(),200,
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
        verboseMessage(store + "\n");

        SoftAssert softAssert = new SoftAssert();
        List<Taxon> taxons = apiV2.getTaxons(store.getId());
        taxons.forEach(taxon -> InstamartApiCheckpoints.assertProductsCountEqualsChildrenSum(taxon, softAssert));
        softAssert.assertAll();
    }

    @Test(  dataProvider = "stores",
            dataProviderClass = RestDataProvider.class,
            description = "Тест заказов во всех магазинах",
            groups = {})
    public void orderByStore(Store store) {
        verboseMessage("Оформляем заказ в " + store + "\n");

        apiV2.order(Users.superuser(), store.getId());
        apiV2.cancelCurrentOrder();
    }

    @CaseId(108)
    @Test(  dataProvider = "stores-parallel",
            dataProviderClass = RestDataProvider.class,
            description = "Тест первых заказов во всех магазинах",
            groups = {})
    public void firstOrderByStore(Store store) {
        verboseMessage("Оформляем первый заказ в " + store);

        final UserData user = Users.apiUser();

        apiV2.registration(user);
        apiV2.order(user, store.getId());
        apiV2.cancelCurrentOrder();
    }
}
