package instamart.core.listeners;

import instamart.core.common.AppManager;
import io.qameta.allure.TmsLink;
import io.qase.api.QaseApi;
import io.qase.api.annotation.CaseId;
import io.qase.api.enums.Automation;
import io.qase.api.enums.RunResultStatus;
import io.qase.api.exceptions.QaseException;
import io.qase.api.models.v1.suites.Suite;
import io.qase.api.models.v1.testplans.TestPlan;
import io.qase.api.models.v1.testruns.TestRun;
import io.qase.api.models.v1.testruns.TestRuns;
import io.qase.api.services.TestCaseService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.qase.api.utils.IntegrationUtils.getStacktrace;

/**
 * Листнер для отправки результатов в QASE
 */
public class TmsListener implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(TmsListener.class);
    private final QaseApi qaseApi = new QaseApi("c6f13d354e35e6561f66981f856b71a16daf8ba1");
    private boolean qase = Boolean.parseBoolean(System.getProperty("qase","false"));
    private static String projectCode;
    private String testRunName;
    private Long runId;
    private List<Integer> testCasesList = new ArrayList<>();
    private String hash;

    public static void setProjectCode(String projectCode) {
        TmsListener.projectCode = projectCode;
    }

    @Override
    public void onStart(ITestContext context) {
        if (projectCode == null) qase = false;
        generateTestCasesList();
        createTestRun();
    }

    @Override
    public void onFinish(ITestContext context) {
    }

    @Override
    public void onTestStart(ITestResult result) {
        hash = sendTestInProgress(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        sendResult(result, RunResultStatus.passed);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        sendResult(result, RunResultStatus.failed);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        sendResult(result, RunResultStatus.blocked);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    /**
     * Если planId указан, то из тест плана получаем список тест-кейсов
     * Если planId не указан, но suiteId указан, то из сьюта получаем automated кейсы
     * Если suiteId не указан, то из всего проекта получаем automated кейсы
     */
    private void generateTestCasesList() {
        if (!qase) return;
        TestCaseService.Filter filter = qaseApi
                .testCases()
                .filter()
                .automation(Automation.automated);
        int planId = Integer.parseInt(System.getProperty("qase.plan.id","0"));
        int suiteId = Integer.parseInt(System.getProperty("qase.suite.id","0"));
        if (planId != 0) {
            TestPlan testPlan = qaseApi
                    .testPlans()
                    .get(projectCode, planId);
            testRunName = testPlan.getTitle();
            testPlan.getCases()
                    .forEach(testCase -> testCasesList.add((int) testCase.getCaseId()));
        } else if (suiteId != 0) {
            filter.suiteId(suiteId);
            testRunName = qaseApi.suites().get(projectCode, suiteId).getTitle();
            addTestCasesToList(filter);
            List<Suite> suites = qaseApi.suites()
                    .getAll(projectCode)
                    .getSuiteList();
            addTestCasesFromChildSuite(filter, suiteId, suites);
        } else {
            testRunName = qaseApi.projects().get(projectCode).getTitle();
            addTestCasesToList(filter);
        }
        if (testCasesList.size() == 0) qase = false;
    }

    private void addTestCasesFromChildSuite(TestCaseService.Filter filter, int parentId, List<Suite> suites) {
        for (Suite suite : suites) {
            if (suite.getParentId() != null && suite.getParentId() == parentId) {
                int suiteId = (int) suite.getId();
                filter.suiteId(suiteId);
                addTestCasesToList(filter);
                addTestCasesFromChildSuite(filter, suiteId, suites);
            }
        }
    }

    private void addTestCasesToList(TestCaseService.Filter filter) {
        qaseApi.testCases()
                .getAll(projectCode, filter)
                .getTestCaseList()
                .forEach(testCase -> testCasesList.add((int) testCase.getId()));
    }

    /**
     * Создаём тест-ран с полученными кейсами
     */
    private void createTestRun() {
        if (!qase) return;
        Integer[] casesArray = new Integer[testCasesList.size()];
        runId = qaseApi.testRuns().create(
                projectCode,
                testRunName + " [" + AppManager.environment.getName() + "] " + LocalDate.now(),
                testCasesList.toArray(casesArray));
    }

    /**
     * Отправляем инфу о том, что тест начат (статус "in_progress")
     * @return получаем String c хэшэм для последующего апдейта результата
     */
    private String sendTestInProgress(ITestResult result) {
        if (!qase) return null;
        Long caseId = getCaseId(result);
        if (caseId != null) {
            try {
                return qaseApi.testRunResults()
                        .create(projectCode,
                                runId,
                                caseId,
                                RunResultStatus.in_progress);
            } catch (QaseException e) {
                logger.error(e.getMessage());
            }
        }
        return null;
    }

    /**
     * Отправляем статус прохождения теста
     */
    private void sendResult(ITestResult result, RunResultStatus status) {
        if (!qase) return;
        Duration timeSpent = Duration
                .ofMillis(result.getEndMillis() - result.getStartMillis());
        Optional<Throwable> resultThrowable = Optional
                .ofNullable(result.getThrowable());
        String comment = resultThrowable
                .flatMap(throwable -> Optional.of(throwable.toString()))
                .orElse(null);
        Boolean isDefect = resultThrowable
                .flatMap(throwable -> Optional.of(throwable instanceof AssertionError))
                .orElse(false);
        String stacktrace = resultThrowable
                .flatMap(throwable -> Optional.of(getStacktrace(throwable)))
                .orElse(null);

        Long caseId = getCaseId(result);
        if (caseId != null) {
            try {
                if (hash == null) {
                    qaseApi.testRunResults()
                            .create(projectCode,
                                    runId,
                                    caseId,
                                    status,
                                    timeSpent,
                                    null,
                                    comment,
                                    stacktrace,
                                    isDefect);
                } else {
                    qaseApi.testRunResults()
                            .update(projectCode,
                                    runId,
                                    hash,
                                    status,
                                    timeSpent,
                                    null,
                                    stacktrace,
                                    isDefect);
                }
            } catch (QaseException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * Получаем CaseId из аннотации теста
     */
    private Long getCaseId(ITestResult result) {
        Method method = result
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
                logger.error("String could not be parsed as Long", e);
            }
        }
        return null;
    }

    /**
     * ЗАПУСК ТЕСТА УДАЛЯЕТ ВСЕ ТЕСТРАНЫ У УКАЗАННОГО ПРОЕКТА
     */
    @Test
    public void deleteAllProjectTestRuns() {
        setProjectCode("");
        TestRuns testRuns = qaseApi
                .testRuns()
                .getAll(projectCode, true);
        List<TestRun> testRunList = testRuns.getTestRunList();
        testRunList.forEach(testRun -> qaseApi.testRuns().delete(projectCode, testRun.getId()));
    }
}
