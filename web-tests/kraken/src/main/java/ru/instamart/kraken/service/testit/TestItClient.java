package ru.instamart.kraken.service.testit;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.config.TestItProperties;
import ru.testit.client.api.AttachmentsApi;
import ru.testit.client.api.AutoTestsApi;
import ru.testit.client.api.TestRunsApi;
import ru.testit.client.invoker.ApiClient;
import ru.testit.client.invoker.ApiException;
import ru.testit.client.invoker.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public final class TestItClient {

    private static final String AUTH_PREFIX = "PrivateToken";

    @Getter
    private final ApiClient defaultClient;
    @Getter
    private final TestRunsApi testRunsApi;
    @Getter
    private final AutoTestsApi autoTestsApi;
    @Getter
    private final AttachmentsApi attachmentsApi;

    public TestItClient() {
        this.defaultClient = Configuration.getDefaultApiClient();
        this.defaultClient.setBasePath(TestItProperties.URL);
        this.defaultClient.setApiKeyPrefix(AUTH_PREFIX);
        this.defaultClient.setApiKey(TestItProperties.PRIVATE_TOKEN);

        this.testRunsApi = new TestRunsApi(defaultClient);
        this.autoTestsApi = new AutoTestsApi(defaultClient);
        this.attachmentsApi = new AttachmentsApi(defaultClient);
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
                //TODO: Можно попробовать с globalId
                .map(result -> result.getAutoTest().getExternalId()).collect(Collectors.toList());
    }
}
