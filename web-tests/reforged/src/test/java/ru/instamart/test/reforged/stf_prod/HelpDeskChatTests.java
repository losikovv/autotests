package ru.instamart.test.reforged.stf_prod;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.core.config.UiProperties.DEFAULT_SID;
import static ru.instamart.reforged.stf.page.StfRouter.checkoutNew;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Чат поддержки")
public final class HelpDeskChatTests {

    @TmsLink("1758")
    //отключено, потому что чат сейчас показывается в чекауте, ПМ чекаута и чата говорят пусть он там будет пока
    @Skip
    @Test(description = "Тест отсутствия виджета HelpDesk в чекауте", groups = {STF_PROD_S})
    public void noHelpDeskWidgetOnCheckout() {
        final var apiHelper = new ApiHelper();
        final var userData = UserManager.getQaUser();

        apiHelper.dropAndFillCart(userData, DEFAULT_SID);

        shop().goToPageWithAuth(DEFAULT_SID, userData);
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().clickToCart();

        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();
        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().interactHelpDesk().checkHelpDeskWidgetNotVisible();
    }

    @TmsLink("1759")
    @Test(description = "Тест работы с виджетом HelpDesk на витрине ритейлера", groups = {STF_PROD_S})
    public void successOperateHelpDeskWidgetOnRetailerPage() {
        shop().goToPage(DEFAULT_SID);
        shop().interactHelpDesk().openChat();
        shop().interactHelpDesk().checkHelpDeskOpen();
        shop().interactHelpDesk().closeChat();
        shop().interactHelpDesk().checkHelpDeskClose();
    }
}
