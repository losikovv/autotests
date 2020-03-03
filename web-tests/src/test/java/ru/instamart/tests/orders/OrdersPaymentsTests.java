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
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress());
        kraken.reach().checkout();
    }

    @Test(
            description = "Тест заказа с оплатой картой онлайн",
            priority = 2102,
            groups = {
                    "lenta-acceptance", "lenta-regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderWithCardOnlineAndCheckDocuments() {
        kraken.checkout().complete(PaymentTypes.cardOnline());

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ с оплатой картой онлайн"));

        Assert.assertTrue(
                kraken.grab().shipmentPayment().equals(PaymentTypes.cardOnline().getDescription()),
                    failMessage("Способ оплаты в деталях заказа не совпадает с выбранным во время оформления"));
    }

    @Test(
            description = "Тест заказа с оплатой картой курьеру",
            priority = 2103,
            groups = {
                    "lenta-acceptance", "lenta-regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderWithCardCourierAndCheckDocuments() {
        kraken.checkout().complete(PaymentTypes.cardCourier());

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ с оплатой картой курьеру"));

        Assert.assertTrue(
                kraken.grab().shipmentPayment().equals(PaymentTypes.cardCourier().getDescription()),
                    failMessage("Способ оплаты в деталях заказа не совпадает с выбранным во время оформления"));
    }

    @Test(
            description = "Тест заказа с оплатой банковским переводом",
            priority = 2104,
            groups = {
                    "lenta-acceptance", "lenta-regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderWithBankTransferAndCheckDocuments() {
        kraken.checkout().complete(PaymentTypes.bankTransfer());

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ с оплатой банковским переводом"));

        Assert.assertTrue(
                kraken.grab().shipmentPayment().equals(PaymentTypes.bankTransfer().getDescription()),
                    failMessage("Способ оплаты в деталях заказа не совпадает с выбранным во время оформления"));
    }

    @AfterMethod(alwaysRun = true)
    public void postconditions() {
        kraken.rest().cancelCurrentOrder();
    }
}
