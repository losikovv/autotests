package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



    // Тесты Корзины



public class ShoppingCart extends TestBase{


    @BeforeMethod(alwaysRun = true)
    public void getAuth()throws Exception {
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
    public void openCart()throws Exception, AssertionError {
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
    public void closeCart()throws Exception, AssertionError {
        app.getShoppingHelper().closeCart();

        // Assert cart is closed
        Assert.assertFalse(app.getShoppingHelper().isCartOpen(),
                "Can't close shopping cart"+"\n");
    }


    @Test(
            description = "Тест пустой корзины",
            groups = {"regression"},
            priority = 303
    )
    public void checkEmptyCartPlaceholder()throws Exception, AssertionError {
        if (!app.getShoppingHelper().isCartOpen()) {
            app.getShoppingHelper().openCart();
        }
        // TODO переделать проверки
        // TODO если корзина не пуста - очищать корзину методом clearCart, затем проверять плейсхолдер
        if(app.getShoppingHelper().isCartEmpty()){
        Assert.assertTrue(app.getShoppingHelper().isEmptyCartPlaceholderPresent(),
                "There is no placeholder in an empty shopping cart"+"\n");
        } else {
            app.getShoppingHelper().printMessage("Cart isn't empty at the moment");
        }
    }

}
