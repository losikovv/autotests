package ru.instamart.test.reforged.stf.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.checkout;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Авторизация")
public final class UserAuthorisationTests extends BaseTest {

    @CaseId(1455)
    @Test(description = "Тест успешной авторизации на витрине", groups = {"acceptance", "regression", "smoke"})
    public void successAuthOnMainPage() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(1456)
    @Test(description = "Тест авторизации из адресной модалки феникса", groups = "regression")
    public void successAuthFromAddressModal() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(1457)
    @Test(description = "Тест успешной авторизации из корзины", groups = "regression")
    public void successAuthFromCart() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().plusFirstItemToCart();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().increaseCountToMin();
        shop().interactCart().submitOrder();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        checkout().checkCheckoutButtonIsVisible();
    }

    @CaseId(1459)
    @Story("Авторизация через Facebook")
    @Test(description = "Тест успешной авторизация через Facebook", groups = {"smoke", "regression"})
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
    @Story("Авторизация через VK")
    @Test(description = "Тест успешной авторизация через ВКонтакте", groups = {"smoke", "regression"})
    public void successRegWithVkontakte() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().checkModalIsVisible();
        shop().interactAuthModal().authViaVk();
        shop().interactAuthModal().interactAuthVkWindow().switchToNextWindow();
        shop().interactAuthModal().interactAuthVkWindow()
                .setEmail(UserManager.getDefaultVkUser().getEmail());
        shop().interactAuthModal().interactAuthVkWindow()
                .setPassword(UserManager.getDefaultVkUser().getPassword());
        shop().interactAuthModal().interactAuthVkWindow()
                .clickToLogin();
        shop().interactAuthModal().interactAuthVkWindow().switchToFirstWindow();
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();
    }

    //TODO: Починить юзера
    @Skip
    @CaseId(1460)
    @Story("Авторизация через Mail.ru")
    @Test(description = "Тест успешной авторизация через MailRu", groups = {"smoke", "regression"})
    public void successRegWithMailRu() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().checkModalIsVisible();
        shop().interactAuthModal().authViaMail();
        shop().interactAuthModal().interactAuthMailWindow().switchToNextWindow();
        shop().interactAuthModal().interactAuthMailWindow()
                .fillName(UserManager.getDefaultMailRuUser().getEmail());
        shop().interactAuthModal().interactAuthMailWindow()
                .clickToEnterPassword();
        shop().interactAuthModal().interactAuthMailWindow()
                .fillPassword(UserManager.getDefaultMailRuUser().getPassword());
        shop().interactAuthModal().interactAuthMailWindow().clickToSubmit();
        shop().interactAuthModal().interactAuthMailWindow().switchToFirstWindow();
        shop().waitPageLoad();
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();
    }

    @Skip
    @CaseId(1461)
    @Story("Авторизация через SberID")
    @Test(description = "Тест успешной авторизация через Sber ID", groups = {"smoke", "regression"})
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
