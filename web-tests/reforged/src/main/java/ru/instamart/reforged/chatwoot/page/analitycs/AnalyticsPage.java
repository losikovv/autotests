package ru.instamart.reforged.chatwoot.page.analitycs;

import io.qameta.allure.Step;
import ru.instamart.reforged.chatwoot.ChatwootPage;
import ru.instamart.reforged.chatwoot.block.menu_vertical_primary.PrimaryMenu;
import ru.instamart.reforged.chatwoot.block.menu_vertical_secondary.SecondaryMenu;
import ru.instamart.reforged.chatwoot.frame.notification_right.NotificationRight;
import ru.instamart.reforged.chatwoot.frame.unread_notifications_modal.UnreadNotificationsModal;
import ru.instamart.reforged.chatwoot.page.analitycs.tables.OperatorsTable;

public final class AnalyticsPage implements ChatwootPage, AnalyticsCheck {

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

    public OperatorsTable interactOperatorsTable(){
        return operatorsTable;
    }

    @Step("Выбираем вкладку 'Эффективность операторов'")
    public void selectEffectivenessTab() {
        effectivenessTab.click();
    }

    @Step("Выбираем вкладку 'Упарвление заявками'")
    public void selectConversationManagementTab() {
        conversationManagementTab.click();
    }

    @Step("Кликаем в поле селектора 'Группа'")
    public void clickTeamSelector(){
        team.click();
    }

    @Step("Выбираем '{teamName}' в селекторе")
    public void selectTeamByName(final String teamName){
        teamByName.click(teamName);
    }

    @Step("Нажимаем 'Найти'")
    public void clickSubmit(){
        submit.click();
    }

    @Step("Нажимаем на кнопку-фильтр '{filterName}'")
    public void clickUnassignedFilter(final String filterName){
        filterButtonByName.click(filterName);
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
