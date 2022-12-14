package ru.instamart.kraken.service.testit;

import ru.instamart.kraken.service.testit.annotation.CaseId;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public final class Utils {

    private static final Pattern paramsPattern = Pattern.compile("\\{\\s*(\\w+)}");

    public static boolean filterTestWithParameters(final List<String> testsForRun, final String externalId) {
        final var pattern = Pattern.compile(externalId.replaceAll("\\{.*}", ".*"));

        for (final var test : testsForRun) {
            final var matcher = pattern.matcher(test);
            if (matcher.find()) {
                return true;
            }
        }

        return false;
    }

    public static String extractCaseID(final Method atomicTest, final Map<String, String> parameters) {
        final CaseId annotation = atomicTest.getAnnotation(CaseId.class);
        return (annotation != null) ? setParameters(annotation.value(), parameters) : getHash(atomicTest.getDeclaringClass().getName() + atomicTest.getName());
    }

    private static String setParameters(String value, final Map<String, String> parameters) {
        if (!isNull(parameters) && !isNull(value)) {
            final var matcher = paramsPattern.matcher(value);

            while (matcher.find()) {
                final var parameterName = matcher.group(1);
                final var parameterValue = parameters.get(parameterName);

                if (!isNull(parameterValue)) {
                    value = value.replace(String.format("{%s}", parameterName), parameters.get(parameterName));
                }
            }
        }

        return value;
    }

    public static String getHash(final String value) {
        try {
            final var md = MessageDigest.getInstance("SHA-256");
            md.update(value.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            return convertToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            return value;
        }
    }

    private static String convertToHex(final byte[] messageDigest) {
        final var bigint = new BigInteger(1, messageDigest);
        var hexText = bigint.toString(16);
        while (hexText.length() < 32) {
            hexText = "0".concat(hexText);
        }
        return hexText.toUpperCase();
    }

    private Utils() {}
}
