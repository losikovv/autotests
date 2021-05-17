package ru.instamart.ui.addons.RetailRocket;

import ru.instamart.core.testdata.lib.Addresses;
import ru.instamart.core.testdata.lib.Widgets;
import ru.instamart.ui.module.Shop;
import ru.instamart.ui.module.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.ui.TestBase;
import ru.instamart.ui.module.shop.ShippingAddressModal;

public class RetailRocketCatalogWidgetsTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
        kraken.get().page("metro/ovoshchi-i-frukty");
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.submit();
    }

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        kraken.get().page("metro/ovoshchi-i-frukty");
    }


    @Test ( description = "Тест наличия виджета 'Выбор покупателей' в каталоге",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successCheckCustomersChoiceWidget() {
        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.CatalogPage.CustomersChoice()),
                    "Нет блока 'Выбор покупателей' в каталоге");
    }

    @Test ( description = "Тест наличия виджета 'Вы недавно смотрели' в каталоге",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successCheckRecentlyViewedWidget() {
        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.CatalogPage.RecentlyViewed()),
                    "Нет блока 'Вы недавно смотрели' в каталоге");
    }

    @Test ( description = "Тест успешного открытия карточки из виджета 'Выбор покупателей' в каталоге",
            groups = {"sbermarket-acceptance","sbermarket-regression"},

            dependsOnMethods = "successCheckCustomersChoiceWidget"
    )
    public void successOpenItemFromCustomersChoiceWidget() {
        Shop.Catalog.Item.open(Widgets.RetailRocket.CatalogPage.CustomersChoice());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Выбор покупателей' в каталоге");
    }

    @Test ( description = "Тест успешного открытия карточки из виджета 'Вы недавно смотрели' в каталоге",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},

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

    @Test ( description = "Тест успешного добавления товара из виджета 'Выбор покупателей' в каталоге",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},

            dependsOnMethods = "successCheckCustomersChoiceWidget"
    )
    public void successAddItemFromCustomersChoiceWidget() {
        Shop.Cart.drop();

        Shop.Catalog.Item.addToCart(Widgets.RetailRocket.CatalogPage.CustomersChoice());

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'Выбор покупателей' в каталоге");
    }

    @Test ( description = "Тест успешного добавления товара из виджета 'Вы неавдно смотрели' в каталоге",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},

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
