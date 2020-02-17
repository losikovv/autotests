package ru.instamart.tests.api.v2;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.application.AppManager;
import ru.instamart.application.rest.RestBase;
import ru.instamart.application.rest.RestDataProvider;

public class ZoneTests extends RestBase {

    @BeforeClass(description = "Проверка самих провайдеров")
    public void selfTest() {
        RestDataProvider.getAvailableZones();
    }

    @Test(  dataProvider = "zones",
            dataProviderClass = RestDataProvider.class,
            description = "Тест заказов во всех зонах всех магазинов",
            groups = {})
    public void orderByZone(String storeName, int sid, String zoneName, double lat, double lon) {

        System.out.println("Оформляем заказ в " + storeName);
        System.out.println("sid: " + sid);
        System.out.println(zoneName);
        System.out.println("lat: " + lat);
        System.out.println("lon: " + lon);
        System.out.println();

        kraken.rest().order(AppManager.session.user, sid, lat, lon);
        kraken.rest().cancelCurrentOrder();
    }
}
