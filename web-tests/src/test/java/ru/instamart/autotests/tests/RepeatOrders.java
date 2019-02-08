package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import ru.instamart.autotests.application.PaymentTypes;
import ru.instamart.autotests.application.Users;


// Тесты повтора заказов


public class RepeatOrders extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void getAuth()throws Exception {
        kraken.get().page("metro");
        kraken.perform().loginAs(Users.superadmin());
    }


    @Test(
            description = "Повтор крайнего заказа и оплата картой онлайн",
            groups = {"acceptance","regression"},
            priority = 1001
    )
    public void successRepeatLastOrderAndPayWithCardOnline() throws Exception {
        kraken.perform().repeatLastOrder();

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не выполнены предусловия теста: заказ не повторяется, что-то пошло не так\n");

        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();
        kraken.checkout().complete(PaymentTypes.cardOnline());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется повторный заказ с оплатой картой онлайн\n");
    }


    @Test(
            description = "Повтор крайнего заказа и оплата картой курьеру",
            groups = {"acceptance","regression"},
            priority = 1002
    )
    public void successRepeatLastOrderAndPayWithCardCourier() throws Exception {
        kraken.perform().repeatLastOrder();

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не выполнены предусловия теста: заказ не повторяется, что-то пошло не так\n");

        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();
        kraken.checkout().complete(PaymentTypes.cardCourier());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется повторный заказ с оплатой картой курьеру\n");
    }


    @Test(
            description = "Повтор крайнего заказа и оплата наличными",
            groups = {"acceptance","regression"},
            priority = 1003
    )
    public void successRepeatLastOrderAndPayWithCash() throws Exception {
        kraken.perform().repeatLastOrder();

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не выполнены предусловия теста: заказ не повторяется, что-то пошло не так\n");

        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();
        kraken.checkout().complete(PaymentTypes.cash());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется повторный заказ с оплатой наличными\n");
    }


    @Test(
            description = "Повтор крайнего заказа и оплата банковским переводом",
            groups = {"acceptance","regression"},
            priority = 1004
    )
    public void successRepeatLastOrderAndPayWithBank() throws Exception {
        kraken.perform().repeatLastOrder();

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не выполнены предусловия теста: заказ не повторяется, что-то пошло не так\n");

        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();
        kraken.checkout().complete(PaymentTypes.bankTransfer());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется повторный заказ с оплатой банковским переводом\n");
    }


    @AfterMethod(alwaysRun = true)
    public void cancelLastOrder()throws Exception {
        kraken.perform().cancelLastOrder();
    }
}
