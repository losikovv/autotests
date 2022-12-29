package ru.instamart.test.reforged.chatwoot;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.chatwoot.user.UserData;
import ru.instamart.kraken.data.chatwoot.user.UserManager;

import static ru.instamart.reforged.Group.CHATWOOT;
import static ru.instamart.reforged.chatwoot.ChatwootRouter.dialogs;
import static ru.instamart.reforged.chatwoot.ChatwootRouter.login;
import static ru.instamart.reforged.chatwoot.enums.OperatorStates.*;

@Epic("Chatwoot")
@Feature("Смена статусов в чатвуте")
public final class OperatorStatusChangeTests {

    @TmsLink("27")
    @Test(description = "После логина в чатвут оператор в статусе оффлайн", groups = {CHATWOOT})
    public void offlineByDefaultTest() {
        UserData userData = UserManager.getOperatorUser();

        login().goToPage();
        login().waitPageLoad();

        login().fillEmail(userData.getEmail());
        login().fillPassword(userData.getPassword());
        login().clickSubmit();

        dialogs().waitPageLoad();
        dialogs().interactLeftMenu().checkAccountMenuButtonDisplayed();

        dialogs().interactLeftMenu().clickAccountMenuButton();
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuDisplayed();
        dialogs().interactLeftMenu().interactAccountMenu().checkActiveUserState(OFFLINE.getName());

        dialogs().interactLeftMenu().interactAccountMenu().clickUserState(ONLINE.getName());
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuNotDisplayed();

        dialogs().interactLeftMenu().clickAccountMenuButton();
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuDisplayed();
        dialogs().interactLeftMenu().interactAccountMenu().checkStateNotAvailable(OFFLINE.getName());
    }

    @TmsLink("74")
    @Test(description = "Переход в статус в Сети", groups = {CHATWOOT})
    public void switchToOnLineTest() {
        UserData userData = UserManager.getOperatorUser();

        //TODO Нагенерить чатов
        login().goToPage();
        login().waitPageLoad();

        login().fillEmail(userData.getEmail());
        login().fillPassword(userData.getPassword());
        login().clickSubmit();

        dialogs().waitPageLoad();
        dialogs().interactLeftMenu().checkAccountMenuButtonDisplayed();
        dialogs().interactLeftMenu().checkAccountMenuIndicatorColor(OFFLINE.getColorCode());

        dialogs().interactLeftMenu().clickAccountMenuButton();
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuDisplayed();
        dialogs().interactLeftMenu().interactAccountMenu().checkActiveUserState(OFFLINE.getName());

        dialogs().interactLeftMenu().interactAccountMenu().clickUserState(ONLINE.getName());
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuNotDisplayed();
        dialogs().interactLeftMenu().checkAccountMenuIndicatorColor(ONLINE.getColorCode());

        dialogs().checkActiveGroupTab("Мои");
        dialogs().checkConversationCountOnActiveTab("3");
        dialogs().checkConversationsVisible();
        dialogs().checkConversationsCount(3);

        dialogs().interactLeftMenu().clickAccountMenuButton();
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuDisplayed();
        dialogs().interactLeftMenu().interactAccountMenu().checkStateNotAvailable(OFFLINE.getName());
    }

    @TmsLink("75")
    @Test(description = "Переход в статус Ожидание", groups = {CHATWOOT})
    public void switchToWaitTest() {
        UserData userData = UserManager.getOperatorUser();

        //TODO Нагенерить 3+ чата
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
        dialogs().checkConversationCountOnActiveTab("3");
        dialogs().checkConversationsVisible();
        dialogs().checkConversationsCount(3);

        dialogs().interactLeftMenu().clickAccountMenuButton();
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuDisplayed();
        dialogs().interactLeftMenu().interactAccountMenu().clickUserState(WAITING.getName());
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuNotDisplayed();
        dialogs().interactLeftMenu().checkAccountMenuIndicatorColor(WAITING.getColorCode());

        dialogs().checkActiveGroupTab("Мои");
        dialogs().checkConversationCountOnActiveTab("3");
        dialogs().checkConversationsVisible();
        dialogs().checkConversationsCount(3);

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

        dialogs().checkConversationCountOnActiveTab("2");
        dialogs().checkConversationsVisible();
        dialogs().checkConversationsCount(2);

        dialogs().checkLoadCompleteMsgVisible();
    }

