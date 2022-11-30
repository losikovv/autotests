package ru.instamart.test.reforged.stf_prod.checkout_new;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.UiProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.stf.enums.PaymentMethods.BY_CARD_TO_COURIER;
import static ru.instamart.reforged.stf.enums.PaymentMethods.BY_CASH;
import static ru.instamart.reforged.stf.enums.ReplacementPolicies.CALL_AND_REMOVE;
import static ru.instamart.reforged.stf.enums.ShipmentStates.ACCEPTED_STATE;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Чекаут V3")
public final class CheckoutTests {
    private final ApiHelper helper = new ApiHelper();
    private UserData ordersUser;

    @BeforeMethod(alwaysRun = true, description = "Шаги предусловия")
    public void beforeTest() {
        this.ordersUser = UserManager.getQaUser();
        this.helper.dropAndFillCart(ordersUser, UiProperties.DEFAULT_SID);
    }

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        this.helper.cancelAllActiveOrders(ordersUser);
    }

    @CaseId(3624)
    @Test(description = "Тест полного оформления заказа с оплатой картой курьеру (Доставка)", groups = {STF_PROD_S})
    public void testCheckoutCompleteWithPayToCourierAndDelivery() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        var apartmentValue = "1";
        checkoutNew().fillApartment(apartmentValue);
        checkoutNew().checkApartmentValue(apartmentValue);

        checkoutNew().fillComment("test");

        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().checkDeliverySlotDateVisible();
        checkoutNew().checkDeliverySlotDeliveryTimeVisible();
        checkoutNew().checkDeliverySlotPriceVisible();

        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_TO_COURIER.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();

        checkoutNew().checkSelectedPaymentMethodContains(BY_CARD_TO_COURIER.getName());

        checkoutNew().checkContactsSummary(ordersUser.getPhone(), ordersUser.getEmail());

        checkoutNew().clickEditContacts();
        checkoutNew().checkContactsPhone(ordersUser.getPhone());
        checkoutNew().checkContactsEmail(ordersUser.getEmail());

        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REMOVE.getName());
        checkoutNew().checkSelectedReplacementPolicy(CALL_AND_REMOVE.getName());

        checkoutNew().clickConfirmOrder();

        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());
        userShipment().checkShipmentNumberVisible();
        userShipment().checkShippingAddressVisible();
        userShipment().checkAssemblyCostVisible();
        userShipment().checkShipmentCostVisible();
        userShipment().checkTotalCostVisible();
    }

    @CaseId(3840)
    @Test(description = "Тест полного оформления заказа с оплатой наличными (Доставка)", groups = {STF_PROD_S})
    public void testCheckoutCompleteWithCash() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        var apartmentValue = "1";
        checkoutNew().fillApartment(apartmentValue);
        checkoutNew().fillEntrance("");
        checkoutNew().checkApartmentValue(apartmentValue);

        checkoutNew().fillComment("test");

        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().checkDeliverySlotDateVisible();
        checkoutNew().checkDeliverySlotDeliveryTimeVisible();
        checkoutNew().checkDeliverySlotPriceVisible();

        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CASH.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();

        checkoutNew().checkSelectedPaymentMethodContains(BY_CASH.getName());

        checkoutNew().checkContactsSummary(ordersUser.getPhone(), ordersUser.getEmail());

        checkoutNew().clickEditContacts();
        checkoutNew().checkContactsPhone(ordersUser.getPhone());
        checkoutNew().checkContactsEmail(ordersUser.getEmail());

        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REMOVE.getName());
        checkoutNew().checkSelectedReplacementPolicy(CALL_AND_REMOVE.getName());

        checkoutNew().clickConfirmOrder();

        userShipment().checkPageContains(userShipments().pageUrl());
        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());
        userShipment().checkShipmentNumberVisible();
        userShipment().checkShippingAddressVisible();
        userShipment().checkAssemblyCostVisible();
        userShipment().checkShipmentCostVisible();
        userShipment().checkTotalCostVisible();
    }
}
