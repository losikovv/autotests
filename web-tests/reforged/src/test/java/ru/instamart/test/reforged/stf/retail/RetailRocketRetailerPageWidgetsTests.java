package ru.instamart.test.reforged.stf.retail;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.home;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

//TODO: На стейдже сейчас нет виджетов, реализацию проверял на проде. После добавления на стейдже, нужно перепроверить
@Epic("STF UI")
@Feature("Retail Rocket")
public final class RetailRocketRetailerPageWidgetsTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    private final UserData userData = UserManager.getUser();

    @BeforeClass
    public void precondition() {
        helper.auth(userData);
        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);
    }

    @CaseId(1785)
    @Test( description = "Тест наличия виджета 'Популярные товары' на главной",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successCheckPopularItemsWidget() {
        shop().goToPage();
        shop().interactRetailRocket().checkBlockPopular();
    }

    // TODO: На проде такого блока нет уже
    @Deprecated
    @Skip
    @CaseId(1786)
    @Test ( description = "Тест наличия виджета 'Вы недавно смотрели' на главной",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successCheckRecentlyViewedWidget() {
    }

    @CaseId(1787)
    @Test ( description = "Тест успешного открытия карточки товара из виджета 'Популярные товары' на главной",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            dependsOnMethods = "successCheckPopularItemsWidget"
    )
    public void successOpenItemFromPopularItemsWidget() {
        shop().goToPage();
        shop().interactRetailRocket().clickToFirstProductInPopular();
        shop().interactProductCard().checkProductCardVisible();
    }

    // TODO: На проде такого блока нет уже
    @Deprecated
    @Skip
    @CaseId(1788)
    @Test ( description = "Тест успешного открытия карточки товара из виджета 'Вы недавно смотрели' на главной",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successOpenItemFromRecentlyViewedWidget() {
    }

    @CaseId(1789)
    @Test ( description = "Тест успешного добавления товара из блока 'Популярные товары' на главной",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            dependsOnMethods = "successCheckPopularItemsWidget"
    )
    public void successAddItemFromPopularItemsWidget() {
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);

        shop().interactHeader().checkProfileButtonVisible();
        shop().interactRetailRocket().addToCartFirstProductInPopular();
        shop().interactHeader().clickToCart();
        shop().interactCart().compareItemsInCart(1);
    }

    // TODO: На проде такого блока нет уже
    @Deprecated
    @Skip
    @CaseId(1790)
    @Test ( description = "Тест успешного добавления товара из блока 'Вы недавно смотрели' на главной",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successAddItemFromRecentlyViewedWidget() {
    }
}
