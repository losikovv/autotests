package ru.instamart.reforged.chatwoot.frame.holdover_popover;

import io.qameta.allure.Step;
import ru.instamart.reforged.chatwoot.frame.date_time_picker.DateTimePicker;

public final class HoldoverPopover implements HoldoverPopoverCheck {

    public DateTimePicker interactDateTimePicker(){
        return dateTimePicker;
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

    @Step("Нажимаем в поле ввода 'Даты/Времени' в окне 'Отложить до'")
    public void clickDateTimeInput() {
        holdoverDateTimeInput.click();
    }

    @Step("Получаем дату и время 'Отложить до' из поля ввода")
    public String getHoldoverDateTime() {
        return holdoverDateTimeInput.getValue().replaceAll("\\.\\d\\d\\d\\d", "");
    }

    @Step("Нажимаем 'Отложить' во всплывающем окне 'Отложить до'")
    public void clickSubmitHoldoverPopover() {
        submitHoldover.click();
    }

}
