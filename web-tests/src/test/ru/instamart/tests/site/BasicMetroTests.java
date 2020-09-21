package ru.instamart.tests.site;

import org.testng.annotations.Test;
import instamart.ui.common.lib.Pages;
import ru.instamart.tests.TestBase;

public class BasicMetroTests extends TestBase {

    @Test(
            description = "Тест валидности элементов и ссылок в шапке METRO Delivery",
            groups = {"metro-smoke","metro-acceptance","metro-regression"},
            priority = 101
    ) public void successValidateMetroTenantHeader() {
        //todo
        throw new AssertionError() {};
    }

    @Test(
            description = "Тест валидности элементов и ссылок в подвале METRO Delivery",
            groups = {"metro-smoke","metro-acceptance","metro-regression"},
            priority = 102
    ) public void successValidateMetroTenantFooter() {
        //todo
        throw new AssertionError() {};
    }

    @Test(
            description = "Тест доступности / недоступности витрин ритейлеров Metro Delivery-CC",
            groups = {"metro-smoke","metro-acceptance","metro-regression"},
            priority = 103
    ) public void successCheckMetroRetailers() {
        assertPageIs404(Pages.Retailers.auchan());
        assertPageIs404(Pages.Retailers.azbuka());
        assertPageIs404(Pages.Retailers.vkusvill());
        assertPageIs404(Pages.Retailers.lenta());
        assertPageIs404(Pages.Retailers.karusel());
        assertPageIs404(Pages.Retailers.selgros());
        assertPageIs404(Pages.Retailers.flora());
        assertPageIs404(Pages.Retailers.foodcity());
        assertPageIs404(Pages.Retailers.magnit());
        assertPageIs404(Pages.Retailers.testretailer());

        assertPageIsAvailable(Pages.Retailers.metro());
    }

    @Test(
            description = "Тест доступности статических страниц на METRO Delivery",
            groups = {"metro-smoke","metro-acceptance","metro-regression"},
            priority = 105
    ) public void successCheckMetroTenantStaticPages() {
        assertPageIsAvailable(Pages.Metro.about());
        assertPageIsAvailable(Pages.Metro.delivery());
        assertPageIsAvailable(Pages.Metro.rules());
        assertPageIsAvailable(Pages.Metro.payment());
        assertPageIsAvailable(Pages.Metro.returnPolicy());
        assertPageIsAvailable(Pages.Metro.faq());
        assertPageIsAvailable(Pages.Metro.terms());
        assertPageIsAvailable(Pages.Metro.contacts());
    }
}
