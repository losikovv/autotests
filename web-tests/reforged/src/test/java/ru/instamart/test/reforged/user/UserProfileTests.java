package ru.instamart.test.reforged.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.kraken.setting.Config;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.lib.Pages;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.admin.AdminRout.pages;
import static ru.instamart.reforged.stf.page.StfRouter.home;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Профиль пользователя")
public class UserProfileTests extends BaseTest {

    @BeforeMethod(alwaysRun = true,
            description = "Проверяем залогинен ли пользователь, если да то завершаем сессию")
    public void quickLogout() {
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(Generate.phoneNumber());
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillSMS(Config.DEFAULT_SMS);
    }

    @CaseId(1524)
    @Story("Выпадающее меню")
    @Test(
            description = "Тест валидации меню профиля Sbermarket",
            groups = {"sbermarket-Ui-smoke"}
    )
    public void successValidateSbermarketTenantProfileMenu() {
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().checkProfileNameExists();
        shop().interactHeader().interactAccountMenu().checkProfileButtonExists();
        shop().interactHeader().interactAccountMenu().checkCompaniesButtonExists();
        shop().interactHeader().interactAccountMenu().checkTermsButtonExists();
        shop().interactHeader().interactAccountMenu().checkLogoutButtonExists();
        shop().interactHeader().interactAccountMenu().checkDeliveryLinkExists();
        shop().interactHeader().interactAccountMenu().checkFAQLinkExists();
    }

    @CaseId(1525)
    @Story("Выпадающее меню")
    @Test(
            description = "Тест валидации кнопки 'Профиль' в меню профиля",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-Ui-smoke"
            }
    )
    public void successValidateUserProfileButton() {
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToProfile();
        pages().checkPageIsAvailable();
//        kraken.get().page(Config.DEFAULT_RETAILER);
//        Shop.AccountMenu.open();
//        baseChecks.checkTransitionValidation(Elements.AccountMenu.profileButton());
    }

//    @CaseId(1527)
//    @Story("Выпадающее меню")
//    @Test(
//            description = "Тест валидации кнопки 'Условия использования' в меню профиля",
//            groups = {
//                    "metro-acceptance", "metro-regression", "sbermarket-Ui-smoke"
//            }
//    )
//    public void successValidateTermsButton() {
//        Shop.AccountMenu.open();
//        baseChecks.checkTransitionValidation(Elements.AccountMenu.termsButton());
//    }
//
//    @CaseId(1528)
//    @Story("Выпадающее меню")
//    @Test(
//            description = "Тест валидации кнопки 'Доставка' в меню профиля",
//            groups = {"sbermarket-Ui-smoke"}
//    )
//    public void successValidateDeliveryButton() {
//        Shop.AccountMenu.open();
//        Shop.AccountMenu.openDelivery();
//        accountChecks.checkIsDeliveryMenuOpen();
//        Shop.AccountMenu.closeDelivery();
//    }
//
//    @CaseId(1530)
//    @Story("Выпадающее меню")
//    @Test(
//            description = "Тест валидации кнопки 'FAQ' в меню профиля",
//            groups = {
//                    "metro-acceptance", "metro-regression", "sbermarket-Ui-smoke"
//            }
//    )
//    public void successValidateFaqButton() {
//        Shop.AccountMenu.open();
//        kraken.perform().click(Elements.AccountMenu.faqButton());
//        baseChecks.checkPageIsAvailable();
//    }
//
//    @CaseId(1531)
//    @Story("навигация в меню пользователя")
//    @Test(
//            description = "Тест доступности страниц профиля пользователя",
//            groups = {
//                    "metro-smoke", "metro-acceptance", "metro-regression", "sbermarket-Ui-smoke"
//            }
//    )
//    public void successCheckProfilePagesAreAvailable() {
//        kraken.get().page(Config.DEFAULT_RETAILER);
//        Shop.AccountMenu.open();
//        baseChecks.checkPageIsAvailable(Pages.UserProfile.edit());
//        baseChecks.checkPageIsAvailable(Pages.UserProfile.favorites());
//        baseChecks.checkPageIsAvailable(Pages.UserProfile.shipments());
//    }
//
//    @CaseId(1532)
//    @Story("Заказы")
//    @Test(
//            description = "Тест валидации дефолтных страниц истории заказов",
//            groups = {
//                    "metro-acceptance", "metro-regression", "sbermarket-Ui-smoke"
//            }
//    )
//    public void successValidateDefaultOrderHistory() {
//        kraken.get().userShipmentsPage();
//        Shop.UserProfile.OrderHistory.applyFilterComplete();
//        baseChecks.checkIsElementPresent(Elements.UserProfile.OrdersHistoryPage.completeOrdersPlaceholder());
//        Shop.UserProfile.OrderHistory.applyFilterActive();
//        baseChecks.checkIsElementPresent(Elements.UserProfile.OrdersHistoryPage.activeOrdersPlaceholder());
//        Shop.UserProfile.OrderHistory.applyFilterAll();
//        baseChecks.checkIsElementPresent(Elements.UserProfile.OrdersHistoryPage.allOrdersPlaceholder());
//        Shop.UserProfile.OrderHistory.goShopping();
//        baseChecks.checkIsElementNotPresent(Elements.UserProfile.OrdersHistoryPage.completeOrdersPlaceholder());
//    }
}
