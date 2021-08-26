package ru.instamart.test.ui.checkout;

import ru.instamart.api.common.RestAddresses;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.ui.checkpoint.BaseUICheckpoints;
import ru.instamart.ui.module.User;
import ru.instamart.ui.Elements;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.test.ui.TestBase;

public class BasicCheckoutTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    @BeforeClass(alwaysRun = true)
    public void preparingForCheckout() {
        User.Logout.quickly();
        User.Do.loginAs(UserManager.getDefaultUser());

        kraken.apiV2().fillCart(UserManager.getDefaultUser(), RestAddresses.Moscow.defaultAddress());
    }

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        kraken.reach().checkout();
    }

    @Skip
    @Test(  description = "Тест валидации дефолтного чекаута",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successValidateDefaultCheckoutPage() {
        baseChecks.checkPageIsAvailable();

        baseChecks.checkIsElementPresent(Elements.Checkout.header());

        baseChecks.checkIsElementPresent(Elements.Checkout.AddressStep.panel());
        baseChecks.checkIsElementPresent(Elements.Checkout.ContactsStep.minimizedPanel());
        baseChecks.checkIsElementPresent(Elements.Checkout.ReplacementsStep.minimizedPanel());
        baseChecks.checkIsElementPresent(Elements.Checkout.PaymentStep.minimizedPanel());
        baseChecks.checkIsElementPresent(Elements.Checkout.DeliveryStep.minimizedPanel());

        baseChecks.checkIsElementPresent(Elements.Checkout.SideBar.panel());
        baseChecks.checkIsElementPresent(Elements.Checkout.SideBar.itemsTotal());
        //baseChecks.checkIsElementPresent(Elements.Checkout.SideBar.total());
        baseChecks.checkIsElementPresent(Elements.Checkout.Promocode.addButton());

        if (kraken.detect().tenant("sbermarket")) baseChecks.checkIsElementPresent(Elements.Checkout.Bonuses.list());
        baseChecks.checkIsElementPresent(Elements.Checkout.SideBar.sendOrderButton());
    }

    @Test(  description = "Тест валидации заполненного чекаута",
            groups = {"sbermarket-regression"}
    )
    public void successValidateFilledCheckoutPage() {
        baseChecks.checkPageIsAvailable();
        throw new AssertionError("TODO");
    }
}
