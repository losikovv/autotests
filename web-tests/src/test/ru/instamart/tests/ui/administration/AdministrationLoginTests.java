package ru.instamart.tests.ui.administration;

import instamart.core.testdata.UserManager;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.common.lib.Pages;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.tests.ui.TestBase;

public class AdministrationLoginTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
    }

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        kraken.get().page(Pages.Admin.login());
    }

    @Test(  description = "Тест валидации элементов логин-страницы админки",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successValidateAdministrationLoginPage() {
        baseChecks.checkIsElementPresent(Elements.Administration.LoginPage.title());
        baseChecks.checkIsElementPresent(Elements.Administration.LoginPage.emailField());
        baseChecks.checkIsElementPresent(Elements.Administration.LoginPage.passwordField());
        baseChecks.checkIsElementPresent(Elements.Administration.LoginPage.submitButton());
    }

    @Test(  description = "Тест неуспешной авторизации с пустыми полями",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
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
            groups = {"sbermarket-acceptance","sbermarket-regression"}
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
            groups = {"sbermarket-acceptance","sbermarket-regression"}
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
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void noAuthWithShortPassword() {

        kraken.perform().fillField(Elements.Administration.LoginPage.emailField(), UserManager.getDefaultUser().getLogin());
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
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void noAuthWithWrongPassword() {

        kraken.perform().fillField(Elements.Administration.LoginPage.emailField(), UserManager.getDefaultUser().getLogin());
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
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAuthOnAdminLoginPage() {

        User.Auth.withEmail(UserManager.getDefaultAdmin());

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    failMessage("Не работает авторизация на логин-странице админки")
        );
    }
}
