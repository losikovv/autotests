package ru.instamart.tests.ui.landings;

import ru.instamart.ui.checkpoints.BaseUICheckpoints;
import ru.instamart.ui.common.lib.Pages;
import ru.instamart.ui.objectsmap.Elements;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

public class DriversHiringLandingTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        kraken.get().page(Pages.Landings.driversHiring());
    }

    @CaseId(1686)
    @Test(
            description = "Тест лендинга найма водителей Сбермаркета",

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
