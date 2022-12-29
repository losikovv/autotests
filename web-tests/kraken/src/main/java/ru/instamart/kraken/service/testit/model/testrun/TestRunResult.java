package ru.instamart.kraken.service.testit.model.testrun;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.ITestResult;
import ru.instamart.kraken.config.TestItProperties;
import ru.instamart.kraken.service.testit.Utils;
import ru.testit.client.model.AttachmentPutModel;
import ru.testit.client.model.AutoTestResultsForTestRunModel;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;
import static ru.instamart.kraken.util.TimeUtil.dateToOffsetDateTime;

public final class TestRunResult extends AutoTestResultsForTestRunModel {

    public TestRunResult(final String autotestId, final ITestResult testResult, final List<AttachmentPutModel> attachments) {
        this.setConfigurationId(UUID.fromString(TestItProperties.CONFIGURATION_ID));
        this.setLinks(Collections.emptyList());
        this.setAutoTestExternalId(autotestId);
        this.setStartedOn(dateToOffsetDateTime(testResult.getStartMillis()));
        this.setCompletedOn(dateToOffsetDateTime(testResult.getEndMillis()));
        this.setDuration(testResult.getEndMillis() - testResult.getStartMillis());
        this.setOutcome(Utils.statusConverter(testResult.getStatus()));
        this.setStepResults(Collections.emptyList());
        this.setParameters(Collections.emptyMap());

        final var throwable = testResult.getThrowable();
        if (nonNull(throwable)) {
            this.setMessage(throwable.getMessage());
            this.setTraces(ExceptionUtils.getStackTrace(throwable));
        }

        if (nonNull(attachments) && !attachments.isEmpty())
            this.attachments(attachments);
    }
}
