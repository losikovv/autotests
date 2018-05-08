package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.UserData;

public class Authorisation extends TestBase {


    @Test(
            description = "Негативный тест попытки авторизации без email",
            groups = {"regression"},
            priority = 1
    )
    public void noAuthWithoutEmail() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getSessionHelper().dropAuth();
        app.getSessionHelper().doLogin(new UserData("", "instamart", null));

        // Assert user isn't authorised
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised(),
                "It's possible to log-in without entering an email!\n");
    }


    @Test(
            description = "Негативный тест попытки авторизации без пароля",
            groups = {"regression"},
            priority = 2
    )
    public void noAuthWithoutPassword() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getSessionHelper().dropAuth();
        app.getSessionHelper().doLogin(new UserData("instatestuser@yandex.ru", "", null));

        // Assert user isn't authorised
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised(),
                "It's possible to log-in without entering a password!"+"\n");
    }


    @Test(
            description = "Негативный тест попытки авторизации несуществующим юзером",
            groups = {"regression"},
            priority = 3
    )
    public void noAuthWithNonexistingUser() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getSessionHelper().dropAuth();
        app.getSessionHelper().doLogin(new UserData("nonexistinguser@example.com", "password", null));

        // Assert user isn't authorised
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised(),
                "It's possible to log-in with nonexisting username!"+"\n");
    }


    @Test(
            description = "Негативный тест попытки авторизации с неверным паролем",
            groups = {"acceptance","regression"},
            priority = 3
    )
    public void noAuthWithWrongPassword() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getSessionHelper().dropAuth();
        app.getSessionHelper().doLogin(new UserData("instatestuser@yandex.ru", "wrongpassword", null));

        // Assert user isn't authorised
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised(),
                "It's possible to log-in with wrong password!"+"\n");
    }


    @Test(
            description = "Тест успешной авторизации на лендинге",
            groups = {"smoke","acceptance","regression"},
            priority = 4
    )
    public void successAuthOnLandingPage() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getSessionHelper().dropAuth();
        app.getSessionHelper().doLogin(new UserData("instatestuser@yandex.ru", "instamart", null));

        // Assert user is authorised
        Assert.assertTrue(app.getSessionHelper().isUserAuthorised(),
                "Can't approve the authorisation is successful"+"\n");

        app.getSessionHelper().doLogout();
    }


    @Test(
            description = "Тест успешной авторизации на витрине",
            groups = {"smoke","acceptance","regression"},
            priority = 5
    )
    public void successAuthOnRetailerPage() throws Exception {
        app.getNavigationHelper().getPage("vkusvill");
        app.getSessionHelper().dropAuth();
        app.getSessionHelper().doLogin(new UserData("instatestuser@yandex.ru", "instamart", null));

        // Assert user is authorised
        Assert.assertTrue(app.getSessionHelper().isUserAuthorised(),
                "Can't approve the authorisation is successful"+"\n");

        app.getSessionHelper().doLogout();
    }

    //TODO добавить тесты на авторизацию через соцсети

}
