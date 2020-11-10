package ru.instamart.tests.orders;

import instamart.api.common.RestAddresses;
import instamart.core.common.AppManager;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
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

    @AfterMethod(alwaysRun = true,
            description ="Очищаем тестовое окружение после теста")
    public void afterTest(ITestResult result) {
        kraken.apiV2().cancelCurrentOrder();
    }

    @Test(dataProvider = "cities",
            description = "Тест заказа в METRO",
            priority = 2301
    )
    public void successOrderInCities(int param) {
        System.out.println(">>> ТЕСТ С ПАРАМЕТРОМ " + param);
        kraken.apiV2().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ " + param));
    }

}
