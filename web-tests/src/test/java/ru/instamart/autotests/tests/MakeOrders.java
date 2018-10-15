package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.testdata.Addresses;



// Тесты заказов



public class MakeOrders extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void preparing() throws Exception {
        app.getNavigationHelper().getRetailerPage("metro");
        app.getSessionHelper().doLoginAs("admin");
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
            groups = {"acceptance","regression"},
            priority = 451
    )
    public void orderInKazan(){

        app.getShoppingHelper().changeShippingAddress(Addresses.Kazan.defaultAddress());

        // Идем в чекаут, при необходимости набирая корзину
        app.getNavigationHelper().getCheckoutPage();
        if(!app.getCheckoutHelper().isOnCheckout()){
            app.getShoppingHelper().grabCart();
            app.getShoppingHelper().proceedToCheckout();
        }

        app.getCheckoutHelper().completeCheckout();

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(app.getProfileHelper().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");

        // Проверяем скачку документов
        checkOrderDocuments();
    }


    @Test(
            description = "Тестовый заказ во Вкусвилл с применением программы лояльности Вкусвилл",
            groups = {"acceptance","regression"},
            priority = 452
    )
    public void orderToVkusvill(){

        app.getNavigationHelper().getRetailerPage("vkusvill");
        app.getShoppingHelper().changeShippingAddress(Addresses.Moscow.testAddress());

        // идем в чекаут, при необходимости набирая корзину
        app.getNavigationHelper().getCheckoutPage();
        if(!app.getCheckoutHelper().isOnCheckout()){
            app.getShoppingHelper().grabCart();
            app.getShoppingHelper().proceedToCheckout();
        }

        // Проверяем что доступна программа лояльности ритейлера
        Assert.assertTrue(app.getCheckoutHelper().isRetailerLoyaltyAvailable(),
                "Retailer loyalty program is not available\n");

        app.getCheckoutHelper().addRetailerLoyalty("vkusvill");

        // Проверяем что программа лояльности ритейлера применилась
        Assert.assertTrue(app.getCheckoutHelper().isRetailerLoyaltyApplied(),
                "Can't apply retailer loyalty program, check manually\n");

        app.getCheckoutHelper().completeCheckout();

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(app.getProfileHelper().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");

        // Проверяем скачку документов
        checkOrderDocuments();
    }


    @Test(
            description = "Тест проверки максимальной стоимости доставки",
            groups = {"regression"},
            priority = 453
    )
    public void checkDeliveryPriceMax() {
        app.getNavigationHelper().getCheckoutPage();
        // если не попали в чекаут - набираем корзину и идем снова
        if(!app.getCheckoutHelper().isOnCheckout()){
            app.getShoppingHelper().grabCart(1488);
            app.getShoppingHelper().proceedToCheckout();
        }
        app.getCheckoutHelper().fillCheckout();

        Assert.assertTrue(app.getCheckoutHelper().checkDeliveryPrice(299),"Delivery price is not correct," +
                "check manually \n" ); // проверка стоимости доставки на чекауте

        app.getCheckoutHelper().sendOrder();

        Assert.assertTrue(app.getProfileHelper().checkDeliveryPrice(299), "Delivery price is not " +
               "correct, check manually \n"); // проверка стоимости заказа на странице деталей заказа
        }


    @Test(
            description = "Тест проверки средней стоимости доставки",
            groups = {"regression"},
            priority = 454
    )
    public void checkDeliveryPriceMiddle() {
        app.getNavigationHelper().getCheckoutPage();
        // если не попали в чекаут - набираем корзину и идем снова
        if(!app.getCheckoutHelper().isOnCheckout()){
            app.getShoppingHelper().grabCart(5500);
            app.getShoppingHelper().proceedToCheckout();
        }
        app.getCheckoutHelper().fillCheckout();

        Assert.assertTrue(app.getCheckoutHelper().checkDeliveryPrice(199),"Delivery price is not correct," +
                "check manually \n" ); // проверка стоимости доставки на чекауте

        app.getCheckoutHelper().sendOrder();
        Assert.assertTrue(app.getProfileHelper().checkDeliveryPrice(199), "Delivery price is not " +
                "correct, check manually \n"); // проверка стоимости заказа на странице деталей заказа
    }


    @Test(
            description = "Тест проверки минимальной стоимости доставки",
            groups = {"regression"},
            priority = 455
    )
    public void checkDeliveryPriceMin() {
        app.getNavigationHelper().getCheckoutPage();
        // если не попали в чекаут - набираем корзину и идем снова
        if(!app.getCheckoutHelper().isOnCheckout()){
            app.getShoppingHelper().grabCart(10100);
            app.getShoppingHelper().proceedToCheckout();
        }
        app.getCheckoutHelper().fillCheckout();

        Assert.assertTrue(app.getCheckoutHelper().checkDeliveryPrice(99),"Delivery price is not correct," +
                "check manually \n" ); // проверка стоимости доставки на чекауте

        app.getCheckoutHelper().sendOrder();

        Assert.assertTrue(app.getProfileHelper().checkDeliveryPrice(99), "Delivery price is not " +
                "correct, check manually \n"); // проверка стоимости заказа на странице деталей заказа
    }


    @AfterMethod(alwaysRun = true)
    public void cancelLastOrder()throws Exception {
        app.getProfileHelper().cancelLastOrder();
        app.getNavigationHelper().goHome();
        app.getShoppingHelper().changeShippingAddress(Addresses.Moscow.defaultAddress());
    }

}
