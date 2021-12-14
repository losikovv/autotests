package ru.instamart.test.api.v2.e2e;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import ru.sbermarket.qase.annotation.CaseId;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.helper.RegistrationHelper;
import ru.instamart.api.model.v2.StoreV2;
import ru.instamart.api.model.v2.ZoneV2;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Run;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;

@Epic("ApiV2")
@Feature("E2E тесты")
@Slf4j
public class ZoneE2ETests extends RestBase {

    @BeforeClass(description = "Проверка самих провайдеров")
    public void selfTest() {
        RestDataProvider.getAvailableZones();
    }

    @CaseId(109)
    @Test(  dataProvider = "zones-parallel",
            dataProviderClass = RestDataProvider.class,
            description = "Тест первых заказов во всех зонах всех магазинов",
            groups = {"api-zones"})
    public void firstOrderByZone(StoreV2 store, String zoneName, ZoneV2 coordinates) {
        apiV2.skipTestIfOnlyPickupIsAvailable(store, zoneName);

        log.debug("Оформляем заказ в {} zone={} coordinates={}", store, zoneName, coordinates);

        final UserData userData = UserManager.getUser();
        RegistrationHelper.registration(userData);
        apiV2.order(userData, store.getId(), coordinates);
        apiV2.cancelCurrentOrder();
    }

    @Run(onServer = Server.PRODUCTION)
    @AfterMethod(description = "Отмена активных заказов",
                 alwaysRun = true)
    public void cancelActiveOrders() {
        log.debug("Отменяем активные заказы");
        apiV2.cancelActiveOrders();
    }
}
