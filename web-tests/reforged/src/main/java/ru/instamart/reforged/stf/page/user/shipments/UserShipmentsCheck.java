package ru.instamart.reforged.stf.page.user.shipments;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface UserShipmentsCheck extends Check, UserShipmentsElement {

    @Step("Проверка отмены статуса")
    default void checkStatusWasCanceled() {
        waitAction().shouldBeVisible(shipmentStatusCancel);
    }

    @Step("Проверка статуса заказа 'Ожидает отправки'")
    default void checkStatusShipmentReady() {
        waitAction().shouldBeVisible(shipmentStatusShipmentReady);
    }

    @Step("Проверка введенного промокода на странице статуса заказа")
    default void checkUserShipmentPromocodeVisible() {
        waitAction().shouldBeVisible(userShipmentPromocode);
    }

    @Step("Проверка метода оплаты 'Картой онлайн'")
    default void checkPaymentMethodCardOnline() {
        waitAction().shouldBeVisible(paymentMethodCardOnline);
    }

    @Step("Проверка метода оплаты 'Картой курьеру'")
    default void checkPaymentMethodCardToCourier() {
        waitAction().shouldBeVisible(paymentMethodCardToCourier);
    }

    @Step("Проверка метода оплаты 'По счёту для бизнеса'")
    default void checkPaymentMethodForBusiness() {
        waitAction().shouldBeVisible(paymentMethodForBusiness);
    }

    @Step("Проверка метода политики замен {0}")
    default void checkReplacementMethod(final String data) {
        waitAction().shouldBeVisible(replacementPolicy, data);
    }
}
