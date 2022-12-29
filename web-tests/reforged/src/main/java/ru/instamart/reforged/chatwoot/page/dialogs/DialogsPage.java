package ru.instamart.reforged.chatwoot.page.dialogs;

import io.qameta.allure.Step;
import ru.instamart.reforged.chatwoot.ChatwootPage;
import ru.instamart.reforged.chatwoot.block.menu_vertical_primary.PrimaryMenu;
import ru.instamart.reforged.chatwoot.block.menu_vertical_secondary.SecondaryMenu;
import ru.instamart.reforged.chatwoot.frame.contact_panel.ContactPanel;
import ru.instamart.reforged.chatwoot.frame.edit_popover.EditPopover;
import ru.instamart.reforged.chatwoot.frame.holdover_popover.HoldoverPopover;
import ru.instamart.reforged.chatwoot.frame.mass_close_popover.MassClosePopover;
import ru.instamart.reforged.chatwoot.frame.notification_right.NotificationRight;
import ru.instamart.reforged.chatwoot.frame.unread_notifications_modal.UnreadNotificationsModal;

public final class DialogsPage implements ChatwootPage, DialogsCheck {

    public PrimaryMenu interactLeftMenu() {
        return primaryVerticalMenu;
    }

    public SecondaryMenu interactSecondaryMenu() {
        return secondaryVerticalMenu;
    }

    public ContactPanel interactContactPanel() {
        return contactPanel;
    }
    public NotificationRight interactNotification() {
        return notificationRight;
    }

    public UnreadNotificationsModal interactUnreadNotificationsModal() {
        return unreadNotificationModal;
    }

    public EditPopover interactEditPopover() {
        return editPopover;
    }

    public HoldoverPopover interactHoldoverPopover() {
        return holdoverPopover;
    }

    public MassClosePopover interactMassClosePopover() {
        return massClosePopover;
    }

    @Step("Выбираем первое обращение в списке")
    public void clickFirstConversationInList() {
        conversations.clickOnFirst();
    }

    @Step("Получаем название первого обращения в списке")
    public String getFirstConversationNameInList() {
        return conversationNames.getElementText(0);
    }

    @Step("Нажимаем на кнопку 'Загрузить больше диалогов'")
    public void clickLoadMoreConversation() {
        loadMoreConversation.click();
    }

    @Step("Нажимаем кнопку 'Редактировать' обращение")
    public void clickEditConversation() {
        editConversation.click();
    }

    @Step("Нажимаем кнопку 'Заморозить' обращение")
    public void clickFreezeConversation() {
        freezeConversation.click();
    }

    @Step("Нажимаем кнопку 'Отложить до..' обращение")
    public void clickHoldoverConversation() {
        holdoverConversation.click();
    }

    @Step("Наводим курсор на кнопку 'Завершить' обращение")
    public void hoverCompleteConversation() {
        // Костыль потому что кракен не даёт кликнуть или даже навестись на задисейбленую кнопку, поэтому целимся на соседнюю и кликаем со смещением
        holdoverConversation.getActions().clickWithOffset(50,0);
    }

    @Step("Нажимаем кнопку 'Завершить' обращение")
    public void clickCompleteConversation() {
        completeConversation.click();
    }

    @Step("Нажимаем кнопку 'Открыть заново'")
    public void clickReopenConversation() {
        reopenConversation.click();
    }

    @Step("Нажимаем кнопку 'Массовое закрытие обращений'")
    public void clickMassCloseButton() {
        massCloseButton.click();
    }

    @Step("Нажимаем на кнопку выбора фильтра статуса диалога")
    public void clickFilterStatusButton() {
        statusFilter.click();
    }

    @Step("Выбираем значение фильтра: '{optionsName}'")
    public void selectOptionsByName(final String optionsName) {
        optionByName.click(optionsName);
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
