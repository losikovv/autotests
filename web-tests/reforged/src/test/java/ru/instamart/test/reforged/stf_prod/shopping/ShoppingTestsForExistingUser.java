package ru.instamart.test.reforged.stf_prod.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.UiProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.stf.page.StfRouter.checkoutNew;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Заказ")
public class ShoppingTestsForExistingUser {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(2606)
    @Story("Тест набора корзины до суммы, достаточной для оформления заказа")
    @Test(description = "Тест набора корзины до суммы, достаточной для оформления заказа",
            groups = {STF_PROD_S})
    public void successCollectItemsForMinOrder() {
        final UserData shoppingCartUser = UserManager.getQaUser();
        helper.dropAndFillCart(shoppingCartUser, UiProperties.DEFAULT_SID);

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

        checkoutNew().checkSpinnerNotVisible();
        checkoutNew().checkDeliverySlotsVisible();
    }
}
