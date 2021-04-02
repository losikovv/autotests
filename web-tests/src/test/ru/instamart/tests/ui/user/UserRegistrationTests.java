package ru.instamart.tests.ui.user;

import instamart.core.settings.Config;
import instamart.core.testdata.UserManager;
import instamart.core.testdata.ui.Generate;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.checkpoints.users.ShoppingCartCheckpoints;
import instamart.ui.checkpoints.users.UsersAuthorizationCheckpoints;
import instamart.ui.common.lib.Addresses;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

@Epic("STF UI")
@Feature("Регистрация пользователя")
public class UserRegistrationTests extends TestBase {
    public static String modalType;
    private static String phone;
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    ShoppingCartCheckpoints shopChecks = new ShoppingCartCheckpoints();
    UsersAuthorizationCheckpoints authChecks = new UsersAuthorizationCheckpoints();

    @BeforeMethod(alwaysRun = true,
            description ="Завершаем сессию, текущего пользователя")
    public void quickLogout() {
        User.Logout.quickly();
        kraken.perform().deleteAllCookies();
    }

    @CaseId(1552)
    @Story("Регистрация на странице ретейлера")
    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с пустыми реквизитами",
            groups = {
                    "metro-acceptance", "metro-regression","sbermarket-Ui-smoke"
            }
    )
    public void noRegWithEmptyRequisites() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.AuthModal.openAuthRetailer();
        User.Do.registration("");
        baseChecks.checkIsErrorMessageElementPresentByPhone("Номер должен начинаться с \"+7 (9..\"",
                "Нет пользовательской ошибки пустого номера телефона");
        kraken.get().page(Config.DEFAULT_RETAILER);
        authChecks.checkIsUserNotAuthorized("Произошла регистрация пользователя с пустыми реквизитами");
    }

    @CaseId(2044)
    @Story("Регистрация на странице ретейлера")
    @Test(
            description = "Тест таймаута повторной отправки смс при быстром перелогине",
            groups = {
                    "sbermarket-Ui-smoke","sbermarket-regression"
            }
    )
    public void timeOutForSendindSMS() {
        phone = Generate.phoneNumber();
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.AuthModal.openAuthRetailer();
        User.Do.registration(phone);
        User.Do.sendSms(Config.DEFAULT_SMS);
        User.Logout.quickly();
        Shop.AuthModal.openAuthLending();
        User.Do.registrationWithoutConfirmation(phone);
        baseChecks.checkIsElementDisabled(Elements.Modals.AuthModal.continueButton());
        kraken.get().baseUrl();
        authChecks.checkIsUserNotAuthorized("Произошла регистрация пользователя с уже используемым email");
    }

    @CaseId(1541)
    @Story("Регистрация на лендинге")
    @Test(
            description = "Регистрация нового пользователя на лендинге",
            groups = {"metro-acceptance","sbermarket-Ui-smoke","MRAutoCheck"}
    )
    public void successRegOnLanding() {
        phone = Generate.phoneNumber();
        kraken.get().baseUrl();
        Shop.AuthModal.openAuthLending();
        User.Do.registration(phone);
        User.Do.sendSms(Config.DEFAULT_SMS);
        authChecks.checkIsUserAuthorized("Не работает регистрация на лендинге");
    }

    @CaseId(1543)
    @Story("Регистрация на странице ретейлера")
    @Test(
            description = "Регистрация нового пользователя на витрине магазина",
            groups = {
                    "metro-smoke", "metro-acceptance", "metro-regression",
                    "sbermarket-Ui-smoke"
            }
    )
    public void successRegOnMainPage() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        phone = Generate.phoneNumber();
        Shop.AuthModal.openAuthRetailer();
        User.Do.registration(phone);
        User.Do.sendSms(Config.DEFAULT_SMS);
        authChecks.checkIsUserAuthorized("Не работает регистрация на витрине магазина");
    }

    @CaseId(1542)
    @Story("Регистрация на странице ретейлера")
    @Test(
            description = "Тест регистрации из адресной модалки феникса",
            groups = {
                    "metro-regression","metro-acceptance",
                    "sbermarket-regression","sbermarket-Ui-smoke"
            }
    )
    public void successRegFromAddressModal() throws AssertionError {
        kraken.get().page(Config.DEFAULT_RETAILER);
        phone = Generate.phoneNumber();
        Shop.ShippingAddressModal.open();
        Shop.ShippingAddressModal.openAuthModal();
//        baseChecks.checkIsAuthModalOpen("Не работает переход на авторизацию из адресной модалки");
        User.Do.registration(phone);
        User.Do.sendSms(Config.DEFAULT_SMS);
        authChecks.checkIsUserAuthorized("Не работает регистрация из адресной модалки феникса");
    }

    @CaseId(748)
    @Story("Регистрация на странице ретейлера")
    @Test(

            description = "Тест регистрации при переходе из корзины в чекаут",
            groups = {
                    "metro-regression",
                    "sbermarket-regression","sbermarket-Ui-smoke"
            }
    )
    public void successRegFromCart() {
        phone = Generate.phoneNumber();
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.ShippingAddressModal.open();
        Shop.ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        Shop.ShippingAddressModal.selectAddressSuggest();
        Shop.ShippingAddressModal.submit();
        Shop.Cart.collectFirstTime();
        Shop.Cart.proceedToCheckout();
        baseChecks.checkIsAuthModalOpen("Не открывается авторизационная" +
                " модалка при переходе неавторизованным из корзины в чекаут");
        User.Do.registration(phone);
        User.Do.sendSms(Config.DEFAULT_SMS);
        authChecks.checkAutoCheckoutRedirect("Нет автоперехода в чекаут после регистрации из корзины");
        kraken.get().baseUrl();
        authChecks.checkIsUserAuthorized("Не работает регистрация из корзины");
        shopChecks.checkIsCartEmpty("регистрации из корзины",
                "Пропали товары после регистрации из корзины");
    }

    @CaseId(1545)
    @Story("Регистрация на странице ретейлера")
    @Test(
            description = "Тест успешной регистрации без проставленной галки Получать выгодные предложения",
            groups = {"testing"}
    )
    public void successRegWithoutMailingCheckbox() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.AuthModal.openAuthRetailer();
        User.Do.registration(phone,true);// ВОт здесь проблема
        User.Do.sendSms(Config.DEFAULT_SMS);
        User.Do.regSequence(UserManager.getUser(), false ); // todo вынести в Shop.AuthModal.fill()
        Shop.AuthModal.submit();
        authChecks.checkIsUserAuthorized("Не работает регистрация без согласия на получение почтовой рассылки");
    }

    @Test(
            description = "Тест успешной регистрации с заново проставленной галкой согласия на почтовую рассылку",
            groups = {}
    )
    public void successRegWithMailingCheckbox() {
        if(modalType.equals("модалка с телефоном")){skipTest();}
        kraken.get().page(Config.DEFAULT_RETAILER);

        Shop.AuthModal.open();
        User.Do.regSequence(UserManager.getUser(), false );
//        kraken.await().fluently(ExpectedConditions.elementToBeClickable(Elements.Modals.AuthModal.agreementCheckbox().getLocator()));
        kraken.perform().setCheckbox(Elements.Modals.AuthModal.checkBoxes(),2);
        kraken.perform().click(Elements.Modals.AuthModal.submitButton());
        kraken.await().implicitly(5);
        authChecks.checkIsUserAuthorized("Не работает регистрация с согласием на получение почтовой рассылки");
    }

    @Test(
            description = "Тест успешной регистрации через ВКонтакте",

            groups = {"testing"}
    )
    public void successRegWithVkontakte() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Auth.withVkontakte(UserManager.getDefaultVkUser());
        authChecks.checkIsUserAuthorized("Не работает регистрация через ВКонтакте");
    }

    @Test(
            description = "Тест успешной регистрации через Facebook",

            groups = {"testing"}
    )
    public void successRegWithFacebook() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Auth.withFacebook(UserManager.getDefaultFbUser());
        authChecks.checkIsUserAuthorized("Не работает регистрация через Facebook");
    }

    @CaseId(1460)
    @Story("Регистрация через партнеров")
    @Test(  description = "Тест успешной регистрации через MailRu",
            groups = {"sbermarket-Ui-smoke"}
    )
    public void successRegWithMailRu() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Auth.withMailRu(UserManager.getDefaultMailRuUser());
        authChecks.checkIsUserAuthorized("Не работает регистрация через MailRu");
    }


    @Test(
            description = "Тест успешной регистрации через Sber ID",

            groups = {"testing"}
    )
    public void successRegWithSberID() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Auth.withSberID(UserManager.getDefaultSberIdUser());
        authChecks.checkIsUserAuthorized("Не работает регистрация через Sber ID");
    }
}
