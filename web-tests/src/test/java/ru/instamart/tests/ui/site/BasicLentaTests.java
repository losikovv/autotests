package ru.instamart.tests.ui.site;

import ru.instamart.ui.checkpoints.BaseUICheckpoints;
import ru.instamart.ui.common.lib.Pages;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

public class BasicLentaTests  extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();

    @Test(
            description = "Тест валидности элементов и ссылок в шапке Lenta",
            groups = {"lenta-smoke","lenta-acceptance","lenta-regression"}
    ) public void successValidateLentaTenantHeader() {
        //todo
        throw new AssertionError() {};
    }

    @Test(
            description = "Тест валидности элементов и ссылок в футере Lenta",
            groups = {"lenta-smoke","lenta-acceptance","lenta-regression"}
    ) public void successValidateLentaTenantFooter() {
        //todo
        throw new AssertionError() {};
    }

    @Test(
            description = "Тест доступности / недоступности витрин ритейлеров для Lenta",
            groups = {"lenta-smoke","lenta-acceptance","lenta-regression"}
    ) public void successCheckLentaRetailers() {
        baseChecks.checkPageIs404(Pages.Retailers.metro());
        baseChecks.checkPageIs404(Pages.Retailers.auchan());
        baseChecks.checkPageIs404(Pages.Retailers.azbuka());
        baseChecks.checkPageIs404(Pages.Retailers.vkusvill());
        baseChecks.checkPageIs404(Pages.Retailers.karusel());
        baseChecks.checkPageIs404(Pages.Retailers.selgros());
        baseChecks.checkPageIs404(Pages.Retailers.flora());
        baseChecks.checkPageIs404(Pages.Retailers.foodcity());
        baseChecks.checkPageIs404(Pages.Retailers.magnit());
        baseChecks.checkPageIs404(Pages.Retailers.testretailer());
        baseChecks.checkPageIsAvailable(Pages.Retailers.lenta());
    }

    @Test(
            description = "Тест доступности статических страниц Lenta",
            groups = {"lenta-smoke","lenta-acceptance","lenta-regression"}
    ) public void successCheckLentaTenantStaticPages() {
        baseChecks.checkPageIsAvailable(Pages.Lenta.about());
        baseChecks.checkPageIsAvailable(Pages.Lenta.delivery());
        baseChecks.checkPageIsAvailable(Pages.Lenta.rules());
        baseChecks.checkPageIsAvailable(Pages.Lenta.payment());
        baseChecks.checkPageIsAvailable(Pages.Lenta.returnPolicy());
        baseChecks.checkPageIsAvailable(Pages.Lenta.faq());
        baseChecks.checkPageIsAvailable(Pages.Lenta.terms());
        baseChecks.checkPageIsAvailable(Pages.Lenta.contacts());
    }

}
