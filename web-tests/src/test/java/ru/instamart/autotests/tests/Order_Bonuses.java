package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.*;

import static ru.instamart.autotests.application.Config.testOrderBonuses;

public class Order_Bonuses extends TestBase {

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

    @Test(  enabled = testOrderBonuses,
            description = "Тест заказа с добавлением бонусов Много.ру",
            groups = {"acceptance", "regression"},
            priority = 951
    )
    public void successOrderWithMnogoRuBonus() {
        kraken.checkout().addBonus(BonusPrograms.mnogoru());
        kraken.checkout().complete();

        String number = kraken.grab().currentOrderNumber();
        kraken.reach().admin(Pages.Admin.Order.details(number));

        Assert.assertTrue(
                kraken.detect().isElementPresent(Elements.Admin.Shipments.Order.Details.loyaltyProgram()),
                    "В заказе не применилась бонусная программа Много.ру\n");
    }

    @Test(  enabled = testOrderBonuses,
            description = "Тест заказа с добавлением бонусов Аерофлот Бонус",
            groups = {"acceptance", "regression"},
            priority = 952

    )
    public void successOrderAeroflotBonus() {
        kraken.checkout().addBonus(BonusPrograms.aeroflot());
        kraken.checkout().complete();

        String number = kraken.grab().currentOrderNumber();
        kraken.reach().admin(Pages.Admin.Order.details(number));

        Assert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.Admin.Shipments.Order.Details.loyaltyProgram()),
                "В заказе не применилась бонусная программа Аерофлот Бонус\n");
    }

    @AfterMethod(alwaysRun = true)
    public void postconditions() {
        kraken.admin().cancelOrder();
    }
}
