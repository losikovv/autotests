package ru.instamart.reforged.chatwoot.page.contacts;

import org.openqa.selenium.By;
import ru.instamart.reforged.chatwoot.block.menu_vertical_primary.PrimaryMenu;
import ru.instamart.reforged.chatwoot.block.menu_vertical_secondary.SecondaryMenu;
import ru.instamart.reforged.chatwoot.frame.notification_right.NotificationRight;
import ru.instamart.reforged.chatwoot.frame.unread_notifications_modal.UnreadNotificationsModal;
import ru.instamart.reforged.core.component.*;

public interface ContactsElement {
    PrimaryMenu primaryVerticalMenu = new PrimaryMenu();
    SecondaryMenu secondaryVerticalMenu = new SecondaryMenu();
    NotificationRight notificationRight = new NotificationRight();
    UnreadNotificationsModal unreadNotificationModal = new UnreadNotificationsModal();
}
