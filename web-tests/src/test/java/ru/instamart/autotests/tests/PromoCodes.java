package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.testdata.Generate;

// Тесты промоушенов
// Пока рабоатет только на стейдже

public class PromoCodes extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void PreparingForPromoCodes() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().dropAuth();
        kraken.perform().registration(Generate.testUserData());
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
    }


    @Test(
            description = "Тест добавления промокода. Правило - первый заказ " +
                    "Дейсвтие - скидка 200р",
            groups = {"regression"},
            priority = 990
    )
    public void addPromocodeFirstOrder() {


        kraken.perform().reachCheckout();
        kraken.checkout().addPromocode("frstord");

        // Assert promocode is applied
        Assert.assertTrue(kraken.detect().isPromocodeApplied(),
                "Can't assert promocode is applied\n");

        kraken.checkout().complete();
    }


    @Test(
            description = "Тест добавления промокода. Правило - сумма заказа больше 2000р" +
                    "Действие - скидка 30% для ритейлера метро",
            groups = {"regression"},
            priority = 991
    )

    public void addPromocodeOrderSum() {

        kraken.get().checkoutPage();
        if(!kraken.checkout().isOnCheckout()){
            kraken.shopping().collectItems(2100);
            kraken.shopping().proceedToCheckout();
        }
        kraken.checkout().addPromocode("2000mtr");

        // Assert promocode is applied
        Assert.assertTrue(kraken.detect().isPromocodeApplied(),
                "Can't assert promocode is applied\n");

        kraken.checkout().complete();
    }


    @Test(
            description = "Тест добавления промокода. Правило - новый пользователь " +
                    "Действие - скидку 300р на продукты для категории молоко",
            groups = {"regression"},
            priority = 992
    )

    public void addPromocodeNewUser () {
        kraken.get().checkoutPage();
        if(!kraken.checkout().isOnCheckout()) {
            kraken.search().item("молоко");
            kraken.shopping().collectItems(2000);
            kraken.shopping().proceedToCheckout();
        }

        kraken.checkout().addPromocode("newusr");

        // Assert promocode is applied
        Assert.assertTrue(kraken.detect().isPromocodeApplied(),
                "Can't assert promocode is applied\n");

        kraken.checkout().complete();

    }

    @Test(
            description = "Тест добавления промокода. Правило - определнный заказ(первый) " +
                    "Действие - Бесплатная доставка",
            groups = {"regression"},
            priority = 993
    )

    public void addPromocodeCertainOrder() {

        kraken.get().checkoutPage();
        if(!kraken.checkout().isOnCheckout()){
            kraken.shopping().collectItems(2100);
            kraken.shopping().proceedToCheckout();
        }
        kraken.checkout().addPromocode("crtnord");

        // Assert promocode is applied
        Assert.assertTrue(kraken.detect().isPromocodeApplied(),
                "Can't assert promocode is applied\n");

        kraken.checkout().complete();
    }

    @Test(
            description = "Тест добавления промокода. Правило - серия заказов(два заказа) " +
                    "Действие - скидка",
            groups = {"regression"},
            priority = 994
    )

    public void addPromocodeSeriesOfOrders() {
        kraken.get().checkoutPage();
        if (!kraken.checkout().isOnCheckout()) {
            kraken.shopping().collectItems(2000);
            kraken.shopping().proceedToCheckout();
        }

        kraken.checkout().addPromocode("srsoford");

        // Assert promocode is applied
        Assert.assertTrue(kraken.detect().isPromocodeApplied(),
                "Can't assert promocode is applied\n");

        kraken.checkout().complete();

        // Повторяем Заказ

        kraken.perform().repeatLastOrder();

        // Проверяем что заказ повторился и корзина не пуста
        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Something went wrong while repeating the last order from the profile\n");

        if (!kraken.detect().isCheckoutButtonActive()) {
            kraken.shopping().collectItems(2000);
        }

        kraken.shopping().proceedToCheckout();
        kraken.checkout().addPromocode("srsoford");

        // Assert promocode is applied
        Assert.assertTrue(kraken.detect().isPromocodeApplied(),
                "Can't assert promocode is applied\n");

        kraken.checkout().complete();
    }

    @AfterMethod(alwaysRun = true)
    public void cleanupTestOrders() throws Exception {
        kraken.cleanup().all();
    }
}




