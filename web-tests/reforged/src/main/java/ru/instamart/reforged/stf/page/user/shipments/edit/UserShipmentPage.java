package ru.instamart.reforged.stf.page.user.shipments.edit;

import io.qameta.allure.Step;
import ru.instamart.reforged.stf.frame.RepeatModal;
import ru.instamart.reforged.stf.frame.shipment_cancel_modal.ShipmentCancelModal;
import ru.instamart.reforged.stf.page.StfPage;
import ru.instamart.reforged.stf.page.user.shipments.edit.delivery_slots_modal.DeliverySlotsModal;

public final class UserShipmentPage implements StfPage, UserShipmentCheck {

    public RepeatModal interactRepeatModal() {
        return repeatModal;
    }

    public ShipmentCancelModal interactShipmentCancelModal() {
        return shipmentCancelModal;
    }

    public DeliverySlotsModal interactDeliverySlotsModal() {
        return deliverySlotsModal;
    }

    @Step("Нажимаем на кнопку 'Повторить заказ' на странице заказа")
    public void clickToRepeatFromOrder() {
        repeatOrder.click();
    }

    @Step("Нажимаем на кнопку 'Отменить заказ'")
    public void clickToCancelFromOrder() {
        cancelOrder.click();
    }

    @Step("Нажимаем на кнопку 'Изменить' (слот доставки)")
    public void clickChangeDeliverySlot() {
        changeDeliverySlot.click();
    }

    @Step("Получаем номер заказа")
    public String getShipmentNumber() {
        return shipmentNumber.getText();
    }

    @Override
    public String pageUrl() {
        return "user/shipments/%s";
    }

    @Override
    public void goToPage() {
        throw new RuntimeException("Для перехода на страницу информации о заказе необходимо использовать метод с параметрами");
    }

    public void openShipmentPageByNumber(final String shipmentNumber) {
        goToPage(String.format(pageUrl(), shipmentNumber));
    }
}
