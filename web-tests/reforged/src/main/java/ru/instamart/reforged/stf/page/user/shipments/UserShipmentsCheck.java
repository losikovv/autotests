package ru.instamart.reforged.stf.page.user.shipments;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface UserShipmentsCheck extends Check, UserShipmentsElement {

    @Step("Проверка отмены статуса")
    default void checkStatusWasCanceled() {
        waitAction().shouldBeVisible(shipmentStatusCancel);
    }
}