    @TmsLink("76")
    @Test(description = "Переход в статус Обед", groups = {CHATWOOT})
    public void switchToDinnerTest() {
        UserData userData = UserManager.getOperatorUser();

        login().goToPage();
        login().waitPageLoad();

        login().fillEmail(userData.getEmail());
        login().fillPassword(userData.getPassword());
        login().clickSubmit();

        dialogs().waitPageLoad();
        dialogs().interactLeftMenu().clickAccountMenuButton();
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuDisplayed();
        dialogs().interactLeftMenu().interactAccountMenu().checkUserStateDisabled(DINNER.getName());

        dialogs().interactLeftMenu().interactAccountMenu().clickUserState(WAITING.getName());
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuNotDisplayed();
        dialogs().interactLeftMenu().checkAccountMenuIndicatorColor(WAITING.getColorCode());

        dialogs().checkConversationsListIsEmpty();

        dialogs().interactLeftMenu().clickAccountMenuButton();
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuDisplayed();
        dialogs().interactLeftMenu().interactAccountMenu().clickUserState(DINNER.getName());
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuNotDisplayed();
        dialogs().interactLeftMenu().checkAccountMenuIndicatorColor(DINNER.getColorCode());

        dialogs().interactLeftMenu().clickAccountMenuButton();
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuDisplayed();
        dialogs().interactLeftMenu().interactAccountMenu().clickUserState(TECH_BREAK.getName());
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuNotDisplayed();
        dialogs().interactLeftMenu().checkAccountMenuIndicatorColor(TECH_BREAK.getColorCode());

        dialogs().interactLeftMenu().clickAccountMenuButton();
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuDisplayed();
        dialogs().interactLeftMenu().interactAccountMenu().clickUserState(DINNER.getName());
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuNotDisplayed();
        dialogs().interactLeftMenu().checkAccountMenuIndicatorColor(DINNER.getColorCode());
    }

    @TmsLink("77")
    @Test(description = "Переход в статус Тех.перерыв", groups = {CHATWOOT})
    public void switchToTechBreakTest() {
        UserData userData = UserManager.getOperatorUser();

        login().goToPage();
        login().waitPageLoad();

        login().fillEmail(userData.getEmail());
        login().fillPassword(userData.getPassword());
        login().clickSubmit();

        dialogs().waitPageLoad();

        dialogs().interactLeftMenu().clickAccountMenuButton();
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuDisplayed();
        dialogs().interactLeftMenu().interactAccountMenu().checkUserStateDisabled(TECH_BREAK.getName());

        dialogs().interactLeftMenu().interactAccountMenu().clickUserState(WAITING.getName());
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuNotDisplayed();
        dialogs().interactLeftMenu().checkAccountMenuIndicatorColor(WAITING.getColorCode());

        dialogs().checkConversationsListIsEmpty();

        dialogs().interactLeftMenu().clickAccountMenuButton();
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuDisplayed();
        dialogs().interactLeftMenu().interactAccountMenu().clickUserState(TECH_BREAK.getName());
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuNotDisplayed();
        dialogs().interactLeftMenu().checkAccountMenuIndicatorColor(TECH_BREAK.getColorCode());

        dialogs().interactLeftMenu().clickAccountMenuButton();
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuDisplayed();
        dialogs().interactLeftMenu().interactAccountMenu().clickUserState(DINNER.getName());
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuNotDisplayed();
        dialogs().interactLeftMenu().checkAccountMenuIndicatorColor(DINNER.getColorCode());

        dialogs().interactLeftMenu().clickAccountMenuButton();
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuDisplayed();
        dialogs().interactLeftMenu().interactAccountMenu().clickUserState(TECH_BREAK.getName());
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuNotDisplayed();
        dialogs().interactLeftMenu().checkAccountMenuIndicatorColor(TECH_BREAK.getColorCode());
    }

    @TmsLink("78")
    @Test(description = "Проверка невозможности перехода в статусы Обед и Тех.перерыв при наличии чатов в работе", groups = {CHATWOOT})
    public void unableToSwitchDinnerAndTechBreakWithActiveConversations() {
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

        dialogs().interactLeftMenu().clickAccountMenuButton();
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuDisplayed();
        dialogs().interactLeftMenu().interactAccountMenu().clickUserState(WAITING.getName());
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuNotDisplayed();
        dialogs().interactLeftMenu().checkAccountMenuIndicatorColor(WAITING.getColorCode());

        dialogs().interactLeftMenu().clickAccountMenuButton();
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuDisplayed();
        dialogs().interactLeftMenu().interactAccountMenu().clickUserState(DINNER.getName());
        dialogs().checkSnackbarVisible();
        dialogs().checkSnackbarText("Ошибка переключения статуса.");

        dialogs().interactLeftMenu().clickAccountMenuButton();
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuDisplayed();
        dialogs().interactLeftMenu().interactAccountMenu().clickUserState(TECH_BREAK.getName());
        dialogs().checkSnackbarVisible();
        dialogs().checkSnackbarText("Ошибка переключения статуса.");
    }
}


