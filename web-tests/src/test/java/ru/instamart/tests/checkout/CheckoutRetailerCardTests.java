package ru.instamart.tests.checkout;

import org.testng.annotations.BeforeClass;
import ru.instamart.application.AppManager;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.rest.RestAddresses;
import ru.instamart.tests.TestBase;

public class CheckoutRetailerCardTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void prepareForCheckout() {
        kraken.get().baseUrl();
        User.Do.loginAs(AppManager.session.admin);

        kraken.rest().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress());
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
