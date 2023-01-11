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
@Feature("Карточка клиента")
public final class CustomerCardTests {

    private final ApiHelper apiHelper = new ApiHelper();

    @BeforeClass(alwaysRun = true, description = "Генерация чатов")
    public void preconditions() {
        apiHelper.generateChatwootConversation(1);
    }

    @TmsLink("35")
    @Test(description = "Отображение данных о клиенте в чате", groups = {CHATWOOT})
    public void CustomerCardInfoTest() {
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

        var firstConversationName = dialogs().getFirstConversationNameInList();

        dialogs().clickFirstConversationInList();
        dialogs().interactContactPanel().checkContactPanelVisible();
        dialogs().interactContactPanel().checkAccountName(firstConversationName);
    }
}


