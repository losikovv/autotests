package ru.instamart.test.api;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.instamart.kraken.service.QaseService;

@Slf4j
public final class ActualizeQaseTest {

    private final QaseService qaseService = new QaseService();

    @Test(groups = "qase-actualize", dataProvider = "project")
    public void actualizeAutomated(final String project) {
        this.qaseService.setProjectCode(project).actualizeAutomatedTestCases();
    }

    @Test(groups = "qase-cleanup", dataProvider = "project")
    public void cleanUpQase(final String project) {
        this.qaseService.setProjectCode(project);
        try {
            this.qaseService.deleteOldTestRuns();
            this.qaseService.deleteOldDefects();
        } catch (Exception e) {
            log.error("CLEANUP: failed", e);
        }
    }

    @DataProvider(name = "project")
    private static Object[][] getProject() {
        return new Object[][] {
                {"INAPI"},
                {"SHAPI"},
                {"STF"},
                {"SA"},
                {"PB"}
        };
    }
}
