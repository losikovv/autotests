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
        app.getCheckoutHelper().completeCheckout(1,"cart");
        //TODO добавить проверку на активность заказа с помощью метода isOrderActive
        app.getProfileHelper().cancelLastOrder();
        //TODO добавить проверку на отмененность заказа с помощью метода isOrderActive
    }

}
