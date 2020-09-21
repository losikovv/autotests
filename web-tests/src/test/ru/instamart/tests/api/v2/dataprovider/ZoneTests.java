package ru.instamart.tests.api.v2.dataprovider;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import instamart.ui.common.pagesdata.UserData;
import instamart.api.common.RestBase;
import instamart.core.testdata.dataprovider.RestDataProvider;
import instamart.api.objects.Store;
import instamart.api.objects.Zone;

public class ZoneTests extends RestBase {

    @BeforeClass(description = "Проверка самих провайдеров")
    public void selfTest() {
        RestDataProvider.getAvailableZones();
    }

    @Test(  dataProvider = "zones-parallel",
            dataProviderClass = RestDataProvider.class,
            description = "Тест первых заказов во всех зонах всех магазинов",
            groups = {"rest-zones"})
    public void firstOrderByZone(Store store, String zoneName, Zone coordinates) {
        kraken.rest().skipTestIfOnlyPickupIsAvailable(store, zoneName);

        System.out.println("Оформляем заказ в " + store);
        System.out.println(zoneName);
        System.out.println(coordinates + "\n");

        UserData user = user();

        kraken.rest().registration(user);
        kraken.rest().order(user, store.getId(), coordinates);
        kraken.rest().cancelCurrentOrder();
    }
}
