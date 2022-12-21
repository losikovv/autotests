package ru.instamart.reforged.chatwoot.page.dialogs;

import org.openqa.selenium.By;
import ru.instamart.reforged.chatwoot.block.menu_vertical_primary.PrimaryMenu;
import ru.instamart.reforged.chatwoot.block.menu_vertical_secondary.SecondaryMenu;
import ru.instamart.reforged.chatwoot.frame.contact_panel.ContactPanel;
import ru.instamart.reforged.chatwoot.frame.edit_popover.EditPopover;
import ru.instamart.reforged.chatwoot.frame.holdover_popover.HoldoverPopover;
import ru.instamart.reforged.chatwoot.frame.mass_close_popover.MassClosePopover;
import ru.instamart.reforged.chatwoot.frame.notification_right.NotificationRight;
import ru.instamart.reforged.chatwoot.frame.unread_notifications_modal.UnreadNotificationsModal;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Input;

public interface DialogsElement {
    PrimaryMenu primaryVerticalMenu = new PrimaryMenu();
    SecondaryMenu secondaryVerticalMenu = new SecondaryMenu();
    ContactPanel contactPanel = new ContactPanel();
    NotificationRight notificationRight = new NotificationRight();
    UnreadNotificationsModal unreadNotificationModal = new UnreadNotificationsModal();
    EditPopover editPopover = new EditPopover();
    HoldoverPopover holdoverPopover = new HoldoverPopover();
    MassClosePopover massClosePopover = new MassClosePopover();

    Element dialogsContent = new Element(By.xpath("//section[@class='conversation-page']"), "Основное содержимое страницы 'Диалоги'");
    Element emptyConversationList = new Element(By.xpath("//p[@class='content-box'][contains(.,'В этой группе нет активных диалогов')]"), "Сообщение пустого списка диалогов");
    Element conversationList = new Element(By.xpath("//div[@class='conversations-list-wrap']"), "Блок управления диалогами");
    Element conversationListTitle = new Element(By.xpath("//h1[@title='Диалоги']"), "Заголовок блока управления диалогами");
    ElementCollection conversations = new ElementCollection(By.xpath("//div[@class='conversations-list']/div[contains(@class,'conversation')]"), "Диалоги");
    ElementCollection conversationNames = new ElementCollection(By.xpath("//h4[@class='conversation--user']"), "Названия диалогов (имена юзеров)");
    Element conversationByName = new Element(ByKraken.xpathExpression("//h4[@class='conversation--user'][contains(.,'%s')]"), "Диалог по названию (имени)");

    Input searchInput = new Input(By.xpath("//input[@class='search--input']"), "Поле ввода поиска по диалогам");
    Element notFound = new Element(By.xpath("//div[@class='search--activity-no-message'][contains(.,'Результаты не найдены.')]"), "Сообщение 'Результаты не найдены'");

    Button statusFilter = new Button(By.xpath("//select[@class='status--filter']"), "Фильтр по статусу беседы");
    ElementCollection filterOptions = new ElementCollection(By.xpath("//select[@class='status--filter']/option"), "Выпадающий список значений фильтра");
    Element optionByName = new Element(ByKraken.xpathExpression("//select[@class='status--filter']/option[contains(.,'%s')]"), "Значение в выпадающем списке по имени");

    Button filterModalButton = new Button(By.xpath("//button[contains(@class,'btn-filter')]"), "Кнопка открытия модалки фильтров");

    Button massCloseButton = new Button(ByKraken.xpathExpression("//button[./span[@aria-label='more']]"), "Кнопка массового меню массового закрытия чатов"); //только под СВ КЦ/Админом

    ElementCollection dialogGroupTabs = new ElementCollection(By.xpath("//ul[contains(@class,'tab--chat-type')]/li"), "Вкладки групп диалогов");
    Element activeDialogTab = new Element(By.xpath("//ul[contains(@class,'tab--chat-type')]/li[contains(@class,'is-active')]"), "Выбранная вкладка групп диалогов");
    Element activeDialogTabConversationCount = new Element(By.xpath("//li[@class='tabs-title is-active']//span[@class='badge']"), "Количество диалогов, указанное на активной вкладке");
    Button loadMoreConversation = new Button(By.xpath("//span[@class='button__content'][contains(.,'Загрузить больше диалогов')]"), "Кнопка 'Загрузить больше диалогов'");
    Element allConversationWasLoadedMessage = new Element(By.xpath("//p[contains(@class,'end-of-list-text')][contains(.,'Все диалоги загружены')]"), "Сообщение 'Все диалоги загружены'");

    Element mainConversationArea = new Element(By.xpath("//div[@class='conversation-details-wrap']"), "Поле сообщений");
    //TODO Header

    Button editConversation = new Button(By.xpath("(//div[@id='issue-details']//button)[1]"), "Кнопка 'Редактировать обращение'");
    Button freezeConversation = new Button(By.xpath("(//div[@id='issue-details']//button)[2]"), "Кнопка 'Заморозить'");
    Button holdoverConversation = new Button(By.xpath("(//div[@id='issue-details']//button)[3]"), "Кнопка 'Отложить до..'");
    Button completeConversation = new Button(By.xpath("(//div[@id='issue-details']//button)[4]"), "Кнопка 'Завершить'");
    Element needFillTopicTooltip = new Element(By.xpath("//div[@class='ant-tooltip-inner'][.='Для завершения беседы необходимо выбрать тематику']"), "Всплывающее сообщение при попытке закрыть диалог без указания темы");
    Button reopenConversation = new Button(By.xpath("//button[./span[@aria-label='rollback']]"), "Кнопка 'Открыть заново'");
    Element notificationMessage = new Element(By.xpath("//div[@class='ant-notification-notice-message']"), "Всплывающее информационное сообщение об успешном выполнении действия по центру");
    Element holdoverLabelInHeader = new Element(By.xpath("//div[@id='issue-details']//span[@class='ant-tag'][contains(.,'Приостановлено до ')]"), "Пометка о приостановке чата в хедере");

    ElementCollection systemInfoMessages = new ElementCollection(By.xpath("//li[@class='message--read center']//p"), "Системное сообщение в чате");
    Element lastSystemInfoMessage = new Element(By.xpath("(//li[@class='message--read center']//p)[last()]"), "Последнее системное сообщение в чате");
    ElementCollection customerMessages = new ElementCollection(By.xpath("//li[@class='message--read left']//p"), "Сообщения клиента в чате");
    Element lastCustomerMessage = new Element(By.xpath("(//li[@class='message--read left']//p)[last()]"), "Последнее сообщение клиента в чате");
    ElementCollection operatorMessages = new ElementCollection(By.xpath("//li[@class='message--read right']//p"), "Сообщения оператора в чате");
    Element lastOperatorMessage = new Element(By.xpath("(//li[@class='message--read right']//p)[last()]"), "Последнее сообщение оператора в чате");
}

