package ru.instamart.autotests.tests.orders;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.instamart.autotests.application.libs.Addresses;
import ru.instamart.autotests.application.Tenants;
import ru.instamart.autotests.appmanager.platform.Shop;
import ru.instamart.autotests.appmanager.platform.User;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.application.Config.TestsConfiguration.OrdersTests.enableOrderRetailersTests;

public class OrdersRetailers extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.get().baseUrl();
        User.Do.loginAs(kraken.session.admin);
        Shop.ShippingAddress.change(Addresses.Moscow.testAddress());
    }

    @Test(enabled = enableOrderRetailersTests,
            description = "Тестовый заказ в Метро Москва",
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2401
    )
    public void successOrderInMetro(){
        kraken.get().page("metro");
        Shop.Cart.drop();

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ в Метро Москва\n");
    }

    @Test(enabled = enableOrderRetailersTests,
            description = "Тестовый заказ в Ашан Москва",
            groups = {
                    "acceptance", "regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2402
    )
    public void successOrderInAuchan(){
        skipTestOn(Tenants.metro());
        kraken.get().page("auchan");
        Shop.Cart.drop();

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ в Ашан Москва\n");
    }

    @AfterMethod(alwaysRun = true)
    public void postconditions() {
        kraken.perform().cancelLastOrder();
    }
}
