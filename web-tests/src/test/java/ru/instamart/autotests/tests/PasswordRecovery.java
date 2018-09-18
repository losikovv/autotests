package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



    // Тесты восстановления пароля



public class PasswordRecovery extends TestBase {

    @BeforeMethod
    public void preconditions() {
        app.getNavigationHelper().getLandingPage();
        app.getSessionHelper().dropAuth();
    }


    @Test(
            description = "Негативный тест попытки восстановления пароля с незаполненным полем email",
            groups = {"acceptance","regression"},
            priority = 600
    )
    public void noRecoveryWithEmptyEmail() throws Exception {
        app.getSessionHelper().recoverPassword(null);

        // Assert recovery is not requested
        Assert.assertFalse(app.getSessionHelper().isRecoverySent(),
                "Recover password form was sent with empty email field\n");
    }


    @Test(
            description = "Негативный тест попытки восстановления пароля c некорректным email",
            groups = {"regression"},
            priority = 601
    )
    public void noRecoveryWithWrongEmail() throws Exception {
        app.getSessionHelper().recoverPassword("wrongemail.example.com");

        // Assert recovery is not requested
        Assert.assertFalse(app.getSessionHelper().isRecoverySent(),
                "Recover password form was sent with wrong email field\n");
    }


    @Test(
            description = "Негативный тест попытки восстановления пароля для несуществующего пользователя",
            groups = {"acceptance","regression"},
            priority = 602
    )
    public void noRecoveryForNonexistingUser() throws Exception {
        app.getSessionHelper().recoverPassword("nonexistinguser@example.com");

        // Assert recovery is not requested
        Assert.assertFalse(app.getSessionHelper().isRecoverySent(),
                "Recover password form was sent for nonexisting user\n");
    }


    @Test(
            description = "Тест успешной отправки восстановления пароля на лендинге",
            groups = {"acceptance","regression"},
            priority = 603
    )
    public void successRecoveryOnLanding() throws AssertionError, Exception {
        app.getSessionHelper().recoverPassword("instatestuser@yandex.ru");

        // Assert recovery is requested
        Assert.assertTrue(app.getSessionHelper().isRecoverySent(),
                "Recover password form wasn't sent\n");
    }


    @Test(
            description = "Тест успешной отправки восстановления пароля на витрине ритейлера",
            groups = {"regression"},
            priority = 604
    )
    public void successRecoveryOnRetailer() throws AssertionError, Exception {
        app.getNavigationHelper().getRetailerPage("metro");
        app.getSessionHelper().recoverPassword("instatestuser@yandex.ru");

        // Assert recovery is requested
        Assert.assertTrue(app.getSessionHelper().isRecoverySent(),
                "Recover password form wasn't sent\n");
    }
}
