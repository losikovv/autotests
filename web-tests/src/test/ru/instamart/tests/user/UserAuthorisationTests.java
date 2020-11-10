package ru.instamart.tests.user;

import instamart.core.settings.Config;
import instamart.core.testdata.Users;
import instamart.core.testdata.ui.generate;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.checkpoints.users.UsersAuthorizationCheckpoints;
import instamart.ui.common.lib.Addresses;
import instamart.ui.common.pagesdata.UserData;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.TestBase;

import static instamart.core.common.AppManager.session;

public class UserAuthorisationTests extends TestBase {
//    Config config = new Config();
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    UsersAuthorizationCheckpoints authChecks = new UsersAuthorizationCheckpoints();

    @BeforeClass(alwaysRun = true,
            description ="Проверяем залогинен ли пользователь до старта скоупа тестов, если да то завершаем сессию")
    public void setup() {
        User.Logout.quickly();
    }

    @BeforeMethod(alwaysRun = true,
            description ="Проверяем залогинен ли пользователь, если да то завершаем сессию")
    public void quickLogout() {
        User.Logout.quickly();
    }

        //        Allure.getLifecycle().updateTestCase((g) -> {
//            g.setStatusDetails( g.getStatusDetails().setMessage("blablablabl"));
//        }); пусть полежит тут, это фунция обновления тест кейса, в будущем может пригодится

    @Description("Тест пытается авторизоваться с пустыми реквизитами")
    @Test(
            description = "Негативный тест попытки авторизации с пустыми реквизитами",
            groups = {
                    "metro-acceptance", "metro-regression","testing",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 111
    )
    public void noAuthWithEmptyRequisites() {
       // if(config.mobileAuth())skipTest();
        kraken.get().page(Config.CoreSettings.defaultRetailer);

        Shop.AuthModal.open();
        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm("", "");
        Shop.AuthModal.submit();

        baseChecks.checkIsErrorMessageElementPresent("Укажите email",
                "Нет пользовательской ошибки пустого поля email");

        baseChecks.checkIsErrorMessageElementPresent("Укажите пароль",
                "Нет пользовательской ошибки пустого поля Пароль");
        authChecks.checkIsUserNotAuthorized("Произошла авторизация с пустыми реквизитами");

    }

    @Description("Тест юзер пытается авторизоваться без email")
    @Test(
            description = "Негативный тест попытки авторизации без email",
            groups = {
                    "metro-regression","testing",
                    "sbermarket-regression"
            },
            priority = 112
    )
    public void noAuthWithoutEmail() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);

        Shop.AuthModal.open();
        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm("", "instamart");
        Shop.AuthModal.submit();

