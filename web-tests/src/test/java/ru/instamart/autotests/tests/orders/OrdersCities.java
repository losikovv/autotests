package ru.instamart.autotests.tests.orders;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.appmanager.ShopHelper;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.application.Config.TestsConfiguration.OrdersTests.enableOrderCitiesTests;

public class OrdersCities extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.get().baseUrl();
        kraken.perform().loginAs(kraken.session.admin);
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().page("metro");
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Москве",
            groups = {"acceptance","regression"},
            priority = 2301
    )
    public void successOrderInMoscow() {
        ShopHelper.ShippingAddress.change(Addresses.Moscow.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ в METRO в Москве\n");
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Санкт-Петербурге",
            groups = {"acceptance","regression"},
            priority = 2302
    )
    public void successOrderInSaintPetersburg() {
        ShopHelper.ShippingAddress.change(Addresses.SaintPetersburg.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не оформляется заказ в METRO в Санкт-Петербурге\n");
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Казани",
            groups = {"acceptance","regression"},
            priority = 2303
    )
    public void successOrderInKazan() {
        ShopHelper.ShippingAddress.change(Addresses.Kazan.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ в METRO в Казани\n");
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Екатеринбурге",
            groups = {"acceptance","regression"},
            priority = 2304
    )
    public void successOrderInEkaterinburg() {
        ShopHelper.ShippingAddress.change(Addresses.Ekaterinburg.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ в METRO в Екатеринбурге\n");
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Нижнем Новгороде",
            groups = {"acceptance","regression"},
            priority = 2305
    )
    public void successOrderInNizhnyNovgorod() {
        ShopHelper.ShippingAddress.change(Addresses.NizhnyNovgorod.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ в METRO в Нижнем Новгороде\n");
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Ростове-на-Дону",
            groups = {"acceptance","regression"},
            priority = 2306
    )
    public void successOrderInRostovNaDonu() {
        ShopHelper.ShippingAddress.change(Addresses.RostovNaDonu.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ в METRO в Ростове-на-Дону\n");
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Уфе",
            groups = {"acceptance","regression"},
            priority = 2307
    )
    public void successOrderInUfa() {
        ShopHelper.ShippingAddress.change(Addresses.Ufa.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ в METRO в Уфе\n");
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Краснодаре",
            groups = {"acceptance","regression"},
            priority = 2308
    )
    public void successOrderInKrasnodar() {
        ShopHelper.ShippingAddress.change(Addresses.Krasnodar.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ в METRO в Краснодаре\n");
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Самаре",
            groups = {"acceptance","regression"},
            priority = 2309
    )
    public void successOrderInSamara() {
        ShopHelper.ShippingAddress.change(Addresses.Samara.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ в METRO в Самаре\n");
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Воронеже",
            groups = {"acceptance","regression"},
            priority = 2310
    )
    public void successOrderInVoronezh() {
        ShopHelper.ShippingAddress.change(Addresses.Voronezh.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ в METRO в Воронеже\n");

        kraken.perform().cancelLastOrder();
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Омске",
            groups = {"acceptance","regression"},
            priority = 2311
    )
    public void successOrderInOmsk() {
        ShopHelper.ShippingAddress.change(Addresses.Omsk.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                "Не удалось оформить заказ в METRO в Омске\n");

        kraken.perform().cancelLastOrder();
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Волгограде",
            groups = {"acceptance","regression"},
            priority = 2312
    )
    public void successOrderInVolgograd() {
        ShopHelper.ShippingAddress.change(Addresses.Volgograd.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                "Не удалось оформить заказ в METRO в Волгограде\n");

        kraken.perform().cancelLastOrder();
    }

    @AfterMethod(alwaysRun = true)
    public void postconditions() {
        kraken.perform().cancelLastOrder();
    }

    @AfterClass(alwaysRun = true)
    public void resetDefaultAddress() {
        ShopHelper.ShippingAddress.change(Addresses.Moscow.defaultAddress());
    }
}
