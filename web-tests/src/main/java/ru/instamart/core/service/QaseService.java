package ru.instamart.core.service;

import io.qameta.allure.TmsLink;
import io.qase.api.QaseApi;
import io.qase.api.annotation.CaseId;
import io.qase.api.enums.Automation;
import io.qase.api.enums.RunResultStatus;
import io.qase.api.exceptions.QaseException;
import io.qase.api.models.v1.attachments.Attachment;
import io.qase.api.models.v1.defects.Defect;
import io.qase.api.models.v1.suites.Suite;
import io.qase.api.models.v1.testplans.TestPlan;
import io.qase.api.models.v1.testrunresults.TestRunResult;
import io.qase.api.models.v1.testruns.TestRun;
import io.qase.api.services.TestCaseService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestResult;
import ru.instamart.core.helpers.HelperBase;
import ru.instamart.core.settings.Config;
import ru.instamart.core.util.Crypt;
import ru.instamart.ui.common.pagesdata.EnvironmentData;

import java.io.File;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.qase.api.utils.IntegrationUtils.getStacktrace;

@Slf4j
public final class QaseService {

    private static final String DESCRIPTION_PREFIX = "[Automation] ";
    private static final Pattern ATTACHMENT_PATTERN_HASH = Pattern.compile(".+/(.+)/.+$");
    private static final LocalDateTime DAYS_TO_DIE = LocalDateTime.now().minusWeeks(4);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final int PLAN_ID = Integer.parseInt(System.getProperty("qase.plan.id","0"));
    private final int SUITE_ID = Integer.parseInt(System.getProperty("qase.suite.id","0"));
    private final String PIPELINE_URL = System.getProperty("pip_url", "https://gitlab.sbermarket.tech/qa/automag/-/pipelines");
    private boolean qase = Boolean.parseBoolean(System.getProperty("qase","false"));

    private final String projectCode;
    @Getter
    private final QaseApi qaseApi;
    private final List<Integer> testCasesList;
    private boolean started = false;

    private String testRunName;
    private Long runId;

    public QaseService(final String projectCode) {
        this.projectCode = projectCode;
        this.qaseApi = new QaseApi(Crypt.INSTANCE.decrypt(Config.QASE_API_TOKEN));
        this.testCasesList = new ArrayList<>();
    }

    /**
     * Если PLAN_ID указан, то из тест плана получаем список тест-кейсов
     * Если PLAN_ID не указан, но suiteId указан, то из сьюта получаем automated кейсы
     * Если SUITE_ID не указан, то из всего проекта получаем automated кейсы
     */
    public void generateTestCasesList() {
        if (!qase || projectCode == null) {
            return;
        }

        final TestCaseService.Filter filter = qaseApi
                .testCases()
                .filter()
                .automation(Automation.automated);

        if (PLAN_ID != 0) {
            final TestPlan testPlan = qaseApi
                    .testPlans()
                    .get(projectCode, PLAN_ID);

            testRunName = testPlan.getTitle();
            testPlan.getCases()
                    .forEach(testCase -> testCasesList.add((int) testCase.getCaseId()));
        } else if (SUITE_ID != 0) {
            filter.suiteId(SUITE_ID);
            testRunName = qaseApi.suites().get(projectCode, SUITE_ID).getTitle();
            addTestCasesToList(filter);

            final List<Suite> suites = qaseApi.suites()
                    .getAll(projectCode)
                    .getSuiteList();
            addTestCasesFromChildSuite(filter, SUITE_ID, suites);
        } else {
            testRunName = qaseApi.projects().get(projectCode).getTitle();
            addTestCasesToList(filter);
        }
        if (testCasesList.isEmpty()) {
            qase = false;
        }
    }

    /**
     * Создаём тест-ран с полученными кейсами
     */
    public void createTestRun() {
        if (!qase || projectCode == null || started) return;
        started = true;

        final Integer[] casesArray = new Integer[testCasesList.size()];
        runId = qaseApi.testRuns().create(
                projectCode,
                testRunName + " [" + EnvironmentData.INSTANCE.getName() + "] " + LocalDate.now(),
                null,
                DESCRIPTION_PREFIX + PIPELINE_URL,
                testCasesList.toArray(casesArray));
        log.info("Create Test run={} for project={}", runId, projectCode);
    }

    /**
     * Отправляем статус прохождения теста
     */
    public void sendResult(final ITestResult result, final RunResultStatus status) {
        this.sendResult(result, status, null);
    }