        baseChecks.checkIsErrorMessageElementPresent("Укажите email",
                "Нет пользовательской ошибки пустого поля email");
        authChecks.checkIsUserNotAuthorized("Произошла авторизация без email");
    }

    @Test(
            description = "Негативный тест попытки авторизации без пароля",
            groups = {
                    "metro-regression","testing",
                    "sbermarket-regression"
            },
            priority = 113
    )
    public void noAuthWithoutPassword() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);

        Shop.AuthModal.open();
        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm(Users.superuser().getLogin(), "");
        Shop.AuthModal.submit();

        baseChecks.checkIsErrorMessageElementPresent("Укажите пароль",
                "Нет пользовательской ошибки пустого поля Пароль");
        authChecks.checkIsUserNotAuthorized("Произошла авторизация без пароля");
    }

    @Test(
            description = "Негативный тест попытки авторизации несуществующим юзером",
            groups = {
                    "metro-regression","testing",
                    "sbermarket-regression"
            },
            priority = 114
    )
    public void noAuthWithNonexistingUser() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);

        Shop.AuthModal.open();
        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm("nonexistinguser@example.com", "password");
        Shop.AuthModal.submit();

        baseChecks.checkIsErrorMessageElementPresent("Неверный email или пароль",
                "Нет пользовательской ошибки авторизации с неверным email или паролем");
        authChecks.checkIsUserNotAuthorized("Произошла авторизация несуществующим юзером");
    }

    @Test(
            description = "Негативный тест попытки авторизации с неверным паролем",
            groups = {
                    "metro-acceptance", "metro-regression","testing",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 115
    )
    public void noAuthWithWrongPassword() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);

        Shop.AuthModal.open();
        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm(Users.superuser().getLogin(), "wrongpassword");
        Shop.AuthModal.submit();

        baseChecks.checkIsErrorMessageElementPresent("Неверный email или пароль",
                "Нет пользовательской ошибки авторизации с неверным email или паролем");
        authChecks.checkIsUserNotAuthorized("Произошла авторизация с неверным паролем");
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
        UserData testUser = generate.testCredentials("user",129);
        kraken.get().page(Config.CoreSettings.defaultRetailer);

        Shop.AuthModal.open();
        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm(testUser.getLogin(), testUser.getPassword());
        Shop.AuthModal.submit();

        baseChecks.checkIsErrorMessageElementPresent("Неверный email или пароль",
                "Нет пользовательской ошибки авторизации с неверным email или паролем");
        authChecks.checkIsUserNotAuthorized("Произошла авторизация при наличии ошибок заполнения формы авторизации");
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
        kraken.get().page(Config.CoreSettings.defaultRetailer);

        Shop.AuthModal.open();
        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm(Users.superuser().getLogin(), Users.superuser().getPassword());
        Shop.AuthModal.close();

        baseChecks.checkIsAuthModalClosed();
        authChecks.checkIsUserNotAuthorized("Произошла авторизация после заполнения всех полей и закрытия модалки");
    }

    @Test(
            description = "Тест успешной авторизации на витрине",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-smoke", "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 119
    )
    public void successAuthOnMainPage() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);

        User.Do.loginAs(session.admin);

        authChecks.checkIsUserAuthorized("Не работает авторизация на витрине магазина");
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
        kraken.get().page(Config.CoreSettings.defaultRetailer);

        Shop.ShippingAddressModal.open();
        kraken.perform().click(Elements.Modals.AddressModal.authButton());
        baseChecks.checkIsAuthModalOpen("Не работает переход на авторизацию из адресной модалки");
        User.Do.loginAs(session.user);
        authChecks.checkIsUserAuthorized("Не работает авторизация из адресной модалки феникса");
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
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();

        baseChecks.checkIsAuthModalOpen("Не открывается авторизационная модалка при переходе" +
                " неавторизованным пользователем из корзины в чекаут");
        User.Auth.withEmail(testuser);
        authChecks.checkAutoCheckoutRedirect("Нет автоперехода в чекаут после авторизации из корзины");
        kraken.get().baseUrl();
        authChecks.checkIsUserAuthorized("Не работает авторизация из корзины");
        baseChecks.checkIsCartEmpty("Авторизация из корзины",
                "Пропали товары после авторизации из корзины");
    }

    @Test(
            description = "Тест успешной авторизации через ВКонтакте",
            priority = 122,
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAuthWithVkontakte() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        User.Auth.withVkontakte(Users.vkontakte());
        authChecks.checkIsUserAuthorized("Не работает авторизация через ВКонтакте");
    }

    @Test(
            description = "Тест успешной авторизации через Facebook",
            priority = 123,
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAuthWithFacebook() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        User.Auth.withFacebook(Users.facebook());
        authChecks.checkIsUserAuthorized("Не работает авторизация через Facebook");
    }

    @Test(  description = "Тест успешной авторизации через MailRu",
            priority = 124,
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAuthWithMailRu() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        User.Auth.withMailRu(Users.mailRu());
        authChecks.checkIsUserAuthorized("Не работает авторизация через MailRu");
    }


    @Test(
            description = "Тест успешной авторизации через Sber ID",
            priority = 125,
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAuthWithSberID() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        User.Auth.withSberID(Users.sberId());
        authChecks.checkIsUserAuthorized("Не работает авторизация через Sber ID");
    }
}

