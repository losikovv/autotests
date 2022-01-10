package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import ru.sbermarket.qase.annotation.CaseId;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.shop;
import static ru.instamart.reforged.stf.page.StfRouter.userShipments;

@Epic("STF UI")
@Feature("Покупка товара")
public final class OrderRepeatTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    private UserData userData;

    @BeforeMethod(alwaysRun = true, description = "Аутентификация и выбор адреса доставки")
    public void preconditions() {
        userData = UserManager.getQaUser();
        helper.makeOrder(userData, EnvironmentProperties.DEFAULT_SID, 2);
    }

    @CaseId(1668)
    @Test(description = "Повтор крайнего заказа из истории заказов", groups = {"smoke","regression", "acceptance"})
    public void successRepeatLastOrderFromOrderHistory() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().clickToRepeat();
        userShipments().interactRepeatModal().clickToAccept();

        shop().checkPageContains(shop().pageUrl());
        shop().interactCart().checkCartNotEmpty();
        shop().interactCart().compareItemsInCart(2);
    }

    @CaseId(1669)
    @Test(description = "Повтор крайнего заказа со страницы заказа", groups = {"regression", "acceptance"})
    public void successRepeatOrderFromOrderDetails() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().clickToFirstShipment();
        userShipments().clickToRepeatFromOrder();
        userShipments().interactRepeatModal().clickToAccept();

        shop().checkPageContains(shop().pageUrl());
        shop().interactCart().checkCartNotEmpty();
        shop().interactCart().compareItemsInCart(2);
    }

    @CaseId(1670)
    @Test(description = "Отмена повтора заказа со страницы заказа", groups = {"regression", "acceptance"})
    public void noRepeatOrderAfterCancel() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().clickToFirstShipment();
        userShipments().clickToCancelFromOrder();
        userShipments().interactShipmentCancelModal().clickToAccept();
        userShipments().checkStatusWasCanceled();
    }
}
