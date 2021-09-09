package ru.instamart.kraken.service;

import io.qameta.allure.TmsLink;
import io.qase.api.QaseApi;
import io.qase.api.annotation.CaseIDs;
import io.qase.api.annotation.CaseId;
import io.qase.api.enums.Automation;
import io.qase.api.enums.RunResultStatus;
import io.qase.api.exceptions.QaseException;
import io.qase.api.models.v1.attachments.Attachment;
import io.qase.api.models.v1.defects.Defect;
import io.qase.api.models.v1.suites.Suite;
import io.qase.api.models.v1.testcases.TestCase;
import io.qase.api.models.v1.testplans.TestPlan;
import io.qase.api.models.v1.testrunresults.TestRunResult;
import io.qase.api.models.v1.testruns.TestRun;
import io.qase.api.services.TestCaseService;
import io.qase.api.services.TestRunResultService;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestResult;
import org.testng.internal.TestResult;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.io.File;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.qase.api.utils.IntegrationUtils.getStacktrace;
import static ru.instamart.kraken.helper.DateTimeHelper.getDateFromMSK;

@Slf4j
@Accessors(chain = true)
public final class QaseService {

    private static final String DESCRIPTION_PREFIX = "[Automation] ";
    private static final Pattern ATTACHMENT_PATTERN_HASH = Pattern.compile(".+/(.+)/.+$");
    private static final LocalDateTime DAYS_TO_DIE = LocalDateTime.now().minusWeeks(3);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Deprecated
    private final int PLAN_ID = Integer.parseInt(System.getProperty("qase.plan.id", "0"));
    @Deprecated
    private final int SUITE_ID = Integer.parseInt(System.getProperty("qase.suite.id", "0"));
    private final String PIPELINE_URL = System.getProperty("pip_url", "https://gitlab.sbermarket.tech/qa/automag/-/pipelines");
    @Setter
    private String projectCode;
    @Getter
    private final QaseApi qaseApi;
    @Deprecated
    private final List<Integer> testCasesList;
    private boolean qase = Boolean.parseBoolean(System.getProperty("qase", "false"));
    private boolean started = false;

    private String testRunName;
    private Long runId;

    public QaseService() {
        this("", "test");
    }

    public QaseService(final String projectCode, final String testRunName) {
        this.projectCode = projectCode;
        this.testRunName = testRunName;
        this.qaseApi = new QaseApi(CoreProperties.QASE_API_TOKEN);
        this.testCasesList = new ArrayList<>();
    }

    /**
     * Если PLAN_ID указан, то из тест плана получаем список тест-кейсов
     * Если PLAN_ID не указан, но SUITE_ID указан, то из сьюта получаем automated кейсы
     * Если SUITE_ID не указан, то из всего проекта получаем automated кейсы
     */
    @Deprecated
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

