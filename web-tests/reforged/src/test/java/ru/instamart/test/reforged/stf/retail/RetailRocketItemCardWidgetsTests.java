package ru.instamart.test.reforged.stf.retail;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.home;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

//TODO: На стейдже сейчас нет виджетов, реализацию проверял на проде. После добавления на стейдже, нужно перепроверить
@Epic("STF UI")
@Feature("Retail Rocket")
public final class RetailRocketItemCardWidgetsTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    private final UserData userData = UserManager.getUser();

    @BeforeClass
    public void precondition() {
        helper.auth(userData);
        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);
    }


    @CaseId(1776)
    @Test( description = "Тест наличия виджета 'С этим товаром покупают' в карточке товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successCheckWithThisItemBuyWidget() {
        shop().goToPage();
        shop().openFirstProductCard();
        shop().interactProductCard().interactRetailRocket().checkBlockWithThisProduct();
    }

    @CaseId(1777)
    @Test(  description = "Тест наличия виджета 'Похожие товары' в карточке товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successCheckSimilarItemsWidget() {
        shop().goToPage();
        shop().addCookie(CookieFactory.COOKIE_ALERT);
        shop().refresh();
        shop().openFirstProductCard();
        shop().interactProductCard().interactRetailRocket().checkBlockSimilar();
    }

    // TODO: На проде такого блока нет уже
    @Deprecated
    @Skip
    @CaseId(1778)
    @Test(  description = "Тест наличия виджета 'Вы недавно смотрели' в карточке товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successCheckRecentlyViewedWidget() {
    }

    @CaseId(1779)
    @Test(  description = "Тест открытия карточки товара из виджета 'C этим товаром покупают' в карточке товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            dependsOnMethods = "successCheckWithThisItemBuyWidget"
    )
    public void successOpenItemFromWithThisItemBuyWidget() {
        shop().goToPage();
        shop().openFirstProductCard();
        shop().interactProductCard().interactRetailRocket().clickToFirstProductInCarousel();
        shop().interactProductCard().checkProductCardVisible();
    }

    @CaseId(1780)
    @Test(  description = "Тест открытия карточки товара из виджета 'Похожие товары' в карточке товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            dependsOnMethods = "successCheckSimilarItemsWidget"
    )
    public void successOpenItemFromSimilarItemsWidget() {
        shop().goToPage();
        shop().addCookie(CookieFactory.COOKIE_ALERT);
        shop().refresh();
        shop().openFirstProductCard();
        shop().interactProductCard().interactRetailRocket().clickToFirstProductInSimilar();
        shop().interactProductCard().checkProductCardVisible();
    }

    // TODO: На проде такого блока нет уже
    @Deprecated
    @Skip
    @CaseId(1781)
    @Test(  description = "Тест открытия карточки товара из виджета 'Вы недавно смотрели' в картчоке товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successOpenItemFromRecentlyViewedWidget() {
    }

    @CaseId(1782)
    @Test(  description = "Тест успешного добавления товара из виджета 'С этим товаром покупают' в карточке товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            dependsOnMethods = "successCheckWithThisItemBuyWidget"
    )
    public void successAddItemFromWithThisItemBuyWidget() {
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);

        shop().interactHeader().checkProfileButtonVisible();
        shop().openFirstProductCard();
        shop().interactProductCard().interactRetailRocket().addToCartFirstProductInCarousel();
        shop().interactHeader().clickToCart();
        shop().interactCart().compareItemsInCart(1);
    }

    @CaseId(1783)
    @Test(  description = "Тест успешного добавления товара из виджета 'Похожие товары' в карточке товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            dependsOnMethods = "successCheckSimilarItemsWidget"
    )
    public void successAddItemFromSimilarItemsWidget() {
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);

        shop().interactHeader().checkProfileButtonVisible();
        shop().addCookie(CookieFactory.COOKIE_ALERT);
        shop().refresh();

        shop().openFirstProductCard();
        shop().interactProductCard().interactRetailRocket().addToCartFirstProductInSimilar();
        shop().interactHeader().clickToCart();
        shop().interactCart().compareItemsInCart(1);
    }

    // TODO: На проде такого блока нет уже
    @Deprecated
    @Skip
    @CaseId(1784)
    @Test(  description = "Тест успешного добавления товара из виджета 'Вы недавно смотрели' в карточке товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successAddItemFromRecentlyViewedWidget() {
    }
}
