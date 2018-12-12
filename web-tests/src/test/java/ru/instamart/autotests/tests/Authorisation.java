package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.models.UserData;
import ru.instamart.autotests.testdata.Generate;


// Тесты авторизации


public class Authorisation extends TestBase {


    @Test(
            description = "Негативный тест попытки авторизации без email",
            groups = {"regression"},
            priority = 101
    )
    public void noAuthWithoutEmail() throws Exception, AssertionError {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();
        kraken.perform().login("", "instamart");

        // TODO переделать на soft-assertion
        Assert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.nameErrorMessage()),
                "Нет пользовательской ошибки пустого поля email\n");

        // Assert user isn't authorised
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Пользователь авторизован без email!\n");
    }


    @Test(
            description = "Негативный тест попытки авторизации без пароля",
            groups = {"regression"},
            priority = 102
    )
    public void noAuthWithoutPassword() throws Exception, AssertionError {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();
        kraken.perform().login("instatestuser@yandex.ru", "");

        // TODO переделать на soft-assertion
        Assert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.emailErrorMessage()),
                "Нет пользовательской ошибки пустого поля password\n");

        // Assert user isn't authorised
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Пользователь авторизован без пароля!"+"\n");
    }


    @Test(
            description = "Негативный тест попытки авторизации несуществующим юзером",
            groups = {"regression"},
            priority = 103
    )
    public void noAuthWithNonexistingUser() throws Exception, AssertionError {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();
        kraken.perform().login("nonexistinguser@example.com", "password");

        // TODO переделать на soft-assertion
        Assert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.nameErrorMessage()),
                "Нет пользовательской ошибки авторизации с неверным email или паролем\n");

        // Assert user isn't authorised
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Авторизован незарегистрированный пользователь!"+"\n");
    }


    @Test(
            description = "Негативный тест попытки авторизации с неверным паролем",
            groups = {"acceptance","regression"},
            priority = 104
    )
    public void noAuthWithWrongPassword() throws Exception, AssertionError {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();
        kraken.perform().login("instatestuser@yandex.ru", "wrongpassword");

        // TODO переделать на soft-assertion
        Assert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.nameErrorMessage()),
                "Нет пользовательской ошибки авторизации с неверным email или паролем\n");

        // Assert user isn't authorised
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Пользователь авторизован с неверным паролем!"+"\n");
    }


    @Test(
            description = "Тест успешной авторизации на лендинге",
            groups = {"smoke","acceptance","regression"},
            priority = 105
    )
    public void successAuthOnLandingPage() throws Exception, AssertionError {
        kraken.get().baseUrl();

        kraken.perform().loginAs("user");

        // Assert user is authorised
        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Пользователь не авторизован на лендинге\n");

        kraken.perform().logout();
    }


    @Test(
            description = "Тест успешной авторизации на витрине",
            groups = {"acceptance","regression"},
            priority = 106
    )
    public void successAuthOnRetailerPage() throws Exception, AssertionError {
        kraken.get().page("vkusvill");

        kraken.perform().loginAs("user");

        // Assert user is authorised
        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Пользователь не авторизован на витрине магазина\n");

        kraken.perform().logout();
    }


    @Test(
            description = "Тест логаута",
            groups = {"regression"},
            priority = 107
    )
    public void logout() throws Exception, AssertionError {
        kraken.perform().loginAs("admin");
        kraken.perform().logout();

        // Assert there is no problems after logout
        assertPageIsAvailable();

        kraken.get().page("metro");

        // Assert user is unauthorised
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Пользователь не разавторизован\n");

    }

    @Test(
            description = "Тест авторизации с адресной модалки феникса",
            groups = {"regression"},
            priority = 108
    )
    public void successAuthFromAddressModal() throws Exception, AssertionError {
        kraken.get().page("metro");
        kraken.perform().dropAuth();

        kraken.shipAddress().openAddressModal();
        kraken.perform().click(Elements.Site.AddressModal.authButton());
        kraken.perform().authSequence("admin");

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Пользователь не авторизован с адресной модалки феникса\n");

        kraken.perform().logout();
    }

    @Test(
            description = "Тест успешной авторизации из корзины",
            groups = {"regression"},
            priority = 109
    )
    public void successAuthFromCart() throws Exception {
        final UserData testuser = Generate.testUserData();
        kraken.get().baseUrl();
        kraken.perform().registration(testuser);
        kraken.perform().logout();

        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
        kraken.shopping().collectItems();
        kraken.shopping().openCart();
        kraken.perform().click(Elements.Site.Cart.checkoutButton());
        kraken.perform().click(Elements.Site.AuthModal.authorisationTab());

        kraken.perform().authSequence(testuser);

        Assert.assertTrue(kraken.detect().isOnCheckout(),
                "Нет автоперехода в чекаут после авторизации из корзины\n");

        kraken.get().baseUrl();

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Пользователь не авторизован после авторизации из корзины\n");
        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Пропали товары после авторизации из корзины\n");

        kraken.shopping().closeCart();
        kraken.perform().logout();
    }

    @Test(
            description = "Негативный тест попытки авторизовать пользователя с длинными полями",
            groups = {"regression"},
            priority = 110
    )
    public void noAuthWithLongFields() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().login(Generate.testUserData(129));

        // TODO переделать на soft-assertion
        Assert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.nameErrorMessage()),
                "Нет пользовательской ошибки авторизации с неверным email или паролем\n");

        kraken.get().baseUrl();
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Пользователь авторизован при наличии ошибок заполнения формы авторизации\n");
    }

    //TODO добавить тесты на авторизацию через соцсети

}
