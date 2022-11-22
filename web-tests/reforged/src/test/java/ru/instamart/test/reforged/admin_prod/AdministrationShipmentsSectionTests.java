package ru.instamart.test.reforged.admin_prod;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.TimeUtil;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.PROD_ADMIN_SMOKE;
import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Управление заказами")
public final class AdministrationShipmentsSectionTests {

    @CaseId(175)
    @Story("Тест на корректное отображение элементов страницы со списком заказов в админке")
    @Test(description = "Тест на корректное отображение элементов страницы со списком заказов в админке", groups = PROD_ADMIN_SMOKE)
    public void validateDefaultAdminShipmentsPage() {
        UserData defaultAdmin = UserManager.getDefaultAdmin();

        login().goToPage();
        login().clickOnLoginViaActiveDirectory();

        activeDirectory().fillMail(defaultAdmin.getEmail());
        activeDirectory().fillPassword(defaultAdmin.getPassword());
        activeDirectory().clickOnLoginButton();

        main().interactAuthoredHeader().checkAdminAuth();

        shipments().goToPageOld();
        shipments().checkPageTitle();
        shipments().checkOrderDateFrom();
        shipments().checkOrderDateTo();
        shipments().checkCustomerName();
        shipments().checkCustomerSurName();
    }

    @CaseId(172)
    @Story("Тест на работоспособность фильтра ДАТА И ВРЕМЯ ДОСТАВКИ")
    @Test(description = "Тест на работоспособность фильтра ДАТА И ВРЕМЯ ДОСТАВКИ", groups = PROD_ADMIN_SMOKE)
    public void validateFilterDateAndTimeAdminShipmentsPage() {
        UserData defaultAdmin = UserManager.getDefaultAdmin();

        login().goToPage();
        login().clickOnLoginViaActiveDirectory();

        activeDirectory().fillMail(defaultAdmin.getEmail());
        activeDirectory().fillPassword(defaultAdmin.getPassword());
        activeDirectory().clickOnLoginButton();

        main().interactAuthoredHeader().checkAdminAuth();

        shipments().goToPageOld();
        shipments().checkPageTitle();
        shipments().setDateAndTimeFilterFromTableDefault(TimeUtil.getDeliveryDateFrom());
        shipments().setDateAndTimeFilterToTableDefault(TimeUtil.getDeliveryDateTo());
        shipments().search();
        shipments().waitPageLoad();
        shipments().checkDateAndTimeShipmentsColumn(TimeUtil.getDateWithoutTimeUTC());
    }
}
