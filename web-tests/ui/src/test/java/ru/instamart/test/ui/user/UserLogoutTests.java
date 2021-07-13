package ru.instamart.test.ui.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.setting.Config;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.lib.Addresses;
import ru.instamart.test.ui.TestBase;
import ru.instamart.ui.checkpoint.BaseUICheckpoints;
import ru.instamart.ui.checkpoint.shipping.ShippingAddressCheckpoints;
import ru.instamart.ui.checkpoint.shoppingcart.ShoppingCartCheckpoints;
import ru.instamart.ui.checkpoint.users.UsersAuthorizationCheckpoints;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.ui.module.Shop;
import ru.instamart.ui.module.User;
import ru.instamart.ui.module.shop.ShippingAddressModal;

@Epic("STF UI")
@Feature("Деавторизация пользователя")
public final class UserLogoutTests extends TestBase implements UsersAuthorizationCheckpoints {

    private final BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    private final ShoppingCartCheckpoints shopChecks = new ShoppingCartCheckpoints();
    private final ShippingAddressCheckpoints shippingChecks = new ShippingAddressCheckpoints();

    @BeforeMethod(alwaysRun = true,
            description = "Выполняем шаги предусловий для теста")
    public void quickLogout() {
        AppManager.closeWebDriver();
    }

    @CaseId(1473)
    @Story("Позитивный кейс")
    @Test(  description = "Тест успешной быстрой деавторизации",
            groups = {
                    "metro-acceptance", "metro-regression","sbermarket-Ui-smoke"
            }
    )
    public void successQuickLogout() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        log.info("Browser session id: {}", ((RemoteWebDriver) AppManager.getWebDriver()).getSessionId());
        Shop.AuthModal.openAuthRetailer();
        User.Do.registration(Generate.phoneNumber(),true);
        User.Do.sendSms(Config.DEFAULT_SMS);
        baseChecks.checkPageIsAvailable();
        checkIsUserAuthorized("Юзер не авторизован на сайте");
        User.Logout.jsLogout();
        checkIsUserNotAuthorized("Не работает быстрый логаут");
    }

    @CaseId(1474)
    @Story("Позитивный кейс")
    @Test(  description = "Тест успешной деавторизации",
            groups = {
                    "metro-acceptance", "metro-regression","sbermarket-Ui-smoke"
            }
    )
    public void successManualLogout() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.AuthModal.openAuthRetailer();
        User.Do.registration(Generate.phoneNumber(),true);
        User.Do.sendSms(Config.DEFAULT_SMS);
        baseChecks.checkPageIsAvailable();
        checkIsUserAuthorized("Юзер не авторизован на сайте");
        User.Do.logoutOnSite();
        baseChecks.checkPageIsAvailable();
        kraken.get().page(Config.DEFAULT_RETAILER);
        checkIsUserNotAuthorized("Не работает логаут");
    }

    @Skip
    @CaseId(1475)
    @Story("Позитивный кейс")
    @Test(  description = "Тест сброса адреса доставки и корзины после деавторизации",
            groups = {
                    "metro-acceptance", "metro-regression","sbermarket-Ui-smoke"
            }
    )
    public void noShipAddressAndEmptyCartAfterLogout() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.AuthModal.openAuthRetailer();
        User.Do.registration(Generate.phoneNumber(),true);
        User.Do.sendSms(Config.DEFAULT_SMS);
        checkIsUserAuthorized("Юзер не авторизован на сайте");
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.selectAddressSuggest();
        ShippingAddressModal.submit();
        Shop.Catalog.Item.addToCart();
        User.Logout.jsLogout();
        checkIsUserNotAuthorized("Не выполнены предусловия - не работает логаут");
        shippingChecks.checkIsShippingAddressNotSet("Логаут");
        kraken.get().page(Config.DEFAULT_RETAILER);
        shopChecks.checkIsCartNotEmpty("Логаут");
    }
}
