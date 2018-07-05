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

        // Assert cart isn't empty
        Assert.assertFalse(app.getShoppingHelper().isCartEmpty(),
                "Something went wrong while repeating the last order from the profile\n");

        if(!app.getShoppingHelper().isCheckoutButtonActive()) {
            app.getShoppingHelper().grabCartWithMinimalOrderSum();
        }
        app.getShoppingHelper().proceedToCheckout();
        app.getCheckoutHelper().completeCheckout("ТЕСТОВЫЙ ЗАКАЗ",1,"card");

        // Assert order is active
        Assert.assertTrue(app.getProfileHelper().isOrderActive(),
                "Order is not active\n");
    }


    @Test(
            description = "Повтор крайнего заказа и оплата наличными",
            groups = {"acceptance","regression"},
            priority = 502
    )
    public void repeatLastOrderAndPayWithCash() throws Exception {
        app.getProfileHelper().repeatLastOrder();

        // Assert cart isn't empty
        Assert.assertFalse(app.getShoppingHelper().isCartEmpty(),
                "Something went wrong while repeating the last order from the profile\n");

        if(!app.getShoppingHelper().isCheckoutButtonActive()) {
            app.getShoppingHelper().grabCartWithMinimalOrderSum();
        }
        app.getShoppingHelper().proceedToCheckout();
        app.getCheckoutHelper().completeCheckout("ТЕСТОВЫЙ ЗАКАЗ",2,"cash");

        // Assert order is active
        Assert.assertTrue(app.getProfileHelper().isOrderActive(),
                "Order is not active\n");
    }


    @Test(
            description = "Повтор крайнего заказа и оплата банковским переводом",
            groups = {"acceptance","regression"},
            priority = 503
    )
    public void repeatLastOrderAndPayWithBank() throws Exception {
        app.getProfileHelper().repeatLastOrder();

        // Assert cart isn't empty
        Assert.assertFalse(app.getShoppingHelper().isCartEmpty(),
                "Something went wrong while repeating the last order from the profile\n");

        if(!app.getShoppingHelper().isCheckoutButtonActive()) {
            app.getShoppingHelper().grabCartWithMinimalOrderSum();
        }
        app.getShoppingHelper().proceedToCheckout();
        app.getCheckoutHelper().completeCheckout("ТЕСТОВЫЙ ЗАКАЗ",3,"bank");

        // Assert order is active
        Assert.assertTrue(app.getProfileHelper().isOrderActive(),
                "Order is not active\n");

    }


    @AfterMethod(alwaysRun = true)
    public void cancelLastOrder()throws Exception {
        app.getProfileHelper().cancelLastOrder();
    }
}
