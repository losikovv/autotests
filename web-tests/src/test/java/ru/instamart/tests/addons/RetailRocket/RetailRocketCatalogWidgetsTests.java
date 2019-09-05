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

public class RetailRocketCatalogWidgetsTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
        kraken.get().page("metro/ovoshchi-i-frukty");
        Shop.ShippingAddress.set(Addresses.Moscow.defaultAddress());
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().page("metro/ovoshchi-i-frukty");
    }

    @Test ( enabled = enableRetailRocketTest,
            description = "Тест наличия виджета 'Выбор покупателей' в каталоге",
            groups = {"acceptance", "regression"},
            priority = 12101
    )
    public void successCheckCustomersChoiceWidget() {
        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.CatalogPage.CustomersChoice()),
                    "Нет блока 'Выбор покупателей' в каталоге");
    }

    @Test ( enabled = enableRetailRocketTest,
            description = "Тест наличия виджета 'Вы недавно смотрели' в каталоге",
            groups = {"acceptance", "regression"},
            priority = 12102
    )
    public void successCheckRecentlyViewedWidget() {
        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.CatalogPage.RecentlyViewed()),
                    "Нет блока 'Вы недавно смотрели' в каталоге");
    }

    @Test ( enabled = enableRetailRocketTest,
            description = "Тест успешного открытия карточки из виджета 'Выбор покупателей' в каталоге",
            groups = {"acceptance","regression"},
            priority = 12103,
            dependsOnMethods = "successCheckCustomersChoiceWidget"
    )
    public void successOpenItemFromCustomersChoiceWidget() {
        Shop.Catalog.Item.open(Widgets.RetailRocket.CatalogPage.CustomersChoice());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Выбор покупателей' в каталоге");
    }

    @Test ( enabled = enableRetailRocketTest,
            description = "Тест успешного открытия карточки из виджета 'Вы недавно смотрели' в каталоге",
            groups = {"acceptance", "regression"},
            priority = 12104,
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successOpenItemFromRecentlyViewedWidget() {
        Shop.Catalog.Item.open();
        Shop.ItemCard.close();
        kraken.perform().refresh();

        Shop.Catalog.Item.open(Widgets.RetailRocket.CatalogPage.RecentlyViewed());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Выбор покупателей' в каталоге");
    }

    @Test ( enabled = enableRetailRocketTest,
            description = "Тест успешного добавления товара из виджета 'Выбор покупателей' в каталоге",
            groups = {"acceptance", "regression"},
            priority = 12105,
            dependsOnMethods = "successCheckCustomersChoiceWidget"
    )
    public void successAddItemFromCustomersChoiceWidget() {
        Shop.Cart.drop();

        Shop.Catalog.Item.addToCart(Widgets.RetailRocket.CatalogPage.CustomersChoice());

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'Выбор покупателей' в каталоге");
    }

    @Test ( enabled = enableRetailRocketTest,
            description = "Тест успешного добавления товара из виджета 'Вы неавдно смотрели' в каталоге",
            groups = {"acceptance", "regression"},
            priority = 12106,
            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successAddItemFromRecentlyViewedWidget() {
        Shop.Cart.drop();
        Shop.Catalog.Item.open();
        Shop.ItemCard.close();
        kraken.perform().refresh();

        Shop.Catalog.Item.addToCart(Widgets.RetailRocket.CatalogPage.RecentlyViewed());

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'Вы недавно смотрели' в каталоге");
    }
}
