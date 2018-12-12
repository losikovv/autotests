package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;


// Тесты заказов


public class MakeOrders extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void preparing() throws Exception {
        kraken.get().page("metro");
        kraken.perform().loginAs("admin");
    }


    @Test (
            description = "Тест скачивания документов к заказам",
            groups = {"regression"},
            priority = 450
    )
    public void downloadOrderDocuments(){
        kraken.perform().checkOrderDocuments("R351510533");  // Заказ с двумя документами
        kraken.perform().checkOrderDocuments("R154547373");  // Заказ с тремя документами
    }


    @Test(
            description = "Тестовый заказ в Казани",
            groups = {"regression"},
            priority = 451
    )
    public void orderInKazan(){

        kraken.shipAddress().change(Addresses.Kazan.defaultAddress());

        // Идем в чекаут, при необходимости набирая корзину
        kraken.get().checkoutPage();
        if(!kraken.checkout().isOnCheckout()){
            kraken.shopping().collectItems();
            kraken.shopping().proceedToCheckout();
        }

        //TODO добавить проверку стоимости доставки как в checkMetroDeliveryPriceDiscount()

        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформлен заказ в Казани\n");

        kraken.perform().checkOrderDocuments();
    }


    @Test(
            description = "Тестовый заказ во Вкусвилл с применением программы лояльности Вкусвилл",
            groups = {"regression"},
            priority = 452
    )
    public void orderToVkusvill(){

        kraken.get().page("vkusvill");
        kraken.shipAddress().change(Addresses.Moscow.testAddress());

        // идем в чекаут, при необходимости набирая корзину
        kraken.get().checkoutPage();
        if(!kraken.checkout().isOnCheckout()){
            kraken.shopping().collectItems();
            kraken.shopping().proceedToCheckout();
        }

        Assert.assertTrue(kraken.detect().isRetailerLoyaltyAvailable(),
                "Не доступна программа лояльности \"vkusvill\" \n");

        kraken.checkout().addRetailerLoyalty("vkusvill");
        Assert.assertTrue(kraken.checkout().isRetailerLoyaltyApplied(),
                "Программа лояльности \"vkusvill\" не применена\n");

        //TODO добавить проверку стоимости доставки как в checkMetroDeliveryPriceDiscount()

        kraken.checkout().complete();
        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформлен заказ во Вкусвилл\n");

        kraken.perform().checkOrderDocuments();
    }


    @AfterMethod(alwaysRun = true)
    public void cancelLastOrder()throws Exception {
        kraken.perform().cancelLastOrder();
        kraken.shipAddress().change(Addresses.Moscow.defaultAddress());
    }

}
