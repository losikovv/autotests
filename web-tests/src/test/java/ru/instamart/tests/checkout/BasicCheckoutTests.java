package ru.instamart.tests.checkout;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.application.AppManager;
import ru.instamart.application.Elements;
import ru.instamart.application.Tenants;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.platform.modules.User;
import ru.instamart.tests.TestBase;

public class BasicCheckoutTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void preparingForCheckout() {
        User.Logout.quickly();
        User.Do.loginAs(AppManager.session.user);
        Shop.Cart.collect();
    }

    @BeforeMethod(alwaysRun = true)
    public void reachCheckout() {
        kraken.reach().checkout();
    }

    @Test(  description = "Тест валидации дефолтного чекаута",
            groups = {"acceptance","regression"},
            priority = 1000
    )
    public void successValidateDefaultCheckoutPage() {
        assertPageIsAvailable();

        assertElementPresence(Elements.Checkout.header());

        assertElementPresence(Elements.Checkout.AddressStep.panel());
        assertElementPresence(Elements.Checkout.ContactsStep.minimizedPanel());
        assertElementPresence(Elements.Checkout.ReplacementsStep.minimizedPanel());
        assertElementPresence(Elements.Checkout.PaymentStep.minimizedPanel());
        assertElementPresence(Elements.Checkout.DeliveryStep.minimizedPanel());

        assertElementPresence(Elements.Checkout.SideBar.panel());
        assertElementPresence(Elements.Checkout.SideBar.itemsTotal());
        assertElementPresence(Elements.Checkout.SideBar.total());
        assertElementPresence(Elements.Checkout.Promocode.addButton());

        if (kraken.detect().tenant(Tenants.instamart())) assertElementPresence(Elements.Checkout.Bonuses.list());
        assertElementPresence(Elements.Checkout.SideBar.sendOrderButton());
    }

    @Test(  description = "Тест валидации заполненного чекаута",
            groups = {"regression"},
            priority = 1900
    )
    public void successValidateFilledCheckoutPage() {
        assertPageIsAvailable();
        throw new AssertionError("TODO");
    }
}
