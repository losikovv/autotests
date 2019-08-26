package ru.instamart.autotests.tests.addons.RetailRocket;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Widgets;
import ru.instamart.autotests.appmanager.ShopHelper;
import ru.instamart.autotests.appmanager.User;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.application.Config.TestsConfiguration.AddonsTests.enableRetailRocketTest;

public class RetailRocketSearchWidgetsTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Do.quickLogout();
        kraken.get().page("metro");
        ShopHelper.ShippingAddress.set(Addresses.Moscow.defaultAddress());
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().page("metro");
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест наличия виджета 'Мы нашли для вас похожие товары' после поиска без результата",
            groups = {"acceptance", "regression"},
            priority = 12301
    )
    public void successCheckSimilarItemsWidget() {
        ShopHelper.Search.item("смысл жизни");

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.Search.SimilarItems()),
                    "Нет блока 'Мы нашли для вас похожие товары' после поиска без результата");
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест наличия виджета 'Те, кто ищут выбирают' после поиска товара",
            groups = {"acceptance", "regression"},
            priority = 12302
    )
    public void successCheckFindersChoiceWidget() {
        ShopHelper.Search.item("макароны");

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.Search.FindersChoice()),
                    "Нет блока 'Те, кто ищут выбирают' после поиска товара");
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест наличия виджета 'Вы недавно смотрели' после поиска товара",
            groups = {"acceptance", "regression"},
            priority = 12303
    )
    public void successCheckRecentlyViewedWidget() {
        ShopHelper.Search.item("макароны");

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.Search.RecentlyViewed()),
                    "Нет блока 'Вы недавно смотрели' после поиска товара");
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест открытия карточки товара из виджета 'Мы нашли для вас похожие товары' после поиска без результата",
            groups = {"acceptance", "regression"},
            priority = 12304,
            dependsOnMethods = "successCheckSimilarItemsWidget"
    )
    public void successOpenItemCardFromSimilarItemsWidget() {
        ShopHelper.Search.item("смысл жизни");

        ShopHelper.Catalog.Item.open(Widgets.RetailRocket.Search.SimilarItems());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Мы нашли для вас похожие товары' после поиска без результатов");
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест открытия карточки товара из виджета 'Те, кто ищут выбирают' после поиска товара",
            groups = {"acceptance", "regression"},
            priority = 12305,
            dependsOnMethods = "successCheckFindersChoiceWidget"
    )
    public void successOpenItemCardFromFindersChoiceWidget() {
        ShopHelper.Search.item("жир");

        ShopHelper.Catalog.Item.open(Widgets.RetailRocket.Search.FindersChoice());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Те кто ищут выбирают' после поиска товара");
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест открытия карточки товара из виджета 'Вы недавно смотрели' после поиска товара",
            groups = {"acceptance", "regression"},
            priority = 12306,
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successOpenItemCardFromRecentlyViewedWidget() {
        ShopHelper.Cart.drop();
        ShopHelper.Catalog.Item.open();
        ShopHelper.ItemCard.close();
        kraken.perform().refresh();

        ShopHelper.Search.item("жир");

        ShopHelper.Catalog.Item.open(Widgets.RetailRocket.Search.RecentlyViewed());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Вы недавно смотрели' после поиска товара");
    }

    // TODO не работает, починить
    @Test(enabled = enableRetailRocketTest,
            description = "Тест успешного добавления товара в корзину из виджета 'Мы нашли для вас похожие товары' после поиска без результата",
            groups = {"acceptance", "regression"},
            priority = 12307,
            dependsOnMethods = "successCheckSimilarItemsWidget"
    )
    public void successAddItemFromSimilarItemsWidget() {
        ShopHelper.Search.item("смысл жизни");

        ShopHelper.Catalog.Item.addToCart(Widgets.RetailRocket.Search.SimilarItems());

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'Мы нашли для вас похожие товары' после поиска без результата");
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест успешного добавления товара в корзину из виджета 'Те, кто ищут выбирают' после поиска товара",
            groups = {"acceptance", "regression"},
            priority = 12308,
            dependsOnMethods = "successCheckFindersChoiceWidget"
    )
    public void successAddItemFromFindersChoiceWidget() {
        ShopHelper.Search.item("жир");
        ShopHelper.Catalog.Item.addToCart(Widgets.RetailRocket.Search.FindersChoice());

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'Те, кто ищут выбирают' после поиска товара");
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест успешного добавления товара в корзину из виджета 'Вы недавно смотрели' после поиска товара",
            groups = {"acceptance", "regression"},
            priority = 12309,
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successAddItemFromRecentlyViewedWidget() {
        ShopHelper.Cart.drop();
        ShopHelper.Catalog.Item.open();
        ShopHelper.ItemCard.close();
        kraken.perform().refresh();

        ShopHelper.Search.item("жир");
        ShopHelper.Catalog.Item.addToCart(Widgets.RetailRocket.Search.RecentlyViewed());

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'Вы недавно смотрели' после поиска товара");
    }
}
