package ru.instamart.tests.user;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.application.lib.Addresses;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.platform.modules.User;
import ru.instamart.tests.TestBase;

import static ru.instamart.application.AppManager.session;

public class UserLogoutTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
    }

    @Test(  description = "Тест успешной быстрой деавторизации",
            priority = 126,
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successQuickLogout() {
        kraken.get().page("metro");

        User.Do.loginAs(session.admin);
        User.Logout.quickly();

        assertPageIsAvailable();

        kraken.get().page("metro");

        Assert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    failMessage("Не работает бысрый логаут"));
    }

    @Test(  description = "Тест успешной деавторизации",
            priority = 127,
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successManualLogout() {
        kraken.get().page("metro");

        User.Do.loginAs(session.admin);
        User.Logout.manually();

        assertPageIsAvailable();

        kraken.get().page("metro");

        Assert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    failMessage("Не работает логаут"));
    }

    @Test(  description = "Тест сброса адреса доставки и корзины после деавторизации",
            priority = 128,
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void noShipAddressAndEmptyCartAfterLogout() {
        kraken.get().page("metro");

        User.Do.loginAs(session.admin);
        User.ShippingAddress.change(Addresses.Moscow.defaultAddress());

        kraken.get().page("metro");
        kraken.await().simply(2);
        Shop.Catalog.Item.addToCart();
        User.Logout.manually();

        kraken.get().page("metro");

        Assert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    failMessage("Не выполнены предусловия - не работает логаут"));

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    failMessage("Не сбросился адрес доставки после логаута"));

        softAssert.assertTrue(
                kraken.detect().isCartEmpty(),
                    failMessage("Не сбросилась корзина после логаута"));

        softAssert.assertAll();
    }
}