    public void sendResult(final ITestResult result, final RunResultStatus status, final List<String> attachmentHash) {
        if (!qase) return;

        final Duration timeSpent = Duration
                .ofMillis(result.getEndMillis() - result.getStartMillis());
        final Optional<Throwable> resultThrowable = Optional
                .ofNullable(result.getThrowable());
        final String comment = resultThrowable
                .flatMap(throwable -> Optional.of(throwable.toString()))
                .orElse(null);
        final Boolean isDefect = resultThrowable
                .flatMap(throwable -> Optional.of(throwable instanceof AssertionError))
                .orElse(false);
        final String stacktrace = resultThrowable
                .flatMap(throwable -> Optional.of(getStacktrace(throwable)))
                .orElse(null);

        final Long caseId = getCaseId(result);
        if (caseId != null) {
            try {
                qaseApi.testRunResults()
                        .create(projectCode,
                                runId,
                                caseId,
                                status,
                                timeSpent,
                                null,
                                comment,
                                stacktrace,
                                isDefect,
                                attachmentHash
                        );
            } catch (QaseException e) {
                log.error("FATAL: can't update test run [caseId={} run={} project={}]", caseId, runId, projectCode, e);
            }
        }
    }

    public List<String> uploadScreenshot() {
        final File file = HelperBase.takeScreenshotFile();
        return qaseApi.attachments()
                .add(projectCode, file)
                .stream()
                .map(Attachment::getHash)
                .collect(Collectors.toList());
    }

    public void deleteOldTestRuns() {
        final List<TestRun> testRunList = qaseApi.testRuns()
                .getAll(projectCode, true)
                .getTestRunList();

        testRunList.forEach(testRun -> {
            if ((testRun.getDescription() != null && testRun.getDescription().contains(DESCRIPTION_PREFIX))
                    && (testRun.getStartTime() != null && DAYS_TO_DIE.isAfter(testRun.getStartTime()))) {
                final List<TestRunResult> testRunResults = qaseApi
                        .testRunResults()
                        .getAll(projectCode, 100, 0, qaseApi.testRunResults().filter().run((int) testRun.getId()))
                        .getTestRunResultList();
                testRunResults.forEach(testRunResult -> testRunResult.getAttachments().forEach(attachment -> {
                    final Matcher matcher = ATTACHMENT_PATTERN_HASH.matcher(attachment.getUrl());
                    if (matcher.find()) {
                        qaseApi.attachments().delete(matcher.group(1));
                    }
                }));
                qaseApi.testRuns().delete(projectCode, testRun.getId());
                log.info("Delete old test run={} for project={}", testRun.getId(), testRun.getTitle());
            }
        });
    }

    public void deleteOldDefects() {
        if (!qase) return;
        final List<Defect> defects = qaseApi.defects().getAll(projectCode).getDefectList();
        defects.forEach(defect -> {
            final LocalDateTime dateTime = LocalDateTime.parse(defect.getCreated(), FORMATTER);
            // userId = 2 это пользователь от которого создаются дефекты для автотестов
            if (defect.getUserId() == 2 && DAYS_TO_DIE.isAfter(dateTime)) {
                qaseApi.defects().delete(projectCode, defect.getId());
            }
        });
    }

    public void completeTestRun() {
        if (!qase) return;
        log.info("Complete test run={} for project={}", runId, projectCode);
        qaseApi.testRuns().completeTestRun(projectCode, runId);
    }

    private void addTestCasesFromChildSuite(final TestCaseService.Filter filter, final int parentId, final List<Suite> suites) {
        suites.forEach(suite -> {
            if (suite.getParentId() != null && suite.getParentId() == parentId) {
                int suiteId = (int) suite.getId();
                filter.suiteId(suiteId);
                addTestCasesToList(filter);
                addTestCasesFromChildSuite(filter, suiteId, suites);
            }
        });
    }

    private void addTestCasesToList(final TestCaseService.Filter filter) {
        qaseApi.testCases()
                .getAll(projectCode, filter)
                .getTestCaseList()
                .forEach(testCase -> testCasesList.add((int) testCase.getId()));
    }

    /**
     * Получаем CaseId из аннотации теста
     */
    private Long getCaseId(final ITestResult result) {
        final Method method = result
                .getMethod()
                .getConstructorOrMethod()
                .getMethod();
        if (method.isAnnotationPresent(CaseId.class)) {
            return method
                    .getDeclaredAnnotation(CaseId.class).value();
        } else if (method.isAnnotationPresent(TmsLink.class)) {
            try {
                return Long.valueOf(method
                        .getDeclaredAnnotation(TmsLink.class).value());
            } catch (NumberFormatException e) {
                log.error("String could not be parsed as Long", e);
            }
        }
        return null;
    }
}

