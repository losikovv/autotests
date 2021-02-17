package ru.instamart.tests.ui.user;

import instamart.core.settings.Config;
import instamart.core.testdata.UserManager;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.checkpoints.users.ShoppingCartCheckpoints;
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
import ru.instamart.tests.ui.TestBase;

public class UserAuthorisationTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    ShoppingCartCheckpoints shopChecks = new ShoppingCartCheckpoints();
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

    @Description("Тест пытается авторизоваться с пустыми реквизитами")
    @Test(
            description = "Негативный тест попытки авторизации с пустыми реквизитами",
            groups = {
                    "metro-acceptance", "metro-regression","testing",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    )
    public void noAuthWithEmptyRequisites() {
       // if(config.mobileAuth())skipTest();
        kraken.get().page(Config.DEFAULT_RETAILER);

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
            }
    )
    public void noAuthWithoutEmail() {
        kraken.get().page(Config.DEFAULT_RETAILER);

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
            }
    )
    public void noAuthWithoutPassword() {
        kraken.get().page(Config.DEFAULT_RETAILER);

        Shop.AuthModal.open();
        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm(UserManager.getDefaultUser().getLogin(), "");
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
            }
    )
    public void noAuthWithNonexistingUser() {
        kraken.get().page(Config.DEFAULT_RETAILER);

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
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    )
    public void noAuthWithWrongPassword() {
        kraken.get().page(Config.DEFAULT_RETAILER);

        Shop.AuthModal.open();
        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm(UserManager.getDefaultUser().getLogin(), "wrongpassword");
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
            }
    )
    public void noAuthWithLongFields() {
        final UserData testUser = UserManager.getUser(129);
        kraken.get().page(Config.DEFAULT_RETAILER);

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
            }
    )
    public void noAuthOnModalClose() {
        kraken.get().page(Config.DEFAULT_RETAILER);

        Shop.AuthModal.open();
        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm(UserManager.getDefaultUser().getLogin(), UserManager.getDefaultUser().getPassword());
        Shop.AuthModal.close();

        baseChecks.checkIsAuthModalClosed();
        authChecks.checkIsUserNotAuthorized("Произошла авторизация после заполнения всех полей и закрытия модалки");
    }

    @Test(
            description = "Тест успешной авторизации на витрине",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "testing", "sbermarket-acceptance","sbermarket-regression"
            }
    )
    public void successAuthOnMainPage() {
        kraken.get().page(Config.DEFAULT_RETAILER);

        User.Do.loginAs(UserManager.getDefaultAdmin());

        authChecks.checkIsUserAuthorized("Не работает авторизация на витрине магазина");
    }

    @Test(
            description = "Тест авторизации из адресной модалки феникса",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"
            }
    )
    public void successAuthFromAddressModal() {
        kraken.get().page(Config.DEFAULT_RETAILER);

        Shop.ShippingAddressModal.open();
        kraken.perform().click(Elements.Modals.AddressModal.authButton());
        baseChecks.checkIsAuthModalOpen("Не работает переход на авторизацию из адресной модалки");
        User.Do.loginAs(UserManager.getDefaultUser());
        authChecks.checkIsUserAuthorized("Не работает авторизация из адресной модалки феникса");
    }

    @Test(
            description = "Тест успешной авторизации из корзины",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"
            }
    )
    public void successAuthFromCart() {
        final UserData testuser = UserManager.getUser();

        User.Do.registration(testuser);
        User.Logout.quickly();
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);

        Shop.Cart.collectFirstTime();
        Shop.Cart.proceedToCheckout();

        baseChecks.checkIsAuthModalOpen("Не открывается авторизационная модалка при переходе" +
                " неавторизованным пользователем из корзины в чекаут");
        User.Auth.withEmail(testuser);
        //authChecks.checkAutoCheckoutRedirect("Нет автоперехода в чекаут после авторизации из корзины");
        kraken.get().baseUrl();
        authChecks.checkIsUserAuthorized("Не работает авторизация из корзины");
//        shopChecks.checkIsCartEmpty("Авторизация из корзины",
//                "Пропали товары после авторизации из корзины");
    }

    @Test(
            description = "Тест успешной авторизации через ВКонтакте",

            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAuthWithVkontakte() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Auth.withVkontakte(UserManager.getDefaultVkUser());
        authChecks.checkIsUserAuthorized("Не работает авторизация через ВКонтакте");
    }

    @Test(
            description = "Тест успешной авторизации через Facebook",

            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAuthWithFacebook() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Auth.withFacebook(UserManager.getDefaultFbUser());
        authChecks.checkIsUserAuthorized("Не работает авторизация через Facebook");
    }

    @Test(  description = "Тест успешной авторизации через MailRu",

            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAuthWithMailRu() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Auth.withMailRu(UserManager.getDefaultMailRuUser());
        authChecks.checkIsUserAuthorized("Не работает авторизация через MailRu");
    }


    @Test(
            description = "Тест успешной авторизации через Sber ID",

            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAuthWithSberID() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Auth.withSberID(UserManager.getDefaultSberIdUser());
        authChecks.checkIsUserAuthorized("Не работает авторизация через Sber ID");
    }
}

