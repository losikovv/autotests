package ru.instamart.reforged.admin.page.shipment.shipment.payments;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ShipmentPaymentsCheck extends Check, ShipmentPaymentsElement {

    @Step("Проверяем, что алерт сохранения платежа показан")
    default void checkPaymentSaveAlertVisible() {
        waitAction().shouldBeVisible(successAddPaymentAlert);
    }

    @Step("Проверяем, что платеж картой при получении в статусе недействителен")
    default void checkPaymentByCardUnavailableVisible() {
        waitAction().shouldBeVisible(invalidPaymentByCard);
    }

    @Step("Проверяем, что платеж через SberPay в статусе ожидает авторизации")
    default void checkPaymentBySberPayWaitingVisible() {
        waitAction().shouldBeVisible(waitingPaymentBySberPay);
    }

    @Step("Проверяем, что платеж картой при получении в статусе оформляется")
    default void checkPaymentByCardPendingVisible() {
        waitAction().shouldBeVisible(pendingPaymentByCard);
    }
}
