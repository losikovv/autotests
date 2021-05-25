package ru.instamart.test.ui.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.kraken.setting.Config;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.lib.Pages;
import ru.instamart.test.ui.TestBase;
import ru.instamart.ui.Elements;
import ru.instamart.ui.checkpoint.BaseUICheckpoints;
import ru.instamart.ui.checkpoint.users.AccountMenuCheckpoints;
import ru.instamart.ui.module.Shop;
import ru.instamart.ui.module.User;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Epic("STF UI")
@Feature("Профиль пользователя")
public class UserProfileTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    AccountMenuCheckpoints accountChecks = new AccountMenuCheckpoints();

    @BeforeClass(alwaysRun = true,
            description ="Проверяем залогинен ли пользователь, если да то завершаем сессию")
    public void quickLogout() {
        User.Logout.logout();
        String phone = Generate.phoneNumber();
        Shop.AuthModal.openAuthLending();
        User.Do.registration(phone,true);
        User.Do.sendSms(Config.DEFAULT_SMS);
    }


    @Test(
            description = "Тест валидации меню профиля Delivery Metro",
            groups = {
                    "metro-smoke","metro-acceptance","metro-regression"
            }
    ) public void successValidateMetroTenantProfileMenu() {
        Shop.AccountMenu.open();

        assertTrue(
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

        assertFalse(
                kraken.detect().isAccountMenuOpen(),
                    failMessage("Не закрывается всплывающее меню профиля Delivery Metro"));
    }

    @CaseId(1524)
    @Story("Выпадающее меню")
    @Test(
            description = "Тест валидации меню профиля Sbermarket",
            groups = {"sbermarket-Ui-smoke"}
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
    @Story("Выпадающее меню")
    @Test(
            description = "Тест валидации кнопки 'Профиль' в меню профиля",
            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-Ui-smoke"
            }
    ) public void successValidateUserProfileButton() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.AccountMenu.open();
        baseChecks.checkTransitionValidation(Elements.AccountMenu.profileButton());
    }

    @CaseId(1527)
    @Story("Выпадающее меню")
    @Test(
            description = "Тест валидации кнопки 'Условия использования' в меню профиля",
            groups = {
                    "metro-acceptance","metro-regression","sbermarket-Ui-smoke"
            }
    ) public void successValidateTermsButton() {
        Shop.AccountMenu.open();
        baseChecks.checkTransitionValidation(Elements.AccountMenu.termsButton());
    }

    @CaseId(1528)
    @Story("Выпадающее меню")
    @Test(
            description = "Тест валидации кнопки 'Доставка' в меню профиля",
            groups = {"sbermarket-Ui-smoke"}
    ) public void successValidateDeliveryButton() {
        Shop.AccountMenu.open();
        Shop.AccountMenu.openDelivery();
        accountChecks.checkIsDeliveryMenuOpen();
        Shop.AccountMenu.closeDelivery();
    }

    @CaseId(1530)
    @Story("Выпадающее меню")
    @Test(
            description = "Тест валидации кнопки 'FAQ' в меню профиля",
            groups = {
                    "metro-acceptance","metro-regression","sbermarket-Ui-smoke"
            }
    ) public void successValidateFaqButton() {
        Shop.AccountMenu.open();
        kraken.perform().click(Elements.AccountMenu.faqButton());
        baseChecks.checkPageIsAvailable();
    }

    @CaseId(1531)
    @Story("навигация в меню пользователя")
    @Test(
            description = "Тест доступности страниц профиля пользователя",
            groups = {
                    "metro-smoke","metro-acceptance","metro-regression", "sbermarket-Ui-smoke"
            }
    )
    public void successCheckProfilePagesAreAvailable() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.AccountMenu.open();
        baseChecks.checkPageIsAvailable(Pages.UserProfile.edit());
        baseChecks.checkPageIsAvailable(Pages.UserProfile.favorites());
        baseChecks.checkPageIsAvailable(Pages.UserProfile.shipments());
    }

    @CaseId(1532)
    @Story("Заказы")
    @Test(
            description = "Тест валидации дефолтных страниц истории заказов",
            groups = {
                    "metro-acceptance","metro-regression","sbermarket-Ui-smoke"
            }
    ) public void successValidateDefaultOrderHistory() {
        kraken.get().userShipmentsPage();
        Shop.UserProfile.OrderHistory.applyFilterComplete();
        baseChecks.checkIsElementPresent(Elements.UserProfile.OrdersHistoryPage.completeOrdersPlaceholder());
        Shop.UserProfile.OrderHistory.applyFilterActive();
        baseChecks.checkIsElementPresent(Elements.UserProfile.OrdersHistoryPage.activeOrdersPlaceholder());
        Shop.UserProfile.OrderHistory.applyFilterAll();
        baseChecks.checkIsElementPresent(Elements.UserProfile.OrdersHistoryPage.allOrdersPlaceholder());
        Shop.UserProfile.OrderHistory.goShopping();
        baseChecks.checkIsElementNotPresent(Elements.UserProfile.OrdersHistoryPage.completeOrdersPlaceholder());
    }
}
