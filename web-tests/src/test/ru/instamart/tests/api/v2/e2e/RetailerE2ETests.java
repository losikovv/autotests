package ru.instamart.tests.api.v2.e2e;

import instamart.api.common.RestBase;
import instamart.api.helpers.RegistrationHelper;
import instamart.api.objects.v2.Store;
import instamart.core.testdata.UserManager;
import instamart.core.testdata.dataprovider.RestDataProvider;
import instamart.ui.common.pagesdata.UserData;
import instamart.ui.helpers.WaitingHelper;
import io.qase.api.annotation.CaseId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public final class RetailerE2ETests extends RestBase {

    private static final Logger log = LoggerFactory.getLogger(RetailerE2ETests.class);

    @BeforeClass(description = "Проверка самих провайдеров")
    public void selfTest() {
        RestDataProvider.getAvailableRetailers();
    }

    @CaseId(104)
    @Test(  dataProvider = "storeOfEachRetailer",
            dataProviderClass = RestDataProvider.class,
            description = "Тест заказов у каждого ретейлера",
            groups = {"api-retailer"})
    public void orderByRetailer(final Store store) {
        apiV2.skipTestIfOnlyPickupIsAvailable(store);

        final UserData userData = UserManager.getUser();
        RegistrationHelper.registration(userData);

        log.info("Оформляем заказ в {}", store.getName());
        apiV2.order(userData, store.getId());
        log.info("Отменяем заказ в {}", store.getName());
        apiV2.cancelCurrentOrder();
        WaitingHelper.simply(30);
    }
}
