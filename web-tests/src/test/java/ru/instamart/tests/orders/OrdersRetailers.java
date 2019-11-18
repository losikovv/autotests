package ru.instamart.tests.orders;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.instamart.application.lib.Addresses;
import ru.instamart.application.Tenants;
import ru.instamart.application.lib.Pages;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.platform.modules.User;
import ru.instamart.tests.TestBase;

import static ru.instamart.application.Config.TestsConfiguration.OrdersTests.enableOrderRetailersTests;

public class OrdersRetailers extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.get().baseUrl();
        User.Do.loginAs(kraken.session.admin);
        User.ShippingAddress.change(Addresses.Moscow.testAddress());
    }

    @Test(enabled = enableOrderRetailersTests,
            description = "Тестовый заказ в Метро Москва",
            priority = 2401,
            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderInMetro(){
        assertPageIsAvailable(Pages.Site.Retailers.metro());
        Shop.Cart.drop();

        Shop.Cart.collect();
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
        skipTestOn(Tenants.metro());
        assertPageIsAvailable(Pages.Site.Retailers.auchan());
        Shop.Cart.drop();

        Shop.Cart.collect();
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
        skipTestOn(Tenants.metro());
        assertPageIsAvailable(Pages.Site.Retailers.azbuka());
        Shop.Cart.drop();

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в Азбука Вкуса Москва"));
    }

    @AfterMethod(alwaysRun = true)
    public void postconditions() {
        kraken.perform().cancelLastOrder();
    }
}
