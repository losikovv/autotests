package ru.instamart.tests.ui.addons.RetailRocket;

import ru.instamart.core.settings.Config;
import ru.instamart.ui.common.lib.Addresses;
import ru.instamart.ui.common.lib.Widgets;
import ru.instamart.ui.modules.Shop;
import ru.instamart.ui.modules.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

public class RetailRocketItemCardWidgetsTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
    }

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        kraken.get().page(Config.DEFAULT_RETAILER);
    }


    @Test ( description = "Тест наличия виджета 'С этим товаром покупают' в карточке товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successCheckWithThisItemBuyWidget() {
        Shop.Catalog.Item.open();

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.ItemCard.WithThisItemBuy()),
                    "Нет блока 'C этим товаром покупают' в картчоке товара");
    }

    @Test(  description = "Тест наличия виджета 'Похожие товары' в карточке товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successCheckSimilarItemsWidget() {
        Shop.Catalog.Item.open();

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.ItemCard.SimilarItems()),
                    "Нет блока 'Похожие товары' в картчоке товара");
    }

    @Test(  description = "Тест наличия виджета 'Вы недавно смотрели' в карточке товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successCheckRecentlyViewedWidget() {
        Shop.Catalog.Item.open();

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.ItemCard.RecentlyViewed()),
                    "Нет блока 'Вы недавно смотрели' в карточке товара");
    }

    @Test(  description = "Тест открытия карточки товара из виджета 'C этим товаром покупают' в карточке товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},

            dependsOnMethods = "successCheckWithThisItemBuyWidget"
    )
    public void successOpenItemFromWithThisItemBuyWidget() {
        Shop.Catalog.Item.open();

        Shop.Catalog.Item.open(Widgets.RetailRocket.ItemCard.WithThisItemBuy());
        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'С этим товаром покупают' в карточке товара");
    }

    @Test(  description = "Тест открытия карточки товара из виджета 'Похожие товары' в карточке товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},

            dependsOnMethods = "successCheckSimilarItemsWidget"
    )
    public void successOpenItemFromSimilarItemsWidget() {
        Shop.Catalog.Item.open();

        Shop.Catalog.Item.open(Widgets.RetailRocket.ItemCard.SimilarItems());
        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Похожие товары' в карточке товара");
    }

    @Test(  description = "Тест открытия карточки товара из виджета 'Вы недавно смотрели' в картчоке товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},

            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successOpenItemFromRecentlyViewedWidget() {
        Shop.Catalog.Item.open();

        Shop.Catalog.Item.open(Widgets.RetailRocket.ItemCard.RecentlyViewed());
        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Вы недавно смотрели' в карточке товара");

    }

    @Test(  description = "Тест успешного добавления товара из виджета 'С этим товаром покупают' в карточке товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},

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

    @Test(  description = "Тест успешного добавления товара из виджета 'Похожие товары' в карточке товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},

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

    @Test(  description = "Тест успешного добавления товара из виджета 'Вы недавно смотрели' в карточке товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},

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
