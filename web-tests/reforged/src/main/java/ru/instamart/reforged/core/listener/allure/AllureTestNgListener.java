package ru.instamart.reforged.core.listener.allure;

import io.qameta.allure.*;
import io.qameta.allure.model.Link;
import io.qameta.allure.model.*;
import io.qameta.allure.util.AnnotationUtils;
import io.qameta.allure.util.ObjectUtils;
import io.qameta.allure.util.ResultsUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.testng.*;
import org.testng.annotations.Parameters;
import org.testng.internal.ConstructorOrMethod;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import ru.instamart.reforged.core.DoNotOpenBrowser;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.KrakenParams;
import ru.instamart.reforged.core.report.CustomReport;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.qameta.allure.util.ResultsUtils.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Comparator.comparing;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@RequiredArgsConstructor
public class AllureTestNgListener implements ISuiteListener, ITestListener, IInvokedMethodListener, IConfigurationListener {

    private static final String ALLURE_UUID = "ALLURE_UUID";

    private final ThreadLocal<Current> currentTestResult = ThreadLocal.withInitial(Current::new);
    private final ThreadLocal<String> currentTestContainer = ThreadLocal.withInitial(() -> UUID.randomUUID().toString());
    private final ThreadLocal<String> currentFakeTestContainer = ThreadLocal.withInitial(() -> UUID.randomUUID().toString());
    private final ThreadLocal<String> currentExecutable = ThreadLocal.withInitial(() -> UUID.randomUUID().toString());
    private final ThreadLocal<String> currentExecutableTearDown = ThreadLocal.withInitial(() -> UUID.randomUUID().toString());

    /**
     * Store uuid for class test containers.
     */
    private final Map<ITestClass, String> classContainerUuidStorage = new ConcurrentHashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private static final List<Class<?>> INJECTED_TYPES = Arrays.asList(
            ITestContext.class, ITestResult.class, XmlTest.class, Method.class, Object[].class
    );

    @Getter
    private final AllureLifecycle lifecycle;

    public AllureTestNgListener() {
        this(Allure.getLifecycle());
    }

    @Override
    public void onStart(final ISuite suite) {
        final var result = new TestResultContainer()
                .setUuid(getUniqueUuid(suite))
                .setName(suite.getName())
                .setStart(System.currentTimeMillis());
        getLifecycle().startTestContainer(result);
    }

    @Override
    public void onStart(final ITestContext context) {
        final var parentUuid = getUniqueUuid(context.getSuite());
        final var uuid = getUniqueUuid(context);

        final var container = new TestResultContainer()
                .setUuid(uuid)
                .setName(context.getName())
                .setStart(System.currentTimeMillis());
        getLifecycle().startTestContainer(parentUuid, container);

        Stream.of(context.getAllTestMethods())
                .map(ITestNGMethod::getTestClass)
                .distinct()
                .forEach(this::onBeforeClass);

        context.getExcludedMethods().stream()
                .filter(ITestNGMethod::isTest)
                .filter(method -> !method.getEnabled())
                .forEach(method -> createFakeResult(context, method));
    }

    @Override
    public void beforeInvocation(final IInvokedMethod method, final ITestResult testResult) {
        final var testMethod = method.getTestMethod();
        final var context = testResult.getTestContext();
        if (isSupportedConfigurationFixture(testMethod)) {
            ifSuiteFixtureStarted(context.getSuite(), testMethod);
            ifTestFixtureStarted(context, testMethod);
            ifClassFixtureStarted(testMethod);
            ifMethodFixtureStarted(currentTestContainer, testMethod);
        }
    }

    @Override
    public void onConfigurationFailure(final ITestResult itr) {
        final var uuid = UUID.randomUUID().toString();
        final var parentUuid = UUID.randomUUID().toString();

        startTestCase(itr, parentUuid, uuid);
        getLifecycle().updateTestCase(
                uuid,
                tr -> tr.getLabels().add(
                        new Label().setName(ALLURE_ID_LABEL_NAME).setValue("-1")
                )
        );
        stopTestCase(uuid, itr.getThrowable(), getStatus(itr.getThrowable()));
    }

