package ru.instamart.test.reforged.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.setting.Config;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.lib.Addresses;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Регистрация пользователя")
public class UserRegistrationTests extends BaseTest {

    @CaseId(1552)
    @Story("Регистрация на странице ретейлера")
    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с пустыми реквизитами",
            groups = {
                    "metro-acceptance", "metro-regression", "sbermarket-Ui-smoke", "ui-smoke-production"
            }
    )
    public void noRegWithEmptyRequisites() {
        home().openSitePage(Config.DEFAULT_RETAILER);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().fillPhone("");
        shop().interactAuthModal().sendSms();
        shop().interactAuthModal().checkPhoneErrorIsVisible();
        home().goToPage();
        home().checkLoginButtonIsVisible();
    }

    @CaseId(1541)
    @Story("Регистрация на лендинге")
    @Test(
            description = "Регистрация нового пользователя на лендинге",
            groups = {"metro-acceptance", "sbermarket-Ui-smoke", "MRAutoCheck"}
    )
    public void successRegOnLanding() {
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(Generate.phoneNumber());
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillSMS(Config.DEFAULT_SMS);
        shop().interactHeader().checkProfileButtonVisible();
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
        home().openSitePage(Config.DEFAULT_RETAILER);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().fillPhone(Generate.phoneNumber());
        shop().interactAuthModal().sendSms();
        shop().interactAuthModal().fillSMS(Config.DEFAULT_SMS);
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(1542)
    @Story("Регистрация на странице ретейлера")
    @Test(
            description = "Тест регистрации из адресной модалки феникса",
            groups = {
                    "metro-regression", "metro-acceptance",
                    "sbermarket-regression", "sbermarket-Ui-smoke"
            }
    )
    public void successRegFromAddressModal() {
        home().openSitePage(Config.DEFAULT_RETAILER);
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().clickToLogin();
        shop().interactAuthModal().fillPhone(Generate.phoneNumber());
        shop().interactAuthModal().sendSms();
        shop().interactAuthModal().fillSMS(Config.DEFAULT_SMS);
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(748)
    @Story("Регистрация на странице ретейлера")
    @Test(

            description = "Тест регистрации при переходе из корзины в чекаут",
            groups = {
                    "metro-regression",
                    "sbermarket-regression", "sbermarket-Ui-smoke"
            }
    )
    public void successRegFromCart() {
        home().openSitePage(Config.DEFAULT_RETAILER);
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().setAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().plusFirstItemToCart();
        home().openSitePage(Config.DEFAULT_RETAILER);
        shop().interactHeader().clickToCart();
        shop().interactCart().increaseCountToMin();
        shop().interactCart().submitOrder();
        shop().interactAuthModal().fillPhone(Generate.phoneNumber());
        shop().interactAuthModal().sendSms();
        shop().interactAuthModal().fillSMS(Config.DEFAULT_SMS);
        checkout().checkCheckoutButtonIsVisible();
    }

    @CaseId(1545)
    @Story("Регистрация на странице ретейлера")
    @Test(
            description = "Тест успешной регистрации без проставленной галки Получать выгодные предложения",
            groups = {"sbermarket-Ui-smoke"}
    )
    public void successRegWithoutMailingCheckbox() {
        home().openSitePage(Config.DEFAULT_RETAILER);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().uncheckPromoMailing();
        shop().interactAuthModal().fillPhone(Generate.phoneNumber());
        shop().interactAuthModal().sendSms();
        shop().interactAuthModal().fillSMS(Config.DEFAULT_SMS);
        shop().interactHeader().checkProfileButtonVisible();
    }
}
