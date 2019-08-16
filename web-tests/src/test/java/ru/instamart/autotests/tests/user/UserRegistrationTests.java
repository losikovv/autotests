package ru.instamart.autotests.tests.user;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Users;
import ru.instamart.autotests.appmanager.ShopHelper;
import ru.instamart.autotests.appmanager.SocialHelper;
import ru.instamart.autotests.testdata.generate;
import ru.instamart.autotests.tests.TestBase;

public class UserRegistrationTests extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void quickLogout() {
        kraken.perform().quickLogout();
    }

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с пустыми реквизитами",
            groups = {"acceptance","regression"},
            priority = 201
    )
    public void noRegWithEmptyRequisites() {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration(
                null,
                null,
                null,
                null
        );

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Укажите имя и фамилию")),
                        "Нет пользовательской ошибки пустого поля name\n");

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Укажите пароль")),
                        "Нет пользовательской ошибки пустого поля email\n");

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Укажите пароль")),
                        "Нет пользовательской ошибки пустого поля password\n");

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Подтвердите пароль")),
                        "Нет пользовательской ошибки пустого поля password confirmation\n");

        kraken.get().baseUrl();

        softAssert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Произошла регистрация пользователя с пустыми реквизитами\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без имени",
            groups = {"regression"},
            priority = 202
    )
    public void noRegWithoutName() {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration(
                null,
                "test@example.com",
                "12345678",
                "12345678"
        );

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Укажите имя и фамилию")),
                        "Нет пользовательской ошибки пустого поля name\n");

        kraken.get().baseUrl();

        softAssert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Произошла регистрация пользователя без имени\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без email",
            groups = {"regression"},
            priority = 203
    )
    public void noRegWithoutEmail() {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration(
                "Test User",
                null,
                "12345678",
                "12345678"
        );

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Укажите email")),
                        "Нет пользовательской ошибки пустого поля email\n");

        kraken.get().baseUrl();

        softAssert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Произошла регистрация пользователя без email\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без пароля",
            groups = {"regression"},
            priority = 204
    )
    public void noRegWithoutPassword() {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration("Test User", "test@example.com", null, "12345678");

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Укажите пароль")),
                        "Нет пользовательской ошибки пустого поля password\n");

        kraken.get().baseUrl();

        softAssert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Произошла регистрация пользователя без пароля\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без подтверждения пароля",
            groups = {"regression"},
            priority = 205
    )
    public void noRegWithoutPasswordConfirmation() {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration(
                "Test User",
                "test@example.com",
                "12345678",
                null
        );

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Подтвердите пароль\n")),
                        "Нет пользовательской ошибки пустого поля password confirmation\n");

        kraken.get().baseUrl();

        softAssert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Произошла регистрация пользователя без подтверждения пароля\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с несовпадающими паролями",
            groups = {"regression"},
            priority = 206
    )
    public void noRegWithWrongPasswordConfirmation() {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration(
                "Test User",
                "test@example.com",
                "12345678",
                "12345679"
        );

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Введенные пароли должны совпадать\n\n")),
                        "Нет пользовательской ошибки несовпадения пароля\n");

        kraken.get().baseUrl();

        softAssert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Произошла регистрация пользователя с несовпадающим подтверждёнием пароля\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки повторно зарегистрировать существующего пользователя",
            groups = {"acceptance","regression"},
            priority = 207
    )
    public void noRegWithExistingEmail() {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration("Test User", Users.superuser().getEmail(),
                "12345678", "12345678");

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Этот email уже зарегистрирован")),
                        "Нет пользовательской ошибки регистрации с уже зарегистрированным email\n");

        kraken.get().baseUrl();

        softAssert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Произошла регистрация пользователя с уже используемым email\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с длинными полями",
            groups = {"regression"},
            priority = 208
    )
    public void noRegWithLongFields() {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().registration(generate.testCredentials("user", 100));

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("todo\n\n")),
                        "Нет пользовательской ошибки превышения длины поля name\n");

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("todo\n\n")),
                        "Нет пользовательской ошибки превышения длины поля email\n");

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("todo\n\n")),
                        "Нет пользовательской ошибки превышения длины поля password\n");

        // Решили не выводить ошибку превышения длины поля password confirmation

        kraken.get().baseUrl();
        softAssert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Произошла регистрация пользователя с ошибками заполнения формы регистрации длинными полями\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест отмены регистрации после заполнения всех полей",
            groups = {"regression"},
            priority = 209
    )
    public void noRegOnCancel() {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().openAuthModal();
        kraken.perform().regSequence(generate.testCredentials("user"));
        kraken.perform().closeAuthModal();

        softAssert.assertFalse(
                kraken.detect().isAuthModalOpen(),
                    "Не закрывается заполненная регистрационная модалка\n");

        kraken.get().baseUrl();

        softAssert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Произошла регистрация пользователя после заполнения всех полей и закрытия модалки\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Регистрация нового пользователя на лендинге",
            groups = {"acceptance","regression"},
            priority = 210
    )
    public void successRegOnLanding() {
        kraken.perform().registration();

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\n\n> Не работает регистрация на лендинге > ");
    }

    @Test(
            description = "Регистрация нового пользователя на витрине магазина",
            groups = {"acceptance", "regression"},
            priority = 211
    )
    public void successRegOnRetailerPage() {
        kraken.get().page("metro");

        kraken.perform().registration();

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "Не работает регистрация на витрине магазина\n");
    }

    @Test(
            description = "Тест регистрации из адресной модалки феникса",
            groups = {"regression"},
            priority = 212
    )
    public void successRegFromAddressModal() throws AssertionError {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");

        kraken.shipAddress().openAddressModal();
        kraken.perform().click(Elements.Modals.AddressModal.authButton());

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе работает переход на авторизацию из адресной модалки");

        kraken.perform().registration();

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\nНе работает регистрация из адресной модалки феникса");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест регистрации при переходе из корзины в чекаут",
            groups = {"regression"},
            priority = 213
    )
    public void successRegFromCart() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе открывается авторизационная модалка при переходе неавторизованным из корзины в чекаут");

        kraken.perform().registration();

        softAssert.assertTrue(
                kraken.detect().isOnCheckout(),
                    "\nНет автоперехода в чекаут после регистрации из корзины");

        kraken.get().baseUrl();

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\nНе работает регистрация из корзины");

        softAssert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "\nПропали товары после регистрации из корзины");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест успешной регистрации через ВКонтакте",
            groups = {"regression"},
            priority = 214
    )
    public void successRegWithVK() throws AssertionError {
        skip(); // TODO включить когда будет тестовый акк VK

        SocialHelper.Vkontakte.denyAccess();
        SocialHelper.Vkontakte.deleteUser();
        kraken.perform().quickLogout();

        SocialHelper.Vkontakte.initAuth();

        Assert.assertTrue(
                kraken.detect().isElementPresent(Elements.Social.Vkontakte.emailField()),
                    "Не открывается окно регистрации через Вконтакте");

        SocialHelper.Vkontakte.submitAuth();

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "Не работает регистрация через ВКонтакте\n");
    }

    @Test(
            description = "Тест успешной регистрации через Facebook",
            groups = {"regression"},
            priority = 215
    )
    public void successRegWithFB() throws AssertionError {
        skip(); // TODO включить когда будет тестовый акк VK

        SocialHelper.Facebook.denyAccess();
        SocialHelper.Facebook.deleteUser();
        kraken.perform().quickLogout();

        SocialHelper.Facebook.initAuth();

        Assert.assertTrue(
                kraken.detect().isElementPresent(Elements.Social.Facebook.emailField()),
                    "Не открывается окно регистрации через Facebook\n");

        SocialHelper.Facebook.submitAuth();

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "Не работает регистрация через Facebook\n");
    }

    @Test(
            description = "Тест успешной регистрации без проставленной галки согласия на почтовую рассылку",
            groups = {"acceptance", "regression"},
            priority = 216
    )
    public void successRegWithoutMailingCheckbox() {
        kraken.perform().openAuthModal();
        kraken.perform().regSequence(generate.testCredentials("user"), false );
        kraken.perform().click(Elements.Modals.AuthModal.submitButton());

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "Не работает регистрация без согласия на получение почтовой рассылки\n");
    }

    @Test(
            description = "Тест успешной регистрации с заново проставленной галкой согласия на почтовую рассылку",
            groups = {"regression"},
            priority = 217
    )
    public void successRegWithMailingCheckbox() {
        kraken.perform().openAuthModal();
        kraken.perform().regSequence(generate.testCredentials("user"), false );
        kraken.perform().click(Elements.Modals.AuthModal.agreementCheckbox());
        kraken.perform().click(Elements.Modals.AuthModal.submitButton());

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "Не работает регистрация с согласием на получение почтовой рассылки\n");
    }
}
