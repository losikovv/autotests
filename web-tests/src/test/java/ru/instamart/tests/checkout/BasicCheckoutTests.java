package ru.instamart.tests.checkout;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.application.AppManager;
import ru.instamart.application.Elements;
import ru.instamart.application.Tenants;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.rest.RestAddresses;
import ru.instamart.tests.TestBase;

public class BasicCheckoutTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void preparingForCheckout() {
        User.Logout.quickly();
        User.Do.loginAs(AppManager.session.user);

        kraken.rest().fillCart(AppManager.session.user, RestAddresses.Moscow.defaultAddress());
    }

    @BeforeMethod(alwaysRun = true)
    public void reachCheckout() {
        kraken.reach().checkout();
    }

    @Test(  description = "Тест валидации дефолтного чекаута",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 1000
    )
    public void successValidateDefaultCheckoutPage() {
        assertPageIsAvailable();

        assertPresence(Elements.Checkout.header());

        assertPresence(Elements.Checkout.AddressStep.panel());
        assertPresence(Elements.Checkout.ContactsStep.minimizedPanel());
        assertPresence(Elements.Checkout.ReplacementsStep.minimizedPanel());
        assertPresence(Elements.Checkout.PaymentStep.minimizedPanel());
        assertPresence(Elements.Checkout.DeliveryStep.minimizedPanel());

        assertPresence(Elements.Checkout.SideBar.panel());
        assertPresence(Elements.Checkout.SideBar.itemsTotal());
        //assertPresence(Elements.Checkout.SideBar.total());
        assertPresence(Elements.Checkout.Promocode.addButton());

        if (kraken.detect().tenant("sbermarket")) assertPresence(Elements.Checkout.Bonuses.list());
        assertPresence(Elements.Checkout.SideBar.sendOrderButton());
    }

    @Test(  description = "Тест валидации заполненного чекаута",
            groups = {"sbermarket-regression"},
            priority = 1900
    )
    public void successValidateFilledCheckoutPage() {
        assertPageIsAvailable();
        throw new AssertionError("TODO");
    }
}
