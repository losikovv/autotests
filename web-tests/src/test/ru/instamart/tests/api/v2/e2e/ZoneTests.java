package ru.instamart.tests.api.v2.e2e;

import instamart.api.common.RestBase;
import instamart.api.objects.v2.Store;
import instamart.api.objects.v2.Zone;
import instamart.core.testdata.UserManager;
import instamart.core.testdata.dataprovider.RestDataProvider;
import instamart.ui.common.pagesdata.UserData;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static instamart.core.helpers.HelperBase.verboseMessage;

public class ZoneTests extends RestBase {

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

        verboseMessage("Оформляем заказ в " + store);
        verboseMessage(zoneName);
        verboseMessage(coordinates + "\n");

        final UserData userData = UserManager.getUser();
        apiV2.registration(userData);
        apiV2.order(userData, store.getId(), coordinates);
        apiV2.cancelCurrentOrder();
    }
}
