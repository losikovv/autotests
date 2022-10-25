package ru.instamart.test.reforged.stf.changing_order_details;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.annotation.CookieProvider;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.enums.v2.StateV2.*;
import static ru.instamart.api.helper.ApiV3Helper.addFlipperActor;
import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_CHECKOUT_SID;
import static ru.instamart.kraken.util.TimeUtil.getPastZoneDbDate;
import static ru.instamart.reforged.Group.*;
import static ru.instamart.reforged.core.config.UiProperties.ALCOHOL_CATEGORY_LINK;
import static ru.instamart.reforged.core.config.UiProperties.FREE_DELIVERY_PROMO_ID;
import static ru.instamart.reforged.stf.enums.PaymentMethods.BY_CARD_AT_CHECKOUT;
import static ru.instamart.reforged.stf.enums.PaymentMethods.BY_CARD_TO_COURIER;
import static ru.instamart.reforged.stf.enums.ReplacementPolicies.CALL_AND_REMOVE;
import static ru.instamart.reforged.stf.enums.ReplacementPolicies.DONT_CALL_AND_REMOVE;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Изменение деталей заказа")
public final class ChangingDeliverySlotTests {
    // Для включения редактирования заказа, должен быть включен ФФ tmp_b2c_9162_spree_shipment_changes
    // Пользователь должен быть добавлен в А/Б-тесты:
    // 2ae723fe-fdc0-4ab6-97ee-7692d2a19c90 группу new_checkout_web
    // 7cb891fd-a69d-4aef-854e-09b0da121536 группу w_changing_details
    // 7be2e177-5ce6-4769-b04e-c794633076e8 группу w_new_statuses

    private final ApiHelper helper = new ApiHelper();

    @CaseId(3692)
    @Story("Изменение слота доставки")
    @Test(description = "Проверка возможности изменения слота доставки", groups = {REGRESSION_STF, POST_ORDERING, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testChangeSlotOnPostCheckout() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
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

        checkoutNew().fillApartment("1");
        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().clickSecondSlot();
        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_TO_COURIER.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();
        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REMOVE.getName());
        checkoutNew().clickConfirmOrder();

        userShipment().waitPageLoad();
        userShipment().checkChangeDeliverySlotButtonVisible();

        userShipment().clickChangeDeliverySlot();
        userShipment().interactDeliverySlotsModal().checkModalVisible();

        userShipment().interactDeliverySlotsModal().clickOnSlot(3);
        userShipment().interactDeliverySlotsModal().checkSelectedSlotPosition(3);
        userShipment().interactDeliverySlotsModal().clickClose();
        userShipment().interactDeliverySlotsModal().checkModalNotVisible();

        userShipment().clickChangeDeliverySlot();
        userShipment().interactDeliverySlotsModal().checkModalVisible();
        userShipment().interactDeliverySlotsModal().checkSelectedSlotPosition(2);

        var newSlotDate = userShipment().interactDeliverySlotsModal().getDate(1);
        userShipment().interactDeliverySlotsModal().clickOnSlot(3);
        var newSlotTime = userShipment().interactDeliverySlotsModal().getSelectedDeliveryTime();
        userShipment().interactDeliverySlotsModal().clickApply();
        userShipment().interactDeliverySlotsModal().checkModalNotVisible();

        userShipment().checkAlertVisible();
        userShipment().checkAlertText("Вы поменяли время доставки. Привезём заказ " + newSlotDate.toLowerCase() + " " + newSlotTime);
        userShipment().checkCurrentDeliveryInterval(StringUtil.getTodayTomorrowOrDate(newSlotDate) + ", " + newSlotTime);
    }

