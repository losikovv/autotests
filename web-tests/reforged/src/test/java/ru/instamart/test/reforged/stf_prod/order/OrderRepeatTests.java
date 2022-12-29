package ru.instamart.test.reforged.stf_prod.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.UiProperties;
import io.qameta.allure.TmsLink;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.stf.page.StfRouter.shop;
import static ru.instamart.reforged.stf.page.StfRouter.userShipments;

@Epic("STF UI")
@Feature("Покупка товара")
public final class OrderRepeatTests {

    private final ApiHelper helper = new ApiHelper();
    private UserData userData;

    @BeforeMethod(alwaysRun = true, description = "Аутентификация и выбор адреса доставки")
    public void preconditions() {
        userData = UserManager.getQaUser();
        helper.makeOrder(userData, UiProperties.DEFAULT_SID, 2);
    }

    @AfterMethod(alwaysRun = true, description = "Отмена заказа")
    public void afterTest() {
        this.helper.cancelAllActiveOrders(userData);
    }

    @TmsLink("2614")
    @Test(description = "Добавление в корзину товаров из истории заказов", groups = {STF_PROD_S})
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

    @TmsLink("1669")
    @Test(description = "Повтор крайнего заказа со страницы заказа", groups = {STF_PROD_S})
    public void successRepeatOrderFromOrderDetails() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().clickToFirstShipment();
        userShipments().clickToRepeatFromOrder();
        userShipments().interactRepeatModal().checkModalWindowVisible();
        userShipments().interactRepeatModal().clickToAccept();

        shop().checkPageContains(shop().pageUrl());
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkCartNotEmpty();
        shop().interactCart().getFirstRetailer().compareItemsInCart(2);
    }
}
