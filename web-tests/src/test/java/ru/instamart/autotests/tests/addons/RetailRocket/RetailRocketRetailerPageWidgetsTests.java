package ru.instamart.autotests.tests.addons.RetailRocket;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.libs.Addresses;
import ru.instamart.autotests.application.libs.Widgets;
import ru.instamart.autotests.appmanager.platform.Shop;
import ru.instamart.autotests.appmanager.platform.User;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.application.Config.TestsConfiguration.AddonsTests.enableRetailRocketTest;

public class RetailRocketRetailerPageWidgetsTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Do.quickLogout();
        kraken.get().page("metro");
        Shop.ShippingAddress.set(Addresses.Moscow.defaultAddress());
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().page("metro");
    }

    @Test ( enabled = enableRetailRocketTest,
            description = "Тест наличия виджета 'Популярные товары' на главной",
            groups = {"acceptance","regression"},
            priority = 12001
    )
    public void successCheckPopularItemsWidget() {

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.MainPage.PopularItems()),
                    "Нет блока 'Популярные товары' на главной");
    }

    @Test ( enabled = enableRetailRocketTest,
            description = "Тест наличия виджета 'Вы недавно смотрели' на главной",
            groups = {"acceptance","regression"},
            priority = 12002
    )
    public void successCheckRecentlyViewedWidget() {

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.MainPage.RecentlyViewed()),
                    "Нет блока 'Вы недавно смотрели' на главной");
    }

    @Test ( enabled = enableRetailRocketTest,
            description = "Тест успешного открытия карточки товара из виджета 'Популярные товары' на главной",
            groups = {"acceptance","regression"},
            priority = 12003,
            dependsOnMethods = "successCheckPopularItemsWidget"
    )
    public void successOpenItemFromPopularItemsWidget() {
        Shop.Catalog.Item.open(Widgets.RetailRocket.MainPage.PopularItems());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Популярные товары' на главной");
    }

    @Test ( enabled = enableRetailRocketTest,
            description = "Тест успешного открытия карточки товара из виджета 'Вы недавно смотрели' на главной",
            groups = {"acceptance","regression"},
            priority = 12004,
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successOpenItemFromRecentlyViewedWidget() {
        Shop.Catalog.Item.open(Widgets.RetailRocket.MainPage.PopularItems());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Вы недавно смотрели' на главной");
    }

    @Test ( enabled = enableRetailRocketTest,
            description = "Тест успешного добавления товара из блока 'Популярные товары' на главной",
            groups = {"acceptance","regression"},
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

    @Test ( enabled = enableRetailRocketTest,
            description = "Тест успешного добавления товара из блока 'Вы недавно смотрели' на главной",
            groups = {"acceptance","regression"},
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
