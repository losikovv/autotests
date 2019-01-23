package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Users;


// Тесты регистрации пользователя


public class Registration extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void quickLogout() throws Exception {
        kraken.perform().quickLogout();
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с пустыми реквизитами",
            groups = {"acceptance","regression"},
            priority = 101
    )
    public void noRegWithEmptyRequisites() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration(null, null, null, null);

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.nameErrorMessage()),
                "Нет пользовательской ошибки пустого поля name\n");

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.emailErrorMessage()),
                "Нет пользовательской ошибки пустого поля email\n");

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.passwordErrorMessage()),
                "Нет пользовательской ошибки пустого поля password\n");

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.passwordConfirmationErrorMessage()),
                "Нет пользовательской ошибки пустого поля password confirmation\n");

        kraken.get().baseUrl();
        softAssert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла регистрация пользователя с пустыми реквизитами\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без имени",
            groups = {"regression"},
            priority = 102
    )
    public void noRegWithoutName() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration(null, "test@example.com", "12345678", "12345678");

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.nameErrorMessage()),
                "Нет пользовательской ошибки пустого поля name\n");

        kraken.get().baseUrl();
        softAssert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла регистрация пользователя без имени\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без email",
            groups = {"regression"},
            priority = 103
    )
    public void noRegWithoutEmail() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration("Test User", null, "12345678", "12345678");

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.emailErrorMessage()),
                "Нет пользовательской ошибки пустого поля email\n");

        kraken.get().baseUrl();
        softAssert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла регистрация пользователя без email\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без пароля",
            groups = {"regression"},
            priority = 104
    )
    public void noRegWithoutPassword() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration("Test User", "test@example.com", null, "12345678");
        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.passwordErrorMessage()),
                "Нет пользовательской ошибки пустого поля password\n");

        kraken.get().baseUrl();
        softAssert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла регистрация пользователя без пароля\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без подтверждения пароля",
            groups = {"regression"},
            priority = 105
    )
    public void noRegWithoutPasswordConfirmation() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration("Test User", "test@example.com", "12345678", null);

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.passwordConfirmationErrorMessage()),
                "Нет пользовательской ошибки пустого поля password confirmation\n");

        kraken.get().baseUrl();
        softAssert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла регистрация пользователя без подтверждения пароля\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с несовпадающими паролями",
            groups = {"regression"},
            priority = 106
    )
    public void noRegWithWrongPasswordConfirmation() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration("Test User", "test@example.com", "12345678", "12345679");

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.passwordConfirmationErrorMessage()),
                "Нет пользовательской ошибки несовпадения пароля\n");

        kraken.get().baseUrl();
        softAssert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла регистрация пользователя с несовпадающим подтверждёнием пароля\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки повторно зарегистрировать существующего пользователя",
            groups = {"acceptance","regression"},
            priority = 107
    )
    public void noRegWithExistingEmail() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration("Test User", Users.getCredentials("user").getLogin(),
                "12345678", "12345678");

        softAssert.assertTrue(kraken.detect().isUserErrorShown(Elements.Site.AuthModal.emailErrorMessage()),
                "Нет пользовательской ошибки регистрации с уже зарегистрированным email\n");

        kraken.get().baseUrl();
        softAssert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла регистрация пользователя с уже используемым email\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с длинными полями",
            groups = {"regression"},
            priority = 108
    )
    public void noRegWithLongFields() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration(kraken.generate().testUserData(129));

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
        softAssert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла регистрация пользователя с ошибками заполнения формы регистрации длинными полями\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Тест отмены регистрации после заполнения всех полей",
            groups = {"regression"},
            priority = 109
    )
    public void noRegOnCancel() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().openAuthModal();
        kraken.perform().regSequence(kraken.generate().testUserData());
        kraken.perform().closeAuthModal();

        softAssert.assertFalse(kraken.detect().isAuthModalOpen(), "Не закрывается заполненная регистрационная модалка\n");

        kraken.get().baseUrl();

        softAssert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла регистрация пользователя после заполнения всех полей и закрытия модалки\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Регистрация нового пользователя на лендинге",
            groups = {"acceptance","regression"},
            priority = 110
    )
    public void successRegOnLanding() throws Exception {
        kraken.perform().registration();

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Не работает регистрация на лендинге\n");
    }


    @Test(
            description = "Регистрация нового пользователя на витрине магазина",
            groups = {"acceptance", "regression"},
            priority = 111
    )
    public void successRegOnRetailerPage() throws Exception {
        kraken.get().page("metro");

        kraken.perform().registration();

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Не работает регистрация на витрине магазина\n");
    }


    @Test(
            description = "Тест регистрации из адресной модалки феникса",
            groups = {"regression"},
            priority = 112
    )
    public void successRegFromAddressModal() throws Exception, AssertionError {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");

        kraken.shipAddress().openAddressModal();
        kraken.perform().click(Elements.Site.AddressModal.authButton());

        softAssert.assertTrue(kraken.detect().isAuthModalOpen(),
                "\nНе работает переход на авторизацию из адресной модалки");

        kraken.perform().registration();

        softAssert.assertTrue(kraken.detect().isUserAuthorised(),
                "\nНе работает регистрация из адресной модалки феникса");

        softAssert.assertAll();
    }


    @Test(
            description = "Тест регистрации при переходе из корзины в чекаут",
            groups = {"regression"},
            priority = 113
    )
    public void successRegFromCart() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());

        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();

        softAssert.assertTrue(kraken.detect().isAuthModalOpen(),
                "\nНе открывается авторизационная модалка при переходе неавторизованным из корзины в чекаут");

        kraken.perform().registration();

        softAssert.assertTrue(kraken.detect().isOnCheckout(),
                "\nНет автоперехода в чекаут после регистрации из корзины");

        kraken.get().baseUrl();

        softAssert.assertTrue(kraken.detect().isUserAuthorised(),
                "\nНе работает регистрация из корзины");

        softAssert.assertFalse(kraken.detect().isCartEmpty(),
                "\nПропали товары после регистрации из корзины");

        softAssert.assertAll();
    }


    @Test(  description = "Тест успешной регистрации через ВКонтакте",
            groups = {"regression"},
            priority = 114
    )
    public void successRegVK() throws Exception, AssertionError {
        if (kraken.detect().environment("PRODUCTION")) {

            kraken.social().denyAccessVK();
            kraken.perform().reachAdmin();
            kraken.social().deleteUserVK();
            kraken.perform().quickLogout();
            kraken.social().authVK();

            Assert.assertTrue(kraken.detect().isUserAuthorised(),
                    "Не работает регистрация через ВКонтакте\n");
        } else {
            kraken.perform().printMessage("Пропускаем тест успешной регистрации через Вконтакте на STAGING");
        }
    }

}