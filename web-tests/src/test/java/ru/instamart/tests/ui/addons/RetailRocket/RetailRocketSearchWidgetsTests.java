package ru.instamart.tests.ui.addons.RetailRocket;

import ru.instamart.core.setting.Config;
import ru.instamart.core.testdata.TestVariables;
import ru.instamart.ui.common.lib.Addresses;
import ru.instamart.ui.common.lib.Widgets;
import ru.instamart.ui.module.Shop;
import ru.instamart.ui.module.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;
import ru.instamart.ui.module.shop.ShippingAddressModal;

public class RetailRocketSearchWidgetsTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
        kraken.get().page(Config.DEFAULT_RETAILER);
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.submit();
    }

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        kraken.get().page(Config.DEFAULT_RETAILER);
    }

    @Test(  description = "Тест наличия виджета 'Мы нашли для вас похожие товары' после поиска без результата",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successCheckSimilarItemsWidget() {
        Shop.Search.searchItem(TestVariables.TestParams.ItemSearch.emptyResultsQuery);

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.Search.SimilarItems()),
                    "Нет блока 'Мы нашли для вас похожие товары' после поиска без результата");
    }

    @Test(  description = "Тест наличия виджета 'Те, кто ищут выбирают' после поиска товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successCheckFindersChoiceWidget() {
        Shop.Search.searchItem("макароны");

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.Search.FindersChoice()),
                    "Нет блока 'Те, кто ищут выбирают' после поиска товара");
    }

    @Test(  description = "Тест наличия виджета 'Вы недавно смотрели' после поиска товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successCheckRecentlyViewedWidget() {
        Shop.Search.searchItem("макароны");

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.Search.RecentlyViewed()),
                    "Нет блока 'Вы недавно смотрели' после поиска товара");
    }

    @Test(  description = "Тест открытия карточки товара из виджета 'Мы нашли для вас похожие товары' после поиска без результата",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},

            dependsOnMethods = "successCheckSimilarItemsWidget"
    )
    public void successOpenItemCardFromSimilarItemsWidget() {
        Shop.Search.searchItem(TestVariables.TestParams.ItemSearch.emptyResultsQuery);

        Shop.Catalog.Item.open(Widgets.RetailRocket.Search.SimilarItems());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Мы нашли для вас похожие товары' после поиска без результатов");
    }

    @Test(  description = "Тест открытия карточки товара из виджета 'Те, кто ищут выбирают' после поиска товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},

            dependsOnMethods = "successCheckFindersChoiceWidget"
    )
    public void successOpenItemCardFromFindersChoiceWidget() {
        Shop.Search.searchItem("жир");

        Shop.Catalog.Item.open(Widgets.RetailRocket.Search.FindersChoice());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Те кто ищут выбирают' после поиска товара");
    }

    @Test(  description = "Тест открытия карточки товара из виджета 'Вы недавно смотрели' после поиска товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},

            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successOpenItemCardFromRecentlyViewedWidget() {
        Shop.Cart.drop();
        Shop.Catalog.Item.open();
        Shop.ItemCard.close();
        kraken.perform().refresh();

        Shop.Search.searchItem("жир");

        Shop.Catalog.Item.open(Widgets.RetailRocket.Search.RecentlyViewed());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Вы недавно смотрели' после поиска товара");
    }

    // TODO не работает, починить - ATST-231
    @Test(  description = "Тест успешного добавления товара в корзину из виджета 'Мы нашли для вас похожие товары' после поиска без результата",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},

            dependsOnMethods = "successCheckSimilarItemsWidget"
    )
    public void successAddItemFromSimilarItemsWidget() {
        Shop.Search.searchItem(TestVariables.TestParams.ItemSearch.emptyResultsQuery);

        Shop.Catalog.Item.addToCart(Widgets.RetailRocket.Search.SimilarItems());

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'Мы нашли для вас похожие товары' после поиска без результата");
    }

    @Test(  description = "Тест успешного добавления товара в корзину из виджета 'Те, кто ищут выбирают' после поиска товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},

            dependsOnMethods = "successCheckFindersChoiceWidget"
    )
    public void successAddItemFromFindersChoiceWidget() {
        Shop.Search.searchItem("жир");
        Shop.Catalog.Item.addToCart(Widgets.RetailRocket.Search.FindersChoice());

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'Те, кто ищут выбирают' после поиска товара");
    }

    @Test(  description = "Тест успешного добавления товара в корзину из виджета 'Вы недавно смотрели' после поиска товара",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},

            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successAddItemFromRecentlyViewedWidget() {
        Shop.Cart.drop();
        Shop.Catalog.Item.open();
        Shop.ItemCard.close();
        kraken.perform().refresh();

        Shop.Search.searchItem("жир");
        Shop.Catalog.Item.addToCart(Widgets.RetailRocket.Search.RecentlyViewed());

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'Вы недавно смотрели' после поиска товара");
    }
}
