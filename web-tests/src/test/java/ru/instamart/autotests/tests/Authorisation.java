package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Elements;


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

        // Assert user isn't authorised
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "It's possible to log-in without entering an email!\n");
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

        // Assert user isn't authorised
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "It's possible to log-in without entering a password!"+"\n");
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

        // Assert user isn't authorised
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "It's possible to log-in with non-existing username!"+"\n");
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

        // Assert user isn't authorised
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "It's possible to log-in with wrong password!"+"\n");
    }


    @Test(
            description = "Тест успешной авторизации на лендинге",
            groups = {"smoke","acceptance","regression"},
            priority = 105
    )
    public void successAuthOnLandingPage() throws Exception, AssertionError {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().loginAs("user");

        // Assert user is authorised
        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Can't approve correct authorisation, check manually\n");

        kraken.perform().logout();
    }


    @Test(
            description = "Тест успешной авторизации на витрине",
            groups = {"acceptance","regression"},
            priority = 106
    )
    public void successAuthOnRetailerPage() throws Exception, AssertionError {
        kraken.get().page("vkusvill");
        kraken.perform().dropAuth();

        kraken.perform().loginAs("user");

        // Assert user is authorised
        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Can't approve correct authorisation, check manually\n");

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
                "Can't approve correct de-authorization, check manually\n");

    }

    @Test(
            description = "Тест авторизации с адресной модалки феникса",
            groups = {"regression"},
            priority = 108
    )
    public void successAuthFromAddressModal() throws Exception, AssertionError {
        kraken.get().page("metro");
        kraken.perform().dropAuth();

        kraken.perform().click(Elements.Site.Header.setShipAddressButton());
        kraken.perform().click(Elements.Site.AddressModal.authButton());
        kraken.perform().authSequence("admin");

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Can't approve correct authorisation, check manually\n");

        kraken.perform().logout();
    }

    //TODO добавить тесты на авторизацию через соцсети

}
