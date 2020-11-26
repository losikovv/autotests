package ru.instamart.tests.orders;

import instamart.api.common.RestAddresses;
import instamart.core.common.AppManager;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.common.lib.Addresses;
import instamart.ui.common.lib.Pages;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.tests.TestBase;

import static instamart.core.settings.Config.TestsConfiguration.OrdersTests.enableOrderRetailersTests;
import static instamart.ui.common.lib.Retailers.*;

public class OrdersRetailers extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    @BeforeClass(alwaysRun = true,
            description = "Подготавливаем тестовое окружение к прогону тестов")
    public void setup() {
        kraken.get().baseUrl();
        User.Do.loginAs(AppManager.session.admin);
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
    }

    @AfterMethod(alwaysRun = true,
            description ="Очищаем тестовое окружение после теста")
    public void afterTest(ITestResult result) {
        kraken.apiV2().cancelCurrentOrder();
    }

    @Test(enabled = enableOrderRetailersTests,
            description = "Тестовый заказ в Метро Москва",
            priority = 2401,
            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderInMetro(){
        baseChecks.checkPageIsAvailable(Pages.Retailers.metro());

        kraken.apiV2().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress(), metro);

        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в Метро Москва"));
    }

    @Test(enabled = enableOrderRetailersTests,
            description = "Тестовый заказ в Ашан Москва",
            priority = 2402,
            groups = {
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderInAuchan(){
        runTestOnlyOnTenant("sbermarket");
        baseChecks.checkPageIsAvailable(Pages.Retailers.auchan());

        kraken.apiV2().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress(), auchan);

        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в Ашан Москва"));
    }

    @Test(enabled = enableOrderRetailersTests,
            description = "Тестовый заказ в Азбука Вкуса Москва",
            priority = 2403,
            groups = {
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderInAzbukaVkusa(){
        runTestOnlyOnTenant("sbermarket");
        baseChecks.checkPageIsAvailable(Pages.Retailers.azbuka());

        kraken.apiV2().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress(), azbuka);

        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в Азбука Вкуса Москва"));
    }

    @Test(enabled = enableOrderRetailersTests,
            description = "Тестовый заказ в Вкусвилл Москва",
            priority = 2404,
            groups = {
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderInVkusvill(){
        runTestOnlyOnTenant("sbermarket");
        baseChecks.checkRetailerIsAvailable(vkusvill);
        kraken.apiV2().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress(), vkusvill);

        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                failMessage("Не удалось оформить заказ в Вкусвилл Москва"));
    }

    @Test(enabled = enableOrderRetailersTests,
            description = "Тестовый заказ в Лента Москва",
            priority = 2405,
            groups = {
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderInLenta(){
        runTestOnlyOnTenant("sbermarket");
        baseChecks.checkRetailerIsAvailable(lenta);
        kraken.apiV2().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress(), lenta);

        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в Лента Москва"));
    }


}
