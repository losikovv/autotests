package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.shop;
import static ru.instamart.reforged.stf.page.StfRouter.userShipments;

@Epic("STF UI")
@Feature("Покупка товара")
public final class OrderRepeatTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    private final UserData userData = UserManager.getUser();

    @BeforeMethod(description = "Аутентификация и выбор адреса доставки")
    public void preconditions() {
        helper.auth(userData);
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
    }

    @CaseId(1668)
    @Test(description = "Повтор крайнего заказа из истории заказов",
            groups = {
                    "sbermarket-regression", "sbermarket-acceptance",
                    "metro-regression", "metro-acceptance"}
    )
    public void successRepeatLastOrderFromOrderHistory() {
        helper.makeAndCancelOrder(userData, EnvironmentProperties.DEFAULT_SID, 2);

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
    @Test(description = "Повтор крайнего заказа со страницы заказа",
            groups = {
                    "sbermarket-regression", "sbermarket-acceptance",
                    "metro-regression", "metro-acceptance"}
    )
    public void successRepeatOrderFromOrderDetails() {
        helper.makeAndCancelOrder(userData, EnvironmentProperties.DEFAULT_SID, 2);

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
    @Test(description = "Отмена повтора заказа со страницы заказа",
            groups = {
                    "sbermarket-regression", "sbermarket-acceptance",
                    "metro-regression", "metro-acceptance"}
    )
    public void noRepeatOrderAfterCancel() {
        helper.makeOrder(userData, EnvironmentProperties.DEFAULT_SID, 2);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userShipments().goToPage();
        userShipments().clickToFirstShipment();
        userShipments().clickToCancelFromOrder();
        userShipments().interactShipmentModal().clickToAccept();
        userShipments().checkStatusWasCanceled();
    }
}
