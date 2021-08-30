package ru.instamart.test.ui.landings;

import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.testdata.lib.Pages;
import ru.instamart.test.ui.TestBase;
import ru.instamart.ui.Elements;
import ru.instamart.ui.checkpoint.BaseUICheckpoints;

public final class SbermarketAppPromoLandingTests extends TestBase {

    private final BaseUICheckpoints baseChecks = new BaseUICheckpoints();

    @Skip
    @CaseId(1685)
    @Test(
            description = "Тест промо-лендинга приложения Сбермаркета",
            groups = {"testing","sbermarket-Ui-smoke"}
    )
    public void successValidateSbermarketAppPromoLanding() {
        kraken.get().page(Pages.Landings.sberAppPromo());
        baseChecks.checkPageIsAvailable();
        baseChecks.checkIsElementPresent(Elements.Landings.sberAppPromoLanding.submitButton());
    }
}
