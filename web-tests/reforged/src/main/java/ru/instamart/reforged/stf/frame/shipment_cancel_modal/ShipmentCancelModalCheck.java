package ru.instamart.reforged.stf.frame.shipment_cancel_modal;

import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ShipmentCancelModalCheck extends ShipmentCancelModalElement, Check {

    default void shipmentCancelModalVisible() {
        waitAction().shouldBeVisible(shipmentCancelModal);
    }

    default void shipmentCancelModalNotVisible() {
        waitAction().shouldNotBeVisible(shipmentCancelModal);
    }
}
