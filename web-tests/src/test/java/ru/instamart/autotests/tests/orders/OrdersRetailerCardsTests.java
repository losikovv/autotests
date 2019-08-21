package ru.instamart.autotests.tests.orders;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.*;
import ru.instamart.autotests.appmanager.AdministrationHelper;
import ru.instamart.autotests.appmanager.ShopHelper;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.application.Config.TestsConfiguration.OrdersTests.enableOrderRetailerCardsTests;

public class OrdersRetailerCardsTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.get().baseUrl();
        kraken.perform().loginAs(kraken.session.admin);
        ShopHelper.ShippingAddress.change(Addresses.Moscow.testAddress());
        ShopHelper.Cart.drop();
    }

    @Test(  enabled = enableOrderRetailerCardsTests,
            description = "Тест заказа с картой Метро (только WL)",
            groups = {"metro"},
            priority = 2601
    )
    public void successOrderWithMetroCard() {
        testOn(Environments.metro_production());

        kraken.get().page("metro");
        ShopHelper.Cart.collect();
        ShopHelper.Cart.proceedToCheckout();
        
        kraken.checkout().addRetailerCard(RetailerCards.metro());
        kraken.checkout().complete();

        String number = kraken.grab().currentOrderNumber();
        kraken.reach().admin(Pages.Admin.Order.details(number));

        Assert.assertTrue(
                kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.Order.Details.loyaltyProgram()),
                    "В заказе не применилась карта Метро\n");

        AdministrationHelper.Orders.cancelOrder();
    }
}
