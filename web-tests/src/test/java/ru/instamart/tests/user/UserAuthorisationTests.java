package ru.instamart.tests.user;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.application.Users;
import ru.instamart.application.models.UserData;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.lib.Addresses;
import ru.instamart.application.Elements;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.testdata.generate;
import ru.instamart.tests.TestBase;

import static ru.instamart.application.AppManager.session;

public class UserAuthorisationTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
    }

    @BeforeMethod(alwaysRun = true)
    public void quickLogout() {
        User.Logout.quickly();
    }

    @Test(
            description = "Негативный тест попытки авторизации с пустыми реквизитами",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 111
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
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 112
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
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 113
    )
    public void noAuthWithoutPassword() {
        kraken.get().page("metro");

        Shop.AuthModal.open();
        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm(Users.superuser().getLogin(), "");
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
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 114
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
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 115
    )
    public void noAuthWithWrongPassword() {
        kraken.get().page("metro");

        Shop.AuthModal.open();
        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm(Users.superuser().getLogin(), "wrongpassword");
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
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 116
    )
    public void noAuthWithLongFields() {
        SoftAssert softAssert = new SoftAssert();
        UserData testUser = generate.testCredentials("user",129);
        kraken.get().page("metro");

        Shop.AuthModal.open();
        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm(testUser.getLogin(), testUser.getPassword());
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
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 117
    )
    public void noAuthOnModalClose() {
        kraken.get().page("metro");

        Shop.AuthModal.open();
        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm(Users.superuser().getLogin(), Users.superuser().getPassword());
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
            description = "Тест успешной авторизации на витрине",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 119
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
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 120
    )
    public void successAuthFromAddressModal() {
        kraken.get().page("metro");

        Shop.ShippingAddressModal.open();
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
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 121
    )
    public void successAuthFromCart() {
        final UserData testuser = generate.testCredentials("user");

        User.Do.registration(testuser);
        User.Logout.quickly();
        kraken.get().page("metro");
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе открывается авторизационная модалка при переходе неавторизованным из корзины в чекаут");

        User.Auth.withEmail(testuser);

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
            priority = 122,
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAuthWithVkontakte() {
        kraken.get().page("metro");

        User.Auth.withVkontakte(Users.vkontakte());

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    failMessage("Не работает авторизация через ВКонтакте"));
    }

    @Test(
            description = "Тест успешной авторизации через Facebook",
            priority = 123,
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAuthWithFacebook() {
        kraken.get().page("metro");

        User.Auth.withFacebook(Users.facebook());

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    failMessage("Не работает авторизация через Facebook"));
    }

    @Test( enabled = false, // надо придумать как избежать в дальнейших тестах блокирующего окошка Mail.ru
            description = "Тест успешной авторизации через MailRu",
            priority = 124,
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAuthWithMailRu() {
        kraken.get().page("metro");

        User.Auth.withMailRu(Users.mailRu());

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    failMessage("Не работает авторизация через MailRu"));
    }


    @Test(
            description = "Тест успешной авторизации через Sber ID",
            priority = 125,
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAuthWithSberID() {
        kraken.get().page("metro");

        User.Auth.withSberID(Users.sberId());

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    failMessage("Не работает авторизация через Sber ID"));
    }
}