    @CaseId(3715)
    @Story("Изменение слота доставки")
    @Test(description = "Проверка возможности изменения слота самовывоза", groups = {REGRESSION_STF, POST_ORDERING, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testChangeSlotOnPostCheckoutPickup() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
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
        checkoutNew().checkPickupTabOpened();

        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().clickSecondSlot();
        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_AT_CHECKOUT.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();
        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(DONT_CALL_AND_REMOVE.getName());
        checkoutNew().clickConfirmOrder();

        userShipment().waitPageLoad();
        userShipment().checkChangeDeliverySlotButtonVisible();

        userShipment().clickChangeDeliverySlot();
        userShipment().interactDeliverySlotsModal().checkModalVisible();
        userShipment().interactDeliverySlotsModal().checkModalTitle("Время самовывоза");
        userShipment().interactDeliverySlotsModal().checkCurrentSlotTitle("Текущее время самовывоза");
        userShipment().interactDeliverySlotsModal().checkSlotBlockTitle("Выберите новое время самовывоза");

        userShipment().interactDeliverySlotsModal().clickOnSlot(3);
        userShipment().interactDeliverySlotsModal().checkSelectedSlotPosition(3);
        userShipment().interactDeliverySlotsModal().clickClose();
        userShipment().interactDeliverySlotsModal().checkModalNotVisible();

        userShipment().clickChangeDeliverySlot();
        userShipment().interactDeliverySlotsModal().checkModalVisible();
        userShipment().interactDeliverySlotsModal().checkSelectedSlotPosition(2);

        var newSlotDate = userShipment().interactDeliverySlotsModal().getDate(1);
        userShipment().interactDeliverySlotsModal().clickOnSlot(3);
        var newSlotTime = userShipment().interactDeliverySlotsModal().getSelectedDeliveryTime();
        userShipment().interactDeliverySlotsModal().clickApply();
        userShipment().interactDeliverySlotsModal().checkModalNotVisible();

        userShipment().checkAlertVisible();
        userShipment().checkAlertText("Вы поменяли время самовывоза. BUG! " + newSlotDate.toLowerCase() + " " + newSlotTime);

        userShipment().checkCurrentDeliveryInterval(StringUtil.getTodayTomorrowOrDate(newSlotDate) + ", " + newSlotTime);
    }

    @CaseId(3693)
    @Story("Изменение слота доставки")
    @Test(description = "Проверка возможности изменения слота доставки/самовывоза с учетом промика бесплатной доставки в заказе", groups = {REGRESSION_STF, POST_ORDERING, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testChangeSlotOnPostCheckoutWithFreeDeliveryPromo() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
        helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());
        var promo = "test_prefix" + Generate.literalString(5) + Generate.string(1);
        final String yesterday = getPastZoneDbDate(1L);
        this.helper.createPromotionCode(promo, FREE_DELIVERY_PROMO_ID, yesterday, yesterday, 100);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().fillApartment("1");
        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().clickSecondSlot();
        checkoutNew().checkActiveDeliverySlotsVisible();
        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_TO_COURIER.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();
        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REMOVE.getName());
        checkoutNew().fillPromoCode(promo);
        checkoutNew().clickApplyPromoCode();

        var orderAmount = checkoutNew().getOrderAmount();
        checkoutNew().checkSpinnerNotVisible();
        checkoutNew().checkDeliveryIsFree();
        checkoutNew().clickConfirmOrder();

        userShipment().waitPageLoad();
        userShipment().checkChangeDeliverySlotButtonVisible();
        userShipment().checkPromoCodeVisible();
        userShipment().checkShipmentCost("бесплатно");

        userShipment().clickChangeDeliverySlot();
        userShipment().interactDeliverySlotsModal().checkModalVisible();
        userShipment().interactDeliverySlotsModal().checkDeliverySlotWithAnotherPriceNotVisible();

        userShipment().interactDeliverySlotsModal().clickOnSlot(3);
        userShipment().interactDeliverySlotsModal().clickApply();
        userShipment().interactDeliverySlotsModal().checkModalNotVisible();

        userShipment().checkAlertVisible();
        userShipment().checkShipmentCost("бесплатно");
        userShipment().checkOrderTotalCost(orderAmount);
    }

