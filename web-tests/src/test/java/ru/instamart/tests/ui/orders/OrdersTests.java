package ru.instamart.tests.ui.orders;

import ru.instamart.api.common.RestAddresses;
import ru.instamart.core.testdata.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

public class OrdersTests extends TestBase {

    private static final Logger log = LoggerFactory.getLogger(OrdersTests.class);

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
            description = "Тест заказа в METRO"
    )
    public void successOrderInCities(int param) {
        log.info(">>> ТЕСТ С ПАРАМЕТРОМ {}", param);
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ " + param));
    }
}
