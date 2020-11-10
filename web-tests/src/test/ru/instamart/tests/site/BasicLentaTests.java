package ru.instamart.tests.site;

import instamart.ui.common.lib.Pages;
import org.testng.annotations.Test;
import ru.instamart.tests.TestBase;

public class BasicLentaTests  extends TestBase {

    @Test(
            description = "Тест валидности элементов и ссылок в шапке Lenta",
            groups = {"lenta-smoke","lenta-acceptance","lenta-regression"},
            priority = 101
    ) public void successValidateLentaTenantHeader() {
        //todo
        throw new AssertionError() {};
    }

    @Test(
            description = "Тест валидности элементов и ссылок в футере Lenta",
            groups = {"lenta-smoke","lenta-acceptance","lenta-regression"},
            priority = 102
    ) public void successValidateLentaTenantFooter() {
        //todo
        throw new AssertionError() {};
    }

    @Test(
            description = "Тест доступности / недоступности витрин ритейлеров для Lenta",
            groups = {"lenta-smoke","lenta-acceptance","lenta-regression"},
            priority = 103
    ) public void successCheckLentaRetailers() {
        assertPageIs404(Pages.Retailers.metro());
        assertPageIs404(Pages.Retailers.auchan());
        assertPageIs404(Pages.Retailers.azbuka());
        assertPageIs404(Pages.Retailers.vkusvill());
        assertPageIs404(Pages.Retailers.karusel());
        assertPageIs404(Pages.Retailers.selgros());
        assertPageIs404(Pages.Retailers.flora());
        assertPageIs404(Pages.Retailers.foodcity());
        assertPageIs404(Pages.Retailers.magnit());
        assertPageIs404(Pages.Retailers.testretailer());

        assertPageIsAvailable(Pages.Retailers.lenta());
    }

    @Test(
            description = "Тест доступности статических страниц Lenta",
            groups = {"lenta-smoke","lenta-acceptance","lenta-regression"},
            priority = 105
    ) public void successCheckLentaTenantStaticPages() {
        assertPageIsAvailable(Pages.Lenta.about());
        assertPageIsAvailable(Pages.Lenta.delivery());
        assertPageIsAvailable(Pages.Lenta.rules());
        assertPageIsAvailable(Pages.Lenta.payment());
        assertPageIsAvailable(Pages.Lenta.returnPolicy());
        assertPageIsAvailable(Pages.Lenta.faq());
        assertPageIsAvailable(Pages.Lenta.terms());
        assertPageIsAvailable(Pages.Lenta.contacts());
    }

}