    @CaseId(3718)
    @Story("Изменение слота доставки")
    @Test(description = "Проверка возможности изменения слота доставки/самовывоза с учетом промика в заказе", groups = {REGRESSION_STF, POST_ORDERING, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testChangeSlotOnPostCheckoutWithPromo() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
        helper.dropAndFillCartWithoutSetAddress(userData, DEFAULT_CHECKOUT_SID);
        helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());
        var promo = "test_prefix" + Generate.literalString(5) + Generate.string(1);
        final String yesterday = getPastZoneDbDate(1L);
        this.helper.createPromotionCode(promo, 2760, yesterday, yesterday, 100);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().fillApartment("1");
        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().clickSecondSlot();
        checkoutNew().checkActiveDeliverySlotsVisible();
        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_TO_COURIER.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();
        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REMOVE.getName());
        checkoutNew().fillPromoCode(promo);
        checkoutNew().clickApplyPromoCode();

        var orderAmount = StringUtil.stringToDouble(checkoutNew().getOrderAmount());
        checkoutNew().checkSpinnerNotVisible();
        checkoutNew().clickConfirmOrder();

        userShipment().waitPageLoad();
        userShipment().checkChangeDeliverySlotButtonVisible();
        userShipment().checkPromoCodeVisible();

        userShipment().clickChangeDeliverySlot();
        userShipment().interactDeliverySlotsModal().checkModalVisible();
        userShipment().interactDeliverySlotsModal().checkDeliverySlotWithHigherPriceVisible();

        userShipment().interactDeliverySlotsModal().selectSlotWithHigherPrice();
        userShipment().interactDeliverySlotsModal().clickApply();
        userShipment().interactDeliverySlotsModal().checkModalNotVisible();
        userShipment().checkAlertVisible();
        userShipment().checkPromoCodeVisible();
        userShipment().checkOrderTotalCostIncreased(orderAmount);

