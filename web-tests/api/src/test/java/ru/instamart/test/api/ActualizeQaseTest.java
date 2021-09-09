package ru.instamart.test.api;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import ru.instamart.kraken.service.QaseService;

@Slf4j
public class ActualizeQaseTest {
    private final QaseService qaseService = new QaseService();

    @Test(groups = "qase-actualize")
    public void actualizeAutomatedINAPI() {
        qaseService.setProjectCode("INAPI").actualizeAutomatedTestCases();
    }

    @Test(groups = "qase-actualize")
    public void actualizeAutomatedSHAPI() {
        qaseService.setProjectCode("SHAPI").actualizeAutomatedTestCases();
    }

    @Test(groups = "qase-actualize")
    public void actualizeAutomatedSTF() {
        qaseService.setProjectCode("STF").actualizeAutomatedTestCases();
    }

    @Test(groups = "qase-actualize")
    public void actualizeAutomatedSA() {
        qaseService.setProjectCode("SA").actualizeAutomatedTestCases();
    }
}
