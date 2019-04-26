package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Widgets;



public class RetailRocketCart extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.perform().quickLogout();
        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().page("metro");
        kraken.shopping().addFirstItemOnPageToCart();
        kraken.shopping().openCart();
    }

    @Test(
            description = "Тест наличия виджета 'Не забудьте купить' в корзине",
            groups = {"acceptance", "regression"},
            priority = 1981

    )
    public void successCheckDontForgetToBuyWidget() {
        Assert.assertTrue(kraken.detect().isWidgetPresent(Widgets.RetailRocket.Cart.DontForgetToBuy()),
                "Нет блока 'Не забудьте купить' в картчоке товара");
    }

    @Test(
            description = "Тест успешного открытия карточки из виджета 'Не забудьте купить' в корзине",
            groups = {"acceptance", "regression"},
            priority = 1982,
            dependsOnMethods = "successCheckDontForgetToBuyWidget"
    )
    public void successOpenItemCardFromDontForgetToBuyWidget() {
        kraken.shopping().openItemCard(Widgets.RetailRocket.Cart.DontForgetToBuy());

        Assert.assertTrue(kraken.detect().isWidgetPresent(Widgets.RetailRocket.Cart.DontForgetToBuy()),
                "Не открывается карточка товара из блока 'Не забудьте купить' в коризне");

    }

    @Test (
            description = "Тест успешного добавления товара в корзину из виджета ' Не забудьте купить' в корзине",
            groups = {"acceptance", "regression"},
            priority = 1983,
            dependsOnMethods = "successCheckDontForgetToBuyWidget"

    )
    public void successAddItemFromDontForgetToBuyWidget() {
        kraken.shopping().addItem(Widgets.RetailRocket.Cart.DontForgetToBuy());
        kraken.shopping().removeItemFromCart();

        Assert.assertTrue(kraken.detect().isWidgetPresent(Widgets.RetailRocket.Cart.DontForgetToBuy()),
                "Не добавляется товар в корзину из блока 'Не забудьте купить' в корзине");

    }
}
