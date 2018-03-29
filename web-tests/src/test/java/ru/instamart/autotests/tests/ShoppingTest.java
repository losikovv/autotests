package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ShoppingTest extends TestBase {

    @Test
    public void addItemToCart() throws Exception {
        app.getNavigationHelper().getPage("metro?sid=12");
        app.getShoppingHelper().addFirstItemOnPageToCart();
        app.getShoppingCartHelper().openCart();
        Assert.assertFalse(app.getShoppingCartHelper().isCartEmpty(), "Item hasn't been added to cart");
    }
}
