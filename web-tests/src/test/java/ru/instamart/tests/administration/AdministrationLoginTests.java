package ru.instamart.tests.administration;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.application.Elements;
import ru.instamart.application.Users;
import ru.instamart.application.lib.Pages;
import ru.instamart.application.platform.modules.User;
import ru.instamart.tests.TestBase;

public class AdministrationLoginTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
    }

    @BeforeMethod(alwaysRun = true)
    public void getAdminLoginPage() {
        kraken.get().page(Pages.Admin.login());
    }

    @Test(  description = "Тест валидации элементов логин-страницы админки",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 10051
    )
    public void successValidateAdministrationLoginPage() {
        assertPresence(Elements.Administration.LoginPage.title());
        assertPresence(Elements.Administration.LoginPage.emailField());
        assertPresence(Elements.Administration.LoginPage.passwordField());
        assertPresence(Elements.Administration.LoginPage.submitButton());
    }

    @Test(  description = "Тест неуспешной авторизации с пустыми полями",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 10052
    )
    public void noAuthWithEmptyFields() {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().fillField(Elements.Administration.LoginPage.emailField(), "");
        kraken.perform().fillField(Elements.Administration.LoginPage.passwordField(), "");
        kraken.perform().click(Elements.Administration.LoginPage.submitButton());

        Assert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    failMessage("На логин-странице админки возможна авторизация c пустыми полями")
        );

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.Administration.LoginPage.emailFieldErrorText("Укажите email")),
                                failMessage("Некорректный текст ошибки при авторизации с пустым email")
        );

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.Administration.LoginPage.passwordFieldErrorText("Укажите пароль")),
                                failMessage("Некорректный текст ошибки при авторизации с пустым паролем")
        );

        softAssert.assertAll();
    }

    @Test(  description = "Тест неуспешной авторизации с некорректным логином",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 10053
    )
    public void noAuthWithIncorrectUsername() {

        kraken.perform().fillField(Elements.Administration.LoginPage.emailField(), "wrongUsername");
        kraken.perform().fillField(Elements.Administration.LoginPage.passwordField(), "123456");
        kraken.perform().click(Elements.Administration.LoginPage.submitButton());

        Assert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    failMessage("На логин-странице админки возможна авторизация c некорректным логином")
        );

        Assert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.Administration.LoginPage.emailFieldErrorText("Email адрес имеет неправильный формат")),
                                failMessage("Некорректный текст ошибки при авторизации с некорректным логином")
        );
    }

    @Test(  description = "Тест неуспешной авторизации с несуществующим логином",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 10054
    )
    public void noAuthWithNonexistingUser() {

        kraken.perform().fillField(Elements.Administration.LoginPage.emailField(), "nonexistinguser@instamart.ru");
        kraken.perform().fillField(Elements.Administration.LoginPage.passwordField(), "123456");
        kraken.perform().click(Elements.Administration.LoginPage.submitButton());

        Assert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    failMessage("На логин-странице админки возможна авторизация c несуществующим логином")
        );

        Assert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.Administration.LoginPage.emailFieldErrorText("Неверный email или пароль")),
                                failMessage("Некорректный текст ошибки при авторизации с несуществующим логином")
        );
    }

    @Test(  description = "Тест неуспешной авторизации с коротким паролем",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 10055
    )
    public void noAuthWithShortPassword() {

        kraken.perform().fillField(Elements.Administration.LoginPage.emailField(), Users.superuser().getLogin());
        kraken.perform().fillField(Elements.Administration.LoginPage.passwordField(), "123");
        kraken.perform().click(Elements.Administration.LoginPage.submitButton());

        Assert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    failMessage("На логин-странице админки возможна авторизация c коротким паролем")
        );

        Assert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.Administration.LoginPage.passwordFieldErrorText("Пароль должен содержать не менее 6 символов")),
                                failMessage("Некорректный текст ошибки при авторизации с коротким паролем")
        );
    }

    @Test(  description = "Тест неуспешной авторизации с неверным паролем",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 10056
    )
    public void noAuthWithWrongPassword() {

        kraken.perform().fillField(Elements.Administration.LoginPage.emailField(), Users.superuser().getLogin());
        kraken.perform().fillField(Elements.Administration.LoginPage.passwordField(), "wrongpassword");
        kraken.perform().click(Elements.Administration.LoginPage.submitButton());

        Assert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    failMessage("На логин-странице админки возможна авторизация c неверным паролем")
        );

        Assert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.Administration.LoginPage.emailFieldErrorText("Неверный email или пароль")),
                                failMessage("Некорректный текст ошибки при авторизации с неверным паролем")
        );
    }

    @Test(  description = "Тест успешной авторизации",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 10057
    )
    public void successAuthOnAdminLoginPage() {

        User.Auth.withEmail(Users.superadmin());

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    failMessage("Не работает авторизация на логин-странице админки")
        );
    }
}
