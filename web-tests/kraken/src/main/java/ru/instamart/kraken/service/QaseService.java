package ru.instamart.kraken.service;

import io.qameta.allure.TmsLink;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestResult;
import org.testng.internal.TestResult;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.sbermarket.qase.QaseApi;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;
import ru.sbermarket.qase.enums.Automation;
import ru.sbermarket.qase.enums.RunResultStatus;
import ru.sbermarket.qase.exceptions.QaseException;
import ru.sbermarket.qase.v1.attachments.Attachment;
import ru.sbermarket.qase.v1.defects.Defect;
import ru.sbermarket.qase.v1.testcases.TestCase;
import ru.sbermarket.qase.v1.testrunresults.TestRunResult;
import ru.sbermarket.qase.v1.testruns.TestRun;

import java.io.File;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static ru.instamart.kraken.helper.DateTimeHelper.getDateFromMSK;
import static ru.sbermarket.qase.utils.IntegrationUtils.getStacktrace;

@Slf4j
@Accessors(chain = true)
public final class QaseService {

    private static final String DESCRIPTION_PREFIX = "[Automation] ";
    private static final Pattern ATTACHMENT_PATTERN_HASH = Pattern.compile(".+/(.+)/.+$");
    private static final LocalDateTime DAYS_TO_DIE = LocalDateTime.now().minusWeeks(3);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String PIPELINE_URL = System.getenv("CI_PIPELINE_URL");
    @Setter
    private String projectCode;
    @Getter
    private final QaseApi qaseApi;
    private final String testRunName;
    private final UserInfoService userInfoService;
    private boolean started = false;
    private Long runId;

    public QaseService() {
        this("", "test");
    }

    public QaseService(final String projectCode, final String testRunName) {
        this.projectCode = projectCode;
        this.testRunName = testRunName;
        this.qaseApi = new QaseApi(CoreProperties.QASE_API_TOKEN);
        this.userInfoService = new UserInfoService();
    }

    /**
     * Создаём тест-ран с полученными кейсами
     */
    public void createTestRun() {
        if (projectCode == null || started) return;
        started = true;

        try {
            runId = qaseApi.testRuns().create(
                    projectCode,
                    testRunName + " [" + EnvironmentProperties.Env.ENV_NAME + "] " + getDateFromMSK(),
                    null,
                    DESCRIPTION_PREFIX + PIPELINE_URL + " UserInfo: " + userInfoService.createUserInfo());
            log.debug("Create Test run={} for project={}", runId, projectCode);
        } catch (Exception e) {
            log.error("FATAL: Create Test run failed with error ", e);
        }
    }

    /**
     * Отправляем статус прохождения теста
     */
    public void sendResult(final ITestResult result, final RunResultStatus status) {
        if (!started) return;
        this.sendResult(result, status, null);
    }

