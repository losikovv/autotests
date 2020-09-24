package ru.instamart.tests.addons.RetailRocket;

import instamart.core.settings.Config;
import instamart.ui.common.lib.Addresses;
import instamart.ui.common.lib.Widgets;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.TestBase;

import static instamart.core.settings.Config.TestsConfiguration.AddonsTests.enableRetailRocketTest;

public class RetailRocketSearchWidgetsTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
        kraken.get().page(Config.CoreSettings.defaultTenant);
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().page(Config.CoreSettings.defaultTenant);
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест наличия виджета 'Мы нашли для вас похожие товары' после поиска без результата",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            priority = 12301
    )
    public void successCheckSimilarItemsWidget() {
        Shop.Search.item("говно жопа");

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.Search.SimilarItems()),
                    "Нет блока 'Мы нашли для вас похожие товары' после поиска без результата");
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест наличия виджета 'Те, кто ищут выбирают' после поиска товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            priority = 12302
    )
    public void successCheckFindersChoiceWidget() {
        Shop.Search.item("макароны");

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.Search.FindersChoice()),
                    "Нет блока 'Те, кто ищут выбирают' после поиска товара");
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест наличия виджета 'Вы недавно смотрели' после поиска товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            priority = 12303
    )
    public void successCheckRecentlyViewedWidget() {
        Shop.Search.item("макароны");

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.Search.RecentlyViewed()),
                    "Нет блока 'Вы недавно смотрели' после поиска товара");
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест открытия карточки товара из виджета 'Мы нашли для вас похожие товары' после поиска без результата",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            priority = 12304,
            dependsOnMethods = "successCheckSimilarItemsWidget"
    )
    public void successOpenItemCardFromSimilarItemsWidget() {
        Shop.Search.item("говно жопа");

        Shop.Catalog.Item.open(Widgets.RetailRocket.Search.SimilarItems());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Мы нашли для вас похожие товары' после поиска без результатов");
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест открытия карточки товара из виджета 'Те, кто ищут выбирают' после поиска товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            priority = 12305,
            dependsOnMethods = "successCheckFindersChoiceWidget"
    )
    public void successOpenItemCardFromFindersChoiceWidget() {
        Shop.Search.item("жир");

        Shop.Catalog.Item.open(Widgets.RetailRocket.Search.FindersChoice());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Те кто ищут выбирают' после поиска товара");
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест открытия карточки товара из виджета 'Вы недавно смотрели' после поиска товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            priority = 12306,
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successOpenItemCardFromRecentlyViewedWidget() {
        Shop.Cart.drop();
        Shop.Catalog.Item.open();
        Shop.ItemCard.close();
        kraken.perform().refresh();

        Shop.Search.item("жир");

        Shop.Catalog.Item.open(Widgets.RetailRocket.Search.RecentlyViewed());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Вы недавно смотрели' после поиска товара");
    }

    // TODO не работает, починить
    @Test(enabled = enableRetailRocketTest,
            description = "Тест успешного добавления товара в корзину из виджета 'Мы нашли для вас похожие товары' после поиска без результата",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            priority = 12307,
            dependsOnMethods = "successCheckSimilarItemsWidget"
    )
    public void successAddItemFromSimilarItemsWidget() {
        Shop.Search.item("говно жопа");

        Shop.Catalog.Item.addToCart(Widgets.RetailRocket.Search.SimilarItems());

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'Мы нашли для вас похожие товары' после поиска без результата");
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест успешного добавления товара в корзину из виджета 'Те, кто ищут выбирают' после поиска товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            priority = 12308,
            dependsOnMethods = "successCheckFindersChoiceWidget"
    )
    public void successAddItemFromFindersChoiceWidget() {
        Shop.Search.item("жир");
        Shop.Catalog.Item.addToCart(Widgets.RetailRocket.Search.FindersChoice());

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'Те, кто ищут выбирают' после поиска товара");
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест успешного добавления товара в корзину из виджета 'Вы недавно смотрели' после поиска товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            priority = 12309,
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successAddItemFromRecentlyViewedWidget() {
        Shop.Cart.drop();
        Shop.Catalog.Item.open();
        Shop.ItemCard.close();
        kraken.perform().refresh();

        Shop.Search.item("жир");
        Shop.Catalog.Item.addToCart(Widgets.RetailRocket.Search.RecentlyViewed());

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'Вы недавно смотрели' после поиска товара");
    }
}
