package ru.instamart.test.reforged.stf.retail;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.shop;

//TODO: На стейдже сейчас нет виджетов, реализацию проверял на проде. После добавления на стейдже, нужно перепроверить
@Epic("STF UI")
@Feature("Retail Rocket")
public final class RetailRocketCartWidgetsTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    private final UserData userData = UserManager.getQaUser();

    @BeforeClass
    public void precondition() {
        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);
    }

    @CaseId(1767)
    @Test(description = "Тест наличия виджета 'Не забудьте купить' в корзине", groups = {"acceptance", "regression"})
    public void successCheckDontForgetToBuyWidget() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactHeader()
                .interactCart()
                .interactRetailRocket()
                .checkBlockAddToCart();
    }

    @CaseId(1768)
    @Test(description = "Тест успешного открытия карточки из виджета 'Не забудьте купить' в корзине",
            groups = {"acceptance", "regression"})
    public void successOpenItemCardFromDontForgetToBuyWidget() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactHeader()
                .interactCart()
                .interactRetailRocket()
                .clickToFirstProductInCarousel();
        shop().interactProductCard().checkProductCardVisible();
    }

    @CaseId(1769)
    @Test(description = "Тест успешного добавления товара в корзину из виджета ' Не забудьте купить' в корзине",
            groups = {"acceptance", "regression"})
    public void successAddItemFromDontForgetToBuyWidget() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactHeader()
                .interactCart()
                .interactRetailRocket()
                .addToCartFirstProductInCarousel();
        shop().interactCart().compareItemsInCart(2);
    }
}
