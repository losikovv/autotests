package ru.instamart.tests.orders;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.application.AppManager;
import ru.instamart.application.Elements;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.rest.RestAddresses;
import ru.instamart.tests.TestBase;

import static ru.instamart.application.Config.TestsConfiguration.OrdersTests.enableOrderRepeatTests;

public class OrderRepeatTests extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().baseUrl();
        User.Do.loginAs(AppManager.session.admin);
        kraken.rest().dropCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress());
    }

    @Test(
            enabled = enableOrderRepeatTests,
            description = "Повтор крайнего заказа из истории заказов",
            priority = 2005,
            groups = {
                    "sbermarket-regression", "sbermarket-acceptance",
                    "metro-regression", "metro-acceptance",
                    "lenta-regression", "lenta-acceptance"}
    )
    public void successRepeatLastOrderFromOrderHistory() {
        kraken.perform().repeatLastOrder();

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не добавились товары в корзину после повтора крайнего заказа\n");
    }

    @Test(
            enabled = enableOrderRepeatTests,
            description = "Повтор крайнего заказа со страницы заказа",
            priority = 2006,
            groups = {
                    "sbermarket-regression", "sbermarket-acceptance",
                    "metro-regression", "metro-acceptance",
                    "lenta-regression", "lenta-acceptance"}
    )
    public void successRepeatOrderFromOrderDetails() {
        kraken.get().userShipmentsPage();
        kraken.perform().click(Elements.UserProfile.OrdersHistoryPage.order.snippet());
        kraken.perform().repeatOrder();

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не добавились товары в корзину после повтора крайнего заказа\n");
    }

    @Test(
            enabled = enableOrderRepeatTests,
            description = "Отмена повтора заказа со страницы заказа",
            priority = 2007,
            groups = {
                    "sbermarket-regression", "sbermarket-acceptance",
                    "metro-regression", "metro-acceptance",
                    "lenta-regression", "lenta-acceptance"}
    )
    public void noRepeatOrderAfterCancel() {
        kraken.get().userShipmentsPage();
        kraken.perform().click(Elements.UserProfile.OrdersHistoryPage.order.snippet());
        kraken.perform().click(Elements.UserProfile.OrderDetailsPage.OrderSummary.repeatOrderButton());
        kraken.await().simply(1); // Ожидание анимации открытия модалки повтора заказа
        kraken.perform().click(Elements.UserProfile.OrderDetailsPage.RepeatOrderModal.noButton());

        Assert.assertTrue(kraken.detect().isCartEmpty(),
                "Сработал повтор заказ после отмены в модалке повтора\n");
    }

    @Test(
            enabled = enableOrderRepeatTests,
            description = "Отмена повтора заказа со страницы заказа",
            priority = 2008,
            groups = {
                    "sbermarket-regression", "sbermarket-acceptance",
                    "metro-regression", "metro-acceptance",
                    "lenta-regression", "lenta-acceptance"}
    )
    public void noRepeatOrderAfterModalClose() {
        kraken.get().userShipmentsPage();
        kraken.perform().click(Elements.UserProfile.OrdersHistoryPage.order.snippet());
        kraken.perform().click(Elements.UserProfile.OrderDetailsPage.OrderSummary.repeatOrderButton());
        kraken.await().simply(1); // Ожидание анимации открытия модалки повтора заказа
        kraken.perform().click(Elements.UserProfile.OrderDetailsPage.RepeatOrderModal.closeButton());

        Assert.assertTrue(kraken.detect().isCartEmpty(),
                "Сработал повтор заказ после закрытия модалки повтора\n");
    }
}
