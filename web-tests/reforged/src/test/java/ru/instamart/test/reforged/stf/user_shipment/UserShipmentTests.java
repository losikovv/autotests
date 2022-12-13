package ru.instamart.test.reforged.stf.user_shipment;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.annotation.CookieProvider;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.time.LocalDateTime;

import static ru.instamart.api.enums.v2.StateV2.*;
import static ru.instamart.api.helper.K8sHelper.changeItemToCancel;
import static ru.instamart.api.helper.K8sHelper.changeToAssembled;
import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_CHECKOUT_SID;
import static ru.instamart.reforged.Group.JORMUNGANDR;
import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.core.config.UiProperties.*;
import static ru.instamart.reforged.stf.enums.PaymentMethods.BY_CARD_AT_CHECKOUT;
import static ru.instamart.reforged.stf.enums.PaymentMethods.BY_CARD_TO_COURIER;
import static ru.instamart.reforged.stf.enums.ReplacementPolicies.DONT_CALL_AND_REMOVE;
import static ru.instamart.reforged.stf.enums.ShipmentStates.*;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Информация о заказе пользователя")
public final class UserShipmentTests {
    // Коментарий к заказу делала команда Jotunheimr, поэтому всё, как мы любим:
    // Для проверки комментария в заказе для пользователя должен быть активен ФФ: tmp_b2c_9162_spree_shipment_changes
    // Пользователь должен быть добавлен в АБ тест: "АБ тест изменения деталей заказа (комметарий) [web]" 19eef5a9-94b9-437b-9135-5c79ae352097,
    // Вопросы - Артёму Брагину

    // Отображение стоимости доставки "Сборка" + "Доставка" (отдельно) или "Сборка и доставка" (одной суммой) зависит от настройки выбранного слота
    private final ApiHelper helper = new ApiHelper();

    @CaseId(3144)
    @Test(description = "Объединение корзины при повторе заказа", groups = {REGRESSION_STF, JORMUNGANDR})
    public void testMergingCartIfRepeatOrder() {
        final var userData = UserManager.getQaUser();
        helper.makeOrder(userData, DEFAULT_CHECKOUT_SID, 1);
        helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        shop().goToPage(ShopUrl.METRO);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        var lineItemName = shop().getProductTitleByPosition(2);
        shop().plusItemToCartByPosition(2);
        shop().interactHeader().checkCartNotificationIsVisible();

        userShipments().goToPage();
        userShipments().waitPageLoad();
        userShipments().clickToFirstShipment();

        userShipment().waitPageLoad();
        userShipment().clickToRepeatFromOrder();

        userShipment().interactRepeatModal().checkModalWindowVisible();
        userShipment().interactRepeatModal().close();

        userShipment().interactRepeatModal().checkModalWindowNotVisible();
        userShipment().interactHeader().clickToCart();

        userShipment().interactHeader().interactCart().checkCartOpen();
        userShipment().interactHeader().interactCart().checkCartNotEmpty();
        userShipment().interactHeader().interactCart().checkItemsCount(1);
        userShipment().interactHeader().interactCart().checkItemListContains(lineItemName);

        userShipment().interactHeader().interactCart().closeCart();
        userShipment().interactHeader().interactCart().checkCartClose();

        userShipment().clickToRepeatFromOrder();
        userShipment().interactRepeatModal().checkModalWindowVisible();
        userShipment().interactRepeatModal().clickToAccept();

        userShipment().interactRepeatModal().checkModalWindowNotVisible();

        shop().waitPageLoad();
        shop().interactHeader().interactCart().checkCartOpen();
        shop().interactHeader().interactCart().checkCartNotEmpty();
        shop().interactHeader().interactCart().checkItemsCountMoreThan(1);
        shop().interactHeader().interactCart().checkItemListContains(lineItemName);

        shop().interactHeader().interactCart().submitOrder();

        checkoutNew().checkDeliverySlotsVisible();
    }

