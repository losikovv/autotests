package ru.instamart.autotests.tests.orders;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.PaymentTypes;
import ru.instamart.autotests.tests.TestBase;

public class OrdersPaymentsTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.get().baseUrl();
        kraken.perform().loginAs(kraken.session.admin);
        kraken.shipAddress().change(Addresses.Moscow.testAddress());
        kraken.drop().cart();
    }


    @Test(
            description = "Тест заказа с оплатой наличными",
            groups = {"acceptance","regression"},
            priority = 2101
    )
    public void successOrderWithCashAndCheckDocuments() {
        kraken.reach().checkout();
        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с оплатой наличными\n");

        Assert.assertTrue(kraken.grab().shipmentPayment().equals(PaymentTypes.cash().getDescription()),
                "Способ оплаты в деталях заказа не совпадает с выбранным во время оформления");

        assertOrderDocumentsAreAvailable();
    }


    @Test(
            description = "Тест заказа с оплатой картой онлайн",
            groups = {"acceptance","regression"},
            priority = 2102
    )
    public void successOrderWithCardOnlineAndCheckDocuments() {
        kraken.reach().checkout();
        kraken.checkout().complete(PaymentTypes.cardOnline());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с оплатой картой онлайн\n");

        Assert.assertTrue(kraken.grab().shipmentPayment().equals(PaymentTypes.cardOnline().getDescription()),
                "Способ оплаты в деталях заказа не совпадает с выбранным во время оформления");

        assertOrderDocumentsAreAvailable();
    }


    @Test(
            description = "Тест заказа с оплатой картой курьеру",
            groups = {"acceptance","regression"},
            priority = 2103
    )
    public void successOrderWithCardCourierAndCheckDocuments() {
        kraken.reach().checkout();
        kraken.checkout().complete(PaymentTypes.cardCourier());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с оплатой картой курьеру\n");

        Assert.assertTrue(kraken.grab().shipmentPayment().equals(PaymentTypes.cardCourier().getDescription()),
                "Способ оплаты в деталях заказа не совпадает с выбранным во время оформления");

        assertOrderDocumentsAreAvailable();
    }


    @Test(
            description = "Тест заказа с оплатой банковским переводом",
            groups = {"acceptance","regression"},
            priority = 2104
    )
    public void successOrderWithBankTransferAndCheckDocuments() {
        kraken.reach().checkout();
        kraken.checkout().complete(PaymentTypes.bankTransfer());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с оплатой банковским переводом\n");

        Assert.assertTrue(kraken.grab().shipmentPayment().equals(PaymentTypes.bankTransfer().getDescription()),
                "Способ оплаты в деталях заказа не совпадает с выбранным во время оформления");

        assertOrderDocumentsAreAvailable();
    }

    @AfterMethod(alwaysRun = true)
    public void postconditions() {
        kraken.perform().cancelLastOrder();
    }
}
