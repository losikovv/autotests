package ru.instamart.autotests.tests.user;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.appmanager.ShopHelper;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.appmanager.ApplicationManager.session;

public class UserProfileTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.get().baseUrl();
        kraken.perform().loginAs(session.admin);
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

        ShopHelper.AccountMenu.open();

        softAssert.assertTrue(
                kraken.detect().isAccountMenuOpen(),
                    failMessage("Не открывается всплывающее меню профиля"));

        ShopHelper.AccountMenu.close();

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

        ShopHelper.AccountMenu.open();

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

        ShopHelper.AccountMenu.open();
        validateTransition(Elements.AccountMenu.ordersHistoryButton());

        ShopHelper.AccountMenu.open();
        validateTransition(Elements.AccountMenu.termsButton());

        ShopHelper.AccountMenu.open();
        kraken.perform().click(Elements.AccountMenu.deliveryButton());

        softAssert.assertTrue(
                kraken.detect().isDeliveryModalOpen(),
                    failMessage("Не открывается модалка 'Доставка' из всплывающего меню 'Профиль'"));

        kraken.perform().refresh();

        ShopHelper.AccountMenu.open();
        kraken.perform().click(Elements.AccountMenu.paymentButton());

        softAssert.assertTrue(
                kraken.detect().isPaymentModalOpen(),
                    failMessage("Не открывается модалка 'Оплата' из всплывающего меню 'Профиль'"));

        kraken.perform().refresh();

        ShopHelper.AccountMenu.open();
        validateTransition(Elements.AccountMenu.faqButton());

        ShopHelper.AccountMenu.open();
        validateTransition(Elements.AccountMenu.contactsButton());

        softAssert.assertAll();
    }
}
