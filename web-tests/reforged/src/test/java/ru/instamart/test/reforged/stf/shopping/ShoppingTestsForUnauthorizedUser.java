package ru.instamart.test.reforged.stf.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.CookieProvider;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Заказ")
public final class ShoppingTestsForUnauthorizedUser {

    @CaseId(1622)
    @Story("Тест недоступности чекаута при сумме корзины меньше минимального заказа")
    @Test(description = "Тест недоступности чекаута при сумме корзины меньше минимального заказа",
            groups = "regression")
    public void noAccessToCheckoutWithCartBelowMinimalOrderSum() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().goToPage();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().interactHeader().fillSearch("молоко");
        shop().interactHeader().clickSearchButton();
        shop().interactHeader().checkEnteredAddressIsVisible();
        search().checkAddToCartButtonVisible();
        search().clickAddToCartFirstSearchResult();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkOrderButtonIsNotEnabled();
    }

    @CaseId(1623)
    @Story("Тест набора корзины до суммы, достаточной для заказа")
    @Test(description = "Тест набора корзины до суммы, достаточной для заказа", groups = "regression")
    public void successCollectItemsForOrder() {
        home().goToPage();
        home().fillAddressInLanding(Addresses.Moscow.defaultAddress());
        home().selectFirstAddressInFounded();
        home().checkDeliveryStoresContainerVisible();
        home().clickOnStoreWithSid(DEFAULT_METRO_MOSCOW_SID);

        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().checkFirstProductCardIsVisible();
        shop().plusItemToCart("1", "0");

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().increaseFirstItemCountToMin();
        shop().interactCart().submitOrder();
        shop().interactAuthModal().checkModalIsVisible();

        checkout().goToPage();
        shop().interactHeader().checkAuthOrRegAlertVisible();
        home().checkLoginButtonIsVisible();
    }

    @CaseId(2608)
    @Test(description = "Тест недоступности пустого чекаута по прямой ссылке", groups = "regression")
    public void testCheckoutNoAccessForUserWithoutCart() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        shop().interactHeader().checkMinAmountAlertVisible();
    }

    @CaseId(2301)
    @Test(description = "Тест недоступности чекаута неавторизованному пользователю", groups = "regression")
    public void testCheckoutNoAccessForGuest() {
        home().goToPage();
        checkout().goToPage();
        shop().interactHeader().checkAuthOrRegAlertVisible();
    }
}
