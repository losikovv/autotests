package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;



    // Тесты повтора заказов



public class RepeatOrders extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void getAuth()throws Exception {
        app.getNavigationHelper().getRetailerPage("metro");
        if(!app.getSessionHelper().isUserAuthorised()) {
            app.getSessionHelper().doLoginAsAdmin();
        }
    }


    @Test(
            description = "Повтор крайнего заказа и оплата картой",
            groups = {"acceptance","regression"},
            priority = 501
    )
    public void repeatLastOrderAndPayWithCard() throws Exception {
        app.getProfileHelper().repeatLastOrder();

        // Проверяем что заказ повторился и корзина не пуста
        Assert.assertFalse(app.getShoppingHelper().isCartEmpty(),
                "Something went wrong while repeating the last order from the profile\n");

        if(!app.getShoppingHelper().isCheckoutButtonActive()) {
            app.getShoppingHelper().grabCartWithMinimalOrderSum();
        }
        app.getShoppingHelper().proceedToCheckout();
        app.getCheckoutHelper().completeCheckout("ТЕСТОВЫЙ ЗАКАЗ",1,"card");

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(app.getProfileHelper().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");
    }


    @Test(
            description = "Повтор крайнего заказа и оплата наличными",
            groups = {"acceptance","regression"},
            priority = 502
    )
    public void repeatLastOrderAndPayWithCash() throws Exception {
        app.getProfileHelper().repeatLastOrder();

        // Проверяем что заказ повторился и корзина не пуста
        Assert.assertFalse(app.getShoppingHelper().isCartEmpty(),
                "Something went wrong while repeating the last order from the profile\n");

        if(!app.getShoppingHelper().isCheckoutButtonActive()) {
            app.getShoppingHelper().grabCartWithMinimalOrderSum();
        }
        app.getShoppingHelper().proceedToCheckout();
        app.getCheckoutHelper().completeCheckout("ТЕСТОВЫЙ ЗАКАЗ",2,"cash");

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(app.getProfileHelper().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");
    }


    @Test(
            description = "Повтор крайнего заказа и оплата банковским переводом",
            groups = {"acceptance","regression"},
            priority = 503
    )
    public void repeatLastOrderAndPayWithBank() throws Exception {
        app.getProfileHelper().repeatLastOrder();

        // Проверяем что заказ повторился и корзина не пуста
        Assert.assertFalse(app.getShoppingHelper().isCartEmpty(),
                "Something went wrong while repeating the last order from the profile\n");

        if(!app.getShoppingHelper().isCheckoutButtonActive()) {
            app.getShoppingHelper().grabCartWithMinimalOrderSum();
        }
        app.getShoppingHelper().proceedToCheckout();
        app.getCheckoutHelper().completeCheckout("ТЕСТОВЫЙ ЗАКАЗ",3,"bank");

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(app.getProfileHelper().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");

    }


    @AfterMethod(alwaysRun = true)
    public void cancelLastOrder()throws Exception {
        app.getProfileHelper().cancelLastOrder();
    }
}
