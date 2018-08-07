package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.UserData;



    // Тесты авторизации



public class Authorisation extends TestBase {


    @Test(
            description = "Негативный тест попытки авторизации без email",
            groups = {"regression"},
            priority = 101
    )
    public void noAuthWithoutEmail() throws Exception, AssertionError {
        app.getNavigationHelper().getLandingPage();
        app.getSessionHelper().dropAuth();
        app.getSessionHelper().doLogin(new UserData("", "instamart"));

        // Assert user isn't authorised
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised(),
                "It's possible to log-in without entering an email!\n");
    }


    @Test(
            description = "Негативный тест попытки авторизации без пароля",
            groups = {"regression"},
            priority = 102
    )
    public void noAuthWithoutPassword() throws Exception, AssertionError {
        app.getNavigationHelper().getLandingPage();
        app.getSessionHelper().dropAuth();
        app.getSessionHelper().doLogin(new UserData("instatestuser@yandex.ru", ""));

        // Assert user isn't authorised
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised(),
                "It's possible to log-in without entering a password!"+"\n");
    }


    @Test(
            description = "Негативный тест попытки авторизации несуществующим юзером",
            groups = {"regression"},
            priority = 103
    )
    public void noAuthWithNonexistingUser() throws Exception, AssertionError {
        app.getNavigationHelper().getLandingPage();
        app.getSessionHelper().dropAuth();
        app.getSessionHelper().doLogin(new UserData("nonexistinguser@example.com", "password"));

        // Assert user isn't authorised
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised(),
                "It's possible to log-in with non-existing username!"+"\n");
    }


    @Test(
            description = "Негативный тест попытки авторизации с неверным паролем",
            groups = {"acceptance","regression"},
            priority = 104
    )
    public void noAuthWithWrongPassword() throws Exception, AssertionError {
        app.getNavigationHelper().getLandingPage();
        app.getSessionHelper().dropAuth();
        app.getSessionHelper().doLogin(new UserData("instatestuser@yandex.ru", "wrongpassword"));

        // Assert user isn't authorised
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised(),
                "It's possible to log-in with wrong password!"+"\n");
    }


    @Test(
            description = "Тест успешной авторизации на лендинге",
            groups = {"smoke","acceptance","regression"},
            priority = 105
    )
    public void successAuthOnLandingPage() throws Exception, AssertionError {
        app.getNavigationHelper().getLandingPage();
        app.getSessionHelper().dropAuth();
        app.getSessionHelper().doLogin(new UserData("instatestuser@yandex.ru", "instamart"));

        // Assert user is authorised
        Assert.assertTrue(app.getSessionHelper().isUserAuthorised(),
                "Can't approve correct authorisation, check manually\n");

        app.getSessionHelper().doLogout();
    }


    @Test(
            description = "Тест успешной авторизации на витрине",
            groups = {"acceptance","regression"},
            priority = 106
    )
    public void successAuthOnRetailerPage() throws Exception, AssertionError {
        app.getNavigationHelper().getPage("vkusvill");
        app.getSessionHelper().dropAuth();
        app.getSessionHelper().doLogin(new UserData("instatestuser@yandex.ru", "instamart"));

        // Assert user is authorised
        Assert.assertTrue(app.getSessionHelper().isUserAuthorised(),
                "Can't approve correct authorisation, check manually\n");

        app.getSessionHelper().doLogout();
    }


    @Test(
            description = "Тест логаута",
            groups = {"regression"},
            priority = 107
    )
    public void logout() throws Exception, AssertionError {
        app.getSessionHelper().doLoginAs("admin");
        app.getSessionHelper().doLogout();

        // Assert there is no problems after logout
        assertPageIsAvailable();

        app.getNavigationHelper().getRetailerPage("metro");

        // Assert user is unauthorised
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised(),
                "Can't approve correct de-authorization, check manually\n");
    }

    //TODO добавить тесты на авторизацию через соцсети

}
