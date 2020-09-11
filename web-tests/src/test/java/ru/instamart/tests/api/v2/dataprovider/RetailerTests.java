package ru.instamart.tests.api.v2.dataprovider;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.application.AppManager;
import ru.instamart.application.rest.RestBase;
import ru.instamart.application.rest.RestDataProvider;
import ru.instamart.application.rest.RestHelper;
import ru.instamart.application.rest.objects.Retailer;
import ru.instamart.application.rest.objects.Store;

public class RetailerTests extends RestBase {

    @BeforeClass(description = "Проверка самих провайдеров")
    public void selfTest() {
        RestDataProvider.getAvailableRetailers();
    }

    @Test(  dataProvider = "retailers",
            dataProviderClass = RestDataProvider.class,
            description = "Тест заказов у каждого ретейлера",
            groups = {"rest"})
    public void orderByRetailer(Retailer retailer) {
        Store store = RestHelper.availableStores(retailer).get(0);
        kraken.rest().skipTestIfOnlyPickupIsAvailable(store);
        System.out.println("Оформляем заказ в " + store.getName() + "\n");

        kraken.rest().order(AppManager.session.user, store.getId());
        kraken.rest().cancelCurrentOrder();
    }
}
