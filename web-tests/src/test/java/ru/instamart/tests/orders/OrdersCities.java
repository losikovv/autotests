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
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2301
    )
    public void successOrderInMoscow() {
        Shop.ShippingAddress.change(Addresses.Moscow.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Москве"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Санкт-Петербурге",
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2302
    )
    public void successOrderInSaintPetersburg() {
        Shop.ShippingAddress.change(Addresses.SaintPetersburg.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не оформляется заказ в METRO в Санкт-Петербурге"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Казани",
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2303
    )
    public void successOrderInKazan() {
        Shop.ShippingAddress.change(Addresses.Kazan.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Казани"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Екатеринбурге",
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2304
    )
    public void successOrderInEkaterinburg() {
        Shop.ShippingAddress.change(Addresses.Ekaterinburg.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Екатеринбурге"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Нижнем Новгороде",
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2305
    )
    public void successOrderInNizhnyNovgorod() {
        Shop.ShippingAddress.change(Addresses.NizhnyNovgorod.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Нижнем Новгороде"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Ростове-на-Дону",
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2306
    )
    public void successOrderInRostovNaDonu() {
        Shop.ShippingAddress.change(Addresses.RostovNaDonu.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Ростове-на-Дону"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Уфе",
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2307
    )
    public void successOrderInUfa() {
        Shop.ShippingAddress.change(Addresses.Ufa.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Уфе"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Краснодаре",
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2308
    )
    public void successOrderInKrasnodar() {
        Shop.ShippingAddress.change(Addresses.Krasnodar.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Краснодаре"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Самаре",
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2309
    )
    public void successOrderInSamara() {
        Shop.ShippingAddress.change(Addresses.Samara.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Самаре"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Воронеже",
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2310
    )
    public void successOrderInVoronezh() {
        Shop.ShippingAddress.change(Addresses.Voronezh.defaultAddress());

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
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2311
    )
    public void successOrderInOmsk() {
        Shop.ShippingAddress.change(Addresses.Omsk.defaultAddress());

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
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2312
    )
    public void successOrderInVolgograd() {
        Shop.ShippingAddress.change(Addresses.Volgograd.defaultAddress());

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
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2313
    )
    public void successOrderInNovosibirsk() {
        Shop.ShippingAddress.change(Addresses.Novosibirsk.defaultAddress());

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
        Shop.ShippingAddress.change(Addresses.Moscow.defaultAddress());
    }
}
