package ru.instamart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import instamart.core.common.AppManager;
import instamart.core.settings.Config;
import instamart.ui.objectsmap.Elements;
import instamart.ui.common.lib.Addresses;
import instamart.ui.common.lib.Pages;
import instamart.core.testdata.ui.PaymentTypes;
import instamart.ui.common.lib.ReplacementPolicies;
import instamart.ui.common.pagesdata.OrderDetailsData;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import instamart.api.helpers.ApiV2Helper;

public class Playground extends TestBase {

    @Test
    public void restOrder() {
        kraken.apiV2().order(AppManager.session.user, 1);
        kraken.apiV2().cancelCurrentOrder();
    }

    @Test
    public void restDepartments() {
        SoftAssert softAssert = new SoftAssert();
        ApiV2Helper.getProductsFromEachDepartmentInStore(128, softAssert);
        softAssert.assertAll();
    }

    @Test
    public void regUser() {
        User.Do.registration();
    }

    @Test
    public void regUserAndSetAddress() {
        User.Do.registration();
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
    }

    @Test
    public void regUserAndPrepareForCheckout() {
        User.Do.registration();
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
        Shop.Cart.collect();
    }

    @Test
    public void regUserAndPrepareForOrder() {
        User.Do.registration();
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
        kraken.reach().checkout();
        kraken.checkout().fillOrderDetails(Config.TestVariables.testOrderDetails());
    }

    @Test
    public void regUserAndMakeOrder() {
        User.Do.registration();
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
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
        kraken.get().page(Pages.Retailers.metro());
        User.ShippingAddress.set(Addresses.Moscow.testAddress(),true);
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
        Assert.assertFalse(kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.placeholder()),
                "Отменились не все тестовые заказы\n");
    }

    @Test
    public void deleteTestUsers() {
        kraken.cleanup().users();
        Assert.assertFalse(kraken.detect().isElementPresent(Elements.Administration.UsersSection.userlistFirstRow()),
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
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        //kraken.await().simply(5);
        Shop.CatalogDrawer.open();
        //kraken.await().simply(5);
        Shop.CatalogDrawer.close();
       // kraken.await().simply(5);
    }
}