    @Override
    public void afterInvocation(final IInvokedMethod method, final ITestResult testResult) {
        final var testMethod = method.getTestMethod();
        if (isSupportedConfigurationFixture(testMethod)) {
            final var executableUuid = currentExecutable.get();
            currentExecutable.remove();
            if (testResult.isSuccess()) {
                getLifecycle().updateFixture(executableUuid, result -> result.setStatus(Status.PASSED));
            } else {
                getLifecycle().updateFixture(executableUuid, result -> result
                        .setStatus(getStatus(testResult.getThrowable()))
                        .setStatusDetails(getStatusDetails(testResult.getThrowable()).orElse(null)));
            }
            getLifecycle().stopFixture(executableUuid);

            if (testMethod.isBeforeMethodConfiguration() || testMethod.isAfterMethodConfiguration()) {
                stopContainer(currentTestContainer, testMethod);
            }
        }
    }

    @Override
    public void onTestStart(final ITestResult testResult) {
        Current current = currentTestResult.get();
        if (current.isStarted()) {
            current = refreshContext();
        }
        current.test();

        final var uuid = current.getUuid();
        final var parentUuid = getUniqueUuid(testResult.getTestContext());

        startTestCase(testResult, parentUuid, uuid);

        Optional.of(testResult)
                .map(ITestResult::getMethod)
                .map(ITestNGMethod::getTestClass)
                .ifPresent(clazz -> addClassContainerChild(clazz, uuid));
    }

    @Override
    public void onTestSuccess(final ITestResult testResult) {
        final var current = currentTestResult.get();
        current.after();
        stopTestCase(current.getUuid(), null, Status.PASSED);
    }

    @Override
    public void onTestFailure(final ITestResult testResult) {
        Current current = currentTestResult.get();

        if (current.isAfter()) {
            current = refreshContext();
        }

        if (!current.isStarted()) {
            createTestResultForTestWithoutSetup(testResult);
        }

        current.after();

        final var uuid = current.getUuid();
        final var throwable = testResult.getThrowable();
        final var status = getStatus(throwable);
        stopTestCase(uuid, throwable, status);
    }

    @Override
    public void onTestSkipped(final ITestResult testResult) {
        Current current = currentTestResult.get();

        if (current.isAfter()) {
            current = refreshContext();
        }

        if (!current.isStarted()) {
            createTestResultForTestWithoutSetup(testResult);
        }
        current.after();
        stopTestCase(current.getUuid(), testResult.getThrowable(), Status.SKIPPED);
    }

    @Override
    public void onFinish(final ITestContext context) {
        final var uuid = getUniqueUuid(context);
        getLifecycle().stopTestContainer(uuid);
        getLifecycle().writeTestContainer(uuid);

        Stream.of(context.getAllTestMethods())
                .map(ITestNGMethod::getTestClass)
                .distinct()
                .forEach(this::onAfterClass);
    }

    @Override
    public void onFinish(final ISuite suite) {
        final var uuid = getUniqueUuid(suite);
        getLifecycle().stopTestContainer(uuid);
        getLifecycle().writeTestContainer(uuid);
    }

    public void tearDownFixtureStarted(final ITestNGMethod testMethod) {
        currentFakeTestContainer.remove();
        final var current = currentTestResult.get();
        final var fixture = getFixtureResult(testMethod);
        final var uuid = currentExecutableTearDown.get();
        getLifecycle().startTearDownFixture(createFakeContainer(currentFakeTestContainer, testMethod, current), uuid, fixture);
    }

    public void tearDownFixtureStop() {
        final var executableUuid = currentExecutableTearDown.get();
        currentExecutableTearDown.remove();
        getLifecycle().stopFixture(executableUuid);
    }

    public void stopContainer(final ITestNGMethod testNGMethod) {
        stopContainer(currentFakeTestContainer, testNGMethod);
    }

