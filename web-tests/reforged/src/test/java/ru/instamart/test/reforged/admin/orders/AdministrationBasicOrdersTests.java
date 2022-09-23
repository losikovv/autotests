package ru.instamart.test.reforged.admin.orders;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminGroup.OD_REGRESS;
import static ru.instamart.reforged.admin.AdminRout.login;
import static ru.instamart.reforged.admin.AdminRout.orders;

/**
 * Сейчас нет пагинации и в ближайшее время не предвидится GARM-839
 */
@Epic("Админка STF")
@Feature("Заказы")
@Story("Страница 'Список заказов' admin/spa/orders")
public final class AdministrationBasicOrdersTests {

    @CaseId(1499)
    @Test(description = "Корректное отображение страницы (/admin/orders). Админ со старыми ролями", groups = {OD_REGRESS})
    public void basicOrdersPageTestOldRoles() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllFilterInputsIsEmpty();
        orders().checkShipmentStatusFiltersNotSelected();
        orders().checkPlatformFiltersNotSelected();
        orders().checkRetailerFiltersNotSelected();
        orders().checkBasicStoreFiltersNotSelected();
        orders().checkStoreFiltersNotSelected();
        orders().checkPaymentMethodFiltersNotSelected();
        orders().checkPaymentStatusFiltersNotSelected();
        orders().checkRegionFiltersNotSelected();
        orders().checkQuickFiltersNotSelected();
        orders().checkResetFiltersButtonVisible();
        orders().checkApplyFiltersButtonVisible();
    }

    @CaseId(1499)
    @Test(description = "Корректное отображение страницы (/admin/orders). Админ с новыми ролями", groups = {OD_REGRESS})
    public void basicOrdersPageTestNewRoles() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        orders().goToPage();
        orders().checkLoadingLabelNotVisible();
        orders().checkAllFilterInputsIsEmpty();
        orders().checkShipmentStatusFiltersNotSelected();
        orders().checkPlatformFiltersNotSelected();
        orders().checkRetailerFiltersNotSelected();
        orders().checkBasicStoreFiltersNotSelected();
        orders().checkStoreFiltersNotSelected();
        orders().checkPaymentMethodFiltersNotSelected();
        orders().checkPaymentStatusFiltersNotSelected();
        orders().checkRegionFiltersNotSelected();
        orders().checkQuickFiltersNotSelected();
        orders().checkResetFiltersButtonVisible();
        orders().checkApplyFiltersButtonVisible();
    }
}
