package ru.instamart.test.reforged.next.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_AUCHAN_SID;
import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID;
import static ru.instamart.reforged.next.page.StfRouter.*;

@Epic("STF UI")
@Feature("Раздел 'Вы покупали ранее'")
public final class ShoppingYouBoughtBeforeTests {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(2943)
    @Test(description = "Товар отображается в блоке 'Вы покупали ранее', если отправлен на сборку", groups = {"regression"})
    public void testYouBoughtBeforeDisplayed() {
        final UserData shoppingCartUser = UserManager.getQaUser();
        helper.makeOrder(shoppingCartUser, DEFAULT_METRO_MOSCOW_SID, 3);
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultAddress());

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

    @CaseId(2944)
    @Test(description = "Товары, купленные у ретейлера отображаются для любого магазина данного ретейлера", groups = {"regression"})
    public void testYouBoughtBeforeDisplayedForAnotherShopSameRetailer() {
        final UserData shoppingCartUser = UserManager.getQaUser();
        helper.makeOrder(shoppingCartUser, DEFAULT_METRO_MOSCOW_SID, 3);
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(shoppingCartUser);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();

        shop().checkFirstCategoryIs(shop().getFirstCategoryTitle(), "Вы покупали ранее");
        var firstShopBoughtBeforeProductNames = shop().getFirstCategoryProductNames();

        shop().interactHeader().clickToSelectAddress();
        shop().interactHeader().interactAddress().checkYmapsReady();
        shop().interactHeader().interactAddress().fillAddress(Addresses.Moscow.testAddress());
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().interactAddress().checkAddressModalIsNotVisible();

        shop().checkFirstCategoryIs(shop().getFirstCategoryTitle(), "Вы покупали ранее");
        shop().checkProductListsEquals(shop().getFirstCategoryProductNames(), firstShopBoughtBeforeProductNames);
    }

    @CaseId(2945)
    @Test(description = "Товары не отображаются, если выбран другой ретейлер", groups = {"regression"})
    public void testYouBoughtBeforeNotDisplayedForAnotherRetailer() {
        final UserData shoppingCartUser = UserManager.getQaUser();
        helper.makeOrder(shoppingCartUser, DEFAULT_METRO_MOSCOW_SID, 3);
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultAddress());

        shop().goToPage(true);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(shoppingCartUser);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();

        shop().checkFirstCategoryIs(shop().getFirstCategoryTitle(), "Вы покупали ранее");

        shop().interactHeader().clickToStoreSelector();
        home().clickOnStoreWithSid(DEFAULT_AUCHAN_SID);

        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().checkYouBoughtBeforeCategoryNotDisplayed();
    }

    @CaseId(2946)
    @Test(description = "Блок 'Вы покупали ранее' не отображается, если у пользователя ранее не было сформированных заказов", groups = {"regression"})
    public void testYouBoughtBeforeNotDisplayedIfNoPreviousOrders() {
        final UserData shoppingCartUser = UserManager.getQaUser();
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(shoppingCartUser);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();

        shop().checkYouBoughtBeforeCategoryNotDisplayed();
    }

    @CaseId(2947)
    @Test(description = "Блок 'Вы покупали ранее' не отображается, если у пользователя отменен заказ и этот заказ единственный", groups = {"regression"})
    public void testYouBoughtBeforeNotDisplayedIfOrderCancelled() {
        final UserData shoppingCartUser = UserManager.getQaUser();
        helper.makeOrder(shoppingCartUser, DEFAULT_METRO_MOSCOW_SID, 3);
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultAddress());

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
