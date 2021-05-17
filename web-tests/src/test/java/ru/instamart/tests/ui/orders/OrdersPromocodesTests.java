package ru.instamart.tests.ui.orders;

import ru.instamart.api.common.RestAddresses;
import ru.instamart.ui.data.lib.Promos;
import ru.instamart.ui.data.pagesdata.UserData;
import ru.instamart.ui.module.Shop;
import ru.instamart.ui.module.User;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;
import ru.instamart.ui.module.checkout.PromocodeActions;

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
    private UserData user;

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void preconditions() {
        User.Logout.quickly();
        this.user = User.Do.registration();
        kraken.apiV2().fillCart(this.user, RestAddresses.Moscow.defaultAddress());
    }

    @AfterMethod(alwaysRun = true,
            description ="Очищаем окружение после теста")
    public void afterTest(ITestResult result){kraken.apiV2().cancelCurrentOrder();
    }

    @Test(
            description = "Тест применения промокода со скидкой на первый заказ",
            groups = {"sbermarket-regression"}
    )
    public void successOrderWithFirstOrderPromo() {
        Shop.Cart.proceedToCheckout();

        PromocodeActions.add(Promos.fixedDiscountOnFirstOrder());

        Assert.assertTrue(kraken.detect().isPromocodeApplied(),
                "Не применяется промокод со скидкой на первый заказ\n");

        kraken.checkout().complete();
    }


    @Test(
            description = "Тест заказа с промокодом на скидку для ритейлера",
            groups = {"sbermarket-regression"}
    )
    public void successOrderWithRetailerPromo() {
        Shop.Cart.proceedToCheckout();

        PromocodeActions.add(Promos.fixedDiscountForRetailer("metro"));

        Assert.assertTrue(kraken.detect().isPromocodeApplied(),
                "Не применяется промокод со скидкой ля ритейлера\n");

        kraken.checkout().complete();
    }


    @Test(
            description = "Тест заказа с промокодом на скидку для новых пользователей",
            groups = {"sbermarket-regression"}
    )
    public void successOrderWithNewUserPromo () {
        Shop.Cart.proceedToCheckout();

        PromocodeActions.add(Promos.fixedDiscountForNewUser());

        Assert.assertTrue(kraken.detect().isPromocodeApplied(),
                "Не применяется промокод со скидкой для новых пользователей\n");

        kraken.checkout().complete();
    }


    @Test(
            description = "Тест применения промокода на бесплатную доставку на первый заказ",
            groups = {"sbermarket-regression"}
    )
    public void successOrderWithCertainOrderPromo() {
        Shop.Cart.proceedToCheckout();

        //PromocodeSteps.add("crtnord");
        //PromocodeSteps.add(Promocodes.);

        Assert.assertTrue(kraken.detect().isPromocodeApplied(),
                "Не применяется промокод на бесплатную доставку на первый заказ\n");

        kraken.checkout().complete();
    }


    @Test(
            description = "Тест применения промокода на скидку на второй заказ",
            groups = {"sbermarket-regression"}
    )
    public void successOrderWithSeriesOfOrdersPromo() {
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        kraken.apiV2().fillCart(this.user, RestAddresses.Moscow.defaultAddress());
        Shop.Cart.proceedToCheckout();
        //PromocodeSteps.add("srsoford");

        Assert.assertTrue(kraken.detect().isPromocodeApplied(),
                "Не применяется промокод на скидку на второй заказ\n");

        kraken.checkout().complete();
    }
}




