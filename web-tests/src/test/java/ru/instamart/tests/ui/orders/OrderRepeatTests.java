package ru.instamart.tests.ui.orders;

import ru.instamart.api.common.RestAddresses;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.ui.modules.User;
import ru.instamart.ui.modules.shop.Order;
import ru.instamart.ui.objectsmap.Elements;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

public class OrderRepeatTests extends TestBase {

    @BeforeMethod(alwaysRun = true,
            description ="Проверяем залогинен ли пользователь, если да то завершаем сессию")
    public void preconditions() {
        kraken.get().baseUrl();
        User.Do.loginAs(UserManager.getDefaultAdmin());
        kraken.apiV2().dropCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress());
    }
    @AfterMethod(alwaysRun = true,
            description ="Очистка окружения после теста")
    public void afterTest(ITestResult result){
        Order.cancelLastActiveOrder();//TODO Проверяем нужно ли это здесь
    }

    @Test(
            description = "Повтор крайнего заказа из истории заказов",

            groups = {
                    "sbermarket-regression", "sbermarket-acceptance",
                    "metro-regression", "metro-acceptance",
                    "lenta-regression", "lenta-acceptance"}
    )
    public void successRepeatLastOrderFromOrderHistory() {
        Order.repeatLastOrder();
        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не добавились товары в корзину после повтора крайнего заказа\n");
    }

    @Test(
            description = "Повтор крайнего заказа со страницы заказа",

            groups = {
                    "sbermarket-regression", "sbermarket-acceptance",
                    "metro-regression", "metro-acceptance",
                    "lenta-regression", "lenta-acceptance"}
    )
    public void successRepeatOrderFromOrderDetails() {
        kraken.get().userShipmentsPage();
        kraken.perform().click(Elements.UserProfile.OrdersHistoryPage.order.snippet());
        Order.repeatOrder();

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не добавились товары в корзину после повтора крайнего заказа\n");
    }

    @Test(
            description = "Отмена повтора заказа со страницы заказа",

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
            description = "Отмена повтора заказа со страницы заказа",

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
