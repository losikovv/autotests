package ru.instamart.test.reforged.stf.checkout_new;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.PaymentCards;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.annotation.CookieProvider;
import ru.instamart.reforged.core.enums.ShopUrl;

import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_CHECKOUT_SID;
import static ru.instamart.reforged.Group.*;
import static ru.instamart.reforged.core.config.UiProperties.ALCOHOL_CATEGORY_LINK;
import static ru.instamart.reforged.stf.enums.PaymentMethods.*;
import static ru.instamart.reforged.stf.enums.ReplacementPolicies.CALL_AND_REMOVE;
import static ru.instamart.reforged.stf.enums.ShipmentStates.ACCEPTED_STATE;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Чекаут V3")
public final class CheckoutTests {

    private final ApiHelper helper = new ApiHelper();

    @TmsLink("3595")
    @Test(description = "Выбранный способ 'Доставка'/'Самовывоз' сохраняется если открыть страницу в новой вкладке", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testPaymentMethodSaveIfOpenCheckoutInNewTab() {
        final var userData = UserManager.getQaUser();
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);

        final var fullAddress = RestAddresses.Moscow.checkoutAddress().fullAddress().toString();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToPickup();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().clickShowAsList();
        shop().interactAddressLarge().checkPickupStoresModalVisible();
        shop().interactAddressLarge().selectRetailerByName("METRO");
        shop().interactAddressLarge().selectStoreByAddress(fullAddress);
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().increaseFirstItemCountToMin();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().openInNewTab(Kraken.currentUrl());

        checkoutNew().switchToNextWindow();

        checkoutNew().checkPickupTabOpened();
        checkoutNew().checkPickupStoreAddress(fullAddress);
    }

