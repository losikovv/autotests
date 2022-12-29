package ru.instamart.reforged.chatwoot.frame.mass_close_popover;

import io.qameta.allure.Step;
import ru.instamart.reforged.chatwoot.frame.date_time_picker.DateTimePicker;

public final class MassClosePopover implements MassClosePopoverCheck {

    public DateTimePicker interactCalendar() {
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

    @Step("Нажимаем в поле ввода 'Дата создания - С'")
    public void clickDateTimeFrom() {
        massCloseDateTimeFrom.click();
    }

    @Step("Нажимаем в поле ввода 'Дата создания - По'")
    public void clickDateTimeTo() {
        massCloseDateTimeTo.click();
    }

    @Step("Вводим в поле 'Сообщение для рассылки' текст: '{text}'")
    public void fillSendMessage(final String text) {
        massCloseMessage.fill(text);
    }

    @Step("Нажимаем ссылку 'Запросить кол-во закрываемых диалогов'")
    public void clickGetFilteredChatCount() {
        massCloseGetFilteredChatsCount.click();
    }

    @Step("Нажимаем 'Завершить'")
    public void clickSubmit() {
        submitMassClose.click();
    }

}
