package ru.instamart.test.ui.landings;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.testdata.lib.Pages;
import ru.instamart.test.ui.TestBase;
import ru.instamart.ui.Elements;
import ru.instamart.ui.checkpoint.BaseUICheckpoints;

@Epic("STF UI")
@Feature("Проверка лендингов")
public final class DriversHiringLandingTests extends TestBase {

    private final BaseUICheckpoints baseChecks = new BaseUICheckpoints();

    @CaseId(1686)
    @Test(
            description = "Тест лендинга найма водителей Сбермаркета",
            groups = {"testing","sbermarket-Ui-smoke"}
    )
    public void successValidateSbermarketDriversHiringLanding() {
        kraken.get().page(Pages.Landings.driversHiring());
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
