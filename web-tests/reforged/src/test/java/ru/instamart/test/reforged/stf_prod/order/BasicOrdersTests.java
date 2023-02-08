package ru.instamart.test.reforged.stf_prod.order;

import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.core.config.UiProperties.*;
import static ru.instamart.reforged.stf.enums.PaymentMethods.BY_CARD_TO_COURIER;
import static ru.instamart.reforged.stf.enums.ReplacementPolicies.CALL_AND_REMOVE;
import static ru.instamart.reforged.stf.enums.ShipmentStates.ACCEPTED_STATE;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Покупка товара")
public final class BasicOrdersTests {

    private static final ThreadLocal<UserData> ordersUser = new ThreadLocal<>();
    private final ApiHelper helper = new ApiHelper();

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(ordersUser.get());
    }

    @TmsLink("1681")
    @Test(description = "Тест заказа с любимыми товарами", groups = {STF_PROD_S})
    public void successOrderWithFavProducts() {
        ordersUser.set(UserManager.getQaUser());
        helper.addFavorites(ordersUser.get(), DEFAULT_SID, 5);
        helper.dropAndFillCartFromFavorites(ordersUser.get(), DEFAULT_SID);

        shop().goToPageWithAuth(DEFAULT_SID, ordersUser.get());
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

    @TmsLink("2623")
    @Issue("B2C-12077")
    @Story("Отмена заказа")
    @Test(description = "Отмена заказа", groups = {STF_PROD_S})
    public void successOrderCancel() {
        ordersUser.set(UserManager.getQaUser());
        helper.makeOrder(ordersUser.get(), DEFAULT_SID, 1);
        helper.setAddress(ordersUser.get(), RestAddresses.Moscow.defaultProdAddress());

        shop().goToPageWithAuth(DEFAULT_SID, ordersUser.get());
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

    @TmsLink("2624")
    @Story("Заказ")
    @Test(description = "Добавление товаров в активный заказ", groups = {STF_PROD_S})
    public void successAddItemsInActiveOrder() {
        ordersUser.set(UserManager.getQaUser());
        helper.makeOrderOnTomorrowByQa(ordersUser.get(), DEFAULT_SID, 1);
        helper.setAddress(ordersUser.get(), RestAddresses.Moscow.defaultProdAddress());

        shop().goToPageWithAuth(DEFAULT_SID, ordersUser.get());
        shop().interactHeader().checkProfileButtonVisible();

        final var itemName = shop().getProductTitleByPosition(1);
        shop().plusFirstItemToCart();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().goToPage(DEFAULT_SID);
        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstRetailer().mergeProducts();
        shop().interactCart().clickToViewOrder();

        userShipments().checkProductListContains(itemName);
    }

    @Skip
    @TmsLink("2626")
    @Story("Заказ")
    @Test(description = "Отмена всего мультизаказа при отмене одного из входящих в него заказов", groups = {STF_PROD_S})
    public void successCancelMultiOrderViaCancelOneOrder() {
        ordersUser.set(UserManager.getQaUser());
        helper.makeMultipleOrderByQa(ordersUser.get(), RestAddresses.Moscow.defaultProdAddress(), DEFAULT_METRO_MOSCOW_SID, DEFAULT_AUCHAN_SID);
        shop().goToPageWithAuth(DEFAULT_SID, ordersUser.get());
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
