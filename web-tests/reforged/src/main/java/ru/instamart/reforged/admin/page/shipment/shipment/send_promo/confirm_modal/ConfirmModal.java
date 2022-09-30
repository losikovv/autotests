package ru.instamart.reforged.admin.page.shipment.shipment.send_promo.confirm_modal;

import io.qameta.allure.Step;

public class ConfirmModal implements ConfirmModalCheck {

    @Step("Нажимаем на кнопку 'Отправить'")
    public void clickOnSendPromo() {
        sendButton.click();
    }
}