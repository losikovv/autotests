package ru.instamart.tests.user;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.application.AppManager;
import ru.instamart.application.Users;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.platform.modules.User;
import ru.instamart.tests.TestBase;

public class UserPasswordRecoveryTests extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void quickLogout() {
        User.Do.quickLogout();
    }

    @Test(
            description = "Негативный тест попытки восстановления пароля с пустым полем email",
            groups = {"regression"},
            priority = 451
    )
    public void noRecoveryWithEmptyEmail() {
        User.Do.requestPasswordRecovery("");

        Assert.assertFalse(kraken.detect().isRecoveryRequested(),
                failMessage("Отправляется запрос на восстановление пароля с пустым полем email"));
    }

    @Test(
            description = "Негативный тест попытки восстановления пароля c некорректным email",
            groups = {"regression"},
            priority = 452
    )
    public void noRecoveryWithWrongEmail() {
        User.Do.requestPasswordRecovery("wrongemail.example.com");

        Assert.assertFalse(kraken.detect().isRecoveryRequested(),
                failMessage("Отправляется запрос на восстановление пароля с некорректным email"));
    }

    @Test(
            description = "Негативный тест попытки восстановления пароля для несуществующего пользователя",
            groups = {"regression"},
            priority = 453
    )
    public void noRecoveryForNonexistingUser() {
        User.Do.requestPasswordRecovery("nonexistinguser@example.com");

        Assert.assertFalse(kraken.detect().isRecoveryRequested(),
                failMessage("Отправляется запрос на восстановление пароля для несуществующего пользователя"));
    }

    @Test(
            description = "Тест успешной отправки восстановления пароля на лендинге",
            groups = {"acceptance","regression"},
            priority = 454
    )
    public void successRequestRecoveryOnLanding() {
        User.Do.requestPasswordRecovery(Users.superuser());

        Assert.assertTrue(kraken.detect().isRecoveryRequested(),
                failMessage("Не отправляется запрос на восстановление пароля на лендинге"));
    }

    @Test(
            description = "Тест успешной отправки восстановления пароля на главной",
            priority = 455,
            groups = {
                    "acceptance","regression",
                    "sbermarket-acceptance","sbermarket-regression"}
    )
    public void successRequestRecoveryOnRetailer() {
        kraken.get().page("metro");

        User.Do.requestPasswordRecovery(Users.superuser());

        Assert.assertTrue(kraken.detect().isRecoveryRequested(),
                failMessage("Не отправляется запрос на восстановление пароля на витрине ритейлера"));
    }

    @Test (
            description = "Тест возможности открыть авторизационную модалку после отправки формы восстановления пароля",
            groups = {"regression"},
            priority = 456
    )
    public void successOpenAuthModalAfterRecovery() {
        User.Do.requestPasswordRecovery(Users.superuser());

        Shop.RecoveryModal.close();
        Shop.AuthModal.open();

        Assert.assertFalse(kraken.detect().isRecoveryRequested(),
                failMessage("Невозможно открыть авторизационную модалку после отправки формы восстановления пароля"));
    }

    @Test (
            description = "Тест на авторизацию с текущим паролем после запроса на восстановление",
            groups = {"regression"},
            priority = 457
    )
    public void successAuthWithCurrentPasswordAfterRecoveryRequest() {
        User.Do.requestPasswordRecovery(AppManager.session.user);
        kraken.get().baseUrl();

        User.Do.loginAs(AppManager.session.user);

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                failMessage("Невозможно авторизоваться с текущим паролем после запроса на восстановление пароля"));
    }

    @Test (
            description = "Тест на авторизацию с новым паролем после восстановления",
            groups = {"regression"},
            priority = 458
    )
    public void successPasswordRecovery() {
        SoftAssert softAssert = new SoftAssert();
        User.Do.requestPasswordRecovery(Users.userGmail());

        User.Do.authGmail(Users.userGmail());
        User.Do.openLastGmail();
        User.Do.clickRecoveryInMail();
        Shop.RecoveryModal.fillRecoveryForm("password1", "password1");
        Shop.RecoveryModal.submitRecovery();

        User.Do.quickLogout();
        User.Do.login(Users.userGmail().getLogin(), "password1");

        softAssert.assertTrue(kraken.detect().isUserAuthorised(),
                failMessage("Невозможно авторизоваться с новым паролем после восстановления пароля"));

        User.Do.quickLogout();
        User.Do.requestPasswordRecovery(Users.userGmail());

        kraken.get().url("https://mail.google.com/mail/u/0/h/");
        User.Do.openLastGmail();
        User.Do.clickRecoveryInMail();
        Shop.RecoveryModal.fillRecoveryForm("password2", "password2");
        Shop.RecoveryModal.submitRecovery();

        User.Do.quickLogout();
        User.Do.login(Users.userGmail().getLogin(), "password1");
        
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                failMessage("Возможно авторизоваться со старым паролем после восстановления пароля!"));
    }
}