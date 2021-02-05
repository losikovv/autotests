package ru.instamart.tests.ui.user;

import instamart.core.settings.Config;
import instamart.core.testdata.UserManager;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.checkpoints.users.ShippingAddressCheckpoints;
import instamart.ui.checkpoints.users.ShoppingCartCheckpoints;
import instamart.ui.checkpoints.users.UsersAuthorizationCheckpoints;
import instamart.ui.common.lib.Addresses;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

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

            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successQuickLogout() {
        kraken.get().page(Config.DEFAULT_RETAILER);

        User.Do.loginAs(UserManager.getDefaultAdmin());
        User.Logout.quickly();
        baseChecks.checkPageIsAvailable();
        kraken.get().page(Config.DEFAULT_RETAILER);
        authChecks.checkIsUserNotAuthorized("Не работает быстрый логаут");
    }

    @Test(  description = "Тест успешной деавторизации",

            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successManualLogout() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Do.loginAs(UserManager.getDefaultAdmin());
        User.Logout.manually();
        baseChecks.checkPageIsAvailable();
        kraken.get().page(Config.DEFAULT_RETAILER);
        authChecks.checkIsUserNotAuthorized("Не работает логаут");
    }

    @Test(  description = "Тест сброса адреса доставки и корзины после деавторизации",

            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void noShipAddressAndEmptyCartAfterLogout() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Do.loginAs(UserManager.getDefaultAdmin());
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
