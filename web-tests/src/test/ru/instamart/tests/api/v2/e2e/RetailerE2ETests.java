package ru.instamart.tests.api.v2.e2e;

import instamart.api.common.RestBase;
import instamart.api.objects.v2.Store;
import instamart.core.testdata.UserManager;
import instamart.core.testdata.dataprovider.RestDataProvider;
import io.qase.api.annotation.CaseId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RetailerE2ETests extends RestBase {

    private static final Logger log = LoggerFactory.getLogger(RetailerE2ETests.class);

    @BeforeClass(description = "Проверка самих провайдеров")
    public void selfTest() {
        RestDataProvider.getAvailableRetailers();
    }

    @CaseId(104)
    @Test(  dataProvider = "storeOfEachRetailer",
            dataProviderClass = RestDataProvider.class,
            description = "Тест заказов у каждого ретейлера",
            groups = {})
    public void orderByRetailer(Store store) {
        apiV2.skipTestIfOnlyPickupIsAvailable(store);
        log.info("Оформляем заказ в {}", store.getName());

        apiV2.order(UserManager.getDefaultUser(), store.getId());
        apiV2.cancelCurrentOrder();
    }
}
