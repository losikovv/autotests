package ru.instamart.reforged.admin.page.shipment.shipment.payments.new_payment.payments;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ShipmentNewPaymentCheck extends Check, ShipmentNewPaymentElement {

    @Step("Проверяем, что модальное окно отправки уведомления показано")
    default void checkSendNotificationModalVisisble() {
        waitAction().shouldBeVisible(sendNotificationModal);
    }

    @Step("Проверяем, что модальное окно отправки уведомления показано")
    default void checkSendNotificationModalNotVisisble() {
        waitAction().shouldNotBeVisible(sendNotificationModal);
    }
}
