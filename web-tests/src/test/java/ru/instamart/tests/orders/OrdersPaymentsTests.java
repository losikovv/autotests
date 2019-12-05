package ru.instamart.tests.orders;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.application.AppManager;
import ru.instamart.application.lib.PaymentTypes;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.rest.RestAddresses;
import ru.instamart.tests.TestBase;

public class OrdersPaymentsTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.get().baseUrl();
        User.Do.loginAs(AppManager.session.admin);
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.learningCenter());
        kraken.reach().checkout();
    }


    @Test(
            description = "Тест заказа с оплатой наличными",
            groups = {"metro-acceptance", "metro-regression",},
            priority = 2101
    )
    public void successOrderWithCashAndCheckDocuments() {
        kraken.checkout().complete(PaymentTypes.cash());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с оплатой наличными\n");

        Assert.assertTrue(kraken.grab().shipmentPayment().equals(PaymentTypes.cash().getDescription()),
                "Способ оплаты в деталях заказа не совпадает с выбранным во время оформления");

        assertOrderDocumentsAreAvailable();
    }


    @Test(
            description = "Тест заказа с оплатой картой онлайн",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2102
    )
    public void successOrderWithCardOnlineAndCheckDocuments() {
        kraken.checkout().complete(PaymentTypes.cardOnline());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с оплатой картой онлайн\n");

        Assert.assertTrue(kraken.grab().shipmentPayment().equals(PaymentTypes.cardOnline().getDescription()),
                "Способ оплаты в деталях заказа не совпадает с выбранным во время оформления");

        assertOrderDocumentsAreAvailable();
    }


    @Test(
            description = "Тест заказа с оплатой картой курьеру",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2103
    )
    public void successOrderWithCardCourierAndCheckDocuments() {
        kraken.checkout().complete(PaymentTypes.cardCourier());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с оплатой картой курьеру\n");

        Assert.assertTrue(kraken.grab().shipmentPayment().equals(PaymentTypes.cardCourier().getDescription()),
                "Способ оплаты в деталях заказа не совпадает с выбранным во время оформления");

        assertOrderDocumentsAreAvailable();
    }


    @Test(
            description = "Тест заказа с оплатой банковским переводом",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2104
    )
    public void successOrderWithBankTransferAndCheckDocuments() {
        kraken.checkout().complete(PaymentTypes.bankTransfer());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с оплатой банковским переводом\n");

        Assert.assertTrue(kraken.grab().shipmentPayment().equals(PaymentTypes.bankTransfer().getDescription()),
                "Способ оплаты в деталях заказа не совпадает с выбранным во время оформления");

        assertOrderDocumentsAreAvailable();
    }

    @AfterMethod(alwaysRun = true)
    public void postconditions() {
        kraken.rest().cancelCurrentOrder();
    }
}
