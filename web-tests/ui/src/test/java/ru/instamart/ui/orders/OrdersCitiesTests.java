package ru.instamart.ui.orders;

import ru.instamart.api.common.RestAddresses;
import ru.instamart.core.setting.Config;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.ui.module.User;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import ru.instamart.ui.TestBase;
import ru.instamart.ui.module.shop.Order;
import ru.instamart.ui.module.shop.ShippingAddressModal;

public class OrdersCitiesTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.get().baseUrl();
        User.Do.loginAs(UserManager.getDefaultAdmin());
    }

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void preconditions() {
        kraken.get().page(Config.DEFAULT_RETAILER);
    }

    @AfterMethod(alwaysRun = true,
            description ="Очищаем окружение после теста")
    public void afterTest(ITestResult result){
        Order.cancelOrder();
    }

    @AfterClass(alwaysRun = true,
            description = "Меняем дефолтный адрес доставки на Москву")
    public void resetDefaultAddress() {
        ShippingAddressModal.open();
        ShippingAddressModal.fill(RestAddresses.Moscow.defaultAddress());
        ShippingAddressModal.submit();
    }

    @Test(  description = "Тест заказа в METRO в Москве",

            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInMoscow() {
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в METRO в Москве"));
    }

    @Test(  description = "Тест заказа в METRO в Санкт-Петербурге",

            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInSaintPetersburg() {
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.SaintPetersburg.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не оформляется заказ в METRO в Санкт-Петербурге"));
    }

    @Test(  description = "Тест заказа в METRO в Казани",

            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInKazan() {
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Kazan.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в METRO в Казани"));
    }

    @Test(  description = "Тест заказа в METRO в Екатеринбурге",

            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInEkaterinburg() {
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Ekaterinburg.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в METRO в Екатеринбурге"));
    }

    @Test(  description = "Тест заказа в METRO в Нижнем Новгороде",

            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInNizhnyNovgorod() {
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.NizhnyNovgorod.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в METRO в Нижнем Новгороде"));
    }

    @Test(  description = "Тест заказа в METRO в Ростове-на-Дону",

            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderInRostovNaDonu() {
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.RostovNaDonu.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в METRO в Ростове-на-Дону"));
    }

    @Test(  description = "Тест заказа в METRO в Уфе",

            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInUfa() {
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Ufa.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в METRO в Уфе"));
    }

    @Test(  description = "Тест заказа в METRO в Краснодаре",

            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderInKrasnodar() {
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Krasnodar.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в METRO в Краснодаре"));
    }

    @Test(  description = "Тест заказа в METRO в Самаре",

            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInSamara() {
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Samara.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в METRO в Самаре"));
    }

    @Test(  description = "Тест заказа в METRO в Воронеже",

            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInVoronezh() {
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Voronezh.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в METRO в Воронеже"));
    }

    @Test(  description = "Тест заказа в METRO в Омске",

            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInOmsk() {
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Omsk.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в METRO в Омске"));
    }

    @Test(  description = "Тест заказа в METRO в Волгограде",

            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInVolgograd() {
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Volgograd.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в METRO в Волгограде"));
    }

    @Test(  description = "Тест заказа в METRO в Новосибирске",

            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInNovosibirsk() {
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Novosibirsk.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в METRO в Новосибирске"));
    }

    @Test(  description = "Тест заказа в METRO в Челябинске",

            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInChelyabinsk() {
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Chelyabinsk.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в METRO в Челябинске"));
    }

    @Test(  description = "Тест заказа в METRO в Тюмени",

            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInTyumen() {
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Tyumen.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в METRO в Тюмени"));
    }

    @Test(  description = "Тест заказа в METRO в Перми",

            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }
    ) public void successOrderInPerm() {
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Perm.defaultAddress());

        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в METRO в Перми"));
    }

}