    private void onBeforeClass(final ITestClass testClass) {
        final var uuid = UUID.randomUUID().toString();
        final var container = new TestResultContainer()
                .setUuid(uuid)
                .setName(testClass.getName());
        getLifecycle().startTestContainer(container);
        setClassContainer(testClass, uuid);
    }

    private void startTestCase(final ITestResult testResult, final String parentUuid, final String uuid) {
        startTestCase(
                testResult.getTestContext(),
                testResult.getMethod(),
                testResult.getTestClass(),
                testResult.getParameters(),
                parentUuid,
                uuid
        );
    }

    @SuppressWarnings({"Indentation", "PMD.ExcessiveMethodLength", "deprecation"})
    private void startTestCase(final ITestContext context,
                               final ITestNGMethod method,
                               final IClass iClass,
                               final Object[] params,
                               final String parentUuid,
                               final String uuid) {
        final ITestClass testClass = method.getTestClass();
        final List<Label> labels = new ArrayList<>();
        labels.addAll(getProvidedLabels());
        labels.addAll(Arrays.asList(
                //Packages grouping
                createPackageLabel(testClass.getName()),
                createTestClassLabel(testClass.getName()),
                createTestMethodLabel(method.getMethodName()),

                //xUnit grouping
                createParentSuiteLabel(safeExtractSuiteName(testClass)),
                createSuiteLabel(safeExtractTestTag(testClass)),
                createSubSuiteLabel(safeExtractTestClassName(testClass)),

                //Timeline grouping
                createHostLabel(),
                createThreadLabel(),

                createFrameworkLabel("testng"),
                createLanguageLabel("java")
        ));
        labels.addAll(getLabels(method, iClass));
        final List<Parameter> parameters = getParameters(context, method, params);
        final var result = new TestResult()
                .setUuid(uuid)
                .setHistoryId(getHistoryId(method, parameters))
                .setName(getMethodName(method))
                .setFullName(getQualifiedName(method))
                .setStatusDetails(new StatusDetails()
                        .setFlaky(isFlaky(method, iClass))
                        .setMuted(isMuted(method, iClass)))
                .setParameters(parameters)
                .setLinks(getLinks(method, iClass))
                .setLabels(labels);
        processDescription(getClass().getClassLoader(), method.getConstructorOrMethod().getMethod(), result);
        getLifecycle().scheduleTestCase(parentUuid, result);
        getLifecycle().startTestCase(uuid);
    }

    private void stopTestCase(final String uuid, final Throwable throwable, final Status status) {
        final StatusDetails details = getStatusDetails(throwable).orElse(null);
        getLifecycle().updateTestCase(uuid, setStatus(status, details));
        getLifecycle().stopTestCase(uuid);
        getLifecycle().writeTestCase(uuid);
    }

    public void onAfterClass(final ITestClass testClass) {
        getClassContainer(testClass).ifPresent(uuid -> {
            getLifecycle().stopTestContainer(uuid);
            getLifecycle().writeTestContainer(uuid);
        });
    }

    private void createTestResultForTestWithoutSetup(final ITestResult result) {
        onTestStart(result);
        currentTestResult.remove();
    }

    private void ifSuiteFixtureStarted(final ISuite suite, final ITestNGMethod testMethod) {
        if (testMethod.isBeforeSuiteConfiguration()) {
            startBefore(getUniqueUuid(suite), testMethod);
        }
        if (testMethod.isAfterSuiteConfiguration()) {
            startAfter(getUniqueUuid(suite), testMethod);
        }
    }

    private void ifClassFixtureStarted(final ITestNGMethod testMethod) {
        if (testMethod.isBeforeClassConfiguration()) {
            getClassContainer(testMethod.getTestClass())
                    .ifPresent(parentUuid -> startBefore(parentUuid, testMethod));
        }
        if (testMethod.isAfterClassConfiguration()) {
            getClassContainer(testMethod.getTestClass())
                    .ifPresent(parentUuid -> startAfter(parentUuid, testMethod));
        }
    }

