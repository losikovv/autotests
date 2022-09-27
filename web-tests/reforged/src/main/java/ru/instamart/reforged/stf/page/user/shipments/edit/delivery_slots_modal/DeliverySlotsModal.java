package ru.instamart.reforged.stf.page.user.shipments.edit.delivery_slots_modal;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.StringUtil;

public final class DeliverySlotsModal implements DeliverySlotsModalCheck {
    @Step("Кликаем на '{dayPosition}-й' день")
    public void clickOnDay(final int dayPosition) {
        deliverySlotsDays.getElements().get(dayPosition - 1).click();
    }

    @Step("Получаем наименование '{dayPosition}-го' дня")
    public String getDate(final int dayPosition) {
        return deliverySlotsDays.getElements().get(dayPosition - 1).getText();
    }

    @Step("Кликаем на '{slotPosition}-й' слот")
    public void clickOnSlot(final int slotPosition) {
        slots.getElements().get(slotPosition - 1).click();
    }

    @Step("Получаем время выбранного слота доставки")
    public String getSelectedDeliveryTime() {
        return selectedSlotTime.getText().replaceAll("\\.", "");
    }

    @Step("Получаем стоимость выбранного слота доставки")
    public String getSelectedDeliveryCost() {
        return selectedSlotCost.getText();
    }

    @Step("Выбираем слот с большей стоимостью доставки")
    public void selectSlotWithHigherPrice(){
        slotsWithHigherDeliveryPrice.clickOnFirst();
    }

    @Step("Выбираем слот с меньшей стоимостью доставки")
    public void selectSlotWithLowerPrice(){
        slotsWithLowerDeliveryPrice.clickOnFirst();
    }

    @Step("Нажимаем 'Выбрать'")
    public void clickApply() {
        applyChanges.click();
    }

    @Step("Нажимаем 'Закрыть'")
    public void clickClose() {
        close.click();
    }
}