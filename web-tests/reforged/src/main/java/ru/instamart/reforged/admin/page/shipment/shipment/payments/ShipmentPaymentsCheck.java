package ru.instamart.reforged.admin.page.shipment.shipment.payments;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.util.StringUtil;
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

    @Step("Проверяем, что дебетовое сальдо отображается")
    default void checkDebetBalanceVisible() {
        waitAction().shouldBeVisible(debetBalance);
    }

    @Step("Проверяем, что дебетовое сальдо положительное")
    default void checkDebetBalancePositive() {
        Assert.assertTrue(StringUtil.stringToDouble(debetBalance.getText()) > 0, "Дебетовое сальдо 0 или отрицательное");
    }

}
