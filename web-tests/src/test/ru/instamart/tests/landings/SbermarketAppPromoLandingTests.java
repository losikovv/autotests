package ru.instamart.tests.landings;

import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.common.lib.Pages;
import instamart.ui.objectsmap.Elements;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.TestBase;

public class SbermarketAppPromoLandingTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        kraken.get().page(Pages.Landings.sberAppPromo());
    }

    @Test(
            description = "Тест промо-лендинга приложения Сбермаркета",
            priority = 42,
            groups = {"testing","sbermarket-Ui-smoke"}
    )
    public void successValidateSbermarketAppPromoLanding() {
        baseChecks.checkPageIsAvailable();
        baseChecks.checkIsElementPresent(Elements.Landings.sberAppPromoLanding.submitButton());
    }
}
