package ru.instamart.tests.user;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.Elements;
import ru.instamart.application.lib.Pages;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.tests.TestBase;

public class UserProfileTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.get().baseUrl();
        User.Do.registration();
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().baseUrl();
    }

    @Test(
            description = "Тест валидации меню профиля Delivery Metro",
            priority = 152,
            groups = {
                    "metro-smoke","metro-acceptance","metro-regression"
            }
    ) public void successValidateMetroTenantProfileMenu() {
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
    ) public void successValidateSbermarketTenantProfileMenu() {
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
                    "metro-acceptance","metro-regression",
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
                    "metro-acceptance","metro-regression",
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
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successValidateFaqButton() {
        Shop.AccountMenu.open();

        validateTransition(Elements.AccountMenu.faqButton());
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
        assertPageIsAvailable(Pages.UserProfile.edit());
        assertPageIsAvailable(Pages.UserProfile.favorites());
        assertPageIsAvailable(Pages.UserProfile.shipments());
    }

    @Test(
            description = "Тест валидации дефолтных страниц истории заказов",
            priority = 162,
            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successValidateDefaultOrderHistory() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().userShipmentsPage();

        kraken.perform().click(Elements.UserProfile.OrdersHistoryPage.completeOrdersFilterButton());
        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.UserProfile.OrdersHistoryPage.completeOrdersPlaceholder()));

        kraken.perform().click(Elements.UserProfile.OrdersHistoryPage.activeOrdersFilterButton());
        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.UserProfile.OrdersHistoryPage.activeOrdersPlaceholder()));

        kraken.perform().click(Elements.UserProfile.OrdersHistoryPage.goShoppingButton());
        softAssert.assertFalse(
                kraken.detect().isElementPresent(
                        Elements.UserProfile.OrdersHistoryPage.completeOrdersPlaceholder()));

        softAssert.assertAll();
    }
}
