package ru.instamart.tests.ui.addons.RetailRocket;

import ru.instamart.core.setting.Config;
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

public class RetailRocketRetailerPageWidgetsTests extends TestBase {

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

    @Test ( description = "Тест наличия виджета 'Популярные товары' на главной",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successCheckPopularItemsWidget() {

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.MainPage.PopularItems()),
                    "Нет блока 'Популярные товары' на главной");
    }

    @Test ( description = "Тест наличия виджета 'Вы недавно смотрели' на главной",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successCheckRecentlyViewedWidget() {

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.MainPage.RecentlyViewed()),
                    "Нет блока 'Вы недавно смотрели' на главной");
    }

    @Test ( description = "Тест успешного открытия карточки товара из виджета 'Популярные товары' на главной",
            groups = {"sbermarket-acceptance","sbermarket-regression"},

            dependsOnMethods = "successCheckPopularItemsWidget"
    )
    public void successOpenItemFromPopularItemsWidget() {
        Shop.Catalog.Item.open(Widgets.RetailRocket.MainPage.PopularItems());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Популярные товары' на главной");
    }

    @Test ( description = "Тест успешного открытия карточки товара из виджета 'Вы недавно смотрели' на главной",
            groups = {"sbermarket-acceptance","sbermarket-regression"},

            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successOpenItemFromRecentlyViewedWidget() {
        Shop.Catalog.Item.open(Widgets.RetailRocket.MainPage.PopularItems());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Вы недавно смотрели' на главной");
    }

    @Test ( description = "Тест успешного добавления товара из блока 'Популярные товары' на главной",
            groups = {"sbermarket-acceptance","sbermarket-regression"},

            dependsOnMethods = "successCheckPopularItemsWidget"
    )
    public void successAddItemFromPopularItemsWidget() {
        Shop.Cart.drop();

        Shop.Catalog.Item.addToCart(Widgets.RetailRocket.MainPage.PopularItems());

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'Популярные товары' на главной");
    }

    @Test ( description = "Тест успешного добавления товара из блока 'Вы недавно смотрели' на главной",
            groups = {"sbermarket-acceptance","sbermarket-regression"},

            dependsOnMethods = "successCheckRecentlyViewedWidget"
    )
    public void successAddItemFromRecentlyViewedWidget() {
        Shop.Cart.drop();

        Shop.Catalog.Item.open();
        Shop.ItemCard.close();
        kraken.perform().refresh();
        Shop.Catalog.Item.addToCart(Widgets.RetailRocket.MainPage.RecentlyViewed());

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется в корзину товар из виджета 'Вы недавно смотрели' на главной");
    }
}
