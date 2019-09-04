package ru.instamart.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.application.AppManager;
import ru.instamart.application.Config;
import ru.instamart.application.Elements;
import ru.instamart.application.lib.*;
import ru.instamart.application.models.ElementData;
import ru.instamart.application.models.OrderDetailsData;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.platform.modules.User;

public class Playground extends TestBase {

    @Test
    public void regUser() {
        User.Do.registration();
    }

    @Test
    public void regUserAndSetAddress() {
        User.Do.registration();
        Shop.ShippingAddress.set(Addresses.Moscow.defaultAddress());
    }

    @Test
    public void regUserAndPrepareForCheckout() {
        User.Do.registration();
        Shop.ShippingAddress.set(Addresses.Moscow.defaultAddress());
        Shop.Cart.collect();
    }

    @Test
    public void regUserAndPrepareForOrder() {
        User.Do.registration();
        Shop.ShippingAddress.set(Addresses.Moscow.defaultAddress());
        kraken.reach().checkout();
        kraken.checkout().fillOrderDetails(Config.TestVariables.testOrderDetails());
    }

    @Test
    public void regUserAndMakeOrder() {
        User.Do.registration();
        Shop.ShippingAddress.set(Addresses.Moscow.defaultAddress());
        kraken.reach().checkout();
        kraken.checkout().complete();
    }

    @Test
    public void makeOrder() {
        User.Do.loginAs(AppManager.session.user);
        kraken.reach().checkout();
        kraken.checkout().complete();
    }

    @Test
    public void makeNewOrder() {
        User.Do.loginAs(AppManager.session.user);
        kraken.reach().checkout();
        kraken.checkout().complete(PaymentTypes.cash());
    }

    @Test
    public void testNewOrder() {

        User.Do.loginAs(AppManager.session.user);
        kraken.get().page(Pages.Site.Retailers.metro());
        Shop.ShippingAddress.change(Addresses.Moscow.testAddress());
        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();

        OrderDetailsData details = new OrderDetailsData();
        details.setAddressDetails(
                "1234",
                "2345",
                "3456",
                false,
                "4567",
                "5678",
                "обожди ебана"
        );
        details.setContactsDetails(
                null,
                null,
                null,
                false,
                null,
                false
        );
        details.setReplacementPolicy(
                ReplacementPolicies.callAndReplace()
        );
        details.setPaymentDetails(
                PaymentTypes.cardCourier()

        );
        details.setDeliveryTime(
                7,
                7
        );
        //orderDetails.setPromocode("unicorn");
        //orderDetails.setBonus(BonusPrograms.aeroflot());
        //orderDetails.setLoyalty(LoyaltyPrograms.vkusvill());

        kraken.checkout().fillOrderDetails(details);
        //kraken.checkout().complete(orderDetails);

        //kraken.perform().cancelLastOrder();
    }

    @Test
    public void cancelTestOrders() {
        kraken.cleanup().orders();
        Assert.assertFalse(kraken.detect().element(Elements.Administration.ShipmentsSection.placeholder()),
                "Отменились не все тестовые заказы\n");
    }

    @Test
    public void deleteTestUsers() {
        kraken.cleanup().users();
        Assert.assertFalse(kraken.detect().element(Elements.Administration.UsersSection.userlistFirstRow()),
                "Удалились не все тестовые юзеры\n");
    }

    @Test
    public void testMultikraken() {
        User.Do.loginAs(AppManager.session.admin);
        kraken.perform().order();
        //kraken.perform().loginAs(session.user);
        //kraken.perform().order();
    }

    @Test
    public void catalog() {
        kraken.get().page("metro");
        //kraken.await().simply(5);
        Shop.CatalogDrawer.open();
        //kraken.await().simply(5);
        Shop.CatalogDrawer.close();
       // kraken.await().simply(5);
    }
}
