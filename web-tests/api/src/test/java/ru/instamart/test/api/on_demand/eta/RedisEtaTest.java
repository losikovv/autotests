package ru.instamart.test.api.on_demand.eta;

import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.EtaBase;
import ru.instamart.api.request.eta.RetailerParametersEtaRequest;
import ru.instamart.api.request.eta.ServiceParametersEtaRequest;
import ru.instamart.api.request.eta.StoreParametersEtaRequest;
import ru.instamart.redis.Redis;
import ru.instamart.redis.RedisManager;
import ru.instamart.redis.RedisService;

import java.util.List;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;

@Epic("ETA")
@Feature("Redis")
public final class RedisEtaTest extends EtaBase {

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        RedisService.del(RedisManager.getConnection(Redis.ETA), List.of(
                "service_parameters",
                "store_" + STORE_UUID,
                "retailer_" + RETAILER_ID
        ));
    }

    @TmsLink("236")
    @Story("Redis ETA")
    @Test(description = "Кеширование настроек сервиса в redis",
            groups = "ondemand-eta")
    public void createKeyForService() {
        final var response = ServiceParametersEtaRequest.GET();

        checkStatusCode(response, 200, "");
        Allure.step("Проверка сохранения кеша в redis", () -> assertTrue(RedisService.isExist(RedisManager.getConnection(Redis.ETA), "service_parameters"), "Ключ не нашелся"));
    }

    @TmsLink("277")
    @Story("Redis ETA")
    @Test(description = "Кеширование настроек ритейлера в redis",
            groups = "ondemand-eta")
    public void createKeyForRetailer() {
        final var response = RetailerParametersEtaRequest.GET(RETAILER_ID);

        checkStatusCode(response, 200, "");
        Allure.step("Проверка сохранения кеша в redis", () -> assertTrue(RedisService.isExist(RedisManager.getConnection(Redis.ETA), "retailer_" + RETAILER_ID), "Ключ не нашелся"));
    }

    @TmsLink("278")
    @Story("Redis ETA")
    @Test(description = "Кеширование настроек магазина в redis",
            groups = "ondemand-eta")
    public void createKeyForStore() {
        final var response = StoreParametersEtaRequest.GET(STORE_UUID);

        checkStatusCode(response, 200, "");
        Allure.step("Проверка сохранения кеша в redis", () -> assertTrue(RedisService.isExist(RedisManager.getConnection(Redis.ETA), "store_" + STORE_UUID), "Ключ не нашелся"));
    }
}
