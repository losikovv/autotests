package ru.instamart.test.reforged.stf_prod.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.core.config.UiProperties.*;
import static ru.instamart.reforged.stf.enums.PaymentMethods.BY_CARD_TO_COURIER;
import static ru.instamart.reforged.stf.enums.ReplacementPolicies.CALL_AND_REMOVE;
import static ru.instamart.reforged.stf.enums.ShipmentStates.ACCEPTED_STATE;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Покупка товара")
public final class BasicOrdersTests {

    private final ApiHelper helper = new ApiHelper();
    private UserData userData;

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(userData);
    }

    @CaseId(1681)
    @Test(description = "Тест заказа с любимыми товарами", groups = {STF_PROD_S})
    public void successOrderWithFavProducts() {
        userData = UserManager.getQaUser();
        helper.addFavorites(userData, DEFAULT_SID, 5);
        helper.dropAndFillCartFromFavorites(userData, DEFAULT_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().clickToCart();
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

    @CaseId(2623)
    @Story("Отмена заказа")
    @Test(description = "Отмена заказа", groups = {STF_PROD_S})
    public void successOrderCancel() {
        userData = UserManager.getQaUser();
        helper.makeOrder(userData, DEFAULT_SID, 1);
        helper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        userShipments().goToPage();
        userShipments().clickToFirstShipment();
        userShipments().clickToCancelFromOrder();

        userShipments().interactShipmentCancelModal().shipmentCancelModalVisible();
        userShipments().interactShipmentCancelModal().clickToAcceptProd();
        userShipments().interactShipmentCancelModal().shipmentCancelModalNotVisible();

        userShipments().checkStatusWasCanceledProd();
    }

    @CaseId(2624)
    @Story("Заказ")
    @Test(description = "Добавление товаров в активный заказ", groups = {STF_PROD_S})
    public void successAddItemsInActiveOrder() {
        userData = UserManager.getQaUser();

        helper.makeOrderOnTomorrowByQa(userData, DEFAULT_SID, 1);
        helper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        final var itemName = shop().getProductTitleByPositionProd(1);
        shop().plusFirstItemToCartProd();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstRetailer().mergeProducts();
        shop().interactCart().clickToViewOrder();

        userShipments().checkProductListContains(itemName);
    }

    @Skip
    @CaseId(2626)
    @Story("Заказ")
    @Test(description = "Отмена всего мультизаказа при отмене одного из входящих в него заказов", groups = {STF_PROD_S})
    public void successCancelMultiOrderViaCancelOneOrder() {
        userData = UserManager.getQaUser();
        helper.makeMultipleOrderByQa(userData, RestAddresses.Moscow.defaultProdAddress(), DEFAULT_METRO_MOSCOW_SID, DEFAULT_AUCHAN_SID);
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().checkPageContains(userShipments().pageUrl());
        userShipments().clickToFirstShipment();
        userShipments().clickToCancelFromOrder();
        userShipments().interactShipmentCancelModal().shipmentCancelModalVisible();
        userShipments().interactShipmentCancelModal().clickToAcceptProd();
        userShipments().checkStatusWasCanceledProd();

        userShipments().goToPage();
        userShipments().checkAllOrderStatusWasCanceled();
    }
}
