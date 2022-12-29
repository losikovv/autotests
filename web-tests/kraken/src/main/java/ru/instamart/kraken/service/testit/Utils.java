package ru.instamart.kraken.service.testit;

import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import ru.instamart.kraken.service.testit.annotation.CaseId;
import ru.testit.client.model.LabelPostModel;
import ru.testit.client.model.LabelShortModel;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Slf4j
public final class Utils {

    public static String urlTrim(final String url) {
        if (url.endsWith("/")) {
            return removeTrailing(url);
        }

        return url;
    }

    private static String removeTrailing(final String s) {
        final var sb = new StringBuilder(s);
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    public static String extractCaseID(final Method atomicTest) {
        final TmsLink annotation = atomicTest.getAnnotation(TmsLink.class);
        return (nonNull(annotation)) ? annotation.value() : null;
    }

    public static String getCaseId(final ITestResult result) {
        final Method method = result
                .getMethod()
                .getConstructorOrMethod()
                .getMethod();
        if (method.isAnnotationPresent(CaseId.class)) {
            return method.getDeclaredAnnotation(CaseId.class).value();
        } else if (method.isAnnotationPresent(TmsLink.class)) {
            return method.getDeclaredAnnotation(TmsLink.class).value();
        } else if (method.isAnnotationPresent(TmsLinks.class)) {
            final var links = method.getDeclaredAnnotation(TmsLinks.class).value();
            return Arrays.stream(links).map(TmsLink::value)
                    .collect(Collectors.toSet())
                    .stream()
                    .findFirst()
                    .orElseThrow();
        }
        return null;
    }

    public static Set<String> getCasesIds(final ITestResult result) {
        final Method method = result
                .getMethod()
                .getConstructorOrMethod()
                .getMethod();
        if (method.isAnnotationPresent(CaseId.class)) {
            return Set.of(method.getDeclaredAnnotation(CaseId.class).value());
        } else if (method.isAnnotationPresent(TmsLink.class)) {
            return Set.of(method.getDeclaredAnnotation(TmsLink.class).value());
        } else if (method.isAnnotationPresent(TmsLinks.class)) {
            final var links = method.getDeclaredAnnotation(TmsLinks.class).value();
            return Arrays.stream(links).map(TmsLink::value).collect(Collectors.toSet());
        }

        return Collections.emptySet();
    }

    public static String getDescription(final ITestResult testResult) {
        final var method = testResult.getMethod().getConstructorOrMethod().getMethod();
        if (method.isAnnotationPresent(Test.class)) {
            return method.getDeclaredAnnotation(Test.class).description();
        }
        return "empty";
    }

    public static String statusConverter(final int value) {
        /*int CREATED = -1;
          int SUCCESS = 1;
          int FAILURE = 2;
          int SKIP = 3;
          int SUCCESS_PERCENTAGE_FAILURE = 4;
          int STARTED = 16;
        */
        switch (value) {
            case 1 : return "Passed";
            case 2 : return "Failed";
            case 3 : return "Skipped";
            case -1 :
            case 4 :
            case 16 :
            default: return "InProgress";
        }
    }

    public static List<LabelPostModel> labelsConvert(List<LabelShortModel> labels) {
        return labels.stream().map(label -> {
            final var model = new LabelPostModel();
            model.setName(label.getName());

            return model;
        }).collect(Collectors.toList());
    }

    private Utils() {}
}
