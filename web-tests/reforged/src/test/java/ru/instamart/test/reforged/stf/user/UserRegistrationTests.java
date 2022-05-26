package ru.instamart.test.reforged.stf.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import ru.sbermarket.qase.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Регистрация пользователя")
public final class UserRegistrationTests {

    @CaseId(1552)
    @Story("Регистрация на странице ретейлера")
    @Test(description = "Негативный тест попытки зарегистрировать пользователя с пустыми реквизитами", groups = "regression")
    public void noRegWithEmptyRequisites() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().fillPhone("");
        shop().interactAuthModal().sendSms();
        shop().interactAuthModal().checkPhoneErrorIsVisible();
    }

    @CaseId(1541)
    @Story("Регистрация на лендинге")
    @Test(description = "Регистрация нового пользователя на лендинге", groups = {"production", "smoke", "MRAutoCheck"})
    public void successRegOnLanding() {
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(UserManager.getQaUser());
        home().checkAuthoredBlockVisible();
    }

    @CaseId(1543)
    @Story("Регистрация на странице ретейлера")
    @Test(description = "Регистрация нового пользователя на витрине магазина", groups = "regression")
    public void successRegOnMainPage() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(1542)
    @Story("Регистрация на странице ретейлера")
    @Test(description = "Тест регистрации из адресной модалки феникса", groups = "regression")
    public void successRegFromAddressModal() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(748)
    @Story("Регистрация на странице ретейлера")
    @Test(description = "Тест регистрации при переходе из корзины в чекаут", groups = "regression")
    public void successRegFromCart() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().plusItemToCart("1", "0");

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().increaseFirstItemCountToMin();
        shop().interactCart().submitOrder();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        checkout().checkCheckoutButtonIsVisible();
    }

    @CaseId(1545)
    @Story("Регистрация на странице ретейлера")
    @Test(description = "Тест успешной регистрации без проставленной галки Получать выгодные предложения", groups = "regression" )
    public void successRegWithoutMailingCheckbox() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().uncheckPromoMailing();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(2622)
    @Story("Регистрация из корзины")
    @Test(description = "Регистрация при попытке перехода из корзины в чекаут", groups = "regression")
    public void successRegFromCartWithQuantityAndAmountCheck() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().plusItemToCart("1", "0");

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().increaseFirstItemCountToMin();
        final var orderAmount = shop().interactCart().getOrderAmount();
        final var positionsCount = shop().interactCart().getFirstRetailer().getItemsCountInHeader();

        shop().interactCart().submitOrder();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        checkout().checkCheckoutButtonIsVisible();

        final var orderAmountInCheckout = checkout().getOrderAmount();
        final var positionsCountInCheckout = checkout().getPositionsCount();

        checkout().compareOrderAmountAfterRegistration(orderAmount, orderAmountInCheckout);
        checkout().comparePositionCountAfterRegistration(positionsCount, positionsCountInCheckout);

        checkout().assertAll();
    }
}
