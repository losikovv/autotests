package ru.instamart.test.reforged.chatwoot;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.chatwoot.user.UserData;
import ru.instamart.kraken.data.chatwoot.user.UserManager;

import static ru.instamart.reforged.Group.CHATWOOT;
import static ru.instamart.reforged.chatwoot.ChatwootRouter.dialogs;
import static ru.instamart.reforged.chatwoot.ChatwootRouter.login;
import static ru.instamart.reforged.chatwoot.enums.OperatorStates.ONLINE;

@Epic("Chatwoot")
@Feature("Массовое закрытие чатов")
public final class MassChatCloseTests {
    private final ApiHelper apiHelper = new ApiHelper();

    @BeforeClass(alwaysRun = true, description = "Генерация чатов")
    public void preconditions() {
        apiHelper.generateChatwootConversation(1);
    }

    @TmsLink("118")
    @Test(description = "Массовое закрытие чатов", groups = {CHATWOOT})
    public void massChatClose() {
        UserData userData = UserManager.getSVCCUser();

        //TODO Нужно 1+ новых чатов

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
        dialogs().clickMassCloseButton();

        dialogs().interactMassClosePopover().checkMassClosePopoverVisible();
        dialogs().interactMassClosePopover().clickTopicInput();
        dialogs().interactMassClosePopover().checkTopicsListVisible();
        dialogs().interactMassClosePopover().selectFirstTopic();
        dialogs().interactMassClosePopover().checkTopicsListNotVisible();

        dialogs().interactMassClosePopover().clickDateTimeFrom();
        dialogs().interactMassClosePopover().interactCalendar().checkCalendarVisible();
        dialogs().interactMassClosePopover().interactCalendar().clickFirstDayOfMonth();
        dialogs().interactMassClosePopover().clickDateTimeTo();
        dialogs().interactMassClosePopover().interactCalendar().checkCalendarVisible();
        dialogs().interactMassClosePopover().interactCalendar().clickTodayInCalendar();
        dialogs().interactMassClosePopover().interactCalendar().clickOkInCalendar();
        dialogs().interactMassClosePopover().interactCalendar().checkCalendarNotVisible();

        dialogs().interactMassClosePopover().fillSendMessage("Массовое закрытие автотест");

        dialogs().interactMassClosePopover().clickGetFilteredChatCount();
        dialogs().interactMassClosePopover().checkFilteredChatsCountNotNull();
        dialogs().interactMassClosePopover().clickSubmit();

        dialogs().checkNotificationMessageVisible();
        dialogs().checkNotificationMessageText("Запрос на закрытие чатов успешно отправлен. Чаты будут закрыты асинхронно в ближайшее время.");

        dialogs().interactNotification().checkMassCloseNotificationDisplayed();
        dialogs().interactNotification().clickButton("Перезагрузить");

        dialogs().waitPageLoad();
    }
}