    @CaseId(1258)
    @Test(description = "Повторение заказа", groups = {REGRESSION_STF, JORMUNGANDR})
    public void testRepeatOrder() {
        final var userData = UserManager.getQaUser();
        helper.makeOrder(userData, DEFAULT_CHECKOUT_SID, 5);
        helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().waitPageLoad();
        userShipments().clickToFirstShipment();

        userShipment().waitPageLoad();
        var firstShipmentLineItems = userShipment().getItemsList();
        userShipment().clickToRepeatFromOrder();

        userShipment().interactRepeatModal().checkModalWindowVisible();
        userShipment().interactRepeatModal().clickToAccept();

        userShipment().interactRepeatModal().checkModalWindowNotVisible();

        shop().waitPageLoad();
        shop().interactHeader().interactCart().checkCartOpen();
        shop().interactHeader().interactCart().checkCartNotEmpty();
        shop().interactHeader().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().fillApartment("1");
        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().clickSecondSlot();
        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_TO_COURIER.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();
        checkoutNew().clickConfirmOrder();

        userShipment().waitPageLoad();
        userShipment().checkProductsList(firstShipmentLineItems);
    }

    @CaseId(3036)
    @Test(description = "Отмена заказа со страницы заказа", groups = {REGRESSION_STF, JORMUNGANDR})
    public void testCancelOrder() {
        final var userData = UserManager.getQaUser();
        helper.makeOrder(userData, DEFAULT_CHECKOUT_SID, 5);
        helper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().waitPageLoad();
        userShipments().clickToFirstShipment();

        userShipment().waitPageLoad();
        userShipment().clickToCancelFromOrder();

        userShipment().interactShipmentCancelModal().shipmentCancelModalVisible();
        userShipment().interactShipmentCancelModal().clickToDecline();

        userShipment().interactShipmentCancelModal().shipmentCancelModalNotVisible();
        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());

        userShipment().clickToCancelFromOrder();

        userShipment().interactShipmentCancelModal().shipmentCancelModalVisible();
        userShipment().interactShipmentCancelModal().clickToAccept();
        userShipment().interactRepeatModal().checkModalWindowNotVisible();

