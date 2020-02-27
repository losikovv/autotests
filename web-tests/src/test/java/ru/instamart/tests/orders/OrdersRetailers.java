package ru.instamart.tests.orders;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.application.AppManager;
import ru.instamart.application.lib.Addresses;
import ru.instamart.application.lib.Pages;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.rest.RestAddresses;
import ru.instamart.tests.TestBase;

import static ru.instamart.application.Config.TestsConfiguration.OrdersTests.enableOrderRetailersTests;
import static ru.instamart.application.lib.Retailers.*;

public class OrdersRetailers extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.get().baseUrl();
        User.Do.loginAs(AppManager.session.admin);
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());
    }

    @Test(enabled = enableOrderRetailersTests,
            description = "Тестовый заказ в Метро Москва",
            priority = 2401,
            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderInMetro(){
        assertPageIsAvailable(Pages.Retailers.metro());

        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress(), metro);

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
        assertPageIsAvailable(Pages.Retailers.auchan());

        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress(), auchan);

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
        assertPageIsAvailable(Pages.Retailers.azbuka());

        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress(), azbuka);

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
        assertRetailerIsAvailable(vkusvill);
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress(), vkusvill);

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
        assertRetailerIsAvailable(lenta);
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress(), lenta);

        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в Лента Москва"));
    }

    @AfterMethod(alwaysRun = true)
    public void postconditions() {
        kraken.rest().cancelCurrentOrder();
    }
}
