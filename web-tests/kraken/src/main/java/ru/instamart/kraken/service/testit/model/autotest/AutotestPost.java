package ru.instamart.kraken.service.testit.model.autotest;

import org.testng.ITestResult;
import ru.instamart.kraken.config.TestItProperties;
import ru.instamart.kraken.service.testit.Utils;
import ru.testit.client.model.AutoTestPostModel;

import java.util.Collections;
import java.util.UUID;

import static ru.instamart.kraken.service.testit.Utils.getCaseId;

public class AutotestPost extends AutoTestPostModel {

    public AutotestPost(final ITestResult testResult) {
        this.setProjectId(UUID.fromString(TestItProperties.PROJECT_ID));
        this.setExternalId(getCaseId(testResult));
        this.setDescription(Utils.getDescription(testResult));
        this.setName(testResult.getName());
        this.setClassname(testResult.getTestClass().getRealClass().getName());
        this.setNamespace(testResult.getTestClass().getRealClass().getName());
        this.setTitle(testResult.getMethod().getMethodName());
        this.setLinks(Collections.emptyList());
        this.setSteps(Collections.emptyList());
        this.setLabels(Collections.emptyList());
    }
}
