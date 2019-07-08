package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Widgets;
import ru.instamart.autotests.appmanager.ShopHelper;

import static ru.instamart.autotests.application.Config.testRetailRocket;

public class RetailRocket_Catalog extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.perform().quickLogout();
        kraken.get().page("metro/ovoshchi-i-frukty");
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().page("metro/ovoshchi-i-frukty");
    }

    @Test ( enabled = testRetailRocket,
            description = "Тест наличия виджета 'Выбор покупателей' в каталоге",
            groups = {"acceptance", "regression"},
            priority = 11101
    )
    public void successCheckCustomersChoiceWidget() {
        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.CatalogPage.CustomersChoice()),
                    "Нет блока 'Выбор покупателей' в каталоге");
    }

    @Test ( enabled = testRetailRocket,
            description = "Тест наличия виджета 'Вы недавно смотрели' в каталоге",
            groups = {"acceptance", "regression"},
            priority = 11102
    )
    public void successCheckRecentlyViewedWidget() {
        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.CatalogPage.RecentlyViewed()),
                    "Нет блока 'Вы недавно смотрели' в каталоге");
    }

    @Test ( enabled = testRetailRocket,
            description = "Тест успешного открытия карточки из виджета 'Выбор покупателей' в каталоге",
            groups = {"acceptance","regression"},
            priority = 11103,
            dependsOnMethods = "successCheckCustomersChoiceWidget"
    )
    public void successOpenItemFromCustomersChoiceWidget() {
        ShopHelper.Catalog.Item.open(Widgets.RetailRocket.CatalogPage.CustomersChoice());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Выбор покупателей' в каталоге");
    }

    @Test ( enabled = testRetailRocket,
            description = "Тест успешного открытия карточки из виджета 'Вы недавно смотрели' в каталоге",
            groups = {"acceptance", "regression"},
            priority = 11104,
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successOpenItemFromRecentlyViewedWidget() {
        ShopHelper.Catalog.Item.open();
        ShopHelper.ItemCard.close();
        kraken.perform().refresh();

        ShopHelper.Catalog.Item.open(Widgets.RetailRocket.CatalogPage.RecentlyViewed());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Выбор покупателей' в каталоге");
    }

    @Test ( enabled = testRetailRocket,
            description = "Тест успешного добавления товара из виджета 'Выбор покупателей' в каталоге",
            groups = {"acceptance", "regression"},
            priority = 11105,
            dependsOnMethods = "successCheckCustomersChoiceWidget"
    )
    public void successAddItemFromCustomersChoiceWidget() {
        kraken.drop().cart();

        ShopHelper.Catalog.Item.addToCart(Widgets.RetailRocket.CatalogPage.CustomersChoice());

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'Выбор покупателей' в каталоге");
    }

    @Test ( enabled = testRetailRocket,
            description = "Тест успешного добавления товара из виджета 'Вы неавдно смотрели' в каталоге",
            groups = {"acceptance", "regression"},
            priority = 11106,
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successAddItemFromRecentlyViewedWidget() {
        kraken.drop().cart();
        ShopHelper.Catalog.Item.open();
        ShopHelper.ItemCard.close();
        kraken.perform().refresh();

        ShopHelper.Catalog.Item.addToCart(Widgets.RetailRocket.CatalogPage.RecentlyViewed());

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'Вы недавно смотрели' в каталоге");
    }
}
