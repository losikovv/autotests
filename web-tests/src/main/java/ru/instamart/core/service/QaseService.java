package instamart.core.service;

import instamart.core.helpers.HelperBase;
import instamart.core.settings.Config;
import instamart.core.util.Crypt;
import instamart.ui.common.pagesdata.EnvironmentData;
import io.qameta.allure.TmsLink;
import io.qase.api.QaseApi;
import io.qase.api.QaseApiClient;
import io.qase.api.annotation.CaseId;
import io.qase.api.enums.Automation;
import io.qase.api.enums.RunResultStatus;
import io.qase.api.exceptions.QaseException;
import io.qase.api.inner.GsonObjectMapper;
import io.qase.api.models.v1.attachments.Attachment;
import io.qase.api.models.v1.suites.Suite;
import io.qase.api.models.v1.testplans.TestPlan;
import io.qase.api.services.TestCaseService;
import kong.unirest.Unirest;
import kong.unirest.UnirestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;

import java.io.File;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.qase.api.utils.IntegrationUtils.getStacktrace;

public final class QaseService {

    private static final Logger logger = LoggerFactory.getLogger(QaseService.class);

    private final String projectCode;
    private final QaseApi qaseApi;
    private final List<Integer> testCasesList;
    private boolean started = false;
    private final QaseTestRunResultService qaseTestRunResultService;

    private boolean qase = Boolean.parseBoolean(System.getProperty("qase","false"));
    private String testRunName;
    private Long runId;

    public QaseService(final String projectCode) {
        this.projectCode = projectCode;
        this.qaseApi = new QaseApi(Crypt.INSTANCE.decrypt(Config.QASE_API_TOKEN));
        this.testCasesList = new ArrayList<>();

        // костыль, пока в https://github.com/qase-tms/qase-java/tree/master/qase-api не реализуют добавление скриншотов
        UnirestInstance unirestInstance = Unirest.spawnInstance();
        unirestInstance.config()
                .setObjectMapper(new GsonObjectMapper())
                .addShutdownHook(true)
                .setDefaultHeader("Token", Crypt.INSTANCE.decrypt(Config.QASE_API_TOKEN));
        this.qaseTestRunResultService = new QaseTestRunResultService(
                new QaseApiClient(unirestInstance, "https://api.qase.io/v1"));
    }

    /**
     * Если planId указан, то из тест плана получаем список тест-кейсов
     * Если planId не указан, но suiteId указан, то из сьюта получаем automated кейсы
     * Если suiteId не указан, то из всего проекта получаем automated кейсы
     */
    public void generateTestCasesList() {
        if (!qase || projectCode == null) return;

        final TestCaseService.Filter filter = qaseApi
                .testCases()
                .filter()
                .automation(Automation.automated);
        final int planId = Integer.parseInt(System.getProperty("qase.plan.id","0"));
        final int suiteId = Integer.parseInt(System.getProperty("qase.suite.id","0"));

        if (planId != 0) {
            final TestPlan testPlan = qaseApi
                    .testPlans()
                    .get(projectCode, planId);

            testRunName = testPlan.getTitle();
            testPlan.getCases()
                    .forEach(testCase -> testCasesList.add((int) testCase.getCaseId()));
        } else if (suiteId != 0) {
            filter.suiteId(suiteId);
            testRunName = qaseApi.suites().get(projectCode, suiteId).getTitle();
            addTestCasesToList(filter);

            final List<Suite> suites = qaseApi.suites()
                    .getAll(projectCode)
                    .getSuiteList();
            addTestCasesFromChildSuite(filter, suiteId, suites);
        } else {
            testRunName = qaseApi.projects().get(projectCode).getTitle();
            addTestCasesToList(filter);
        }
        if (testCasesList.isEmpty()) qase = false;
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
                testCasesList.toArray(casesArray));
    }

    /**
     * Отправляем статус прохождения теста
     */
    public void sendResult(final ITestResult result,    final RunResultStatus status) {
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
                                isDefect);
            } catch (QaseException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * Отправляем статус прохождения теста с аттачем
     */
    public void sendResult(final ITestResult result,
                           final RunResultStatus status,
                           final List<String> attachmentsHash) {
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
                qaseTestRunResultService
                        .create(projectCode,
                                runId,
                                caseId,
                                status,
                                timeSpent,
                                null,
                                comment,
                                stacktrace,
                                isDefect,
                                attachmentsHash);
            } catch (QaseException e) {
                logger.error(e.getMessage());
            }
        }
    }

    public QaseApi getQaseApi() {
        return qaseApi;
    }

    private void addTestCasesFromChildSuite(final TestCaseService.Filter filter, final int parentId, final List<Suite> suites) {
        for (Suite suite : suites) {
            if (suite.getParentId() != null && suite.getParentId() == parentId) {
                int suiteId = (int) suite.getId();
                filter.suiteId(suiteId);
                addTestCasesToList(filter);
                addTestCasesFromChildSuite(filter, suiteId, suites);
            }
        }
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
                logger.error("String could not be parsed as Long", e);
            }
        }
        return null;
    }

    public List<String> uploadScreenshot() {
        File file = HelperBase.takeScreenshotFile();
        return qaseApi.attachments()
                .add(projectCode, file)
                .stream()
                .map(Attachment::getHash)
                .collect(Collectors.toList());
    }
}