    private void ifTestFixtureStarted(final ITestContext context, final ITestNGMethod testMethod) {
        if (testMethod.isBeforeTestConfiguration()) {
            startBefore(getUniqueUuid(context), testMethod);
        }
        if (testMethod.isAfterTestConfiguration()) {
            startAfter(getUniqueUuid(context), testMethod);
        }
    }

    private void startBefore(final String parentUuid, final ITestNGMethod method) {
        final var uuid = currentExecutable.get();
        getLifecycle().startPrepareFixture(parentUuid, uuid, getFixtureResult(method));
    }

    private void startAfter(final String parentUuid, final ITestNGMethod method) {
        final var uuid = currentExecutable.get();
        getLifecycle().startTearDownFixture(parentUuid, uuid, getFixtureResult(method));
    }

    private void ifMethodFixtureStarted(final ThreadLocal<String> threadLocal, final ITestNGMethod testMethod) {
        threadLocal.remove();
        Current current = currentTestResult.get();
        final var fixture = getFixtureResult(testMethod);
        final var uuid = currentExecutable.get();

        if (testMethod.isBeforeMethodConfiguration()) {
            if (current.isStarted()) {
                currentTestResult.remove();
                current = currentTestResult.get();
            }
            getLifecycle().startPrepareFixture(createFakeContainer(threadLocal, testMethod, current), uuid, fixture);
        }

        if (testMethod.isAfterMethodConfiguration()) {
            getLifecycle().startTearDownFixture(createFakeContainer(threadLocal, testMethod, current), uuid, fixture);
        }

        if (testMethod.isTest()) {
            getLifecycle().startTearDownFixture(createFakeContainer(threadLocal, testMethod, current), uuid, fixture);
        }
    }

    private String createFakeContainer(final ThreadLocal<String> threadLocal, final ITestNGMethod method, final Current current) {
        final var parentUuid = threadLocal.get();
        final var container = new TestResultContainer()
                .setUuid(parentUuid)
                .setName(getQualifiedName(method))
                .setStart(System.currentTimeMillis())
                .setDescription(method.getDescription())
                .setChildren(Collections.singletonList(current.getUuid()));
        getLifecycle().startTestContainer(container);
        return parentUuid;
    }

    private String getQualifiedName(final ITestNGMethod method) {
        return method.getRealClass().getName() + "." + method.getMethodName();
    }

    private FixtureResult getFixtureResult(final ITestNGMethod method) {
        final var fixtureResult = new FixtureResult()
                .setName(method.isTest() ? "Additional information" : getMethodName(method))
                .setStart(System.currentTimeMillis())
                .setDescription(method.getDescription())
                .setStage(Stage.RUNNING);
        getJavadocDescription(getClass().getClassLoader(), method.getConstructorOrMethod().getMethod());
        return fixtureResult;
    }

    private void stopContainer(final ThreadLocal<String> threadLocal, final ITestNGMethod testMethod) {
        final var uuid = threadLocal.get();
        validateContainerExists(getQualifiedName(testMethod), uuid);
        threadLocal.remove();
        getLifecycle().stopTestContainer(uuid);
        getLifecycle().writeTestContainer(uuid);
    }

    private void createFakeResult(final ITestContext context, final ITestNGMethod method) {
        final var uuid = UUID.randomUUID().toString();
        final var parentUuid = UUID.randomUUID().toString();
        startTestCase(context, method, method.getTestClass(), new Object[]{}, parentUuid, uuid);
        stopTestCase(uuid, null, null);
    }

    private String getHistoryId(final ITestNGMethod method, final List<Parameter> parameters) {
        final var digest = getMd5Digest();
        final var testClassName = method.getTestClass().getName();
        final var methodName = method.getMethodName();
        digest.update(testClassName.getBytes(UTF_8));
        digest.update(methodName.getBytes(UTF_8));
        parameters.stream()
                .sorted(comparing(Parameter::getName).thenComparing(Parameter::getValue))
                .forEachOrdered(parameter -> {
                    digest.update(parameter.getName().getBytes(UTF_8));
                    digest.update(parameter.getValue().getBytes(UTF_8));
                });
        final byte[] bytes = digest.digest();
        return bytesToHex(bytes);
    }

