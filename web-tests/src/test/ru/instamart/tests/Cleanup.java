package ru.instamart.tests;

import instamart.core.service.QaseService;
import instamart.core.settings.Config;
import io.qase.api.models.v1.testruns.TestRun;
import io.qase.api.models.v1.testruns.TestRuns;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class Cleanup {

    @BeforeClass
    public void setUp() {
        Config.load();
    }

    @Test(
            description = "ЗАПУСК ТЕСТА УДАЛЯЕТ ВСЕ ТЕСТРАНЫ У УКАЗАННОГО ПРОЕКТА",
            groups = "cleanup"
    )
    public void deleteAllProjectTestRuns() {
        final QaseService qaseService = new QaseService("");
        final TestRuns testRuns = qaseService
                .getQaseApi()
                .testRuns()
                .getAll("", true);
        final List<TestRun> testRunList = testRuns.getTestRunList();
        testRunList.forEach(testRun -> qaseService.getQaseApi().testRuns().delete("", testRun.getId()));
    }
}
