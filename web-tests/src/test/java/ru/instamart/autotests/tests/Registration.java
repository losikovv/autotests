package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.testdata.Generate;


public class Registration extends TestBase {


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с пустыми полями",
            groups = {"acceptance", "regression"}
    )
    public void noRegWithEmptyRequisites() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().registration(null, null, null, null);

        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "It's possible to register new user with empty requisites!\n");
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без имени",
            groups = {"regression"},
            priority = 1
    )
    public void noRegWithoutName() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().registration(null, "test@example.com", "12345678", "12345678");

        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "It's possible to register new user without entering a name!\n");
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без email",
            groups = {"regression"},
            priority = 2
    )
    public void noRegWithoutEmail() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().registration("Test User", null, "12345678", "12345678");

        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "It's possible to register new user without entering an email!\n");
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без пароля",
            groups = {"regression"},
            priority = 3
    )
    public void noRegWithoutPassword() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().registration("Test User", "test@example.com", null, "12345678");

        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "It's possible to register new user without entering a password!\n");
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без подтверждения пароля",
            groups = {"regression"},
            priority = 4
    )
    public void noRegWithoutPasswordConfirmation() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().registration("Test User", "test@example.com", "12345678", null);

        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "It's possible to register new user without entering a password confirmation!\n");
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с несовпадающими паролями",
            groups = {"regression"},
            priority = 5
    )
    public void noRegWithWrongPasswordConfirmation() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().registration("Test User", "test@example.com", "12345678", "12345679");

        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "It's possible to register new user with wrong password confirmation!\n");
    }


    @Test(
            description = "Негативный тест попытки повторно зарегистрировать существующего пользователя",
            groups = {"regression"},
            priority = 6
    )
    public void noRegWithExistingEmail() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().registration("Test User", "autotestuser@instamart.ru", "12345678", "12345679");

        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "It's possible to register new user with email of already existing one!\n");
    }


    @Test(
            description = "Регистрация нового пользователя на лендинге",
            groups = {"acceptance","regression"},
            priority = 7
    )
    public void successRegOnLandingPage() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().registration(Generate.testUserData());

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Can't approve correct registration, check manually\n");

        kraken.perform().logout();
    }


    @Test(
            description = "Регистрация нового пользователя на витрине магазина",
            groups = {"regression"},
            priority = 8
    )
    public void successRegOnRetailerPage() throws Exception {
        kraken.get().page("metro");
        kraken.perform().dropAuth();

        kraken.perform().registration(Generate.testUserData());

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Can't approve correct registration, check manually\n");

        kraken.perform().logout();
    }


    @Test(
            description = "Тест регистрации с адресной модалки феникса",
            groups = {"regression"},
            priority = 9
    )
    public void successRegistrationFromAddressModal() throws Exception, AssertionError {
        kraken.get().page("metro");

        kraken.perform().click(Elements.Site.Header.setShipAddressButton());
        kraken.perform().click(Elements.Site.AddressModal.authButton());
        kraken.perform().regSequence(Generate.testUserData());

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Can't approve correct authorisation, check manually\n");

        kraken.perform().logout();
    }

}