    @Issues({@Issue("B2C-9732"), @Issue("B2C-9730")})
    @TmsLink("3623")
    @Test(description = "Тест полного оформления заказа с оплатой картой онлайн (Доставка)", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckoutCompleteWithOnlinePaymentAndDelivery() {
        final var userData = UserManager.getQaUser();
        var card = PaymentCards.testCardNo3ds();
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

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
        checkoutNew().fillEntrance("");
        checkoutNew().checkApartmentValue(apartmentValue);

        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().checkDeliverySlotDateVisible();
        checkoutNew().checkDeliverySlotDeliveryTimeVisible();
        checkoutNew().checkDeliverySlotPriceVisible();

        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_ONLINE.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactAddPaymentCardModal().checkModalVisible();

        checkoutNew().interactAddPaymentCardModal().fillCardData(card);
        checkoutNew().interactAddPaymentCardModal().clickAdd();
        //B2C-9732

        checkoutNew().interactAddPaymentCardModal().checkModalNotVisible();
        checkoutNew().checkSelectedPaymentMethodContains(card.getCardNumber().substring(card.getCardNumber().length() - 4));

        checkoutNew().checkContactsSummary(userData.getPhone(), userData.getEmail());

        checkoutNew().clickEditContacts();
        checkoutNew().checkContactsPhone(userData.getPhone());
        checkoutNew().checkContactsEmail(userData.getEmail());

        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REMOVE.getName());
        checkoutNew().checkSelectedReplacementPolicy(CALL_AND_REMOVE.getName());

        checkoutNew().clickConfirmPay();
        //B2C-9730

        userShipment().checkPageContains(userShipments().pageUrl());
        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());
        userShipment().checkShipmentNumberVisible();
        //userShipment().checkShippingAddressVisible();
        //userShipment().checkShipmentCostVisible();
        userShipment().checkTotalCostVisible();
    }

    @TmsLink("3624")
    @Test(description = "Тест полного оформления заказа с оплатой картой курьеру (Доставка)", groups = {REGRESSION_STF, SMOKE_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckoutCompleteWithPayToCourierAndDelivery() {
        final var userData = UserManager.getQaUser();
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

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
        checkoutNew().checkApartmentValue(apartmentValue);

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

        checkoutNew().checkContactsSummary(userData.getPhone(), userData.getEmail());

        checkoutNew().clickEditContacts();
        checkoutNew().checkContactsPhone(userData.getPhone());
        checkoutNew().checkContactsEmail(userData.getEmail());

        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REMOVE.getName());
        checkoutNew().checkSelectedReplacementPolicy(CALL_AND_REMOVE.getName());

        checkoutNew().clickConfirmOrder();

        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());
        userShipment().checkShipmentNumberVisible();
        userShipment().checkShippingAddressVisible();
        //userShipment().checkAssemblyCostVisible();
        //userShipment().checkShipmentCostVisible();
        userShipment().checkTotalCostVisible();
    }

    @TmsLink("3839")
    @Test(description = "Тест полного оформления заказа с оплатой 'Новой картой онлайн' (Доставка)", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckoutCompleteWithNewCard() {
        final var userData = UserManager.getQaUser();
        var card = PaymentCards.testCardNo3ds();
        var cardNew = PaymentCards.testCardNo3dsWithSpasibo();
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

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
        checkoutNew().fillEntrance("");
        checkoutNew().checkApartmentValue(apartmentValue);

        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().checkDeliverySlotDateVisible();
        checkoutNew().checkDeliverySlotDeliveryTimeVisible();
        checkoutNew().checkDeliverySlotPriceVisible();

        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_ONLINE.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactAddPaymentCardModal().checkModalVisible();

        checkoutNew().interactAddPaymentCardModal().fillCardData(card);
        checkoutNew().interactAddPaymentCardModal().clickAdd();

        checkoutNew().interactAddPaymentCardModal().checkModalNotVisible();
        checkoutNew().checkSelectedPaymentMethodContains(card.getCardNumber().substring(card.getCardNumber().length() - 4));

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_NEW_CARD_ONLINE.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactAddPaymentCardModal().checkModalVisible();

        checkoutNew().interactAddPaymentCardModal().fillCardData(cardNew);
        checkoutNew().interactAddPaymentCardModal().clickAdd();

        checkoutNew().interactAddPaymentCardModal().checkModalNotVisible();
        checkoutNew().checkSelectedPaymentMethodContains(cardNew.getCardNumber().substring(cardNew.getCardNumber().length() - 4));

        checkoutNew().checkContactsSummary(userData.getPhone(), userData.getEmail());

        checkoutNew().clickEditContacts();
        checkoutNew().checkContactsPhone(userData.getPhone());
        checkoutNew().checkContactsEmail(userData.getEmail());

        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REMOVE.getName());
        checkoutNew().checkSelectedReplacementPolicy(CALL_AND_REMOVE.getName());

        checkoutNew().clickConfirmPay();

        userShipment().checkPageContains(userShipments().pageUrl());
        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());
        userShipment().checkShipmentNumberVisible();
        userShipment().checkShippingAddressVisible();
        //userShipment().checkAssemblyCostVisible();
        //userShipment().checkShipmentCostVisible();
        userShipment().checkTotalCostVisible();
    }

    @TmsLink("3838")
    @Test(description = "Тест полного оформления заказа с оплатой бонусами от СберСпасибо (Доставка)", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckoutCompleteWithSberSpasibo() {
        final var userData = UserManager.getQaUser();
        var card = PaymentCards.testCardNo3dsWithSpasibo();
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

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
        checkoutNew().fillEntrance("");
        checkoutNew().checkApartmentValue(apartmentValue);

        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().checkDeliverySlotDateVisible();
        checkoutNew().checkDeliverySlotDeliveryTimeVisible();
        checkoutNew().checkDeliverySlotPriceVisible();

        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);

        var bonusesAmount = "1";
        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(SBERSPASIBO_BONUSES.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();

        checkoutNew().interactSberSpasiboModal().checkModalVisible();
        checkoutNew().interactSberSpasiboModal().fillCardData(card);
        checkoutNew().interactSberSpasiboModal().fillBonusesAmount(bonusesAmount);
        checkoutNew().interactSberSpasiboModal().clickWithdraw();

        checkoutNew().interactSberSpasiboModal().checkBonusesFieldDisplayed();

        checkoutNew().interactSberSpasiboModal().fillBonusesAmount(bonusesAmount);
        checkoutNew().interactSberSpasiboModal().clickWithdraw();

        checkoutNew().interactSberSpasiboModal().checkModalNotVisible();
        checkoutNew().checkSelectedPaymentMethodContains(SBERSPASIBO_BONUSES.getName());
        checkoutNew().checkSberSpasiboWidgetText("Спишем " +
                bonusesAmount
                + " бонусов с карты •••• " +
                card.getCardNumber().substring(card.getCardNumber().length() - 4));
        checkoutNew().checkSberSpasiboInOrderSummaryVisible();
        checkoutNew().checkSberSpasiboAmountInOrderSummary(String.format("-%s ₽", bonusesAmount));

        checkoutNew().checkContactsSummary(userData.getPhone(), userData.getEmail());

        checkoutNew().clickEditContacts();
        checkoutNew().checkContactsPhone(userData.getPhone());
        checkoutNew().checkContactsEmail(userData.getEmail());

        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REMOVE.getName());
        checkoutNew().checkSelectedReplacementPolicy(CALL_AND_REMOVE.getName());

        checkoutNew().clickConfirmPay();

        userShipment().checkPageContains(userShipments().pageUrl());
        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());
        userShipment().checkShipmentNumberVisible();
        //userShipment().checkShippingAddressVisible();
        //userShipment().checkAssemblyCostVisible();
        userShipment().checkTotalCostVisible();
    }

    @TmsLink("3840")
    @Test(description = "Тест полного оформления заказа с оплатой наличными (Доставка)", groups = {REGRESSION_STF, SMOKE_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckoutCompleteWithCash() {
        final var userData = UserManager.getQaUser();
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

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
        checkoutNew().fillEntrance("");
        checkoutNew().checkApartmentValue(apartmentValue);

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

        checkoutNew().checkContactsSummary(userData.getPhone(), userData.getEmail());

        checkoutNew().clickEditContacts();
        checkoutNew().checkContactsPhone(userData.getPhone());
        checkoutNew().checkContactsEmail(userData.getEmail());

        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REMOVE.getName());
        checkoutNew().checkSelectedReplacementPolicy(CALL_AND_REMOVE.getName());

        checkoutNew().clickConfirmOrder();

        userShipment().checkPageContains(userShipments().pageUrl());
        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());
        userShipment().checkShipmentNumberVisible();
        userShipment().checkShippingAddressVisible();
        //userShipment().checkAssemblyCostVisible();
        //userShipment().checkShipmentCostVisible();
        userShipment().checkTotalCostVisible();
    }

    @TmsLink("3647")
    @Test(description = "Тест полного оформления заказа с оплатой Картой онлайн (Cамовывоз | Только продукты)", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckoutCompleteWithOnlinePaymentAndPickup() {
        final var userData = UserManager.getQaUser();
        var card = PaymentCards.testCardNo3ds();
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

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

        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().checkDeliverySlotDateVisible();
        checkoutNew().checkDeliverySlotDeliveryTimeVisible();
        checkoutNew().checkDeliverySlotPriceVisible();

        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_ONLINE.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactAddPaymentCardModal().checkModalVisible();

        checkoutNew().interactAddPaymentCardModal().fillCardData(card);
        checkoutNew().interactAddPaymentCardModal().clickAdd();
        //B2C-9732

        checkoutNew().interactAddPaymentCardModal().checkModalNotVisible();
        checkoutNew().checkSelectedPaymentMethodContains(card.getCardNumber().substring(card.getCardNumber().length() - 4));

        checkoutNew().checkContactsSummary(userData.getPhone(), userData.getEmail());

        checkoutNew().clickEditContacts();
        checkoutNew().checkContactsPhone(userData.getPhone());
        checkoutNew().checkContactsEmail(userData.getEmail());

        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REMOVE.getName());
        checkoutNew().checkSelectedReplacementPolicy(CALL_AND_REMOVE.getName());

        checkoutNew().clickConfirmPay();

        userShipment().checkPageContains(userShipments().pageUrl());
        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());
        userShipment().checkShipmentNumberVisible();
        userShipment().checkShippingAddressVisible();
        userShipment().checkAssemblyCostVisible();
        userShipment().checkShipmentCostNotVisible();
        userShipment().checkTotalCostVisible();
    }

    @TmsLink("3648")
    @Test(description = "Тест полного оформления заказа с оплатой Картой на кассе (Самовывоз | Только продукты)", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckoutCompleteWithPayOnCashDeskAndPickup() {
        final var userData = UserManager.getQaUser();
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

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

        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().checkDeliverySlotDateVisible();
        checkoutNew().checkDeliverySlotDeliveryTimeVisible();
        checkoutNew().checkDeliverySlotPriceVisible();

        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_AT_CHECKOUT.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();

        checkoutNew().checkSelectedPaymentMethodContains(BY_CARD_AT_CHECKOUT.getName());

        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REMOVE.getName());
        checkoutNew().checkSelectedReplacementPolicy(CALL_AND_REMOVE.getName());

        checkoutNew().clickConfirmOrder();

        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());
        userShipment().checkShipmentNumberVisible();
        userShipment().checkShippingAddressVisible();
        userShipment().checkAssemblyCostVisible();
        userShipment().checkTotalCostVisible();
    }

    @TmsLink("3841")
    @Test(description = "Тест полного оформления заказа с оплатой 'Новой картой онлайн' (Самовывоз | Только продукты)", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckoutCompleteWithNewCardAndPickup() {
        final var userData = UserManager.getQaUser();
        var card = PaymentCards.testCardNo3ds();
        var cardNew = PaymentCards.testCardNo3dsWithSpasibo();
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

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

        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().checkDeliverySlotDateVisible();
        checkoutNew().checkDeliverySlotDeliveryTimeVisible();
        checkoutNew().checkDeliverySlotPriceVisible();

        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_ONLINE.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactAddPaymentCardModal().checkModalVisible();

        checkoutNew().interactAddPaymentCardModal().fillCardData(card);
        checkoutNew().interactAddPaymentCardModal().clickAdd();

        checkoutNew().interactAddPaymentCardModal().checkModalNotVisible();
        checkoutNew().checkSelectedPaymentMethodContains(card.getCardNumber().substring(card.getCardNumber().length() - 4));

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_NEW_CARD_ONLINE.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactAddPaymentCardModal().checkModalVisible();

        checkoutNew().interactAddPaymentCardModal().fillCardData(cardNew);
        checkoutNew().interactAddPaymentCardModal().clickAdd();

        checkoutNew().interactAddPaymentCardModal().checkModalNotVisible();
        checkoutNew().checkSelectedPaymentMethodContains(cardNew.getCardNumber().substring(cardNew.getCardNumber().length() - 4));

        checkoutNew().checkContactsSummary(userData.getPhone(), userData.getEmail());

        checkoutNew().clickEditContacts();
        checkoutNew().checkContactsPhone(userData.getPhone());
        checkoutNew().checkContactsEmail(userData.getEmail());

        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REMOVE.getName());
        checkoutNew().checkSelectedReplacementPolicy(CALL_AND_REMOVE.getName());

        checkoutNew().clickConfirmPay();

        userShipment().checkPageContains(userShipments().pageUrl());
        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());
        userShipment().checkShipmentNumberVisible();
        userShipment().checkShippingAddressVisible();
        userShipment().checkAssemblyCostVisible();
        userShipment().checkTotalCostVisible();
    }

    @TmsLink("3842")
    @Test(description = "Тест полного оформления заказа с оплатой бонусами от СберСпасибо (Самовывоз | Только продукты)", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckoutCompleteWithSberSpasiboAndPickup() {
        final var userData = UserManager.getQaUser();
        var card = PaymentCards.testCardNo3dsWithSpasibo();
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

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

        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().checkDeliverySlotDateVisible();
        checkoutNew().checkDeliverySlotDeliveryTimeVisible();
        checkoutNew().checkDeliverySlotPriceVisible();

        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);

        var bonusesAmount = "1";
        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(SBERSPASIBO_BONUSES.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();

        checkoutNew().interactSberSpasiboModal().checkModalVisible();
        checkoutNew().interactSberSpasiboModal().fillCardData(card);
        checkoutNew().interactSberSpasiboModal().fillBonusesAmount(bonusesAmount);
        checkoutNew().interactSberSpasiboModal().clickWithdraw();

        checkoutNew().interactSberSpasiboModal().checkBonusesFieldDisplayed();

        checkoutNew().interactSberSpasiboModal().fillBonusesAmount(bonusesAmount);
        checkoutNew().interactSberSpasiboModal().clickWithdraw();

        checkoutNew().interactSberSpasiboModal().checkModalNotVisible();
        checkoutNew().checkSelectedPaymentMethodContains(SBERSPASIBO_BONUSES.getName());
        checkoutNew().checkSberSpasiboWidgetText("Спишем " +
                bonusesAmount
                + " бонусов с карты •••• " +
                card.getCardNumber().substring(card.getCardNumber().length() - 4));
        checkoutNew().checkSberSpasiboInOrderSummaryVisible();
        checkoutNew().checkSberSpasiboAmountInOrderSummary(String.format("-%s ₽", bonusesAmount));

        checkoutNew().checkContactsSummary(userData.getPhone(), userData.getEmail());

        checkoutNew().clickEditContacts();
        checkoutNew().checkContactsPhone(userData.getPhone());
        checkoutNew().checkContactsEmail(userData.getEmail());

        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REMOVE.getName());
        checkoutNew().checkSelectedReplacementPolicy(CALL_AND_REMOVE.getName());

        checkoutNew().clickConfirmPay();

        userShipment().checkPageContains(userShipments().pageUrl());
        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());
        userShipment().checkShipmentNumberVisible();
        userShipment().checkShippingAddressVisible();
        //userShipment().checkAssemblyCostVisible();
        userShipment().checkTotalCostVisible();
    }

    @TmsLink("3622")
    @Test(description = "Тест полного оформления заказа с оплатой Наличными (Самовывоз | Только продукты)", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckoutCompleteWithCashAndPickup() {
        final var userData = UserManager.getQaUser();
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

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

        checkoutNew().checkContactsSummary(userData.getPhone(), userData.getEmail());

        checkoutNew().clickEditContacts();
        checkoutNew().checkContactsPhone(userData.getPhone());
        checkoutNew().checkContactsEmail(userData.getEmail());

        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REMOVE.getName());
        checkoutNew().checkSelectedReplacementPolicy(CALL_AND_REMOVE.getName());

        checkoutNew().clickConfirmOrder();

        userShipment().checkPageContains(userShipments().pageUrl());
        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());
        userShipment().checkShipmentNumberVisible();
        userShipment().checkShippingAddressVisible();
        userShipment().checkAssemblyCostVisible();
        userShipment().checkTotalCostVisible();
    }

    @TmsLink("3625")
    @Test(description = "Тест полного оформления заказа с оплатой Картой онлайн (Самовывоз | Алкоголь и продукты)", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckoutCompleteWithOnlinePaymentIncludeAlcoAndPickup() {
        final var userData = UserManager.getQaUser();
        var card = PaymentCards.testCardNo3ds();
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        final var fullAddress = RestAddresses.Moscow.checkoutAddress().fullAddress().toString();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToPickup();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().clickTakeFromHere();
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().goToPage(ShopUrl.METRO.getUrl() + ALCOHOL_CATEGORY_LINK);

        shop().interactDisclaimer().checkDisclaimerModalVisible();
        shop().interactDisclaimer().agreeAndConfirm();
        shop().interactDisclaimer().checkDisclaimerModalNotVisible();

        seo().interactHeader().checkPickupSelected();
        seo().addFirstProductOnDepartmentToCart();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().increaseFirstItemCountToMin();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().switchToPickup();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().checkDeliverySlotDateVisible();
        checkoutNew().checkDeliverySlotDeliveryTimeVisible();
        checkoutNew().checkDeliverySlotPriceVisible();

        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_ONLINE.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactAddPaymentCardModal().checkModalVisible();

        checkoutNew().interactAddPaymentCardModal().fillCardData(card);
        checkoutNew().interactAddPaymentCardModal().clickAdd();

        checkoutNew().interactAddPaymentCardModal().checkModalNotVisible();
        checkoutNew().checkSelectedPaymentMethodContains(card.getCardNumber().substring(card.getCardNumber().length() - 4));

        checkoutNew().checkContactsSummary(userData.getPhone(), userData.getEmail());

        checkoutNew().clickEditContacts();
        checkoutNew().checkContactsPhone(userData.getPhone());
        checkoutNew().checkContactsEmail(userData.getEmail());

        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REMOVE.getName());
        checkoutNew().checkSelectedReplacementPolicy(CALL_AND_REMOVE.getName());

        checkoutNew().clickConfirmPay();

        userShipment().checkPageContains(userShipments().pageUrl());
        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());
        userShipment().checkShipmentNumberVisible();
        //userShipment().checkShippingAddressVisible();
        //userShipment().checkAssemblyCostVisible();
        userShipment().checkTotalCostVisible();
    }

    @TmsLink("3843")
    @Test(description = "Тест полного оформления заказа с оплатой Наличными (Самовывоз | Алкоголь и продукты)", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckoutCompleteWithCashIncludeAlcoAndPickup() {
        final var userData = UserManager.getQaUser();
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToPickup();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().clickTakeFromHere();
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().goToPage(ShopUrl.METRO.getUrl() + ALCOHOL_CATEGORY_LINK);

        shop().interactDisclaimer().checkDisclaimerModalVisible();
        shop().interactDisclaimer().agreeAndConfirm();
        shop().interactDisclaimer().checkDisclaimerModalNotVisible();

        seo().interactHeader().checkPickupSelected();
        seo().addFirstProductOnDepartmentToCart();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().switchToPickup();

        checkoutNew().checkSpinnerNotVisible();

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

        checkoutNew().checkContactsSummary(userData.getPhone(), userData.getEmail());

        checkoutNew().clickEditContacts();
        checkoutNew().checkContactsPhone(userData.getPhone());
        checkoutNew().checkContactsEmail(userData.getEmail());

        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REMOVE.getName());
        checkoutNew().checkSelectedReplacementPolicy(CALL_AND_REMOVE.getName());

        checkoutNew().clickConfirmOrder();

        userShipment().checkPageContains(userShipments().pageUrl());
        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());
        userShipment().checkShipmentNumberVisible();
        userShipment().checkShippingAddressVisible();
        userShipment().checkAssemblyCostVisible();
        userShipment().checkTotalCostVisible();
    }

    @TmsLink("3844")
    @Test(description = "Тест полного оформления заказа с оплатой Картой на кассе (Самовывоз | Алкоголь и продукты)", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckoutCompleteWithPayOnCashDeskIncludeAlcoAndPickup() {
        final var userData = UserManager.getQaUser();
        this.helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToPickup();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().clickTakeFromHere();
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().goToPage(ShopUrl.METRO.getUrl() + ALCOHOL_CATEGORY_LINK);

        shop().interactDisclaimer().checkDisclaimerModalVisible();
        shop().interactDisclaimer().agreeAndConfirm();
        shop().interactDisclaimer().checkDisclaimerModalNotVisible();

        seo().interactHeader().checkPickupSelected();
        seo().addFirstProductOnDepartmentToCart();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().switchToPickup();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().checkDeliverySlotDateVisible();
        checkoutNew().checkDeliverySlotDeliveryTimeVisible();
        checkoutNew().checkDeliverySlotPriceVisible();

        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_AT_CHECKOUT.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();

        checkoutNew().checkSelectedPaymentMethodContains(BY_CARD_AT_CHECKOUT.getName());

        checkoutNew().checkContactsSummary(userData.getPhone(), userData.getEmail());

        checkoutNew().clickEditContacts();
        checkoutNew().checkContactsPhone(userData.getPhone());
        checkoutNew().checkContactsEmail(userData.getEmail());

        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REMOVE.getName());
        checkoutNew().checkSelectedReplacementPolicy(CALL_AND_REMOVE.getName());

        checkoutNew().clickConfirmOrder();

        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());
        userShipment().checkShipmentNumberVisible();
        userShipment().checkShippingAddressVisible();
        //userShipment().checkAssemblyCostVisible();
        userShipment().checkShipmentCostNotVisible();
        userShipment().checkTotalCostVisible();
    }

    @TmsLink("3845")
    @Test(description = "Тест полного оформления заказа с оплатой Картой на кассе (Самовывоз | Только алкоголь)", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckoutCompleteWithPayOnCashDeskOnlyAlcoAndPickup() {
        final var userData = UserManager.getQaUser();
        this.helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        final var fullAddress = RestAddresses.Moscow.checkoutAddress().fullAddress().toString();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToPickup();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().clickTakeFromHere();
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().goToPage(ShopUrl.METRO.getUrl() + ALCOHOL_CATEGORY_LINK);

        shop().interactDisclaimer().checkDisclaimerModalVisible();
        shop().interactDisclaimer().agreeAndConfirm();
        shop().interactDisclaimer().checkDisclaimerModalNotVisible();

        seo().interactHeader().checkPickupSelected();
        seo().addFirstProductOnDepartmentToCart();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().increaseFirstItemCountToMin();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().checkDeliverySlotDateVisible();
        checkoutNew().checkDeliverySlotDeliveryTimeVisible();
        checkoutNew().checkDeliverySlotPriceVisible();

        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_AT_CHECKOUT.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();

        checkoutNew().checkSelectedPaymentMethodContains(BY_CARD_AT_CHECKOUT.getName());

        checkoutNew().checkContactsSummary(userData.getPhone(), userData.getEmail());

        checkoutNew().clickEditContacts();
        checkoutNew().checkContactsPhone(userData.getPhone());
        checkoutNew().checkContactsEmail(userData.getEmail());

        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REMOVE.getName());
        checkoutNew().checkSelectedReplacementPolicy(CALL_AND_REMOVE.getName());

        checkoutNew().clickConfirmOrder();

        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());
        userShipment().checkShipmentNumberVisible();
        userShipment().checkShippingAddressVisible();
        //userShipment().checkAssemblyCostVisible();
        userShipment().checkShipmentCostNotVisible();
        userShipment().checkTotalCostVisible();
    }

    @TmsLink("3884")
    @Test(description = "Тест перехода на v3 чекаут с незалогиненного нового юзера", groups = {REGRESSION_STF, CHECKOUT_WEB_NEW})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testAuthAndForwardV3CheckoutFromCartNewUser() {
        final var userData = UserManager.getQaUser();

        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.checkoutAddress());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddressLarge().clickSave();
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().plusFirstItemToCart();
        shop().interactHeader().checkCartNotificationIsVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartNotEmpty();
        shop().interactCart().increaseFirstItemCountToMin();
        shop().interactCart().submitOrder();

        shop().interactAuthModal().checkModalIsVisible();
        shop().interactAuthModal().authViaPhone(userData);

        checkoutNew().checkDeliverySlotsVisible();
    }
}