        try {
            runId = qaseApi.testRuns().create(
                    projectCode,
                    testRunName + " [" + EnvironmentProperties.Env.ENV_NAME + "] " + getDateFromMSK(),
                    null,
                    DESCRIPTION_PREFIX + PIPELINE_URL);
            log.info("Create Test run={} for project={}", runId, projectCode);
        } catch (Exception e) {
            log.error("FATAL: Create Test run failed with error ", e);
        }
    }

    /**
     * Отправляем статус прохождения теста
     */
    public void sendResult(final ITestResult result, final RunResultStatus status) {
        this.sendResult(result, status, null);
    }

    synchronized public void sendResult(final ITestResult result,
                                        final RunResultStatus status,
                                        final List<String> attachmentHash) {
        if (!qase) return;

        final Duration timeSpent = Duration
                .ofMillis(result.getEndMillis() - result.getStartMillis());
        final Optional<Throwable> resultThrowable = Optional
                .ofNullable(result.getThrowable());
        final String comment = resultThrowable
                .flatMap(throwable -> Optional.of(throwable.toString()))
                .orElse("");
        final Boolean isDefect = resultThrowable
                .flatMap(throwable -> Optional.of(throwable instanceof AssertionError))
                .orElse(false);
        final String stacktrace = resultThrowable
                .flatMap(throwable -> Optional.of(getStacktrace(throwable)))
                .orElse(null);

        final Long caseId = getCaseId(result);
        if (caseId != null) {
            createTestResult(caseId, status, timeSpent, comment, stacktrace, isDefect, attachmentHash);
        }

        final CaseId[] caseIDs = getCaseIDs(result);

        if (caseIDs != null && result instanceof TestResult) {
            int index = ((TestResult) result).getParameterIndex();
            Long caseIdItem = caseIDs[index].value();
            if (caseIdItem != null) {
                createTestResult(caseIdItem, status, timeSpent, comment, stacktrace, isDefect, attachmentHash);
            }
        }
    }

    private void createTestResult(final Long caseId, final RunResultStatus status, final Duration timeSpent,
                                  final String comment, final String stacktrace, final Boolean isDefect,
                                  final List<String> attachmentHash) {
        try {
            qaseApi.testRunResults()
                    .create(projectCode,
                            runId,
                            caseId,
                            status,
                            timeSpent,
                            null,
                            DESCRIPTION_PREFIX + comment,
                            stacktrace,
                            isDefect,
                            attachmentHash
                    );
        } catch (Exception e) {
            log.error("FATAL: can't update test run [caseId={} run={} project={}]", caseId, runId, projectCode, e);
        }
    }

    public List<String> uploadScreenshot(final File file) {
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
                try {
                    qaseApi.testRuns().delete(projectCode, testRun.getId());
                    log.info("Delete old test run={} for project={}", testRun.getId(), testRun.getTitle());
                } catch (Exception e) {
                    log.warn("Delete old test failed run={} for project={}", testRun.getId(), testRun.getTitle());
                }
            }
        });
    }

    public void deleteOldDefects() {
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

    public void actualizeAutomatedTestCases() {
        log.info("Актуализация автоматизированных тест кейсов:");
        try {
            List<TestCase> allTestCases = new ArrayList<>();
            int testCasesSize;
            int offset = 0;
            do {
                try {
                    log.info("Получаем {} страницу тест-кейсов", offset/100+1);
                    List<TestCase> testCases = qaseApi
                            .testCases()
                            .getAll(projectCode, 100, offset)
                            .getTestCaseList();
                    allTestCases.addAll(testCases);
                    testCasesSize = testCases.size();
                    log.info("Получили {} тест-кейсов", testCasesSize);
                } catch (QaseException qaseException) {
                    log.error("Something went wrong: " + qaseException);
                    testCasesSize = 0;
                }
                offset += 100;
            } while (testCasesSize > 0);

            int automatedNumber = 0;
            int actualizedNumber = 0;
            for (TestCase testCase : allTestCases) {
                try {
                    TestRunResultService.Filter filter = qaseApi
                            .testRunResults()
                            .filter()
                            .caseId((int) testCase.getId())
                            .fromEndTime(LocalDateTime.now().minusDays(2));
                    log.info("Получаем последние результаты прогонов теста " + testCase.getTitle());
                    List<TestRunResult> testRunResults = qaseApi
                            .testRunResults()
                            .getAll(projectCode, 100, 0, filter)
                            .getTestRunResultList();

                    boolean automated = false;
                    for (TestRunResult testRunResult : testRunResults) {
                        if (testRunResult.getComment() != null && testRunResult.getComment().startsWith(DESCRIPTION_PREFIX.trim())) {
                            automated = true;
                            automatedNumber++;
                            break;
                        }
                    }
                    if (testCase.getAutomation() == 2 && !automated) {
                        log.info("Указываем, что тест не автоматизирован");
                        qaseApi.testCases().update(projectCode, (int) testCase.getId(), Automation.is_not_automated);
                        actualizedNumber++;
                    } else if (testCase.getAutomation() != 2 && automated) {
                        log.info("Указываем, что тест автоматизирован");
                        qaseApi.testCases().update(projectCode, (int) testCase.getId(), Automation.automated);
                        actualizedNumber++;
                    }
                } catch (QaseException qaseException) {
                    log.error("Something went wrong: " + qaseException);
                }
            }
            log.info("Всего тест кейсов: {}", allTestCases.size());
            log.info("Всего автоматизировано: {}", automatedNumber);
            log.info("Сейчас актуализировано: {}", actualizedNumber);
        } catch (Exception e) {
            log.error("FATAL: something went wrong when try to actualize test cases", e);
        }
    }

    @Deprecated
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

    @Deprecated
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

    /**
     * Получаем список CaseId
     *
     * @param result
     * @return
     */
    private CaseId[] getCaseIDs(final ITestResult result) {
        final Method method = result
                .getMethod()
                .getConstructorOrMethod()
                .getMethod();
        if (method.isAnnotationPresent(CaseIDs.class)) {
            return method
                    .getDeclaredAnnotation(CaseIDs.class).value();
        }
        return null;
    }
}

