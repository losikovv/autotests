package ru.instamart.test.reforged.chatwoot;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.chatwoot.user.UserData;
import ru.instamart.kraken.data.chatwoot.user.UserManager;
import ru.instamart.kraken.util.TimeUtil;

import static ru.instamart.reforged.Group.CHATWOOT;
import static ru.instamart.reforged.chatwoot.ChatwootRouter.dialogs;
import static ru.instamart.reforged.chatwoot.ChatwootRouter.login;
import static ru.instamart.reforged.chatwoot.enums.OperatorStates.ONLINE;

@Epic("Chatwoot")
@Feature("Ручная приостановка чатов")
public final class ManualHoldoverTests {

    @TmsLink("2")
    @Test(description = "Приостановка чата до указанного оператором времени", groups = {CHATWOOT})
    public void holdoverConversationTest() {
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

        dialogs().interactLeftMenu().interactAccountMenu().clickUserState(ONLINE.getName());
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuNotDisplayed();
        dialogs().interactLeftMenu().checkAccountMenuIndicatorColor(ONLINE.getColorCode());

        dialogs().checkActiveGroupTab("Мои");
        dialogs().checkConversationsVisible();

        dialogs().clickFirstConversationInList();
        dialogs().checkEditConversationButtonVisible();

        dialogs().clickHoldoverConversation();
        dialogs().interactHoldoverPopover().checkHoldoverConversationPopoverVisible();
        dialogs().interactHoldoverPopover().clickDateTimeInput();
        dialogs().interactHoldoverPopover().interactDateTimePicker().checkCalendarVisible();
        dialogs().interactHoldoverPopover().interactDateTimePicker().clickTomorrowInCalendar();
        dialogs().interactHoldoverPopover().interactDateTimePicker().clickOkInCalendar();
        dialogs().interactHoldoverPopover().interactDateTimePicker().checkCalendarNotVisible();
        var holdoverDateTime = dialogs().interactHoldoverPopover().getHoldoverDateTime();
        dialogs().interactHoldoverPopover().clickSubmitHoldoverPopover();

        dialogs().checkHoldoverHeaderLabelVisible();
        dialogs().checkHoldoverHeaderLabelDateTime("Приостановлено до " + holdoverDateTime);
        dialogs().checkLastSystemChatMessageContains("Разговор был помечен как отложенный Оператор Автотест");
    }

    @TmsLink("1")
    @Test(description = "Заморозка чата на 20 минут", groups = {CHATWOOT})
    public void freezeConversationTest() {
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

        dialogs().interactLeftMenu().interactAccountMenu().clickUserState(ONLINE.getName());
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuNotDisplayed();
        dialogs().interactLeftMenu().checkAccountMenuIndicatorColor(ONLINE.getColorCode());

        dialogs().checkActiveGroupTab("Мои");
        dialogs().checkConversationsVisible();

        dialogs().clickFirstConversationInList();
        dialogs().checkEditConversationButtonVisible();

        dialogs().clickFreezeConversation();

        dialogs().checkHoldoverHeaderLabelVisible();
        dialogs().checkHoldoverHeaderLabelDateTime("Приостановлено до " + TimeUtil.getDateTimeForChatwootLabelTwentyMinutes());
        dialogs().checkLastSystemChatMessageContains("Разговор был помечен как отложенный Оператор Автотест");
    }

    @TmsLink("8")
    @Test(description = "Приостановленный чат отображается в очереди чатов 'Отложенные'", groups = {CHATWOOT})
    public void holdoverFilterTest() {
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

        dialogs().interactLeftMenu().interactAccountMenu().clickUserState(ONLINE.getName());
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuNotDisplayed();
        dialogs().interactLeftMenu().checkAccountMenuIndicatorColor(ONLINE.getColorCode());

        dialogs().checkActiveGroupTab("Мои");
        dialogs().checkConversationsVisible();

        var firstChatName = dialogs().getFirstConversationNameInList();
        dialogs().clickFirstConversationInList();
        dialogs().checkEditConversationButtonVisible();

        dialogs().clickFreezeConversation();
        dialogs().checkConversationNotVisible(firstChatName);

        dialogs().clickFilterStatusButton();
        dialogs().checkStatusFilterOptionsListVisible();
        dialogs().selectOptionsByName("Отложенн");

        dialogs().checkConversationVisible(firstChatName);
    }
}


