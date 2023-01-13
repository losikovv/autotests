package ru.instamart.test.api.on_demand.shippingcalc;

import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.ShippingCalcBase;
import ru.instamart.redis.Redis;
import ru.instamart.redis.RedisManager;
import ru.instamart.redis.RedisService;

import java.util.UUID;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.helper.SurgeLevelHelper.publishEventSurge;

@Epic("ShippingCalc")
@Feature("Redis")
public class RedisTest extends ShippingCalcBase {

    private final String STORE_ID = UUID.randomUUID().toString();

    @TmsLink("286")
    @Story("Redis")
    @Test(description = "Запись/обновление surgelevel к магазину в redis",
            groups = "ondemand-shippingcalc")
    public void createKeyForSurge() {
        publishEventSurge(STORE_ID, 1, 1);

        Allure.step("Проверка сохранения surgelevel в redis", () -> assertTrue(RedisService.isExist(RedisManager.getConnection(Redis.SHIPPINGCALC), "store:" + STORE_ID), "Ключ не нашелся"));
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        RedisService.del(RedisManager.getConnection(Redis.SHIPPINGCALC), "store:" + STORE_ID);
    }
}
