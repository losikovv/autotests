package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.UserData;

public class CartFunctionalityTest extends TestBase{

    @Test
    public void authorisation() throws Exception {
        // идем и чекаем витрину Метро
        getPageAndAssertAvailability("https://instamart.ru/metro");
        // логинимся
        app.getAuthorisationHelper().doLogin(new UserData("autotestuser@instamart.ru", "DyDrasLipMeibe7"));
        // проверяем что авторизованы
        Assert.assertTrue(app.getAuthorisationHelper().userIsAuthorised());
    }

    @Test
    public void openCart()throws Exception {
        if (app.getCartHelper().cartIsOpen()) {
            app.getCartHelper().closeCart();
        }
        app.getCartHelper().openCart();
        Assert.assertTrue(app.getCartHelper().cartIsOpen(), "Can't open cart");
    }

    @Test
    public void closeCart()throws Exception {
        if (!app.getCartHelper().cartIsOpen()) {
            app.getCartHelper().openCart();
        }
        app.getCartHelper().closeCart();
        Assert.assertFalse(app.getCartHelper().cartIsOpen(), "Can't close cart");
    }

    @Test
    public void emptyCart()throws Exception {
        if (!app.getCartHelper().cartIsOpen()) {
            app.getCartHelper().openCart();
        }
        // TODO добавить проверку на наличие товаров в корзине и если нет - очищать корзину
        Assert.assertTrue(app.getCartHelper().isCartEmpty(), "There is no placeholder in empty cart");
    }

}
