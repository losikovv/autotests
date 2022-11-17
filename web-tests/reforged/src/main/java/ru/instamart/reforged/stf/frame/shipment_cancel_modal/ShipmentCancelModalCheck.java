package ru.instamart.reforged.stf.frame.shipment_cancel_modal;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

public interface ShipmentCancelModalCheck extends ShipmentCancelModalElement, Check {

    @Step("Проверяем что модальное окно отмены заказа показано")
    default void shipmentCancelModalVisible() {
        shipmentCancelModal.should().visible();
        shipmentCancelModal.should().animationFinished();
    }

    @Step("Проверяем что модальное окно отмены заказа не видно")
    default void shipmentCancelModalNotVisible() {
        shipmentCancelModal.should().invisible();
    }
}
