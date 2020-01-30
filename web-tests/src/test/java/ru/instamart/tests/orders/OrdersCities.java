package ru.instamart.tests.orders;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.instamart.application.AppManager;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.rest.RestAddresses;
import ru.instamart.tests.TestBase;

import static ru.instamart.application.Config.TestsConfiguration.OrdersTests.enableOrderCitiesTests;

public class OrdersCities extends TestBase {

    @DataProvider(name="city")
    public static Object[][] cityDataProvider() {
        return new Object[][] { { 1 }, { 2 }, { 3 } };
    }

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.get().baseUrl();
        User.Do.loginAs(AppManager.session.admin);
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().page("metro");
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Москве",
            priority = 2301,
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInMoscow() {
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Москве"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Санкт-Петербурге",
            priority = 2302,
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInSaintPetersburg() {
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.SaintPetersburg.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не оформляется заказ в METRO в Санкт-Петербурге"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Казани",
            priority = 2303,
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInKazan() {
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Kazan.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Казани"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Екатеринбурге",
            priority = 2304,
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInEkaterinburg() {
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Ekaterinburg.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Екатеринбурге"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Нижнем Новгороде",
            priority = 2305,
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInNizhnyNovgorod() {
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.NizhnyNovgorod.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Нижнем Новгороде"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Ростове-на-Дону",
            priority = 2306,
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderInRostovNaDonu() {
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.RostovNaDonu.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Ростове-на-Дону"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Уфе",
            priority = 2307,
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInUfa() {
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Ufa.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Уфе"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Краснодаре",
            priority = 2308,
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderInKrasnodar() {
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Krasnodar.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Краснодаре"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Самаре",
            priority = 2309,
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInSamara() {
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Samara.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Самаре"));
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Воронеже",
            priority = 2310,
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInVoronezh() {
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Voronezh.defaultAddress());

        kraken.reach().checkout();
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
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInOmsk() {
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Omsk.defaultAddress());

        kraken.reach().checkout();
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
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInVolgograd() {
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Volgograd.defaultAddress());

        kraken.reach().checkout();
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
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInNovosibirsk() {
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Novosibirsk.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Новосибирске"));

        kraken.perform().cancelLastOrder();
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Челябинске",
            priority = 2314,
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInChelyabinsk() {
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Chelyabinsk.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Челябинске"));

        kraken.perform().cancelLastOrder();
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Тюмени",
            priority = 2315,
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInTyumen() {
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Tyumen.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Тюмени"));

        kraken.perform().cancelLastOrder();
    }

    @Test(enabled = enableOrderCitiesTests,
            description = "Тест заказа в METRO в Перми",
            priority = 2316,
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInPerm() {
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Perm.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    failMessage("Не удалось оформить заказ в METRO в Gthvb"));

        kraken.perform().cancelLastOrder();
    }

    @AfterMethod(alwaysRun = true)
    public void postconditions() {
        kraken.rest().cancelCurrentOrder();
    }

    @AfterClass(alwaysRun = true)
    public void resetDefaultAddress() {
        User.ShippingAddress.set(RestAddresses.Moscow.defaultAddress());
    }
}
