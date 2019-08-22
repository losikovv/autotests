package ru.instamart.autotests.tests.orders;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.application.ReplacementPolicies;
import ru.instamart.autotests.appmanager.ShopHelper;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.application.Config.TestsConfiguration.OrdersTests.enableOrderReplacementsTests;

public class OrdersReplacementsTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.get().baseUrl();
        kraken.perform().loginAs(kraken.session.admin);
        ShopHelper.ShippingAddress.change(Addresses.Moscow.testAddress());
        ShopHelper.Cart.drop();
    }

    @Test(enabled = enableOrderReplacementsTests,
            description = "Тест заказа с политикой Звонить / Заменять",
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2201
    )
    public void successOrderWithCallAndReplacePolicy() {
        kraken.reach().checkout();
        kraken.checkout().complete(ReplacementPolicies.callAndReplace());

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ с политикой \"Звонить / Заменять\"\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.perform().cancelLastOrder();
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
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2202
    )
    public void successOrderWithCallAndRemovePolicy() {
        kraken.reach().checkout();
        kraken.checkout().complete(ReplacementPolicies.callAndRemove());

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ с политикой \"Звонить / Убирать\"\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.perform().cancelLastOrder();
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
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2203
    )
    public void successOrderWithReplacePolicy() {
        kraken.reach().checkout();
        kraken.checkout().complete(ReplacementPolicies.replace());

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ с политикой \"Не звонить / Заменять\"\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.perform().cancelLastOrder();
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
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 2204
    )
    public void successOrderWithRemovePolicy() {
        kraken.reach().checkout();
        kraken.checkout().complete(ReplacementPolicies.remove());

        Assert.assertTrue(
                kraken.detect().isOrderActive(),
                    "Не удалось оформить заказ с политикой \"Не звонить / Убирать\"\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.perform().cancelLastOrder();
        kraken.reach().admin(Pages.Admin.Order.details(number));

        Assert.assertEquals(
                kraken.grab().text(Elements.Administration.ShipmentsSection.Order.Details.replacementPolicy()),
                    ReplacementPolicies.remove().getInstruction(),
                        "Текст инструкции по сборке не совпадает с выбранной политикой замен"
        );
    }
}
