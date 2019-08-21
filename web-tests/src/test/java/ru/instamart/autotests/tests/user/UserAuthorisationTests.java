package ru.instamart.autotests.tests.user;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Users;
import ru.instamart.autotests.appmanager.ShopHelper;
import ru.instamart.autotests.appmanager.SocialHelper;
import ru.instamart.autotests.models.UserData;
import ru.instamart.autotests.testdata.generate;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.appmanager.ApplicationManager.session;

public class UserAuthorisationTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.perform().quickLogout();
    }

    @BeforeMethod(alwaysRun = true)
    public void quickLogout() {
        kraken.perform().quickLogout();
    }

    @Test(
            description = "Негативный тест попытки авторизации с пустыми реквизитами",
            groups = {"acceptance", "regression"},
            priority = 101
    )
    public void noAuthWithEmptyRequisites() throws AssertionError {
        kraken.get().page("metro");

        kraken.perform().openAuthModal();
        kraken.perform().authSequence("", "");
        kraken.perform().sendForm();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Укажите email")),
                        "Нет пользовательской ошибки пустого поля email\n");

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Укажите пароль")),
                        "Нет пользовательской ошибки пустого поля Пароль\n");

        kraken.get().baseUrl();
        Assert.assertFalse(
                kraken.detect().isUserAuthorised(),
                        "Произошла авторизация с пустыми реквизитами"+"\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки авторизации без email",
            groups = {"regression"},
            priority = 102
    )
    public void noAuthWithoutEmail() throws AssertionError {
        kraken.get().page("metro");

        kraken.perform().openAuthModal();
        kraken.perform().authSequence("", "instamart");
        kraken.perform().sendForm();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Укажите email")),
                        "Нет пользовательской ошибки пустого поля email\n");

        kraken.get().baseUrl();
        Assert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Произошла авторизация без email\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки авторизации без пароля",
            groups = {"regression"},
            priority = 103
    )
    public void noAuthWithoutPassword() throws AssertionError {
        kraken.get().page("metro");

        kraken.perform().openAuthModal();
        kraken.perform().authSequence(Users.superuser().getEmail(), "");
        kraken.perform().sendForm();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Укажите пароль")),
                        "Нет пользовательской ошибки пустого поля Пароль\n");

        kraken.get().baseUrl();
        Assert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Произошла авторизация без пароля"+"\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки авторизации несуществующим юзером",
            groups = {"regression"},
            priority = 104
    )
    public void noAuthWithNonexistingUser() throws AssertionError {
        kraken.get().page("metro");

        kraken.perform().openAuthModal();
        kraken.perform().authSequence("nonexistinguser@example.com", "password");
        kraken.perform().sendForm();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Неверный email или пароль")),
                        "Нет пользовательской ошибки авторизации с неверным email или паролем\n");

        kraken.get().baseUrl();
        Assert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Произошла авторизация несуществующим юзером"+"\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки авторизации с неверным паролем",
            groups = {"acceptance","regression"},
            priority = 105
    )
    public void noAuthWithWrongPassword() throws AssertionError {
        kraken.get().page("metro");

        kraken.perform().openAuthModal();
        kraken.perform().authSequence(Users.superuser().getEmail(), "wrongpassword");
        kraken.perform().sendForm();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Неверный email или пароль")),
                        "Нет пользовательской ошибки авторизации с неверным email или паролем\n");

        kraken.get().baseUrl();
        Assert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Произошла авторизация с неверным паролем"+"\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки авторизовать пользователя с длинными полями",
            groups = {"regression"},
            priority = 106
    )
    public void noAuthWithLongFields() throws AssertionError {
        kraken.get().page("metro");

        kraken.perform().openAuthModal();
        kraken.perform().authSequence(generate.testCredentials("user",129));
        kraken.perform().sendForm();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Неверный email или пароль")),
                        "Нет пользовательской ошибки авторизации с неверным email или паролем\n");

        kraken.get().baseUrl();
        softAssert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Произошла авторизация при наличии ошибок заполнения формы авторизации\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест отмены авторизации после заполнения всех полей",
            groups = {"regression"},
            priority = 107
    )
    public void noAuthOnCancel() throws AssertionError {
        kraken.get().page("metro");

        kraken.perform().openAuthModal();
        kraken.perform().authSequence(Users.superuser());
        kraken.perform().closeAuthModal();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertFalse(
                kraken.detect().isAuthModalOpen(),
                    "Не закрывается заполненная авторизационная модалка\n");

        kraken.get().baseUrl();

        softAssert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Произошла авторизация после заполнения всех полей и закрытия модалки\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест успешной авторизации на лендинге",
            groups = {"smoke","acceptance","regression"},
            priority = 108
    )
    public void successAuthOnLanding() throws AssertionError {
        kraken.perform().quickLogout();

        kraken.perform().loginAs(session.admin);

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "Не работает авторизация на лендинге\n");
    }

    @Test(
            description = "Тест успешной авторизации на витрине",
            groups = {"acceptance","regression"},
            priority = 109
    )
    public void successAuthOnRetailerPage() throws AssertionError {
        skipOn("metro");
        kraken.get().page("metro");

        kraken.perform().loginAs(session.admin);

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "Не работает авторизация на витрине магазина\n");
    }

    @Test(
            description = "Тест авторизации из адресной модалки феникса",
            groups = {"regression"},
            priority = 110
    )
    public void successAuthFromAddressModal() throws AssertionError {
        kraken.get().page("metro");

        ShopHelper.ShippingAddress.openAddressModal();
        kraken.perform().click(Elements.Modals.AddressModal.authButton());

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе работает переход на авторизацию из адресной модалки");

        kraken.perform().loginAs(session.user);

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\nНе работает авторизация из адресной модалки феникса");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест успешной авторизации из корзины",
            groups = {"regression"},
            priority = 111
    )
    public void successAuthFromCart() {
        final UserData testuser = generate.testCredentials("user");

        kraken.perform().registration(testuser);
        kraken.perform().quickLogout();
        kraken.get().page("metro");
        ShopHelper.ShippingAddress.set(Addresses.Moscow.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе открывается авторизационная модалка при переходе неавторизованным из корзины в чекаут");

        kraken.perform().authorisation(testuser);

        softAssert.assertTrue(
                kraken.detect().isOnCheckout(),
                    "\nНет автоперехода в чекаут после авторизации из корзины");

        kraken.get().baseUrl();

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\nНе работает авторизация из корзины");

        softAssert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "\nПропали товары после авторизации из корзины");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест успешной авторизации через ВКонтакте",
            groups = {"regression"},
            priority = 112
    )
    public void successAuthWithVK() throws AssertionError {
        skip(); // TODO включить когда будет тестовый акк VK

        kraken.get().page("metro");
        SocialHelper.Vkontakte.initAuth();

        Assert.assertTrue(
                kraken.detect().isElementPresent(Elements.Social.Vkontakte.emailField()),
                    "Не открывается окно авторизации через Вконтакте\n");

        SocialHelper.Vkontakte.submitAuth();

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "Не работает авторизация через ВКонтакте\n");
    }

    @Test(
            description = "Тест успешной авторизации через Facebook",
            groups = {"regression"},
            priority = 113
    )
    public void successAuthWithFB() throws AssertionError {
        skip(); // TODO включить когда будет тестовый акк VK

        kraken.get().page("metro");
        SocialHelper.Facebook.initAuth();

        Assert.assertTrue(
                kraken.detect().isElementPresent(Elements.Social.Facebook.emailField()),
                    "Не открывается окно авторизации через Facebook\n");

        SocialHelper.Facebook.submitAuth();

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "Не работает авторизация через Facebook\n");
    }

    @Test(
            description = "Тест успешной деавторизации",
            groups = {"acceptance","regression"},
            priority = 114
    )
    public void successLogout() throws AssertionError {
        kraken.get().page("metro");

        kraken.perform().loginAs(session.admin);
        kraken.perform().logout();

        assertPageIsAvailable(); // Assert there is no problems after logout

        kraken.get().page("metro");

        Assert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Не работает деавторизация\n");
    }
}
