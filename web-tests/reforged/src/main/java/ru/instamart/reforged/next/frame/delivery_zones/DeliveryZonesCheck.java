package ru.instamart.reforged.next.frame.delivery_zones;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface DeliveryZonesCheck extends DeliveryZonesElement, Check {

    @Step("Проверяем, что модальное окно зон доставки открыто")
    default void checkDeliveryZonesModalIsOpen() {
        waitAction().shouldBeVisible(deliveryZonesModal);
    }
}
