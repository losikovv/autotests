package ru.instamart.tests.api.v2.e2e;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import instamart.core.common.AppManager;
import instamart.api.common.RestBase;
import instamart.core.testdata.dataprovider.RestDataProvider;
import instamart.api.helpers.ApiV2Helper;
import instamart.api.objects.v2.Retailer;
import instamart.api.objects.v2.Store;

import static instamart.core.helpers.HelperBase.verboseMessage;

public class RetailerTests extends RestBase {

    @BeforeClass(description = "Проверка самих провайдеров")
    public void selfTest() {
        RestDataProvider.getAvailableRetailers();
    }

    @Test(  dataProvider = "retailers",
            dataProviderClass = RestDataProvider.class,
            description = "Тест заказов у каждого ретейлера",
            groups = {})
    public void orderByRetailer(Retailer retailer) {
        Store store = ApiV2Helper.availableStores(retailer).get(0);
        apiV2.skipTestIfOnlyPickupIsAvailable(store);
        verboseMessage("Оформляем заказ в " + store.getName() + "\n");

        apiV2.order(AppManager.session.user, store.getId());
        apiV2.cancelCurrentOrder();
    }
}
