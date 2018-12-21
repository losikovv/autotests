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
        kraken.get().page("metro");
        kraken.perform().loginAs("admin");
    }

    // TODO сделать тесты на заказы во все ритейлеры Москвы

    @Test(
            description = "Тестовый заказ в Казани",
            groups = {"regression"},
            priority = 451
    )
    public void successOrderInKazan(){
        kraken.shipAddress().change(Addresses.Kazan.defaultAddress());
        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();

        //TODO добавить проверку стоимости доставки как в checkMetroDeliveryPriceDiscount()

        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется заказ в Казани\n");

        kraken.perform().checkOrderDocuments();
    }


    @Test(
            description = "Тестовый заказ в Екате",
            groups = {"regression"},
            priority = 452
    )
    public void successOrderInEkat(){
        kraken.shipAddress().change(Addresses.Ekaterinburg.defaultAddress());
        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();

        //TODO добавить проверку стоимости доставки как в checkMetroDeliveryPriceDiscount()

        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется заказ в Екате\n");

        kraken.perform().checkOrderDocuments();
    }


    @Test(
            description = "Тестовый заказ во Вкусвилл с применением программы лояльности Вкусвилл",
            groups = {"regression"},
            priority = 453
    )
    public void successOrderInVkusvill(){
        kraken.get().page("vkusvill");
        kraken.shipAddress().change(Addresses.Moscow.testAddress());
        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();

        Assert.assertTrue(kraken.detect().isRetailerLoyaltyAvailable(),
                "Не доступна программа лояльности \"vkusvill\" \n");

        kraken.checkout().addRetailerLoyalty("vkusvill");
        Assert.assertTrue(kraken.checkout().isRetailerLoyaltyApplied(),
                "Программа лояльности \"vkusvill\" не применена\n");

        //TODO добавить проверку стоимости доставки как в checkMetroDeliveryPriceDiscount()

        kraken.checkout().complete();
        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется заказ во Вкусвилл\n");

        kraken.perform().checkOrderDocuments();
    }


    @AfterMethod(alwaysRun = true)
    public void postconditions()throws Exception {
        kraken.perform().cancelLastOrder();
        kraken.shipAddress().change(Addresses.Moscow.defaultAddress());
    }

}
