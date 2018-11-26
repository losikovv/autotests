package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.configuration.Pages;


// Тесты покупок



public class Shopping extends TestBase{


    @BeforeMethod(alwaysRun = true)
    public void getAuth() throws Exception {
        kraken.getNavigationHelper().getBaseUrl();
        kraken.getSessionHelper().doLoginAs("admin");
    }


    @Test(
            description = "Тест пустой корзины",
            groups = {"acceptance","regression"},
            priority = 300
    )
    public void checkEmptyCart() throws Exception, AssertionError {
        kraken.getShoppingHelper().dropCart();
        kraken.getShoppingHelper().openCart();

        // Assert cart is open
        Assert.assertTrue(kraken.getShoppingHelper().isCartOpen(),
                "Can't open shopping cart\n");

        // Assert cart is empty
        Assert.assertTrue(kraken.getShoppingHelper().isCartEmpty(),
                "Cart isn't empty\n");

        // Assert checkout button is disabled in an empty card
        Assert.assertFalse(kraken.getShoppingHelper().isCheckoutButtonActive(),
                "Checkout button is active in an empty cart\n");

        kraken.getShoppingHelper().closeCart();

        // Assert cart is closed
        Assert.assertFalse(kraken.getShoppingHelper().isCartOpen(),
                "Can't close shopping cart\n");
    }


    @Test(
            description = "Тест недоступности пустого чекаута по прямой ссылке",
            groups = {"acceptance","regression"},
            priority = 301
    )
    public void checkoutIsUnreachableWithEmptyCart() throws Exception {
        kraken.getShoppingHelper().dropCart();
        assertPageIsUnreachable(Pages.Site.checkout());
    }


    @Test(
            description = "Тест успешного добавления товара в корзину",
            groups = {"acceptance","regression"},
            priority = 302
    )
    public void addItemToCart()throws Exception, AssertionError {
        kraken.getShoppingHelper().dropCart();
        kraken.getShoppingHelper().addFirstItemOnPageToCart();

        // Assert cart isn't empty
        Assert.assertFalse(kraken.getShoppingHelper().isCartEmpty(),
                "Cart is still empty after adding an item into it\n");
    }


    @Test(
            description = "Тест набора корзины до суммы, достаточной для заказа",
            groups = {"acceptance","regression"},
            priority = 303
    )
    public void grabCart()throws Exception, AssertionError {
        kraken.getShoppingHelper().grabCart();

        // Assert checkout button is enabled
        Assert.assertTrue(kraken.getShoppingHelper().isCheckoutButtonActive(),
                "Checkout button is not active with minimal order cart\n");
    }


    @Test(
            description = "Успешного перехода в чекаут при сумме корзины выше суммы минимального заказа",
            groups = {"acceptance","regression"},
            priority = 304
    )
    public void proceedFromCartToCheckout()throws Exception, AssertionError {
        if(!kraken.getShoppingHelper().isCheckoutButtonActive()) {
            kraken.getShoppingHelper().grabCart();
        }
        if(kraken.getShoppingHelper().isCheckoutButtonActive()){
            kraken.getShoppingHelper().proceedToCheckout();
        }

        // Assert can access checkout by clicking on order button in cart
        Assert.assertTrue(kraken.getCheckoutHelper().isOnCheckout(),
                "Can't access checkout by clicking on order button in cart\n");
    }

    // TODO тест на изменение кол-ва товаров в корзине


    // TODO тест на удаление товаров из корзины

}
