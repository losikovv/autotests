package ru.instamart.autotests.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.appmanager.ShopHelper;

import static ru.instamart.autotests.appmanager.ApplicationManager.session;

public class Profile extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() throws Exception {
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
                "Не открывается всплывающее меню профиля\n");

        ShopHelper.AccountMenu.close();

        softAssert.assertFalse(
                kraken.detect().isAccountMenuOpen(),
                "Не закрывается всплывающее меню профиля\n");

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

        // Проверяем наличие элементов
        kraken.check().elementPresence(Elements.Site.AccountMenu.popup());
        kraken.check().elementPresence(Elements.Site.AccountMenu.header());
        kraken.check().elementPresence(Elements.Site.AccountMenu.profileButton());
        kraken.check().elementPresence(Elements.Site.AccountMenu.ordersHistoryButton());
        kraken.check().elementPresence(Elements.Site.AccountMenu.termsButton());
        kraken.check().elementPresence(Elements.Site.AccountMenu.logoutButton());
        kraken.check().elementPresence(Elements.Site.AccountMenu.deliveryButton());
        kraken.check().elementPresence(Elements.Site.AccountMenu.paymentButton());
        kraken.check().elementPresence(Elements.Site.AccountMenu.faqButton());
        kraken.check().elementPresence(Elements.Site.AccountMenu.contactsButton());

        // Валидируем ссылки
        validateTransition(Elements.Site.AccountMenu.profileButton());

        ShopHelper.AccountMenu.open();
        validateTransition(Elements.Site.AccountMenu.ordersHistoryButton());

        ShopHelper.AccountMenu.open();
        validateTransition(Elements.Site.AccountMenu.termsButton());

        ShopHelper.AccountMenu.open();
        kraken.perform().click(Elements.Site.AccountMenu.deliveryButton());
        softAssert.assertTrue(
                kraken.detect().isDeliveryModalOpen(),
                "Не открывается модалка \"Доставка\" из всплывающего меню \"Профиль\"\n");
        kraken.perform().refresh();

        ShopHelper.AccountMenu.open();
        kraken.perform().click(Elements.Site.AccountMenu.paymentButton());
        softAssert.assertTrue(
                kraken.detect().isPaymentModalOpen(),
                "Не открывается модалка \"Оплата\" из всплывающего меню \"Профиль\"\n");
        kraken.perform().refresh();

        ShopHelper.AccountMenu.open();
        validateTransition(Elements.Site.AccountMenu.faqButton());


        ShopHelper.AccountMenu.open();
        validateTransition(Elements.Site.AccountMenu.contactsButton());

        softAssert.assertAll();
    }
}
