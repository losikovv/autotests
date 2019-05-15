package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.application.ReplacementPolicies;

import static ru.instamart.autotests.application.Config.enableOrderReplacementsTests;

public class Order_Replacements extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().loginAs(kraken.session.admin);
        kraken.shipAddress().change(Addresses.Moscow.testAddress());
        kraken.drop().cart();
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.reach().checkout();
    }

    @Test(enabled = enableOrderReplacementsTests,
            description = "Тест заказа с политикой Звонить / Заменять",
            groups = {"acceptance","regression"},
            priority = 911
    )
    public void successOrderWithCallAndReplacePolicy() throws Exception {
        kraken.checkout().complete(ReplacementPolicies.callAndReplace());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с политикой Звонить / Заменять\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.perform().cancelLastOrder();
        kraken.reach().admin(Pages.Admin.Order.details(number));

        Assert.assertEquals(
                kraken.grab().text(Elements.Admin.Shipments.Order.Details.replacementPolicy()),
                ReplacementPolicies.callAndReplace().getInstruction(),
                "Текст инструкции по сборке не совпадает с выбранной политикой замен"
        );
    }

    @Test(enabled = enableOrderReplacementsTests,
            description = "Тест заказа с политикой Звонить / Убирать",
            groups = {"acceptance","regression"},
            priority = 912
    )
    public void successOrderWithCallAndRemovePolicy() throws Exception {
        kraken.checkout().complete(ReplacementPolicies.callAndRemove());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с политикой Звонить / Убирать\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.perform().cancelLastOrder();
        kraken.reach().admin(Pages.Admin.Order.details(number));

        Assert.assertEquals(
                kraken.grab().text(Elements.Admin.Shipments.Order.Details.replacementPolicy()),
                ReplacementPolicies.callAndRemove().getInstruction(),
                "Текст инструкции по сборке не совпадает с выбранной политикой замен"
        );
    }

    @Test(enabled = enableOrderReplacementsTests,
            description = "Тест заказа с политикой Не звонить / Заменять",
            groups = {"acceptance","regression"},
            priority = 913
    )
    public void successOrderWithReplacePolicy() throws Exception {
        kraken.checkout().complete(ReplacementPolicies.replace());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с политикой Не звонить / Заменять\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.perform().cancelLastOrder();
        kraken.reach().admin(Pages.Admin.Order.details(number));

        Assert.assertEquals(
                kraken.grab().text(Elements.Admin.Shipments.Order.Details.replacementPolicy()),
                ReplacementPolicies.replace().getInstruction(),
                "Текст инструкции по сборке не совпадает с выбранной политикой замен"
        );
    }

    @Test(enabled = enableOrderReplacementsTests,
            description = "Тест заказа с политикой Не звонить / Убирать",
            groups = {"acceptance","regression"},
            priority = 914
    )
    public void successOrderWithRemovePolicy() throws Exception {
        kraken.checkout().complete(ReplacementPolicies.remove());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ с политикой Не звонить / Убирать\n");

        String number = kraken.grab().currentOrderNumber();
        kraken.perform().cancelLastOrder();
        kraken.reach().admin(Pages.Admin.Order.details(number));

        Assert.assertEquals(
                kraken.grab().text(Elements.Admin.Shipments.Order.Details.replacementPolicy()),
                ReplacementPolicies.remove().getInstruction(),
                "Текст инструкции по сборке не совпадает с выбранной политикой замен"
        );
    }
}
