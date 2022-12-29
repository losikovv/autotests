package ru.instamart.reforged.chatwoot.frame.unread_notifications_modal;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

public interface UnreadNotificationsModalCheck extends Check, UnreadNotificationsModalElement {

    @Step("Проверяем, что всплывающеее сообщение отображается")
    default void checkNotificationDisplayed() {
        unreadNotificationModal.should().visible();
    }

    @Step("Проверяем, что всплывающеее сообщение не отображается")
    default void checkNotificationNotDisplayed() {
        unreadNotificationModal.should().invisible();
    }

}
