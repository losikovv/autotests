package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTest extends TestBase {

    @Test(priority = 1)
    public void makeTestOrder() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getSessionHelper().doLoginAsReturningCustomer();
        Assert.assertTrue(app.getSessionHelper().isUserAuthorised(), "User wasn't successfully authorised"+"\n");

        app.getShoppingHelper().openCart();
        app.getShoppingHelper().proceedToCheckout();
        app.getCheckoutHelper().completeCheckout("ТЕСТОВЫЙ ЗАКАЗ",1,"cart");
        Assert.assertTrue(app.getProfileHelper().isOrderActive(), "Order is not active\n");

        app.getProfileHelper().cancelLastOrder();
        Assert.assertFalse(app.getProfileHelper().isLastOrderActive(), "Last order wasn't canceled\n");
    }

}
