package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Widgets;

public class RetailRocketItemCard extends TestBase {

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

    @Test (
            description = "Тест наличия виджета 'С этим товаром покупают' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 1963
    )
    public void successCheckWithThisItemBuyWidget() {
        kraken.shopping().openFirstItemCard();

        Assert.assertTrue(kraken.detect().isWidgetPresent(Widgets.RetailRocket.ItemCard.WithThisItemBuy()),
                "Нет блока 'C этим товаром покупают' в картчоке товара");

    }

    @Test (
            description = "Тест наличия виджета 'Похожие товары' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 1964
    )
    public void successCheckSimilarItemsWidget() {
        kraken.shopping().openFirstItemCard();

        Assert.assertTrue(kraken.detect().isWidgetPresent(Widgets.RetailRocket.ItemCard.SimilarItems()),
                "Нет блока 'Похожие товары' в картчоке товара");

    }

    @Test (
            description = "Тест наличия виджета 'Вы недавно смотрели' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 1965
    )
    public void successCheckRecentlyViewedWidget() {
        kraken.shopping().openFirstItemCard();

        Assert.assertTrue(kraken.detect().isWidgetPresent(Widgets.RetailRocket.ItemCard.RecentlyViewed()),
                "Нет блока 'Вы недавно смотрели' в карточке товара");

    }

    @Test (
            description = "Тест открытия карточки товара из виджета 'C этим товаром покупают' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 1966,
            dependsOnMethods = "successCheckWithThisItemBuyWidget"
    )
    public void successOpenItemFromWithThisItemBuyWidget() {
        kraken.shopping().openFirstItemCard();

        kraken.shopping().openItemCard(Widgets.RetailRocket.ItemCard.WithThisItemBuy());
        Assert.assertTrue(kraken.detect().isItemCardOpen(),
                "Не открывается карточка товара из виджета 'С этим товаром покупают' в карточке товара");
    }

    @Test (
            description = "Тест открытия карточки товара из виджета 'Похожие товары' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 1967,
            dependsOnMethods = "successCheckSimilarItemsWidget"
    )
    public void successOpenItemFromSimilarItemsWidget() {
        kraken.shopping().openFirstItemCard();

        kraken.shopping().openItemCard(Widgets.RetailRocket.ItemCard.SimilarItems());
        Assert.assertTrue(kraken.detect().isItemCardOpen(),
                "Не открывается карточка товара из виджета 'Похожие товары' в карточке товара");
    }

    @Test (
            description = "Тест открытия карточки товара из виджета 'Вы недавно смотрели' в картчоке товара",
            groups = {"acceptance", "regression"},
            priority = 1968,
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successOpenItemFromRecentlyViewedWidget() {
        kraken.shopping().openFirstItemCard();

        kraken.shopping().openItemCard(Widgets.RetailRocket.ItemCard.RecentlyViewed());
        Assert.assertTrue(kraken.detect().isItemCardOpen(),
                "Не открывается карточка товара из виджета 'Вы недавно смотрели' в карточке товара");

    }

    @Test(
            description = "Тест успешного добавления товара из виджета 'С этим товаром покупают' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 1969,
            dependsOnMethods = "successCheckWithThisItemBuyWidget"
    )
    public void successAddItemFromWithThisItemBuyWidget() {
        kraken.drop().cart();

        kraken.shopping().openFirstItemCard();
        kraken.shopping().addItem(Widgets.RetailRocket.ItemCard.WithThisItemBuy());
        kraken.shopping().closeItemCard();

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не добавляется в корзину товар из виджета 'C этим товаром покупают' в карточке товара");
    }

    @Test(
            description = "Тест успешного добавления товара из виджета 'Похожие товары' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 1970,
            dependsOnMethods = "successCheckSimilarItemsWidget"
    )
    public void successAddItemFromSimilarItemsWidget() {
        kraken.drop().cart();

        kraken.shopping().openFirstItemCard();
        kraken.shopping().addItem(Widgets.RetailRocket.ItemCard.SimilarItems());
        kraken.shopping().closeItemCard();

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не добавляется в корзину товар из виджета 'Похожие товары' в карточке товара");

    }

    @Test(
            description = "Тест успешного добавления товара из виджета 'Вы недавно смотрели' в карточке товара",
            groups = {"acceptance", "regression"},
            priority = 1971,
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successAddItemFromRecentlyViewedWidget() {
        kraken.drop().cart();

        kraken.shopping().openFirstItemCard();
        kraken.shopping().addItem(Widgets.RetailRocket.ItemCard.RecentlyViewed());
        kraken.shopping().closeItemCard();

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не добавляется в корзину товар из виджета 'Вы недавно смотрели' в карточке товара");

    }

}
