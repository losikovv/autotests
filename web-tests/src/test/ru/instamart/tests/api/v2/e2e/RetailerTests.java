package ru.instamart.tests.api.v2.e2e;

import instamart.api.common.RestBase;
import instamart.api.objects.v2.Retailer;
import instamart.api.objects.v2.Store;
import instamart.core.common.AppManager;
import instamart.core.testdata.dataprovider.RestDataProvider;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static instamart.core.helpers.HelperBase.verboseMessage;

public class RetailerTests extends RestBase {

    @BeforeClass(description = "Проверка самих провайдеров")
    public void selfTest() {
        RestDataProvider.getAvailableRetailers();
    }

    @CaseId(104)
    @Test(  dataProvider = "retailers",
            dataProviderClass = RestDataProvider.class,
            description = "Тест заказов у каждого ретейлера",
            groups = {})
    public void orderByRetailer(Retailer retailer) {
        Store store = apiV2.availableStores(retailer).get(0);
        apiV2.skipTestIfOnlyPickupIsAvailable(store);
        verboseMessage("Оформляем заказ в " + store.getName() + "\n");

        apiV2.order(AppManager.session.user, store.getId());
        apiV2.cancelCurrentOrder();
    }
}
