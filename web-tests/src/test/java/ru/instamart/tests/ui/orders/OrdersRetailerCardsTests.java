package ru.instamart.tests.ui.orders;

import ru.instamart.api.common.RestAddresses;
import ru.instamart.core.settings.Config;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.core.testdata.ui.RetailerCards;
import ru.instamart.ui.common.lib.Addresses;
import ru.instamart.ui.common.lib.Pages;
import ru.instamart.ui.modules.Checkout;
import ru.instamart.ui.modules.Shop;
import ru.instamart.ui.modules.User;
import ru.instamart.ui.objectsmap.Elements;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

import static org.testng.Assert.assertTrue;

public class OrdersRetailerCardsTests extends TestBase {
    @BeforeClass(alwaysRun = true,
            description = "Подготавливаем тестовое окружение к прогону тестов")
    public void setup() {
        kraken.get().baseUrl();
        User.Do.loginAs(UserManager.getDefaultAdmin());
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
        Shop.Cart.drop();
    }

    @Test(  description = "Тест заказа с картой Метро (только METRO WL)",
            groups = {"metro-acceptance","metro-regression"}
    )
    public void successOrderWithMetroRetailerCard() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress());
        kraken.reach().checkout();
        
        Checkout.RetailerCards.addCard(RetailerCards.metro());
        kraken.checkout().complete();

        String number = kraken.grab().shipmentNumber();
        kraken.perform().cancelOrder();
        kraken.reach().admin(Pages.Admin.Order.details(number));

        assertTrue(
                kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.OrderDetailsPage.Details.loyaltyProgram()),
                    "В заказе не применилась карта Метро\n");
    }

    @Test(  description = "Тест заказа с картой Вкусвилл (только Sbermarket)",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successOrderWithVkusvillRetailerCard() {
        kraken.get().page(Pages.Retailers.vkusvill());
        User.ShippingAddress.set(RestAddresses.Moscow.Vkusvill.michurinsky(),true);
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress());
        kraken.reach().checkout();

        Checkout.RetailerCards.addCard(RetailerCards.vkusvill());
        kraken.checkout().complete();

        String number = kraken.grab().shipmentNumber();
        kraken.perform().cancelOrder();
        kraken.reach().admin(Pages.Admin.Order.details(number));

        assertTrue(
                kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.OrderDetailsPage.Details.loyaltyProgram()),
                "В заказе не применилась карта Вкусвилл\n");
    }
}
