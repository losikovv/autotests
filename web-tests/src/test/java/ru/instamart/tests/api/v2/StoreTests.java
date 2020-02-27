package ru.instamart.tests.api.v2;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.application.AppManager;
import ru.instamart.application.models.UserData;
import ru.instamart.application.rest.RestBase;
import ru.instamart.application.rest.RestDataProvider;
import ru.instamart.application.rest.RestHelper;
import ru.instamart.application.rest.objects.Store;

import java.util.UUID;

public class StoreTests extends RestBase {

    @BeforeClass(description = "Проверка самих провайдеров")
    public void selfTest() {
        RestDataProvider.getAvailableStores();
    }

    @Test(  dataProvider = "stores",
            dataProviderClass = RestDataProvider.class,
            description = "Тест категорий на главных страницах всех магазинов",
            groups = {})
    public void departmentsOnMainPages(Store store) {

        System.out.println(store + "\n");

        RestHelper.getProductsFromEachDepartmentInStore(
                store.getId(),
                6, // 6 is max
                false);
    }

    @Test(  dataProvider = "stores",
            dataProviderClass = RestDataProvider.class,
            description = "Тест заказов во всех магазинах",
            groups = {})
    public void orderByStore(Store store) {

        System.out.println("Оформляем заказ в " + store + "\n");

        kraken.rest().order(AppManager.session.user, store.getId());
        kraken.rest().cancelCurrentOrder();
    }

    @Test(  dataProvider = "stores",
            dataProviderClass = RestDataProvider.class,
            description = "Тест первых заказов во всех магазинах",
            groups = {})
    public void firstOrderByStore(Store store) {

        System.out.println("Оформляем первый заказ в " + store);

        UserData user = new UserData(
                UUID.randomUUID() + "@example.com",
                "instamart",
                "Василий Автотестов");

        kraken.rest().registration(user);
        kraken.rest().order(user, store.getId());
        kraken.rest().cancelCurrentOrder();
    }
}
