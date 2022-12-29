package ru.instamart.reforged.chatwoot.frame.notification_right;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

public interface NotificationRightCheck extends Check, NotificationRightElement {

    @Step("Проверяем, что всплывающеее сообщение о массовых операциях с чатами в правом углу отображается")
    default void checkMassCloseNotificationDisplayed() {
        notificationMassCloseRefresh.should().visible();
    }

    @Step("Проверяем, что всплывающеее сообщение не отображается")
    default void checkNotificationNotDisplayed() {
        notificationMassCloseRefresh.should().invisible();
    }

}
