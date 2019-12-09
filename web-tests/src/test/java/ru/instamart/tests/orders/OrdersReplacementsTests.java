package ru.instamart.tests.orders;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.application.AppManager;
import ru.instamart.application.Elements;
import ru.instamart.application.lib.Pages;
import ru.instamart.application.lib.ReplacementPolicies;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.rest.RestAddresses;
import ru.instamart.tests.TestBase;

import static ru.instamart.application.Config.TestsConfiguration.OrdersTests.enableOrderReplacementsTests;

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
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2201
    )
    public void successOrderWithCallAndReplacePolicy() {
        kraken.checkout().complete(ReplacementPolicies.callAndReplace());

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ с политикой \"Звонить / Заменять\"\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.reach().admin(Pages.Admin.Order.details(number));

        Assert.assertEquals(
                kraken.grab().text(Elements.Administration.ShipmentsSection.Order.Details.replacementPolicy()),
                    ReplacementPolicies.callAndReplace().getInstruction(),
                        "Текст инструкции по сборке не совпадает с выбранной политикой замен"
        );
    }

    @Test(enabled = enableOrderReplacementsTests,
            description = "Тест заказа с политикой Звонить / Убирать",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2202
    )
    public void successOrderWithCallAndRemovePolicy() {
        kraken.checkout().complete(ReplacementPolicies.callAndRemove());

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ с политикой \"Звонить / Убирать\"\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.reach().admin(Pages.Admin.Order.details(number));

        Assert.assertEquals(
                kraken.grab().text(Elements.Administration.ShipmentsSection.Order.Details.replacementPolicy()),
                    ReplacementPolicies.callAndRemove().getInstruction(),
                        "Текст инструкции по сборке не совпадает с выбранной политикой замен"
        );
    }

    @Test(enabled = enableOrderReplacementsTests,
            description = "Тест заказа с политикой Не звонить / Заменять",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2203
    )
    public void successOrderWithReplacePolicy() {
        kraken.checkout().complete(ReplacementPolicies.replace());

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ с политикой \"Не звонить / Заменять\"\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.reach().admin(Pages.Admin.Order.details(number));

        Assert.assertEquals(
                kraken.grab().text(Elements.Administration.ShipmentsSection.Order.Details.replacementPolicy()),
                    ReplacementPolicies.replace().getInstruction(),
                        "Текст инструкции по сборке не совпадает с выбранной политикой замен"
        );
    }

    @Test(enabled = enableOrderReplacementsTests,
            description = "Тест заказа с политикой Не звонить / Убирать",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2204
    )
    public void successOrderWithRemovePolicy() {
        kraken.checkout().complete(ReplacementPolicies.remove());

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ с политикой \"Не звонить / Убирать\"\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.reach().admin(Pages.Admin.Order.details(number));

        Assert.assertEquals(
                kraken.grab().text(Elements.Administration.ShipmentsSection.Order.Details.replacementPolicy()),
                    ReplacementPolicies.remove().getInstruction(),
                        "Текст инструкции по сборке не совпадает с выбранной политикой замен"
        );
    }

    @AfterMethod(alwaysRun = true)
    public void postcondition() {
        kraken.rest().cancelCurrentOrder();
    }
}
