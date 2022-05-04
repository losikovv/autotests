package ru.instamart.reforged.admin.page.shipment.shipment;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ShipmentCheck extends Check, ShipmentElement {

    @Step("Проверяем, что отобразилась информация о заказе")
    default void checkOrderInfoVisible() {
        waitAction().shouldBeVisible(orderInfo);
    }
}
