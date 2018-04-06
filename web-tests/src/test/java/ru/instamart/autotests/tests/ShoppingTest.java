package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ShoppingTest extends TestBase {


    @Test(priority = 1)
    public void chooseShippingAddress() throws Exception{

    }

    @Test(priority = 2)
    public void chooseShop() throws Exception{

    }

    @Test(priority = 3)
    public void addItemToCart() throws Exception {
        app.getNavigationHelper().getPage("metro?sid=12");
        app.getShoppingHelper().addFirstLineItemOnPageToCart();
        app.getShoppingHelper().openCart();
        Assert.assertFalse(app.getShoppingHelper().isCartEmpty(), "Item hasn't been added to cart");
    }
}
