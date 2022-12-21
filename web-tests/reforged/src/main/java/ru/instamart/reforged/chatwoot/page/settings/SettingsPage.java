package ru.instamart.reforged.chatwoot.page.settings;

import ru.instamart.reforged.chatwoot.ChatwootPage;
import ru.instamart.reforged.chatwoot.block.menu_vertical_primary.PrimaryMenu;
import ru.instamart.reforged.chatwoot.block.menu_vertical_secondary.SecondaryMenu;
import ru.instamart.reforged.chatwoot.frame.notification_right.NotificationRight;
import ru.instamart.reforged.chatwoot.frame.unread_notifications_modal.UnreadNotificationsModal;

public final class SettingsPage implements ChatwootPage, SettingsCheck {

    public PrimaryMenu interactLeftMenu() {
        return primaryVerticalMenu;
    }

    public SecondaryMenu interactSecondaryMenu() {
        return secondaryVerticalMenu;
    }

    public NotificationRight interactNotification() {
        return notificationRight;
    }

    public UnreadNotificationsModal interactUnreadNotificationsModal() {
        return unreadNotificationModal;
    }

    @Override
    public void goToPage() {
        goToPage(pageUrl());
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