        userShipment().clickChangeDeliverySlot();
        userShipment().interactDeliverySlotsModal().checkDeliverySlotWithLowerPriceVisible();
        userShipment().interactDeliverySlotsModal().selectSlotWithLowerPrice();
        userShipment().checkAlertVisible();
        userShipment().checkPromoCodeVisible();
        userShipment().checkOrderTotalCost(orderAmount);
    }

    @CaseId(3782)
    @Story("Изменение слота доставки")
    @Test(description = "Проверка возможности изменения слота с обычного на OnDemand", groups = {REGRESSION_STF, POST_ORDERING, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testChangeSlotToOnDemandOnPostCheckout() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
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
        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().checkOnDemandDeliverySlotsVisible();
        checkoutNew().fillApartment("1");
        checkoutNew().clickSecondSlot();
        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_TO_COURIER.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();
        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(DONT_CALL_AND_REMOVE.getName());
        checkoutNew().clickConfirmOrder();

        userShipment().waitPageLoad();
        userShipment().checkChangeDeliverySlotButtonVisible();

        userShipment().clickChangeDeliverySlot();
        userShipment().interactDeliverySlotsModal().checkModalVisible();
        var newSlotDate = userShipment().interactDeliverySlotsModal().getDate(1);
        userShipment().interactDeliverySlotsModal().clickOnSlot(1);
        var newSlotTime = userShipment().interactDeliverySlotsModal().getSelectedDeliveryTime();
        userShipment().interactDeliverySlotsModal().clickApply();
        userShipment().interactDeliverySlotsModal().checkModalNotVisible();

        userShipment().checkAlertVisible();
        userShipment().checkAlertText("Вы поменяли время доставки. Привезём заказ " + newSlotDate.toLowerCase() + " " + newSlotTime);

        userShipment().checkCurrentDeliveryInterval(StringUtil.getTodayTomorrowOrDate(newSlotDate) + ", " + newSlotTime);
    }

    @CaseId(3695)
    @Story("Изменение слота доставки")
    @Test(description = "Проверка невозможности изменения слота доставки когда оформлен OnDemand", groups = {REGRESSION_STF, POST_ORDERING, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testNotAvailableChangeSlotFromOnDemandToNormalOnPostCheckout() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
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
        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().checkOnDemandDeliverySlotsVisible();
        checkoutNew().fillApartment("1");
        checkoutNew().clickFirstSlot();
        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_TO_COURIER.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();
        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(DONT_CALL_AND_REMOVE.getName());
        checkoutNew().clickConfirmOrder();

        userShipment().waitPageLoad();
        userShipment().checkChangeDeliverySlotButtonVisible();
        userShipment().clickChangeDeliverySlot();

        userShipment().checkAlertVisible();
        userShipment().checkAlertText("Заказ скоро начнут собирать. Время поменять не получится");
    }

    // У магазина должен быть отключена функция смены слота доставки Админка -> Редактирование магазина -> Разрешить менять слот доставки после оформления заказа
    @CaseId(3719)
    @Story("Изменение слота доставки")
    @Test(description = "Проверка невозможности изменения слота при невозможности изменения в выбранном магазине", groups = {REGRESSION_STF, POST_ORDERING, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testNotAvailableChangeSlotIfDisabledInAdmin() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
        helper.dropAndFillCart(userData, 6);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();
        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().fillApartment("1");
        checkoutNew().clickSecondSlot();
        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_TO_COURIER.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();
        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(DONT_CALL_AND_REMOVE.getName());
        checkoutNew().clickConfirmOrder();

        userShipment().waitPageLoad();
        userShipment().checkChangeDeliverySlotButtonVisible();
        userShipment().clickChangeDeliverySlot();

        userShipment().checkAlertVisible();
        userShipment().checkAlertText("Изменение недоступно для этого магазина");
    }

    @CaseId(3712)
    @Story("Изменение слота доставки")
    @Test(description = "Проверка невозможности изменения слота самовывоза, имея в шипменте алкоголь", groups = {REGRESSION_STF, POST_ORDERING, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testNotAvailableChangeSlotIfAlcohol() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToPickup();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().clickShowAsList();
        shop().interactAddressLarge().checkPickupStoresModalVisible();
        shop().interactAddressLarge().selectRetailerByName("METRO");
        shop().interactAddressLarge().selectStoreByAddress(RestAddresses.Moscow.checkoutAddress().fullAddress().toString());
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        seo().goToPage(ShopUrl.METRO.getUrl() + ALCOHOL_CATEGORY_LINK);
        shop().interactDisclaimer().checkDisclaimerModalVisible();
        shop().interactDisclaimer().agreeAndConfirm();
        shop().interactDisclaimer().checkDisclaimerModalNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();
        seo().addFirstProductOnDepartmentToCart();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().clickSecondSlot();
        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_AT_CHECKOUT.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();
        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(DONT_CALL_AND_REMOVE.getName());
        checkoutNew().clickConfirmOrder();

        userShipment().waitPageLoad();
        userShipment().checkChangeDeliverySlotButtonVisible();

        userShipment().waitPageLoad();
        userShipment().checkChangeDeliverySlotButtonVisible();
        userShipment().clickChangeDeliverySlot();

        userShipment().checkAlertVisible();
        userShipment().checkAlertText("В заказе есть алкоголь. Время поменять не получится");
    }

    @CaseId(3714)
    @Story("Изменение слота доставки")
    @Test(description = "Проверка невозможности изменения слота доставки, когда статус заказа старше статуса 'Ожидает сборки'", groups = {REGRESSION_STF, POST_ORDERING, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testNotAvailableChangeSlotIfIncorrectShipmentState() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
        var order = helper.makeOrder(userData, DEFAULT_CHECKOUT_SID, 1);
        helper.updateShipmentStateByOrderNumber(order.getNumber(), COLLECTING.getValue());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipment().openShipmentPageByNumber(order.getShipments().get(0).getNumber());
        userShipment().waitPageLoad();
        userShipment().checkChangeDeliverySlotButtonVisible();
        userShipment().checkActiveShipmentState("Собираем");
        userShipment().clickChangeDeliverySlot();

        userShipment().checkAlertVisible();
        userShipment().checkAlertText("Заказ уже собирается. Время поменять не получится");

        helper.updateShipmentStateByOrderNumber(order.getNumber(), READY_TO_SHIP.getValue());

        userShipment().refresh();
        userShipment().waitPageLoad();
        userShipment().checkChangeDeliverySlotButtonVisible();
        userShipment().checkActiveShipmentState("Скоро отправим");
        userShipment().clickChangeDeliverySlot();

        userShipment().checkAlertVisible();
        userShipment().checkAlertText("Заказ ожидает доставки. Время поменять не получится");

        helper.updateShipmentStateByOrderNumber(order.getNumber(), SHIPPING.getValue());

        userShipment().refresh();
        userShipment().waitPageLoad();
        userShipment().checkChangeDeliverySlotButtonVisible();
        userShipment().checkActiveShipmentState("В пути");
        userShipment().clickChangeDeliverySlot();

        userShipment().checkAlertVisible();
        userShipment().checkAlertText("Заказ уже в пути. Время поменять не получится");

        helper.updateShipmentStateByOrderNumber(order.getNumber(), CANCELED.getValue());

        userShipment().checkAlertNotVisible();
        userShipment().clickChangeDeliverySlot();

        userShipment().checkAlertVisible();
        userShipment().checkAlertText("Заказ отменён. Время поменять не получится");
    }

    @CaseId(3716)
    @Story("Изменение слота доставки")
    @Test(description = "Проверка невозможности изменения слота самовывоза, когда статус заказа старше статуса 'Ожидает сборки'", groups = {REGRESSION_STF, POST_ORDERING, JOTUNHEIMR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testNotAvailableChangeSlotIfIncorrectShipmentStatePickup() {
        final var userData = UserManager.getQaUser();
        addFlipperActor("checkout_web_new", userData.getId());
        addFlipperActor("checkout_web_force_all", userData.getId());
        addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
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
        checkoutNew().checkPickupTabOpened();

        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().clickSecondSlot();
        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_AT_CHECKOUT.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();
        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(DONT_CALL_AND_REMOVE.getName());
        checkoutNew().clickConfirmOrder();

        userShipment().waitPageLoad();
        helper.updateShipmentStateByShipmentNumber(userShipment().getShipmentNumber(), COLLECTING.getValue());

        userShipment().refresh();
        userShipment().waitPageLoad();
        userShipment().checkChangeDeliverySlotButtonVisible();
        userShipment().checkActiveShipmentState("Собираем");
        userShipment().clickChangeDeliverySlot();

        userShipment().checkAlertVisible();
        userShipment().checkAlertText("Заказ уже собирается. Время поменять не получится");

        helper.updateShipmentStateByShipmentNumber(userShipment().getShipmentNumber(), READY_TO_SHIP.getValue());

        userShipment().refresh();
        userShipment().waitPageLoad();
        userShipment().checkChangeDeliverySlotButtonVisible();
        userShipment().checkActiveShipmentState("Можно забирать");
        userShipment().clickChangeDeliverySlot();

        userShipment().checkAlertVisible();
        userShipment().checkAlertText("Заказ собран. Время поменять не получится");


        helper.updateShipmentStateByShipmentNumber(userShipment().getShipmentNumber(), CANCELED.getValue());

        userShipment().checkAlertNotVisible();
        userShipment().clickChangeDeliverySlot();

        userShipment().checkAlertVisible();
        userShipment().checkAlertText("Заказ отменён. Время поменять не получится");
    }
}