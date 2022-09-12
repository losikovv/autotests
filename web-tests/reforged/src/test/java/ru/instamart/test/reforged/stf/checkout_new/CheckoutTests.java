package ru.instamart.test.reforged.stf.checkout_new;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Issues;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.PaymentCards;
import ru.instamart.kraken.data.Promos;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.CookieProvider;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.helper.ApiV3Helper.addFlipperActor;
import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_CHECKOUT_SID;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Чекаут [NEW]")
public final class CheckoutTests {
    // Для включения нового чекаута необходимо, чтобы были включены ФФ checkout_web_new, checkout_web_force_all, tmp_b2c_9162_spree_shipment_changes
    // Пользователь должен быть добавлен в А/Б-тесты:
    // 2ae723fe-fdc0-4ab6-97ee-7692d2a19c90 группу new_checkout_web
    // 7cb891fd-a69d-4aef-854e-09b0da121536 группу w_changing_details
    // 7be2e177-5ce6-4769-b04e-c794633076e8 группу w_new_statuses

    private final ApiHelper helper = new ApiHelper();

    @Issues({@Issue("B2C-9732"), @Issue("B2C-9730")})
    @CaseId(3623)
    @Test(description = "Тест полного оформления заказа с оплатой картой онлайн (Доставка)", groups = {"regression", "checkout_web_new"})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckoutCompleteWithOnlinePaymentAndDelivery() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
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

        var paymentMethod = "Картой онлайн";
        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(paymentMethod);
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

        var replacementPolicy = "Позвонить мне. Убрать из заказа, если не смогу ответить";
        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(replacementPolicy);
        checkoutNew().checkSelectedReplacementPolicyContains(replacementPolicy);

        checkoutNew().clickConfirmPay();
        //B2C-9730

