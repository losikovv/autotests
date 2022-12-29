package ru.instamart.reforged.chatwoot.frame.notification_right;

import io.qameta.allure.Step;

public final class NotificationRight implements NotificationRightCheck {

    @Step("Нажимаем на кнопку: '{buttonText}'")
    public void clickButton(final String buttonText) {
        buttonWithText.click(buttonText);
    }

    @Step("Закрываем уведомление")
    public void close() {
        close.click();
    }
}
