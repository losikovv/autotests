package ru.instamart.reforged.chatwoot.frame.edit_popover;

import io.qameta.allure.Step;

public final class EditPopover implements EditPopoverCheck {

    @Step("Вводим в поле 'Номер доставки' значение: '{shipmentNumber}'")
    public void fillShipmentNumber(final String shipmentNumber){
        shipmentInput.clear();
        shipmentInput.fill(shipmentNumber);
    }

    @Step("Нажимаем в поле ввода 'Тема' в окне редактирования")
    public void clickTopicInput() {
        topicInput.click();
    }

    @Step("Выбираем первую тему из выпадающего списка тем в окне редактирования")
    public void selectFirstTopic() {
        topicsList.clickOnFirst();
    }

    @Step("Нажимаем 'Сохранить' в всплывающем окне редактирования")
    public void clickSubmitEditPopover() {
        submitEdit.click();
    }

}
