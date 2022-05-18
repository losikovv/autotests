package ru.instamart.reforged.next.frame.shipment_cancel_modal;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ShipmentCancelModalCheck extends ShipmentCancelModalElement, Check {

    @Step("Проверяем что модальное окно отмены заказа показано")
    default void shipmentCancelModalVisible() {
        waitAction().shouldBeVisible(shipmentCancelModal);
        waitAction().shouldNotBeAnimated(shipmentCancelModal);
    }

    @Step("Проверяем что модальное окно отмены заказа не видно")
    default void shipmentCancelModalNotVisible() {
        waitAction().shouldNotBeVisible(shipmentCancelModal);
    }
}
