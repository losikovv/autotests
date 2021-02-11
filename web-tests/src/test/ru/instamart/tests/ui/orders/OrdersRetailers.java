package ru.instamart.tests.ui.orders;

import instamart.api.common.RestAddresses;
import instamart.core.testdata.UserManager;
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
import ru.instamart.tests.ui.TestBase;

import static instamart.ui.common.lib.Retailers.*;

public class OrdersRetailers extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    @BeforeClass(alwaysRun = true,
            description = "Подготавливаем тестовое окружение к прогону тестов")
    public void setup() {
        kraken.get().baseUrl();
        User.Do.loginAs(UserManager.getDefaultAdmin());
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
    }

    @AfterMethod(alwaysRun = true,
            description ="Очищаем тестовое окружение после теста")
    public void afterTest(ITestResult result) {
        kraken.apiV2().cancelCurrentOrder();
    }

    @Test(description = "Тестовый заказ в Метро Москва",

            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderInMetro(){
        baseChecks.checkPageIsAvailable(Pages.Retailers.metro());

        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress(), metro);

        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в Метро Москва"));
    }

    @Test(description = "Тестовый заказ в Ашан Москва",

            groups = {
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderInAuchan(){
        runTestOnlyOnTenant("sbermarket");
        baseChecks.checkPageIsAvailable(Pages.Retailers.auchan());

        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress(), auchan);

        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в Ашан Москва"));
    }

    @Test(description = "Тестовый заказ в Азбука Вкуса Москва",

            groups = {
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderInAzbukaVkusa(){
        runTestOnlyOnTenant("sbermarket");
        baseChecks.checkPageIsAvailable(Pages.Retailers.azbuka());

        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress(), azbuka);

        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в Азбука Вкуса Москва"));
    }

    @Test(description = "Тестовый заказ в Вкусвилл Москва",

            groups = {
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderInVkusvill(){
        runTestOnlyOnTenant("sbermarket");
        baseChecks.checkRetailerIsAvailable(vkusvill);
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress(), vkusvill);

        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                failMessage("Не удалось оформить заказ в Вкусвилл Москва"));
    }

    @Test(description = "Тестовый заказ в Лента Москва",

            groups = {
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderInLenta(){
        runTestOnlyOnTenant("sbermarket");
        baseChecks.checkRetailerIsAvailable(lenta);
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress(), lenta);

        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в Лента Москва"));
    }


}
