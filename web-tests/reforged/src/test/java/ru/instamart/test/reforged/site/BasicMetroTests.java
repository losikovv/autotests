package ru.instamart.test.reforged.site;

import org.testng.annotations.Test;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.metro;

public class BasicMetroTests extends BaseTest {

    @Test(
            description = "Тест валидности элементов и ссылок в шапке METRO Delivery",
            groups = {"metro-smoke","metro-acceptance","metro-regression"}
    ) public void successValidateMetroTenantHeader() throws InterruptedException {
        metro().goToPage();


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
