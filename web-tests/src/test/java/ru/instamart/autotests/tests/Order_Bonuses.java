package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.*;

// Тесты заказов со всеми бонусными программами

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


    @Test(
            description = "Тест Много ру",
            groups = {"acceptance", "regression"},
            priority = 1231231
    )
    public void successOrderWithMnogoRu() throws Exception {
        kraken.checkout().addBonus(BonusPrograms.mnogoru());
        kraken.checkout().complete();

        String number = kraken.grab().currentOrderNumber();
        kraken.reach().admin(Pages.Admin.Order.details(number));


        Assert.assertTrue(kraken.detect().isElementPresent(Elements.Admin.Shipments.Order.Details.loyaltyProgram()),
                "Бонусная программа не применилась");

    }

    @Test(
            description = "Тест аерофлот бонус",
            groups = {"acceptance", "regression"},
            priority = 1231233

    )
    public void successOrderAeroflot() throws Exception {
        kraken.checkout().addBonus(BonusPrograms.aeroflot());
        kraken.checkout().complete();

        String number = kraken.grab().currentOrderNumber();
        kraken.reach().admin(Pages.Admin.Order.details(number));


        Assert.assertTrue(kraken.detect().isElementPresent(Elements.Admin.Shipments.Order.Details.loyaltyProgram()),
                "Бонусная программа не применилась");
    }





}
