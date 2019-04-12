package ru.instamart.autotests.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.*;
import ru.instamart.autotests.models.*;

import static ru.instamart.autotests.appmanager.ApplicationManager.session;

public class Playground extends TestBase {

    @Test
    public void regUser() throws Exception {
        kraken.perform().registration();
    }

    @Test
    public void regUserAndSetAddress() throws Exception {
        kraken.perform().registration();
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
    }

    @Test
    public void regUserAndPrepareForCheckout() throws Exception {
        kraken.perform().registration();
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
        kraken.shopping().collectItems();
    }

    @Test
    public void regUserAndPrepareForOrder() throws Exception {
        kraken.perform().registration();
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
        kraken.reach().checkout();
        kraken.checkout().fillOrderDetails(Config.testOrderDetails());
    }

    @Test
    public void regUserAndMakeOrder() throws Exception {
        kraken.perform().registration();
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
        kraken.reach().checkout();
        kraken.checkout().complete();
    }

    @Test
    public void makeOrder() throws Exception {
        kraken.perform().loginAs(session.user);
        kraken.reach().checkout();
        kraken.checkout().complete();
    }

    @Test
    public void makeNewOrder() throws Exception {
        kraken.perform().loginAs(session.user);
        kraken.reach().checkout();
        kraken.checkout().complete(PaymentTypes.cash());
    }

    @Test
    public void testNewOrder() throws Exception {

        kraken.perform().loginAs(session.user);
        kraken.get().page(Pages.Site.Retailers.metro());
        kraken.shipAddress().change(Addresses.Moscow.testAddress());
        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();

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
    public void cancelTestOrders() throws Exception {
        kraken.cleanup().orders();
        Assert.assertFalse(kraken.detect().element(Elements.Admin.Shipments.emptyListPlaceholder()),
                "Отменились не все тестовые заказы\n");
    }

    @Test
    public void deleteTestUsers() throws Exception {
        kraken.cleanup().users();
        Assert.assertFalse(kraken.detect().element(Elements.Admin.Users.userlistFirstRow()),
                "Удалились не все тестовые юзеры\n");
    }

    @Test
    public void testMultikraken() throws Exception {
        kraken.perform().loginAs(session.admin);
        kraken.perform().order();
        //kraken.perform().loginAs(session.user);
        //kraken.perform().order();
    }

    @Test
    public void catalog() throws Exception {
        kraken.get().page("metro");
        kraken.await().simply(5);
        kraken.shopping().openCatalog();
        kraken.await().simply(5);
        kraken.perform().click(By.xpath("//a[contains(text(),'Овощи и фрукты')]"));
        kraken.await().simply(5);
    }
}
