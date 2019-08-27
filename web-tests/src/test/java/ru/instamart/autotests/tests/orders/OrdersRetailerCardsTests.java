package ru.instamart.autotests.tests.orders;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.*;
import ru.instamart.autotests.application.libs.Addresses;
import ru.instamart.autotests.application.libs.RetailerCards;
import ru.instamart.autotests.appmanager.platform.Administration;
import ru.instamart.autotests.appmanager.platform.Shop;
import ru.instamart.autotests.appmanager.platform.User;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.application.Config.TestsConfiguration.OrdersTests.enableOrderRetailerCardsTests;

public class OrdersRetailerCardsTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.get().baseUrl();
        User.Do.loginAs(kraken.session.admin);
        Shop.ShippingAddress.change(Addresses.Moscow.testAddress());
        Shop.Cart.drop();
    }

    @Test(  enabled = enableOrderRetailerCardsTests,
            description = "Тест заказа с картой Метро (только WL)",
            groups = {"metro"},
            priority = 2601
    )
    public void successOrderWithMetroCard() {
        runTestOnlyOn(Environments.metro_production());

        kraken.get().page("metro");
        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        
        kraken.checkout().addRetailerCard(RetailerCards.metro());
        kraken.checkout().complete();

        String number = kraken.grab().currentOrderNumber();
        kraken.reach().admin(Pages.Admin.Order.details(number));

        Assert.assertTrue(
                kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.Order.Details.loyaltyProgram()),
                    "В заказе не применилась карта Метро\n");

        Administration.Orders.cancelOrder();
    }
}
