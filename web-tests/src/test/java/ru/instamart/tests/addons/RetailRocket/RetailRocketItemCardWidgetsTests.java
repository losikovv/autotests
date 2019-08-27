package ru.instamart.tests.addons.RetailRocket;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.lib.Addresses;
import ru.instamart.application.lib.Widgets;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.tests.TestBase;

import static ru.instamart.application.Config.TestsConfiguration.AddonsTests.enableRetailRocketTest;

public class RetailRocketItemCardWidgetsTests extends TestBase {

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
            description = "Тест наличия виджета 'С этим товаром покупают' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 12201
    )
    public void successCheckWithThisItemBuyWidget() {
        Shop.Catalog.Item.open();

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.ItemCard.WithThisItemBuy()),
                    "Нет блока 'C этим товаром покупают' в картчоке товара");
    }

    @Test (enabled = enableRetailRocketTest,
            description = "Тест наличия виджета 'Похожие товары' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 12202
    )
    public void successCheckSimilarItemsWidget() {
        Shop.Catalog.Item.open();

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.ItemCard.SimilarItems()),
                    "Нет блока 'Похожие товары' в картчоке товара");
    }

    @Test (enabled = enableRetailRocketTest,
            description = "Тест наличия виджета 'Вы недавно смотрели' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 12203
    )
    public void successCheckRecentlyViewedWidget() {
        Shop.Catalog.Item.open();

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.ItemCard.RecentlyViewed()),
                    "Нет блока 'Вы недавно смотрели' в карточке товара");
    }

    @Test (enabled = enableRetailRocketTest,
            description = "Тест открытия карточки товара из виджета 'C этим товаром покупают' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 12204,
            dependsOnMethods = "successCheckWithThisItemBuyWidget"
    )
    public void successOpenItemFromWithThisItemBuyWidget() {
        Shop.Catalog.Item.open();

        Shop.Catalog.Item.open(Widgets.RetailRocket.ItemCard.WithThisItemBuy());
        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'С этим товаром покупают' в карточке товара");
    }

    @Test (enabled = enableRetailRocketTest,
            description = "Тест открытия карточки товара из виджета 'Похожие товары' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 12205,
            dependsOnMethods = "successCheckSimilarItemsWidget"
    )
    public void successOpenItemFromSimilarItemsWidget() {
        Shop.Catalog.Item.open();

        Shop.Catalog.Item.open(Widgets.RetailRocket.ItemCard.SimilarItems());
        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Похожие товары' в карточке товара");
    }

    @Test (enabled = enableRetailRocketTest,
            description = "Тест открытия карточки товара из виджета 'Вы недавно смотрели' в картчоке товара",
            groups = {"acceptance", "regression"},
            priority = 12206,
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successOpenItemFromRecentlyViewedWidget() {
        Shop.Catalog.Item.open();

        Shop.Catalog.Item.open(Widgets.RetailRocket.ItemCard.RecentlyViewed());
        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Вы недавно смотрели' в карточке товара");

    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест успешного добавления товара из виджета 'С этим товаром покупают' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 12207,
            dependsOnMethods = "successCheckWithThisItemBuyWidget"
    )
    public void successAddItemFromWithThisItemBuyWidget() {
        Shop.Cart.drop();

        Shop.Catalog.Item.open();
        Shop.Catalog.Item.addToCart(Widgets.RetailRocket.ItemCard.WithThisItemBuy());
        Shop.ItemCard.close();

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'C этим товаром покупают' в карточке товара");
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест успешного добавления товара из виджета 'Похожие товары' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 12208,
            dependsOnMethods = "successCheckSimilarItemsWidget"
    )
    public void successAddItemFromSimilarItemsWidget() {
        Shop.Cart.drop();

        Shop.Catalog.Item.open();
        Shop.Catalog.Item.addToCart(Widgets.RetailRocket.ItemCard.SimilarItems());
        Shop.ItemCard.close();

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'Похожие товары' в карточке товара");
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест успешного добавления товара из виджета 'Вы недавно смотрели' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 12209,
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successAddItemFromRecentlyViewedWidget() {
        Shop.Cart.drop();

        Shop.Catalog.Item.open();
        Shop.Catalog.Item.addToCart(Widgets.RetailRocket.ItemCard.RecentlyViewed());
        Shop.ItemCard.close();

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'Вы недавно смотрели' в карточке товара");
    }
}
