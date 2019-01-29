package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;


// Тесты промоушенов

public class PromoCodes extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void preconditions() throws Exception {
        kraken.get().baseUrl();
        kraken.drop().auth();
        kraken.perform().registration();
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
    }


    @Test(
            description = "Тест применения промокода со скидкой 200р на первый заказ",
            groups = {"regression"},
            priority = 1401
    )
    public void successOrderWithFirstOrderPromo() {
        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();

        kraken.checkout().addPromocode("frstord");

        Assert.assertTrue(kraken.detect().isPromocodeApplied(),
                "Не применяется промокод со скидкой 200р на первый заказ\n");

        kraken.checkout().complete();
    }


    @Test(
            description = "Тест применения промокода со скидкой 30% на заказ от 2000р в Метро",
            groups = {"regression"},
            priority = 1402
    )
    public void successOrderWithOrderSumPromo() {
        kraken.shopping().collectItems(2100);
        kraken.shopping().proceedToCheckout();

        kraken.checkout().addPromocode("2000mtr");

        Assert.assertTrue(kraken.detect().isPromocodeApplied(),
                "Не применяется промокод со скидкой 30% на заказ от 2000р в Метро\n");

        kraken.checkout().complete();
    }


    @Test(
            description = "Тест применения промокода со скидкой 300р на молочные продукты для новых пользователей",
            groups = {"regression"},
            priority = 1403
    )
    public void successOrderWithNewUserPromo () {
        kraken.search().item("молоко");
        kraken.shopping().collectItems(2000);
        kraken.shopping().proceedToCheckout();

        kraken.checkout().addPromocode("newusr");

        Assert.assertTrue(kraken.detect().isPromocodeApplied(),
                "Не применяется промокод со скидкой 300р на молочные продукты для новых пользователей\n");

        kraken.checkout().complete();
    }


    @Test(
            description = "Тест применения промокода на бесплатную доставку на первый заказ",
            groups = {"regression"},
            priority = 1404
    )
    public void successOrderWithCertainOrderPromo() {
        kraken.shopping().collectItems(2100);
        kraken.shopping().proceedToCheckout();

        kraken.checkout().addPromocode("crtnord");

        Assert.assertTrue(kraken.detect().isPromocodeApplied(),
                "Не применяется промокод на бесплатную доставку на первый заказ\n");

        kraken.checkout().complete();
    }


    @Test(
            description = "Тест применения промокода на скидку на второй заказ",
            groups = {"regression"},
            priority = 1405
    )
    public void successOrderWithSeriesOfOrdersPromo() {
        kraken.shopping().collectItems(2000);
        kraken.shopping().proceedToCheckout();
        kraken.checkout().complete();

        kraken.get().baseUrl();
        kraken.shopping().collectItems(2000);
        kraken.shopping().proceedToCheckout();
        kraken.checkout().addPromocode("srsoford");

        Assert.assertTrue(kraken.detect().isPromocodeApplied(),
                "Не применяется промокод на скидку на второй заказ\n");

        kraken.checkout().complete();
    }


    @AfterMethod(alwaysRun = true)
    public void postconditions() throws Exception {
        kraken.cleanup().all();
    }
}




