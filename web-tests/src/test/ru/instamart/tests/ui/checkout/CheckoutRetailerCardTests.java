package ru.instamart.tests.ui.checkout;

import instamart.api.common.RestAddresses;
import instamart.core.testdata.UserManager;
import instamart.ui.modules.User;
import org.testng.annotations.BeforeClass;
import ru.instamart.tests.ui.TestBase;

public class CheckoutRetailerCardTests extends TestBase {
    @BeforeClass(alwaysRun = true)
    public void prepareForCheckout() {
        kraken.get().baseUrl();
        User.Do.loginAs(UserManager.getDefaultAdmin());
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress());
    }

    // TODO написать тесты добавления карты ритейлера (priority = 1800)

        // TODO public void successAddRetailerCard {}

        // TODO public void noAddRetailerCardOnCancel {}

        // TODO public void noAddRetailerCardOnModalClose {}

        // TODO public void noAddRetailerCardWithWrongNumber {}

        // TODO public void noAddRetailerCardWithEmptyNumber {}

        // TODO public void successEditRetailerCard {}

        // TODO public void noEditRetailerCardOnCancel {}

        // TODO public void noEditRetailerCardOnModalClose {}

        // TODO public void noEditRetailerCardWithSameCardNumber() {}

        // TODO public void noEditRetailerCardWithEmptyCardNumber() {}

        // TODO public void successDeleteRetailerCard {}

        // TODO public void noDeleteRetailerCardOnCancel() {}

        // TODO public void noDeleteRetailerCardOnModalClose() {}
}
