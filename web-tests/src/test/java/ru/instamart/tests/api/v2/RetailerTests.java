package ru.instamart.tests.api.v2;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.application.AppManager;
import ru.instamart.application.rest.RestBase;
import ru.instamart.application.rest.RestDataProvider;
import ru.instamart.application.rest.RestHelper;
import ru.instamart.application.rest.objects.Store;

import java.util.List;

public class RetailerTests extends RestBase {

    @BeforeClass(description = "Проверка самих провайдеров")
    public void selfTest() {
        RestDataProvider.getAvailableRetailers();
    }

    @Test(  dataProvider = "retailers",
            dataProviderClass = RestDataProvider.class,
            description = "Тест заказов у каждого ретейлера",
            groups = {"create-order"})
    public void orderByRetailer(String slug) {
        //ToDo вынести получение stores в data provider для ускорения теста
        List<Store> stores = RestHelper.availableStores();

        int i = 0;
        while (!stores.get(i).getRetailer().getSlug().equalsIgnoreCase(slug)) i++;
        Store store = stores.get(i);

        System.out.println("Оформляем заказ в " + store.getName() + "\n");

        kraken.rest().order(AppManager.session.user, store.getLocation(), slug);
        kraken.rest().cancelCurrentOrder();
    }
}
