package ru.instamart.tests.ui.orders;

import instamart.api.common.RestAddresses;
import instamart.core.testdata.UserManager;
import instamart.core.testdata.ui.PaymentTypes;
import instamart.ui.modules.User;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

public class
OrdersPaymentsTests extends TestBase {
    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.get().baseUrl();
        User.Do.loginAs(UserManager.getDefaultAdmin());
    }

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void preconditions() {
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress());
        kraken.reach().checkout();
    }

    @AfterMethod(alwaysRun = true,
            description ="Очищаем окружение после теста")
    public void afterTest(ITestResult result){kraken.apiV2().cancelCurrentOrder();
    }

    @Test(
            description = "Тест заказа с оплатой картой онлайн",

            groups = {
                    "lenta-acceptance", "lenta-regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderWithCardOnline() {
        kraken.checkout().complete(PaymentTypes.cardOnline());

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ с оплатой картой онлайн"));

        Assert.assertEquals(
                kraken.grab().shipmentPayment(),
                    PaymentTypes.cardOnline().getDescription(),
                        failMessage("Способ оплаты в деталях заказа не совпадает с выбранным во время оформления"));
    }

    @Test(
            description = "Тест заказа с оплатой картой курьеру",

            groups = {
                    "lenta-acceptance", "lenta-regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderWithCardCourier() {
        kraken.checkout().complete(PaymentTypes.cardCourier());

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ с оплатой картой курьеру"));

        Assert.assertEquals(
                kraken.grab().shipmentPayment(),
                    PaymentTypes.cardCourier().getDescription(),
                        failMessage("Способ оплаты в деталях заказа не совпадает с выбранным во время оформления"));
    }

    @Test(
            description = "Тест заказа с оплатой банковским переводом",

            groups = {
                    "lenta-acceptance", "lenta-regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderWithBankTransfer() {
        kraken.checkout().complete(PaymentTypes.bankTransfer());

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ с оплатой банковским переводом"));

        Assert.assertEquals(
                kraken.grab().shipmentPayment(),
                    PaymentTypes.bankTransfer().getDescription(),
                        failMessage("Способ оплаты в деталях заказа не совпадает с выбранным во время оформления"));
    }


}
