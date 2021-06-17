package ru.instamart.test.ui.user;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.setting.Config;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.lib.Addresses;
import ru.instamart.test.ui.TestBase;
import ru.instamart.ui.Elements;
import ru.instamart.ui.checkpoint.BaseUICheckpoints;
import ru.instamart.ui.checkpoint.users.UsersAuthorizationCheckpoints;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.ui.module.Shop;
import ru.instamart.ui.module.User;
import ru.instamart.ui.module.shop.ShippingAddressModal;

@Epic("STF UI")
@Feature("Авторизация")
public final class UserAuthorisationTests extends TestBase implements UsersAuthorizationCheckpoints {

    private final BaseUICheckpoints baseChecks = new BaseUICheckpoints();

    @BeforeMethod(alwaysRun = true,
            description ="Проверяем залогинен ли пользователь, если да то завершаем сессию")
    public void quickLogout() {
        AppManager.closeWebDriver();
        kraken.get().baseUrl();
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
        checkIsUserNotAuthorized("Произошла авторизация с пустыми реквизитами");

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
        checkIsUserNotAuthorized("Произошла авторизация без email");
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
        checkIsUserNotAuthorized("Произошла авторизация без пароля");
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
        checkIsUserNotAuthorized("Произошла авторизация несуществующим юзером");
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
        checkIsUserNotAuthorized("Произошла авторизация с неверным паролем");
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
        checkIsUserNotAuthorized("Произошла авторизация при наличии ошибок заполнения формы авторизации");
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
        checkIsUserNotAuthorized("Произошла авторизация после заполнения всех полей и закрытия модалки");
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

        checkIsUserAuthorized("Не работает авторизация на витрине магазина");
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

        ShippingAddressModal.open();
        kraken.perform().click(Elements.Modals.AddressModal.authButton());
        baseChecks.checkIsAuthModalOpen("Не работает переход на авторизацию из адресной модалки");
        User.Do.loginAs(UserManager.getDefaultUser());
        checkIsUserAuthorized("Не работает авторизация из адресной модалки феникса");
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
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.submit();

        Shop.Cart.collectFirstTime();
        Shop.Cart.proceedToCheckout();

        baseChecks.checkIsAuthModalOpen("Не открывается авторизационная модалка при переходе" +
                " неавторизованным пользователем из корзины в чекаут");
        User.Auth.withEmail(testuser);
        //checkAutoCheckoutRedirect("Нет автоперехода в чекаут после авторизации из корзины");
        kraken.get().baseUrl();
        checkIsUserAuthorized("Не работает авторизация из корзины");
//        shopChecks.checkIsCartEmpty("Авторизация из корзины",
//                "Пропали товары после авторизации из корзины");
    }

    @CaseId(1459)
    @Test(
            description = "Тест успешной авторизация через Facebook",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    @Story("Авторизация через Facebook")
    public void successRegWithFacebook() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.AuthModal.openAuthRetailer();
        Shop.AuthModal.hitFacebookButton();
        User.Auth.withFacebook(UserManager.getDefaultFbUser());
        checkIsUserAuthorized("Не работает авторизация через Facebook");
    }

    @CaseId(1458)
    @Test(
            description = "Тест успешной авторизация через ВКонтакте",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    @Story("Авторизация через VK")
    public void successRegWithVkontakte() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.AuthModal.openAuthRetailer();
        Shop.AuthModal.hitVkontakteButton();
        User.Auth.withVkontakte(UserManager.getDefaultVkUser()); //Создавать второй поток и работать в нем?
        checkIsUserAuthorized("Не работает авторизация через ВКонтакте");
    }

    @CaseId(1460)
    @Story("Регистрация через партнеров")
    @Test(  description = "Тест успешной авторизация через MailRu",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    @Story("Авторизация через Mail.ru")
    public void successRegWithMailRu() throws InterruptedException {
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.AuthModal.openAuthRetailer();
        Shop.AuthModal.hitMailRuButton();
        User.Auth.withMailRu(UserManager.getDefaultMailRuUser());
        checkIsUserAuthorized("Не работает авторизация через MailRu");
    }

    @Skip
    @CaseId(1461)
    @Test(
            description = "Тест успешной авторизация через Sber ID",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    @Story("Авторизация через SberID")
    public void successRegWithSberID() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.AuthModal.openAuthRetailer();
        Shop.AuthModal.hitSberIdButton();
        User.Auth.withSberID(UserManager.getDefaultSberIdUser());
        checkIsUserAuthorized("Не работает авторизация через Sber ID");
    }
}

