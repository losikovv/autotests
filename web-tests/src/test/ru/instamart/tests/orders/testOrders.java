package ru.instamart.tests.orders;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import instamart.core.common.AppManager;
import instamart.api.common.RestAddresses;
import ru.instamart.tests.TestBase;

public class testOrders extends TestBase {

    @DataProvider(name="cities")
    public static Object[][] citiesDataProvider() {
        return new Object[][] {
                { 1 },
                { 2 },
                { 3 }
        };
    }

    @Test(dataProvider = "cities",
            description = "Тест заказа в METRO",
            priority = 2301
    )
    public void successOrderInCities(int param) {
        System.out.println(">>> ТЕСТ С ПАРАМЕТРОМ " + param);
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ " + param));
    }

}
