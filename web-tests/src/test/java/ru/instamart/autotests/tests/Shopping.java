package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Pages;


// Тесты магазина


public class Shopping extends TestBase{


    @BeforeMethod(alwaysRun = true)
    public void getAuth() throws Exception {
        kraken.perform().dropAuth();
        kraken.perform().loginAs("admin");
    }


    @Test(
            description = "Тест пустой корзины",
            groups = {"acceptance","regression"},
            priority = 350
    )
    public void checkEmptyCart() throws Exception, AssertionError {
        kraken.shopping().dropCart();
        kraken.shopping().openCart();

        // Assert cart is open
        Assert.assertTrue(kraken.shopping().isCartOpen(),
                "Can't open shopping cart\n");

        // Assert cart is empty
        Assert.assertTrue(kraken.shopping().isCartEmpty(),
                "Cart isn't empty\n");

        // Assert checkout button is disabled in an empty card
        Assert.assertFalse(kraken.shopping().isCheckoutButtonActive(),
                "Checkout button is active in an empty cart\n");

        kraken.shopping().closeCart();

        // Assert cart is closed
        Assert.assertFalse(kraken.shopping().isCartOpen(),
                "Can't close shopping cart\n");
    }


    @Test(
            description = "Тест недоступности пустого чекаута по прямой ссылке",
            groups = {"acceptance","regression"},
            priority = 351
    )
    public void checkoutIsUnreachableWithEmptyCart() throws Exception {
        kraken.shopping().dropCart();
        assertPageIsUnreachable(Pages.Site.checkout());
    }


    @Test(
            description = "Тест успешного добавления товара в корзину",
            groups = {"acceptance","regression"},
            priority = 352
    )
    public void addItemToCart()throws Exception, AssertionError {
        kraken.shopping().dropCart();

        kraken.shopping().addFirstItemOnPageToCart();

        Assert.assertFalse(kraken.shopping().isCartEmpty(),
                "Cart is still empty after adding an item into it\n");

        kraken.shopping().closeCart();
    }


    @Test(
            description = "Тест набора корзины до суммы, достаточной для заказа",
            groups = {"acceptance","regression"},
            priority = 353
    )
    public void grabCart()throws Exception, AssertionError {
        kraken.shopping().grabCart();

        // Assert checkout button is enabled
        Assert.assertTrue(kraken.shopping().isCheckoutButtonActive(),
                "Checkout button is not active with minimal order cart\n");

        kraken.shopping().closeCart();
    }


    @Test(
            description = "Успешного перехода в чекаут при сумме корзины выше суммы минимального заказа",
            groups = {"acceptance","regression"},
            priority = 354
    )
    public void proceedFromCartToCheckout()throws Exception, AssertionError {
        if(!kraken.shopping().isCheckoutButtonActive()) {
            kraken.shopping().grabCart();
        }
        if(kraken.shopping().isCheckoutButtonActive()){
            kraken.shopping().proceedToCheckout();
        }

        // Assert can access checkout by clicking on order button in cart
        Assert.assertTrue(kraken.checkout().isOnCheckout(),
                "Can't access checkout by clicking on order button in cart\n");
    }

    // TODO тест на изменение кол-ва товаров в корзине


    // TODO тест на удаление товаров из корзины

}
