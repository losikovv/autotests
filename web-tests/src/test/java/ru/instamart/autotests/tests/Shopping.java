package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.models.UserData;
import ru.instamart.autotests.testdata.Generate;


// Тесты магазина


public class Shopping extends TestBase{


    @BeforeMethod(alwaysRun = true)
    public void getAuth() throws Exception {
        kraken.get().page("metro");
    }


    @Test(
            description = "Тест пустой корзины",
            groups = {"acceptance","regression"},
            priority = 350
    )
    public void checkEmptyCart() throws Exception, AssertionError {
        kraken.perform().loginAs("admin");
        kraken.perform().dropCart();
        kraken.shopping().openCart();

        // Assert cart is open
        Assert.assertTrue(kraken.detect().isCartOpen(),
                "Не открылась козина\n");

        // Assert cart is empty
        Assert.assertTrue(kraken.detect().isCartEmpty(),
                "Корзина не пуста\n");

        // Assert checkout button is disabled in an empty card
        Assert.assertFalse(kraken.detect().isCheckoutButtonActive(),
                "Кнопка чекаута активна в пустой козине\n");

        kraken.shopping().closeCart();

        // Assert cart is closed
        Assert.assertFalse(kraken.detect().isCartOpen(),
                "Не закрылась корзина\n");
    }


    @Test(
            description = "Тест недоступности пустого чекаута по прямой ссылке",
            groups = {"acceptance","regression"},
            priority = 351
    )
    public void checkoutIsUnreachableWithEmptyCart() throws Exception {
        kraken.perform().loginAs("admin");
        kraken.perform().dropCart();
        assertPageIsUnreachable(Pages.Site.checkout());
    }


    @Test(
            description = "Тест успешного добавления товара в корзину",
            groups = {"acceptance","regression"},
            priority = 352
    )
    public void addItemToCart()throws Exception, AssertionError {
        kraken.perform().loginAs("admin");
        kraken.perform().dropCart();

        kraken.shopping().addFirstItemOnPageToCart();

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Корзина пуста после добавления товара\n");

        kraken.shopping().closeCart();
    }


    @Test(
            description = "Тест набора корзины до суммы, достаточной для заказа",
            groups = {"acceptance","regression"},
            priority = 353
    )
    public void grabCart()throws Exception, AssertionError {
        kraken.perform().loginAs("admin");
        kraken.shopping().collectItems();

        // Assert checkout button is enabled
        Assert.assertTrue(kraken.detect().isCheckoutButtonActive(),
                "Кнопка чекаута не активна, при минимальной сумме заказа в корзине\n");

        kraken.shopping().closeCart();
    }


    @Test(
            description = "Тест на подтягивание адреса и мердж корзины из профиля при авторизации",
            groups = {"acceptance", "regression"},
            priority = 354
    )
    public void mergeShipAddressAndCartAfterAuth() throws Exception {
        //TODO вынести в dataProvider
        kraken.perform().dropAuth();
        final UserData testuser = Generate.testUserData();
        kraken.get().baseUrl();
        kraken.perform().registration(testuser);
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
        kraken.shopping().addFirstItemOnPageToCart();
        kraken.perform().logout();

        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.testAddress());
        kraken.perform().login(testuser);

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Не прошла авторизация\n");

        Assert.assertTrue(kraken.detect().isShippingAddressSet(),
                "Слетел адрес доставки при авторизации\n");

        Assert.assertEquals(kraken.grab().currentShipAddress(), Addresses.Moscow.defaultAddress(),
                "Не обновился адрес доставки при авторизации\n");

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не смержиласть корзина при авторизации\n");
    }


    @Test(
            description = "Тест успешного перехода в чекаут при сумме корзины выше суммы минимального заказа",
            groups = {"acceptance","regression"},
            priority = 355
    )
    public void proceedFromCartToCheckout()throws Exception, AssertionError {
        kraken.perform().loginAs("admin");
        kraken.shopping().collectItems();

        kraken.shopping().proceedToCheckout();

        Assert.assertTrue(kraken.checkout().isOnCheckout(),
                "Не удалось перейти из корзины в чекаут\n");
    }

    // TODO тест на изменение кол-ва товаров в корзине

    // TODO тест на удаление товаров из корзины

}
