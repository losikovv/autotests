package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.PaymentTypes;


// Тесты заказов со всеми способами оплаты


public class Order_Payments extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().loginAs(kraken.session.user);
        kraken.shipAddress().change(Addresses.Moscow.testAddress());
        kraken.drop().cart();
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.reach().checkout();
    }


    @Test(
            description = "Тест заказа с оплатой наличными",
            groups = {"acceptance","regression"},
            priority = 901
    )
    public void successOrderWithCash() {
        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с оплатой наличными\n");

        Assert.assertTrue(kraken.grab().shipmentPayment().equals(PaymentTypes.cash().getDescription()),
                "Способ оплаты в деталях заказа не совпадает с выбранным во время оформления");

        kraken.check().orderDocuments();
        assertPageIsAvailable();
    }


    @Test(
            description = "Тест заказа с оплатой картой онлайн",
            groups = {"acceptance","regression"},
            priority = 902
    )
    public void successOrderWithCardOnline() {
        kraken.checkout().complete(PaymentTypes.cardOnline());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с оплатой картой онлайн\n");

        Assert.assertTrue(kraken.grab().shipmentPayment().equals(PaymentTypes.cardOnline().getDescription()),
                "Способ оплаты в деталях заказа не совпадает с выбранным во время оформления");

        kraken.check().orderDocuments();
        assertPageIsAvailable();
    }


    @Test(
            description = "Тест заказа с оплатой картой курьеру",
            groups = {"acceptance","regression"},
            priority = 903
    )
    public void successOrderWithCardCourier() {
        kraken.checkout().complete(PaymentTypes.cardCourier());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с оплатой картой курьеру\n");

        Assert.assertTrue(kraken.grab().shipmentPayment().equals(PaymentTypes.cardCourier().getDescription()),
                "Способ оплаты в деталях заказа не совпадает с выбранным во время оформления");

        kraken.check().orderDocuments();
        assertPageIsAvailable();
    }


    @Test(
            description = "Тест заказа с оплатой банковским переводом",
            groups = {"acceptance","regression"},
            priority = 904
    )
    public void successOrderWithBankTransfer() {
        kraken.checkout().complete(PaymentTypes.bankTransfer());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с оплатой банковским переводом\n");

        Assert.assertTrue(kraken.grab().shipmentPayment().equals(PaymentTypes.bankTransfer().getDescription()),
                "Способ оплаты в деталях заказа не совпадает с выбранным во время оформления");

        kraken.check().orderDocuments();
        assertPageIsAvailable();
    }

    @AfterMethod(alwaysRun = true)
    public void postconditions() {
        kraken.perform().cancelLastOrder();
    }
}
