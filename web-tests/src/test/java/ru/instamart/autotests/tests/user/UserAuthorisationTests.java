package ru.instamart.autotests.tests.user;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.libs.Addresses;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Tenants;
import ru.instamart.autotests.application.Users;
import ru.instamart.autotests.appmanager.platform.Shop;
import ru.instamart.autotests.appmanager.SocialHelper;
import ru.instamart.autotests.appmanager.platform.User;
import ru.instamart.autotests.appmanager.models.UserData;
import ru.instamart.autotests.testdata.generate;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.appmanager.ApplicationManager.session;

public class UserAuthorisationTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Do.quickLogout();
    }

    @BeforeMethod(alwaysRun = true)
    public void quickLogout() {
        User.Do.quickLogout();
    }

    @Test(
            description = "Негативный тест попытки авторизации с пустыми реквизитами",
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 101
    )
    public void noAuthWithEmptyRequisites() {
        kraken.get().page("metro");

        User.Do.openAuthModal();
        User.Do.authSequence("", "");
        User.Do.sendForm();

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
            groups = {
                    "regression",
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 102
    )
    public void noAuthWithoutEmail() {
        kraken.get().page("metro");

        User.Do.openAuthModal();
        User.Do.authSequence("", "instamart");
        User.Do.sendForm();

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
            groups = {
                    "regression",
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 103
    )
    public void noAuthWithoutPassword() {
        kraken.get().page("metro");

        User.Do.openAuthModal();
        User.Do.authSequence(Users.superuser().getEmail(), "");
        User.Do.sendForm();

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
            groups = {
                    "regression",
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 104
    )
    public void noAuthWithNonexistingUser() {
        kraken.get().page("metro");

        User.Do.openAuthModal();
        User.Do.authSequence("nonexistinguser@example.com", "password");
        User.Do.sendForm();

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
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 105
    )
    public void noAuthWithWrongPassword() {
        kraken.get().page("metro");

        User.Do.openAuthModal();
        User.Do.authSequence(Users.superuser().getEmail(), "wrongpassword");
        User.Do.sendForm();

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
            groups = {
                    "regression",
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 106
    )
    public void noAuthWithLongFields() {
        kraken.get().page("metro");

        User.Do.openAuthModal();
        User.Do.authSequence(generate.testCredentials("user",129));
        User.Do.sendForm();

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
            groups = {
                    "regression",
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 107
    )
    public void noAuthOnCancel() {
        kraken.get().page("metro");

        User.Do.openAuthModal();
        User.Do.authSequence(Users.superuser());
        User.Do.closeAuthModal();

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
            description = "Тест успешной авторизации на лендинге Инстамарта",
            groups = {"smoke","acceptance","regression"},
            priority = 108
    )
    public void successAuthOnLanding() {
        User.Do.quickLogout();

        User.Do.loginAs(session.admin);

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "Не работает авторизация на лендинге\n");
    }

    @Test(
            description = "Тест успешной авторизации на витрине",
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 109
    )
    public void successAuthOnMainPage() {
        skipTestOn(Tenants.metro());
        kraken.get().page("metro");

        User.Do.loginAs(session.admin);

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "Не работает авторизация на витрине магазина\n");
    }

    @Test(
            description = "Тест авторизации из адресной модалки феникса",
            groups = {
                    "regression",
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 110
    )
    public void successAuthFromAddressModal() {
        kraken.get().page("metro");

        Shop.ShippingAddress.openAddressModal();
        kraken.perform().click(Elements.Modals.AddressModal.authButton());

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе работает переход на авторизацию из адресной модалки");

        User.Do.loginAs(session.user);

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\nНе работает авторизация из адресной модалки феникса");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест успешной авторизации из корзины",
            groups = {
                    "regression",
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 111
    )
    public void successAuthFromCart() {
        final UserData testuser = generate.testCredentials("user");

        User.Do.registration(testuser);
        User.Do.quickLogout();
        kraken.get().page("metro");
        Shop.ShippingAddress.set(Addresses.Moscow.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе открывается авторизационная модалка при переходе неавторизованным из корзины в чекаут");

        User.Do.login(testuser);

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
            groups = {
                    "regression",
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 112
    )
    public void successAuthWithVK() {
        skipTest(); // TODO включить когда будет тестовый акк VK

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
            groups = {
                    "regression",
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 113
    )
    public void successAuthWithFB() {
        skipTest(); // TODO включить когда будет тестовый акк VK

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
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 114
    )
    public void successLogout() {
        kraken.get().page("metro");

        User.Do.loginAs(session.admin);
        User.Do.logout();

        assertPageIsAvailable(); // Assert there is no problems after logout

        kraken.get().page("metro");

        Assert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Не работает деавторизация\n");
    }
}