        userShipment().checkPageContains(userShipments().pageUrl());
        userShipment().checkActiveShipmentState("Принят");
        userShipment().checkShipmentNumberVisible();
        userShipment().checkShippingAddressVisible();
        userShipment().checkPaymentMethodEquals(paymentMethod);
        userShipment().checkProductsCostVisible();
        userShipment().checkShipmentCostVisible();
        userShipment().checkTotalCostVisible();
    }

    @CaseId(3624)
    @Test(description = "Тест полного оформления заказа с оплатой картой курьеру (Доставка)", groups = {"regression", "checkout_web_new"})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckoutCompleteWithPayToCourierAndDelivery() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
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

        var paymentMethod = "Картой курьеру";
        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(paymentMethod);
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();

        checkoutNew().checkSelectedPaymentMethodContains(paymentMethod);

        checkoutNew().checkContactsSummary(userData.getPhone(), userData.getEmail());

        checkoutNew().clickEditContacts();
        checkoutNew().checkContactsPhone(userData.getPhone());
        checkoutNew().checkContactsEmail(userData.getEmail());

        var replacementPolicy = "Позвонить мне. Убрать из заказа, если не смогу ответить";
        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(replacementPolicy);
        checkoutNew().checkSelectedReplacementPolicyContains(replacementPolicy);

        checkoutNew().clickConfirmOrder();

        userShipment().checkActiveShipmentState("Принят");
        userShipment().checkShipmentNumberVisible();
        userShipment().checkShippingAddressVisible();
        userShipment().checkPaymentMethodEquals("Картой при получении");
        userShipment().checkProductsCostVisible();
        userShipment().checkShipmentCostVisible();
        userShipment().checkTotalCostVisible();
    }

    @Issue("B2C-9738")
    @CaseId(3638)
    @Test(description = "Выбор слота доставки", groups = {"regression", "checkout_web_new"})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testSelectDeliverySlot() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
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

        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().checkActiveDeliverySlotsNotVisible();

        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);
        checkoutNew().checkSelectedDeliverySlotsCount(1);

        var slotDate = checkoutNew().getActiveSlotDate();
        var slotTime = checkoutNew().getActiveSlotTime();
        var slotCost = checkoutNew().getActiveSlotCost();

        checkoutNew().openDeliverySlotsModalFromOthers();
        checkoutNew().interactDeliverySlotsModal().checkAvailableDeliveryDaysVisible();
        checkoutNew().interactDeliverySlotsModal().checkAvailableDeliveryDaysCount(7);
        checkoutNew().interactDeliverySlotsModal().checkSelectedDeliveryDaysCount(1);
        checkoutNew().interactDeliverySlotsModal().checkSelectedDayPosition(1);

        checkoutNew().interactDeliverySlotsModal().checkSelectedDayNameContainsText(slotDate);
        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotTimeContainsText(slotTime);
        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotCostContainsText(slotCost);

        checkoutNew().interactDeliverySlotsModal().clickOnDay(2);
        checkoutNew().interactDeliverySlotsModal().checkSelectedDayPosition(2);

        slotDate = checkoutNew().interactDeliverySlotsModal().getDayName(2);

        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotNotVisible();

        checkoutNew().interactDeliverySlotsModal().clickOnSlot(1);
        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotPosition(1);
        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotsCount(1);

        slotTime = checkoutNew().interactDeliverySlotsModal().getSelectedDeliveryTime();
        slotCost = checkoutNew().interactDeliverySlotsModal().getSelectedDeliveryCost();

        checkoutNew().interactDeliverySlotsModal().clickApply();
        checkoutNew().interactDeliverySlotsModal().checkModalNotVisible();

        checkoutNew().checkDeliveryTitleContains(slotDate);
        checkoutNew().checkSelectedDeliverySlotsCount(1);
        checkoutNew().scrollSlotsToStart();

        checkoutNew().checkSlotActive(1);
        checkoutNew().checkSelectedDeliverySlotsCount(1);

        checkoutNew().checkSelectedSlotDayNameContainsText(slotDate);
        checkoutNew().checkSelectedSlotTimeContainsText(slotTime);
        checkoutNew().checkSelectedSlotCostContainsText(slotCost);

        checkoutNew().openDeliverySlotsModalFromTitle();

        checkoutNew().interactDeliverySlotsModal().checkAvailableDeliveryDaysVisible();
        checkoutNew().interactDeliverySlotsModal().checkAvailableDeliveryDaysCount(7);
        checkoutNew().interactDeliverySlotsModal().checkSelectedDeliveryDaysCount(1);
        checkoutNew().interactDeliverySlotsModal().checkSelectedDayPosition(2);
        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotsCount(1);
        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotPosition(1);

        checkoutNew().interactDeliverySlotsModal().checkSelectedDayNameContainsText(slotDate);
        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotTimeContainsText(slotTime);
        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotCostContainsText(slotCost);

        checkoutNew().interactDeliverySlotsModal().clickOnDay(3);
        checkoutNew().interactDeliverySlotsModal().checkSelectedDayPosition(3);

        slotDate = checkoutNew().interactDeliverySlotsModal().getDayName(3);

        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotNotVisible();

        checkoutNew().interactDeliverySlotsModal().clickOnSlot(1);
        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotPosition(1);
        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotsCount(1);

        slotTime = checkoutNew().interactDeliverySlotsModal().getSelectedDeliveryTime();
        slotCost = checkoutNew().interactDeliverySlotsModal().getSelectedDeliveryCost();

        checkoutNew().interactDeliverySlotsModal().clickApply();
        checkoutNew().interactDeliverySlotsModal().checkModalNotVisible();

        checkoutNew().checkSelectedSlotDayNameContainsText(slotDate);
        checkoutNew().checkSelectedSlotTimeContainsText(slotTime);
        checkoutNew().checkSelectedSlotCostContainsText(slotCost);
    }

    @Issue("B2C-9776")
    @CaseId(3616)
    @Test(description = "Сброс способа оплаты 'Картой курьеру' при выборе 'Бесконтактная доставка'", groups = {"regression", "checkout_web_new"})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testPayByCardCourierDeliveryToDoor() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
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

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod("Картой курьеру");
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();

        checkoutNew().checkSelectedPaymentMethodContains("Картой курьеру");

        checkoutNew().clickDeliveryToDoor();

        checkoutNew().checkSpinnerVisible();
        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().checkPaymentMethodEmpty();

        checkoutNew().checkNotificationVisible();
        checkoutNew().checkNotificationTitle("Оплата картой курьеру недоступна");
        checkoutNew().checkNotificationText("Вы выбрали бесконтактную доставку, заказ нужно оплатить онлайн");
    }

    @Issue("B2C-9777")
    @CaseId(3637)
    @Test(description = "Проверка предвыбора метода 'Оплатить онлайн' при выборе самовывоза", groups = {"regression", "checkout_web_new"})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testCheckPayOnlineSelectedByDefaultInPickup() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
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

        checkoutNew().checkPickupTabOpened();

        checkoutNew().checkSelectedPaymentMethodContains("Оплатить онлайн");
    }

    @Issues({@Issue("B2C-9732"), @Issue("B2C-9730")})
    @CaseId(3649)
    @Test(description = "Проверка добавления новой карты оплаты", groups = {"regression", "checkout_web_new"})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testAddNewPaymentCard() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
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

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod("Картой онлайн");
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactAddPaymentCardModal().checkModalVisible();

        checkoutNew().interactAddPaymentCardModal().fillCardData(card);
        //B2C-9732

        checkoutNew().interactAddPaymentCardModal().clickAdd();
        checkoutNew().interactAddPaymentCardModal().checkModalNotVisible();

        checkoutNew().checkSelectedPaymentMethodContains(card.getCardNumber().substring(card.getCardNumber().length() - 4));
        //B2C-9730
    }

    @Issue("B2C-9732")
    @CaseId(3642)
    @Test(description = "Проверка появления кнопки 'Оплатить' при способе оплаты 'Картой онлайн'", groups = {"regression", "checkout_web_new"})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testPayButtonDisplayedWithSelectOnlinePaymentMethod() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
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

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod("Картой онлайн");
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactAddPaymentCardModal().checkModalVisible();

        checkoutNew().interactAddPaymentCardModal().fillCardData(card);
        //B2C-9732

        checkoutNew().interactAddPaymentCardModal().clickAdd();
        checkoutNew().interactAddPaymentCardModal().checkModalNotVisible();

        checkoutNew().checkConfirmPayVisible();
        checkoutNew().checkConfirmPayActive();
    }

    @CaseId(3643)
    @Test(description = "Проверка появления кнопки 'Заказать' при способе оплаты 'Картой курьеру'", groups = {"regression", "checkout_web_new"})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testOrderConfirmButtonDisplayedWithSelectCardToCourierPaymentMethod() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
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

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod("Картой курьеру");
        checkoutNew().interactPaymentMethodsModal().clickConfirm();

        checkoutNew().checkConfirmOrderVisible();
        checkoutNew().checkConfirmOrderActive();
    }
}