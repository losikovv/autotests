package ru.instamart.tests.orders;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import instamart.core.common.AppManager;
import instamart.ui.objectsmap.Elements;
import instamart.core.testdata.ui.BonusPrograms;
import instamart.ui.common.lib.Pages;
import instamart.ui.modules.Administration;
import instamart.ui.modules.Checkout;
import instamart.ui.modules.User;
import instamart.api.common.RestAddresses;
import ru.instamart.tests.TestBase;

import static instamart.core.settings.Config.TestsConfiguration.OrdersTests.enableOrderBonusesTests;

public class OrdersBonusesTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Do.loginAs(AppManager.session.admin);
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress());

        kraken.reach().checkout();
        Checkout.Bonuses.deleteAll();
    }

    @Test(  enabled = enableOrderBonusesTests,
            description = "Тест заказа с добавлением бонусов Много.ру",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            priority = 2501
    )
    public void successOrderWithMnogoRuBonus() {
        Checkout.Bonuses.add(BonusPrograms.mnogoru());
        kraken.checkout().complete();

        String number = kraken.grab().shipmentNumber();
        kraken.reach().admin();
        Administration.Orders.searchOrder(number);

        Assert.assertTrue(
                kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.OrderDetailsPage.Details.loyaltyProgram()),
                    "В заказе не применилась бонусная программа Много.ру\n");
    }

    @Test(  enabled = enableOrderBonusesTests,
            description = "Тест заказа с добавлением бонусов Аерофлот Бонус",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            priority = 2502

    )
    public void successOrderWithAeroflotBonus() {
        Checkout.Bonuses.add(BonusPrograms.aeroflot());
        kraken.checkout().complete();

        String number = kraken.grab().shipmentNumber();
        kraken.reach().admin(Pages.Admin.Order.details(number));

        Assert.assertTrue(
                kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.OrderDetailsPage.Details.loyaltyProgram()),
                    "В заказе не применилась бонусная программа Аерофлот Бонус\n");
    }

    @AfterMethod(alwaysRun = true)
    public void postconditions() {
        kraken.rest().cancelCurrentOrder();
    }
}
