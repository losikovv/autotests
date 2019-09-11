package ru.instamart.tests.orders;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.lib.Addresses;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.tests.TestBase;

import static ru.instamart.application.Config.TestsConfiguration.OrdersTests.enableOrderCitiesTests;

public class OrdersCities extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.get().baseUrl();
        User.Do.loginAs(kraken.session.admin);
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().page("metro");
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Москве",
            priority = 2301,
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression"
            }
    ) public void successOrderInMoscow() {
        User.ShippingAddress.change(Addresses.Moscow.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Москве"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Санкт-Петербурге",
            priority = 2302,
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression"
            }
    ) public void successOrderInSaintPetersburg() {
        User.ShippingAddress.change(Addresses.SaintPetersburg.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не оформляется заказ в METRO в Санкт-Петербурге"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Казани",
            priority = 2303,
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression"
            }
    ) public void successOrderInKazan() {
        User.ShippingAddress.change(Addresses.Kazan.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Казани"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Екатеринбурге",
            priority = 2304,
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression"
            }
    ) public void successOrderInEkaterinburg() {
        User.ShippingAddress.change(Addresses.Ekaterinburg.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Екатеринбурге"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Нижнем Новгороде",
            priority = 2305,
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression"
            }
    ) public void successOrderInNizhnyNovgorod() {
        User.ShippingAddress.change(Addresses.NizhnyNovgorod.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Нижнем Новгороде"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Ростове-на-Дону",
            priority = 2306,
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderInRostovNaDonu() {
        User.ShippingAddress.change(Addresses.RostovNaDonu.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Ростове-на-Дону"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Уфе",
            priority = 2307,
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression"
            }
    ) public void successOrderInUfa() {
        User.ShippingAddress.change(Addresses.Ufa.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Уфе"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Краснодаре",
            priority = 2308,
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderInKrasnodar() {
        User.ShippingAddress.change(Addresses.Krasnodar.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Краснодаре"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Самаре",
            priority = 2309,
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression"
            }
    ) public void successOrderInSamara() {
        User.ShippingAddress.change(Addresses.Samara.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Самаре"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Воронеже",
            priority = 2310,
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression"
            }
    ) public void successOrderInVoronezh() {
        User.ShippingAddress.change(Addresses.Voronezh.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Воронеже"));

        kraken.perform().cancelLastOrder();
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Омске",
            priority = 2311,
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression"
            }
    ) public void successOrderInOmsk() {
        User.ShippingAddress.change(Addresses.Omsk.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Омске"));

        kraken.perform().cancelLastOrder();
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Волгограде",
            priority = 2312,
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression"
            }
    ) public void successOrderInVolgograd() {
        User.ShippingAddress.change(Addresses.Volgograd.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Волгограде"));

        kraken.perform().cancelLastOrder();
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Новосибирске",
            priority = 2313,
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression"
            }
    ) public void successOrderInNovosibirsk() {
        User.ShippingAddress.change(Addresses.Novosibirsk.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Новосибирске"));

        kraken.perform().cancelLastOrder();
    }

    @AfterMethod(alwaysRun = true)
    public void postconditions() {
        kraken.perform().cancelLastOrder();
    }

    @AfterClass(alwaysRun = true)
    public void resetDefaultAddress() {
        User.ShippingAddress.change(Addresses.Moscow.defaultAddress());
    }
}
