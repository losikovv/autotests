package ru.instamart.tests.api.v2.dataprovider;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.application.models.UserData;
import ru.instamart.application.rest.RestBase;
import ru.instamart.application.rest.RestDataProvider;
import ru.instamart.application.rest.objects.Store;
import ru.instamart.application.rest.objects.Zone;

public class ZoneTests extends RestBase {

    @BeforeClass(description = "Проверка самих провайдеров")
    public void selfTest() {
        RestDataProvider.getAvailableZones();
    }

    @Test(  dataProvider = "zones-parallel",
            dataProviderClass = RestDataProvider.class,
            description = "Тест первых заказов во всех зонах всех магазинов",
            groups = {"rest","rest-zones"})
    public void firstOrderByZone(Store store, String zoneName, Zone coordinates) {
        System.out.println("Оформляем заказ в " + store);
        System.out.println(zoneName);
        System.out.println(coordinates + "\n");

        UserData user = user();

        kraken.rest().registration(user);
        kraken.rest().order(user, store.getId(), coordinates);
        kraken.rest().cancelCurrentOrder();
    }
}
