package ru.instamart.tests.api.v2.dataprovider;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import instamart.core.common.AppManager;
import instamart.ui.common.pagesdata.UserData;
import instamart.api.v2.ApiV2Requests;
import instamart.api.common.RestBase;
import instamart.core.testdata.dataprovider.RestDataProvider;
import instamart.api.v2.ApiV2Helper;
import instamart.api.v2.objects.Product;
import instamart.api.v2.objects.Store;
import instamart.api.v2.objects.Taxon;

import java.util.List;

import static instamart.api.v2.ApiV2Helper.getProductsFromEachDepartmentInStore;

public class StoreTests extends RestBase {

    @BeforeClass(description = "Проверка самих провайдеров")
    public void selfTest() {
        RestDataProvider.getAvailableStores();
    }

    @Test(  dataProvider = "stores-parallel",
            dataProviderClass = RestDataProvider.class,
            description = "Тест категорий на главных страницах всех магазинов",
            groups = {"rest"})
    public void departmentsOnMainPages(Store store) {
        System.out.println(store + "\n");

        SoftAssert softAssert = new SoftAssert();
        ApiV2Helper.getProductsFromEachDepartmentInStore(store.getId(), softAssert);
        softAssert.assertAll();
    }

    @Test(  dataProvider = "stores-parallel",
            dataProviderClass = RestDataProvider.class,
            description = "Тест продуктов на главных страницах всех магазинов",
            groups = {})
    public void productsOnMainPages(Store store) {
        System.out.println(store + "\n" + RestAssured.baseURI + "/api/v2/departments?sid=" + store.getId() + "\n");

        SoftAssert softAssert = new SoftAssert();
        List<Product> products = getProductsFromEachDepartmentInStore(store.getId());
        for (Product product : products) {
            softAssert.assertEquals(ApiV2Requests.getProducts(product.getId()).getStatusCode(),200,
                    "\n" + product + " " + RestAssured.baseURI + "/api/v2/products/" + product.getId());
        }
        softAssert.assertAll();
    }

    @Test(  dataProvider = "stores-parallel",
            dataProviderClass = RestDataProvider.class,
            description = "Тест количества товаров в таксонах",
            groups = {})
    public void categoriesProductsCount(Store store) {
        System.out.println(store + "\n");

        SoftAssert softAssert = new SoftAssert();
        List<Taxon> taxons = ApiV2Helper.getCategories(store.getId());
        for (Taxon taxon : taxons) {
            ApiV2Helper.assertProductsCountEqualsChildrenSum(taxon, softAssert);
        }
        softAssert.assertAll();
    }

    @Test(  dataProvider = "stores",
            dataProviderClass = RestDataProvider.class,
            description = "Тест заказов во всех магазинах",
            groups = {})
    public void orderByStore(Store store) {
        System.out.println("Оформляем заказ в " + store + "\n");

        kraken.apiV2().order(AppManager.session.user, store.getId());
        kraken.apiV2().cancelCurrentOrder();
    }

    @Test(  dataProvider = "stores-parallel",
            dataProviderClass = RestDataProvider.class,
            description = "Тест первых заказов во всех магазинах",
            groups = {})
    public void firstOrderByStore(Store store) {
        System.out.println("Оформляем первый заказ в " + store);

        UserData user = user();

        kraken.apiV2().registration(user);
        kraken.apiV2().order(user, store.getId());
        kraken.apiV2().cancelCurrentOrder();
    }
}
