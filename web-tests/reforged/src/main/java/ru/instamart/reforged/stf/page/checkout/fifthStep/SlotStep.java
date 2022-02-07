package ru.instamart.reforged.stf.page.checkout.fifthStep;

import io.qameta.allure.Step;

public class SlotStep implements SlotStepCheck {

    @Step("Выбрать первый активный слот")
    public void setFirstActiveSlot() {
        activeSlots.clickOnFirst();
    }

    @Step("Выбрать последний активный слот")
    public void setLastActiveSlot() {
        activeSlots.clickOnLast();
    }

    @Step("Выбрать первый активный слот")
    public void setFirstActiveSlotSecondRetailer() {
        firstActiveSlotSecondRetailer.click();
    }

    @Step("Выбрать другое время доставки")
    public void setAnotherSlot() {
        choseAnotherTimeButton.click();
    }

    @Step("Нажать продолжить")
    public void clickToSubmit() {
        submit.click();
    }
}
