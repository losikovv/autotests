package ru.instamart.test.reforged.site;

import org.testng.annotations.Test;
import ru.instamart.test.reforged.BaseTest;
import static ru.instamart.reforged.stf.page.StfRouter.metro;

public class BasicMetroTests extends BaseTest {

    @Test(
            description = "Тест валидности элементов и ссылок в шапке METRO Delivery",
            groups = {"metro-smoke","metro-acceptance","metro-regression"}
    ) public void successValidateMetroTenantHeader() {
        metro().goToPage();
        metro().checkPageIsAvailable();

        metro().interactHeader().checkHeaderVisible();
        metro().interactHeader().checkSelectAddressButtonVisible();
        metro().interactHeader().checkSelectAddressTextButtonVisible();
        metro().interactHeader().checkHotlineWorkHoursVisible();
        metro().interactHeader().checkHotlinePhoneVisible();
        metro().interactHeader().checkDeliveryButtonVisible();
        metro().interactHeader().checkPickupButtonVisible();
        metro().interactHeader().checkShopLogoButtonVisible();
        metro().interactHeader().checkForB2bVisible();
        metro().interactHeader().checkHelpVisible();
        metro().interactHeader().checkCategoryMenuVisible();
        metro().interactHeader().checkSearchInputVisible();
        metro().interactHeader().checkSearchButtonVisible();
        metro().interactHeader().checkCartVisible();
        metro().interactHeader().checkPartnershipLabelVisible();
        metro().interactHeader().checkLoginIsVisible();
    }

    @Test(
            description = "Тест валидности элементов и ссылок в подвале METRO Delivery",
            groups = {"metro-smoke","metro-acceptance","metro-regression"}
    ) public void successValidateMetroTenantFooter() {


    }

    @Test(
            description = "Тест доступности / недоступности витрин ритейлеров Metro Delivery-CC",
            groups = {"metro-smoke","metro-acceptance","metro-regression"}
    ) public void successCheckMetroRetailers() {

    }

    @Test(
            description = "Тест доступности статических страниц на METRO Delivery",
            groups = {"metro-smoke","metro-acceptance","metro-regression"}
    ) public void successCheckMetroTenantStaticPages() {

    }
}
