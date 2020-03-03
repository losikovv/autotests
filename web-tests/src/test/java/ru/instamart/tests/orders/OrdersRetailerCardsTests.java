package ru.instamart.tests.orders;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.application.AppManager;
import ru.instamart.application.Config;
import ru.instamart.application.Elements;
import ru.instamart.application.lib.*;
import ru.instamart.application.platform.modules.Administration;
import ru.instamart.application.platform.modules.Checkout;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.rest.RestAddresses;
import ru.instamart.tests.TestBase;

import static org.testng.Assert.assertTrue;

public class OrdersRetailerCardsTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.get().baseUrl();
        User.Do.loginAs(kraken.session.admin);
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());
        Shop.Cart.drop();
    }

    @Test(  enabled = Config.TestsConfiguration.OrdersTests.enableOrderRetailerCardsTests,
            description = "Тест заказа с картой Метро (только METRO WL)",
            groups = {"metro-acceptance","metro-regression"},
            priority = 2601
    )
    public void successOrderWithMetroRetailerCard() {
        kraken.get().page("metro");
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress());
        kraken.reach().checkout();
        
        Checkout.RetailerCards.addCard(RetailerCards.metro());
        kraken.checkout().complete();

        String number = kraken.grab().shipmentNumber();
        kraken.perform().cancelOrder();
        kraken.reach().admin(Pages.Admin.Order.details(number));

        assertTrue(
                kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.Order.Details.loyaltyProgram()),
                    "В заказе не применилась карта Метро\n");
    }

    @Test(  enabled = Config.TestsConfiguration.OrdersTests.enableOrderRetailerCardsTests,
            description = "Тест заказа с картой Вкусвилл (только Sbermarket)",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 2602
    )
    public void successOrderWithVkusvillRetailerCard() {
        kraken.get().page(Pages.Retailers.vkusvill());
        User.ShippingAddress.set(RestAddresses.Moscow.Vkusvill.michurinsky());
        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress());
        kraken.reach().checkout();

        Checkout.RetailerCards.addCard(RetailerCards.vkusvill());
        kraken.checkout().complete();

        String number = kraken.grab().shipmentNumber();
        kraken.perform().cancelOrder();
        kraken.reach().admin(Pages.Admin.Order.details(number));

        assertTrue(
                kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.Order.Details.loyaltyProgram()),
                "В заказе не применилась карта Вкусвилл\n");
    }
}
