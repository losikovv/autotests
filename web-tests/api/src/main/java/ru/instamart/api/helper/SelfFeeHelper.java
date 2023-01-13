package ru.instamart.api.helper;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import ru.instamart.api.request.self_fee.SelfFeeV1Request;
import ru.instamart.api.request.self_fee.SelfFeeV3Request;
import ru.instamart.api.response.self_fee.RegistryV1Response;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.SECONDS;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Slf4j
public class SelfFeeHelper {


    @Step("Ожидание обработки файла id: {id}. Максимальное время ожидания: {seconds}")
    public static void awaitFile(final Integer id, final long seconds, final Integer awaitStatusCode) {
        Awaitility.with()
                .pollInSameThread()
                .pollInterval(Duration.of(10, SECONDS))
                .await()
                .atMost(seconds, TimeUnit.SECONDS)
                .until(() -> checkStatusFile(id, awaitStatusCode));
    }

    @Step("Проверка статуса кода {awaitStatusCode} файла с id: {id}")
    private static boolean checkStatusFile(final Integer id, final Integer awaitStatusCode) {
        final var response = SelfFeeV3Request.Upload.GET(id);
        final var statusCode = response.getStatusCode();
        log.debug("Status code: {}", statusCode);
        return awaitStatusCode.equals(statusCode);
    }


    @Step("Ожидание обработки файла id: {id}. Максимальное время ожидания: {seconds}")
    public static void awaitFiscal(final String fileName, final long seconds) {
        Awaitility.with()
                .pollInSameThread()
                .pollInterval(Duration.of(10, SECONDS))
                .await()
                .atMost(seconds, TimeUnit.SECONDS)
                .until(() -> checkFiscal(fileName));
    }

    private static boolean checkFiscal(final String fileName) {
        final var respRegistry = SelfFeeV1Request.Registry.GET(0, false);
        checkStatusCode200(respRegistry);
        final var registryResponse = respRegistry.as(RegistryV1Response.class);
        final var filters = registryResponse.getResult().stream()
                .filter(item -> item.getFile().getFilename().equals(fileName))
                .collect(Collectors.toList());
        if (filters.size() != 0) {
            return filters.get(0).getStatus().equals("fiscalized");
        }
        return false;
    }
}
