package ru.instamart.tests.user;

import instamart.core.settings.Config;
import instamart.core.testdata.Users;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.TestBase;

public class UserPasswordRecoveryTests extends TestBase {

    // todo не трогать до перехода на авторизацию по номера телефона, изменится логика восстановления

    @BeforeMethod(alwaysRun = true,
            description ="Проверяем залогинен ли пользователь, если да то завершаем сессию")
    public void quickLogout() {
        User.Logout.quickly();
    }

    @Test(
            description = "Негативный тест попытки восстановления пароля с пустым полем email",
            priority = 451,
            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void noRecoveryRequestWithEmptyEmail() {
        User.PasswordRecovery.request(""); // todo проверять что показана пользовательская ошибка

        Assert.assertFalse(
                kraken.detect().isRecoveryRequested(),
                    failMessage("Отправляется запрос на восстановление пароля с пустым полем email"));
    }

    @Test(
            description = "Негативный тест попытки восстановления пароля c некорректным email",
            priority = 452,
            groups = {
                    "metro-regression",
                    "sbermarket-regression",
            }
    ) public void noRecoveryRequestWithWrongEmail() {
        User.PasswordRecovery.request("wrongemail.example.com"); // todo проверять что показана пользовательская ошибка

        Assert.assertFalse(
                kraken.detect().isRecoveryRequested(),
                    failMessage("Отправляется запрос на восстановление пароля с некорректным email"));
    }

    @Test(
            description = "Негативный тест попытки восстановления пароля для несуществующего пользователя",
            priority = 453,
            groups = {
                    "metro-regression",
                    "sbermarket-regression",
            }
    ) public void noRecoveryRequestForNonexistingUser() {
        User.PasswordRecovery.request("nonexistinguser@example.com"); // todo проверять что показана пользовательская ошибка

        Assert.assertFalse(
                kraken.detect().isRecoveryRequested(),
                    failMessage("Отправляется запрос на восстановление пароля для несуществующего пользователя"));
    }

    @Test(
            description = "Тест успешной отправки восстановления пароля на лендинге",
            priority = 454,
            groups = {
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successRecoveryRequestOnLanding() {
        User.PasswordRecovery.request(Users.superuser());

        Assert.assertTrue(
                kraken.detect().isRecoveryRequested(),
                    failMessage("Не отправляется запрос на восстановление пароля на лендинге"));
    }

    @Test(
            description = "Тест успешной отправки восстановления пароля на главной",
            priority = 455,
            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successRequestRecoveryOnMainPage() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        User.PasswordRecovery.request(Users.superuser());

        Assert.assertTrue(
                kraken.detect().isRecoveryRequested(),
                    failMessage("Не отправляется запрос на восстановление пароля на витрине ритейлера"));
    }

    @Test (
            description = "Тест возможности открыть авторизационную модалку после отправки формы восстановления пароля",
            priority = 456,
            groups = {
                    "metro-regression",
                    "sbermarket-regression",
            }
    ) public void successOpenAuthModalAfterRecoveryRequest() {
        User.PasswordRecovery.request(Users.superuser());

        Shop.RecoveryModal.close();
        Shop.AuthModal.open();

        Assert.assertFalse(
                kraken.detect().isRecoveryRequested(),
                    failMessage("Невозможно открыть авторизационную модалку после отправки формы восстановления пароля"));
    }

    @Test (
            description = "Тест успешной авторизации с текущим паролем после отправки запроса на восстановление пароля",
            priority = 457,
            groups = {
                    "metro-regression",
                    "sbermarket-regression",
            }
    ) public void successAuthWithCurrentPasswordAfterRecoveryRequest() {
        User.PasswordRecovery.request(Users.superuser());
        kraken.get().baseUrl();

        User.Do.loginAs(Users.superuser());

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    failMessage("Невозможно авторизоваться с текущим паролем после запроса на восстановление пароля"));
    }

    @Test (
            description = "Тест на авторизацию с новым паролем после восстановления",
            priority = 458,
            groups = {
                    "metro-regression",
                    "sbermarket-regression"
            }
    ) public void successAuthWithNewPasswordAfterCompletePasswordRecovery() {
        User.PasswordRecovery.request(Users.gmail());
        User.PasswordRecovery.complete(Users.gmail(),"newPassword");
        User.Logout.quickly();

        User.Auth.withEmail(Users.gmail().getLogin(), "newPassword");

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    failMessage("Невозможно авторизоваться с новым паролем после восстановления пароля"));
    }

    @Test (
            description = "Тест на авторизацию с новым паролем после восстановления",
            priority = 459,
            groups = {
                    "metro-regression",
                    "sbermarket-regression"
            }
    ) public void noAuthWithOldPasswordAfterCompletePasswordRecovery() {
        User.PasswordRecovery.request(Users.gmail());
        User.PasswordRecovery.complete(Users.gmail(),"password");
        User.Logout.quickly();

        User.Auth.withEmail(Users.gmail().getLogin(), "newPassword");

        Assert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    failMessage("Возможно авторизоваться со старым паролем после восстановления пароля"));
    }
}