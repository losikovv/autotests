package ru.instamart.test.ui;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.testdata.PaymentTypes;
import ru.instamart.kraken.testdata.TestVariables;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.lib.Pages;
import ru.instamart.kraken.testdata.lib.ReplacementPolicies;
import ru.instamart.kraken.testdata.pagesdata.OrderDetailsData;
import ru.instamart.ui.module.Shop;
import ru.instamart.ui.module.User;
import ru.instamart.ui.module.shop.Order;

public class PlaygroundTests extends TestBase {

    @Test
    public void restOrder() {
        kraken.apiV2().order(UserManager.getDefaultUser(), 1);
        kraken.apiV2().cancelCurrentOrder();
    }

    @Test
    public void restDepartments() {
        SoftAssert softAssert = new SoftAssert();
        kraken.apiV2().getProductsFromEachDepartmentInStore(128, softAssert);
        softAssert.assertAll();
    }

    @Test
    public void regUser() {
        User.Do.registration();
    }

    @Test
    public void regUserAndSetAddress() {
        User.Do.registration();
//        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
    }

    @Test
    public void regUserAndPrepareForCheckout() {
        User.Do.registration();
//        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
        Shop.Cart.collectFirstTime();
    }

    @Test
    public void regUserAndPrepareForOrder() {
        User.Do.registration();
//        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
        kraken.reach().checkout();
        Order.fillOrderDetails(TestVariables.testOrderDetails());
    }

    @Test
    public void regUserAndMakeOrder() {
        User.Do.registration();
//        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
        kraken.reach().checkout();
        kraken.checkout().complete();
    }

    @Test
    public void makeOrder() {
        User.Do.loginAs(UserManager.getDefaultUser());
        kraken.reach().checkout();
        kraken.checkout().complete();
    }

    @Test
    public void makeNewOrder() {
        User.Do.loginAs(UserManager.getDefaultUser());
        kraken.reach().checkout();
        kraken.checkout().complete(PaymentTypes.cash());
    }

    @Test
    public void testNewOrder() {

        User.Do.loginAs(UserManager.getDefaultUser());
        kraken.get().page(Pages.Retailers.metro());
//        User.ShippingAddress.set(Addresses.Moscow.testAddress(),true);
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

        Order.fillOrderDetails(details);
        //kraken.ru.instamart.test.ui.checkout().complete(orderDetails);

        //kraken.perform().cancelLastOrder();
    }

    @Test
    public void testMultikraken() {
        User.Do.loginAs(UserManager.getDefaultAdmin());
        Order.order();
        //kraken.perform().loginAs(session.ru.instamart.test.ui.user);
        //kraken.perform().order();
    }

    @Test
    public void catalog() {
        kraken.get().page(CoreProperties.DEFAULT_RETAILER);
        //kraken.await().simply(5);
        Shop.CatalogDrawer.open();
        //kraken.await().simply(5);
        Shop.CatalogDrawer.close();
       // kraken.await().simply(5);
    }
}
