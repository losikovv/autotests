package ru.instamart.tests.ui.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.core.settings.Config;
import ru.instamart.core.testdata.ui.Generate;
import ru.instamart.tests.ui.TestBase;
import ru.instamart.ui.checkpoints.BaseUICheckpoints;
import ru.instamart.ui.checkpoints.shoppingcart.ShoppingCartCheckpoints;
import ru.instamart.ui.checkpoints.users.UsersAuthorizationCheckpoints;
import ru.instamart.ui.common.lib.Addresses;
import ru.instamart.ui.common.lib.Pages;
import ru.instamart.ui.modules.Shop;
import ru.instamart.ui.modules.User;
import ru.instamart.ui.modules.shop.ShippingAddressModal;
import ru.instamart.ui.objectsmap.Elements;

@Epic("STF UI")
@Feature("Регистрация пользователя")
public final class UserRegistrationTests extends TestBase implements UsersAuthorizationCheckpoints {

    private static String phone;
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    ShoppingCartCheckpoints shopChecks = new ShoppingCartCheckpoints();

    @BeforeMethod(alwaysRun = true,
            description ="Завершаем сессию, текущего пользователя")
    public void quickLogout() {
        User.Logout.quickly();
    }

    @CaseId(1552)
    @Story("Регистрация на странице ретейлера")
    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с пустыми реквизитами",
            groups = {
                    "metro-acceptance", "metro-regression","sbermarket-Ui-smoke","ui-smoke-production"
            }
    )
    public void noRegWithEmptyRequisites() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.AuthModal.openAuthRetailer();
        User.Do.registration("",true);
        baseChecks.checkIsErrorMessageElementPresentByPhone("Номер должен начинаться с \"+7 (9..\"",
                "Нет пользовательской ошибки пустого номера телефона");
        kraken.get().page(Config.DEFAULT_RETAILER);
        checkIsUserNotAuthorized("Произошла регистрация пользователя с пустыми реквизитами");
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
        User.Do.registration(phone,true);
        User.Do.sendSms(Config.DEFAULT_SMS);
        User.Logout.quickly();
        Shop.AuthModal.openAuthLending();
        User.Do.registrationWithoutConfirmation(phone);
        baseChecks.checkIsElementDisabled(Elements.Modals.AuthModal.continueButton());
        kraken.get().baseUrl();
        checkIsUserNotAuthorized("Произошла регистрация пользователя с уже используемым email");
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
        User.Do.registration(phone,true);
        User.Do.sendSms(Config.DEFAULT_SMS);
        checkIsUserAuthorized("Не работает регистрация на лендинге");
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
        User.Do.registration(phone,true);
        User.Do.sendSms(Config.DEFAULT_SMS);
        checkIsUserAuthorized("Не работает регистрация на витрине магазина");
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
        phone = Generate.phoneNumber();
        kraken.get().page(Config.DEFAULT_RETAILER);
        ShippingAddressModal.open();
        ShippingAddressModal.openAuthModal();
//        baseChecks.checkIsAuthModalOpen("Не работает переход на авторизацию из адресной модалки");
        User.Do.registration(phone,true);
        User.Do.sendSms(Config.DEFAULT_SMS);
        checkIsUserAuthorized("Не работает регистрация из адресной модалки феникса");
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
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.selectAddressSuggest();
        ShippingAddressModal.submit();
        Shop.Cart.collectFirstTime();
        kraken.get().page(Pages.Retailers.metro());
        Shop.Cart.proceedToCheckout();
        baseChecks.checkIsAuthModalOpen("Не открывается авторизационная" +
                " модалка при переходе неавторизованным из корзины в чекаут");

        User.Do.registration(phone,true);
        User.Do.sendSms(Config.DEFAULT_SMS);
        checkAutoCheckoutRedirect("Нет автоперехода в чекаут после регистрации из корзины");
        kraken.get().baseUrl();
        checkIsUserAuthorized("Не работает регистрация из корзины");
        shopChecks.checkIsCartEmpty("регистрации из корзины",
                "Пропали товары после регистрации из корзины");
    }

    @CaseId(1545)
    @Story("Регистрация на странице ретейлера")
    @Test(
            description = "Тест успешной регистрации без проставленной галки Получать выгодные предложения",
            groups = {"sbermarket-Ui-smoke"}
    )
    public void successRegWithoutMailingCheckbox() {
        phone = Generate.phoneNumber();
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.AuthModal.openAuthRetailer();
        User.Do.registration(phone,false);
        User.Do.sendSms(Config.DEFAULT_SMS);
        checkIsUserAuthorized("Не работает регистрация без согласия на получение почтовой рассылки");
    }
}
