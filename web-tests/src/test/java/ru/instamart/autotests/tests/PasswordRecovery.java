package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Users;


// Тесты восстановления пароля


public class PasswordRecovery extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void quickLogout() throws Exception {
        kraken.perform().quickLogout();
    }


    @Test(
            description = "Негативный тест попытки восстановления пароля с пустым полем email",
            groups = {"regression"},
            priority = 600
    )
    public void noRecoveryWithEmptyEmail() throws Exception {
        kraken.perform().recoverPassword(null);

        Assert.assertFalse(kraken.detect().isRecoveryRequested(),
                "Отправляется запрос на восстановление пароля с пустым полем email\n");
    }


    @Test(
            description = "Негативный тест попытки восстановления пароля c некорректным email",
            groups = {"regression"},
            priority = 601
    )
    public void noRecoveryWithWrongEmail() throws Exception {
        kraken.perform().recoverPassword("wrongemail.example.com");

        Assert.assertFalse(kraken.detect().isRecoveryRequested(),
                "Отправляется запрос на восстановление пароля с некорректным email\n");
    }


    @Test(
            description = "Негативный тест попытки восстановления пароля для несуществующего пользователя",
            groups = {"regression"},
            priority = 602
    )
    public void noRecoveryForNonexistingUser() throws Exception {
        kraken.perform().recoverPassword("nonexistinguser@example.com");

        Assert.assertFalse(kraken.detect().isRecoveryRequested(),
                "Отправляется запрос на восстановление пароля для несуществующего пользователя\n");
    }


    @Test(
            description = "Тест успешной отправки восстановления пароля на лендинге",
            groups = {"acceptance","regression"},
            priority = 603
    )
    public void successRequestRecoveryOnLanding() throws AssertionError, Exception {
        kraken.perform().recoverPasswordAs("user");

        Assert.assertTrue(kraken.detect().isRecoveryRequested(),
                "Не отправляется запрос на восстановление пароля на лендинге\n");
    }


    @Test(
            description = "Тест успешной отправки восстановления пароля на витрине ритейлера",
            groups = {"acceptance","regression"},
            priority = 604
    )
    public void successRequestRecoveryOnRetailer() throws AssertionError, Exception {
        kraken.get().page("metro");

        kraken.perform().recoverPasswordAs("user");

        Assert.assertTrue(kraken.detect().isRecoveryRequested(),
                "Не отправляется запрос на восстановление пароля на витрине ритейлера\n");
    }


    @Test (
            description = "Тест возможности открыть авторизационную модалку после отправки формы восстановления пароля",
            groups = {"regression"},
            priority = 605
    )
    public void successOpenAuthModalAfterRecovery() throws Exception {
        kraken.perform().recoverPasswordAs("user");

        kraken.perform().closeAuthModal();
        kraken.perform().openAuthModal();

        Assert.assertFalse(kraken.detect().isRecoveryRequested(),
                "Невозможно открыть авторизационную модалку после отправки формы восстановления пароля\n");
    }


    @Test (
            description = "Тест на авторизацию с текущим паролем после запроса на восстановление",
            groups = {"regression"},
            priority = 606
    )
    public void successAuthWithCurrentPasswordAfterRecoveryRequest() throws Exception {
        kraken.perform().recoverPasswordAs("user");
        kraken.get().baseUrl();
        
        kraken.perform().loginAs("user");

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Невозможно авторизоваться с текущим паролем после запроса на восстановление пароля\n");
    }


    @Test (
            description = "Тест на авторизацию с новым паролем после восстановления",
            groups = {"regression"},
            priority = 607
    )
    public void successPasswordRecovery() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().recoverPasswordAs("userGmail");

        kraken.perform().authGmail("userGmail");
        kraken.perform().openLastGmail();
        kraken.perform().clickRecoveryInMail();
        kraken.perform().submitRecovery("password1", "password1");

        kraken.perform().quickLogout();
        kraken.perform().login(Users.getCredentials("userGmail").getLogin(), "password1");

        softAssert.assertTrue(kraken.detect().isUserAuthorised(),
                "Невозможно авторизоваться с новым паролем после восстановления пароля\n");

        kraken.perform().quickLogout();
        kraken.perform().recoverPasswordAs("userGmail");

        kraken.get().url("https://mail.google.com/mail/u/0/h/");
        kraken.perform().openLastGmail();
        kraken.perform().clickRecoveryInMail();
        kraken.perform().submitRecovery("password2", "password2");

        kraken.perform().quickLogout();
        kraken.perform().login(Users.getCredentials("userGmail").getLogin(), "password1");
        
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Возможно авторизоваться со старым паролем после восстановления пароля!\n");
    }
}