package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ShoppingCartTest extends TestBase{

    //TODO слить в shopping test

    @Test
    public void authorisation() throws Exception {
        // идем и чекаем витрину Метро
        assertPageIsAvailable("https://instamart.ru/metro");
        // логинимся
        app.getSessionHelper().doLoginAsReturningCustomer();
        // проверяем что авторизованы
        Assert.assertTrue(app.getSessionHelper().isUserAuthorised(), "User wasn't successfully authorised"+"\n");
    }

    @Test(priority = 1)
    public void openCart()throws Exception {
        if (app.getShoppingHelper().isCartOpen()) {
            app.getShoppingHelper().closeCart();
        }
        app.getShoppingHelper().openCart();
        Assert.assertTrue(app.getShoppingHelper().isCartOpen(), "Can't open shopping cart"+"\n");
    }

    @Test(priority = 2)
    public void closeCart()throws Exception {
        if (!app.getShoppingHelper().isCartOpen()) {
            app.getShoppingHelper().openCart();
        }
        app.getShoppingHelper().closeCart();
        Assert.assertFalse(app.getShoppingHelper().isCartOpen(), "Can't close shopping cart"+"\n");
    }

    @Test(priority = 3)
    public void checkEmptyCartPlaceholder()throws Exception {
        if (!app.getShoppingHelper().isCartOpen()) {
            app.getShoppingHelper().openCart();
        }
        // TODO переделать проверки
        // TODO если корзина не пуста - очищать корзину методом clearCart, затем проверять плейсхолдер
        if(app.getShoppingHelper().isCartEmpty()){
        Assert.assertTrue(app.getShoppingHelper().isEmptyCartPlaceholderPresent(), "There is no placeholder in an empty shopping cart"+"\n");
        } else {app.getShoppingHelper().printMessage("Cart isn't empty at the moment");}
    }

}
