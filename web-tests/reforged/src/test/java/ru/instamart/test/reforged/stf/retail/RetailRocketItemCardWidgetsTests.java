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
import ru.instamart.reforged.CookieFactory;

import static ru.instamart.reforged.stf.page.StfRouter.shop;

//TODO: На стейдже сейчас нет виджетов, реализацию проверял на проде. После добавления на стейдже, нужно перепроверить
@Epic("STF UI")
@Feature("Retail Rocket")
public final class RetailRocketItemCardWidgetsTests {

    private final ApiHelper helper = new ApiHelper();
    private final UserData userData = UserManager.getQaUser();

    @BeforeClass(alwaysRun = true)
    public void precondition() {
        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);
    }

    @CaseId(1776)
    @Test(description = "Тест наличия виджета 'С этим товаром покупают' в карточке товара", groups = {"acceptance", "regression"})
    public void successCheckWithThisItemBuyWidget() {
        shop().goToPage();
        shop().openFirstProductCardProd();
        shop().interactProductCard().interactRetailRocket().checkBlockWithThisProduct();
    }

    @CaseId(1777)
    @Test(description = "Тест наличия виджета 'Похожие товары' в карточке товара", groups = {"acceptance", "regression"})
    public void successCheckSimilarItemsWidget() {
        shop().goToPage();
        shop().addCookie(CookieFactory.COOKIE_ALERT);
        shop().refresh();
        shop().openFirstProductCardProd();
        shop().interactProductCard().interactRetailRocket().checkBlockSimilar();
    }

    @CaseId(1779)
    @Test(  description = "Тест открытия карточки товара из виджета 'C этим товаром покупают' в карточке товара",
            groups = {"acceptance", "regression"},
            dependsOnMethods = "successCheckWithThisItemBuyWidget")
    public void successOpenItemFromWithThisItemBuyWidget() {
        shop().goToPage();
        shop().openFirstProductCardProd();
        shop().interactProductCard().interactRetailRocket().clickToFirstProductInCarousel();
        shop().interactProductCard().checkProductCardVisible();
    }

    @CaseId(1780)
    @Test(  description = "Тест открытия карточки товара из виджета 'Похожие товары' в карточке товара",
            groups = {"acceptance", "regression"},
            dependsOnMethods = "successCheckSimilarItemsWidget")
    public void successOpenItemFromSimilarItemsWidget() {
        shop().goToPage();
        shop().addCookie(CookieFactory.COOKIE_ALERT);
        shop().refresh();
        shop().openFirstProductCardProd();
        shop().interactProductCard().interactRetailRocket().clickToFirstProductInSimilar();
        shop().interactProductCard().checkProductCardVisible();
    }

    @CaseId(1782)
    @Test(  description = "Тест успешного добавления товара из виджета 'С этим товаром покупают' в карточке товара",
            groups = {"acceptance", "regression"},
            dependsOnMethods = "successCheckWithThisItemBuyWidget")
    public void successAddItemFromWithThisItemBuyWidget() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().openFirstProductCardProd();
        shop().interactProductCard().interactRetailRocket().addToCartFirstProductInCarousel();
        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstRetailer().compareItemsInCart(1);
    }

    @CaseId(1783)
    @Test(  description = "Тест успешного добавления товара из виджета 'Похожие товары' в карточке товара",
            groups = {"acceptance", "regression"},
            dependsOnMethods = "successCheckSimilarItemsWidget")
    public void successAddItemFromSimilarItemsWidget() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
        shop().addCookie(CookieFactory.COOKIE_ALERT);
        shop().refresh();

        shop().openFirstProductCardProd();
        shop().interactProductCard().interactRetailRocket().addToCartFirstProductInSimilar();
        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstRetailer().compareItemsInCart(1);
    }
}
