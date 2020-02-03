package ru.instamart.tests.api.v2;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.application.AppManager;
import ru.instamart.application.models.UserData;
import ru.instamart.application.rest.RestBase;
import ru.instamart.application.rest.RestDataProvider;
import ru.instamart.application.rest.RestHelper;

import java.util.UUID;

public class StoreTests extends RestBase {

    @BeforeClass(description = "Проверка самих провайдеров",
            groups = {})
    public void selfTest() {
        RestDataProvider.getAvailableStores();
    }

    @Test(  dataProvider = "stores",
            dataProviderClass = RestDataProvider.class,
            description = "Тест категорий на главных станицах всех магазинов",
            groups = {})
    public void departmentsOnMainPages(String name, int sid) {

        System.out.println(name + "\n");

        RestHelper.getProductsFromEachDepartmentInStore(
                sid,
                1,
                false);
    }

    @Test(  dataProvider = "stores",
            dataProviderClass = RestDataProvider.class,
            description = "Тест заказов во всех магазинах",
            groups = {})
    public void orderByStore(String name, int sid) {

        System.out.println("!!! Оформляем заказ в " + name + " !!!");

        kraken.rest().order(AppManager.session.user, sid);
        kraken.rest().cancelCurrentOrder();
    }

    @Test(  dataProvider = "stores",
            dataProviderClass = RestDataProvider.class,
            description = "Тест первых заказов во всех магазинах",
            groups = {})
    public void firstOrderByStore(String name, int sid) {

        System.out.println("!!! Оформляем первый заказ в " + name + " !!!");

        UserData user = new UserData(
                UUID.randomUUID() + "@example.com",
                "instamart",
                "Василий Автотестов");

        kraken.rest().registration(user);
        kraken.rest().order(user, sid);
        kraken.rest().cancelCurrentOrder();
    }
}