        userShipment().checkShipmentStateCancelled();
    }

    @CaseId(3146)
    @Test(description = "Отображение информации о заказе | заказ в статусе Принят", groups = {REGRESSION_STF, JORMUNGANDR})
    public void testShipmentReadyView() {
        final var userData = UserManager.getQaUser();
        //addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
        var order = helper.makeOrderOnTomorrow(userData, DEFAULT_AUCHAN_SID, 6);
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().waitPageLoad();
        userShipments().clickToFirstShipment();

        userShipment().waitPageLoad();
        userShipment().checkShipmentTitle("Ваш заказ");
        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());

        userShipment().checkAdditionalOrderTitle("Нужно ещё что-нибудь?");
        userShipment().checkAdditionalOrderText("Добавьте товары в корзину и перенесите в заказ. Сборщик их соберёт");

        userShipment().checkFilterAllButtonVisible();
        userShipment().checkFilterCollectedButtonVisible();
        userShipment().checkFilterReplacedButtonVisible();
        userShipment().checkFilterCancelledButtonVisible();
        userShipment().checkActiveProductListFilter("Все");

        userShipment().checkSeeMoreButtonVisible();
        userShipment().clickSeeMore();
        userShipment().checkSeeMoreButtonNotVisible();

        userShipment().checkProductListView(order.getShipments().get(0).getLineItems().size(), "ожидает");

        userShipment().checkShipmentNumber(order.getShipments().get(0).getNumber());
        userShipment().checkShippingAddress(order.getAddress().getFullAddress());
        userShipment().checkUserPhone(StringUtil.convertDigitsStringToPhoneNumber(userData.getPhone()));
        userShipment().checkOrderTotalCost(order.getTotal());

        //userShipment().checkShipmentSummaryCost(order.getShipTotal());

        userShipment().checkShipmentCostVisible();
        userShipment().checkAssemblyCostVisible();

        userShipment().checkRepeatOrderButtonVisible();
        userShipment().checkCancelOrderButtonVisible();
    }

    @CaseId(3149)
    @Test(description = "Отображение информации о заказе | заказ в статусе Собирается", groups = {REGRESSION_STF, JORMUNGANDR})
    public void testShipmentCollectingView() {
        final var userData = UserManager.getQaUser();
        //addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
        var order = helper.makeOrderOnTomorrow(userData, DEFAULT_AUCHAN_SID, 6);
        helper.updateShipmentStateByOrderNumber(order.getNumber(), COLLECTING.getValue());
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().waitPageLoad();
        userShipments().clickToFirstShipment();

        userShipment().waitPageLoad();
        userShipment().checkShipmentTitle("Ваш заказ");
        userShipment().checkActiveShipmentState(COLLECTING_STATE.getName());
        //Уточнить по пояснению к статусу

        userShipment().checkAdditionalOrderTitle("Заказ собирают");
        userShipment().checkAdditionalOrderText("К нему не получится добавить товары");

        userShipment().checkFilterAllButtonVisible();
        userShipment().checkFilterCollectedButtonVisible();
        userShipment().checkFilterReplacedButtonVisible();
        userShipment().checkFilterCancelledButtonVisible();
        userShipment().checkActiveProductListFilter("Все");

        userShipment().checkSeeMoreButtonVisible();
        userShipment().clickSeeMore();
        userShipment().checkSeeMoreButtonNotVisible();

        userShipment().checkProductListView(order.getShipments().get(0).getLineItems().size());

        userShipment().checkShipmentNumber(order.getShipments().get(0).getNumber());
        userShipment().checkShippingAddress(order.getAddress().getFullAddress());
        userShipment().checkUserPhone(StringUtil.convertDigitsStringToPhoneNumber(userData.getPhone()));
        userShipment().checkOrderTotalCost(order.getTotal());

        //userShipment().checkShipmentSummaryCost(order.getShipTotal());
        userShipment().checkShipmentCostVisible();
        userShipment().checkAssemblyCostVisible();

        userShipment().checkRepeatOrderButtonVisible();
        userShipment().checkCancelOrderButtonVisible();
    }

    @CaseId(3147)
    @Test(description = "Отображение информации о заказе | заказ в статусе Собран", groups = {REGRESSION_STF, JORMUNGANDR})
    public void testShipmentCollectedView() {
        final var userData = UserManager.getQaUser();
        //addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
        var order = helper.makeOrderOnTomorrow(userData, DEFAULT_AUCHAN_SID, 6);
        helper.updateShipmentStateByOrderNumber(order.getNumber(), READY_TO_SHIP.getValue());
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().waitPageLoad();
        userShipments().clickToFirstShipment();

        userShipment().waitPageLoad();
        userShipment().checkShipmentTitle("Ваш заказ");
        userShipment().checkActiveShipmentState(READY_TO_SHIP_STATE.getName());

        userShipment().checkAdditionalOrderTitle("Заказ собран");
        userShipment().checkAdditionalOrderText("К нему не получится добавить товары");

        userShipment().checkFilterAllButtonVisible();
        userShipment().checkFilterCollectedButtonVisible();
        userShipment().checkFilterReplacedButtonVisible();
        userShipment().checkFilterCancelledButtonVisible();
        userShipment().checkActiveProductListFilter("Все");

        userShipment().checkSeeMoreButtonVisible();
        userShipment().clickSeeMore();
        userShipment().checkSeeMoreButtonNotVisible();

        userShipment().checkProductListView(order.getShipments().get(0).getLineItems().size());

        userShipment().checkShipmentNumber(order.getShipments().get(0).getNumber());
        userShipment().checkShippingAddress(order.getAddress().getFullAddress());
        userShipment().checkUserPhone(StringUtil.convertDigitsStringToPhoneNumber(userData.getPhone()));
        userShipment().checkOrderTotalCost(order.getTotal());

        //userShipment().checkShipmentSummaryCost(order.getShipTotal());
        userShipment().checkShipmentCostVisible();
        userShipment().checkAssemblyCostVisible();

        userShipment().checkRepeatOrderButtonVisible();
        userShipment().checkCancelOrderButtonVisible();
    }

    @CaseId(3148)
    @Test(description = "Отображение информации о заказе | заказ в статусе В пути", groups = {REGRESSION_STF, JORMUNGANDR})
    public void testShipmentShippingView() {
        final var userData = UserManager.getQaUser();
        //addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
        var order = helper.makeOrderOnTomorrow(userData, DEFAULT_AUCHAN_SID, 6);
        helper.updateShipmentStateByOrderNumber(order.getNumber(), SHIPPING.getValue());
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().waitPageLoad();
        userShipments().clickToFirstShipment();

        userShipment().waitPageLoad();
        userShipment().checkShipmentTitle("Ваш заказ");
        userShipment().checkActiveShipmentState(SHIPPING_STATE.getName());

        userShipment().checkAdditionalOrderTitle("Заказ в пути");
        userShipment().checkAdditionalOrderText("К нему не получится добавить товары");

        userShipment().checkFilterAllButtonVisible();
        userShipment().checkFilterCollectedButtonVisible();
        userShipment().checkFilterReplacedButtonVisible();
        userShipment().checkFilterCancelledButtonVisible();
        userShipment().checkActiveProductListFilter("Все");

        userShipment().checkSeeMoreButtonVisible();
        userShipment().clickSeeMore();
        userShipment().checkSeeMoreButtonNotVisible();

        userShipment().checkProductListView(order.getShipments().get(0).getLineItems().size());

        userShipment().checkShipmentNumber(order.getShipments().get(0).getNumber());
        userShipment().checkShippingAddress(order.getAddress().getFullAddress());
        userShipment().checkUserPhone(StringUtil.convertDigitsStringToPhoneNumber(userData.getPhone()));
        userShipment().checkOrderTotalCost(order.getTotal());

        //userShipment().checkShipmentSummaryCost(order.getShipTotal());
        userShipment().checkShipmentCostVisible();
        userShipment().checkAssemblyCostVisible();

        userShipment().checkRepeatOrderButtonVisible();
        userShipment().checkCancelOrderButtonInvisible();
    }

    @CaseId(3150)
    @Test(description = "Отображение информации о заказе | заказ в статусе Получен", groups = {REGRESSION_STF, JORMUNGANDR})
    public void testShipmentShippedView() {
        final var userData = UserManager.getQaUser();
        //addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
        var order = helper.makeAndCompleteOrder(userData, DEFAULT_AUCHAN_SID, 6);
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().waitPageLoad();
        userShipments().interactOrderEvaluationModal().checkOrderEvaluationModalDisplayed();
        userShipments().interactOrderEvaluationModal().close();
        userShipments().interactOrderEvaluationModal().checkOrderEvaluationModalNotDisplayed();
        userShipments().clickToFirstShipment();

        userShipment().waitPageLoad();
        userShipment().checkRetailerLabelVisible();
        userShipment().checkShippedDateTitleVisible();
        userShipment().checkShippedDateVisible();

        //Оценка заказа под АБ
        userShipment().checkRateBlockVisible();

        userShipment().checkFilterAllButtonVisible();
        userShipment().checkFilterCollectedButtonVisible();
        userShipment().checkFilterReplacedButtonVisible();
        userShipment().checkFilterCancelledButtonVisible();
        userShipment().checkActiveProductListFilter("Все");

        userShipment().checkSeeMoreButtonVisible();
        userShipment().clickSeeMore();
        userShipment().checkSeeMoreButtonNotVisible();

        userShipment().checkProductListView(order.getShipments().get(0).getLineItems().size());

        userShipment().checkShipmentNumber(order.getShipments().get(0).getNumber());
        userShipment().checkShippingAddress(order.getAddress().getFullAddress());
        userShipment().checkUserPhone(StringUtil.convertDigitsStringToPhoneNumber(userData.getPhone()));
        userShipment().checkOrderTotalCost(order.getTotal());

        //userShipment().checkShipmentSummaryCost(order.getShipTotal());
        userShipment().checkShipmentCostVisible();
        userShipment().checkAssemblyCostVisible();

        userShipment().checkRepeatOrderButtonVisible();
        userShipment().checkCancelOrderButtonInvisible();
    }

    @CaseId(3151)
    @Test(description = "Отображение информации о заказе | заказ в статусе Отменен", groups = {REGRESSION_STF, JORMUNGANDR})
    public void testShipmentCancelledView() {
        final var userData = UserManager.getQaUser();
        //addFlipperActor("tmp_b2c_9162_spree_shipment_changes", userData.getId());
        var order = helper.makeOrderOnTomorrow(userData, DEFAULT_AUCHAN_SID, 6);
        helper.updateShipmentStateByOrderNumber(order.getNumber(), CANCELED.getValue());
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().waitPageLoad();
        userShipments().clickToFirstShipment();

        userShipment().waitPageLoad();
        userShipment().checkShipmentTitle("Ваш заказ");

        userShipment().checkShipmentStateCancelled();

        userShipment().checkFilterAllButtonVisible();
        userShipment().checkFilterCollectedButtonVisible();
        userShipment().checkFilterReplacedButtonVisible();
        userShipment().checkFilterCancelledButtonVisible();
        userShipment().checkActiveProductListFilter("Все");

        userShipment().checkSeeMoreButtonVisible();
        userShipment().clickSeeMore();
        userShipment().checkSeeMoreButtonNotVisible();

        userShipment().checkProductListView(order.getShipments().get(0).getLineItems().size());

        userShipment().checkShipmentNumber(order.getShipments().get(0).getNumber());
        userShipment().checkShippingAddress(order.getAddress().getFullAddress());
        userShipment().checkUserPhone(StringUtil.convertDigitsStringToPhoneNumber(userData.getPhone()));
        userShipment().checkOrderTotalCost(order.getTotal());

        //userShipment().checkShipmentSummaryCost(order.getShipTotal());
        userShipment().checkShipmentCostVisible();
        userShipment().checkAssemblyCostVisible();

        userShipment().checkRepeatOrderButtonVisible();
        userShipment().checkCancelOrderButtonInvisible();
    }

    @CaseId(3703)
    @Test(description = "Отображение информации об оценке заказа | заказ НЕ оценен", groups = {REGRESSION_STF, JORMUNGANDR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testNotRatedShipmentView() {
        final var userData = UserManager.getQaUser();
        helper.makeAndCompleteOrder(userData, DEFAULT_AUCHAN_SID, 6);
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().waitPageLoad();
        userShipments().interactOrderEvaluationModal().checkOrderEvaluationModalDisplayed();
        userShipments().interactOrderEvaluationModal().close();
        userShipments().interactOrderEvaluationModal().checkOrderEvaluationModalNotDisplayed();
        userShipments().clickToFirstShipment();

        userShipment().waitPageLoad();
        userShipment().checkRateBlockVisible();
        userShipment().checkRateBlockStarsVisible();
        userShipment().hoverThirdStar();
    }

    @CaseId(3704)
    @Test(description = "Отображение информации об оценке заказа | заказ Оценен", groups = {REGRESSION_STF, JORMUNGANDR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testRatedShipmentView() {
        final var userData = UserManager.getQaUser();
        helper.makeAndCompleteOrder(userData, DEFAULT_AUCHAN_SID, 6);
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().waitPageLoad();
        userShipments().interactOrderEvaluationModal().checkOrderEvaluationModalDisplayed();
        userShipments().interactOrderEvaluationModal().setEvaluationValue(5);
        userShipments().interactOrderEvaluationModal().clickSend();
        userShipments().interactOrderEvaluationModal().checkOrderEvaluationModalNotDisplayed();

        userShipments().clickToFirstShipment();

        userShipment().waitPageLoad();
        userShipment().checkRateBlockStarsNotVisible();
        userShipment().checkRateText("Ваша оценка — отлично");
    }

    @CaseId(3705)
    @Test(description = "Отображение информации об оценке заказа | заказ Не оценен, время для оценки прошло", groups = {REGRESSION_STF, JORMUNGANDR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testTooLateToRatedShipmentView() {
        final var userData = UserManager.getQaUser();
        helper.makeAndCompleteOrderWithDate(userData, DEFAULT_AUCHAN_SID, 6, LocalDateTime.now().minusDays(8));
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().waitPageLoad();
        userShipments().interactOrderEvaluationModal().checkOrderEvaluationModalNotDisplayed();

        userShipments().clickToFirstShipment();

        userShipment().waitPageLoad();
        userShipment().checkRateBlockStarsNotVisible();
        userShipment().checkRateText("Время для оценки заказа закончилось");
    }

    @CaseId(3802)
    @Test(description = "Оценка заказа с экрана информации о заказе", groups = {REGRESSION_STF, JORMUNGANDR})
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT"})
    public void testRateShipment() {
        final var userData = UserManager.getQaUser();
        helper.makeAndCompleteOrder(userData, DEFAULT_AUCHAN_SID, 6);
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().waitPageLoad();
        userShipments().interactOrderEvaluationModal().checkOrderEvaluationModalDisplayed();
        userShipments().interactOrderEvaluationModal().close();
        userShipments().interactOrderEvaluationModal().checkOrderEvaluationModalNotDisplayed();
        userShipments().clickToFirstShipment();

        userShipment().waitPageLoad();
        userShipment().checkRateBlockVisible();
        userShipment().clickStar(5);
        userShipment().interactOrderEvaluationModal().checkOrderEvaluationModalDisplayed();
        userShipment().interactOrderEvaluationModal().checkEvaluationRate(5);
        userShipment().interactOrderEvaluationModal().clickSend();
        userShipment().interactOrderEvaluationModal().checkOrderEvaluationModalNotDisplayed();
        userShipment().checkRateText("Ваша оценка — отлично");
    }

    @CaseId(3153)
    @Test(description = "Фильтр товаров 'Собрано' на экране информации о заказе", groups = {REGRESSION_STF, JORMUNGANDR})
    public void testCollectedFilter() {
        final var userData = UserManager.getQaUser();
        var order = helper.makeOrderOnTomorrow(userData, DEFAULT_AUCHAN_SID, 5);
        //TODO Смена статуса итема в заказе
        changeToAssembled(order.getShipments().get(0).getNumber(), "0");
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().waitPageLoad();
        userShipments().clickToFirstShipment();

        userShipment().waitPageLoad();

        userShipment().checkFilterCollectedButtonVisible();
        userShipment().selectCollectedFilter();
        userShipment().checkActiveProductListFilter("Собрано");

        userShipment().checkFirstEmptyFilteredMessage("Все собранные без замен товары появятся здесь");
    }

    @CaseId(3154)
    @Test(description = "Фильтр товаров 'Замены' на экране информации о заказе", groups = {REGRESSION_STF, JORMUNGANDR})
    public void testReplacementFilter() {
        final var userData = UserManager.getQaUser();
        helper.makeOrderOnTomorrow(userData, DEFAULT_AUCHAN_SID, 5);
        //TODO смена статуса итема в заказе
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().waitPageLoad();
        userShipments().clickToFirstShipment();

        userShipment().waitPageLoad();

        userShipment().checkFilterReplacedButtonVisible();
        userShipment().selectReplacementFilter();
        userShipment().checkActiveProductListFilter("Замены");

        userShipment().checkFirstEmptyFilteredMessage(
                "Иногда в магазине может не оказаться некоторых товаров или их качество неудовлетворительное");
        userShipment().checkSecondEmptyFilteredMessage(
                "Во время комплектации заказа " + "все произведенные замены товаров появятся здесь");
        userShipment().checkThirdEmptyFilteredMessage(
                "Стоимость заказа будет скорректирована с учетом замен");
    }

    @CaseId(3155)
    @Test(description = "Фильтр товаров 'Отмены' на экране информации о заказе", groups = {REGRESSION_STF, JORMUNGANDR})
    public void testCancelFilter() {
        final var userData = UserManager.getQaUser();
        var order = helper.makeOrderOnTomorrow(userData, DEFAULT_AUCHAN_SID, 5);
        //TODO смена статуса итема в заказе
        changeItemToCancel(order.getShipments().get(0).getNumber(), "0");
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().waitPageLoad();
        userShipments().clickToFirstShipment();

        userShipment().waitPageLoad();

        userShipment().checkFilterReplacedButtonVisible();
        userShipment().selectCancelledFilter();
        userShipment().checkActiveProductListFilter("Отмены");

        userShipment().checkFirstEmptyFilteredMessage(
                "Иногда в магазине может не оказаться некоторых товаров или их качество неудовлетворительное");
        userShipment().checkSecondEmptyFilteredMessage(
                "Во время комплектации заказа " + "все произведенные отмены товаров появятся здесь");
        userShipment().checkThirdEmptyFilteredMessage(
                "Стоимость заказа будет скорректирована с учетом отмен");
    }

    @CaseId(3536)
    @Test(description = "Вывод информации о дозаказе / заказ доставлен или отменен", groups = {REGRESSION_STF, JORMUNGANDR})
    public void testAdditionalOrderInfoIfMainOrderCompleteCancel() {
        final var userData = UserManager.getQaUser();
        helper.makeAndCompleteOrder(userData, DEFAULT_AUCHAN_SID, 1);
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().waitPageLoad();
        userShipments().interactOrderEvaluationModal().checkOrderEvaluationModalDisplayed();
        userShipments().interactOrderEvaluationModal().close();
        userShipments().interactOrderEvaluationModal().checkOrderEvaluationModalNotDisplayed();
        userShipments().clickToFirstShipment();

        userShipment().waitPageLoad();
        userShipment().checkAdditionalOrderInfoNotVisible();
    }

    @CaseId(3565)
    @Test(description = "Вывод информации о дозаказе / редактирование заказа в магазине запрещено", groups = {REGRESSION_STF, JORMUNGANDR})
    public void testAdditionalOrderInfoEditOffInStore() {
        final var userData = UserManager.getQaUser();
        helper.makeOrder(userData, DEFAULT_AZBUKAVKUSA_SID, 1);
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().waitPageLoad();
        userShipments().clickToFirstShipment();

        userShipment().waitPageLoad();

        userShipment().checkAdditionalOrderTitle("Заказ принят");
        userShipment().checkAdditionalOrderText("В этом магазине нельзя добавить товары к заказу");
    }

    @CaseId(3533)
    @Test(description = "Вывод информации о дозаказе / кончилось время для редактирования заказа", groups = {REGRESSION_STF, JORMUNGANDR})
    public void testAdditionalOrderInfoEditOutOfTime() {
        final var userData = UserManager.getQaUser();
        helper.makeOrder(userData, DEFAULT_AUCHAN_SID, 1);
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().waitPageLoad();
        userShipments().clickToFirstShipment();

        userShipment().waitPageLoad();

        userShipment().checkAdditionalOrderTitle("Заказ скоро соберут");
        userShipment().checkAdditionalOrderText("К нему не получится добавить товары");
    }

    @CaseIDs({@CaseId(3535), @CaseId(2062)})
    @Test(description = "Вывод информации о дозаказе / в заказе есть алкоголь", groups = {REGRESSION_STF, JORMUNGANDR})
    public void testAdditionalOrderInfoEditAlcohol() {
        final var userData = UserManager.getQaUser();

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

        shop().goToPage(ShopUrl.METRO.getUrl() + ALCOHOL_CATEGORY_LINK);
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

        userShipment().checkAdditionalOrderTitle("В заказе есть алкоголь");
        userShipment().checkAdditionalOrderText("К нему не получится добавить товары");
        userShipment().checkAlcoholInfoVisible();
        userShipment().checkAlcoholInfoText(
                "Реализация зарезервированной алкогольной продукции происходит по цене в торговом зале на дату самовывоза заказа и может отличаться от цены товара на сайте.");
    }

    @CaseId(3033)
    @Test(description = "Открытие карточки товара по нажатию на товар", groups = {REGRESSION_STF, JORMUNGANDR})
    public void testOpenProductCard() {
        final var userData = UserManager.getQaUser();
        helper.makeOrder(userData, DEFAULT_AUCHAN_SID, 1);
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().waitPageLoad();
        userShipments().clickToFirstShipment();

        userShipment().waitPageLoad();

        userShipment().clickFirstProductName();
        userShipment().interactProductCard().checkProductCardVisible();
    }
}