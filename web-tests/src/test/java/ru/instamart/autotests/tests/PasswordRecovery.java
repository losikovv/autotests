package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;


// Тесты восстановления пароля


public class PasswordRecovery extends TestBase {


    @Test(
            description = "Негативный тест попытки восстановления пароля с незаполненным полем email",
            groups = {"regression"},
            priority = 600
    )
    public void noRecoveryWithEmptyEmail() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().recoverPassword(null);

        // Assert recovery is not requested
        Assert.assertFalse(kraken.detect().isRecoverySent(),
                "Recover password form was sent with empty email field\n");
    }


    @Test(
            description = "Негативный тест попытки восстановления пароля c некорректным email",
            groups = {"regression"},
            priority = 601
    )
    public void noRecoveryWithWrongEmail() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().recoverPassword("wrongemail.example.com");

        // Assert recovery is not requested
        Assert.assertFalse(kraken.detect().isRecoverySent(),
                "Recover password form was sent with wrong email field\n");
    }


    @Test(
            description = "Негативный тест попытки восстановления пароля для несуществующего пользователя",
            groups = {"regression"},
            priority = 602
    )
    public void noRecoveryForNonexistingUser() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().recoverPassword("nonexistinguser@example.com");

        // Assert recovery is not requested
        Assert.assertFalse(kraken.detect().isRecoverySent(),
                "Recover password form was sent for nonexisting user\n");
    }


    @Test(
            description = "Тест успешной отправки восстановления пароля на лендинге",
            groups = {"acceptance","regression"},
            priority = 603
    )
    public void successRecoveryOnLanding() throws AssertionError, Exception {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().recoverPassword("instatestuser@yandex.ru");

        // Assert recovery is requested
        Assert.assertTrue(kraken.detect().isRecoverySent(),
                "Recover password form wasn't sent\n");
    }


    @Test(
            description = "Тест успешной отправки восстановления пароля на витрине ритейлера",
            groups = {"acceptance","regression"},
            priority = 604
    )
    public void successRecoveryOnRetailer() throws AssertionError, Exception {
        kraken.get().page("metro");
        kraken.perform().dropAuth();

        kraken.perform().recoverPassword("instatestuser@yandex.ru");

        // Assert recovery is requested
        Assert.assertTrue(kraken.detect().isRecoverySent(),
                "Recover password form wasn't sent\n");
    }
}
