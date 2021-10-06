package ru.instamart.test.reforged.stf.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.testdata.lib.Addresses;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;
import static ru.instamart.reforged.stf.page.StfRouter.search;

@Epic("STF UI")
@Feature("Заказ")
public class ShoppingTestsForUnauthorizedUser extends BaseTest {

    @CaseId(1620)
    @Story("Тест недоступности чекаута неавторизованному юзеру")
    @Test(
            description = "Тест недоступности чекаута неавторизованному юзеру",
            groups = {"sbermarket-acceptance", "sbermarket-regression",
                    "metro-acceptance", "metro-regression"
            }
    )
    public void noAccessToCheckoutByDefault() {
        shop().goToPage();

        checkout().goToPage();
        home().checkMainBlockTextIsVisible();
    }

    @CaseId(1621)
    @Story("Тест недоступности чекаута неавторизованному юзеру c выбранным адресом и пустой корзиной")
    @Test(
            description = "Тест недоступности чекаута неавторизованному юзеру c выбранным адресом и пустой корзиной",
            groups = {"sbermarket-acceptance", "sbermarket-regression",
                    "metro-acceptance", "metro-regression"
            }
    )
    public void noAccessToCheckoutForUnauthorizedUserWithShipAddressAndEmptyCart() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().setAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        checkout().goToPage();
        shop().interactHeader().checkAuthOrRegAlertVisible();
        shop().checkDefaultShopOpened();
    }

    @CaseId(1622)
    @Story("Тест недоступности чекаута при сумме корзины меньше минимального заказа")
    @Test(
            description = "Тест недоступности чекаута при сумме корзины меньше минимального заказа",
            groups = {"sbermarket-acceptance", "sbermarket-regression",
                    "metro-acceptance", "metro-regression"
            }
    )
    public void noAccessToCheckoutWithCartBelowMinimalOrderSum() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().setAddress(Addresses.Moscow.defaultAddress());
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
    @Test(
            description = "Тест набора корзины до суммы, достаточной для заказа",
            groups = {"sbermarket-acceptance", "sbermarket-regression",
                    "metro-acceptance", "metro-regression"
            }
    )
    public void successCollectItemsForOrder() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().setAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().plusFirstItemToCart();

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().increaseCountToMin();
        shop().interactCart().submitOrder();
        shop().interactAuthModal().checkModalIsVisible();

        checkout().goToPage();
        shop().interactHeader().checkAuthOrRegAlertVisible();
        shop().checkDefaultShopOpened();
    }
}
