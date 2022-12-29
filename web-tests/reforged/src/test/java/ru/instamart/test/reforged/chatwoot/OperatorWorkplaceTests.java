package ru.instamart.test.reforged.chatwoot;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.chatwoot.user.UserData;
import ru.instamart.kraken.data.chatwoot.user.UserManager;

import static ru.instamart.reforged.Group.CHATWOOT;
import static ru.instamart.reforged.chatwoot.ChatwootRouter.*;
import static ru.instamart.reforged.chatwoot.enums.OperatorStates.ONLINE;

@Epic("Chatwoot")
@Feature("Рабочее место оператора")
public final class OperatorWorkplaceTests {

    @TmsLink("117")
    @Test(description = "После разлогина чаты в работе падают в общую очередь распределения", groups = {CHATWOOT})
    public void chatsUnassignedAutomaticallyOnLogoutClose() {
        UserData userData = UserManager.getOperatorUser();

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
        dialogs().checkConversationsVisible();

        var firstChatName = dialogs().getFirstConversationNameInList();

        dialogs().interactLeftMenu().clickAccountMenuButton();
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuDisplayed();
        dialogs().interactLeftMenu().interactAccountMenu().clickLogout();

        login().waitPageLoad();

        login().fillEmail(userData.getEmail());
        login().fillPassword(userData.getPassword());
        login().clickSubmit();

        dialogs().waitPageLoad();
        dialogs().checkActiveGroupTab("Мои");
        dialogs().checkConversationsListIsEmpty();

        dialogs().interactLeftMenu().clickAnalyticsButton();


        analytics().waitPageLoad();
        analytics().clickUnassignedFilter("Неназначенные");
        // Заявка может отображаться не на первой странице, если страниц неназначенных несколько, как быть?
        // analytics().checkConversationVisible(firstChatName);
    }
}


