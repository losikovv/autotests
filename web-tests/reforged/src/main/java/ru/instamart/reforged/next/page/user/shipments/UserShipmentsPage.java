package ru.instamart.reforged.next.page.user.shipments;

import io.qameta.allure.Step;
import ru.instamart.reforged.next.frame.RepeatModal;
import ru.instamart.reforged.next.frame.shipment_cancel_modal.ShipmentCancelModal;
import ru.instamart.reforged.next.page.StfPage;

public final class UserShipmentsPage implements StfPage, UserShipmentsCheck {

    public RepeatModal interactRepeatModal() {
        return repeatModal;
    }

    public ShipmentCancelModal interactShipmentCancelModal() {
        return shipmentModal;
    }

    @Step("Нажать на первый заказ")
    public void clickToFirstShipment() {
        shipments.clickOnFirst();
    }

    @Step("Нажать на кнопку 'Повторить'")
    public void clickToRepeat() {
        repeatOrder.click();
    }

    @Step("Нажать на кнопку разворачивания деталей заказа")
    public void clickToDetails() {
        orderDetailsTrigger.click();
    }

    @Step("Нажать на кнопку 'Повторить заказ' на странице заказа")
    public void clickToRepeatFromOrder() {
        repeatOrderFromOrderPage.click();
    }

    @Step("Нажать на кнопку 'Отменить заказ' на странице заказа")
    public void clickToCancelFromOrder() {
        cancelOrderFromOrderPage.click();
    }

    @Override
    public String pageUrl() {
        return "user/shipments";
    }
}
