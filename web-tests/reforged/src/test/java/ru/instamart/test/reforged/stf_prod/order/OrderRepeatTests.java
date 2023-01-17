package ru.instamart.test.reforged.stf_prod.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.UiProperties;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.stf.page.StfRouter.shop;
import static ru.instamart.reforged.stf.page.StfRouter.userShipments;

@Epic("STF UI")
@Feature("Покупка товара")
public final class OrderRepeatTests {

    private static final ThreadLocal<UserData> ordersUser = new ThreadLocal<>();
    private final ApiHelper helper = new ApiHelper();

    @BeforeMethod(alwaysRun = true, description = "Аутентификация и выбор адреса доставки")
    public void preconditions() {
        ordersUser.set(UserManager.getQaUser());
        helper.makeOrder(ordersUser.get(), UiProperties.DEFAULT_SID, 2);
    }

    @AfterMethod(alwaysRun = true, description = "Отмена заказа")
    public void afterTest() {
        this.helper.cancelAllActiveOrders(ordersUser.get());
    }

    @TmsLink("2614")
    @Test(description = "Добавление в корзину товаров из истории заказов", groups = {STF_PROD_S})
    public void successRepeatLastOrderFromOrderHistory() {
        shop().goToPageProd();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser.get());
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
        shop().goToPageProd();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser.get());
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
