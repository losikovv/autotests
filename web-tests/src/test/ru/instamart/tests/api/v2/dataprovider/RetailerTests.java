package ru.instamart.tests.api.v2.dataprovider;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import instamart.core.common.AppManager;
import instamart.api.common.RestBase;
import instamart.core.testdata.dataprovider.RestDataProvider;
import instamart.api.v2.ApiV2Helper;
import instamart.api.v2.objects.Retailer;
import instamart.api.v2.objects.Store;

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
        Store store = ApiV2Helper.availableStores(retailer).get(0);
        kraken.apiV2().skipTestIfOnlyPickupIsAvailable(store);
        System.out.println("Оформляем заказ в " + store.getName() + "\n");

        kraken.apiV2().order(AppManager.session.user, store.getId());
        kraken.apiV2().cancelCurrentOrder();
    }
}
