package ru.instamart.tests.landings;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.application.Elements;
import ru.instamart.application.lib.Pages;
import ru.instamart.tests.TestBase;

public class DriversHiringLandingTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        kraken.get().page(Pages.Landings.driversHiring());
    }

    @Test(
            description = "Тест лендинга найма водителей Сбермаркета",
            priority = 41,
            groups = {"sbermarket-smoke","sbermarket-acceptance","sbermarket-regression"}
    )
    public void successValidateSbermarketDriversHiringLanding() {
        assertPageIsAvailable();
        Assert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.Landings.driversHiringLanding.submitButton()));
    }
}
