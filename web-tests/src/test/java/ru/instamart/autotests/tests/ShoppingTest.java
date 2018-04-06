package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ShoppingTest extends TestBase {

    @Test
    public void addItemToCart() throws Exception {
        app.getNavigationHelper().getPage("metro?sid=12");
        app.getShoppingHelper().addFirstLineItemOnPageToCart();
        app.getShoppingHelper().openCart();
        Assert.assertFalse(app.getShoppingHelper().isCartEmpty(), "Item hasn't been added to cart");
    }
}
