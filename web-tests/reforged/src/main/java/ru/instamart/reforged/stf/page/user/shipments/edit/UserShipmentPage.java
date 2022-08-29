package ru.instamart.reforged.stf.page.user.shipments.edit;

import io.qameta.allure.Step;
import ru.instamart.reforged.stf.frame.RepeatModal;
import ru.instamart.reforged.stf.frame.shipment_cancel_modal.ShipmentCancelModal;
import ru.instamart.reforged.stf.page.StfPage;

public final class UserShipmentPage implements StfPage, UserShipmentCheck {

    public RepeatModal interactRepeatModal() {
        return repeatModal;
    }

    public ShipmentCancelModal interactShipmentCancelModal() {
        return shipmentCancelModal;
    }


    @Step("Нажать на кнопку 'Повторить заказ' на странице заказа")
    public void clickToRepeatFromOrder() {
        repeatOrder.click();
    }

    @Step("Нажать на кнопку 'Отменить заказ'")
    public void clickToCancelFromOrder() {
        cancelOrder.click();
    }

    @Override
    public String pageUrl() {
        return "user/shipments/%s";
    }

    @Override
    public void goToPage() {
        throw new RuntimeException("Для перехода на страницу информации о заказе необходимо использовать метод с параметрами");
    }

    public void goToPage(final String shipmentNumber) {
        goToPage(String.format(pageUrl(), shipmentNumber));
    }
}