    private Status getStatus(final Throwable throwable) {
        return ResultsUtils.getStatus(throwable).orElse(Status.BROKEN);
    }

    @SuppressWarnings("BooleanExpressionComplexity")
    private boolean isSupportedConfigurationFixture(final ITestNGMethod testMethod) {
        return testMethod.isBeforeMethodConfiguration() || testMethod.isAfterMethodConfiguration()
                || testMethod.isBeforeTestConfiguration() || testMethod.isAfterTestConfiguration()
                || testMethod.isBeforeClassConfiguration() || testMethod.isAfterClassConfiguration()
                || testMethod.isBeforeSuiteConfiguration() || testMethod.isAfterSuiteConfiguration();
    }

    private void validateContainerExists(final String fixtureName, final String containerUuid) {
        if (isNull(containerUuid)) {
            throw new IllegalStateException(
                    "Could not find container for after method fixture " + fixtureName
            );
        }
    }

    private List<Label> getLabels(final ITestNGMethod method, final IClass iClass) {
        final List<Label> labels = new ArrayList<>();
        getMethod(method)
                .map(AnnotationUtils::getLabels)
                .ifPresent(labels::addAll);
        getClass(iClass)
                .map(AnnotationUtils::getLabels)
                .ifPresent(labels::addAll);

        getMethod(method)
                .map(this::getSeverity)
                .filter(Optional::isPresent)
                .orElse(getClass(iClass).flatMap(this::getSeverity))
                .map(ResultsUtils::createSeverityLabel)
                .ifPresent(labels::add);
        return labels;
    }

    private Optional<SeverityLevel> getSeverity(final AnnotatedElement annotatedElement) {
        return Stream.of(annotatedElement.getAnnotationsByType(Severity.class))
                .map(Severity::value)
                .findAny();
    }

    private List<Link> getLinks(final ITestNGMethod method, final IClass iClass) {
        final List<Link> links = new ArrayList<>();
        getMethod(method)
                .map(AnnotationUtils::getLinks)
                .ifPresent(links::addAll);
        getClass(iClass)
                .map(AnnotationUtils::getLinks)
                .ifPresent(links::addAll);
        return links;
    }

    private boolean isFlaky(final ITestNGMethod method, final IClass iClass) {
        final boolean flakyMethod = getMethod(method)
                .map(m -> m.isAnnotationPresent(Flaky.class))
                .orElse(false);
        final boolean flakyClass = getClass(iClass)
                .map(clazz -> clazz.isAnnotationPresent(Flaky.class))
                .orElse(false);
        return flakyMethod || flakyClass;
    }

    private boolean isMuted(final ITestNGMethod method, final IClass iClass) {
        final boolean mutedMethod = getMethod(method)
                .map(m -> m.isAnnotationPresent(Muted.class))
                .orElse(false);
        final boolean mutedClass = getClass(iClass)
                .map(clazz -> clazz.isAnnotationPresent(Muted.class))
                .orElse(false);
        return mutedMethod || mutedClass;
    }

    private Optional<Method> getMethod(final ITestNGMethod method) {
        return Optional.ofNullable(method)
                .map(ITestNGMethod::getConstructorOrMethod)
                .map(ConstructorOrMethod::getMethod);
    }

    private Optional<Class<?>> getClass(final IClass iClass) {
        return Optional.ofNullable(iClass).map(IClass::getRealClass);
    }

    /**
     * Returns the unique id for given results item.
     */
    private String getUniqueUuid(final IAttributes suite) {
        if (isNull(suite.getAttribute(ALLURE_UUID))) {
            suite.setAttribute(ALLURE_UUID, UUID.randomUUID().toString());
        }
        return Objects.toString(suite.getAttribute(ALLURE_UUID));
    }

    private static String safeExtractSuiteName(final ITestClass testClass) {
        final Optional<XmlTest> xmlTest = Optional.ofNullable(testClass.getXmlTest());
        return xmlTest.map(XmlTest::getSuite).map(XmlSuite::getName).orElse("Undefined suite");
    }

