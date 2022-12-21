package ru.instamart.reforged.chatwoot.frame.unread_notifications_modal;

import io.qameta.allure.Step;

public final class UnreadNotificationsModal implements UnreadNotificationsModalCheck {

    @Step("Нажимаем на кнопку: 'Просмотр всех уведомлений'")
    public void clickSeeAll() {
        seeAllButton.click();
    }

    @Step("Закрываем модальное окно")
    public void close() {
        close.click();
    }
}
