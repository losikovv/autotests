package ru.instamart.tests.api.v2;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.application.AppManager;
import ru.instamart.application.rest.RestBase;
import ru.instamart.application.rest.RestDataProvider;
import ru.instamart.application.rest.RestHelper;
import ru.instamart.application.rest.objects.Address;
import ru.instamart.application.rest.objects.Store;

import java.util.List;

public class RetailerTests extends RestBase {

    @BeforeClass(description = "Проверка самих провайдеров",
            groups = {})
    public void selfTest() {
        RestDataProvider.getAvailableRetailers();
    }

    @Test(  dataProvider = "retailers",
            dataProviderClass = RestDataProvider.class,
            description = "Тест заказов у каждого ретейлера",
            groups = {})
    public void orderByRetailer(String slug) {
        List<Store> stores = RestHelper.availableStores();

        int i = 0;
        while (!stores.get(i).getRetailer().getSlug().equalsIgnoreCase(slug)) i++;
        Store store = stores.get(i);

        System.out.println("!!! Оформляем заказ в " + store.getName() + " !!!");

        kraken.rest().order(AppManager.session.user, new Address(store.getLocation()), slug);
        kraken.rest().cancelCurrentOrder();
    }
}
