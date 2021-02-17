package ru.instamart.tests.ui.administration;

import instamart.core.testdata.UserManager;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.checkpoints.users.AdminPageCheckpoints;
import instamart.ui.common.lib.Pages;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

public class AdministrationLoginTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    AdminPageCheckpoints adminChecks = new AdminPageCheckpoints();
    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
    }

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        User.Logout.quickly();
        kraken.get().page(Pages.Admin.login());
    }

    @CaseId(439)
    @Test(  description = "Тест валидации элементов логин-страницы админки",
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    )
    public void successValidateAdministrationLoginPage() {
        baseChecks.checkIsElementPresent(Elements.Administration.LoginPage.title());
        baseChecks.checkIsElementPresent(Elements.Administration.LoginPage.emailField());
        baseChecks.checkIsElementPresent(Elements.Administration.LoginPage.passwordField());
        baseChecks.checkIsElementPresent(Elements.Administration.LoginPage.submitButton());
    }

    @CaseId(440)
    @Test(  description = "Тест неуспешной авторизации с пустыми полями",
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    )
    public void noAuthWithEmptyFields() {
        kraken.perform().fillField(Elements.Administration.LoginPage.emailField(), "");
        kraken.perform().fillField(Elements.Administration.LoginPage.passwordField(), "");
        kraken.perform().click(Elements.Administration.LoginPage.submitButton());
        adminChecks.checkIsUserNotAutorisedAdminPage("На логин-странице админки возможна авторизация c пустыми полями");
        adminChecks.checkErrorMessageUserNameAdminPage("Укажите email",
                "Некорректный текст ошибки при авторизации с пустым email");
        adminChecks.checkPasswordFieldErrorTextAdminPage("Укажите пароль",
                "Некорректный текст ошибки при авторизации с пустым паролем");
    }

    @CaseId(441)
    @Test(  description = "Тест неуспешной авторизации с некорректным логином",
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    )
    public void noAuthWithIncorrectUsername() {
        kraken.perform().fillField(Elements.Administration.LoginPage.emailField(), "wrongUsername");
        kraken.perform().fillField(Elements.Administration.LoginPage.passwordField(), "123456");
        kraken.perform().click(Elements.Administration.LoginPage.submitButton());
        adminChecks.checkIsUserNotAutorisedAdminPage("На логин-странице админки возможна авторизация c некорректным логином");
        adminChecks.checkErrorMessageUserNameAdminPage("Email адрес имеет неправильный формат",
                "Некорректный текст ошибки при авторизации с некорректным логином");
    }

    @CaseId(442)
    @Test(  description = "Тест неуспешной авторизации с несуществующим логином",
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    )
    public void noAuthWithNonexistingUser() {
        kraken.perform().fillField(Elements.Administration.LoginPage.emailField(), "nonexistinguser@instamart.ru");
        kraken.perform().fillField(Elements.Administration.LoginPage.passwordField(), "123456");
        kraken.perform().click(Elements.Administration.LoginPage.submitButton());
        adminChecks.checkIsUserNotAutorisedAdminPage("На логин-странице админки возможна авторизация c несуществующим логином");
        adminChecks.checkErrorMessageUserNameAdminPage("Неверный email или пароль",
                "Некорректный текст ошибки при авторизации с несуществующим логином");
    }

    @CaseId(443)
    @Test(  description = "Тест неуспешной авторизации с коротким паролем",
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    )
    public void noAuthWithShortPassword() {
        kraken.perform().fillField(Elements.Administration.LoginPage.emailField(), UserManager.getDefaultUser().getLogin());
        kraken.perform().fillField(Elements.Administration.LoginPage.passwordField(), "123");
        kraken.perform().click(Elements.Administration.LoginPage.submitButton());
        adminChecks.checkIsUserNotAutorisedAdminPage("На логин-странице админки возможна авторизация c коротким паролем");
        adminChecks.checkPasswordFieldErrorTextAdminPage("Пароль должен содержать не менее 6 символов",
                "Некорректный текст ошибки при авторизации с коротким паролем");
    }

    @CaseId(444)
    @Test(  description = "Тест неуспешной авторизации с неверным паролем",
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    )
    public void noAuthWithWrongPassword() {
        kraken.perform().fillField(Elements.Administration.LoginPage.emailField(), UserManager.getDefaultUser().getLogin());
        kraken.perform().fillField(Elements.Administration.LoginPage.passwordField(), "wrongpassword");
        kraken.perform().click(Elements.Administration.LoginPage.submitButton());
        adminChecks.checkIsUserNotAutorisedAdminPage("На логин-странице админки возможна авторизация c неверным паролем");
        adminChecks.checkPasswordFieldErrorTextAdminPage("Неверный email или пароль",
                "Некорректный текст ошибки при авторизации с неверным паролем");
    }

    @CaseId(415)
    @Test(  description = "Тест успешной авторизации",
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    )
    public void successAuthOnAdminLoginPage() {
        User.Auth.withEmail(UserManager.getDefaultAdmin());
        adminChecks.checkIsAdminPageOpen();
    }
}
