package ru.instamart.test.reforged.stf.checkout_new;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.annotation.CookieProvider;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_CHECKOUT_SID;
import static ru.instamart.reforged.Group.*;
import static ru.instamart.reforged.stf.enums.PaymentMethods.BY_CARD_TO_COURIER;
import static ru.instamart.reforged.stf.enums.ReplacementPolicies.CALL_AND_REMOVE;
import static ru.instamart.reforged.stf.enums.ReplacementPolicies.NOT_SELECTED;
import static ru.instamart.reforged.stf.page.StfRouter.checkoutNew;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Чекаут V3")
public final class CheckoutContactsAndReplacementPolicyTests {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(3691)
    @Story("Контакты и замены")
    @Test(description = "Проверка отсутствия предвыбранного способа замены товара при первом чекауте", groups = {REGRESSION_STF, SMOKE_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckReplacementPolicyNotSelectedFirstTime() {
        final var userData = UserManager.getQaUser();
        helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();
        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().checkSelectedReplacementPolicy(NOT_SELECTED.getName());
    }

    @CaseId(3632)
    @Story("Контакты и замены")
    @Test(description = "Проверка что поле 'Замена товара' является обязательным при первом заказе", groups = {REGRESSION_STF, SMOKE_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckReplacementPolicyRequired() {
        final var userData = UserManager.getQaUser();
        helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();
        checkoutNew().checkSpinnerNotVisible();

        var apartmentValue = "1";
        checkoutNew().fillApartment(apartmentValue);

        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_TO_COURIER.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();

        checkoutNew().clickConfirmOrder();
        checkoutNew().checkSelectedReplacementPolicyInvalid();
        checkoutNew().checkSelectedReplacementPolicy(NOT_SELECTED.getName());
        checkoutNew().checkReplacementPolicyErrorDescriptionVisible();
        checkoutNew().checkReplacementPolicyErrorDescriptionContains("Выберите вариант замены");
    }

    @CaseId(3631)
    @Story("Контакты и замены")
    @Test(description = "Проверка что значение поля 'Замена товара' предвыбрано при повторных чекаутах", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckRequiredFields() {
        final var userData = UserManager.getQaUser();
        var order = helper.makeOrder(userData, DEFAULT_CHECKOUT_SID, 1);
        helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();
        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().checkSelectedReplacementPolicyInCollapsedBlock(order.getReplacementPolicy().getDescription());

        checkoutNew().clickEditReplacementPolicy();
        checkoutNew().checkSelectedReplacementPolicy(order.getReplacementPolicy().getDescription());

        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REMOVE.getName());
        checkoutNew().checkSelectedReplacementPolicy(CALL_AND_REMOVE.getName());
    }


    @CaseId(3628)
    @Story("Контакты и замены")
    @Test(description = "Проверка отображения в блоке Контакты номера телефона из профиля при первом заказе", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckContactsFillFromProfile() {
        final var userData = UserManager.getQaUser();
        helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();
        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().checkContactsSummary(userData.getPhone(), userData.getEmail());

        checkoutNew().clickEditContacts();
        checkoutNew().checkContactsPhone(userData.getPhone());
        checkoutNew().checkContactsEmail(userData.getEmail());
    }

    @CaseId(3629)
    @Story("Контакты и замены")
    @Test(description = "Проверка что поля телефон и e-mail обязательны и валидируются при потере фокуса", groups = {REGRESSION_STF, SMOKE_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckPhoneAndEmailRequiredFields() {
        final var userData = UserManager.getQaUser();
        helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();
        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().clickEditContacts();
        checkoutNew().clearPhone();
        checkoutNew().clickAddressTitle();

        checkoutNew().checkPhoneInvalid();
        checkoutNew().checkPhoneErrorDescriptionVisible();
        checkoutNew().checkPhoneErrorDescriptionContains("Обязательное поле");

        checkoutNew().fillPhone(userData.getPhone().substring(0, 5));
        checkoutNew().clickAddressTitle();
        checkoutNew().checkPhoneInvalid();
        checkoutNew().checkPhoneErrorDescriptionVisible();
        checkoutNew().checkPhoneErrorDescriptionContains("Минимальная длина 11 цифр");

        checkoutNew().clearEmail();

        checkoutNew().clickAddressTitle();
        checkoutNew().checkEmailInvalid();
        checkoutNew().checkEmailErrorDescriptionVisible();
        checkoutNew().checkEmailErrorDescriptionContains("Обязательное поле");

        checkoutNew().fillEmail(userData.getEmail().substring(0, 5));
        checkoutNew().clickAddressTitle();
        checkoutNew().checkEmailInvalid();
        checkoutNew().checkEmailErrorDescriptionVisible();
        checkoutNew().checkEmailErrorDescriptionContains("E-mail написан неверно");
    }
}
