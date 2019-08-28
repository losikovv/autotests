package ru.instamart.tests.user;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.application.lib.Users;
import ru.instamart.application.models.UserData;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.platform.helpers.SocialHelper;
import ru.instamart.application.lib.Addresses;
import ru.instamart.application.lib.Elements;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.testdata.generate;
import ru.instamart.tests.TestBase;

import static ru.instamart.application.AppManager.session;

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

        Shop.AuthModal.open();
        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm("", "");
        Shop.AuthModal.submit();

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

        Shop.AuthModal.open();
        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm("", "instamart");
        Shop.AuthModal.submit();

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

        Shop.AuthModal.open();
        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm(Users.superuser().getEmail(), "");
        Shop.AuthModal.submit();

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

        Shop.AuthModal.open();
        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm("nonexistinguser@example.com", "password");
        Shop.AuthModal.submit();

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

        Shop.AuthModal.open();
        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm(Users.superuser().getEmail(), "wrongpassword");
        Shop.AuthModal.submit();

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
        SoftAssert softAssert = new SoftAssert();
        UserData testUser = generate.testCredentials("user",129);
        kraken.get().page("metro");

        Shop.AuthModal.open();
        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm(testUser.getEmail(), testUser.getPassword());
        Shop.AuthModal.submit();

        softAssert.assertTrue(
                kraken.detect().isElementPresent(Elements.Modals.AuthModal.errorMessage("Неверный email или пароль")),
                    failMessage("Нет пользовательской ошибки авторизации с неверным email или паролем"));

        kraken.get().baseUrl();

        softAssert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    failMessage("Произошла авторизация при наличии ошибок заполнения формы авторизации"));

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
    public void noAuthOnModalClose() {
        kraken.get().page("metro");

        Shop.AuthModal.open();
        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm(Users.superuser().getEmail(), Users.superuser().getPassword());
        Shop.AuthModal.close();

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
            description = "Тест успешной авторизации через Facebook",
            groups = {
                    "regression",
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 113
    )
    public void successAuthWithMailRu() {
        skipTest(); // TODO включить когда будет тестовый акк Mail

        kraken.get().page("metro");
        // todo SocialHelper.MailRu.initAuth();

        // todo SocialHelper.MailRu.submitAuth();

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "Не работает авторизация через MailRu\n");
    }

    @Test(
            description = "Тест успешной деавторизации",
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 115
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
