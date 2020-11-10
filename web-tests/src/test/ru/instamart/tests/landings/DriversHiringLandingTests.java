package ru.instamart.tests.landings;

import instamart.ui.common.lib.Pages;
import instamart.ui.objectsmap.Elements;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.TestBase;

public class DriversHiringLandingTests extends TestBase {
    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
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
