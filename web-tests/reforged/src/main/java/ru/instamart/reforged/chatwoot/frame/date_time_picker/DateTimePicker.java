package ru.instamart.reforged.chatwoot.frame.date_time_picker;

import io.qameta.allure.Step;

public final class DateTimePicker implements DateTimePickerCheck {
    @Step("Выбираем первый день текущего месяца в календаре")
    public void clickFirstDayOfMonth() {
        firstDayOfThisMonth.click();
    }

    @Step("Выбираем текущий день в календаре")
    public void clickTodayInCalendar() {
        today.click();
    }

    @Step("Выбираем следующий за текущим день в календаре")
    public void clickTomorrowInCalendar() {
        tomorrow.click();
    }

    @Step("Нажимаем 'Ok' в календаре")
    public void clickOkInCalendar() {
        okDateTime.click();
    }

}
