package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Users;
import ru.instamart.autotests.appmanager.ShopHelper;
import ru.instamart.autotests.models.UserData;
import ru.instamart.autotests.testdata.generate;


// Тесты регистрации пользователя


public class Registration extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void quickLogout() throws Exception {
        kraken.perform().quickLogout();
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с пустыми реквизитами",
            groups = {"acceptance","regression"},
            priority = 201
    )
    public void noRegWithEmptyRequisites() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration(null, null, null, null);

        softAssert.assertTrue(kraken.detect().isElementPresent(
                Elements.Site.AuthModal.errorMessage("Укажите имя и фамилию")),
                "Нет пользовательской ошибки пустого поля name\n");

        softAssert.assertTrue(kraken.detect().isElementPresent(
                Elements.Site.AuthModal.errorMessage("Укажите пароль")),
                "Нет пользовательской ошибки пустого поля email\n");

        softAssert.assertTrue(kraken.detect().isElementPresent(
                Elements.Site.AuthModal.errorMessage("Укажите пароль")),
                "Нет пользовательской ошибки пустого поля password\n");

        softAssert.assertTrue(kraken.detect().isElementPresent(
                Elements.Site.AuthModal.errorMessage("Подтвердите пароль")),
                "Нет пользовательской ошибки пустого поля password confirmation\n");

        kraken.get().baseUrl();
        softAssert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла регистрация пользователя с пустыми реквизитами\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без имени",
            groups = {"regression"},
            priority = 202
    )
    public void noRegWithoutName() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration(null, "test@example.com", "12345678", "12345678");

        softAssert.assertTrue(kraken.detect().isElementPresent(
                Elements.Site.AuthModal.errorMessage("Укажите имя и фамилию")),
                "Нет пользовательской ошибки пустого поля name\n");

        kraken.get().baseUrl();
        softAssert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла регистрация пользователя без имени\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без email",
            groups = {"regression"},
            priority = 203
    )
    public void noRegWithoutEmail() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration("Test User", null, "12345678", "12345678");

        softAssert.assertTrue(kraken.detect().isElementPresent(
                Elements.Site.AuthModal.errorMessage("Укажите email")),
                "Нет пользовательской ошибки пустого поля email\n");

        kraken.get().baseUrl();
        softAssert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла регистрация пользователя без email\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без пароля",
            groups = {"regression"},
            priority = 204
    )
    public void noRegWithoutPassword() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration("Test User", "test@example.com", null, "12345678");
        softAssert.assertTrue(kraken.detect().isElementPresent(
                Elements.Site.AuthModal.errorMessage("Укажите пароль")),
                "Нет пользовательской ошибки пустого поля password\n");

        kraken.get().baseUrl();
        softAssert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла регистрация пользователя без пароля\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без подтверждения пароля",
            groups = {"regression"},
            priority = 205
    )
    public void noRegWithoutPasswordConfirmation() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration("Test User", "test@example.com", "12345678", null);

        softAssert.assertTrue(kraken.detect().isElementPresent(
                Elements.Site.AuthModal.errorMessage("Подтвердите пароль\n")),
                "Нет пользовательской ошибки пустого поля password confirmation\n");

        kraken.get().baseUrl();
        softAssert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла регистрация пользователя без подтверждения пароля\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с несовпадающими паролями",
            groups = {"regression"},
            priority = 206
    )
    public void noRegWithWrongPasswordConfirmation() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration("Test User", "test@example.com", "12345678", "12345679");

        softAssert.assertTrue(kraken.detect().isElementPresent(
                Elements.Site.AuthModal.errorMessage("Введенные пароли должны совпадать\n\n")),
                "Нет пользовательской ошибки несовпадения пароля\n");

        kraken.get().baseUrl();
        softAssert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла регистрация пользователя с несовпадающим подтверждёнием пароля\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки повторно зарегистрировать существующего пользователя",
            groups = {"acceptance","regression"},
            priority = 207
    )
    public void noRegWithExistingEmail() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration("Test User", Users.superuser().getEmail(),
                "12345678", "12345678");

        softAssert.assertTrue(kraken.detect().isElementPresent(
                Elements.Site.AuthModal.errorMessage("Этот email уже зарегистрирован")),
                "Нет пользовательской ошибки регистрации с уже зарегистрированным email\n");

        kraken.get().baseUrl();
        softAssert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла регистрация пользователя с уже используемым email\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с длинными полями",
            groups = {"regression"},
            priority = 208
    )
    public void noRegWithLongFields() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration(generate.testCredentials("user", 100));

        softAssert.assertTrue(kraken.detect().isElementPresent(
                Elements.Site.AuthModal.errorMessage("todo\n\n")),
                "Нет пользовательской ошибки превышения длины поля name\n");

        softAssert.assertTrue(kraken.detect().isElementPresent(
                Elements.Site.AuthModal.errorMessage("todo\n\n")),
                "Нет пользовательской ошибки превышения длины поля email\n");

        softAssert.assertTrue(kraken.detect().isElementPresent(
                Elements.Site.AuthModal.errorMessage("todo\n\n")),
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
            priority = 209
    )
    public void noRegOnCancel() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().openAuthModal();
        kraken.perform().regSequence(generate.testCredentials("user"));
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
            priority = 210
    )
    public void successRegOnLanding() throws Exception {
        kraken.perform().registration();

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Не работает регистрация на лендинге\n");
    }


    @Test(
            description = "Регистрация нового пользователя на витрине магазина",
            groups = {"acceptance", "regression"},
            priority = 211
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
            priority = 212
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
            priority = 213
    )
    public void successRegFromCart() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();

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


    @Test(
            description = "Тест успешной регистрации через ВКонтакте",
            groups = {"regression"},
            priority = 214
    )
    public void successRegWithVK() throws Exception, AssertionError {
        skip(); // TODO включить когда будет тестовый акк VK

        kraken.social().denyAccessVK();
        kraken.social().deleteUserVK();
        kraken.perform().quickLogout();

        kraken.social().initAuthVK();

        Assert.assertTrue(kraken.detect().isElementPresent(Elements.Social.Vkontakte.emailField()),
                "Не открывается окно регистрации через Вконтакте");

        kraken.social().submitAuthVK();

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Не работает регистрация через ВКонтакте\n");
    }

    @Test(
            description = "Тест успешной регистрации через Facebook",
            groups = {"regression"},
            priority = 215
    )
    public void successRegWithFB() throws Exception, AssertionError {
        skip(); // TODO включить когда будет тестовый акк VK

        kraken.social().denyAccessFB();
        kraken.social().deleteUserFB();
        kraken.perform().quickLogout();

        kraken.social().initAuthFB();

        Assert.assertTrue(kraken.detect().isElementPresent(Elements.Social.Facebook.emailField()),
                "Не открывается окно регистрации через Facebook\n");

        kraken.social().submitAuthFB();

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Не работает регистрация через Facebook\n");
    }

    /* ТЕСТ устарел, чекбоса больше нет
    @Test(
            description = "Негативный тест регистрации без проставленной галки на обработку данных",
            groups = {"acceptance", "regression"},
            priority = 216
    )
    public void noRegWithoutCheckboxInfo() throws Exception {
        kraken.perform().openAuthModal();
        kraken.perform().regSequence(generate.testCredentials("user"), false );
        kraken.perform().click(Elements.Site.AuthModal.submitButton());

        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Работает регистрация без согласия на обработку перс. данных");
    }
    */
}
