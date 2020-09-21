package ru.instamart.tests.landings;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import instamart.ui.objectsmap.Elements;
import instamart.ui.common.lib.Pages;
import ru.instamart.tests.TestBase;

public class SbermarketAppPromoLandingTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        kraken.get().page(Pages.Landings.sberAppPromo());
    }

    @Test(
            description = "Тест промо-лендинга приложения Сбермаркета",
            priority = 42,
            groups = {"sbermarket-smoke","sbermarket-acceptance","sbermarket-regression"}
    )
    public void successValidateSbermarketAppPromoLanding() {
        assertPageIsAvailable();
        Assert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.Landings.sberAppPromoLanding.submitButton()));
    }
}
