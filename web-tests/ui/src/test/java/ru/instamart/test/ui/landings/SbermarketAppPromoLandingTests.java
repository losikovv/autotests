package ru.instamart.test.ui.landings;

import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.testdata.lib.Pages;
import ru.instamart.test.ui.TestBase;
import ru.instamart.ui.Elements;
import ru.instamart.ui.checkpoint.BaseUICheckpoints;

public final class SbermarketAppPromoLandingTests extends TestBase {

    private final BaseUICheckpoints baseChecks = new BaseUICheckpoints();

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        kraken.get().page(Pages.Landings.sberAppPromo());
    }

    @Skip
    @CaseId(1685)
    @Test(
            description = "Тест промо-лендинга приложения Сбермаркета",
            groups = {"testing","sbermarket-Ui-smoke"}
    )
    public void successValidateSbermarketAppPromoLanding() {
        baseChecks.checkPageIsAvailable();
        baseChecks.checkIsElementPresent(Elements.Landings.sberAppPromoLanding.submitButton());
    }
}
