package ru.instamart.tests.user;

import instamart.core.settings.Config;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.checkpoints.users.ShippingAddressCheckpoints;
import instamart.ui.checkpoints.users.ShoppingCartCheckpoints;
import instamart.ui.checkpoints.users.UsersAuthorizationCheckpoints;
import instamart.ui.common.lib.Addresses;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.TestBase;

import static instamart.core.common.AppManager.session;

public class UserLogoutTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    ShoppingCartCheckpoints shopChecks = new ShoppingCartCheckpoints();
    UsersAuthorizationCheckpoints authChecks = new UsersAuthorizationCheckpoints();
    ShippingAddressCheckpoints shippingChecks = new ShippingAddressCheckpoints();

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void quickLogout() {
        User.Logout.quickly();
    }

    @Test(  description = "Тест успешной быстрой деавторизации",
            priority = 126,
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successQuickLogout() {
        kraken.get().page(Config.DEFAULT_RETAILER);

        User.Do.loginAs(session.admin);
        User.Logout.quickly();
        baseChecks.checkPageIsAvailable();
        kraken.get().page(Config.DEFAULT_RETAILER);
        authChecks.checkIsUserNotAuthorized("Не работает быстрый логаут");
    }

    @Test(  description = "Тест успешной деавторизации",
            priority = 127,
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successManualLogout() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Do.loginAs(session.admin);
        User.Logout.manually();
        baseChecks.checkPageIsAvailable();
        kraken.get().page(Config.DEFAULT_RETAILER);
        authChecks.checkIsUserNotAuthorized("Не работает логаут");
    }

    @Test(  description = "Тест сброса адреса доставки и корзины после деавторизации",
            priority = 128,
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void noShipAddressAndEmptyCartAfterLogout() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Do.loginAs(session.admin);
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
        kraken.get().page(Config.DEFAULT_RETAILER);
        kraken.await().simply(2);
        Shop.Catalog.Item.addToCart();
        User.Logout.manually();
        kraken.get().page(Config.DEFAULT_RETAILER);
        authChecks.checkIsUserNotAuthorized("Не выполнены предусловия - не работает логаут");
        shippingChecks.checkIsShippingAddressNotSet("Логаут");
        kraken.get().page(Config.DEFAULT_RETAILER);
        shopChecks.checkIsCartNotEmpty("Логаут");
    }
}
