package ru.instamart.test.ui.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import ru.instamart.kraken.setting.Config;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.util.ThreadUtil;
import ru.instamart.ui.checkpoint.BaseUICheckpoints;
import ru.instamart.ui.checkpoint.shipping.ShippingAddressCheckpoints;
import ru.instamart.ui.checkpoint.shoppingcart.ShoppingCartCheckpoints;
import ru.instamart.ui.checkpoint.users.UsersAuthorizationCheckpoints;
import ru.instamart.kraken.testdata.lib.Addresses;
import ru.instamart.ui.module.Shop;
import ru.instamart.ui.module.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.test.ui.TestBase;
import ru.instamart.ui.module.shop.ShippingAddressModal;

@Epic("STF UI")
@Feature("Деавторизация пользователя")
public class UserLogoutTests extends TestBase implements UsersAuthorizationCheckpoints {
    private static String phone;
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    ShoppingCartCheckpoints shopChecks = new ShoppingCartCheckpoints();
    ShippingAddressCheckpoints shippingChecks = new ShippingAddressCheckpoints();

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void quickLogout() {
        User.Logout.quickly();
    }

    @CaseId(1473)
    @Story("Позитивный кейс")
    @Test(  description = "Тест успешной быстрой деавторизации",
            groups = {
                    "metro-acceptance", "metro-regression","sbermarket-Ui-smoke"
            }
    ) public void successQuickLogout() {
        phone = Generate.phoneNumber();
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.AuthModal.openAuthRetailer();
        User.Do.registration(phone,true);
        User.Do.sendSms(Config.DEFAULT_SMS);
        baseChecks.checkPageIsAvailable();
        checkIsUserAuthorized("Юзер не авторизован на сайте");
        User.Logout.quickly();
        kraken.get().page(Config.DEFAULT_RETAILER);
        checkIsUserNotAuthorized("Не работает быстрый логаут");
    }

    @CaseId(1474)
    @Story("Позитивный кейс")
    @Test(  description = "Тест успешной деавторизации",
            groups = {
                    "metro-acceptance", "metro-regression","sbermarket-Ui-smoke"
            }
    ) public void successManualLogout() {
        phone = Generate.phoneNumber();
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.AuthModal.openAuthRetailer();
        User.Do.registration(phone,true);
        User.Do.sendSms(Config.DEFAULT_SMS);
        baseChecks.checkPageIsAvailable();
        checkIsUserAuthorized("Юзер не авторизован на сайте");
        User.Do.logoutOnSite();
        baseChecks.checkPageIsAvailable();
        kraken.get().page(Config.DEFAULT_RETAILER);
        checkIsUserNotAuthorized("Не работает логаут");
    }

    @CaseId(1475)
    @Story("Позитивный кейс")
    @Test(  description = "Тест сброса адреса доставки и корзины после деавторизации",
            groups = {
                    "metro-acceptance", "metro-regression","sbermarket-Ui-smoke"
            }
    ) public void noShipAddressAndEmptyCartAfterLogout() {
        phone = Generate.phoneNumber();
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.AuthModal.openAuthRetailer();
        User.Do.registration(phone,true);
        User.Do.sendSms(Config.DEFAULT_SMS);
        checkIsUserAuthorized("Юзер не авторизован на сайте");
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.selectAddressSuggest();
        ShippingAddressModal.submit();
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.Catalog.Item.addToCart();
        User.Do.logoutOnSite();
        checkIsUserNotAuthorized("Не выполнены предусловия - не работает логаут");
        shippingChecks.checkIsShippingAddressNotSet("Логаут");
        kraken.get().page(Config.DEFAULT_RETAILER);
        shopChecks.checkIsCartNotEmpty("Логаут");
    }
}
