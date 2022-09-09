package ru.instamart.reforged.stf.page.checkout_new.b2b_order_modal;

import io.qameta.allure.Step;

public class B2BOrderModal implements B2BOrderModalCheck {

    @Step("Нажимаем 'Да, хочу'")
    public void clickConfirm() {
        confirmButton.click();
    }

    @Step("Нажимаем 'Закрыть'")
    public void clickClose() {
        close.click();
    }
}