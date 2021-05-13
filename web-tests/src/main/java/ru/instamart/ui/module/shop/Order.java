package ru.instamart.ui.module.shop;

import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.ui.module.testdata.PaymentTypes;
import ru.instamart.ui.common.lib.Addresses;
import ru.instamart.ui.common.lib.Pages;
import ru.instamart.ui.common.pagesdata.OrderDetailsData;
import ru.instamart.ui.helper.PaymentHelper;
import ru.instamart.ui.helper.WaitingHelper;
import ru.instamart.ui.module.Base;
import ru.instamart.ui.module.checkout.Checkout;
import ru.instamart.ui.module.Shop;
import ru.instamart.ui.module.checkout.AddressSteps;
import ru.instamart.ui.module.checkout.BonusesActions;
import ru.instamart.ui.module.checkout.PromocodeActions;
import ru.instamart.ui.module.checkout.RetailerCardsActions;
import ru.instamart.ui.Elements;

public final class Order extends Base {
    private static final Logger log = LoggerFactory.getLogger(Order.class);

    public Order(final AppManager kraken) {
        super(kraken);
    }

    /** Оформить тестовый заказ */
    @Step("Оформляем тестовы заказ")
    public static void order() {
        log.info("> оформляем тестовы заказ");
        if (!kraken.detect().isShippingAddressSet()) {
            ShippingAddressModal.open();
            ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
            ShippingAddressModal.submit();
        }
        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();
    }

    @Step("Заполнение чекаута")
    public static void makeOrder(OrderDetailsData orderDetails) {
        log.info("Заполнение чекаута");
        fillOrderDetails(orderDetails);
        if(orderDetails.getPromocode() != null) {
            PromocodeActions.add(orderDetails.getPromocode());}
        if(orderDetails.getBonus() != null) {
//            ru.instamart.ui.modules.checkout.AddressSteps .Bonuses.add(orderDetails.getBonus());}
            BonusesActions.add(orderDetails.getBonus());}
        if(orderDetails.getRetailerCard() != null) {
            RetailerCardsActions.addCard(orderDetails.getRetailerCard());}
        sendOrderFromSidebar();
        if(orderDetails.getPaymentDetails().getPaymentType().getDescription().equalsIgnoreCase(PaymentTypes.cardOnline().getDescription())) {
            if (orderDetails.getPaymentDetails().getCreditCard().getSecure()){
                PaymentHelper.cloudpaymentsFlow();}
        }
    }

    @Step("Заполнение шагов чекаута")
    public static void fillOrderDetails(OrderDetailsData orderDetails) {
        Checkout.initCheckout();
        for (int position = 1; position <= 5; position++) {
            log.info("Заполнение шага номер {}", position);
            AddressSteps.fillStep(position,orderDetails);
        }
    }

    @Step("Нажатие кнопки Оформление заказа в сайдбаре")
    public static void sendOrderFromSidebar() {
        log.info("Отправляем заказ...");
        kraken.await().fluently(
                ExpectedConditions.elementToBeClickable(
                        Elements.Checkout.SideBar.sendOrderButton().getLocator()),
                "Неактивна кнопка отправки заказа\n");
        kraken.perform().click(Elements.Checkout.SideBar.sendOrderButton());
        kraken.await().fluently(
                ExpectedConditions.invisibilityOfElementLocated(
                        Elements.Checkout.header().getLocator()),
                "Превышено время ожидания отправки заказа\n");
        log.info("✓ Заказ оформлен");
    }

    /** Повторить крайний заказ с экрана истории заказов */
    @Step("Повторяем крайний заказ с экрана истории заказов")
    public static void repeatLastOrder() {
        log.info("Повторяем крайний заказ");
        kraken.get().page(Pages.UserProfile.shipments());
        if (kraken.detect().isOrdersHistoryEmpty()) {
            throw new AssertionError("Невозможно повторить заказ, у пользователя нет заказов в истории\n");
        } else {
            kraken.perform().click(Elements.UserProfile.OrdersHistoryPage.order.repeatButton());
            kraken.await().fluently(
                    ExpectedConditions.visibilityOfElementLocated(Elements.Cart.drawer().getLocator()),
                    "Не добавились товары в корзину при повторе заказа\n");
        }
    }

    /** Повторить заказ с экрана деталей заказа */
    @Step("Повторяем заказ с экрана деталей заказа")
    public static void repeatOrder() {
        log.info("Повторяем заказ");
        kraken.perform().click(Elements.UserProfile.OrderDetailsPage.OrderSummary.repeatOrderButton());
        kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(
                Elements.UserProfile.OrderDetailsPage.RepeatOrderModal.yesButton().getLocator()));
        kraken.perform().click(Elements.UserProfile.OrderDetailsPage.RepeatOrderModal.yesButton());
        kraken.await().fluently(
                ExpectedConditions.visibilityOfElementLocated(Elements.Cart.drawer().getLocator()),
                "Не добавились товары в корзину при повторе заказа\n");
    }

    /** Отменить крайний активный заказ */
    @Step("Отменяем крайний активный заказ")
    public static void cancelLastActiveOrder() {
        log.info("> отменяем крайний активный заказ");
        kraken.get().page(Pages.UserProfile.shipments());
        kraken.perform().click(Elements.UserProfile.OrdersHistoryPage.activeOrdersFilterButton());
        if(!kraken.detect().isElementPresent(Elements.UserProfile.OrdersHistoryPage.activeOrdersPlaceholder())) {
            kraken.perform().click(Elements.UserProfile.OrdersHistoryPage.order.snippet());
            cancelOrder();
        } else log.warn("> Заказ не активен");
    }

    /** Отменить заказ на странице деталей заказа */
    @Step("Отменяем заказ на странице деталей заказа")
    public static void cancelOrder() {
        log.info("> отменяем заказ на странице деталей заказа");
        kraken.perform().click(Elements.UserProfile.OrderDetailsPage.OrderSummary.cancelOrderButton());
        WaitingHelper.simply(1); // Ожидание анимации открытия модалки отмены заказа
        kraken.perform().click(Elements.UserProfile.OrderDetailsPage.CancelOrderModal.yesButton());
        kraken.await().fluently(
                ExpectedConditions.presenceOfElementLocated(Elements.UserProfile.OrderDetailsPage.CancelOrderModal.popup().getLocator()),
                "Не отменился заказ за допустимое время ожидания\n"
        );
        log.info("✓ Заказ отменен");
    }
}
