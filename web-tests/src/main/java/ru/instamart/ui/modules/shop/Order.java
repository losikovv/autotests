package ru.instamart.ui.modules.shop;

import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.instamart.core.common.AppManager;
import ru.instamart.ui.common.lib.Addresses;
import ru.instamart.ui.common.lib.Pages;
import ru.instamart.ui.helpers.WaitingHelper;
import ru.instamart.ui.modules.Base;
import ru.instamart.ui.modules.Shop;
import ru.instamart.ui.objectsmap.Elements;

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
