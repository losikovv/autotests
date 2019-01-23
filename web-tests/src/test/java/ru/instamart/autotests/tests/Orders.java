package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;


// Тесты заказов


public class Orders extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void preconditions() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().loginAs("admin");
    }

    @Test(
            description = "Тестовый заказ с любимыми товарами",
            groups = {"acceptance", "regression"},
            priority = 900
    )
    public void successOrderWithFavoriteProducts() throws Exception {
        kraken.perform().registration();
        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());

        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitAddToFavoritesButton();
        kraken.get().favoritesPage();
        kraken.shopping().collectItems();
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
                "Не оформляется заказ в Метро Москва\n");
    }


    @Test(
            description = "Тестовый заказ в Метро Казань",
            groups = {"regression"},
            priority = 902
    )
    public void successOrderInMetroKazan(){
        kraken.get().page("metro");
        kraken.shipAddress().change(Addresses.Kazan.defaultAddress());
        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();

        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется заказ в Метро Казань\n");
    }


    @Test(
            description = "Тестовый заказ в Метро Екатеринбург",
            groups = {"regression"},
            priority = 903
    )
    public void successOrderInMetroEkaterinburg(){
        kraken.get().page("metro");
        kraken.shipAddress().change(Addresses.Ekaterinburg.defaultAddress());
        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();

        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется заказ в Метро Екатеринбург\n");
    }


    @Test( // TODO перенести проверку добавления карты Вкусвилл в тесты чекаута
            description = "Тестовый заказ во Вкусвилл Москва с применением программы лояльности",
            groups = {"regression"},
            priority = 904
    )
    public void successOrderInVkusvillMoscow(){
        kraken.get().page("vkusvill");
        kraken.shipAddress().change(Addresses.Moscow.testAddress());
        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();

        Assert.assertTrue(kraken.detect().isRetailerLoyaltyAvailable(),
                "Не доступна программа лояльности Вкусвилл \n");

        kraken.checkout().addRetailerLoyalty("vkusvill");

        Assert.assertTrue(kraken.checkout().isRetailerLoyaltyApplied(),
                "Программа лояльности Вкусвилл не применена\n");


        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется заказ во Вкусвилл Москва\n");
    }


    @Test(
            description = "Тестовый заказ в Ашан Москва",
            groups = {"regression"},
            priority = 905
    )
    public void successOrderInAuchanMoscow(){
        kraken.get().page("auchan");
        kraken.shipAddress().change(Addresses.Moscow.testAddress());
        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();

        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется заказ в Ашан Москва\n");
    }


    @Test(
            description = "Тестовый заказ в Ленту Москва",
            groups = {"regression"},
            priority = 906
    )
    public void successOrderInLentaMoscow(){
        kraken.get().page("lenta");
        kraken.shipAddress().change(Addresses.Moscow.testAddress());
        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();

        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется заказ в Ленту Москва\n");
    }


    @AfterMethod(alwaysRun = true)
    public void postconditions()throws Exception {
        //kraken.perform().checkOrderDocuments();
        kraken.perform().cancelLastOrder();
        //kraken.get().baseUrl();
        kraken.shipAddress().change(Addresses.Moscow.defaultAddress());
    }

}
