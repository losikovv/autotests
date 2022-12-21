package ru.instamart.reforged.chatwoot.page.settings;

import ru.instamart.reforged.chatwoot.block.menu_vertical_primary.PrimaryMenu;
import ru.instamart.reforged.chatwoot.block.menu_vertical_secondary.SecondaryMenu;
import ru.instamart.reforged.chatwoot.frame.notification_right.NotificationRight;
import ru.instamart.reforged.chatwoot.frame.unread_notifications_modal.UnreadNotificationsModal;

public interface SettingsElement {
    PrimaryMenu primaryVerticalMenu = new PrimaryMenu();
    SecondaryMenu secondaryVerticalMenu = new SecondaryMenu();
    NotificationRight notificationRight = new NotificationRight();
    UnreadNotificationsModal unreadNotificationModal = new UnreadNotificationsModal();
}
