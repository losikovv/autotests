package ru.instamart.test.reforged.stf.checkout_new;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Issues;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.PaymentCards;
import ru.instamart.kraken.data.Promos;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.UiProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.helper.ApiV3Helper.checkFlipper;
import static ru.instamart.api.helper.ApiV3Helper.checkFlipperOff;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Чекаут [NEW]")
public final class CheckoutTests {

    private final ApiHelper helper = new ApiHelper();

    @BeforeClass(alwaysRun = true)
    public void checkAndSwitchFlagsOn() {
        checkFlipper("checkout_web_new");
        checkFlipper("checkout_web_force_all");
    }

    @AfterClass(alwaysRun = true)
    public void switchFlagsOff() {
        checkFlipperOff("checkout_web_new");
        checkFlipperOff("checkout_web_force_all");
    }

    @Issues({@Issue("B2C-9732"), @Issue("B2C-9730")})
    @CaseId(3623)
    @Test(description = "Тест полного оформления заказа с оплатой картой онлайн (Доставка)", groups = "regression")
    public void testCheckoutCompleteWithOnlinePaymentAndDelivery() {
        final var userData = UserManager.getQaUser();
        var card = PaymentCards.testCardNo3ds();
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

        userShipments().checkPageContains(userShipments().pageUrl());
        userShipments().checkStatusShipmentReady();
        userShipments().checkShipmentNumberVisible();
        userShipments().checkShippingAddressVisible();
        userShipments().checkPaymentMethodEquals(paymentMethod);
        userShipments().checkProductsCostVisible();
        userShipments().checkShipmentCostVisible();
        userShipments().checkTotalCostVisible();
    }

    @CaseId(3624)
    @Test(description = "Тест полного оформления заказа с оплатой картой курьеру (Доставка)", groups = "regression")
    public void testCheckoutCompleteWithPayToCourierAndDelivery() {
        final var userData = UserManager.getQaUser();
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

        userShipments().checkPageContains(userShipments().pageUrl());
        userShipments().checkStatusShipmentReady();
        userShipments().checkShipmentNumberVisible();
        userShipments().checkShippingAddressVisible();
        userShipments().checkPaymentMethodEquals("Картой при получении");
        userShipments().checkProductsCostVisible();
        userShipments().checkShipmentCostVisible();
        userShipments().checkTotalCostVisible();
    }

    @Issue("B2C-9738")
    @CaseId(3638)
    @Test(description = "Выбор слота доставки", groups = "regression")
    public void testSelectDeliverySlot() {
        final var userData = UserManager.getQaUser();
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
    @Test(description = "Сброс способа оплаты 'Картой курьеру' при выборе 'Бесконтактная доставка'", groups = "regression")
    public void testPayByCardCourierDeliveryToDoor() {
        final var userData = UserManager.getQaUser();
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
    @Test(description = "Проверка предвыбора метода 'Оплатить онлайн' при выборе самовывоза", groups = "regression")
    public void testCheckPayOnlineSelectedByDefaultInPickup() {
        final var userData = UserManager.getQaUser();
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

        checkoutNew().checkPickupTabOpened();

        checkoutNew().checkSelectedPaymentMethodContains("Оплатить онлайн");
    }

    @Issues({@Issue("B2C-9732"), @Issue("B2C-9730")})
    @CaseId(3649)
    @Test(description = "Проверка добавления новой карты оплаты", groups = "regression")
    public void testAddNewPaymentCard() {
        final var userData = UserManager.getQaUser();
        var card = PaymentCards.testCardNo3ds();
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
    @Test(description = "Проверка появления кнопки 'Оплатить' при способе оплаты 'Картой онлайн'", groups = "regression")
    public void testPayButtonDisplayedWithSelectOnlinePaymentMethod() {
        final var userData = UserManager.getQaUser();
        var card = PaymentCards.testCardNo3ds();
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
    @Test(description = "Проверка появления кнопки 'Заказать' при способе оплаты 'Картой курьеру'", groups = "regression")
    public void testOrderConfirmButtonDisplayedWithSelectCardToCourierPaymentMethod() {
        final var userData = UserManager.getQaUser();
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

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod("Картой курьеру");
        checkoutNew().interactPaymentMethodsModal().clickConfirm();

        checkoutNew().checkConfirmOrderVisible();
        checkoutNew().checkConfirmOrderActive();
    }

    @CaseId(3645)
    @Test(description = "Применение несуществующего промокода", groups = "regression")
    public void testAddInvalidPromoCode() {
        final var userData = UserManager.getQaUser();
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

        checkoutNew().fillPromoCode(Generate.literalString(6));
        checkoutNew().clickApplyPromoCode();

        checkoutNew().checkPromoCodeErrorVisible();
        checkoutNew().checkPromoCodeInputErrorContains("Промокод не существует");
    }

    @CaseId(3646)
    @Test(description = "Применение существующего промокода", groups = "regression")
    public void testAddValidPromoCode() {
        final var userData = UserManager.getQaUser();
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

        checkoutNew().fillPromoCode(Generate.literalString(1));
        checkoutNew().checkPromoApplyButtonVisible();

        checkoutNew().fillPromoCode(Promos.discountOnFirstOrder().getCode());
        checkoutNew().clickApplyPromoCode();

        checkoutNew().checkPromoCancelButtonVisible();
        checkoutNew().checkPromoAppliedLabelVisible();
        checkoutNew().checkPromoAppliedDiscountVisible();
    }
}