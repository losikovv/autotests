package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Widgets;

public class RetailRocketMainPage extends TestBase {

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
            description = "Тест наличия виджета 'Популярные товары' на главной",
            groups = {"acceptance","regression"},
            priority = 11001
    )
    public void successCheckPopularItemsWidget() {

        Assert.assertTrue(kraken.detect().isWidgetPresent(Widgets.RetailRocket.MainPage.PopularItems()),
                "Нет блока 'Популярные товары' на главной");
    }


    @Test (
            description = "Тест наличия виджета 'Вы недавно смотрели' на главной",
            groups = {"acceptance","regression"},
            priority = 11002
    )
    public void successCheckRecentlyViewedWidget() {

        Assert.assertTrue(kraken.detect().isWidgetPresent(Widgets.RetailRocket.MainPage.RecentlyViewed()),
                "Нет блока 'Вы недавно смотрели' на главной");
    }


    @Test (
            description = "Тест успешного открытия карточки товара из виджета 'Популярные товары' на главной",
            groups = {"acceptance","regression"},
            priority = 11003,
            dependsOnMethods = "successCheckPopularItemsWidget"
    )
    public void successOpenItemFromPopularItemsWidget() {

        kraken.shopping().openItemCard(Widgets.RetailRocket.MainPage.PopularItems());

        Assert.assertTrue(kraken.detect().isItemCardOpen(),
                "Не открывается карточка товара из виджета 'Популярные товары' на главной");
    }


    @Test (
            description = "Тест успешного открытия карточки товара из виджета 'Вы недавно смотрели' на главной",
            groups = {"acceptance","regression"},
            priority = 11004,
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successOpenItemFromRecentlyViewedWidget() {

        kraken.shopping().openItemCard(Widgets.RetailRocket.MainPage.PopularItems());

        Assert.assertTrue(kraken.detect().isItemCardOpen(),
                "Не открывается карточка товара из виджета 'Вы недавно смотрели' на главной");
    }


    @Test (
            description = "Тест успешного добавления товара из блока 'Популярные товары' на главной",
            groups = {"acceptance","regression"},
            priority = 11005,
            dependsOnMethods = "successCheckPopularItemsWidget"
    )
    public void successAddItemFromPopularItemsWidget() {
        kraken.drop().cart();

        kraken.shopping().addItem(Widgets.RetailRocket.MainPage.PopularItems());

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не добавляется в корзину товар из виджета 'Популярные товары' на главной");
    }


    @Test (
            description = "Тест успешного добавления товара из блока 'Вы недавно смотрели' на главной",
            groups = {"acceptance","regression"},
            priority = 11006,
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successAddItemFromRecentlyViewedWidget() {
        kraken.drop().cart();

        kraken.shopping().addItem(Widgets.RetailRocket.MainPage.RecentlyViewed());

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не добавляется в корзину товар из виджета 'Вы недавно смотрели' на главной");
    }
}
