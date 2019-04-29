package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Widgets;

import static ru.instamart.autotests.application.Config.enableRetailRocketTests;

public class RetailRocket_Search extends TestBase {

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

    @Test(enabled = enableRetailRocketTests,
            description = "Тест наличия виджета 'Мы нашли для вас похожие товары' после поиска без результата",
            groups = {"acceptance", "regression"},
            priority = 11301
    )
    public void successCheckSimilarItemsWidget() {
        kraken.search().item("смысл жизни");

        Assert.assertTrue(kraken.detect().isWidgetPresent(Widgets.RetailRocket.Search.SimilarItems()),
                "Нет блока 'Мы нашли для вас похожие товары' после поиска без результата");
    }

    @Test(enabled = enableRetailRocketTests,
            description = "Тест наличия виджета 'Те, кто ищут выбирают' после поиска товара",
            groups = {"acceptance", "regression"},
            priority = 11302
    )
    public void successCheckFindersChoiceWidget() {
        kraken.search().item("макароны");

        Assert.assertTrue(kraken.detect().isWidgetPresent(Widgets.RetailRocket.Search.FindersChoice()),
                "Нет блока 'Те, кто ищут выбирают' после поиска товара");
    }

    @Test(enabled = enableRetailRocketTests,
            description = "Тест наличия виджета 'Вы недавно смотрели' после поиска товара",
            groups = {"acceptance", "regression"},
            priority = 11303
    )
    public void successCheckRecentlyViewedWidget() {
        kraken.search().item("макароны");

        Assert.assertTrue(kraken.detect().isWidgetPresent(Widgets.RetailRocket.Search.RecentlyViewed()),
                "Нет блока 'Вы недавно смотрели' после поиска товара");
    }

    @Test(enabled = enableRetailRocketTests,
            description = "Тест открытия карточки товара из виджета 'Мы нашли для вас похожие товары' после поиска без результата",
            groups = {"acceptance", "regression"},
            priority = 11304,
            dependsOnMethods = "successCheckSimilarItemsWidget"
    )
    public void successOpenItemCardFromSimilarItemsWidget() {
        kraken.search().item("смысл жизни");

        kraken.shopping().openItemCard(Widgets.RetailRocket.Search.SimilarItems());

        Assert.assertTrue(kraken.detect().isItemCardOpen(),
                "Не открывается карточка товара из виджета 'Мы нашли для вас похожие товары' после поиска без результатов");
    }

    @Test(enabled = enableRetailRocketTests,
            description = "Тест открытия карточки товара из виджета 'Те, кто ищут выбирают' после поиска товара",
            groups = {"acceptance", "regression"},
            priority = 11305,
            dependsOnMethods = "successCheckFindersChoiceWidget"
    )
    public void successOpenItemCardFromFindersChoiceWidget() {
        kraken.search().item("жир");

        kraken.shopping().openItemCard(Widgets.RetailRocket.Search.FindersChoice());

        Assert.assertTrue(kraken.detect().isItemCardOpen(),
                "Не открывается карточка товара из виджета 'Те кто ищут выбирают' после поиска товара");
    }

    @Test(enabled = enableRetailRocketTests,
            description = "Тест открытия карточки товара из виджета 'Вы недавно смотрели' после поиска товара",
            groups = {"acceptance", "regression"},
            priority = 11306,
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successOpenItemCardFromRecentlyViewedWidget() {
        kraken.drop().cart();
        kraken.shopping().openFirstItemCard();
        kraken.shopping().closeItemCard();
        kraken.perform().refresh();

        kraken.search().item("жир");

        kraken.shopping().openItemCard(Widgets.RetailRocket.Search.RecentlyViewed());

        Assert.assertTrue(kraken.detect().isItemCardOpen(),
                "Не открывается карточка товара из виджета 'Вы недавно смотрели' после поиска товара");
    }

    @Test(enabled = enableRetailRocketTests,
            description = "Тест успешного добавления товара в корзину из виджета 'Мы нашли для вас похожие товары' после поиска без результата",
            groups = {"acceptance", "regression"},
            priority = 11307,
            dependsOnMethods = "successCheckSimilarItemsWidget"
    )
    //=======Не работает ========
    public void successAddItemFromSimilarItemsWidget() {
        kraken.search().item("смысл жизни");

        kraken.shopping().addItem(Widgets.RetailRocket.Search.SimilarItems());

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не добавляется в корзину товар из виджета 'Мы нашли для вас похожие товары' после поиска без результата");
    }

    @Test(enabled = enableRetailRocketTests,
            description = "Тест успешного добавления товара в корзину из виджета 'Те, кто ищут выбирают' после поиска товара",
            groups = {"acceptance", "regression"},
            priority = 11308,
            dependsOnMethods = "successCheckFindersChoiceWidget"
    )
    public void successAddItemFromFindersChoiceWidget() {
        kraken.search().item("жир");
        kraken.shopping().addItem(Widgets.RetailRocket.Search.FindersChoice());

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не добавляется в корзину товар из виджета 'Те, кто ищут выбирают' после поиска товара");
    }

    @Test(enabled = enableRetailRocketTests,
            description = "Тест успешного добавления товара в корзину из виджета 'Вы недавно смотрели' после поиска товара",
            groups = {"acceptance", "regression"},
            priority = 11309,
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successAddItemFromRecentlyViewedWidget() {
        kraken.drop().cart();
        kraken.shopping().openFirstItemCard();
        kraken.shopping().closeItemCard();
        kraken.perform().refresh();

        kraken.search().item("жир");

        kraken.shopping().addItem(Widgets.RetailRocket.Search.RecentlyViewed());

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не добавляется в корзину товар из виджета 'Вы недавно смотрели' после поиска товара");
    }
}
