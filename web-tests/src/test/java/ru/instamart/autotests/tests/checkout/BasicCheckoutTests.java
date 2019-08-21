package ru.instamart.autotests.tests.checkout;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.appmanager.ApplicationManager.session;

public class BasicCheckoutTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void preparingForCheckout() {
        kraken.perform().quickLogout();
        kraken.perform().loginAs(session.user);
        kraken.shopping().collectItems();
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
        assertElementPresence(Elements.Checkout.Bonuses.list());
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
