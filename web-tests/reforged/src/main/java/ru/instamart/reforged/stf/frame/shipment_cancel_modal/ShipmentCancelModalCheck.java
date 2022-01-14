package ru.instamart.reforged.stf.frame.shipment_cancel_modal;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ShipmentCancelModalCheck extends ShipmentCancelModalElement, Check {

    @Step("Проверяем что моадльное окно отмены заказа показано")
    default void shipmentCancelModalVisible() {
        waitAction().shouldBeVisible(shipmentCancelModal);
    }

    @Step("Проверяем что моадльное окно отмены заказа не видно")
    default void shipmentCancelModalNotVisible() {
        waitAction().shouldNotBeVisible(shipmentCancelModal);
    }
}
