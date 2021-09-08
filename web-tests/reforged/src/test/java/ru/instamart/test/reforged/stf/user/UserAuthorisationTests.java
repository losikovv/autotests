package ru.instamart.test.reforged.stf.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.lib.Addresses;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.kraken.config.CoreProperties.DEFAULT_RETAILER;
import static ru.instamart.kraken.config.CoreProperties.DEFAULT_SMS;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Авторизация")
public final class UserAuthorisationTests extends BaseTest {

    @CaseId(1455)
    @Test(
            description = "Тест успешной авторизации на витрине",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "testing", "sbermarket-acceptance", "sbermarket-regression"
            }
    )
    public void successAuthOnMainPage() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().fillPhone("79000000000");
        shop().interactAuthModal().sendSms();
        shop().interactAuthModal().fillSMS(DEFAULT_SMS);
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(1456)
    @Test(
            description = "Тест авторизации из адресной модалки феникса",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"
            }
    )
    public void successAuthFromAddressModal() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().clickToLogin();
        shop().interactAuthModal().fillPhone("79000000001");
        shop().interactAuthModal().sendSms();
        shop().interactAuthModal().fillSMS(DEFAULT_SMS);
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(1457)
    @Test(
            description = "Тест успешной авторизации из корзины",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"
            }
    )
    public void successAuthFromCart() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().setAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().plusFirstItemToCart();

        home().openSitePage(DEFAULT_RETAILER);
        shop().interactHeader().clickToCart();
        shop().interactCart().increaseCountToMin();
        shop().interactCart().submitOrder();
        shop().interactAuthModal().fillPhone("79000000002");
        shop().interactAuthModal().sendSms();
        shop().interactAuthModal().fillSMS(DEFAULT_SMS);
        checkout().checkCheckoutButtonIsVisible();
    }

    @CaseId(1459)
    @Test(
            description = "Тест успешной авторизация через Facebook",
            groups = {"sbermarket-Ui-smoke", "ui-smoke-production"}
    )
    @Story("Авторизация через Facebook")
    public void successRegWithFacebook() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().checkModalIsVisible();
        shop().interactAuthModal().authViaFacebook();
        shop().interactAuthModal().interactAuthFacebookWindow().switchToNextWindow();
        shop().interactAuthModal().interactAuthFacebookWindow()
                .setEmail(UserManager.getDefaultFbUser().getEmail());
        shop().interactAuthModal().interactAuthFacebookWindow()
                .setPassword(UserManager.getDefaultFbUser().getPassword());
        shop().interactAuthModal().interactAuthFacebookWindow()
                .clickToLogin();
        shop().interactAuthModal().interactAuthFacebookWindow().switchToFirstWindow();
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(2735)
    @Test(
            description = "Тест успешной авторизация через ВКонтакте",
            groups = {"sbermarket-Ui-smoke", "ui-smoke-production"}
    )
    @Story("Авторизация через VK")
    public void successRegWithVkontakte() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaVk();
        shop().interactAuthModal().interactAuthVkWindow().switchToNextWindow();
        shop().interactAuthModal().interactAuthVkWindow()
                .setEmail(UserManager.getDefaultVkUser().getEmail());
        shop().interactAuthModal().interactAuthVkWindow()
                .setPassword(UserManager.getDefaultVkUser().getPassword());
        shop().interactAuthModal().interactAuthVkWindow()
                .clickToLogin();
        shop().interactAuthModal().interactAuthFacebookWindow().switchToFirstWindow();
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(1460)
    @Story("Регистрация через партнеров")
    @Test(description = "Тест успешной авторизация через MailRu",
            groups = {"sbermarket-Ui-smoke", "ui-smoke-production"}
    )
    @Story("Авторизация через Mail.ru")
    public void successRegWithMailRu() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaMail();
        shop().interactAuthModal().interactAuthMailWindow().switchToNextWindow();
        shop().interactAuthModal().interactAuthMailWindow()
                .fillName(UserManager.getDefaultMailRuUser().getEmail());
        shop().interactAuthModal().interactAuthMailWindow()
                .clickToEnterPassword();
        shop().interactAuthModal().interactAuthMailWindow()
                .fillPassword(UserManager.getDefaultMailRuUser().getPassword());
        shop().interactAuthModal().interactAuthMailWindow().clickToSubmit();
        shop().interactAuthModal().interactAuthFacebookWindow().switchToFirstWindow();
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();
    }

    @Skip
    @CaseId(1461)
    @Test(
            description = "Тест успешной авторизация через Sber ID",
            groups = {"sbermarket-Ui-smoke", "ui-smoke-production"}
    )
    @Story("Авторизация через SberID")
    public void successRegWithSberID() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaSberId();
        shop().interactAuthModal().interactAuthSberIdPage()
                .fillPhoneNumber(UserManager.getDefaultSberIdUser().getEmail());
        shop().interactAuthModal().interactAuthSberIdPage().clickToSubmitLogin();
        shop().interactAuthModal().interactAuthSberIdPage().clickToReceivedSms();
        //TODO: необходимо автоматизировать получение смс, либо получить номер с заготовленной смс
        shop().interactAuthModal().interactAuthSberIdPage()
                .enterCode("111111");
        shop().interactAuthModal().interactAuthSberIdPage().clickToSubmitSmsCode();
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();
    }
}
