package ru.instamart.autotests.tests.administration;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.appmanager.User;
import ru.instamart.autotests.tests.TestBase;

public class AdministrationAccessTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Do.quickLogout();
    }

    @BeforeMethod(alwaysRun = true)
    public void getAdminLoginPage() {
        kraken.get().page(Pages.Admin.login());
    }

    @Test(  description = "Тест успешной валидации логин-страницы админки",
            groups = {"acceptance","regression"},
            priority = 10051
    )
    public void successValidateAdministrationLoginPage() {
        assertElementPresence(Elements.Administration.LoginPage.title());
        assertElementPresence(Elements.Administration.LoginPage.emailField());
        assertElementPresence(Elements.Administration.LoginPage.passwordField());
        assertElementPresence(Elements.Administration.LoginPage.submitButton());
    }

    //todo noAuthWithEmptyFields

    //todo noAuthWithWrongPassword

    //todo noAuthWithIncorrectUsername

    //todo noAuthWithNonexistingUsername

    //todo successAuth
}
