package ru.instamart.test.reforged.stf.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Run;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.reforged.core.config.BasicProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.Group.STARTING_X;
import static ru.instamart.reforged.core.config.UiProperties.DEFAULT_METRO_MOSCOW_SID;
import static ru.instamart.reforged.sber_id_auth.SberIdPageRouter.sberId;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Авторизация")
public final class UserAuthorisationTests {

    private final ApiHelper apiHelper = new ApiHelper();

    @CaseId(1455)
    @Test(description = "Тест успешной авторизации на витрине", groups = {STARTING_X, REGRESSION_STF, "smoke"})
    public void successAuthOnMainPage() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(2543)
    @Test(description = "Авторизация по номеру телефона", groups = {STARTING_X, REGRESSION_STF, "smoke"})
    public void successAuthOnMainPageUserWithOrder() {
        final var user = UserManager.getQaUser();
        apiHelper.dropAndFillCartByOneProduct(user, DEFAULT_METRO_MOSCOW_SID, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(user);

        home().interactMultisearchHeader().checkUserActionsButtonVisible();
        home().interactMultisearchHeader().checkEnteredAddress(Addresses.Moscow.defaultAddressRest());
        home().checkDeliveryStoresContainerVisible();
    }

    @Skip
    // Сейчас для незарегистрированного пользователя отображается "большая" модалка выбора адреса в ней нет кнопки авторизации
    @CaseId(1456)
    @Test(description = "Тест авторизации из адресной модалки феникса", groups = {STARTING_X, REGRESSION_STF})
    public void successAuthFromAddressModal() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(2621)
    @Test(description = "Тест успешной авторизации из корзины", groups = {STARTING_X, REGRESSION_STF})
    public void successAuthFromCart() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddressLarge().clickSave();
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().plusFirstItemToCartProd();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().increaseFirstItemCountToMin();
        shop().interactCart().submitOrder();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        checkout().checkCheckoutButtonIsVisible();
    }

    //Короче FB опять заблокировал наш ip
    @CaseId(1459)
    @Story("Авторизация через Facebook")
    @Skip
    @Test(description = "Тест успешной авторизация через Facebook", groups = {"smoke", REGRESSION_STF})
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
    @Test(description = "Тест успешной авторизация через ВКонтакте", groups = {STARTING_X, "smoke", REGRESSION_STF})
    public void successRegWithVkontakte() {
        UserData vkUser = UserManager.getNewVkUser();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().checkModalIsVisible();
        shop().interactAuthModal().authViaVk();
        shop().interactAuthModal().interactAuthVkWindow().switchToNextWindow();
        shop().interactAuthModal().interactAuthVkWindow().setEmail(vkUser.getEmail());
        shop().interactAuthModal().interactAuthVkWindow().setPassword(vkUser.getPassword());
        shop().interactAuthModal().interactAuthVkWindow().clickToLogin();
        shop().interactAuthModal().interactAuthVkWindow().switchToFirstWindow();
        shop().interactAuthModal().checkModalIsNotVisible();

        //TODO на production появляется окно подтверждения номера телефона, на кракене - нет, тест падает.
        shop().interactAuthModal().checkModalConfirmPhoneIsVisible();
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(1460)
    @Story("Авторизация через Mail.ru")
    @Test(description = "Тест успешной авторизация через MailRu", groups = {STARTING_X, "smoke", REGRESSION_STF})
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
        shop().interactAuthModal().checkModalIsNotVisible();

        //TODO на production появляется окно подтверждения номера телефона, на кракене - нет, тест падает.
        shop().interactAuthModal().checkModalConfirmPhoneIsVisible();
        shop().interactHeader().checkProfileButtonVisible();
    }

    //Нет учетки для SberID
    @CaseId(1461)
    @Story("Авторизация через SberID")
    @Skip
    @Test(description = "Тест успешной авторизация через Sber ID", groups = {STARTING_X, "smoke", REGRESSION_STF})
    public void successRegWithSberID() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaSberId();

        sberId().checkPhoneInputVisible();
        sberId().fillPhoneNumber(UserManager.getDefaultSberIdUser().getEmail());
        sberId().clickToSubmitLogin();
        sberId().clickToReceivedSms();
        //TODO: необходимо автоматизировать получение смс, либо получить номер с заготовленной смс
        sberId().enterCode("111111");
        sberId().clickToSubmitSmsCode();
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();
    }

    //@CaseId(1459)
    @CaseId(3522)
    @Story("Авторизация через SberID")
    @Test(description = "Тест перехода на сайт Sber ID", groups = {STARTING_X, "smoke", REGRESSION_STF})
    public void checkCorrectTransitionToSberIdSite() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().checkModalIsVisible();
        shop().interactAuthModal().authViaSberId();

        sberId().checkPhoneInputVisible();
        sberId().checkPageContains(BasicProperties.SBER_ID_URL);
    }

    //    @CaseId(1459)
    @Story("Авторизация через СберБизнесID")
    @Test(description = "Тест успешной авторизация через СберБизнесID", groups = {STARTING_X, "smoke", REGRESSION_STF})
    public void successRegWithSberBusinessID() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();

        shop().interactAuthModal().checkModalIsVisible();
        shop().interactAuthModal().checkForBusiness();
        shop().interactAuthModal().authViaSberBusinessId();

        shop().interactAuthModal().interactAuthSberBusinessIdPage()
                .setLogin(UserManager.getDefaultSberBusinessIdUser().getEmail());
        shop().interactAuthModal().interactAuthSberBusinessIdPage()
                .setPassword(UserManager.getDefaultSberBusinessIdUser().getPassword());
        shop().interactAuthModal().interactAuthSberBusinessIdPage().clickToNext();
        shop().interactAuthModal().interactAuthSberBusinessIdPage()
                .enterCode(UserManager.getDefaultSberBusinessIdUser().getSmsCode());

        //TODO По информации от Артёма Кофтаенко, сейчас проблемы с авторизацией по SberBusinessID на тестах, разбираются
        shop().waitPageLoad();
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();
    }
}
