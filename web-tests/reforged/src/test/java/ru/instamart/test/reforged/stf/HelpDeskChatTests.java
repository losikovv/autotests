package ru.instamart.test.reforged.stf;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.UiProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Чат поддержки")
public final class HelpDeskChatTests {

    @CaseId(1758)
    @Test(description = "Тест отсутствия виджета HelpDesk в чекауте", groups = "regression")
    public void noHelpDeskWidgetOnCheckout() {
        final ApiHelper apiHelper = new ApiHelper();
        final UserData userData = UserManager.getQaUser();

        apiHelper.dropAndFillCart(userData, UiProperties.DEFAULT_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().interactHelpDesk().checkHelpDeskWidgetNotVisible();
    }

    @CaseId(1759)
    @Test(description = "Тест работы с виджетом HelpDesk на витрине ритейлера", groups = "regression")
    public void successOperateHelpDeskWidgetOnRetailerPage() {
        shop().goToPage();
        shop().interactHelpDesk().openChat();
        shop().interactHelpDesk().checkHelpDeskOpen();
        shop().interactHelpDesk().closeChat();
        shop().interactHelpDesk().checkHelpDeskClose();
    }

    @CaseId(1761)
    @Test(description = "Тест работы с виджетом HelpDesk на странице 404", groups = "regression")
    public void successOperateHelpDeskWidgetOnPage404() {
        notfound().goToPage();
        notfound().interactHelpDesk().openChat();
        notfound().interactHelpDesk().checkHelpDeskOpen();
        notfound().interactHelpDesk().closeChat();
        notfound().interactHelpDesk().checkHelpDeskClose();
    }
}
