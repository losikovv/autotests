package ru.instamart.tests.orders;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import instamart.core.common.AppManager;
import instamart.ui.common.lib.ReplacementPolicies;
import instamart.ui.modules.User;
import instamart.api.common.RestAddresses;
import ru.instamart.tests.TestBase;

import static instamart.core.settings.Config.TestsConfiguration.OrdersTests.enableOrderReplacementsTests;

public class OrdersReplacementsTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.get().baseUrl();
        User.Do.loginAs(AppManager.session.admin);
    }

    @BeforeMethod(alwaysRun = true)
    public void precondition() {
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress());
        kraken.reach().checkout();
    }

    @Test(enabled = enableOrderReplacementsTests,
            description = "Тест заказа с политикой Звонить / Заменять",
            priority = 2201,
            groups = {
                    "lenta-acceptance", "lenta-regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderWithCallAndReplacePolicy() {
        kraken.checkout().complete(ReplacementPolicies.callAndReplace());

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ с политикой \"Звонить / Заменять\""));

        Assert.assertEquals(
                kraken.grab().shipmentReplacementPolicy(),
                    ReplacementPolicies.callAndReplace().getUserDescription(),
                        failMessage("Текст инструкции по сборке не совпадает с выбранной политикой замен"));
    }

    @Test(enabled = enableOrderReplacementsTests,
            description = "Тест заказа с политикой Звонить / Убирать",
            priority = 2201,
            groups = {
                    "lenta-acceptance", "lenta-regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderWithCallAndRemovePolicy() {
        kraken.checkout().complete(ReplacementPolicies.callAndRemove());

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ с политикой \"Звонить / Убирать\""));

        Assert.assertEquals(
                kraken.grab().shipmentReplacementPolicy(),
                    ReplacementPolicies.callAndRemove().getUserDescription(),
                        failMessage("Текст инструкции по сборке не совпадает с выбранной политикой замен"));
    }

    @Test(enabled = enableOrderReplacementsTests,
            description = "Тест заказа с политикой Не звонить / Заменять",
            priority = 2203,
            groups = {
                    "lenta-acceptance", "lenta-regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderWithReplacePolicy() {
        kraken.checkout().complete(ReplacementPolicies.replace());

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ с политикой \"Не звонить / Заменять\""));

        Assert.assertEquals(
                kraken.grab().shipmentReplacementPolicy(),
                    ReplacementPolicies.replace().getUserDescription(),
                        failMessage("Текст инструкции по сборке не совпадает с выбранной политикой замен"));
    }

    @Test(enabled = enableOrderReplacementsTests,
            description = "Тест заказа с политикой Не звонить / Убирать",
            priority = 2204,
            groups = {
                    "lenta-acceptance", "lenta-regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderWithRemovePolicy() {
        kraken.checkout().complete(ReplacementPolicies.remove());

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ с политикой \"Не звонить / Убирать\""));

        Assert.assertEquals(
                kraken.grab().shipmentReplacementPolicy(),
                    ReplacementPolicies.remove().getUserDescription(),
                        failMessage("Текст инструкции по сборке не совпадает с выбранной политикой замен"));
    }

    @AfterMethod(alwaysRun = true)
    public void postcondition() {
        kraken.rest().cancelCurrentOrder();
    }
}
