package ru.instamart.autotests.tests.addons.RetailRocket;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Widgets;
import ru.instamart.autotests.appmanager.ShopHelper;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.application.Config.TestsConfiguration.AddonsTests.enableRetailRocketTest;

public class RetailRocketCartWidgetsTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.perform().quickLogout();
        kraken.get().page("metro");
        ShopHelper.ShippingAddress.set(Addresses.Moscow.defaultAddress());
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().page("metro");
        ShopHelper.Catalog.Item.addToCart();
        ShopHelper.Cart.open();
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест наличия виджета 'Не забудьте купить' в корзине",
            groups = {"acceptance", "regression"},
            priority = 12401
    )
    public void successCheckDontForgetToBuyWidget() {
        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.Cart.DontForgetToBuy()),
                    "Нет блока 'Не забудьте купить' в картчоке товара");
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест успешного открытия карточки из виджета 'Не забудьте купить' в корзине",
            groups = {"acceptance", "regression"},
            priority = 12402,
            dependsOnMethods = "successCheckDontForgetToBuyWidget"
    )
    public void successOpenItemCardFromDontForgetToBuyWidget() {
        ShopHelper.Catalog.Item.open(Widgets.RetailRocket.Cart.DontForgetToBuy());

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.Cart.DontForgetToBuy()),
                    "Не открывается карточка товара из блока 'Не забудьте купить' в коризне");
    }

    @Test (enabled = enableRetailRocketTest,
            description = "Тест успешного добавления товара в корзину из виджета ' Не забудьте купить' в корзине",
            groups = {"acceptance", "regression"},
            priority = 12403,
            dependsOnMethods = "successCheckDontForgetToBuyWidget"
    )
    public void successAddItemFromDontForgetToBuyWidget() {
        ShopHelper.Catalog.Item.addToCart(Widgets.RetailRocket.Cart.DontForgetToBuy());
        ShopHelper.Cart.Item.remove();

        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.Cart.DontForgetToBuy()),
                    "Не добавляется товар в корзину из блока 'Не забудьте купить' в корзине");
    }
}
