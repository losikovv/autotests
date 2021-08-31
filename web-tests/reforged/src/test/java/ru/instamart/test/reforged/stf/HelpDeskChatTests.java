package ru.instamart.test.reforged.stf;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Чат поддержки")
public final class HelpDeskChatTests extends BaseTest {

    @Test(  description = "Тест отсутствия виджета HelpDesk на лендинге",
            groups = {"sbermarket-acceptance","sbermarket-regression",}
    )
    public void noHelpDeskWidgetOnLanding() {
        home().goToPage();
        home().interactHelpDesk().checkHelpDeskWidgetNotVisible();
    }

    @Test(  description = "Тест отсутствия виджета HelpDesk в чекауте",
            groups = {"sbermarket-acceptance","sbermarket-regression",}
    )
    public void noHelpDeskWidgetOnCheckout() {
        final ApiHelper apiHelper = new ApiHelper();
        final UserData userData = UserManager.getUser();

        apiHelper.auth(userData);
        apiHelper.dropAndFillCart(userData, EnvironmentData.INSTANCE.getDefaultSid());

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
        checkout().goToPage();
        checkout().interactHelpDesk().checkHelpDeskWidgetNotVisible();
    }

    @Test(  description = "Тест работы с виджетом HelpDesk на витрине ритейлера",
            groups = {"sbermarket-acceptance","sbermarket-regression",}
    )
    public void successOperateHelpDeskWidgetOnRetailerPage() {
        shop().goToPage();
        shop().interactHelpDesk().openChat();
        shop().interactHelpDesk().checkHelpDeskOpen();
        shop().interactHelpDesk().closeChat();
        shop().interactHelpDesk().checkHelpDeskClose();
    }

    @Test(  description = "Тест работы с виджетом HelpDesk на странице 404",
            groups = {"sbermarket-acceptance","sbermarket-regression",}
    )
    public void successOperateHelpDeskWidgetOnPage404() {
        notfound().goToPage();
        notfound().interactHelpDesk().openChat();
        notfound().interactHelpDesk().checkHelpDeskOpen();
        notfound().interactHelpDesk().closeChat();
        notfound().interactHelpDesk().checkHelpDeskClose();
    }

    //TODO: Нельзя, стучится в продовский чат
    @Skip
    @Test(  description = "Тест успешной отправки сообщения в HelpDesk",
            groups = {"sbermarket-regression",}
    )
    public void successSendMessageToHelpDeskFromRetailerPage() {
    }
}
