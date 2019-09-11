package ru.instamart.tests.orders;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.application.lib.Addresses;
import ru.instamart.application.lib.Promos;
import ru.instamart.application.platform.modules.Checkout;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.platform.modules.User;
import ru.instamart.tests.TestBase;

// Тесты заказов с промокодами
    // сначала негативная проверка, затем позитивная и заказ
    // + проверка в админке что в заказе есть промик
    // + проверка что в заказе есть скидка/бесплатная доставка

    // TODO Заказ c промокодом на бесплатную доставку (unicorn)

    // TODO Заказ c промокодом на фикс. скидку

    // TODO Заказ c промокодом на процентную скидку

    // TODO Заказ c промокодом на процентную скидку с лимитом по сумме (проверить что лимит отрабатывает, нельзя превысить)

    // TODO Негативный кейс на применение промика на первый заказ старым юзером

    // TODO Негативный кейс на применение своего собственного реферального промика

    // TODO Позитивные и негативные кейсы на каждое правило промиков (на магазин, категорию, товар)


public class OrdersPromocodesTests extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().baseUrl();
        kraken.drop().auth();
        User.Do.registration();
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());
    }

    @Test(
            description = "Тест применения промокода со скидкой на первый заказ",
            groups = {"regression"},
            priority = 1401
    )
    public void successOrderWithFirstOrderPromo() {
        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();

        Checkout.Promocode.add(Promos.fixedDiscountOnFirstOrder());

        Assert.assertTrue(kraken.detect().isPromocodeApplied(),
                "Не применяется промокод со скидкой на первый заказ\n");

        kraken.checkout().complete();
    }


    @Test(
            description = "Тест заказа с промокодом на скидку для ритейлера",
            groups = {"regression"},
            priority = 1402
    )
    public void successOrderWithRetailerPromo() {
        kraken.get().page("metro");
        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();

        Checkout.Promocode.add(Promos.fixedDiscountForRetailer("metro"));

        Assert.assertTrue(kraken.detect().isPromocodeApplied(),
                "Не применяется промокод со скидкой ля ритейлера\n");

        kraken.checkout().complete();
    }


    @Test(
            description = "Тест заказа с промокодом на скидку для новых пользователей",
            groups = {"regression"},
            priority = 1403
    )
    public void successOrderWithNewUserPromo () {
        Shop.Search.item("молоко");
        Shop.Cart.collect(2000);
        Shop.Cart.proceedToCheckout();

        Checkout.Promocode.add(Promos.fixedDiscountForNewUser());

        Assert.assertTrue(kraken.detect().isPromocodeApplied(),
                "Не применяется промокод со скидкой для новых пользователей\n");

        kraken.checkout().complete();
    }


    @Test(
            description = "Тест применения промокода на бесплатную доставку на первый заказ",
            groups = {"regression"},
            priority = 1404
    )
    public void successOrderWithCertainOrderPromo() {
        Shop.Cart.collect(2100);
        Shop.Cart.proceedToCheckout();

        //Checkout.Promocode.add("crtnord");
        //Checkout.Promocode.add(Promocodes.);

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
        Shop.Cart.collect(2000);
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        kraken.get().baseUrl();
        Shop.Cart.collect(2000);
        Shop.Cart.proceedToCheckout();
        //Checkout.Promocode.add("srsoford");

        Assert.assertTrue(kraken.detect().isPromocodeApplied(),
                "Не применяется промокод на скидку на второй заказ\n");

        kraken.checkout().complete();
    }

    @AfterMethod(alwaysRun = true)
    public void postconditions() {
        kraken.cleanup().orders();
    }
}




