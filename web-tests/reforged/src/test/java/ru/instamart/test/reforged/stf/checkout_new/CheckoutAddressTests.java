package ru.instamart.test.reforged.stf.checkout_new;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.CookieProvider;
import ru.instamart.reforged.core.config.UiProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.helper.ApiV3Helper.addFlipperActor;
import static ru.instamart.reforged.stf.enums.PaymentMethods.BY_CARD_TO_COURIER;
import static ru.instamart.reforged.stf.enums.ReplacementPolicies.CALL_AND_REPLACE;
import static ru.instamart.reforged.stf.enums.ShipmentStates.ACCEPTED;
import static ru.instamart.reforged.stf.page.StfRouter.*;
import static ru.instamart.reforged.business.page.BusinessRouter.business;

@Epic("STF UI")
@Feature("Чекаут [NEW]")
public final class CheckoutAddressTests {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(3594)
    @Test(description = "Проверка того, что поле 'Кв, офис' является обязательным к заполнению", groups = {"regression", "checkout_web_new"})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckRequiredFields() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());

        this.helper.dropAndFillCartWithoutSetAddress(userData, UiProperties.DEFAULT_METRO_MOSCOW_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

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

        userShipment().checkActiveShipmentState(ACCEPTED.getName());
    }

    @CaseId(3780)
    @Test(description = "Проверка отображения правильного адреса доставки, выбранного ранее", groups = {"regression", "checkout_web_new"})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckRecentlyEnteredAddress() {
        final var userData = UserManager.getQaUser();
        final var address = RestAddresses.Moscow.defaultAddress();
        final var fullAddress = address.getCity() + ", " + address.getStreet() + ", " + address.getBuilding();

        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());

        this.helper.dropAndFillCartWithoutSetAddress(userData, UiProperties.DEFAULT_METRO_MOSCOW_SID);
        this.helper.setAddress(userData, address);

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
    @Test(description = "Проверка появления бабла 'Оплата картой курьеру недоступна'", groups = {"regression", "checkout_web_new"})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckCourierByCardPaymentAlert() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());

        this.helper.dropAndFillCartWithoutSetAddress(userData, UiProperties.DEFAULT_METRO_MOSCOW_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

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
    @Test(description = "Проверка сохранения новоВведенного адреса при перезаходе на чекаут", groups = {"regression", "checkout_web_new"})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckAddressAfterReenterOnCheckout() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());

        this.helper.dropAndFillCartWithoutSetAddress(userData, UiProperties.DEFAULT_METRO_MOSCOW_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

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
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkApartmentValue(apartment);
        checkoutNew().checkEntranceValue(entrance);
        checkoutNew().checkFloorValue(floor);
        checkoutNew().checkDoorPhoneValue(doorPhone);

        checkoutNew().checkDeliveryAddress(addressFromTitle);
    }

    @CaseId(3821)
    @Test(description = "Проверка перехода на см-бизнес через клик по 'Заказываю для бизнеса' в блоке адрес (Доставка)", groups = {"regression", "checkout_web_new"})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testTransitionOnB2BViaCheckoutFromDelivery() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());

        this.helper.dropAndFillCartWithoutSetAddress(userData, UiProperties.DEFAULT_METRO_MOSCOW_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

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
        checkoutNew().interactB2BOrderModal().clickConfirm();

        checkoutNew().switchToNextWindow();
        business().checkPageContains("smbusiness");
    }

    @CaseId(3825)
    @Test(description = "Проверка перехода на см-бизнес через клик по 'Заказываю для бизнеса' в блоке адрес (Самовывоз)", groups = {"regression", "checkout_web_new"})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testTransitionOnB2BViaCheckoutFromPickup() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());

        this.helper.dropAndFillCartWithoutSetAddress(userData, UiProperties.DEFAULT_METRO_MOSCOW_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

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

        checkoutNew().switchToNextWindow();
        business().checkPageContains("smbusiness");
    }

    @CaseId(3823)
    @Test(description = "Проверка отсутствия кнопки 'Заказываю для бизнеса' при отключенной связке с b2b у магазина (Доставка)", groups = {"regression", "checkout_web_new"})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testNoB2BButtonIfStoreHaveNoB2BIntegrationFromDelivery() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());

        this.helper.dropAndFillCartWithoutSetAddress(userData, 20);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutWithoutBusinessAddress());

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
    @Test(description = "Проверка отсутствия кнопки 'Заказываю для бизнеса' при отключенной связке с b2b у магазина (Самовывоз)", groups = {"regression", "checkout_web_new"})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testNoB2BButtonIfStoreHaveNoB2BIntegrationFromPickup() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());

        this.helper.dropAndFillCartWithoutSetAddress(userData, 20);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutWithoutBusinessAddress());

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

        checkoutNew().checkB2BTransitionButtonNotVisible();
    }
}
