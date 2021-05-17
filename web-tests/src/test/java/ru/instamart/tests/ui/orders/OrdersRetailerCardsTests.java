package ru.instamart.tests.ui.orders;

import ru.instamart.api.common.RestAddresses;
import ru.instamart.core.setting.Config;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.core.testdata.RetailerCards;
import ru.instamart.ui.data.lib.Addresses;
import ru.instamart.ui.data.lib.Pages;
import ru.instamart.ui.module.Shop;
import ru.instamart.ui.module.User;
import ru.instamart.ui.module.checkout.RetailerCardsActions;
import ru.instamart.ui.module.shop.Order;
import ru.instamart.ui.module.shop.ShippingAddressModal;
import ru.instamart.ui.Elements;
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
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.submit();
        Shop.Cart.drop();
    }

    @Test(  description = "Тест заказа с картой Метро (только METRO WL)",
            groups = {"metro-acceptance","metro-regression"}
    )
    public void successOrderWithMetroRetailerCard() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress());
        kraken.reach().checkout();
        
        RetailerCardsActions.addCard(RetailerCards.metro());
        kraken.checkout().complete();

        String number = kraken.grab().shipmentNumber();
        Order.cancelOrder();
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
        ShippingAddressModal.open();
        ShippingAddressModal.fill(RestAddresses.Moscow.Vkusvill.michurinsky());
        ShippingAddressModal.submit();
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress());
        kraken.reach().checkout();

        RetailerCardsActions.addCard(RetailerCards.vkusvill());
        kraken.checkout().complete();

        String number = kraken.grab().shipmentNumber();
        Order.cancelOrder();
        kraken.reach().admin(Pages.Admin.Order.details(number));

        assertTrue(
                kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.OrderDetailsPage.Details.loyaltyProgram()),
                "В заказе не применилась карта Вкусвилл\n");
    }
}
