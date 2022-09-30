package ru.instamart.reforged.admin.page.shipment.shipment.send_promo.confirm_modal;

import io.qameta.allure.Step;

public interface ConfirmModalCheck extends ConfirmModalElement {

    @Step("Проверяем, что отобразилось модальное окно")
    default void checkModalVisible() {
        modal.should().visible();
        modal.should().notAnimated();
    }
}