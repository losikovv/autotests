package ru.instamart.test.ui;

import io.qase.api.models.v1.testruns.TestRun;
import io.qase.api.models.v1.testruns.TestRuns;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.kraken.service.QaseService;
import ru.instamart.ui.config.ConfigManager;

import java.util.List;

public class Cleanup {

    @BeforeClass
    public void setUp() {
        ConfigManager.getInstance().loadConfig();
    }

    @Test(  description = "ЗАПУСК ТЕСТА УДАЛЯЕТ ВСЕ ТЕСТРАНЫ У УКАЗАННОГО ПРОЕКТА",
            groups = "cleanup")
    public void deleteAllProjectTestRuns() {
        final QaseService qaseService = new QaseService("", "");
        final TestRuns testRuns = qaseService
                .getQaseApi()
                .testRuns()
                .getAll("", true);
        final List<TestRun> testRunList = testRuns.getTestRunList();
        testRunList.forEach(testRun -> qaseService.getQaseApi().testRuns().delete("", testRun.getId()));
    }
}
