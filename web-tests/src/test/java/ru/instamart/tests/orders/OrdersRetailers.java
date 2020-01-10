package ru.instamart.tests.orders;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.application.AppManager;
import ru.instamart.application.Tenants;
import ru.instamart.application.lib.Addresses;
import ru.instamart.application.lib.Pages;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.rest.RestAddresses;
import ru.instamart.application.rest.RestRetailers;
import ru.instamart.tests.TestBase;

import static ru.instamart.application.Config.TestsConfiguration.OrdersTests.enableOrderRetailersTests;

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

        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress(), RestRetailers.metro);

        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
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

        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress(), RestRetailers.auchan);

        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
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

        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress(), RestRetailers.azbuka);

        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
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
        assertPageIsAvailable(Pages.Retailers.vkusvill());

        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress(), RestRetailers.vkusvill);

        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                failMessage("Не удалось оформить заказ в Вкусвилл Москва"));
    }

    @AfterMethod(alwaysRun = true)
    public void postconditions() {
        kraken.rest().cancelCurrentOrder();
    }
}
