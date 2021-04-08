package ru.instamart.tests.ui.site;

import ru.instamart.ui.checkpoints.BaseUICheckpoints;
import ru.instamart.ui.common.lib.Pages;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

public class BasicMetroTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();

    @Test(
            description = "Тест валидности элементов и ссылок в шапке METRO Delivery",
            groups = {"metro-smoke","metro-acceptance","metro-regression"}
    ) public void successValidateMetroTenantHeader() {
        //todo
        throw new AssertionError() {};
    }

    @Test(
            description = "Тест валидности элементов и ссылок в подвале METRO Delivery",
            groups = {"metro-smoke","metro-acceptance","metro-regression"}
    ) public void successValidateMetroTenantFooter() {
        //todo
        throw new AssertionError() {};
    }

    @Test(
            description = "Тест доступности / недоступности витрин ритейлеров Metro Delivery-CC",
            groups = {"metro-smoke","metro-acceptance","metro-regression"}
    ) public void successCheckMetroRetailers() {
        baseChecks.checkPageIs404(Pages.Retailers.auchan());
        baseChecks.checkPageIs404(Pages.Retailers.azbuka());
        baseChecks.checkPageIs404(Pages.Retailers.vkusvill());
        baseChecks.checkPageIs404(Pages.Retailers.lenta());
        baseChecks.checkPageIs404(Pages.Retailers.karusel());
        baseChecks.checkPageIs404(Pages.Retailers.selgros());
        baseChecks.checkPageIs404(Pages.Retailers.flora());
        baseChecks.checkPageIs404(Pages.Retailers.foodcity());
        baseChecks.checkPageIs404(Pages.Retailers.magnit());
        baseChecks.checkPageIs404(Pages.Retailers.testretailer());
        baseChecks.checkPageIsAvailable(Pages.Retailers.metro());
    }

    @Test(
            description = "Тест доступности статических страниц на METRO Delivery",
            groups = {"metro-smoke","metro-acceptance","metro-regression"}
    ) public void successCheckMetroTenantStaticPages() {
        baseChecks.checkPageIsAvailable(Pages.Metro.about());
        baseChecks.checkPageIsAvailable(Pages.Metro.delivery());
        baseChecks.checkPageIsAvailable(Pages.Metro.rules());
        baseChecks.checkPageIsAvailable(Pages.Metro.payment());
        baseChecks.checkPageIsAvailable(Pages.Metro.returnPolicy());
        baseChecks.checkPageIsAvailable(Pages.Metro.faq());
        baseChecks.checkPageIsAvailable(Pages.Metro.terms());
        baseChecks.checkPageIsAvailable(Pages.Metro.contacts());
    }
}
