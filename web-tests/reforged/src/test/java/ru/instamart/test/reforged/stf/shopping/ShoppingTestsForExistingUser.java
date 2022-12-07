package ru.instamart.test.reforged.stf.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.core.config.UiProperties.DEFAULT_AUCHAN_SID;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Заказ")
public class ShoppingTestsForExistingUser {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(1616)
    @Story("Тест недоступности чекаута по прямой ссылке авторизованному юзеру c выбранным адресом и пустой корзиной")
    @Test(description = "Тест недоступности чекаута по прямой ссылке авторизованному юзеру c выбранным адресом и пустой корзиной",
            groups = REGRESSION_STF)
    public void noAccessToCheckoutForAuthorizedUserWithShipAddressAndEmptyCart() {
        final UserData shoppingCartUser = UserManager.getQaUser();
        helper.dropCart(shoppingCartUser);
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(shoppingCartUser);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        checkout().goToPage();
        checkout().checkPageError404();
    }

    @CaseId(1617)
    @Story("Тест недоступности чекаута при сумме корзины меньше минимального заказа")
    @Test(description = "Тест недоступности чекаута при сумме корзины меньше минимального заказа",
            groups = REGRESSION_STF)
    public void noAccessToCheckoutWithCartBelowMinimalOrderSum() {
        final UserData shoppingCartUser = UserManager.getQaUser();
        helper.dropCart(shoppingCartUser);
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(shoppingCartUser);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().interactHeader().fillSearch("молоко");
        shop().interactHeader().clickSearchButton();
        shop().interactHeader().checkEnteredAddressIsVisible();
        search().checkAddToCartButtonVisible();
        search().clickAddToCartFirstSearchResult();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkOrderButtonIsNotEnabled();
    }

    @CaseId(2606)
    @Story("Тест набора корзины до суммы, достаточной для оформления заказа")

    @Test(description = "Тест набора корзины до суммы, достаточной для оформления заказа",
            groups = REGRESSION_STF)
    public void successCollectItemsForMinOrder() {
        final UserData shoppingCartUser = UserManager.getQaUser();
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();

        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(shoppingCartUser);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().plusFirstItemToCart();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartNotEmpty();
        shop().interactCart().checkOrderButtonIsNotEnabled();
        shop().interactCart().increaseFirstItemCountToMin();
        shop().interactCart().checkOrderButtonIsEnabled();
        shop().interactCart().submitOrder();

        checkoutNew().checkDeliverySlotsVisible();
    }

    @CaseId(1619)
    @Story("Тест на подтягивание адреса и мердж корзины из профиля при авторизации")
    @Test(description = "Тест на подтягивание адреса и мердж корзины из профиля при авторизации", groups = REGRESSION_STF)
    public void successMergeShipAddressAndCartAfterAuthorisation() {
        final UserData shoppingCartUser = UserManager.getQaUser();
        helper.dropAndFillCart(shoppingCartUser, DEFAULT_AUCHAN_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(shoppingCartUser);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartNotEmpty();
    }
}
