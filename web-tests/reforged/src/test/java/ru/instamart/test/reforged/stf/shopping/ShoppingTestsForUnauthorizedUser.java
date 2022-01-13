package ru.instamart.test.reforged.stf.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import ru.sbermarket.qase.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;
import static ru.instamart.reforged.stf.page.StfRouter.search;

@Epic("STF UI")
@Feature("Заказ")
public final class ShoppingTestsForUnauthorizedUser extends BaseTest {

    @CaseId(1622)
    @Story("Тест недоступности чекаута при сумме корзины меньше минимального заказа")
    @Test(description = "Тест недоступности чекаута при сумме корзины меньше минимального заказа",
            groups = {"acceptance", "regression"})
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
    @Test(description = "Тест набора корзины до суммы, достаточной для заказа", groups = {"acceptance", "regression"})
    public void successCollectItemsForOrder() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().plusFirstItemToCart();

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstItem().increaseCountToMin();
        shop().interactCart().submitOrder();
        shop().interactAuthModal().checkModalIsVisible();

        checkout().goToPage();
        shop().interactHeader().checkAuthOrRegAlertVisible();
        shop().checkDefaultShopOpened();
    }

    @CaseId(2608)
    @Test(description = "Тест недоступности пустого чекаута по прямой ссылке", groups = {"acceptance", "regression"})
    public void testCheckoutNoAccessForUserWithoutCart() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        shop().interactHeader().checkMinAmountAlertVisible();
    }

    @CaseId(2301)
    @Test(description = "Тест недоступности чекаута неавторизованному пользователю", groups = {"acceptance", "regression"})
    public void testCheckoutNoAccessForGuest() {
        home().goToPage();
        checkout().goToPage();
        shop().interactHeader().checkAuthOrRegAlertVisible();
    }
}
