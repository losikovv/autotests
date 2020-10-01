package ru.instamart.tests.user;

import instamart.core.helpers.ConsoleOutputCapturerHelper;
import instamart.core.settings.Config;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.checkpoints.users.UsersAuthorizationCheckpoints;
import instamart.ui.common.lib.Addresses;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import io.qameta.allure.Allure;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.tests.TestBase;

import static instamart.core.common.AppManager.session;

public class UserLogoutTests extends TestBase {
    Config config = new Config();
    ConsoleOutputCapturerHelper capture = new ConsoleOutputCapturerHelper();
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    UsersAuthorizationCheckpoints authChecks = new UsersAuthorizationCheckpoints();

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
    }
    @BeforeMethod(alwaysRun = true,
            description ="Проверяем залогинен ли пользователь, если да то завершаем сессию")
    public void quickLogout() {
        capture.start();
        User.Logout.quickly();
    }
    @AfterMethod(alwaysRun = true,
            description ="Добавление сообщений из консоли в лог теста")
    public void afterTest(){
        String value = capture.stop();
        Allure.addAttachment("Системный лог теста",value);
    }

    @Test(  description = "Тест успешной быстрой деавторизации",
            priority = 126,
            groups = {
                    "metro-acceptance", "metro-regression","testing",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successQuickLogout() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);

        User.Do.loginAs(session.admin);
        User.Logout.quickly();
        baseChecks.assertPageIsAvailable();
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        authChecks.checkIsUserNotAuthorized("Не работает быстрый логаут");
    }

    @Test(  description = "Тест успешной деавторизации",
            priority = 127,
            groups = {
                    "metro-acceptance", "metro-regression","testing",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successManualLogout() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);

        User.Do.loginAs(session.admin);
        User.Logout.manually();

        assertPageIsAvailable();

        kraken.get().page(Config.CoreSettings.defaultRetailer);

        Assert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    failMessage("Не работает логаут"));
    }

    @Test(  description = "Тест сброса адреса доставки и корзины после деавторизации",
            priority = 128,
            groups = {
                    "metro-acceptance", "metro-regression","testing",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void noShipAddressAndEmptyCartAfterLogout() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);

        User.Do.loginAs(session.admin);
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());

        kraken.get().page(Config.CoreSettings.defaultRetailer);
        kraken.await().simply(2);
        Shop.Catalog.Item.addToCart();
        User.Logout.manually();

        kraken.get().page(Config.CoreSettings.defaultRetailer);

        Assert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    failMessage("Не выполнены предусловия - не работает логаут"));

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertFalse(
                kraken.detect().isShippingAddressSet(),
                    failMessage("Не сбросился адрес доставки после логаута"));

        softAssert.assertTrue(
                kraken.detect().isCartEmpty(),
                    failMessage("Не сбросилась корзина после логаута"));

        softAssert.assertAll();
    }
}
