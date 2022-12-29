package ru.instamart.reforged.chatwoot.page.analitycs;

import org.openqa.selenium.By;
import ru.instamart.reforged.chatwoot.block.menu_vertical_primary.PrimaryMenu;
import ru.instamart.reforged.chatwoot.block.menu_vertical_secondary.SecondaryMenu;
import ru.instamart.reforged.chatwoot.frame.notification_right.NotificationRight;
import ru.instamart.reforged.chatwoot.frame.unread_notifications_modal.UnreadNotificationsModal;
import ru.instamart.reforged.chatwoot.page.analitycs.tables.OperatorsTable;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Input;

public interface AnalyticsElement {
    PrimaryMenu primaryVerticalMenu = new PrimaryMenu();
    SecondaryMenu secondaryVerticalMenu = new SecondaryMenu();
    NotificationRight notificationRight = new NotificationRight();
    UnreadNotificationsModal unreadNotificationModal = new UnreadNotificationsModal();

    OperatorsTable operatorsTable = new OperatorsTable();

    Element header = new Element(By.xpath("//h1[@class='page-title'][contains(.,'Аналитика')]"), "Заголовок страницы");
    Element effectivenessTab = new Element(By.xpath("//li[contains(@data-menu-id,'effectiveness')]"), "Вкладка 'Эффективность операторов'");
    Element conversationManagementTab = new Element(By.xpath("//li[contains(@data-menu-id,'applications')]"), "Вкладка 'Управление заявками'");

    Input team = new Input(By.xpath("//input[@id='teamId']"), "Поле ввода 'Группа'");
    ElementCollection teamsList = new ElementCollection(By.xpath("//div[@id='teamId_list']/following-sibling::div[@class='rc-virtual-list']//div[contains(@class,'ant-select-item ant-select-item-option')]"), "Выпадающий список селектора 'Группа'");
    Element teamByName = new Element(ByKraken.xpathExpression("//div[@id='teamId_list']/following-sibling::div[@class='rc-virtual-list']//div[contains(@class,'ant-select-item ant-select-item-option')][contains(.,'%s')]"), "Группа в выпадающес списке по названию");

    Element statusSelector = new Element(By.xpath("//div[@class='ant-select-selector'][./span[.='Статус']]"), "Мультиселектор по статусу");
    ElementCollection statusSelectorOptions = new ElementCollection(By.xpath("//div[@id='availability_list']/following-sibling::div[@class='rc-virtual-list']//div[@label]"), "Выпадающий список статусов для выбора");
    Element statusByName = new Element(ByKraken.xpathExpression("//div[@id='availability_list']/following-sibling::div[@class='rc-virtual-list']//div[@label='%s']"), "Статус по имени в выпадающем списке");

    Button submit = new Button(By.xpath("//button[@type='submit']"), "Кнопка 'Найти'");

    ElementCollection operatorsInTable = new ElementCollection(By.xpath("//tr[@class='ant-table-row ant-table-row-level-0']"), "Список операторов в таблице");

    Element filterButtonByName = new Element(ByKraken.xpathExpression("//span[contains(@class,'ant-radio-button')]/following-sibling::span[contains(.,'%s')]"), "Кнопка-фильтр по названию");
    Element conversationInTableByName = new Element(ByKraken.xpathExpression("//a[contains(.,'%s')]"), "Заявка по названию (Инициатору)");
}
