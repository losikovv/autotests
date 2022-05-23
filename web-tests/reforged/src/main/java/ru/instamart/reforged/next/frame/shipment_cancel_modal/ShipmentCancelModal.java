package ru.instamart.reforged.next.frame.shipment_cancel_modal;

import io.qameta.allure.Step;
import ru.instamart.reforged.next.frame.Close;

public final class ShipmentCancelModal implements Close, ShipmentCancelModalCheck {

    @Step("Подтвердить действие")
    public void clickToAccept() {
        accept.click();
        checkRequestsWasLoad();
    }

    @Step("Отклонить действие")
    public void clickToDecline() {
        decline.click();
    }
}
