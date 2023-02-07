package ru.instamart.api.helper;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.testng.Assert;
import ru.instamart.api.request.self_fee.SelfFeeV1Request;
import ru.instamart.api.request.self_fee.SelfFeeV3Request;
import ru.instamart.api.response.self_fee.RegistryV1Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Slf4j
public class SelfFeeHelper {

    @Step("Ожидание обработки загруженного файла id: {id}. Максимальное время ожидания: {seconds}")
    public static Response awaitFile(final Integer id, final int seconds) {
        AtomicReference<Response> responseUpload = new AtomicReference();
        WaitHelper.withRetriesAsserted(() -> {
                    final var statusCode = responseUpload.get().getStatusCode();
                    assertEquals(200, statusCode, "");
                },
                () -> {
                    responseUpload.set(SelfFeeV3Request.Upload.GET(id));
                    return responseUpload.get().statusCode() == 422;
                },
                "Файл содержит ошибки и сервис ответил с ошибкой 422",
                seconds);
        return responseUpload.get();
    }

    @Step("Ожидание обработки реестра id: {id}. Максимальное время ожидания: {seconds}")
    public static Response awaitFiscalize(final Integer id, final boolean withErrors, final int seconds) {
        AtomicReference<Response> responseUpload = new AtomicReference();
        WaitHelper.withRetriesAsserted(() -> {
                    responseUpload.set(SelfFeeV3Request.Registry.GET(id, withErrors));
                    final var statusCode = responseUpload.get().getStatusCode();
                    assertEquals(200, statusCode, "");
                },
                seconds);
        return responseUpload.get();
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

    public static Path renameFile(String file, String sufix) {
        final var path = Paths.get(file);
        final var newFileName = sufix + "_" + path.getFileName();
        try {
            return Files.copy(path, new File(path.getParent().toFile(), newFileName).toPath());
        } catch (IOException e) {
            throw new AssertionError("Ошибка переименования файла. " + e.getStackTrace());
        }
    }
}
