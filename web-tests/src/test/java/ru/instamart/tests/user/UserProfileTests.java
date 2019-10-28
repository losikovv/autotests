package ru.instamart.tests.user;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.application.Tenants;
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

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().baseUrl();
    }

    @Test(
            description = "Тест валидации меню профиля Instamart",
            priority = 151,
            groups = {
                    "smoke","acceptance","regression",
            }
    ) public void successValidateProfileMenuInstamart() {
        SoftAssert softAssert = new SoftAssert();

        Shop.AccountMenu.open();

        softAssert.assertTrue(
                kraken.detect().isAccountMenuOpen(),
                    failMessage("Не открывается всплывающее меню профиля Instamart"));

        assertPresence(Elements.AccountMenu.popup());
        assertPresence(Elements.AccountMenu.header());
        assertPresence(Elements.AccountMenu.profileButton());
        assertPresence(Elements.AccountMenu.ordersHistoryButton());
        assertPresence(Elements.AccountMenu.termsButton());
        assertPresence(Elements.AccountMenu.logoutButton());
        assertPresence(Elements.AccountMenu.deliveryButton());
        assertPresence(Elements.AccountMenu.faqButton());
        assertPresence(Elements.AccountMenu.contactsButton());

        Shop.AccountMenu.close();

        softAssert.assertFalse(
                kraken.detect().isAccountMenuOpen(),
                    failMessage("Не закрывается всплывающее меню профиля Instamart"));

        softAssert.assertAll();
    }

    @Test(
            description = "Тест валидации меню профиля Delivery Metro",
            priority = 152,
            groups = {
                    "metro-smoke","metro-acceptance","metro-regression"
            }
    ) public void successValidateProfileMenuDeliveryMetro() {
        SoftAssert softAssert = new SoftAssert();

        Shop.AccountMenu.open();

        softAssert.assertTrue(
                kraken.detect().isAccountMenuOpen(),
                    failMessage("Не открывается всплывающее меню профиля Delivery Metro"));

        assertPresence(Elements.AccountMenu.popup());
        assertPresence(Elements.AccountMenu.header());
        assertPresence(Elements.AccountMenu.profileButton());
        assertPresence(Elements.AccountMenu.ordersHistoryButton());
        assertPresence(Elements.AccountMenu.termsButton());
        assertPresence(Elements.AccountMenu.logoutButton());
        assertPresence(Elements.AccountMenu.deliveryButton());
        assertPresence(Elements.AccountMenu.paymentButton());
        assertPresence(Elements.AccountMenu.faqButton());
        assertAbsence(Elements.AccountMenu.contactsButton());

        Shop.AccountMenu.close();

        softAssert.assertFalse(
                kraken.detect().isAccountMenuOpen(),
                    failMessage("Не закрывается всплывающее меню профиля Delivery Metro"));

        softAssert.assertAll();
    }

    @Test(
            description = "Тест валидации меню профиля Sbermarket",
            priority = 153,
            groups = {
                    "sbermarket-smoke","sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successValidateProfileMenuSbermarket() {
        SoftAssert softAssert = new SoftAssert();

        Shop.AccountMenu.open();

        softAssert.assertTrue(
                kraken.detect().isAccountMenuOpen(),
                    failMessage("Не открывается всплывающее меню профиля Sbermarket"));

        assertPresence(Elements.AccountMenu.popup());
        assertPresence(Elements.AccountMenu.header());
        assertPresence(Elements.AccountMenu.profileButton());
        assertPresence(Elements.AccountMenu.ordersHistoryButton());
        assertPresence(Elements.AccountMenu.termsButton());
        assertPresence(Elements.AccountMenu.logoutButton());
        assertPresence(Elements.AccountMenu.deliveryButton());
        assertPresence(Elements.AccountMenu.faqButton());

        Shop.AccountMenu.close();

        softAssert.assertFalse(
                kraken.detect().isAccountMenuOpen(),
                    failMessage("Не закрывается всплывающее меню профиля Sbermarket"));

        softAssert.assertAll();
    }

    @Test(
            description = "Тест валидации кнопки 'Профиль' в меню профиля",
            priority = 154,
            groups = {
                    "acceptance","regression",
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successValidateUserProfileButton() {
        Shop.AccountMenu.open();

        validateTransition(Elements.AccountMenu.profileButton());
    }

    @Test(
            description = "Тест валидации кнопки 'История заказов' в меню профиля",
            priority = 155,
            groups = {
                    "acceptance","regression",
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successValidateOrdersHistoryButton() {
        Shop.AccountMenu.open();

        validateTransition(Elements.AccountMenu.ordersHistoryButton());
    }

    @Test(
            description = "Тест валидации кнопки 'Условия использования' в меню профиля",
            priority = 156,
            groups = {
                    "acceptance","regression",
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successValidateTermsButton() {
        Shop.AccountMenu.open();

        validateTransition(Elements.AccountMenu.termsButton());
    }

    @Test(
            description = "Тест валидации кнопки 'Доставка' в меню профиля",
            priority = 157,
            groups = {
                    "acceptance","regression",
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successValidateDeliveryButton() {
        Shop.AccountMenu.open();

        kraken.perform().click(Elements.AccountMenu.deliveryButton());

        Assert.assertTrue(
                kraken.detect().isDeliveryModalOpen(),
                    failMessage("Не открывается модалка 'Доставка' из всплывающего меню 'Профиль'"));
    }

    @Test(
            description = "Тест валидации кнопки 'Оплата' в меню профиля",
            priority = 158,
            groups = {
                    "metro-smoke","metro-acceptance","metro-regression",
            }
    ) public void successValidatePaymentButton() {
        Shop.AccountMenu.open();

        kraken.perform().click(Elements.AccountMenu.paymentButton());

        Assert.assertTrue(
                kraken.detect().isPaymentModalOpen(),
                    failMessage("Не открывается модалка 'Оплата' из всплывающего меню 'Профиль'"));
    }

    @Test(
            description = "Тест валидации кнопки 'FAQ' в меню профиля",
            priority = 159,
            groups = {
                    "acceptance","regression",
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successValidateFaqButton() {
        Shop.AccountMenu.open();

        validateTransition(Elements.AccountMenu.faqButton());
    }

    @Test(
            description = "Тест валидации кнопки 'Контакты' в меню профиля",
            priority = 160,
            groups = {
                    "acceptance","regression",
            }
    ) public void successValidateContactsButton() {
        skipTestOn(Tenants.metro());
        skipTestOn(Tenants.sbermarket());

        Shop.AccountMenu.open();

        validateTransition(Elements.AccountMenu.contactsButton());
    }

    @Test(
            description = "Тест доступности страниц профиля пользователя",
            priority = 161,
            groups = {
                    "smoke","acceptance","regression",
                    "metro-smoke","metro-acceptance","metro-regression",
                    "sbermarket-smoke","sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successCheckProfilePagesAreAvailable() {
        // TODO переделать на assertPagesAvailable(Pages.Site.Profile.*)
        assertPageIsAvailable(Pages.Site.Profile.edit());
        assertPageIsAvailable(Pages.Site.Profile.favorites());
        assertPageIsAvailable(Pages.Site.Profile.orders());
        assertPageIsAvailable(Pages.Site.Profile.addresses());
    }
}
