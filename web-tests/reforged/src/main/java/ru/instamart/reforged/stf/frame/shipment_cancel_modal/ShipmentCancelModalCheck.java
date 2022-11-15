package ru.instamart.reforged.stf.frame.shipment_cancel_modal;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

public interface ShipmentCancelModalCheck extends ShipmentCancelModalElement, Check {

    @Step("Проверяем что модальное окно отмены заказа показано")
    default void shipmentCancelModalVisible() {
        shipmentCancelModal.should().visible();
        Assert.assertTrue(shipmentCancelModal.is().animationFinished());
    }

    @Step("Проверяем что модальное окно отмены заказа не видно")
    default void shipmentCancelModalNotVisible() {
        Assert.assertTrue(shipmentCancelModal.is().invisible());
    }
}
