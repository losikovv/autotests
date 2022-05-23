package ru.instamart.test.reforged.stf.retail;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import ru.sbermarket.qase.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.reforged.stf.page.StfRouter.shop;

//TODO: На стейдже сейчас нет виджетов, реализацию проверял на проде. После добавления на стейдже, нужно перепроверить
@Epic("STF UI")
@Feature("Retail Rocket")
public final class RetailRocketRetailerPageWidgetsTests {

    private final ApiHelper helper = new ApiHelper();
    private final UserData userData = UserManager.getQaUser();

    @BeforeClass(alwaysRun = true)
    public void precondition() {
        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);
    }

    @CaseId(1785)
    @Test(description = "Тест наличия виджета 'Популярные товары' на главной", groups = {"acceptance", "regression"})
    public void successCheckPopularItemsWidget() {
        shop().goToPage();
        shop().interactRetailRocket().checkBlockPopular();
    }

    @CaseId(1787)
    @Test ( description = "Тест успешного открытия карточки товара из виджета 'Популярные товары' на главной",
            groups = {"acceptance", "regression"},
            dependsOnMethods = "successCheckPopularItemsWidget" )
    public void successOpenItemFromPopularItemsWidget() {
        shop().goToPage();
        shop().interactRetailRocket().clickToFirstProductInPopular();
        shop().interactProductCard().checkProductCardVisible();
    }

    @CaseId(1789)
    @Test ( description = "Тест успешного добавления товара из блока 'Популярные товары' на главной",
            groups = {"acceptance", "regression"},
            dependsOnMethods = "successCheckPopularItemsWidget" )
    public void successAddItemFromPopularItemsWidget() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactRetailRocket().addToCartFirstProductInPopular();
        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstRetailer().compareItemsInCart(1);
    }
}
