package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
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
        SoftAssert softAssert = new SoftAssert();

        kraken.get().baseUrl();
        kraken.perform().dropAuth();
        kraken.perform().login("", "instamart");

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.nameErrorMessage()),
                "Нет пользовательской ошибки пустого поля email\n");

        kraken.get().baseUrl();
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла авторизация без email\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки авторизации без пароля",
            groups = {"regression"},
            priority = 102
    )
    public void noAuthWithoutPassword() throws Exception, AssertionError {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().baseUrl();
        kraken.perform().dropAuth();
        kraken.perform().login("instatestuser@yandex.ru", "");

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.emailErrorMessage()),
                "Нет пользовательской ошибки пустого поля password\n");

        kraken.get().baseUrl();
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла авторизация без пароля"+"\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки авторизации несуществующим юзером",
            groups = {"regression"},
            priority = 103
    )
    public void noAuthWithNonexistingUser() throws Exception, AssertionError {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().baseUrl();
        kraken.perform().dropAuth();
        kraken.perform().login("nonexistinguser@example.com", "password");

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.nameErrorMessage()),
                "Нет пользовательской ошибки авторизации с неверным email или паролем\n");

        kraken.get().baseUrl();
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла авторизация несуществующим юзером"+"\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки авторизации с неверным паролем",
            groups = {"acceptance","regression"},
            priority = 104
    )
    public void noAuthWithWrongPassword() throws Exception, AssertionError {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().baseUrl();
        kraken.perform().dropAuth();
        kraken.perform().login("instatestuser@yandex.ru", "wrongpassword");

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.nameErrorMessage()),
                "Нет пользовательской ошибки авторизации с неверным email или паролем\n");

        kraken.get().baseUrl();
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла авторизация с неверным паролем"+"\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки авторизовать пользователя с длинными полями",
            groups = {"regression"},
            priority = 105
    )
    public void noAuthWithLongFields() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().login(Generate.testUserData(129));

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.nameErrorMessage()),
                "Нет пользовательской ошибки авторизации с неверным email или паролем\n");

        kraken.get().baseUrl();
        softAssert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла авторизация при наличии ошибок заполнения формы авторизации\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Тест успешной авторизации на лендинге",
            groups = {"smoke","acceptance","regression"},
            priority = 106
    )
    public void successAuthOnLandingPage() throws Exception, AssertionError {
        kraken.get().baseUrl();

        kraken.perform().loginAs("user");

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Не работает авторизация на лендинге\n");

        kraken.perform().quickLogout();
    }


    @Test(
            description = "Тест успешной авторизации на витрине",
            groups = {"acceptance","regression"},
            priority = 107
    )
    public void successAuthOnRetailerPage() throws Exception, AssertionError {
        kraken.get().page("vkusvill");
        kraken.perform().loginAs("user");

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Не работает авторизация на витрине магазина\n");

        kraken.perform().quickLogout();
    }


    @Test(
            description = "Тест успешной деавторизации",
            groups = {"regression"},
            priority = 108
    )
    public void successLogout() throws Exception, AssertionError {
        kraken.perform().loginAs("admin");

        kraken.perform().logout();

        assertPageIsAvailable(); // Assert there is no problems after logout

        kraken.get().page("metro");
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Не работает деавторизация\n");
    }


    @Test(
            description = "Тест авторизации из адресной модалки феникса",
            groups = {"regression"},
            priority = 109
    )
    public void successAuthFromAddressModal() throws Exception, AssertionError {
        kraken.get().page("metro");
        kraken.perform().dropAuth();

        kraken.shipAddress().openAddressModal();
        kraken.perform().click(Elements.Site.AddressModal.authButton());
        kraken.perform().authSequence("admin");

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Не работает авторизация из адресной модалки феникса\n");

        kraken.perform().quickLogout();
    }


    @Test(
            description = "Тест успешной авторизации из корзины",
            groups = {"regression"},
            priority = 110
    )
    public void successAuthFromCart() throws Exception {
        SoftAssert softAssert = new SoftAssert();
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

        softAssert.assertTrue(kraken.detect().isOnCheckout(),
                "Нет автоперехода в чекаут после авторизации из корзины\n");

        kraken.get().baseUrl();

        softAssert.assertTrue(kraken.detect().isUserAuthorised(),
                "Не работает авторизация из из корзины\n");

        softAssert.assertFalse(kraken.detect().isCartEmpty(),
                "Пропали товары после авторизации из корзины\n");

        kraken.shopping().closeCart();
        kraken.perform().quickLogout();

        softAssert.assertAll();
    }

    //TODO добавить тесты на авторизацию через соцсети

}
