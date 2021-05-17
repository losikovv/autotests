package ru.instamart.ui.user;

import ru.instamart.core.setting.Config;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.core.util.ThreadUtil;
import ru.instamart.ui.checkpoint.BaseUICheckpoints;
import ru.instamart.ui.checkpoint.shipping.ShippingAddressCheckpoints;
import ru.instamart.ui.checkpoint.shoppingcart.ShoppingCartCheckpoints;
import ru.instamart.ui.checkpoint.users.UsersAuthorizationCheckpoints;
import ru.instamart.core.testdata.lib.Addresses;
import ru.instamart.ui.helper.WaitingHelper;
import ru.instamart.ui.module.Shop;
import ru.instamart.ui.module.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.ui.TestBase;
import ru.instamart.ui.module.shop.ShippingAddressModal;

public class UserLogoutTests extends TestBase implements UsersAuthorizationCheckpoints {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    ShoppingCartCheckpoints shopChecks = new ShoppingCartCheckpoints();
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
        checkIsUserNotAuthorized("Не работает быстрый логаут");
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
        checkIsUserNotAuthorized("Не работает логаут");
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
        ThreadUtil.simply(2);
        Shop.Catalog.Item.addToCart();
        User.Logout.manually();
        kraken.get().page(Config.DEFAULT_RETAILER);
        checkIsUserNotAuthorized("Не выполнены предусловия - не работает логаут");
        shippingChecks.checkIsShippingAddressNotSet("Логаут");
        kraken.get().page(Config.DEFAULT_RETAILER);
        shopChecks.checkIsCartNotEmpty("Логаут");
    }
}
