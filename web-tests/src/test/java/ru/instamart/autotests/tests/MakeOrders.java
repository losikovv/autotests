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
        checkOrderDocuments("R351510533");  // Заказ с двумя документами
        checkOrderDocuments("R154547373");  // Заказ с тремя документами
    }


    @Test(
            description = "Тестовый заказ в Казани",
            groups = {"regression"},
            priority = 451
    )
    public void orderInKazan(){

        kraken.shopping().changeShippingAddress(Addresses.Kazan.defaultAddress());

        // Идем в чекаут, при необходимости набирая корзину
        kraken.get().checkoutPage();
        if(!kraken.checkout().isOnCheckout()){
            kraken.shopping().grabCart();
            kraken.shopping().proceedToCheckout();
        }

        kraken.checkout().completeCheckout();

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(kraken.getProfileHelper().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");

        // Проверяем скачку документов
        checkOrderDocuments();
    }


    @Test(
            description = "Тестовый заказ во Вкусвилл с применением программы лояльности Вкусвилл",
            groups = {"regression"},
            priority = 452
    )
    public void orderToVkusvill(){

        kraken.get().page("vkusvill");
        kraken.shopping().changeShippingAddress(Addresses.Moscow.testAddress());

        // идем в чекаут, при необходимости набирая корзину
        kraken.get().checkoutPage();
        if(!kraken.checkout().isOnCheckout()){
            kraken.shopping().grabCart();
            kraken.shopping().proceedToCheckout();
        }

        // Проверяем что доступна программа лояльности ритейлера
        Assert.assertTrue(kraken.checkout().isRetailerLoyaltyAvailable(),
                "Retailer loyalty program is not available\n");

        kraken.checkout().addRetailerLoyalty("vkusvill");

        // Проверяем что программа лояльности ритейлера применилась
        Assert.assertTrue(kraken.checkout().isRetailerLoyaltyApplied(),
                "Can't apply retailer loyalty program, check manually\n");

        kraken.checkout().completeCheckout();

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(kraken.getProfileHelper().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");

        // Проверяем скачку документов
        checkOrderDocuments();
    }


    @AfterMethod(alwaysRun = true)
    public void cancelLastOrder()throws Exception {
        kraken.getProfileHelper().cancelLastOrder();
        kraken.shopping().changeShippingAddress(Addresses.Moscow.defaultAddress());
    }

}
