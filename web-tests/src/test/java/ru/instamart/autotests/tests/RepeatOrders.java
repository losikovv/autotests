package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.*;
import ru.instamart.autotests.models.UserData;


// Тесты повтора заказов


public class RepeatOrders extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void getAuth()throws Exception {
        kraken.get().page("metro");
        kraken.perform().loginAs(Users.superadmin());
    }


    @Test(
            description = "Повтор крайнего заказа и оплата картой онлайн",
            groups = {"acceptance","regression"},
            priority = 1001
    )
    public void successRepeatLastOrderAndPayWithCardOnline() throws Exception {
        kraken.perform().repeatLastOrder();

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не выполнены предусловия теста: заказ не повторяется, что-то пошло не так\n");

        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();
        kraken.checkout().complete(PaymentTypes.cardOnline());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется повторный заказ с оплатой картой онлайн\n");
    }


    @Test(
            description = "Повтор крайнего заказа и оплата картой курьеру",
            groups = {"acceptance","regression"},
            priority = 1002
    )
    public void successRepeatLastOrderAndPayWithCardCourier() throws Exception {
        kraken.perform().repeatLastOrder();

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не выполнены предусловия теста: заказ не повторяется, что-то пошло не так\n");

        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();
        kraken.checkout().complete(PaymentTypes.cardCourier());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется повторный заказ с оплатой картой курьеру\n");
    }


    @Test(
            description = "Повтор крайнего заказа и оплата наличными",
            groups = {"acceptance","regression"},
            priority = 1003
    )
    public void successRepeatLastOrderAndPayWithCash() throws Exception {
        kraken.perform().repeatLastOrder();

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не выполнены предусловия теста: заказ не повторяется, что-то пошло не так\n");

        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();
        kraken.checkout().complete(PaymentTypes.cash());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется повторный заказ с оплатой наличными\n");
    }


    @Test(
            description = "Повтор крайнего заказа и оплата банковским переводом",
            groups = {"acceptance","regression"},
            priority = 1004
    )
    public void successRepeatLastOrderAndPayWithBank() throws Exception {
        kraken.perform().repeatLastOrder();

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не выполнены предусловия теста: заказ не повторяется, что-то пошло не так\n");

        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();
        kraken.checkout().complete(PaymentTypes.bankTransfer());

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется повторный заказ с оплатой банковским переводом\n");
    }


    @Test(
            description = "Повтор крайнего заказа c новым номером телефона",
            groups = {"acceptance","regression"},
            priority = 1005
    )
    public void successRepeatLastOrderWithNewPhone() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        UserData userData = kraken.generate().testUserData();

        kraken.perform().quickLogout();
        kraken.perform().registration(userData);
        kraken.perform().order();

        String order1 = kraken.grab().currentOrderNumber();
        kraken.perform().cancelLastOrder();
        kraken.perform().reachAdmin(Pages.Admin.Order.requisites(order1));

        softAssert.assertEquals(kraken.grab().strippedPhoneNumber(Elements.Admin.Shipments.Order.Requisites.phoneField()), Config.testOrderDetails().getContactsDetails().getPhone(),
                "Номер телефона в админке не совпадает с указанным номером во время заказа");

        kraken.perform().quickLogout();
        kraken.perform().authorisation(userData);
        kraken.perform().repeatLastOrder();
        String phone = kraken.generate().digitString(10);
        kraken.perform().reachCheckout();
        kraken.checkout().complete(true, phone);

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не оформляется повторный заказ с новым номером телефона\n");

        String order2 = kraken.grab().currentOrderNumber();
        kraken.perform().cancelLastOrder();
        kraken.perform().reachAdmin(Pages.Admin.Order.requisites(order2));

        softAssert.assertEquals(kraken.grab().strippedPhoneNumber(Elements.Admin.Shipments.Order.Requisites.phoneField()), phone,
                "Номер телефона в админке не совпадает с указанным номером во время заказа");

        softAssert.assertAll();
    }


    @AfterMethod(alwaysRun = true)
    public void cancelLastOrder()throws Exception {
        kraken.perform().cancelLastOrder();
    }
}
