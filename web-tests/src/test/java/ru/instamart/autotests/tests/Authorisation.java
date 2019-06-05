package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.application.Users;
import ru.instamart.autotests.appmanager.ShopHelper;
import ru.instamart.autotests.models.UserData;
import ru.instamart.autotests.testdata.generate;

import static ru.instamart.autotests.appmanager.ApplicationManager.session;


// Тесты авторизации


public class Authorisation extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void quickLogout() {
        kraken.perform().quickLogout();
    }

    @Test(
            description = "Негативный тест попытки авторизации с пустыми реквизитами",
            groups = {"acceptance", "regression"},
            priority = 101
    )
    public void noAuthWithEmptyRequisites() throws Exception, AssertionError {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().openAuthModal();
        kraken.perform().authSequence("", "");
        kraken.perform().sendForm();

        softAssert.assertTrue(kraken.detect().isElementPresent(
                Elements.Site.AuthModal.errorMessage("Укажите email")),
                "Нет пользовательской ошибки пустого поля email\n");

        softAssert.assertTrue(kraken.detect().isElementPresent(
                Elements.Site.AuthModal.errorMessage("Укажите пароль")),
                "Нет пользовательской ошибки пустого поля Пароль\n");

        kraken.get().baseUrl();
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла авторизация с пустыми реквизитами"+"\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки авторизации без email",
            groups = {"regression"},
            priority = 102
    )
    public void noAuthWithoutEmail() throws Exception, AssertionError {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().openAuthModal();
        kraken.perform().authSequence("", "instamart");
        kraken.perform().sendForm();

        softAssert.assertTrue(kraken.detect().isElementPresent(
                Elements.Site.AuthModal.errorMessage("Укажите email")),
                "Нет пользовательской ошибки пустого поля email\n");

        kraken.get().baseUrl();
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла авторизация без email\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки авторизации без пароля",
            groups = {"regression"},
            priority = 103
    )
    public void noAuthWithoutPassword() throws Exception, AssertionError {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().openAuthModal();
        kraken.perform().authSequence(Users.superuser().getEmail(), "");
        kraken.perform().sendForm();

        softAssert.assertTrue(kraken.detect().isElementPresent(
                Elements.Site.AuthModal.errorMessage("Укажите пароль")),
                "Нет пользовательской ошибки пустого поля Пароль\n");

        kraken.get().baseUrl();
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла авторизация без пароля"+"\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки авторизации несуществующим юзером",
            groups = {"regression"},
            priority = 104
    )
    public void noAuthWithNonexistingUser() throws Exception, AssertionError {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().openAuthModal();
        kraken.perform().authSequence("nonexistinguser@example.com", "password");
        kraken.perform().sendForm();

        softAssert.assertTrue(kraken.detect().isElementPresent(
                Elements.Site.AuthModal.errorMessage("Неверный email или пароль")),
                "Нет пользовательской ошибки авторизации с неверным email или паролем\n");

        kraken.get().baseUrl();
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла авторизация несуществующим юзером"+"\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки авторизации с неверным паролем",
            groups = {"acceptance","regression"},
            priority = 105
    )
    public void noAuthWithWrongPassword() throws Exception, AssertionError {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().openAuthModal();
        kraken.perform().authSequence(Users.superuser().getEmail(), "wrongpassword");
        kraken.perform().sendForm();

        softAssert.assertTrue(kraken.detect().isElementPresent(
                Elements.Site.AuthModal.errorMessage("Неверный email или пароль")),
                "Нет пользовательской ошибки авторизации с неверным email или паролем\n");

        kraken.get().baseUrl();
        Assert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла авторизация с неверным паролем"+"\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки авторизовать пользователя с длинными полями",
            groups = {"regression"},
            priority = 106
    )
    public void noAuthWithLongFields() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().openAuthModal();
        kraken.perform().authSequence(generate.testCredentials("user",129));
        kraken.perform().sendForm();

        softAssert.assertTrue(kraken.detect().isElementPresent(
                Elements.Site.AuthModal.errorMessage("Неверный email или пароль")),
                "Нет пользовательской ошибки авторизации с неверным email или паролем\n");

        kraken.get().baseUrl();
        softAssert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла авторизация при наличии ошибок заполнения формы авторизации\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест отмены авторизации после заполнения всех полей",
            groups = {"regression"},
            priority = 107
    )
    public void noAuthOnCancel() throws Exception, AssertionError {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().openAuthModal();
        kraken.perform().authSequence(Users.superuser());
        kraken.perform().closeAuthModal();

        softAssert.assertFalse(kraken.detect().isAuthModalOpen(), "Не закрывается заполненная авторизационная модалка\n");

        kraken.get().baseUrl();

        softAssert.assertFalse(kraken.detect().isUserAuthorised(),
                "Произошла авторизация после заполнения всех полей и закрытия модалки\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест успешной авторизации на лендинге",
            groups = {"smoke","acceptance","regression"},
            priority = 108
    )
    public void successAuthOnLanding() throws Exception, AssertionError {
        kraken.perform().loginAs(session.admin);

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Не работает авторизация на лендинге\n");
    }

    @Test(
            description = "Тест успешной авторизации на витрине",
            groups = {"acceptance","regression"},
            priority = 109
    )
    public void successAuthOnRetailerPage() throws Exception, AssertionError {
        skipOn("metro");
        kraken.get().page("vkusvill");
        kraken.perform().loginAs(session.admin);

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Не работает авторизация на витрине магазина\n");
    }

    @Test(
            description = "Тест авторизации из адресной модалки феникса",
            groups = {"regression"},
            priority = 110
    )
    public void successAuthFromAddressModal() throws Exception, AssertionError {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");

        kraken.shipAddress().openAddressModal();
        kraken.perform().click(Elements.Site.AddressModal.authButton());

        softAssert.assertTrue(kraken.detect().isAuthModalOpen(),
                "\nНе работает переход на авторизацию из адресной модалки");

        kraken.perform().loginAs(session.user);

        softAssert.assertTrue(kraken.detect().isUserAuthorised(),
                "\nНе работает авторизация из адресной модалки феникса");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест успешной авторизации из корзины",
            groups = {"regression"},
            priority = 111
    )
    public void successAuthFromCart() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        final UserData testuser = generate.testCredentials("user");
        kraken.perform().registration(testuser);
        kraken.perform().quickLogout();
        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();

        softAssert.assertTrue(kraken.detect().isAuthModalOpen(),
                "\nНе открывается авторизационная модалка при переходе неавторизованным из корзины в чекаут");

        kraken.perform().authorisation(testuser);

        softAssert.assertTrue(kraken.detect().isOnCheckout(),
                "\nНет автоперехода в чекаут после авторизации из корзины");

        kraken.get().baseUrl();

        softAssert.assertTrue(kraken.detect().isUserAuthorised(),
                "\nНе работает авторизация из корзины");

        softAssert.assertFalse(kraken.detect().isCartEmpty(),
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

        kraken.social().initAuthVK();

        Assert.assertTrue(kraken.detect().isElementPresent(Elements.Social.Vkontakte.emailField()),
                "Не открывается окно авторизации через Вконтакте\n");

        kraken.social().submitAuthVK();

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Не работает авторизация через ВКонтакте\n");
    }

    @Test(
            description = "Тест успешной авторизации через Facebook",
            groups = {"regression"},
            priority = 113
    )
    public void successAuthWithFB() throws AssertionError {
        skip(); // TODO включить когда будет тестовый акк VK

        kraken.social().initAuthFB();

        Assert.assertTrue(kraken.detect().isElementPresent(Elements.Social.Facebook.emailField()),
                "Не открывается окно авторизации через Facebook\n");

        kraken.social().submitAuthFB();

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Не работает авторизация через Facebook\n");
    }

    @Test(
            description = "Тест доступности страниц профиля пользователя",
            groups = {"smoke","acceptance","regression"},
            priority = 114
    )
    public void successCheckProfilePages() throws Exception, AssertionError {
        kraken.get().baseUrl();
        kraken.perform().loginAs(session.admin);

        // TODO переделать на assertPagesAvailable(Pages.Site.Profile.*)
        assertPageIsAvailable(Pages.Site.Profile.edit());
        assertPageIsAvailable(Pages.Site.Profile.favorites());
        assertPageIsAvailable(Pages.Site.Profile.orders());
        assertPageIsAvailable(Pages.Site.Profile.addresses());
    }

    @Test(
            description = "Тест работы с меню профиля",
            groups = {"smoke","acceptance","regression"},
            priority = 115
    )
    public void successOperateProfileMenu() throws Exception, AssertionError {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().baseUrl();
        kraken.perform().loginAs(session.admin);

        ShopHelper.AccountMenu.open();

        softAssert.assertTrue(
                kraken.detect().isAccountMenuOpen(),
                    "Не открывается всплывающее меню профиля\n");

        ShopHelper.AccountMenu.close();

        softAssert.assertFalse(
                kraken.detect().isAccountMenuOpen(),
                    "Не закрывается всплывающее меню профиля\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест валидации элементов в меню профиля",
            groups = {"smoke","acceptance","regression"},
            priority = 117
    )
    public void successValidateProfileMenu() throws Exception, AssertionError {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().baseUrl();
        kraken.perform().loginAs(session.admin);

        ShopHelper.AccountMenu.open();

        // Проверяем наличие элементов
        kraken.check().elementPresence(Elements.Site.AccountMenu.popup());
        kraken.check().elementPresence(Elements.Site.AccountMenu.header());
        kraken.check().elementPresence(Elements.Site.AccountMenu.profileButton());
        kraken.check().elementPresence(Elements.Site.AccountMenu.ordersHistoryButton());
        kraken.check().elementPresence(Elements.Site.AccountMenu.termsButton());
        kraken.check().elementPresence(Elements.Site.AccountMenu.logoutButton());
        kraken.check().elementPresence(Elements.Site.AccountMenu.deliveryButton());
        kraken.check().elementPresence(Elements.Site.AccountMenu.paymentButton());
        kraken.check().elementPresence(Elements.Site.AccountMenu.faqButton());
        kraken.check().elementPresence(Elements.Site.AccountMenu.contactsButton());

        // Валидируем ссылки
        validateTransition(Elements.Site.AccountMenu.profileButton());

        ShopHelper.AccountMenu.open();
        validateTransition(Elements.Site.AccountMenu.ordersHistoryButton());

        ShopHelper.AccountMenu.open();
        validateTransition(Elements.Site.AccountMenu.termsButton());

        ShopHelper.AccountMenu.open();
        kraken.perform().click(Elements.Site.AccountMenu.deliveryButton());
        softAssert.assertTrue(
                kraken.detect().isDeliveryModalOpen(),
                    "Не открывается модалка \"Доставка\" из всплывающего меню \"Профиль\"\n");
        kraken.perform().refresh();

        ShopHelper.AccountMenu.open();
        kraken.perform().click(Elements.Site.AccountMenu.paymentButton());
        softAssert.assertTrue(
                kraken.detect().isPaymentModalOpen(),
                    "Не открывается модалка \"Оплата\" из всплывающего меню \"Профиль\"\n");
        kraken.perform().refresh();

        ShopHelper.AccountMenu.open();
        validateTransition(Elements.Site.AccountMenu.faqButton());


        ShopHelper.AccountMenu.open();
        validateTransition(Elements.Site.AccountMenu.contactsButton());

        softAssert.assertAll();
    }

    @Test(
            description = "Тест успешной деавторизации",
            groups = {"acceptance","regression"},
            priority = 118
    )
    public void successLogout() throws Exception, AssertionError {
        kraken.perform().loginAs(session.admin);

        kraken.perform().logout();

        assertPageIsAvailable(); // Assert there is no problems after logout

        kraken.get().page("metro");

        Assert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Не работает деавторизация\n");
    }
}
