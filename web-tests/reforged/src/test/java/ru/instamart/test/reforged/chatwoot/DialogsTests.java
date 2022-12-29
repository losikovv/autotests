package ru.instamart.test.reforged.chatwoot;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.chatwoot.user.UserData;
import ru.instamart.kraken.data.chatwoot.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.CHATWOOT;
import static ru.instamart.reforged.chatwoot.ChatwootRouter.dialogs;
import static ru.instamart.reforged.chatwoot.ChatwootRouter.login;
import static ru.instamart.reforged.chatwoot.enums.OperatorStates.ONLINE;

@Epic("Chatwoot")
@Feature("Диалоги")
public final class DialogsTests {

    @CaseId(32)
    @Test(description = "Назначенные на оператора чаты отображаются во вкладке Мои диалоги", groups = {CHATWOOT})
    public void assignedChatsAvailableOnOperatorMyTabTest() {
        UserData userData = UserManager.getOperatorUser();

        //TODO Должно быть хотя бы 3 чата

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
    }

    @CaseId(114)
    @Test(description = "Ручное добавление атрибутов чата - Номер доставки", groups = {CHATWOOT})
    public void editAttributesTest() {
        UserData userData = UserManager.getOperatorUser();

        //TODO Долж быть хотя бы 1 чат

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
        dialogs().clickEditConversation();
        dialogs().interactEditPopover().checkEditConversationPopoverVisible();
        dialogs().interactEditPopover().fillShipmentNumber("H" + Generate.digitalString(11));
        dialogs().interactEditPopover().clickTopicInput();
        dialogs().interactEditPopover().checkTopicsListVisible();
        dialogs().interactEditPopover().selectFirstTopic();
        dialogs().interactEditPopover().checkTopicsListNotVisible();
        dialogs().interactEditPopover().clickSubmitEditPopover();
        dialogs().checkNotificationMessageVisible();
        dialogs().checkNotificationMessageText("Данные успешно сохранены");

        //Чтобы не мешал при следующем прогоне
        dialogs().clickCompleteConversation();
    }

    @CaseId(38)
    @Test(description = "Невозможность завершить чат без указания Тематики", groups = {CHATWOOT})
    public void needFillTopicToCompleteCo() {
        UserData userData = UserManager.getOperatorUser();

        //TODO Долж быть хотя бы 1 чат

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

        dialogs().hoverCompleteConversation();
        dialogs().checkNeedFillTopicTooltipVisible();

        dialogs().checkEditConversationButtonVisible();
    }

    @CaseId(102)
    @Test(description = "Завершение чата", groups = {CHATWOOT})
    public void chatCompleteConversationTest() {
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
    }

    @CaseId(125)
    @Test(description = "Переоткрытие чата оператором", groups = {CHATWOOT})
    public void reopenChatTest() {
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

        dialogs().clickFilterStatusButton();
        dialogs().selectOptionsByName("Закрытые");
        dialogs().checkConversationsVisible();

        var firstChatName = dialogs().getFirstConversationNameInList();
        dialogs().clickFirstConversationInList();
        dialogs().checkReopenConversationVisible();
        dialogs().clickReopenConversation();

        dialogs().checkConversationNotVisible(firstChatName);

        dialogs().clickFilterStatusButton();
        dialogs().selectOptionsByName("Открытые");
        dialogs().checkConversationsVisible();

        dialogs().checkConversationVisible(firstChatName);

        dialogs().checkEditConversationButtonVisible();
        dialogs().checkLastSystemChatMessageContains("Оператор Автотест открыл заново диалог");
    }
}


