package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.*;
import ru.instamart.autotests.appmanager.AdministrationHelper;
import ru.instamart.autotests.appmanager.ShopHelper;

import static ru.instamart.autotests.application.Config.testOrderRetailerCards;

public class OrderRetailerCards extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.get().baseUrl();
        kraken.perform().loginAs(kraken.session.admin);
        kraken.shipAddress().change(Addresses.Moscow.testAddress());
        kraken.drop().cart();
    }

    @Test(  enabled = testOrderRetailerCards,
            description = "Тест заказа с картой Метро (только WL)",
            groups = {"metro"},
            priority = 961
    )
    public void successOrderWithMetroCard() {
        testOn(Environments.metro_production());

        kraken.get().page("metro");
        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        
        kraken.checkout().addRetailerCard(RetailerCards.metro());
        kraken.checkout().complete();

        String number = kraken.grab().currentOrderNumber();
        kraken.reach().admin(Pages.Admin.Order.details(number));

        Assert.assertTrue(
                kraken.detect().isElementPresent(Elements.Admin.Shipments.Order.Details.loyaltyProgram()),
                    "В заказе не применилась карта Метро\n");

        AdministrationHelper.Orders.cancelOrder();
    }
}
