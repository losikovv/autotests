package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Widgets;
import ru.instamart.autotests.appmanager.ShopHelper;

import static ru.instamart.autotests.application.Config.enableRetailRocketTests;

public class RetailRocket_ItemCard extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.perform().quickLogout();
        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().page("metro");
    }

    @Test ( enabled = enableRetailRocketTests,
            description = "Тест наличия виджета 'С этим товаром покупают' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 11201
    )
    public void successCheckWithThisItemBuyWidget() {
        ShopHelper.Catalog.Item.open();

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.ItemCard.WithThisItemBuy()),
                    "Нет блока 'C этим товаром покупают' в картчоке товара");
    }

    @Test (enabled = enableRetailRocketTests,
            description = "Тест наличия виджета 'Похожие товары' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 11202
    )
    public void successCheckSimilarItemsWidget() {
        ShopHelper.Catalog.Item.open();

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.ItemCard.SimilarItems()),
                    "Нет блока 'Похожие товары' в картчоке товара");
    }

    @Test (enabled = enableRetailRocketTests,
            description = "Тест наличия виджета 'Вы недавно смотрели' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 11203
    )
    public void successCheckRecentlyViewedWidget() {
        ShopHelper.Catalog.Item.open();

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.ItemCard.RecentlyViewed()),
                    "Нет блока 'Вы недавно смотрели' в карточке товара");
    }

    @Test (enabled = enableRetailRocketTests,
            description = "Тест открытия карточки товара из виджета 'C этим товаром покупают' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 11204,
            dependsOnMethods = "successCheckWithThisItemBuyWidget"
    )
    public void successOpenItemFromWithThisItemBuyWidget() {
        ShopHelper.Catalog.Item.open();

        ShopHelper.Catalog.Item.open(Widgets.RetailRocket.ItemCard.WithThisItemBuy());
        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'С этим товаром покупают' в карточке товара");
    }

    @Test (enabled = enableRetailRocketTests,
            description = "Тест открытия карточки товара из виджета 'Похожие товары' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 11205,
            dependsOnMethods = "successCheckSimilarItemsWidget"
    )
    public void successOpenItemFromSimilarItemsWidget() {
        ShopHelper.Catalog.Item.open();

        ShopHelper.Catalog.Item.open(Widgets.RetailRocket.ItemCard.SimilarItems());
        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Похожие товары' в карточке товара");
    }

    @Test (enabled = enableRetailRocketTests,
            description = "Тест открытия карточки товара из виджета 'Вы недавно смотрели' в картчоке товара",
            groups = {"acceptance", "regression"},
            priority = 11206,
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successOpenItemFromRecentlyViewedWidget() {
        ShopHelper.Catalog.Item.open();

        ShopHelper.Catalog.Item.open(Widgets.RetailRocket.ItemCard.RecentlyViewed());
        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Вы недавно смотрели' в карточке товара");

    }

    @Test(enabled = enableRetailRocketTests,
            description = "Тест успешного добавления товара из виджета 'С этим товаром покупают' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 11207,
            dependsOnMethods = "successCheckWithThisItemBuyWidget"
    )
    public void successAddItemFromWithThisItemBuyWidget() {
        kraken.drop().cart();

        ShopHelper.Catalog.Item.open();
        ShopHelper.Catalog.Item.addToCart(Widgets.RetailRocket.ItemCard.WithThisItemBuy());
        ShopHelper.ItemCard.close();

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'C этим товаром покупают' в карточке товара");
    }

    @Test(enabled = enableRetailRocketTests,
            description = "Тест успешного добавления товара из виджета 'Похожие товары' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 11208,
            dependsOnMethods = "successCheckSimilarItemsWidget"
    )
    public void successAddItemFromSimilarItemsWidget() {
        kraken.drop().cart();

        ShopHelper.Catalog.Item.open();
        ShopHelper.Catalog.Item.addToCart(Widgets.RetailRocket.ItemCard.SimilarItems());
        ShopHelper.ItemCard.close();

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'Похожие товары' в карточке товара");
    }

    @Test(enabled = enableRetailRocketTests,
            description = "Тест успешного добавления товара из виджета 'Вы недавно смотрели' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 11209,
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successAddItemFromRecentlyViewedWidget() {
        kraken.drop().cart();

        ShopHelper.Catalog.Item.open();
        ShopHelper.Catalog.Item.addToCart(Widgets.RetailRocket.ItemCard.RecentlyViewed());
        ShopHelper.ItemCard.close();

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'Вы недавно смотрели' в карточке товара");
    }
}
