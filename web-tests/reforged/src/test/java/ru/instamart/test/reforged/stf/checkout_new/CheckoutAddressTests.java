package ru.instamart.test.reforged.stf.checkout_new;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.annotation.CookieProvider;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_CHECKOUT_SID;
import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_NOT_B2B_SID;
import static ru.instamart.reforged.Group.*;
import static ru.instamart.reforged.stf.enums.PaymentMethods.BY_BUSINESS_ACCOUNT;
import static ru.instamart.reforged.stf.enums.PaymentMethods.BY_CARD_TO_COURIER;
import static ru.instamart.reforged.stf.enums.ReplacementPolicies.CALL_AND_REPLACE;
import static ru.instamart.reforged.stf.enums.ShipmentStates.ACCEPTED_STATE;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Чекаут V3")
public final class CheckoutAddressTests {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(3594)
    @Story("Адрес")
    @Test(description = "Проверка того, что поле 'Кв, офис' является обязательным к заполнению", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckRequiredFields() {
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

        checkoutNew().checkDeliverySlotsVisible();

        checkoutNew().clickConfirmPay();
        checkoutNew().checkApartmentNumberAlertVisible();

        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_TO_COURIER.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();

        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REPLACE.getName());

        checkoutNew().clickConfirmOrder();
        checkoutNew().checkApartmentNumberAlertVisible();

        checkoutNew().fillApartment("123");
        checkoutNew().clickConfirmOrder();

        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());
    }

    @CaseId(3780)
    @Story("Адрес")
    @Test(description = "Проверка отображения правильного адреса доставки, выбранного ранее", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckRecentlyEnteredAddress() {
        final var userData = UserManager.getQaUser();
        final var address = RestAddresses.Moscow.checkoutAddress();
        final var fullAddress = address.getCity() + ", " + address.getStreet() + ", " + address.getBuilding();

        helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        helper.setAddress(userData, address);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().checkDeliveryAddress(fullAddress);
    }

    @CaseId(3796)
    @Story("Адрес")
    @Test(description = "Проверка появления бабла 'Оплата картой курьеру недоступна'", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckCourierByCardPaymentAlert() {
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

        checkoutNew().checkDeliverySlotsVisible();

        checkoutNew().clickConfirmPay();
        checkoutNew().checkApartmentNumberAlertVisible();

        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_TO_COURIER.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();

        checkoutNew().clickDeliveryToDoor();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().checkCardToCourierBubbleVisible();
    }

    @CaseId(3820)
    @Story("Адрес")
    @Test(description = "Проверка сохранения новоВведенного адреса при перезаходе на чекаут", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckAddressAfterReenterOnCheckout() {
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

        var apartment = "7";
        var entrance = "3";
        var floor = "11";
        var doorPhone = "777";

        checkoutNew().fillApartment(apartment);
        checkoutNew().fillEntrance(entrance);
        checkoutNew().fillFloor(floor);
        checkoutNew().fillDoorPhone(doorPhone);

        checkoutNew().checkSpinnerVisible();
        checkoutNew().checkSpinnerNotVisible();

        var addressFromTitle = checkoutNew().getAddressFromTitle();

        shop().goToPage();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkDeliverySlotsVisible();

        checkoutNew().checkApartmentValue(apartment);
        checkoutNew().checkEntranceValue(entrance);
        checkoutNew().checkFloorValue(floor);
        checkoutNew().checkDoorPhoneValue(doorPhone);

        checkoutNew().checkDeliveryAddress(addressFromTitle);
    }

    @CaseId(3821)
    @Story("Адрес")
    @Test(description = "Проверка перехода на см-бизнес через клик по 'Заказываю для бизнеса' в блоке адрес (Доставка)", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testTransitionOnB2BViaCheckoutFromDelivery() {
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

        checkoutNew().clickOrderForBusiness();
        checkoutNew().interactB2BOrderModal().checkModalVisible();
        checkoutNew().interactB2BOrderModal().clickClose();
        checkoutNew().interactB2BOrderModal().checkModalNotVisible();

        checkoutNew().clickOrderForBusiness();
        checkoutNew().interactB2BOrderModal().checkModalVisible();
        checkoutNew().interactB2BOrderModal().clickConfirm();
        checkoutNew().interactB2BOrderModal().checkModalNotVisible();

        checkout().waitPageLoad();
        checkout().checkCheckoutButtonIsVisible();
    }

    @CaseId(3825)
    @Story("Адрес")
    @Test(description = "Проверка перехода на см-бизнес через клик по 'Заказываю для бизнеса' в блоке адрес (Самовывоз)", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testTransitionOnB2BViaCheckoutFromPickup() {
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

        checkoutNew().switchToPickup();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().clickOrderForBusiness();
        checkoutNew().interactB2BOrderModal().checkModalVisible();
        checkoutNew().interactB2BOrderModal().clickClose();
        checkoutNew().interactB2BOrderModal().checkModalNotVisible();

        checkoutNew().clickOrderForBusiness();
        checkoutNew().interactB2BOrderModal().clickConfirm();
        checkoutNew().interactB2BOrderModal().checkModalNotVisible();

        checkout().waitPageLoad();
        checkout().checkCheckoutButtonIsVisible();
    }

    @CaseId(3823)
    @Story("Адрес")
    @Test(description = "Проверка отсутствия кнопки 'Заказываю для бизнеса' при отключенной связке с b2b у магазина (Доставка)", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testNoB2BButtonIfStoreHaveNoB2BIntegrationFromDelivery() {
        final var userData = UserManager.getQaUser();

        helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_NOT_B2B_SID);
        helper.setAddress(userData, RestAddresses.Moscow.checkoutWithoutBusinessAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().checkB2BTransitionButtonNotVisible();
    }

    @CaseId(3826)
    @Story("Адрес")
    @Test(description = "Проверка отсутствия кнопки 'Заказываю для бизнеса' при отключенной связке с b2b у магазина (Самовывоз)", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testNoB2BButtonIfStoreHaveNoB2BIntegrationFromPickup() {
        final var userData = UserManager.getQaUser();

        helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_NOT_B2B_SID);
        helper.setAddress(userData, RestAddresses.Moscow.checkoutWithoutBusinessAddress());

        shop().goToPage(ShopUrl.METRO);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().switchToPickup();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().checkB2BTransitionButtonNotVisible();
    }

    @CaseId(3941)
    @Story("Адрес")
    @Test(description = "Проверка перехода на v1 чек-аут через способо оплаты 'По счету для бизнеса' (Доставка)", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckTransitionV1CheckoutIfBusinessPaymentMethodSelected() {
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

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_BUSINESS_ACCOUNT.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();

        checkoutNew().clickOrderForBusiness();
        checkoutNew().interactB2BOrderModal().clickConfirm();
        checkoutNew().interactB2BOrderModal().checkModalNotVisible();

        checkout().waitPageLoad();
        checkout().checkCheckoutButtonIsVisible();
    }
}
