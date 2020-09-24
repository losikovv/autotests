package ru.instamart.tests.addons.RetailRocket;

import instamart.core.settings.Config;
import instamart.ui.common.lib.Addresses;
import instamart.ui.common.lib.Widgets;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.TestBase;

import static instamart.core.settings.Config.TestsConfiguration.AddonsTests.enableRetailRocketTest;

public class RetailRocketCartWidgetsTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        Shop.Catalog.Item.addToCart();
        Shop.Cart.open();
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест наличия виджета 'Не забудьте купить' в корзине",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            priority = 12401
    )
    public void successCheckDontForgetToBuyWidget() {
        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.Cart.DontForgetToBuy()),
                    "Нет блока 'Не забудьте купить' в картчоке товара");
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест успешного открытия карточки из виджета 'Не забудьте купить' в корзине",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            priority = 12402,
            dependsOnMethods = "successCheckDontForgetToBuyWidget"
    )
    public void successOpenItemCardFromDontForgetToBuyWidget() {
        Shop.Catalog.Item.open(Widgets.RetailRocket.Cart.DontForgetToBuy());

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.Cart.DontForgetToBuy()),
                    "Не открывается карточка товара из блока 'Не забудьте купить' в коризне");
    }

    @Test (enabled = enableRetailRocketTest,
            description = "Тест успешного добавления товара в корзину из виджета ' Не забудьте купить' в корзине",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            priority = 12403,
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
