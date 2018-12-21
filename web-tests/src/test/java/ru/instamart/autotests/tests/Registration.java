package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.testdata.Generate;


// Тесты регистрации пользователя


public class Registration extends TestBase {


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с пустыми полями",
            groups = {"acceptance", "regression"},
            priority = 1
    )
    public void noRegWithEmptyRequisites() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().registration(null, null, null, null);

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.nameErrorMessage()),
                "Нет пользовательской ошибки пустого поля name\n");

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.emailErrorMessage()),
                "Нет пользовательской ошибки пустого поля email\n");

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.passwordErrorMessage()),
                "Нет пользовательской ошибки пустого поля password\n");

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.passwordConfirmationErrorMessage()),
                "Нет пользовательской ошибки пустого поля password confirmation\n");

        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Зарегистрирован пользователь без данных!\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без имени",
            groups = {"regression"},
            priority = 2
    )
    public void noRegWithoutName() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().registration(null, "test@example.com", "12345678", "12345678");

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.nameErrorMessage()),
                "Нет пользовательской ошибки пустого поля name\n");

        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Зарегистрирован пользователь без имени!\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без email",
            groups = {"regression"},
            priority = 3
    )
    public void noRegWithoutEmail() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().registration("Test User", null, "12345678", "12345678");

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.emailErrorMessage()),
                "Нет пользовательской ошибки пустого поля email\n");

        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Зарегистрирован пользователь без email!\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без пароля",
            groups = {"regression"},
            priority = 4
    )
    public void noRegWithoutPassword() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().registration("Test User", "test@example.com", null, "12345678");

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.passwordErrorMessage()),
                "Нет пользовательской ошибки пустого поля password\n");

        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Зарегистрирован пользователь без пароля!\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без подтверждения пароля",
            groups = {"regression"},
            priority = 5
    )
    public void noRegWithoutPasswordConfirmation() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().registration("Test User", "test@example.com", "12345678", null);

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.passwordConfirmationErrorMessage()),
                "Нет пользовательской ошибки пустого поля password confirmation\n");

        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Зарегистрирован пользователь без подтверждения пароля!\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с несовпадающими паролями",
            groups = {"regression"},
            priority = 6
    )
    public void noRegWithWrongPasswordConfirmation() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().registration("Test User", "test@example.com", "12345678", "12345679");

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.passwordConfirmationErrorMessage()),
                "Нет пользовательской ошибки несовпадения пароля\n");

        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Зарегистрирован пользователь с некорректно подтверждённым паролем!\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки повторно зарегистрировать существующего пользователя",
            groups = {"regression"},
            priority = 7
    )
    public void noRegWithExistingEmail() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().registration("Test User", "autotestuser@instamart.ru", "12345678", "12345678");

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.emailErrorMessage()),
                "Нет пользовательской ошибки регистрации с уже зарегистрированным email\n");

        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Зарегистрирован пользователь с уже используемым email!\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с длинными полями",
            groups = {"regression"},
            priority = 8
    )
    public void noRegWithLongFields() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().registration(Generate.testUserData(129));

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.nameErrorMessage()),
                "Нет пользовательской ошибки превышения длины поля name\n");

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.emailErrorMessage()),
                "Нет пользовательской ошибки превышения длины поля email\n");

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.passwordErrorMessage()),
                "Нет пользовательской ошибки превышения длины поля password\n");

        // Решили не выводить ошибку
        // softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.passwordConfirmationErrorMessage()),
        //        "Нет пользовательской ошибки превышения длины поля password confirmation\n");

        kraken.get().baseUrl();
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Пользователь зарегистрирован, при наличии ошибок заполнения формы регистрации\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Регистрация нового пользователя на лендинге",
            groups = {"regression"},
            priority = 9
    )
    public void successRegOnLandingPage() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();

        kraken.perform().registration(Generate.testUserData());

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Пользователь не авторизован после регистрации на лендинге\n");

        kraken.perform().logout();
    }


    @Test(
            description = "Регистрация нового пользователя на витрине магазина",
            groups = {"acceptance", "regression"},
            priority = 10
    )
    public void successRegOnRetailerPage() throws Exception {
        kraken.get().page("metro");
        kraken.perform().dropAuth();

        kraken.perform().registration(Generate.testUserData());

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Пользователь не авторизован после регистрации на витрине магазина\n");

        kraken.perform().logout();
    }


    @Test(
            description = "Тест регистрации с адресной модалки феникса",
            groups = {"regression"},
            priority = 11
    )
    public void successRegistrationFromAddressModal() throws Exception, AssertionError {
        kraken.get().page("metro");

        kraken.shipAddress().openAddressModal();
        kraken.perform().click(Elements.Site.AddressModal.authButton());
        kraken.perform().regSequence(Generate.testUserData());

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Пользователь не авторизован после регистрации с адресной модалки феникса\n");

        kraken.perform().logout();
    }


    @Test(
            description = "Тест регистрации при переходе из корзины в чекаут",
            groups = {"regression"},
            priority = 12
    )
    public void successRegFromCart() throws Exception {
        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
        kraken.shopping().collectItems();
        kraken.shopping().openCart();
        kraken.perform().click(Elements.Site.Cart.checkoutButton());
        kraken.perform().click(Elements.Site.AuthModal.registrationTab());

        kraken.perform().regSequence(Generate.testUserData());

        Assert.assertTrue(kraken.detect().isOnCheckout(),
                "Нет автоперехода в чекаут после регистрации из корзины\n");

        kraken.get().baseUrl();

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Юзер неавторизован после регистрации из корзины\n");

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Пропали товары после регистрации из корзины\n");

        kraken.shopping().closeCart();
        kraken.perform().logout();
    }
}