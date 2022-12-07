package ru.instamart.test.reforged.stf;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.reforged.core.config.UiProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Чат поддержки")
public final class HelpDeskChatTests {

    @CaseId(1758)
    @Test(description = "Тест отсутствия виджета HelpDesk в чекауте", groups = REGRESSION_STF)
    public void noHelpDeskWidgetOnCheckout() {
        final ApiHelper apiHelper = new ApiHelper();
        final UserData userData = UserManager.getQaUser();

        apiHelper.dropAndFillCart(userData, UiProperties.DEFAULT_AUCHAN_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().interactHelpDesk().checkHelpDeskWidgetNotVisible();
    }

    @CaseId(1759)
    @Test(description = "Тест работы с виджетом HelpDesk на витрине ритейлера", groups = REGRESSION_STF)
    public void successOperateHelpDeskWidgetOnRetailerPage() {
        shop().goToPage();
        shop().interactHelpDesk().openChat();
        shop().interactHelpDesk().checkHelpDeskOpen();
        shop().interactHelpDesk().closeChat();
        shop().interactHelpDesk().checkHelpDeskClose();
    }

    @CaseId(1761)
    @Issue("CC-762")
    @Skip
    @Test(description = "Тест работы с виджетом HelpDesk на странице 404", groups = REGRESSION_STF)
    public void successOperateHelpDeskWidgetOnPage404() {
        notfound().goToPage();
        notfound().interactHelpDesk().openChat();
        notfound().interactHelpDesk().checkHelpDeskOpen();
        notfound().interactHelpDesk().closeChat();
        notfound().interactHelpDesk().checkHelpDeskClose();
    }
}
