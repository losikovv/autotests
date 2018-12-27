package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Users;
import ru.instamart.autotests.models.UserData;
import ru.instamart.autotests.testdata.Generate;


// Тесты авторизации


public class Authorisation extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void quickLogout() throws Exception {
        kraken.perform().quickLogout();
    }


    @Test(
            description = "Негативный тест попытки авторизации без email",
            groups = {"regression"},
            priority = 101
    )
    public void noAuthWithoutEmail() throws Exception, AssertionError {
        SoftAssert softAssert = new SoftAssert();

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

        kraken.perform().login(Users.getCredentials("user").getLogin(), "");

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

        kraken.perform().login(Users.getCredentials("user").getLogin(), "wrongpassword");

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
        kraken.perform().loginAs("user");

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Не работает авторизация на лендинге\n");
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
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");

        kraken.shipAddress().openAddressModal();
        kraken.perform().click(Elements.Site.AddressModal.authButton());

        softAssert.assertTrue(kraken.detect().isAuthModalOpen(),
                "\nНе работает переход на авторизацию из адресной модалки");

        kraken.perform().loginAs("admin");

        softAssert.assertTrue(kraken.detect().isUserAuthorised(),
                "\nНе работает авторизация из адресной модалки феникса");

        softAssert.assertAll();
    }


    @Test(
            description = "Тест успешной авторизации из корзины",
            groups = {"regression"},
            priority = 110
    )
    public void successAuthFromCart() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        final UserData testuser = Generate.testUserData();
        kraken.perform().registration(testuser);
        kraken.perform().quickLogout();
        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());

        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();

        softAssert.assertTrue(kraken.detect().isAuthModalOpen(),
                "\nНе открывается авторизационная модалка при переходе неавторизованным из корзины в чекаут");

        kraken.perform().login(testuser);

        softAssert.assertTrue(kraken.detect().isOnCheckout(),
                "\nНет автоперехода в чекаут после авторизации из корзины");

        kraken.get().baseUrl();

        softAssert.assertTrue(kraken.detect().isUserAuthorised(),
                "\nНе работает авторизация из корзины");

        softAssert.assertFalse(kraken.detect().isCartEmpty(),
                "\nПропали товары после авторизации из корзины");

        softAssert.assertAll();
    }

    //TODO добавить тесты на авторизацию через соцсети

}
