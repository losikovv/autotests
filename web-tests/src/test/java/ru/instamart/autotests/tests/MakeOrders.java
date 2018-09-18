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
            groups = {"acceptance","regression"},
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
            app.getShoppingHelper().grabCartWithMinimalOrderSum();
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
            app.getShoppingHelper().grabCartWithMinimalOrderSum();
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


    @AfterMethod(alwaysRun = true)
    public void cancelLastOrder()throws Exception {
        app.getProfileHelper().cancelLastOrder();
        app.getNavigationHelper().goHome();
        app.getShoppingHelper().changeShippingAddress(Addresses.Moscow.defaultAddress());
    }

}
