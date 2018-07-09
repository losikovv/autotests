package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.RetailerData;
import ru.instamart.autotests.testdata.Generate;



    // Тесты регистрации



public class Registration extends TestBase {


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с пустыми полями",
            groups = {"acceptance", "regression"}
    )
    public void noRegWithEmptyRequisites() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getSessionHelper().dropAuth();
        app.getSessionHelper().regNewUser(null, null, null, null);

        // Assert user is not authorised
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised(),
                "It's possible to register new user with empty requisites!\n");

        // TODO добавить проверку наличия пользователя в админке
        // Assert user is not registered
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без имени",
            groups = {"regression"},
            priority = 1
    )
    public void noRegWithoutName() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getSessionHelper().dropAuth();
        app.getSessionHelper().regNewUser(null, "test@example.com", "12345678", "12345678");

        // Assert user is not authorised
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised(),
                "It's possible to register new user without entering a name!\n");

        // TODO добавить проверку наличия пользователя в админке
        // Assert user is not registered
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без email",
            groups = {"regression"},
            priority = 2
    )
    public void noRegWithoutEmail() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getSessionHelper().dropAuth();
        app.getSessionHelper().regNewUser("Test User", null, "12345678", "12345678");

        // Assert user is not authorised
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised(),
                "It's possible to register new user without entering an email!\n");

        // TODO добавить проверку наличия пользователя в админке
        // Assert user is not registered
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без пароля",
            groups = {"regression"},
            priority = 3
    )
    public void noRegWithoutPassword() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getSessionHelper().dropAuth();
        app.getSessionHelper().regNewUser("Test User", "test@example.com", null, "12345678");

        // Assert user is not authorised
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised(),
                "It's possible to register new user without entering a password!\n");

        // TODO добавить проверку наличия пользователя в админке
        // Assert user is not registered
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без подтверждения пароля",
            groups = {"regression"},
            priority = 4
    )
    public void noRegWithoutPasswordConfirmation() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getSessionHelper().dropAuth();
        app.getSessionHelper().regNewUser("Test User", "test@example.com", "12345678", null);

        // Assert user is not authorised
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised(),
                "It's possible to register new user without entering a password confirmation!\n");

        // TODO добавить проверку наличия пользователя в админке
        // Assert user is not registered
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с несовпадающими паролями",
            groups = {"regression"},
            priority = 5
    )
    public void noRegWithWrongPasswordConfirmation() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getSessionHelper().dropAuth();
        app.getSessionHelper().regNewUser("Test User", "test@example.com", "12345678", "12345679");

        // Assert user is not authorised
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised(),
                "It's possible to register new user with wrong password confirmation!\n");

        // TODO добавить проверку наличия пользователя в админке
        // Assert user is not registered
    }


    @Test(
            description = "Регистрация нового пользователя на лендинге",
            groups = {"acceptance","regression"},
            priority = 6
    )
    public void successRegOnLandingPage() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getSessionHelper().dropAuth();
        app.getSessionHelper().regNewUser(Generate.testUserData());

        // Assert user is authorised
        Assert.assertTrue(app.getSessionHelper().isUserAuthorised(),
                "Can't approve correct registration, check manually\n");

        // TODO добавить проверку что после регистрации авторизованы верным пользователем
        // TODO добавить проверку наличия пользователя в админке

        app.getSessionHelper().doLogout();
    }


    @Test(
            description = "Регистрация нового пользователя на витрине магазина",
            groups = {"regression"},
            priority = 7
    )
    public void successRegOnRetailerPage() throws Exception {
        app.getNavigationHelper().getRetailerPage(new RetailerData("metro"));
        app.getSessionHelper().dropAuth();
        app.getSessionHelper().regNewUser(Generate.testUserData());

        // Assert user is authorised
        Assert.assertTrue(app.getSessionHelper().isUserAuthorised(),
                "Can't approve correct registration, check manually\n");

        // TODO добавить проверку что после регистрации авторизованы верным пользователем
        // TODO добавить проверку наличия пользователя в админке

        app.getSessionHelper().doLogout();
    }

}
