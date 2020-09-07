package ru.instamart.tests.api.v2;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.application.AppManager;
import ru.instamart.application.models.UserData;
import ru.instamart.application.rest.Requests;
import ru.instamart.application.rest.RestBase;
import ru.instamart.application.rest.RestDataProvider;
import ru.instamart.application.rest.RestHelper;
import ru.instamart.application.rest.objects.Product;
import ru.instamart.application.rest.objects.Store;
import ru.instamart.application.rest.objects.Taxon;

import java.util.List;
import java.util.UUID;

import static ru.instamart.application.rest.RestHelper.getProductsFromEachDepartmentInStore;

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
        RestHelper.getProductsFromEachDepartmentInStore(store.getId(), softAssert);
        softAssert.assertAll();
    }

    @Test(  dataProvider = "stores-parallel",
            dataProviderClass = RestDataProvider.class,
            description = "Тест продуктов на главных страницах всех магазинов",
            groups = {"rest"})
    public void productsOnMainPages(Store store) {
        System.out.println(store + "\n" + RestAssured.baseURI + "/api/v2/departments?sid=" + store.getId() + "\n");

        SoftAssert softAssert = new SoftAssert();
        List<Product> products = getProductsFromEachDepartmentInStore(store.getId());
        for (Product product : products) {
            softAssert.assertEquals(Requests.getProducts(product.getId()).getStatusCode(),200,
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
        List<Taxon> taxons = RestHelper.getCategories(store.getId());
        for (Taxon taxon : taxons) {
            RestHelper.assertProductsCountEqualsChildrenSum(taxon, softAssert);
        }
        softAssert.assertAll();
    }

    @Test(  dataProvider = "stores",
            dataProviderClass = RestDataProvider.class,
            description = "Тест заказов во всех магазинах",
            groups = {"create-order"})
    public void orderByStore(Store store) {
        System.out.println("Оформляем заказ в " + store + "\n");

        kraken.rest().order(AppManager.session.user, store.getId());
        kraken.rest().cancelCurrentOrder();
    }

    @Test(  dataProvider = "stores-parallel",
            dataProviderClass = RestDataProvider.class,
            description = "Тест первых заказов во всех магазинах",
            groups = {"create-order"})
    public void firstOrderByStore(Store store) {
        System.out.println("Оформляем первый заказ в " + store);

        //todo разобраться с рандомными заказами
        UserData user = new UserData(
                UUID.randomUUID() + "@example.com",
                "instamart",
                "Василий Автотестов");

        kraken.rest().registration(user);
        kraken.rest().order(user, store.getId());
        kraken.rest().cancelCurrentOrder();
    }
}
