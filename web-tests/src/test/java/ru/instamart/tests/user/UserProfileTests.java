package ru.instamart.tests.user;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.Elements;
import ru.instamart.application.lib.Pages;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.tests.TestBase;

import static ru.instamart.application.AppManager.session;

public class UserProfileTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.get().baseUrl();
        User.Do.loginAs(session.admin);
    }

    @Test(
            description = "Тест доступности страниц профиля пользователя",
            groups = {"smoke","acceptance","regression"},
            priority = 151
    )
    public void successCheckProfilePages() throws AssertionError {

        // TODO переделать на assertPagesAvailable(Pages.Site.Profile.*)
        assertPageIsAvailable(Pages.Site.Profile.edit());
        assertPageIsAvailable(Pages.Site.Profile.favorites());
        assertPageIsAvailable(Pages.Site.Profile.orders());
        assertPageIsAvailable(Pages.Site.Profile.addresses());
    }

    @Test(
            description = "Тест работы с меню профиля",
            groups = {"smoke","acceptance","regression"},
            priority = 152
    )
    public void successOperateProfileMenu() throws AssertionError {
        SoftAssert softAssert = new SoftAssert();

        Shop.AccountMenu.open();

        softAssert.assertTrue(
                kraken.detect().isAccountMenuOpen(),
                    failMessage("Не открывается всплывающее меню профиля"));

        Shop.AccountMenu.close();

        softAssert.assertFalse(
                kraken.detect().isAccountMenuOpen(),
                    failMessage("Не закрывается всплывающее меню профиля"));

        softAssert.assertAll();
    }

    @Test(
            description = "Тест валидации элементов в меню профиля",
            groups = {"smoke","acceptance","regression"},
            priority = 153
    )
    public void successValidateProfileMenu() throws AssertionError {
        SoftAssert softAssert = new SoftAssert();

        Shop.AccountMenu.open();

        assertElementPresence(Elements.AccountMenu.popup());
        assertElementPresence(Elements.AccountMenu.header());
        assertElementPresence(Elements.AccountMenu.profileButton());
        assertElementPresence(Elements.AccountMenu.ordersHistoryButton());
        assertElementPresence(Elements.AccountMenu.termsButton());
        assertElementPresence(Elements.AccountMenu.logoutButton());
        assertElementPresence(Elements.AccountMenu.deliveryButton());
        assertElementPresence(Elements.AccountMenu.paymentButton());
        assertElementPresence(Elements.AccountMenu.faqButton());
        assertElementPresence(Elements.AccountMenu.contactsButton());

        // Валидируем ссылки
        validateTransition(Elements.AccountMenu.profileButton());

        Shop.AccountMenu.open();
        validateTransition(Elements.AccountMenu.ordersHistoryButton());

        Shop.AccountMenu.open();
        validateTransition(Elements.AccountMenu.termsButton());

        Shop.AccountMenu.open();
        kraken.perform().click(Elements.AccountMenu.deliveryButton());

        softAssert.assertTrue(
                kraken.detect().isDeliveryModalOpen(),
                    failMessage("Не открывается модалка 'Доставка' из всплывающего меню 'Профиль'"));

        kraken.perform().refresh();

        Shop.AccountMenu.open();
        kraken.perform().click(Elements.AccountMenu.paymentButton());

        softAssert.assertTrue(
                kraken.detect().isPaymentModalOpen(),
                    failMessage("Не открывается модалка 'Оплата' из всплывающего меню 'Профиль'"));

        kraken.perform().refresh();

        Shop.AccountMenu.open();
        validateTransition(Elements.AccountMenu.faqButton());

        Shop.AccountMenu.open();
        validateTransition(Elements.AccountMenu.contactsButton());

        softAssert.assertAll();
    }
}
