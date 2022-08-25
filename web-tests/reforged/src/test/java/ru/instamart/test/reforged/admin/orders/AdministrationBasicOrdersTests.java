package ru.instamart.test.reforged.admin.orders;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Заказы")
@Story("Страница 'Список заказов' admin/spa/orders")
public final class AdministrationBasicOrdersTests {

    @CaseId(1499)
    @Test(description = "Корректное отображение страницы (/admin/orders). Админ со старыми ролями",
            groups = {"regression", "ondemand_orders_regression", "ondemand_orders_smoke"})
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

        orders().clickShipmentStatusFilterSelector();
        orders().checkShipmentStatusDropdownItemsVisible();
        orders().clickShipmentNumberInput();
        orders().checkShipmentStatusDropdownItemsNotVisible();

        orders().clickShipmentCreateDateStart();
        orders().checkCreateDateTimePickerVisible();
        orders().clickShipmentNumberInput();
        orders().checkCreateDateTimePickerNotVisible();

        orders().clickShipmentCreateDateEnd();
        orders().checkCreateDateTimePickerVisible();
        orders().clickShipmentNumberInput();
        orders().checkCreateDateTimePickerNotVisible();

        orders().clickShipmentDeliveryDateStart();
        orders().checkDeliveryDateTimePickerVisible();
        orders().clickShipmentNumberInput();
        orders().checkDeliveryDateTimePickerNotVisible();

        orders().clickShipmentDeliveryDateEnd();
        orders().checkDeliveryDateTimePickerVisible();
        orders().clickShipmentNumberInput();
        orders().checkDeliveryDateTimePickerNotVisible();

        orders().clickPlatformFilterSelector();
        orders().checkPlatformDropdownItemsVisible();
        orders().clickShipmentNumberInput();
        orders().checkPlatformDropdownItemsNotVisible();

        orders().clickRetailerFilterSelector();
        orders().checkRetailerDropdownItemsVisible();
        orders().clickShipmentNumberInput();
        orders().checkRetailerDropdownItemsNotVisible();

        orders().clickBasicStoreFilterSelector();
        orders().checkBasicStoreDropdownItemsVisible();
        orders().clickShipmentNumberInput();
        orders().checkBasicStoreDropdownItemsNotVisible();

        orders().clickStoreFilterSelector();
        orders().checkStoreDropdownItemsVisible();
        orders().clickShipmentNumberInput();
        orders().checkStoreDropdownItemsNotVisible();

        orders().clickPaymentMethodFilterSelector();
        orders().checkPaymentMethodDropdownItemsVisible();
        orders().clickShipmentNumberInput();
        orders().checkPaymentMethodDropdownItemsNotVisible();

        orders().clickPaymentStatusFilterSelector();
        orders().checkPaymentStatusDropdownItemsVisible();
        orders().clickShipmentNumberInput();
        orders().checkPaymentStatusDropdownItemsNotVisible();

        orders().clickCollectorFilterSelector();
        orders().checkCollectorDropdownItemsVisible();
        orders().clickShipmentNumberInput();
        orders().checkCollectorDropdownItemsNotVisible();

        orders().clickCourierFilterSelector();
        orders().checkCourierDropdownItemsVisible();
        orders().clickShipmentNumberInput();
        orders().checkCourierDropdownItemsNotVisible();

        orders().clickRegionFilterSelector();
        orders().checkRegionDropdownItemsVisible();
        orders().clickShipmentNumberInput();
        orders().checkRegionDropdownItemsNotVisible();

        orders().clickQuickFiltersSelector();
        orders().checkQuickFiltersDropdownItemsVisible();
        orders().clickShipmentNumberInput();
        orders().checkQuickFiltersDropdownItemsNotVisible();
    }

    @CaseId(1499)
    @Test(description = "Корректное отображение страницы (/admin/orders). Админ с новыми ролями",
            groups = {"regression", "ondemand_orders_regression", "ondemand_orders_smoke"})
    public void basicOrdersPageTestNewRoles() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

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


        orders().clickShipmentStatusFilterSelector();
        orders().checkShipmentStatusDropdownItemsVisible();
        orders().clickShipmentNumberInput();
        orders().checkShipmentStatusDropdownItemsNotVisible();

        orders().clickShipmentCreateDateStart();
        orders().checkCreateDateTimePickerVisible();
        orders().clickShipmentNumberInput();
        orders().checkCreateDateTimePickerNotVisible();

        orders().clickShipmentCreateDateEnd();
        orders().checkCreateDateTimePickerVisible();
        orders().clickShipmentNumberInput();
        orders().checkCreateDateTimePickerNotVisible();

        orders().clickShipmentDeliveryDateStart();
        orders().checkDeliveryDateTimePickerVisible();
        orders().clickShipmentNumberInput();
        orders().checkDeliveryDateTimePickerNotVisible();

        orders().clickShipmentDeliveryDateEnd();
        orders().checkDeliveryDateTimePickerVisible();
        orders().clickShipmentNumberInput();
        orders().checkDeliveryDateTimePickerNotVisible();

        orders().clickPlatformFilterSelector();
        orders().checkPlatformDropdownItemsVisible();
        orders().clickShipmentNumberInput();
        orders().checkPlatformDropdownItemsNotVisible();

        orders().clickRetailerFilterSelector();
        orders().checkRetailerDropdownItemsVisible();
        orders().clickShipmentNumberInput();
        orders().checkRetailerDropdownItemsNotVisible();

        orders().clickBasicStoreFilterSelector();
        orders().checkBasicStoreDropdownItemsVisible();
        orders().clickShipmentNumberInput();
        orders().checkBasicStoreDropdownItemsNotVisible();

        orders().clickStoreFilterSelector();
        orders().checkStoreDropdownItemsVisible();
        orders().clickShipmentNumberInput();
        orders().checkStoreDropdownItemsNotVisible();

        orders().clickPaymentMethodFilterSelector();
        orders().checkPaymentMethodDropdownItemsVisible();
        orders().clickShipmentNumberInput();
        orders().checkPaymentMethodDropdownItemsNotVisible();

        orders().clickPaymentStatusFilterSelector();
        orders().checkPaymentStatusDropdownItemsVisible();
        orders().clickShipmentNumberInput();
        orders().checkPaymentStatusDropdownItemsNotVisible();

        orders().clickCollectorFilterSelector();
        orders().checkCollectorDropdownItemsVisible();
        orders().clickShipmentNumberInput();
        orders().checkCollectorDropdownItemsNotVisible();

        orders().clickCourierFilterSelector();
        orders().checkCourierDropdownItemsVisible();
        orders().clickShipmentNumberInput();
        orders().checkCourierDropdownItemsNotVisible();

        orders().clickRegionFilterSelector();
        orders().checkRegionDropdownItemsVisible();
        orders().clickShipmentNumberInput();
        orders().checkRegionDropdownItemsNotVisible();

        orders().clickQuickFiltersSelector();
        orders().checkQuickFiltersDropdownItemsVisible();
        orders().clickShipmentNumberInput();
        orders().checkQuickFiltersDropdownItemsNotVisible();
    }
}
