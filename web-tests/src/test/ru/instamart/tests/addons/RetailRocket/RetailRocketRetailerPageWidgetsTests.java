package ru.instamart.tests.addons.RetailRocket;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import instamart.core.settings.Config;
import instamart.ui.common.lib.Addresses;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import instamart.ui.common.lib.Widgets;
import ru.instamart.tests.TestBase;

public class RetailRocketRetailerPageWidgetsTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);
    }

    @Test ( enabled = Config.TestsConfiguration.AddonsTests.enableRetailRocketTest,
            description = "Тест наличия виджета 'Популярные товары' на главной",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 12001
    )
    public void successCheckPopularItemsWidget() {

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.MainPage.PopularItems()),
                    "Нет блока 'Популярные товары' на главной");
    }

    @Test ( enabled = Config.TestsConfiguration.AddonsTests.enableRetailRocketTest,
            description = "Тест наличия виджета 'Вы недавно смотрели' на главной",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 12002
    )
    public void successCheckRecentlyViewedWidget() {

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.MainPage.RecentlyViewed()),
                    "Нет блока 'Вы недавно смотрели' на главной");
    }

    @Test ( enabled = Config.TestsConfiguration.AddonsTests.enableRetailRocketTest,
            description = "Тест успешного открытия карточки товара из виджета 'Популярные товары' на главной",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 12003,
            dependsOnMethods = "successCheckPopularItemsWidget"
    )
    public void successOpenItemFromPopularItemsWidget() {
        Shop.Catalog.Item.open(Widgets.RetailRocket.MainPage.PopularItems());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Популярные товары' на главной");
    }

    @Test ( enabled = Config.TestsConfiguration.AddonsTests.enableRetailRocketTest,
            description = "Тест успешного открытия карточки товара из виджета 'Вы недавно смотрели' на главной",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 12004,
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successOpenItemFromRecentlyViewedWidget() {
        Shop.Catalog.Item.open(Widgets.RetailRocket.MainPage.PopularItems());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Вы недавно смотрели' на главной");
    }

    @Test ( enabled = Config.TestsConfiguration.AddonsTests.enableRetailRocketTest,
            description = "Тест успешного добавления товара из блока 'Популярные товары' на главной",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 12005,
            dependsOnMethods = "successCheckPopularItemsWidget"
    )
    public void successAddItemFromPopularItemsWidget() {
        Shop.Cart.drop();

        Shop.Catalog.Item.addToCart(Widgets.RetailRocket.MainPage.PopularItems());

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'Популярные товары' на главной");
    }

    @Test ( enabled = Config.TestsConfiguration.AddonsTests.enableRetailRocketTest,
            description = "Тест успешного добавления товара из блока 'Вы недавно смотрели' на главной",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 12006,
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successAddItemFromRecentlyViewedWidget() {
        Shop.Cart.drop();

        Shop.Catalog.Item.open();
        Shop.ItemCard.close();
        kraken.perform().refresh();
        Shop.Catalog.Item.addToCart(Widgets.RetailRocket.MainPage.RecentlyViewed());

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'Вы недавно смотрели' на главной");
    }
}
