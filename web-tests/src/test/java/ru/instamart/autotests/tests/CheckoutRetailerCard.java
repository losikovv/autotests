package ru.instamart.autotests.tests;

import org.testng.annotations.BeforeClass;

public class CheckoutRetailerCard extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void prepareForCheckout() {
        kraken.get().baseUrl();
        kraken.perform().loginAs(kraken.session.admin);
    }

    // TODO написать тесты добавления карты ритейлера

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
