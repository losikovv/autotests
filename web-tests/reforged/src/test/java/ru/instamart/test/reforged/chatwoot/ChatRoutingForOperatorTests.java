package ru.instamart.test.reforged.chatwoot;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.chatwoot.user.UserData;
import ru.instamart.kraken.data.chatwoot.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.CHATWOOT;
import static ru.instamart.reforged.chatwoot.ChatwootRouter.dialogs;
import static ru.instamart.reforged.chatwoot.ChatwootRouter.login;
import static ru.instamart.reforged.chatwoot.enums.OperatorStates.ONLINE;
import static ru.instamart.reforged.chatwoot.enums.OperatorStates.WAITING;

@Epic("Chatwoot")
@Feature("Маршрутизация чатов для роли Оператор")
public final class ChatRoutingForOperatorTests {

    @CaseId(52)
    @Test(description = "Чаты на оператора в статусе Оффлайн не маршрутизируются", groups = {CHATWOOT})
    public void routingDisableIfOfflineTest() {
        UserData userData = UserManager.getOperatorUser();

        //TODO Должен быть хотя бы 1 чат

        login().goToPage();
        login().waitPageLoad();

        login().fillEmail(userData.getEmail());
        login().fillPassword(userData.getPassword());
        login().clickSubmit();

        dialogs().waitPageLoad();
        dialogs().interactLeftMenu().checkAccountMenuButtonDisplayed();
        dialogs().interactLeftMenu().clickAccountMenuButton();
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuDisplayed();

        dialogs().checkActiveGroupTab("Мои");
        dialogs().checkConversationCountOnActiveTab("0");
        dialogs().checkConversationsListIsEmpty();
    }

    @CaseId(53)
    @Test(description = "Чаты на оператора в статусе Ожидание не маршрутизируются", groups = {CHATWOOT})
    public void routingDisableIfWaitingTest() {
        UserData userData = UserManager.getOperatorUser();

        //TODO Должен быть хотя бы 1 чат

        login().goToPage();
        login().waitPageLoad();

        login().fillEmail(userData.getEmail());
        login().fillPassword(userData.getPassword());
        login().clickSubmit();

        dialogs().waitPageLoad();
        dialogs().interactLeftMenu().checkAccountMenuButtonDisplayed();
        dialogs().interactLeftMenu().clickAccountMenuButton();
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuDisplayed();

        dialogs().interactLeftMenu().interactAccountMenu().clickUserState(WAITING.getName());
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuNotDisplayed();
        dialogs().interactLeftMenu().checkAccountMenuIndicatorColor(WAITING.getColorCode());

        dialogs().checkActiveGroupTab("Мои");
        dialogs().checkConversationCountOnActiveTab("0");
        dialogs().checkConversationsListIsEmpty();
    }

    @CaseId(57)
    @Test(description = "Новые чаты маршрутизируются на оператора пока у него есть свободные слоты", groups = {CHATWOOT})
    public void chatRoutingDuringLimitNotReachedTest() {
        UserData userData = UserManager.getOperatorUser();

        //TODO Должен быть хотя бы 3+ чата

        login().goToPage();
        login().waitPageLoad();

        login().fillEmail(userData.getEmail());
        login().fillPassword(userData.getPassword());
        login().clickSubmit();

        dialogs().waitPageLoad();
        dialogs().interactLeftMenu().checkAccountMenuButtonDisplayed();
        dialogs().interactLeftMenu().clickAccountMenuButton();
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuDisplayed();

        dialogs().interactLeftMenu().interactAccountMenu().clickUserState(ONLINE.getName());
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuNotDisplayed();
        dialogs().interactLeftMenu().checkAccountMenuIndicatorColor(ONLINE.getColorCode());

        dialogs().checkActiveGroupTab("Мои");
        dialogs().checkConversationsVisible();
        dialogs().checkConversationCountOnActiveTab("3");
        dialogs().checkConversationsCount(3);

        var firstChatName = dialogs().getFirstConversationNameInList();
        dialogs().clickFirstConversationInList();
        dialogs().checkEditConversationButtonVisible();
        dialogs().clickEditConversation();
        dialogs().interactEditPopover().checkEditConversationPopoverVisible();
        dialogs().interactEditPopover().clickTopicInput();
        dialogs().interactEditPopover().checkTopicsListVisible();
        dialogs().interactEditPopover().selectFirstTopic();
        dialogs().interactEditPopover().checkTopicsListNotVisible();
        dialogs().interactEditPopover().clickSubmitEditPopover();
        dialogs().checkNotificationMessageVisible();
        dialogs().checkNotificationMessageText("Данные успешно сохранены");
        dialogs().clickCompleteConversation();
        dialogs().checkReopenConversationVisible();

        dialogs().checkConversationNotVisible(firstChatName);

        dialogs().checkActiveGroupTab("Мои");
        dialogs().checkConversationsVisible();
        dialogs().checkConversationCountOnActiveTab("3");
        dialogs().checkConversationsCount(3);
    }
}


