package ru.instamart.test.reforged.stf.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.UiProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Заказ")
public class ShoppingTestsForExistingUser {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(1616)
    @Story("Тест недоступности чекаута по прямой ссылке авторизованному юзеру c выбранным адресом и пустой корзиной")
    @Test(description = "Тест недоступности чекаута по прямой ссылке авторизованному юзеру c выбранным адресом и пустой корзиной",
            groups = "regression")
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
        shop().interactHeader().checkMinAmountAlertVisible();
        home().checkPageUrl(UiProperties.STF_URL);
    }

    @CaseId(1617)
    @Story("Тест недоступности чекаута при сумме корзины меньше минимального заказа")
    @Test(description = "Тест недоступности чекаута при сумме корзины меньше минимального заказа",
            groups = "regression")
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

        shop().goToPage(true);
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

    @CaseId(2606)
    @Story("Тест набора корзины до суммы, достаточной для оформления заказа")
    @Test(description = "Тест набора корзины до суммы, достаточной для оформления заказа",
            groups = "regression")
    public void successCollectItemsForMinOrder() {
        final UserData shoppingCartUser = UserManager.getQaUser();
        helper.dropAndFillCart(shoppingCartUser, 1);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(shoppingCartUser);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartNotEmpty();
        shop().interactCart().checkOrderButtonIsEnabled();
        shop().interactCart().submitOrder();
        checkout().checkPageIsAvailable();
        checkout().checkCheckoutButtonIsVisible();
    }

    @CaseId(1619)
    @Story("Тест на подтягивание адреса и мердж корзины из профиля при авторизации")
    @Test(description = "Тест на подтягивание адреса и мердж корзины из профиля при авторизации", groups = "regression")
    public void successMergeShipAddressAndCartAfterAuthorisation() {
        final UserData shoppingCartUser = UserManager.getQaUser();
        helper.dropAndFillCart(shoppingCartUser, 1);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(shoppingCartUser);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartNotEmpty();
    }
}
