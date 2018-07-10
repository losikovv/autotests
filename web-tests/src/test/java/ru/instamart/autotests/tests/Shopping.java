package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



    // Тесты покупок



public class Shopping extends TestBase{


    @BeforeMethod(alwaysRun = true)
    public void getAuth() throws Exception {
        app.getNavigationHelper().getLandingPage();
        //app.getNavigationHelper().getRetailerPage("metro");
        if(!app.getSessionHelper().isUserAuthorised()) {
            app.getSessionHelper().doLoginAsAdmin();
        }
    }


    @Test(
            description = "Тест пустой корзины",
            groups = {"acceptance","regression"},
            priority = 300
    )
    public void checkEmptyCart() throws Exception, AssertionError {
        app.getShoppingHelper().dropCart();
        app.getShoppingHelper().openCart();

        // Assert cart is open
        Assert.assertTrue(app.getShoppingHelper().isCartOpen(),
                "Can't open shopping cart\n");

        // Assert cart is empty
        Assert.assertTrue(app.getShoppingHelper().isCartEmpty(),
                "Cart isn't empty\n");

        // Assert checkout button is disabled in an empty card
        Assert.assertFalse(app.getShoppingHelper().isCheckoutButtonActive(),
                "Checkout button is active in an empty cart\n");

        app.getShoppingHelper().proceedToCheckout();

        // Assert can't go to checkout by clicking on disabled order button in cart
        Assert.assertFalse(app.getCheckoutHelper().isOnCheckout(),
                "It's possible to access checkout by clicking on disabled order button in cart\n");

        app.getShoppingHelper().closeCart();

        // Assert cart is closed
        Assert.assertFalse(app.getShoppingHelper().isCartOpen(),
                "Can't close shopping cart\n");
    }


    @Test(
            description = "Тест недоступности пустого чекаута по прямой ссылке",
            groups = {"acceptance","regression"},
            priority = 301
    )
    public void checkoutIsUnreachableWithEmptyCart() throws Exception {
        app.getShoppingHelper().dropCart();

        // Assert can't reach checkout page by direct url
        assertPageIsUnreachable("https://instamart.ru/checkout/edit?"); // TODO параметризовать окружение
    }


    @Test(
            description = "Тест успешного добавления товара в корзину",
            groups = {"acceptance","regression"},
            priority = 302
    )
    public void addItemToCart()throws Exception, AssertionError {
        app.getShoppingHelper().dropCart();
        app.getShoppingHelper().addFirstItemOnPageToCart();

        // Assert cart isn't empty
        Assert.assertFalse(app.getShoppingHelper().isCartEmpty(),
                "Cart is still empty after adding an item into it\n");
    }


    @Test(
            description = "Тест набора корзины до суммы, достаточной для заказа",
            groups = {"acceptance","regression"},
            priority = 303
    )
    public void grabCart()throws Exception, AssertionError {
        app.getShoppingHelper().grabCartWithMinimalOrderSum();

        // Assert checkout button is enabled
        Assert.assertTrue(app.getShoppingHelper().isCheckoutButtonActive(),
                "Checkout button is not active with minimal order cart\n");
    }


    @Test(
            description = "Успешного перехода в чекаут при сумме корзины выше суммы минимального заказа",
            groups = {"acceptance","regression"},
            priority = 304
    )
    public void proceedToCheckout()throws Exception, AssertionError {
        if(!app.getShoppingHelper().isCheckoutButtonActive()) {
            app.getShoppingHelper().grabCartWithMinimalOrderSum();
        }
        // if(app.getShoppingHelper().isCheckoutButtonActive()){
            app.getShoppingHelper().proceedToCheckout();
        //}

        // Assert can access checkout by clicking on order button in cart
        Assert.assertTrue(app.getCheckoutHelper().isOnCheckout(),
                "Can't access checkout by clicking on order button in cart\n");
    }

    // TODO тест на изменение кол-ва товаров в корзине

    // TODO тест на удаление товаров из корзины

}
