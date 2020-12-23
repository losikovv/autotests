package ru.instamart.tests.api.v2.e2e;

import instamart.api.common.RestBase;
import instamart.api.objects.v2.Store;
import instamart.api.objects.v2.Zone;
import instamart.core.testdata.UserManager;
import instamart.core.testdata.dataprovider.RestDataProvider;
import instamart.ui.common.pagesdata.UserData;
import io.qase.api.annotation.CaseId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ZoneTests extends RestBase {

    private static final Logger log = LoggerFactory.getLogger(ZoneTests.class);

    @BeforeClass(description = "Проверка самих провайдеров")
    public void selfTest() {
        RestDataProvider.getAvailableZones();
    }

    @CaseId(109)
    @Test(  dataProvider = "zones-parallel",
            dataProviderClass = RestDataProvider.class,
            description = "Тест первых заказов во всех зонах всех магазинов",
            groups = {"api-zones"})
    public void firstOrderByZone(Store store, String zoneName, Zone coordinates) {
        apiV2.skipTestIfOnlyPickupIsAvailable(store, zoneName);

        log.info("Оформляем заказ в {} zone={} coordinates={}", store, zoneName, coordinates);

        final UserData userData = UserManager.getUser();
        apiV2.registration(userData);
        apiV2.order(userData, store.getId(), coordinates);
        apiV2.cancelCurrentOrder();
    }
}
