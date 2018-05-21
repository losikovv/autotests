package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



    // Тесты Корзины



public class Shopping extends TestBase{


    @BeforeMethod(alwaysRun = true)
    public void getAuth() throws Exception {
        app.getNavigationHelper().getRetailerPage("metro");
        if(!app.getSessionHelper().isUserAuthorised()) {
            app.getSessionHelper().doLoginAsAdmin();
        }
    }


    @Test(
            description = "Тест открытия корзины",
            groups = {"regression"},
            priority = 301
    )
    public void openCart() throws Exception, AssertionError {
        app.getShoppingHelper().openCart();

        // Assert cart is open
        Assert.assertTrue(app.getShoppingHelper().isCartOpen(),
                "Can't open shopping cart"+"\n");
    }


    @Test(
            description = "Тест закрытия корзины",
            groups = {"regression"},
            priority = 302
    )
    public void closeCart() throws Exception, AssertionError {
        if(!app.getShoppingHelper().isCartOpen()){
            openCart();
        }
        app.getShoppingHelper().closeCart();

        // Assert cart is closed
        Assert.assertFalse(app.getShoppingHelper().isCartOpen(),
                "Can't close shopping cart"+"\n");
    }


    @Test(
            description = "Тест пустой корзины",
            groups = {"acceptance","regression"},
            priority = 303
    )
    public void checkEmptyCart() throws Exception, AssertionError {
        app.getShoppingHelper().dropCart();
        if (!app.getShoppingHelper().isCartOpen()) {
            app.getShoppingHelper().openCart();
        }

        // Assert cart is empty
        Assert.assertTrue(app.getShoppingHelper().isCartEmpty(),
                "Cart isn't empty at the moment\n");

        // Assert cart placeholder is present
        Assert.assertTrue(app.getShoppingHelper().isEmptyCartPlaceholderPresent(),
                "There is no placeholder in an empty shopping cart\n");

        closeCart();
    }


    @Test(
            description = "Тест добавления товара в корзину",
            groups = {"acceptance","regression"},
            priority = 304
    )
    public void addItemToCart()throws Exception, AssertionError {
        openCart();
        if(!app.getShoppingHelper().isCartEmpty()){
            checkEmptyCart();
        }
        app.getShoppingHelper().addFirstLineItemOnPageToCart();
        app.getHelper().waitForIt();
        openCart();

        // Assert cart isn't empty
        Assert.assertFalse(app.getShoppingHelper().isCartEmpty(),
                "Cart is still empty after item adding\n");
    }

    // TODO тест на изменение кол-ва товаров в корзине

    // TODO тест на удаление товара из корзины

}
