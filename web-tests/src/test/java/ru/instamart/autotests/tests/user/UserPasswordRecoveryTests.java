package ru.instamart.autotests.tests.user;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Users;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.appmanager.ApplicationManager.session;

public class UserPasswordRecoveryTests extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void quickLogout() {
        kraken.perform().quickLogout();
    }

    @Test(
            description = "Негативный тест попытки восстановления пароля с пустым полем email",
            groups = {"regression"},
            priority = 451
    )
    public void noRecoveryWithEmptyEmail() {
        kraken.perform().recoverPassword(null);

        Assert.assertFalse(kraken.detect().isRecoveryRequested(),
                "Отправляется запрос на восстановление пароля с пустым полем email\n");
    }

    @Test(
            description = "Негативный тест попытки восстановления пароля c некорректным email",
            groups = {"regression"},
            priority = 452
    )
    public void noRecoveryWithWrongEmail() {
        kraken.perform().recoverPassword("wrongemail.example.com");

        Assert.assertFalse(kraken.detect().isRecoveryRequested(),
                "Отправляется запрос на восстановление пароля с некорректным email\n");
    }

    @Test(
            description = "Негативный тест попытки восстановления пароля для несуществующего пользователя",
            groups = {"regression"},
            priority = 453
    )
    public void noRecoveryForNonexistingUser() {
        kraken.perform().recoverPassword("nonexistinguser@example.com");

        Assert.assertFalse(kraken.detect().isRecoveryRequested(),
                "Отправляется запрос на восстановление пароля для несуществующего пользователя\n");
    }

    @Test(
            description = "Тест успешной отправки восстановления пароля на лендинге",
            groups = {"acceptance","regression"},
            priority = 454
    )
    public void successRequestRecoveryOnLanding() {
        kraken.perform().recoverPasswordAs(Users.superuser());

        Assert.assertTrue(kraken.detect().isRecoveryRequested(),
                "Не отправляется запрос на восстановление пароля на лендинге\n");
    }

    @Test(
            description = "Тест успешной отправки восстановления пароля на витрине ритейлера",
            groups = {"acceptance","regression"},
            priority = 455
    )
    public void successRequestRecoveryOnRetailer() {
        kraken.get().page("metro");

        kraken.perform().recoverPasswordAs(Users.superuser());

        Assert.assertTrue(kraken.detect().isRecoveryRequested(),
                "Не отправляется запрос на восстановление пароля на витрине ритейлера\n");
    }

    @Test (
            description = "Тест возможности открыть авторизационную модалку после отправки формы восстановления пароля",
            groups = {"regression"},
            priority = 456
    )
    public void successOpenAuthModalAfterRecovery() {
        kraken.perform().recoverPasswordAs(Users.superuser());

        kraken.perform().closeAuthModal();
        kraken.perform().openAuthModal();

        Assert.assertFalse(kraken.detect().isRecoveryRequested(),
                "Невозможно открыть авторизационную модалку после отправки формы восстановления пароля\n");
    }

    @Test (
            description = "Тест на авторизацию с текущим паролем после запроса на восстановление",
            groups = {"regression"},
            priority = 457
    )
    public void successAuthWithCurrentPasswordAfterRecoveryRequest() {
        kraken.perform().recoverPasswordAs(session.user);
        kraken.get().baseUrl();
        
        kraken.perform().loginAs(session.user);

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Невозможно авторизоваться с текущим паролем после запроса на восстановление пароля\n");
    }

    @Test (
            description = "Тест на авторизацию с новым паролем после восстановления",
            groups = {"regression"},
            priority = 458
    )
    public void successPasswordRecovery() {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().recoverPasswordAs(Users.userGmail());

        kraken.perform().authGmail(Users.userGmail());
        kraken.perform().openLastGmail();
        kraken.perform().clickRecoveryInMail();
        kraken.perform().submitRecovery("password1", "password1");

        kraken.perform().quickLogout();
        kraken.perform().authorisation(Users.userGmail().getEmail(), "password1");

        softAssert.assertTrue(kraken.detect().isUserAuthorised(),
                "Невозможно авторизоваться с новым паролем после восстановления пароля\n");

        kraken.perform().quickLogout();
        kraken.perform().recoverPasswordAs(Users.userGmail());

        kraken.get().url("https://mail.google.com/mail/u/0/h/");
        kraken.perform().openLastGmail();
        kraken.perform().clickRecoveryInMail();
        kraken.perform().submitRecovery("password2", "password2");

        kraken.perform().quickLogout();
        kraken.perform().authorisation(Users.userGmail().getEmail(), "password1");
        
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Возможно авторизоваться со старым паролем после восстановления пароля!\n");
    }
}