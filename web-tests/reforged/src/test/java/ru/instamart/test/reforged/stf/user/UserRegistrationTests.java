package ru.instamart.test.reforged.stf.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.kraken.util.ThreadUtil;
import ru.instamart.reforged.core.annotation.CookieProvider;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.*;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Регистрация пользователя")
public final class UserRegistrationTests {

    @CaseId(1552)
    @Story("Регистрация на странице ретейлера")
    @Test(description = "Негативный тест попытки зарегистрировать пользователя с пустыми реквизитами", groups = {STARTING_X, REGRESSION_STF})
    public void noRegWithEmptyRequisites() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        ThreadUtil.simplyAwait(1);
        shop().interactAuthModal().fillPhone("");
        shop().interactAuthModal().sendSms();
        shop().interactAuthModal().checkPhoneErrorIsVisible();
    }

    @CaseId(1541)
    @Story("Регистрация на лендинге")
    @Test(description = "Регистрация нового пользователя на лендинге", groups = {STARTING_X, REGRESSION_STF, SMOKE_STF, "MRAutoCheck"})
    public void successRegOnLanding() {
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(UserManager.getQaUser());
        home().checkLogoutButtonDisplayed();
    }

    @CaseId(1543)
    @Story("Регистрация на странице ретейлера")
    @Test(description = "Регистрация нового пользователя на витрине магазина", groups = {STARTING_X, REGRESSION_STF, SMOKE_STF})
    public void successRegOnMainPage() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(1542)
    @Story("Регистрация на странице ретейлера")
    @Test(description = "Тест регистрации из адресной модалки феникса", groups = {STARTING_X, REGRESSION_STF})
    @Skip() // Для незарегистрированного пользователя теперь появляется большое окно выбора адреса, там нет авторизации
    public void successRegFromAddressModal() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(748)
    @Story("Регистрация на странице ретейлера")
    @Test(description = "Тест регистрации при переходе из корзины в чекаут", groups = {STARTING_X, REGRESSION_STF})
    public void successRegFromCart() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddressLarge().clickSave();
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().plusFirstItemToCart();

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().increaseFirstItemCountToMin();
        shop().interactCart().submitOrder();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        checkout().checkCheckoutButtonIsVisible();
    }

    @CaseId(1545)
    @Story("Регистрация на странице ретейлера")
    @Test(description = "Тест успешной регистрации без проставленной галки Получать выгодные предложения", groups = {STARTING_X, REGRESSION_STF, SMOKE_STF})
    public void successRegWithoutMailingCheckbox() {
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().checkModalIsVisible();
        home().interactAuthModal().uncheckPromoMailing();
        home().interactAuthModal().authViaPhone(UserManager.getQaUser());
        home().checkLogoutButtonDisplayed();
    }

    @CaseId(2622)
    @Story("Регистрация из корзины")
    @Test(description = "Регистрация при попытке перехода из корзины в чекаут", groups = {STARTING_X, REGRESSION_STF, SMOKE_STF})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_NEW_CART"})
    public void successRegFromCartWithQuantityAndAmountCheck() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddressLarge().clickSave();
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().plusFirstItemToCart();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().increaseFirstItemCountToMin();
        final var orderAmount = shop().interactCart().getOrderAmount();
        final var positionsCount = shop().interactCart().getFirstRetailer().getItemsCountInHeaderProd();

        shop().interactCart().submitOrder();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        checkoutNew().checkDeliverySlotsVisible();

        final var orderAmountInCheckout = checkoutNew().getOrderAmountDouble();

        checkoutNew().compareOrderAmountAfterRegistration(orderAmount, orderAmountInCheckout);
    }
}
