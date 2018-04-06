package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.UserData;

public class ShoppingCartTest extends TestBase{

    @Test
    public void authorisation() throws Exception {
        // идем и чекаем витрину Метро
        getAndAssertPageIsAvailable("https://instamart.ru/metro");
        // логинимся
        app.getSessionHelper().doLogin(new UserData("autotestuser@instamart.ru", "DyDrasLipMeibe7", null));
        // проверяем что авторизованы
        Assert.assertTrue(app.getSessionHelper().userIsAuthorised(), "User wasn't successfully authorised"+"\n");
    }

    @Test
    public void openCart()throws Exception {
        if (app.getShoppingHelper().cartIsOpen()) {
            app.getShoppingHelper().closeCart();
        }
        app.getShoppingHelper().openCart();
        Assert.assertTrue(app.getShoppingHelper().cartIsOpen(), "Can't open shopping cart"+"\n");
    }

    @Test
    public void closeCart()throws Exception {
        if (!app.getShoppingHelper().cartIsOpen()) {
            app.getShoppingHelper().openCart();
        }
        app.getShoppingHelper().closeCart();
        Assert.assertFalse(app.getShoppingHelper().cartIsOpen(), "Can't close shopping cart"+"\n");
    }

    @Test
    public void showEmptyCartPlaceholder()throws Exception {
        if (!app.getShoppingHelper().cartIsOpen()) {
            app.getShoppingHelper().openCart();
        }
        // TODO добавить проверку на наличие товаров в корзине и если нет - очищать корзину методом clearCart
        Assert.assertTrue(app.getShoppingHelper().isCartEmpty(), "There is no placeholder in an empty shopping cart"+"\n");
    }

}
