package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.LoyaltyPrograms;

import static ru.instamart.autotests.appmanager.ApplicationManager.session;


// Тесты заказов


public class Orders extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void preconditions() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().loginAs(session.user);
    }

    @Test(
            description = "Тестовый заказ с любимыми товарами",
            groups = {"acceptance", "regression"},
            priority = 900
    )
    public void successOrderWithFavProducts() throws Exception {
        if(kraken.detect().isFavoritesEmpty()) {
            kraken.get().page("metro");
            kraken.shopping().hitFirstItemAddToFavoritesButton();
            kraken.get().favoritesPage();
        }
        kraken.shopping().collectItems();

        Assert.assertFalse(kraken.detect().isCheckoutButtonActive(),
                "Не выполнено предусловие: корзина не набралась до суммы минимального заказа\n");

        kraken.shopping().proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется заказ с любимыми товарами\n");
    }


    @Test(
            description = "Тестовый заказ в Метро Москва",
            groups = {"regression"},
            priority = 901
    )
    public void successOrderInMetroMoscow(){
        kraken.get().page("metro");
        kraken.shipAddress().change(Addresses.Moscow.defaultAddress());
        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();

        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ в Метро Москва\n");
    }


    @Test(
            description = "Тестовый заказ в Метро Санкт-Петербург",
            groups = {"regression"},
            priority = 902
    )
    public void successOrderInMetroSaintPetersburg(){
        kraken.get().page("metro");
        kraken.shipAddress().change(Addresses.SaintPetersburg.defaultAddress());
        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();

        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется заказ в Метро Санкт-Петербург\n");
    }


    @Test(
            description = "Тестовый заказ в Метро Казань",
            groups = {"regression"},
            priority = 903
    )
    public void successOrderInMetroKazan(){
        kraken.get().page("metro");
        kraken.shipAddress().change(Addresses.Kazan.defaultAddress());
        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();

        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ в Метро Казань\n");
    }


    @Test(
            description = "Тестовый заказ в Метро Екатеринбург",
            groups = {"regression"},
            priority = 904
    )
    public void successOrderInMetroEkaterinburg(){
        kraken.get().page("metro");
        kraken.shipAddress().change(Addresses.Ekaterinburg.defaultAddress());
        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();

        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ в Метро Екатеринбург\n");
    }


    @Test(
            description = "Тестовый заказ в Нижний Новгород",
            groups = {"regression"},
            priority = 905
    )
    public void successOrderInMetroNizhnyNovgorod(){
        kraken.get().page("metro");
        kraken.shipAddress().change(Addresses.NizhnyNovgorod.defaultAddress());
        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();

        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ в Метро Нижний Новгород\n");
    }


    @Test( // TODO перенести проверку добавления карты Вкусвилл в тесты чекаута
            description = "Тестовый заказ во Вкусвилл Москва с применением бонусной программы",
            groups = {"regression"},
            priority = 906
    )
    public void successOrderInVkusvillMoscow(){
        kraken.get().page("vkusvill");
        kraken.shipAddress().change(Addresses.Moscow.testAddress());
        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();

        Assert.assertTrue(kraken.detect().isRetailerLoyaltyAvailable(),
                "Не доступна бонусная программа Вкусвилл \n");

        kraken.checkout().addLoyalty(LoyaltyPrograms.vkusvill());

        Assert.assertTrue(kraken.detect().isLoyaltyAdded(LoyaltyPrograms.vkusvill()),
                "Не применяется бонусная программа Вкусвилл\n");

        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ во Вкусвилл Москва\n");
    }


    @Test(
            description = "Тестовый заказ в Ашан Москва",
            groups = {"regression"},
            priority = 907
    )
    public void successOrderInAuchanMoscow(){
        kraken.get().page("auchan");
        kraken.shipAddress().change(Addresses.Moscow.testAddress());
        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();

        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ в Ашан Москва\n");
    }


    @Test(
            description = "Тестовый заказ в Ленту Москва",
            groups = {"regression"},
            priority = 908
    )
    public void successOrderInLentaMoscow(){
        kraken.get().page("lenta");
        kraken.shipAddress().change(Addresses.Moscow.testAddress());
        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();

        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ в Ленту Москва\n");
    }


    @AfterMethod(alwaysRun = true)
    public void postconditions()throws Exception {
        kraken.perform().cancelLastOrder();
        kraken.shipAddress().change(Addresses.Moscow.defaultAddress());
    }

}
