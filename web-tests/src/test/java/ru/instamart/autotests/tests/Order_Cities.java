package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.appmanager.ShopHelper;

import static ru.instamart.autotests.application.Config.testCitiesOrders;

public class Order_Cities extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.get().baseUrl();
        kraken.perform().loginAs(kraken.session.admin);
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().page("metro");
    }

    @Test(enabled = testCitiesOrders,
            description = "Тест заказа в Метро Москва",
            groups = {"acceptance","regression"},
            priority = 921
    )
    public void successOrderInMoscow() {
        kraken.shipAddress().change(Addresses.Moscow.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ в Метро Москва\n");
    }


    @Test(enabled = testCitiesOrders,
            description = "Тест заказа в Метро Санкт-Петербург",
            groups = {"acceptance","regression"},
            priority = 922
    )
    public void successOrderInSaintPetersburg() {
        kraken.shipAddress().change(Addresses.SaintPetersburg.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не оформляется заказ в Метро Санкт-Петербург\n");
    }


    @Test(enabled = testCitiesOrders,
            description = "Тест заказа в Метро Казань",
            groups = {"acceptance","regression"},
            priority = 923
    )
    public void successOrderInKazan() {
        kraken.shipAddress().change(Addresses.Kazan.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ в Метро Казань\n");
    }

    @Test(enabled = testCitiesOrders,
            description = "Тест заказа в Метро Екатеринбург",
            groups = {"acceptance","regression"},
            priority = 924
    )
    public void successOrderInEkaterinburg() {
        kraken.shipAddress().change(Addresses.Ekaterinburg.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ в Метро Екатеринбург\n");
    }

    @Test(enabled = testCitiesOrders,
            description = "Тест заказа в Метро Нижний Новгород",
            groups = {"acceptance","regression"},
            priority = 925
    )
    public void successOrderInNizhnyNovgorod() {
        kraken.shipAddress().change(Addresses.NizhnyNovgorod.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ в Метро Нижний Новгород\n");
    }

    @Test(enabled = testCitiesOrders,
            description = "Тест заказа в Метро Ростов-на-Дону",
            groups = {"acceptance","regression"},
            priority = 926
    )
    public void successOrderInRostovNaDonu() {
        kraken.shipAddress().change(Addresses.RostovNaDonu.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ в Метро Ростов-на-Дону\n");
    }

    @Test(enabled = testCitiesOrders,
            description = "Тест заказа в Метро Уфа",
            groups = {"acceptance","regression"},
            priority = 927
    )
    public void successOrderInUfa() {
        kraken.shipAddress().change(Addresses.Ufa.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ в Метро Уфа\n");
    }

    @Test(enabled = testCitiesOrders,
            description = "Тест заказа в Метро Краснодар",
            groups = {"acceptance","regression"},
            priority = 928
    )
    public void successOrderInKrasnodar() {
        kraken.shipAddress().change(Addresses.Krasnodar.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ в Метро Краснодар\n");
    }

    @Test(enabled = testCitiesOrders,
            description = "Тест заказа в Метро Самара",
            groups = {"acceptance","regression"},
            priority = 929
    )
    public void successOrderInSamara() {
        kraken.shipAddress().change(Addresses.Samara.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ в Метро Самара\n");
    }

    @Test(enabled = testCitiesOrders,
            description = "Тест заказа в Метро Воронеж",
            groups = {"acceptance","regression"},
            priority = 930
    )
    public void successOrderInVoronezh() {
        kraken.shipAddress().change(Addresses.Voronezh.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ в Метро Воронеж\n");
    }

    @AfterMethod(alwaysRun = true)
    public void postconditions() {
        kraken.perform().cancelLastOrder();
    }

    @AfterClass(alwaysRun = true)
    public void resetDefaultAddress() {
        kraken.shipAddress().change(Addresses.Moscow.defaultAddress());
    }
}
