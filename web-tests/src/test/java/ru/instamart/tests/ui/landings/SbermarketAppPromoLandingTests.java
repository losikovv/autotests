package ru.instamart.tests.ui.landings;

import ru.instamart.ui.checkpoint.BaseUICheckpoints;
import ru.instamart.ui.common.lib.Pages;
import ru.instamart.ui.Elements;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

public class SbermarketAppPromoLandingTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        kraken.get().page(Pages.Landings.sberAppPromo());
    }

    @CaseId(1685)
    @Test(
            description = "Тест промо-лендинга приложения Сбермаркета",
            groups = {"testing","sbermarket-Ui-smoke"},
            enabled = false
    )
    public void successValidateSbermarketAppPromoLanding() {
        baseChecks.checkPageIsAvailable();
        baseChecks.checkIsElementPresent(Elements.Landings.sberAppPromoLanding.submitButton());
    }
}