    synchronized public void sendResult(final ITestResult result,
                                        final RunResultStatus status,
                                        final List<String> attachmentHash) {
        if (!started) return;

        final Duration timeSpent = Duration
                .ofMillis(result.getEndMillis() - result.getStartMillis());
        final Optional<Throwable> resultThrowable = Optional
                .ofNullable(result.getThrowable());
        final String comment = resultThrowable
                .flatMap(throwable -> Optional.of(throwable.toString()))
                .orElse("");
        final Boolean isDefect = false; // создание дефектов пока отключено
//                resultThrowable
//                .flatMap(throwable -> Optional.of(throwable instanceof AssertionError))
//                .orElse(false);
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
            Long caseIdItem = null;
            if (index < caseIDs.length) {
                caseIdItem = caseIDs[index].value();
            }
            if (Objects.nonNull(caseIdItem)) {
                createTestResult(caseIdItem, status, timeSpent, comment, stacktrace, isDefect, attachmentHash);
            }
            if (Objects.equals(result.getParameters().length, 0)) {
                Arrays.stream(caseIDs)
                        .filter(Objects::nonNull)
                        .forEach(item -> createTestResult(item.value(), status, timeSpent, comment, stacktrace, isDefect, attachmentHash));
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
        try {
            return qaseApi.attachments()
                    .add(projectCode, file)
                    .stream()
                    .map(Attachment::getHash)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("FAILED: Upload screenshot", e);
            return Collections.emptyList();
        }
    }

    public void deleteOldTestRuns() {
        //Создаем фильтр с выборкой старше трех недель от текущего времени
        final var filter = qaseApi
                .testRuns()
                .filter()
                .toStartTime(DAYS_TO_DIE);

        //Получаем все прогоны удовлетворяющие фильтру
        final List<TestRun> allTestRuns = new ArrayList<>();
        int testRunSize;
        int offset = 0;
        do {
            try {
                //Тут специально по 50 иначе кейс начинает 500
                log.debug("Получаем {} страницу тестовых прогонов", offset / 50 + 1);
                final List<TestRun> testRunList = qaseApi.testRuns()
                        .getAll(projectCode, 50, offset, filter, false)
                        .getTestRunList();
                allTestRuns.addAll(testRunList);
                testRunSize = testRunList.size();
                log.debug("Получили {} тестовых прогонов", testRunSize);
            } catch (QaseException qaseException) {
                log.error("Something went wrong: " + qaseException);
                testRunSize = 0;
            }
            offset += 50;
        } while (testRunSize > 0);

        final var testRunIds = allTestRuns
                .stream()
                //Фильтруем полученный список прогонов, по тем что были созданы автотестами
                .filter(testRun -> nonNull(testRun.getDescription()) && testRun.getDescription().contains(DESCRIPTION_PREFIX))
                //Из полученного списка извлекаем все id, так как остальная инфа нам не нужна
                .map(TestRun::getId)
                .collect(Collectors.toList());

        testRunIds.forEach(id -> {
            try {
                //Создаем фильтр по прогону
                //TODO: хоть в апи и написано, что поддерживается указание, через запятую, но по факту это не работает
                final var filterRunId = qaseApi.testRunResults().filter().run(Math.toIntExact(id));
                final List<TestRunResult> testRunResults = qaseApi
                        .testRunResults()
                        .getAll(projectCode, 100, 0, filterRunId)
                        .getTestRunResultList();

                testRunResults.forEach(testRunResult ->
                        //Выгребаем все скрины созданные для прогона и удалем их
                        testRunResult.getAttachments()
                                .forEach(attachment -> {
                                    final Matcher matcher = ATTACHMENT_PATTERN_HASH.matcher(attachment.getUrl());
                                    if (matcher.find()) {
                                        final var hash = matcher.group(1);
                                        log.info("CLEANUP: Delete old attachment={} in project={}", hash, projectCode);
                                        qaseApi.attachments().delete(hash);
                                    }
                                }));
                //Удаляем прогон
                qaseApi.testRuns().delete(projectCode, id);
                log.info("CLEANUP: Delete old test run={} for project={}", id, projectCode);
            } catch (Exception e) {
                log.warn("CLEANUP: Delete old test failed run={} for project={}", id, projectCode);
                log.error("CLEANUP: Failure with error ", e);
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
                log.info("CLEANUP: Delete old defect={} for project={}", defect.getId(), projectCode);
            }
        });
    }

    public void completeTestRun() {
        if (!started) return;
        log.debug("Complete test run={} for project={}", runId, projectCode);
        qaseApi.testRuns().completeTestRun(projectCode, runId);
    }

    public void actualizeAutomatedTestCases() {
        log.debug("Актуализация автоматизированных тест кейсов:");
        try {
            List<TestCase> allTestCases = new ArrayList<>();
            int testCasesSize;
            int offset = 0;
            do {
                try {
                    log.debug("Получаем {} страницу тест-кейсов", offset / 100 + 1);
                    List<TestCase> testCases = qaseApi
                            .testCases()
                            .getAll(projectCode, 100, offset)
                            .getTestCaseList();
                    allTestCases.addAll(testCases);
                    testCasesSize = testCases.size();
                    log.debug("Получили {} тест-кейсов", testCasesSize);
                } catch (QaseException qaseException) {
                    log.error("Something went wrong: " + qaseException);
                    testCasesSize = 0;
                }
                offset += 100;
            } while (testCasesSize > 0);

            int automatedNumber = 0;
            int addToAuto = 0;
            int removeFromAuto = 0;

            //Создаем фильтр от 00:00 текущего дня до текущий день минус два дня
            var filter = qaseApi
                    .testRuns()
                    .filter()
                    .fromStartTime(LocalDateTime.now().minusDays(2))
                    .toStartTime(LocalDateTime.now().with(LocalTime.MIN));
            //Пытаемся получить последние 40 прогонов для проекта учитывая фильтрацию и включающие список кейсов(их id)
            var testRun = qaseApi.testRuns()
                    //стоит учитывать, что если сделать выборку боль 40 или если значительно увеличится список кейсов в прогоне, то qase
                    //будет отдавать 502 и тогда нужно будет сделать через offset
                    .getAll(projectCode, 40, 0, filter, true)
                    .getTestRunList()
                    .stream()
                    .filter(t -> {
                        //Фильтруем полученный список по наличию [Automation] в дескрипшене
                        //К сожалению несмотря на то, что появился флаг isAutomated при создании прогона, фильтровать по нему все еще нельзя
                        //из-за чего этот костыль имеет место быть. Так же это приводит к тому, что в результате получается меньше чем 40 последних прогонов
                        var desc = t.getDescription();
                        return nonNull(desc) && desc.contains(DESCRIPTION_PREFIX);
                    }).collect(Collectors.toList());

            //Собираем все id кейсов в HashSet, именно HashSet тут не случайно,
            //а из-за того что дальше идет contains по Set. HashSet гарантирует константный O(1) contains
            final var cases = testRun.stream().flatMap(t -> t.getCases().stream()).collect(Collectors.toCollection(HashSet::new));

            for (final var testCase : allTestCases) {
                try {
                    final int caseId = (int) testCase.getId();
                    final var inList = cases.contains(caseId);
                    //Если текущий кейс не автоматизирован и он был списке авто прогонов
                    if (testCase.getAutomation() != 2 && inList) {
                        log.debug("Указываем, что тест={} автоматизирован", testCase.getId());
                        qaseApi.testCases().update(projectCode, caseId, Automation.automated);
                        automatedNumber++;
                        addToAuto++;
                        //Если текущий кейс был автоматизирован, но его теперь не в списке авто прогонов
                    } else if (testCase.getAutomation() == 2 && !inList) {
                        log.debug("Указываем, что тест={} не автоматизирован", testCase.getId());
                        qaseApi.testCases().update(projectCode, caseId, Automation.is_not_automated);
                        automatedNumber--;
                        removeFromAuto++;
                        //Хреновина чисто для того, что бы более точно посчитать количество автоматизированных кейсов
                    } else if (testCase.getAutomation() == 2) {
                        automatedNumber++;
                    }
                } catch (QaseException qaseException) {
                    log.error("Something went wrong: " + qaseException);
                }
            }
            log.info("Актуализация кейсов для проекта: {}", projectCode);
            log.info("Всего тест кейсов: {}", allTestCases.size());
            log.info("Добавлено в список автоматизированных: {}", addToAuto);
            log.info("Удалено из списка автоматизированных: {}", removeFromAuto);
            log.info("Всего автоматизировано: {}", automatedNumber);
        } catch (Exception e) {
            log.error("FATAL: something went wrong when try to actualize test cases", e);
        }
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

