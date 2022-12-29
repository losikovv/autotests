package ru.instamart.kraken.service.testit;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestResult;
import ru.instamart.kraken.config.TestItProperties;
import ru.instamart.kraken.service.UserInfoService;
import ru.instamart.kraken.service.testit.model.autotest.AutotestPost;
import ru.instamart.kraken.service.testit.model.autotest.AutotestPut;
import ru.instamart.kraken.service.testit.model.testrun.TestRunResult;
import ru.testit.client.api.AttachmentsApi;
import ru.testit.client.api.AutoTestsApi;
import ru.testit.client.api.ProjectsApi;
import ru.testit.client.api.TestRunsApi;
import ru.testit.client.invoker.ApiException;
import ru.testit.client.invoker.Configuration;
import ru.testit.client.model.*;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Slf4j
public final class TestItClient {

    private static final String AUTH_PREFIX = "PrivateToken";

    private final List<AutoTestResultsForTestRunModel> autotestResult = Collections.synchronizedList(new ArrayList<>());

    private final TestRunsApi testRunsApi;
    private final AutoTestsApi autoTestsApi;
    private final AttachmentsApi attachmentsApi;
    private final ProjectsApi projectsApi;
    @Getter
    private final UserInfoService userInfoService;

    public TestItClient() {
        this(TestItProperties.URL, TestItProperties.PRIVATE_TOKEN);
    }

    public TestItClient(final String url, final String token) {
        final var defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(Utils.urlTrim(url));
        defaultClient.setApiKeyPrefix(AUTH_PREFIX);
        defaultClient.setApiKey(token);

        this.testRunsApi = new TestRunsApi(defaultClient);
        this.autoTestsApi = new AutoTestsApi(defaultClient);
        this.attachmentsApi = new AttachmentsApi(defaultClient);
        this.projectsApi = new ProjectsApi(defaultClient);
        this.userInfoService = new UserInfoService();
    }

    public TestRunV2GetModel createEmpty(final TestRunV2PostShortModel testRunV2PostShortModel) {
        try {
            return testRunsApi.createEmpty(testRunV2PostShortModel);
        } catch (ApiException e) {
            log.error("Can't create test run for project={} with name={}", TestItProperties.PROJECT_ID, TestItProperties.TEST_RUN_NAME, e);
        }
        return new TestRunV2GetModel();
    }

    public void startTestRun(final TestRunV2GetModel testRunV2GetModel) {
        try {
            testRunsApi.startTestRun(nonNull(testRunV2GetModel) ? testRunV2GetModel.getId() : UUID.fromString(TestItProperties.TEST_RUN_ID));
        } catch (ApiException e) {
            log.error("Can't start test run for project={} with name={}", TestItProperties.PROJECT_ID, TestItProperties.TEST_RUN_NAME, e);
        }
    }

    public void updateAutoTest(final AutotestPut autoTestPutModel, final ITestResult testResult, final File... attachment) {
        try {
            autoTestsApi.updateAutoTest(autoTestPutModel);
            autotestResult.add(new TestRunResult(autoTestPutModel.getExternalId(), testResult, addAttachment(attachment)));
        } catch (ApiException e) {
            log.error("Can't update auto test={}", autoTestPutModel, e);
        }
    }

    public AutoTestModel createAutoTest(final AutotestPost model, final ITestResult testResult, final File... attachment) {
        try {
            final var result = autoTestsApi.createAutoTest(model);
            autotestResult.add(new TestRunResult(result.getExternalId(), testResult, addAttachment(attachment)));
            return result;
        } catch (ApiException e) {
            log.error("Can't create auto test={}", model, e);
        }
        return new AutoTestModel();
    }

    public void linkAutoTestToWorkItem(final String id, final String workItemId) {
        try {
            autoTestsApi.linkAutoTestToWorkItem(id, new WorkItemIdModel().id(workItemId));
        } catch (ApiException e) {
            log.error("Can't link autotest={} to case={}", id, workItemId, e);
        }
    }

    public void sendTestResult() {
        if (isTestRunNotExist()) return;

        if (autotestResult.isEmpty()) {
            log.error("Test Result is empty");
            return;
        }

        final var run_uuid = UUID.fromString(TestItProperties.TEST_RUN_ID);
        try {
            testRunsApi.setAutoTestResultsForTestRun(run_uuid, autotestResult);
            log.debug("Send test result for test run={}", run_uuid);
        } catch (ApiException e) {
            log.error("Failed send test result={} for test run={}", autotestResult, run_uuid, e);
        }
    }

    public void completeTestRun() {
        if (isTestRunNotExist()) return;

        final var run_uuid = UUID.fromString(TestItProperties.TEST_RUN_ID);
        try {
            testRunsApi.completeTestRun(run_uuid);
            log.debug("Complete test run={}", run_uuid);
        } catch (ApiException e) {
            log.error("Can't complete test run={}", run_uuid, e);
        }
    }

    public List<AttachmentPutModel> addAttachment(final File... attachments) {
        return Arrays.stream(attachments)
                .map(attachment -> {
                    try {
                        final var attach = attachmentsApi.apiV2AttachmentsPost(attachment);
                        log.debug("Add attachment={}", attach);
                        return new AttachmentPutModel().id(attach.getId());
                    } catch (ApiException e) {
                        log.error("Can't add attachment={} to testrun={}", attachment.getName(), UUID.fromString(TestItProperties.TEST_RUN_ID), e);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<String> getTestFromTestRun() {
        try {
            final var testsForRun = getTestFromTestRun(TestItProperties.TEST_RUN_ID, TestItProperties.CONFIGURATION_ID);
            log.debug("List of tests from test run: {}", testsForRun);

            return testsForRun;
        } catch (ApiException e) {
            log.error("Could not get tests from test run", e);
        }
        return Collections.emptyList();
    }

    public List<String> getTestFromTestRun(final String testRunUuid, final String configurationId) throws ApiException {
        final var model = testRunsApi.getTestRunById(UUID.fromString(testRunUuid));
        final var configUUID = UUID.fromString(configurationId);

        if (model.getTestResults().size() == 0) {
            return Collections.emptyList();
        }

        return model.getTestResults().stream()
                .filter(result -> result.getConfigurationId().equals(configUUID))
                //externalId == globalId - или если проще этот ID кейса
                .map(result -> result.getAutoTest().getExternalId()).collect(Collectors.toList());
    }

    public AutoTestModel getAutotestByExternalId(final String externalId) {
        final List<AutoTestModel> tests;
        try {
            tests = autoTestsApi.getAllAutoTests(UUID.fromString(TestItProperties.PROJECT_ID),
                    externalId, null, null, null, null,
                    null, null, null, null, null, null,
                    null, null, null, null, null, null,
                    true, true, null, null, null, null, null);
        } catch (ApiException e) {
            log.error("Can't obtain test from project={} by globalId={}", TestItProperties.PROJECT_ID, externalId, e);
            return null;
        }

        return tests.stream().findFirst().orElse(null);
    }

    public List<WorkItemShortModel> getWorkItems(final String id) {
        try {
            return projectsApi
                    .getWorkItemsByProjectIdWithHttpInfo(id, false, null, false,
                            0, 5000, null, null, null)
                    .getData();
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isTestRunNotExist() {
        return TestItProperties.TEST_RUN_ID.equals("null");
    }
}
