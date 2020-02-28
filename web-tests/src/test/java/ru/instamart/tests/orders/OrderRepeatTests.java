package ru.instamart.tests.orders;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.application.AppManager;
import ru.instamart.application.lib.PaymentTypes;
import ru.instamart.application.platform.modules.Shop;
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

    @Test(enabled = enableOrderRepeatTests,
            description = "Повтор крайнего заказа из истории заказов",
            groups = {"sbermarket-regression", "sbermarket-acceptance"},
            priority = 2005
    )
    public void successRepeatLastOrderFromOrderHistory() {
        kraken.perform().repeatLastOrder();

        Assert.assertTrue(kraken.detect().isCartOpen(),
                "Не открылась корзина после повтора крайнего заказа\n");

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не добавились товары в корзину после повтора крайнего заказа\n");
    }
    
}
