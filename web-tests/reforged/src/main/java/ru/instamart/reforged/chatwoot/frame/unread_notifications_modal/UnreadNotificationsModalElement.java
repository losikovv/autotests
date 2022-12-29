package ru.instamart.reforged.chatwoot.frame.unread_notifications_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface UnreadNotificationsModalElement {

    Element unreadNotificationModal = new Element(By.xpath("//div[contains(@class,'notification-wrap')]"), "Модальное окно непрочитанных уведомлений");
    Element noUnreadMessage = new Element(By.xpath("//div[contains(@class,'notification-wrap')]//h3[contains(.,'У вас нет непрочитанных уведомлений')]"), "Сообщение от отсутствии непрочитанных уведомлений");
    Button seeAllButton = new Button(By.xpath("//div[contains(@class,'notification-wrap')]//button[contains(.,'Просмотр всех уведомлений')]"), "Кнопка 'Просмотр всех уведомлений'");
    Button close = new Button(By.xpath("//div[contains(@class,'notification-wrap')]//button[contains(@class,'button--only-icon')]"), "Кнопка 'X'");
}