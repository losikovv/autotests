package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.RetailerData;

public class ShoppingTest extends TestBase {

    @Test(priority = 1)
    public void emptyShippingAddressWhileUnauthorized() throws Exception {
        app.getNavigationHelper().getRetailerPage(new RetailerData("metro"));
        Assert.assertTrue(app.getShoppingHelper().isShippingAddressEmpty(),
                "Shipping address is not empty on retailer page while unauthorized\n");
    }

    @Test(priority = 2)
    public void setAddressWithNoAvailableStoresOnLanding() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getShoppingHelper().setShippingAddress("Москва, ул Лосиноостровская, д 1");
        Assert.assertFalse(app.getShoppingHelper().isAnyShopsAvailable(), "Shops available with current shipping address\n");
    }

    @Test(priority = 3)
    public void setAddressWithAvailableStoresOnLanding() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getShoppingHelper().setShippingAddress("Москва, ул Долгоруковская, д 2");
        Assert.assertTrue(app.getShoppingHelper().isAnyShopsAvailable(), "No shops available with current shipping address\n");
    }



    @Test(enabled = false)
    public void setAddressWithNoStoresOnRetailerPage() throws Exception {
        app.getNavigationHelper().getRetailerPage(new RetailerData("metro"));
        app.getShoppingHelper().setShippingAddress("Москва, ул Лосиноостровская, д 1");
        app.getShoppingHelper().openShopsList();
        Assert.assertFalse(app.getShoppingHelper().isAnyShopsAvailable(), "Seems like there are some shops available with current shipping address\n");
    }

    //TODO добавить тесты на наличие магазов по адресу

    @Test(enabled = false)
    public void setShippingAddressOnLandingPage() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getShoppingHelper().setShippingAddress("Москва, ул Долгоруковская, д 2");
        app.getShoppingHelper().selectShop(1);
        Assert.assertFalse(app.getShoppingHelper().isShippingAddressEmpty(), "Shipping address is empty after it was set on landing\n");
        Assert.assertTrue(app.getShoppingHelper().isShippingAddressSet(), "Shipping address wasn't set\n");
    }

    @Test(enabled = false)
    public void changeShippingAddressOnRetailerPage() throws Exception {
        app.getNavigationHelper().getRetailerPage(new RetailerData("metro"));
        app.getShoppingHelper().changeShippingAddress("Москва, ул Тверская, д 1");
        app.getShoppingHelper().selectShop(1);
        Assert.assertFalse(app.getShoppingHelper().isShippingAddressEmpty(), "Shipping address is empty after it was set on landing\n");
        Assert.assertTrue(app.getShoppingHelper().isShippingAddressSet(), "Shipping address wasn't set\n");
    }


/*
    @Test(priority = 2)
    public void selectShop() throws Exception {

    }

    @Test(priority = 3)
    public void addItemToCart() throws Exception {
        app.getNavigationHelper().getPage("metro?sid=12");
        app.getShoppingHelper().addFirstLineItemOnPageToCart();
        app.getShoppingHelper().openCart();
        // работает только если корзина была пуста, нужно переделать
        Assert.assertFalse(app.getShoppingHelper().isCartEmpty(), "Item hasn't been added to cart");
    }

    */

}
