package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Checkout extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void prepareForCheckout() {
        kraken.get().baseUrl();
        kraken.perform().loginAs(kraken.session.admin);
    }

    // TODO successValidateCheckoutPage
        // TODO successValidateCheckoutSideBar
}
