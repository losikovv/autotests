package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Widgets;

public class RetailRocketCatalogPage extends TestBase {

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

    @Test (
            description = "Тест наличия виджета 'Выбор покупателей' в каталоге",
            groups = {"acceptance", "regression"},
            priority = 1957
    )
    public void successCheckCustomersChoiceWidget() {
        Assert.assertTrue(kraken.detect().isWidgetPresent(Widgets.RetailRocket.CatalogPage.CustomersChoice()),
                "Нет блока 'Выбор покупателей' в каталоге");
    }

    @Test (
            description = "Тест наличия виджета 'Вы недавно смотрели' в каталоге",
            groups = {"acceptance", "regression"},
            priority = 1958
    )
    public void successCheckRecentlyViewedWidget() {
        Assert.assertTrue(kraken.detect().isWidgetPresent(Widgets.RetailRocket.CatalogPage.RecentlyViewed()),
                "Нет блока 'Вы недавно смотрели' в каталоге");
    }

    @Test (
            description = "Тест успешного открытия карточки из виджета 'Выбор покупателей' в каталоге",
            groups = {"acceptance","regression"},
            priority = 1959,
            dependsOnMethods = "successCheckCustomersChoiceWidget"
    )
    public void successOpenItemFromCustomersChoiceWidget() {
        kraken.shopping().openItemCard(Widgets.RetailRocket.CatalogPage.CustomersChoice());

        Assert.assertTrue(kraken.detect().isItemCardOpen(),
                "Не открывается карточка товара из виджета 'Выбор покупателей' в каталоге");
    }

    @Test (
            description = "Тест успешного открытия карточки из виджета 'Вы недавно смотрели' в каталоге",
            groups = {"acceptance", "regression"},
            priority = 1960,
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successOpenItemFromRecentlyViewedWidget() {
        kraken.shopping().openFirstItemCard();
        kraken.shopping().closeItemCard();
        kraken.perform().refresh();

        kraken.shopping().openItemCard(Widgets.RetailRocket.CatalogPage.RecentlyViewed());

        Assert.assertTrue(kraken.detect().isItemCardOpen(),
                "Не открывается карточка товара из виджета 'Выбор покупателей' в каталоге");
    }

    @Test (
            description = "Тест успешного добавления товара из виджета 'Выбор покупателей' в каталоге",
            groups = {"acceptance", "regression"},
            priority = 1961,
            dependsOnMethods = "successCheckCustomersChoiceWidget"
    )
    public void successAddItemFromCustomersChoiceWidget() {
        kraken.drop().cart();

        kraken.shopping().addItem(Widgets.RetailRocket.CatalogPage.CustomersChoice());

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не добавляется в корзину товар из виджета 'Выбор покупателей' в каталоге");
    }

    @Test (
            description = "Тест успешного добавления товара из виджета 'Вы неавдно смотрели' в каталоге",
            groups = {"acceptance", "regression"},
            priority = 1962,
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successAddItemFromRecentlyViewedWidget() {
        kraken.drop().cart();
        kraken.shopping().openFirstItemCard();
        kraken.shopping().closeItemCard();
        kraken.perform().refresh();

        kraken.shopping().addItem(Widgets.RetailRocket.CatalogPage.RecentlyViewed());

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не добавляется в корзину товар из виджета 'Вы недавно смотрели' в каталоге");
    }
}
