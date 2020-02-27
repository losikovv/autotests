package ru.instamart.tests.api.v2;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.application.AppManager;
import ru.instamart.application.rest.RestBase;
import ru.instamart.application.rest.RestDataProvider;
import ru.instamart.application.rest.objects.Store;
import ru.instamart.application.rest.objects.Zone;

public class ZoneTests extends RestBase {

    @BeforeClass(description = "Проверка самих провайдеров")
    public void selfTest() {
        RestDataProvider.getAvailableZones();
    }

    @Test(  dataProvider = "zones",
            dataProviderClass = RestDataProvider.class,
            description = "Тест заказов во всех зонах всех магазинов",
            groups = {})
    public void orderByZone(Store store, String zoneName, Zone coordinates) {

        System.out.println("Оформляем заказ в " + store);
        System.out.println(zoneName);
        System.out.println(coordinates + "\n");

        kraken.rest().order(AppManager.session.user, store.getId(), coordinates);
        kraken.rest().cancelCurrentOrder();
    }
}