    private static String safeExtractTestTag(final ITestClass testClass) {
        final Optional<XmlTest> xmlTest = Optional.ofNullable(testClass.getXmlTest());
        return xmlTest.map(XmlTest::getName).orElse("Undefined testng tag");
    }

    private static String safeExtractTestClassName(final ITestClass testClass) {
        return firstNonEmpty(testClass.getTestName(), testClass.getName()).orElse("Undefined class name");
    }

    private List<Parameter> getParameters(final ITestContext context, final ITestNGMethod method, final Object... parameters) {
        final Map<String, String> result = new HashMap<>(
                context.getCurrentXmlTest().getAllParameters()
        );
        final Object instance = method.getInstance();
        if (nonNull(instance)) {
            Stream.of(instance.getClass().getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(TestInstanceParameter.class))
                    .forEach(field -> {
                        final var name = Optional.ofNullable(field.getAnnotation(TestInstanceParameter.class))
                                .map(TestInstanceParameter::value)
                                .filter(s -> !s.isEmpty())
                                .orElseGet(field::getName);
                        try {
                            field.setAccessible(true);
                            final var value = ObjectUtils.toString(field.get(instance));
                            result.put(name, value);
                        } catch (IllegalAccessException e) {
                            log.debug("Could not access field value");
                        }
                    });
        }

        getMethod(method).ifPresent(m -> {
            final Class<?>[] parameterTypes = m.getParameterTypes();

            if (parameterTypes.length != parameters.length) {
                return;
            }

            final String[] providedNames = Optional.ofNullable(m.getAnnotation(Parameters.class))
                    .map(Parameters::value)
                    .orElse(new String[]{});
            final String[] reflectionNames = Stream.of(m.getParameters())
                    .map(java.lang.reflect.Parameter::getName)
                    .toArray(String[]::new);

            int skippedCount = 0;
            for (int i = 0; i < parameterTypes.length; i++) {
                final Class<?> parameterType = parameterTypes[i];
                if (INJECTED_TYPES.contains(parameterType)) {
                    skippedCount++;
                    continue;
                }

                final int indexFromAnnotation = i - skippedCount;
                if (indexFromAnnotation < providedNames.length) {
                    result.put(providedNames[indexFromAnnotation], ObjectUtils.toString(parameters[i]));
                    continue;
                }

                if (i < reflectionNames.length) {
                    result.put(reflectionNames[i], ObjectUtils.toString(parameters[i]));
                }
            }
        });

        return result.entrySet().stream()
                .map(entry -> createParameter(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private String getMethodName(final ITestNGMethod method) {
        return firstNonEmpty(
                method.getDescription(),
                method.getMethodName(),
                getQualifiedName(method)).orElse("Unknown");
    }

    @SuppressWarnings("SameParameterValue")
    private Consumer<TestResult> setStatus(final Status status) {
        return result -> result.setStatus(status);
    }

    private Consumer<TestResult> setStatus(final Status status, final StatusDetails details) {
        return result -> {
            result.setStatus(status);
            if (nonNull(details)) {
                result.getStatusDetails().setTrace(details.getTrace());
                result.getStatusDetails().setMessage(details.getMessage());
            }
        };
    }

    private void addClassContainerChild(final ITestClass clazz, final String childUuid) {
        lock.writeLock().lock();
        try {
            final String parentUuid = classContainerUuidStorage.get(clazz);
            if (nonNull(parentUuid)) {
                getLifecycle().updateTestContainer(
                        parentUuid,
                        container -> container.getChildren().add(childUuid)
                );
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    private Optional<String> getClassContainer(final ITestClass clazz) {
        lock.readLock().lock();
        try {
            return Optional.ofNullable(classContainerUuidStorage.get(clazz));
        } finally {
            lock.readLock().unlock();
        }
    }

    private void setClassContainer(final ITestClass clazz, final String uuid) {
        lock.writeLock().lock();
        try {
            classContainerUuidStorage.put(clazz, uuid);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private Current refreshContext() {
        currentTestResult.remove();
        return currentTestResult.get();
    }
}
