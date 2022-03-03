package ru.instamart.reforged.business.page.checkout.slotsStep;

import io.qameta.allure.Step;

public class B2BSlotStep implements B2BSlotStepCheck {

    @Step("Выбираем первый активный слот доставки")
    public void setFirstActiveSlot() {
        firstActiveSlot.click();
    }

    @Step("Нажимаем 'Продолжить'")
    public void clickToSubmit() {
        submit.click();
    }
}
