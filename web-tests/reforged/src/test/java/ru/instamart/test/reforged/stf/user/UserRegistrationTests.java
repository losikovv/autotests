package ru.instamart.test.reforged.stf.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import ru.sbermarket.qase.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Регистрация пользователя")
public final class UserRegistrationTests extends BaseTest {

    @CaseId(1552)
    @Story("Регистрация на странице ретейлера")
    @Test(description = "Негативный тест попытки зарегистрировать пользователя с пустыми реквизитами", groups = {"acceptance", "regression"})
    public void noRegWithEmptyRequisites() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().fillPhone("");
        shop().interactAuthModal().sendSms();
        shop().interactAuthModal().checkPhoneErrorIsVisible();
    }

    @Skip
    @CaseId(1541)
    @Story("Регистрация на лендинге")
    @Test(description = "Регистрация нового пользователя на лендинге", groups = {"acceptance", "smoke", "MRAutoCheck"})
    public void successRegOnLanding() {
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(1543)
    @Story("Регистрация на странице ретейлера")
    @Test(description = "Регистрация нового пользователя на витрине магазина", groups = {"acceptance", "regression"})
    public void successRegOnMainPage() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(1542)
    @Story("Регистрация на странице ретейлера")
    @Test(description = "Тест регистрации из адресной модалки феникса", groups = {"regression", "acceptance"})
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
        shop().plusFirstItemToCart();

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().increaseCountToMin();
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
}
