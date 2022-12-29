package ru.instamart.kraken.service.testit;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestResult;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.config.TestItProperties;
import ru.instamart.kraken.service.testit.model.autotest.AutotestPost;
import ru.instamart.kraken.service.testit.model.autotest.AutotestPut;
import ru.testit.client.model.TestRunV2GetModel;
import ru.testit.client.model.TestRunV2PostShortModel;

import java.io.File;
import java.util.UUID;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static ru.instamart.kraken.helper.DateTimeHelper.getDateFromMSK;

@Slf4j
public enum TestItService {

    INSTANCE;

    private static final String RUN_PREFIX = "[Automation]";
    private final String PIPELINE_URL = System.getenv("CI_PIPELINE_URL");

    @Getter
    private final TestItClient client;

    TestItService() {
        this.client = new TestItClient();
    }

    public void startTestRun() {
        //TODO: Убрать после перехода
        if (getClient().isTestRunNotExist()) return;

        TestRunV2GetModel response = null;
        if (TestItProperties.TEST_RUN_ID.equals("null")) {
            final  var model = new TestRunV2PostShortModel();
            model.setProjectId(UUID.fromString(TestItProperties.PROJECT_ID));
            model.setName(String.format("%s %s [%s] %s", RUN_PREFIX, TestItProperties.TEST_RUN_NAME, EnvironmentProperties.Env.ENV_NAME, getDateFromMSK()));
            model.setDescription(String.format("Pipline: %s  UserInfo: %s", isNull(PIPELINE_URL) ? "local run" : PIPELINE_URL, getClient().getUserInfoService().createUserInfo()));
            model.setLaunchSource("From Java Code");
            response = getClient().createEmpty(model);
            TestItProperties.TEST_RUN_ID = response.getId().toString();
        }
        getClient().startTestRun(response);
    }

    public void updateTest(final ITestResult testResult, final File... attachment) {
        if (getClient().isTestRunNotExist()) return;

        log.debug("Update test");
        final var casesIds = Utils.getCasesIds(testResult);
        final var caseId = casesIds.stream().findFirst().orElseThrow();
        final var autoTestModel = getClient().getAutotestByExternalId(caseId);

        String autotestId;
        if (nonNull(autoTestModel)) {
            getClient().updateAutoTest(new AutotestPut(autoTestModel), testResult, attachment);
            autotestId = autoTestModel.getId().toString();
        } else {
            final var result = getClient().createAutoTest(new AutotestPost(testResult), testResult, attachment);
            autotestId = result.getId().toString();
        }
        casesIds.forEach(id -> getClient().linkAutoTestToWorkItem(autotestId, id));
    }

    public void completeTestRun() {
        getClient().sendTestResult();
        getClient().completeTestRun();
    }
}
