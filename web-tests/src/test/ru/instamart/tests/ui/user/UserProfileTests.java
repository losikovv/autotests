package ru.instamart.tests.ui.user;

import instamart.core.settings.Config;
import instamart.core.testdata.ui.Generate;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.checkpoints.users.AccountMenuCheckpoints;
import instamart.ui.common.lib.Pages;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import io.qase.api.annotation.CaseId;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.tests.ui.TestBase;

public class UserProfileTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    AccountMenuCheckpoints accountChecks = new AccountMenuCheckpoints();

    @BeforeClass(alwaysRun = true,
            description ="Проверяем залогинен ли пользователь, если да то завершаем сессию")
    public void quickLogout() {
        User.Logout.quickly();
        String phone = Generate.phoneNumber();
        User.Do.registration(
                null,
                "test@example.com",
                "12345678",
                "12345678",
                phone,
                "1111"
        );
    }


    @Test(
            description = "Тест валидации меню профиля Delivery Metro",

            groups = {
                    "metro-smoke","metro-acceptance","metro-regression"
            }
    ) public void successValidateMetroTenantProfileMenu() {
        SoftAssert softAssert = new SoftAssert();

        Shop.AccountMenu.open();

        softAssert.assertTrue(
                kraken.detect().isAccountMenuOpen(),
                    failMessage("Не открывается всплывающее меню профиля Delivery Metro"));

        baseChecks.checkIsElementPresent(Elements.AccountMenu.popup());
        baseChecks.checkIsElementPresent(Elements.AccountMenu.header());
        baseChecks.checkIsElementPresent(Elements.AccountMenu.profileButton());
        baseChecks.checkIsElementPresent(Elements.AccountMenu.ordersHistoryButton());
        baseChecks.checkIsElementPresent(Elements.AccountMenu.termsButton());
        baseChecks.checkIsElementPresent(Elements.AccountMenu.logoutButton());
        baseChecks.checkIsElementPresent(Elements.AccountMenu.deliveryButton());
        baseChecks.checkIsElementPresent(Elements.AccountMenu.paymentButton());
        baseChecks.checkIsElementPresent(Elements.AccountMenu.faqButton());
        baseChecks.checkElementAbsence(Elements.AccountMenu.contactsButton());

        Shop.AccountMenu.close();

        softAssert.assertFalse(
                kraken.detect().isAccountMenuOpen(),
                    failMessage("Не закрывается всплывающее меню профиля Delivery Metro"));

        softAssert.assertAll();
    }
    @CaseId(1524)
    @Test(
            description = "Тест валидации меню профиля Sbermarket",

            groups = {
                    "sbermarket-Ui-smoke","testing"
            }
    ) public void successValidateSbermarketTenantProfileMenu() {
        Shop.AccountMenu.open();
        accountChecks.checkIsAccountMenuOpen();
        baseChecks.checkIsElementPresent(Elements.AccountMenu.popup());
        baseChecks.checkIsElementPresent(Elements.AccountMenu.header());
        baseChecks.checkIsElementPresent(Elements.AccountMenu.logoutButton());
        baseChecks.checkIsElementPresent(Elements.AccountMenu.profileButton());
        baseChecks.checkIsElementPresent(Elements.AccountMenu.termsButton());
        baseChecks.checkIsElementPresent(Elements.AccountMenu.deliveryButton());
        baseChecks.checkIsElementPresent(Elements.AccountMenu.faqButton());
        Shop.AccountMenu.close();
        accountChecks.checkIsAccountMenuClosed();
    }

    @CaseId(1525)
    @Test(
            description = "Тест валидации кнопки 'Профиль' в меню профиля",

            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-Ui-smoke","testing"
            }
    ) public void successValidateUserProfileButton() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.AccountMenu.open();
        baseChecks.checkTransitionValidation(Elements.AccountMenu.profileButton());
    }

    @CaseId(1526)
    @Test(
            description = "Тест валидации кнопки 'История заказов' в меню профиля",

            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
            )
    @Deprecated
        public void successValidateOrdersHistoryButton() {
        Shop.AccountMenu.open();
        baseChecks.checkTransitionValidation(Elements.AccountMenu.ordersHistoryButton());
    }

    @Test(
            description = "Тест валидации кнопки 'Условия использования' в меню профиля",

            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","testing"
            }
    ) public void successValidateTermsButton() {
        Shop.AccountMenu.open();
        baseChecks.checkTransitionValidation(Elements.AccountMenu.termsButton());
    }
    @CaseId(1528)
    @Test(
            description = "Тест валидации кнопки 'Доставка' в меню профиля",

            groups = {
                    "sbermarket-Ui-smoke","testing"
            }
    ) public void successValidateDeliveryButton() {
        Shop.AccountMenu.open();
        kraken.perform().click(Elements.AccountMenu.deliveryButton());
        accountChecks.checkIsDeliveryMenuOpen();
        kraken.perform().click(Elements.AccountMenu.deliveryModalButtonClose());
    }

    @Test(
            description = "Тест валидации кнопки 'Оплата' в меню профиля",

            groups = {
                    "metro-acceptance","metro-regression"
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

            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","testing"
            }
    ) public void successValidateFaqButton() {
        Shop.AccountMenu.open();
        baseChecks.checkTransitionValidation(Elements.AccountMenu.faqButton());
    }

    @CaseId(1531)
    @Test(
            description = "Тест доступности страниц профиля пользователя",

            groups = {
                    "metro-smoke","metro-acceptance","metro-regression",
                    "sbermarket-Ui-smoke","testing"
            }
    )
    public void successCheckProfilePagesAreAvailable() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.AccountMenu.open();
        baseChecks.checkPageIsAvailable(Pages.UserProfile.edit());
        baseChecks.checkPageIsAvailable(Pages.UserProfile.favorites());
        baseChecks.checkPageIsAvailable(Pages.UserProfile.shipments());
    }

    @Test(
            description = "Тест валидации дефолтных страниц истории заказов",

            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successValidateDefaultOrderHistory() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().userShipmentsPage();

        Shop.UserProfile.OrderHistory.applyFilterComplete();
        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.UserProfile.OrdersHistoryPage.completeOrdersPlaceholder()));

        Shop.UserProfile.OrderHistory.applyFilterActive();
        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.UserProfile.OrdersHistoryPage.activeOrdersPlaceholder()));

        Shop.UserProfile.OrderHistory.applyFilterAll();
        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.UserProfile.OrdersHistoryPage.allOrdersPlaceholder()));

        Shop.UserProfile.OrderHistory.goShopping();
        softAssert.assertFalse(
                kraken.detect().isElementPresent(
                        Elements.UserProfile.OrdersHistoryPage.completeOrdersPlaceholder()));

        softAssert.assertAll();
    }
}
