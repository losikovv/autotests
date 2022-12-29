package ru.instamart.test.reforged.chatwoot;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.chatwoot.user.UserData;
import ru.instamart.kraken.data.chatwoot.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.CHATWOOT;
import static ru.instamart.reforged.chatwoot.ChatwootRouter.*;
import static ru.instamart.reforged.chatwoot.enums.OperatorStates.ONLINE;

@Epic("Chatwoot")
@Feature("Страница Эффективность операторов (СВ КЦ)")
public final class EffectivenessTests {

    @CaseId(59)
    @Test(description = "Проверка отображения таблицы эффективности работы операторов на странице", groups = {CHATWOOT})
    public void checkEffectivenessOperatorTableClose() {
        UserData userData = UserManager.getSVCCUser();

        //TODO Нужно 1+ новых чатов

        login().goToPage();
        login().waitPageLoad();

        login().fillEmail(userData.getEmail());
        login().fillPassword(userData.getPassword());
        login().clickSubmit();

        dialogs().waitPageLoad();
        dialogs().interactLeftMenu().checkAccountMenuButtonDisplayed();
        dialogs().interactLeftMenu().clickAnalyticsButton();

        analytics().checkAnalyticsPageOpened();
        analytics().selectEffectivenessTab();
        analytics().checkTeamsSelectorVisible();
        analytics().clickTeamSelector();
        analytics().selectTeamByName("Все команды");
        analytics().clickSubmit();

        analytics().checkOperatorsTableVisible();

        analytics().checkOperatorColumnValueNotEmpty(0);
    }
}


