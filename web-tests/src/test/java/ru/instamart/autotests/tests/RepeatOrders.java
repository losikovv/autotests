package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import ru.instamart.autotests.application.*;
import ru.instamart.autotests.appmanager.ShopHelper;

import static ru.instamart.autotests.application.Config.testRepeatOrders;


// Тесты повтора заказов


public class RepeatOrders extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() throws Exception {
        kraken.get().page("metro");
        kraken.perform().loginAs(kraken.session.admin);
        kraken.shipAddress().change(Addresses.Moscow.testAddress());
        kraken.drop().cart();
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.perform().repeatLastOrder();
    }

    @Test(enabled = testRepeatOrders,
            description = "Повтор крайнего заказа и оплата картой онлайн",
            groups = {"acceptance","regression"},
            priority = 1001
    )
    public void successRepeatLastOrderAndPayWithCardOnline() {
        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();

        kraken.checkout().complete(PaymentTypes.cardOnline());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется повторный заказ с оплатой картой онлайн\n");
    }

    @Test(enabled = testRepeatOrders,
            description = "Повтор крайнего заказа и оплата картой курьеру",
            groups = {"acceptance","regression"},
            priority = 1002
    )
    public void successRepeatLastOrderAndPayWithCardCourier() {
        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();

        kraken.checkout().complete(PaymentTypes.cardCourier());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется повторный заказ с оплатой картой курьеру\n");
    }

    @Test(enabled = testRepeatOrders,
            description = "Повтор крайнего заказа и оплата наличными",
            groups = {"acceptance","regression"},
            priority = 1003
    )
    public void successRepeatLastOrderAndPayWithCash() {
        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();

        kraken.checkout().complete(PaymentTypes.cash());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется повторный заказ с оплатой наличными\n");
    }

    @Test(enabled = testRepeatOrders,
            description = "Повтор крайнего заказа и оплата банковским переводом",
            groups = {"acceptance","regression"},
            priority = 1004
    )
    public void successRepeatLastOrderAndPayWithBank() {
        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();

        kraken.checkout().complete(PaymentTypes.bankTransfer());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется повторный заказ с оплатой банковским переводом\n");
    }

    @AfterMethod(alwaysRun = true)
    public void cancelLastOrder() {
        kraken.perform().cancelLastOrder();
    }
}
