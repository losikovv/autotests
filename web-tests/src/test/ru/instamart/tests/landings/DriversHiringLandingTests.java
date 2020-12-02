package ru.instamart.tests.landings;

import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.common.lib.Pages;
import instamart.ui.objectsmap.Elements;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.TestBase;

public class DriversHiringLandingTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        kraken.get().page(Pages.Landings.driversHiring());
    }


    @Test(
            description = "Тест лендинга найма водителей Сбермаркета",
            priority = 41,
            groups = {"testing","sbermarket-Ui-smoke"}
    )
    public void successValidateSbermarketDriversHiringLanding() {
        baseChecks.checkPageIsAvailable();
        baseChecks.checkIsElementPresent(Elements.Landings.driversHiringLanding.DriverJobForm.submitButton());
        baseChecks.checkIsElementPresent(Elements.Landings.driversHiringLanding.DriverJobForm.citySelector());
        baseChecks.checkIsElementPresent(Elements.Landings.driversHiringLanding.DriverJobForm.countrySelector());
        baseChecks.checkIsElementPresent(Elements.Landings.driversHiringLanding.DriverJobForm.phoneField());
        baseChecks.checkIsElementPresent(Elements.Landings.driversHiringLanding.DriverJobForm.nameField());
        baseChecks.checkIsElementPresent(Elements.Landings.driversHiringLanding.howToJoin());
        baseChecks.checkIsElementPresent(Elements.Landings.driversHiringLanding.whatToDo());
        baseChecks.checkIsElementPresent(Elements.Landings.driversHiringLanding.workConditions());
    }
}
