package ru.instamart.reforged.chatwoot.frame.date_time_picker;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

public interface DateTimePickerCheck extends Check, DateTimePickerElement {

    @Step("Проверяем, что отображается календарь выбора даты/времени")
    default void checkCalendarVisible() {
        dateTimePicker.should().visible();
    }

    @Step("Проверяем, что календарь выбора даты/времени не отображается")
    default void checkCalendarNotVisible() {
        dateTimePicker.should().invisible();
    }
}
