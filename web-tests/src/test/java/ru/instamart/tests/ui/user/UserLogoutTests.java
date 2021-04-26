package ru.instamart.tests.ui.user;

import ru.instamart.core.settings.Config;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.ui.checkpoints.BaseUICheckpoints;
import ru.instamart.ui.checkpoints.shipping.ShippingAddressCheckpoints;
import ru.instamart.ui.checkpoints.shoppingcart.ShoppingCartCheckpoints;
import ru.instamart.ui.checkpoints.users.UsersAuthorizationCheckpoints;
import ru.instamart.ui.common.lib.Addresses;
import ru.instamart.ui.modules.Shop;
import ru.instamart.ui.modules.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;
import ru.instamart.ui.modules.shop.ShippingAddressModal;

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
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.submit();
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
