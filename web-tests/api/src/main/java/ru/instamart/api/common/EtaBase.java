package ru.instamart.api.common;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.instamart.grpc.helper.GrpcHelper;
import ru.instamart.jdbc.dao.eta.StoreParametersDao;
import ru.instamart.redis.Redis;
import ru.instamart.redis.RedisManager;
import ru.instamart.redis.RedisService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static ru.instamart.api.helper.EtaHelper.*;
import static ru.instamart.kraken.helper.LogbackLogBuffer.clearLogbackLogBuffer;
import static ru.instamart.kraken.helper.LogbackLogBuffer.getLogbackBufferLog;

public class EtaBase {

    protected Response response;
    protected GrpcHelper grpc = new GrpcHelper();
    protected final String STORE_UUID_WITH_ML = "684609ad-6360-4bae-9556-03918c1e41c1";
    protected final String STORE_UUID_UNKNOWN_FOR_ML = "7f6b0fa1-ec20-41f9-9246-bfa0d6529dad";
    protected static final String STORE_UUID = UUID.randomUUID().toString();
    protected static final String STORE_UUID_WITH_DIFFERENT_TIMEZONE = UUID.randomUUID().toString();

    @BeforeSuite(alwaysRun = true, description = "Добавляем тестовые магазины")
    public void addStores() {
        addStore(STORE_UUID, 55.7010f, 37.7280f, "Europe/Moscow", false, "00:00:00", "00:00:00", "00:00:00", true, false);
        addStore(STORE_UUID_WITH_DIFFERENT_TIMEZONE, 55.7010f, 37.7280f, "Europe/Kaliningrad", false, "00:00:00", "00:00:00", "00:00:00", true, false);
        checkStore(STORE_UUID_WITH_ML, 55.7006f, 37.7266f, "Europe/Moscow", true, "00:00:00", "00:00:00", "00:00:00", true, false);
        checkStore(STORE_UUID_UNKNOWN_FOR_ML, 55.7010f, 37.7280f, "Europe/Moscow", true, "00:00:00", "00:00:00", "00:00:00", true, false);
        RedisService.del(RedisManager.getConnection(Redis.ETA), List.of("store_" + STORE_UUID_WITH_ML, "store_" + STORE_UUID_UNKNOWN_FOR_ML));
    }

    @AfterMethod(alwaysRun = true, description = "Добавляем системный лог к тесту")
    public void captureFinish() {
        final String result = getLogbackBufferLog();
        clearLogbackLogBuffer();
        Allure.addAttachment("Системный лог теста", result);
    }

    @AfterSuite(alwaysRun = true, description = "Удаляем тестовые магазины")
    public void deleteStores() {
        if (Objects.nonNull(STORE_UUID)) {
            StoreParametersDao.INSTANCE.delete(STORE_UUID);
        }
        if (Objects.nonNull(STORE_UUID_WITH_DIFFERENT_TIMEZONE)) {
            StoreParametersDao.INSTANCE.delete(STORE_UUID_WITH_DIFFERENT_TIMEZONE);
        }
    }
}
