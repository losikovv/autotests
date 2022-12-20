package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.UiProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.Group.SMOKE_STF;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Покупка товара")
public final class OrderRepeatTests {

    private final ApiHelper helper = new ApiHelper();
    private UserData userData;

    @BeforeMethod(alwaysRun = true, description = "Аутентификация и выбор адреса доставки")
    public void preconditions() {
        userData = UserManager.getQaUser();
        helper.makeOrder(userData, UiProperties.DEFAULT_AUCHAN_SID, 2);
    }

    @AfterMethod(alwaysRun = true, description = "Отмена заказа")
    public void afterTest() {
        helper.cancelAllActiveOrders(userData);
    }

    @CaseId(2614)
    @Test(description = "Добавление в корзину товаров из истории заказов", groups = {REGRESSION_STF,SMOKE_STF})
    public void successRepeatLastOrderFromOrderHistory() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().clickToRepeat();
        userShipments().interactRepeatModal().checkModalWindowVisible();
        userShipments().interactRepeatModal().clickToAccept();

        shop().interactHeader().checkProfileButtonVisible();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkCartNotEmpty();
        shop().interactCart().getFirstRetailer().compareItemsInCart(2);
    }

    @CaseId(1669)
    @Test(description = "Повтор крайнего заказа со страницы заказа", groups = {REGRESSION_STF,SMOKE_STF})
    public void successRepeatOrderFromOrderDetails() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().clickToFirstShipment();
        userShipment().clickToRepeatFromOrder();
        userShipment().interactRepeatModal().checkModalWindowVisible();
        userShipment().interactRepeatModal().clickToAccept();

        shop().checkPageContains(shop().pageUrl());
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkCartNotEmpty();
        shop().interactCart().getFirstRetailer().compareItemsInCart(2);
    }

    @CaseId(1670)
    @Test(description = "Отмена повтора заказа со страницы заказа", groups = REGRESSION_STF)
    public void noRepeatOrderAfterCancel() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().clickToFirstShipment();
        userShipment().clickToRepeatFromOrder();
        userShipment().interactRepeatModal().checkModalWindowVisible();
        userShipment().interactRepeatModal().clickToDecline();

        userShipment().interactRepeatModal().checkModalWindowNotVisible();
        userShipment().interactHeader().clickToCart();

        userShipment().interactHeader().interactCart().checkCartOpen();
        userShipment().interactHeader().interactCart().checkCartEmpty();
    }
}
