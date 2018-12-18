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
        Assert.assertFalse(kraken.detect().isRecoveryRequested(),
                "Форма восстановления пароля отправлена с пустым полем email\n");
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
        Assert.assertFalse(kraken.detect().isRecoveryRequested(),
                "Форма восстановления пароля отправлена с некорректным email\n");
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
        Assert.assertFalse(kraken.detect().isRecoveryRequested(),
                "Форма восстановления пароля отправлена для несуществующего пользователя\n");
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
        Assert.assertTrue(kraken.detect().isRecoveryRequested(),
                "Форма восстановления пароля не была отправлена на лендинге\n");
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
        Assert.assertTrue(kraken.detect().isRecoveryRequested(),
                "Форма восстановления пароля не была отправлена на витрине ритейлера\n");


    }


    @Test (
            description = "Тест возможности открыть авторизационную модалку после отправки формы восстановления пароля",
            groups = {"regression"},
            priority = 605
    )
    public void openAuthModalAfterRecoveryRequest() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().recoverPassword("instatestuser@yandex.ru");
        kraken.perform().closeAuthModal();
        kraken.perform().openAuthModal();

        Assert.assertFalse(kraken.detect().isRecoveryRequested(),
                "Невозможно открыть авторизационную модалку после отправки формы восстановления пароля\n");
    }


    @Test (
            description = "Авторизация в инстамарте со старым паролем после запроса на восстановление, но до сброса старого пароля",
            groups = {"regression"},
            priority = 606
    )
    public void successAuthAfterInitRecovery() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().recoverPassword("instatestuser@yandex.ru");
        kraken.get().baseUrl();
        kraken.perform().login("instatestuser@yandex.ru", "instamart");

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Невозможно авторизоваться после запроса на восстановление пароля\n");

        kraken.perform().dropAuth();
    }


    @Test (
            description = "Авторизация в инстамарте с новым паролем после восстановления пароля\n",
            groups = {"regression"},
            priority = 607
    )
    public void successAuthAfterCompleteRecovery() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();
        kraken.perform().recoverPassword("instamartmailtest@gmail.com");

        kraken.perform().authGmail("instamartmailtest@gmail.com", "instamart");
        kraken.perform().openLastGmail();
        kraken.perform().clickRecoveryInMail();
        kraken.perform().submitRecovery("password1", "password1");

        kraken.perform().dropAuth();
        kraken.perform().login("instamartmailtest@gmail.com", "password1");

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Невозможно авторизоваться с новым паролем после восстановления пароля\n");

        kraken.perform().dropAuth();
    }


    @Test (
            description = "Авторизация в инстамарте со старым паролем после восстановления пароля\n",
            groups = {"regression"},
            priority = 608
    )
    public void noAuthAfterCompleteRecovery() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();
        kraken.perform().recoverPassword("instamartmailtest@gmail.com");

        kraken.perform().authGmail("instamartmailtest@gmail.com", "instamart");
        kraken.perform().openLastGmail();
        kraken.perform().clickRecoveryInMail();
        kraken.perform().submitRecovery("password2", "password2");

        kraken.perform().dropAuth();
        kraken.perform().login("instamartmailtest@gmail.com", "password1");
        
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Возможно авторизоваться со старым паролем после восстановления пароля!\n");

        kraken.perform().dropAuth();
    }
}