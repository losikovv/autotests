package ru.instamart.test.reforged.stf_prod.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.core.config.UiProperties.DEFAULT_SID;
import static ru.instamart.reforged.stf.page.StfRouter.shop;
import static ru.instamart.reforged.stf.page.StfRouter.userShipments;

@Epic("STF UI")
@Feature("Раздел 'Вы покупали ранее'")
public final class ShoppingYouBoughtBeforeTests {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(2943)
    @Test(description = "Товар отображается в блоке 'Вы покупали ранее', если отправлен на сборку", groups = {STF_PROD_S})
    public void testYouBoughtBeforeDisplayed() {
        final UserData shoppingCartUser = UserManager.getQaUser();
        helper.makeOrder(shoppingCartUser, DEFAULT_SID, 3);
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultProdAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(shoppingCartUser);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();

        shop().checkFirstCategoryIs(shop().getFirstCategoryTitle(), "Вы покупали ранее");
        var firstCategoryProductNames = shop().getFirstCategoryProductNames();

        userShipments().goToPage();
        userShipments().clickToFirstShipment();
        userShipments().checkProductListsEquals(firstCategoryProductNames);
    }

    @CaseId(2947)
    @Test(description = "Блок 'Вы покупали ранее' не отображается, если у пользователя отменен заказ и этот заказ единственный", groups = {STF_PROD_S})
    public void testYouBoughtBeforeNotDisplayedIfOrderCancelled() {
        final UserData shoppingCartUser = UserManager.getQaUser();
        helper.makeOrder(shoppingCartUser, DEFAULT_SID, 3);
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultProdAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(shoppingCartUser);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();

        shop().checkFirstCategoryIs(shop().getFirstCategoryTitle(), "Вы покупали ранее");

        userShipments().goToPage();
        userShipments().clickToFirstShipment();
        userShipments().clickToCancelFromOrder();
        userShipments().interactShipmentCancelModal().shipmentCancelModalVisible();
        userShipments().interactShipmentCancelModal().clickToAccept();
        userShipments().checkStatusWasCanceled();

        shop().goToPage();
        shop().checkYouBoughtBeforeCategoryNotDisplayed();
    }
}
