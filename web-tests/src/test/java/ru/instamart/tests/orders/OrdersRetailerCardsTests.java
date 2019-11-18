package ru.instamart.tests.orders;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.application.Config;
import ru.instamart.application.Elements;
import ru.instamart.application.Servers;
import ru.instamart.application.lib.*;
import ru.instamart.application.platform.modules.Administration;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.platform.modules.User;
import ru.instamart.tests.TestBase;

import static org.testng.Assert.assertTrue;

public class OrdersRetailerCardsTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.get().baseUrl();
        User.Do.loginAs(kraken.session.admin);
        User.ShippingAddress.change(Addresses.Moscow.testAddress());
        Shop.Cart.drop();
    }

    @Test(  enabled = Config.TestsConfiguration.OrdersTests.enableOrderRetailerCardsTests,
            description = "Тест заказа с картой Метро (только WL)",
            groups = {"metro-acceptance"},
            priority = 2601
    )
    public void successOrderWithMetroCard() {
        runTestOnlyOn(Servers.metro_production());

        kraken.get().page("metro");
        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        
        kraken.checkout().addRetailerCard(RetailerCards.metro());
        kraken.checkout().complete();

        String number = kraken.grab().currentOrderNumber();
        kraken.reach().admin(Pages.Admin.Order.details(number));

        assertTrue(
                kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.Order.Details.loyaltyProgram()),
                    "В заказе не применилась карта Метро\n");

        Administration.Orders.cancelOrder();
    }
}
