package ru.instamart.test.reforged.stf_prod.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.annotation.Metrics;
import ru.instamart.reforged.core.config.UiProperties;
import io.qameta.allure.TmsLink;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Регистрация пользователя")
public final class UserRegistrationTests {

    @Metrics
    @TmsLink("1541")
    @Story("Регистрация на лендинге")
    @Test(description = "Регистрация нового пользователя на лендинге", groups = {STF_PROD_S})
    public void successRegOnLanding() {
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(UserManager.getQaUser());
        home().checkLogoutButtonDisplayed();
    }

    @Metrics
    @TmsLink("1543")
    @Story("Регистрация на странице ретейлера")
    @Test(description = "Регистрация нового пользователя на витрине магазина", groups = {STF_PROD_S})
    public void successRegOnMainPage() {
        shop().goToPageProd();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
    }

    @Metrics
    @TmsLink("1545")
    @Test(description = "Тест успешной регистрации без проставленной галки Получать выгодные предложения", groups = {STF_PROD_S})
    public void successRegWithoutMailingCheckbox() {
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().checkModalIsVisible();
        home().interactAuthModal().uncheckPromoMailing();
        home().interactAuthModal().authViaPhone(UserManager.getQaUser());
        home().checkLogoutButtonDisplayed();
    }

    @Metrics
    @TmsLink("2622")
    @Story("Регистрация из корзины")
    @Test(description = "Регистрация при попытке перехода из корзины в чекаут", groups = {STF_PROD_S})
    public void successRegFromCartWithQuantityAndAmountCheck() {
        home().goToPage();
        home().clickToSetAddress();
        home().interactAddressModal().checkYmapsReady();
        home().interactAddressModal().fillAddress(Addresses.Moscow.trainingAddressProd());
        home().interactAddressModal().selectFirstAddress();
        home().interactAddressModal().checkMarkerOnMapInAdviceIsNotVisible();
        home().interactAddressModal().clickFindStores();
        home().interactAddressModal().checkAddressModalIsNotVisible();

        home().clickOnStoreWithSid(UiProperties.DEFAULT_SID);

        shop().waitPageLoad();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().plusItemToCartByPosition(3);

        shop().interactHeader().checkCartNotificationIsVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().increaseFirstItemCountToMin();
        final var orderAmount = shop().interactCart().getOrderAmount();

        shop().interactCart().submitOrder();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());

        checkoutNew().checkSpinnerNotVisible();
        checkoutNew().checkDeliverySlotsVisible();

        final var orderAmountInCheckout = checkoutNew().getOrderAmountDouble();

        checkoutNew().compareOrderAmountAfterRegistration(orderAmount, orderAmountInCheckout);
    }
}
