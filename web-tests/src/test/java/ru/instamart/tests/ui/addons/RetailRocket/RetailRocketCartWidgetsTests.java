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
import ru.instamart.ui.modules.shop.ShippingAddressModal;

public class RetailRocketCartWidgetsTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
        kraken.get().page(Config.DEFAULT_RETAILER);
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.submit();
    }

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловия для теста")
    public void beforeTest() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.Catalog.Item.addToCart();
        Shop.Cart.open();
    }


    @Test(  description = "Тест наличия виджета 'Не забудьте купить' в корзине",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successCheckDontForgetToBuyWidget() {
        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.Cart.DontForgetToBuy()),
                    "Нет блока 'Не забудьте купить' в картчоке товара");
    }

    @Test(  description = "Тест успешного открытия карточки из виджета 'Не забудьте купить' в корзине",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},

            dependsOnMethods = "successCheckDontForgetToBuyWidget"
    )
    public void successOpenItemCardFromDontForgetToBuyWidget() {
        Shop.Catalog.Item.open(Widgets.RetailRocket.Cart.DontForgetToBuy());

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.Cart.DontForgetToBuy()),
                    "Не открывается карточка товара из блока 'Не забудьте купить' в коризне");
    }

    @Test(  description = "Тест успешного добавления товара в корзину из виджета ' Не забудьте купить' в корзине",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},

            dependsOnMethods = "successCheckDontForgetToBuyWidget"
    )
    public void successAddItemFromDontForgetToBuyWidget() {
        Shop.Catalog.Item.addToCart(Widgets.RetailRocket.Cart.DontForgetToBuy());
        Shop.Cart.Item.remove();

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.Cart.DontForgetToBuy()),
                    "Не добавляется товар в корзину из блока 'Не забудьте купить' в корзине");
    }
}
