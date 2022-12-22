package ru.instamart.test.api.on_demand.eta;

import eta.Eta;
import eta.PredEtaGrpc;
import io.grpc.StatusRuntimeException;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.SkipException;
import org.testng.annotations.*;
import ru.instamart.api.common.EtaBase;
import ru.instamart.api.helper.EtaHelper;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.redis.*;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.testng.Assert.assertNotEquals;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.helper.EtaHelper.*;
import static ru.instamart.kraken.util.TimeUtil.getZoneDbDate;

@Epic("ETA")
@Feature("Store Eta")
public class StoreEtaTest extends EtaBase {

    private PredEtaGrpc.PredEtaBlockingStub clientEta;
    private boolean etaEnableOnDemandCheck;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientEta = PredEtaGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_ETA));
        etaEnableOnDemandCheck = EtaHelper.getInstance().isStoreOndemand();
    }

    @BeforeMethod(alwaysRun = true)
    public void clearCache() {
        updateStoreWorkingTime(STORE_UUID, "00:00:00", "00:00:00", "00:00:00");
        RedisService.del(RedisManager.getConnection(Redis.ETA), String.format("store_%s", STORE_UUID));
    }

    @CaseIDs(value = {@CaseId(1), @CaseId(58), @CaseId(48), @CaseId(30)})
    @Story("Store ETA")
    @Test(description = "Отправка запроса с валидными store_uuid, lat, lon",
            groups = "ondemand-eta")
    public void getEta() {
        var request = getStoreUserEtaRequest(STORE_UUID, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, STORE_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @CaseId(3)
    @Story("Store ETA")
    @Test(description = "Отправка запроса на 2+ store_uuid",
            groups = "ondemand-eta")
    public void getEtaForTwoStores() {
        var request = Eta.StoreUserEtaRequest.newBuilder()
                .addStoreUuids(STORE_UUID)
                .addStoreUuids(STORE_UUID_WITH_DIFFERENT_TIMEZONE)
                .setLat(55.7006f)
                .setLon(37.7266f)
                .build();

        var response = clientEta.getStoreEta(request);
        checkMultipleStoreEta(response, STORE_UUID, STORE_UUID_WITH_DIFFERENT_TIMEZONE);
    }

    @CaseId(4)
    @Story("Store ETA")
    @Test(description = "Изменение результата, при изменении координат пользователя",
            groups = "ondemand-eta")
    public void getEtaForStoreWithDifferentCoordinates() {
        var requestFirstCoordinates = getStoreUserEtaRequest(STORE_UUID, 55.7006f, 37.7266f);
        var requestSecondCoordinates = getStoreUserEtaRequest(STORE_UUID, 55.7000f, 37.7200f);

        var responseFirstCoordinates = clientEta.getStoreEta(requestFirstCoordinates);
        var responseSecondCoordinates = clientEta.getStoreEta(requestSecondCoordinates);
        checkStoreEta(responseFirstCoordinates, STORE_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
        checkStoreEta(responseSecondCoordinates, STORE_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);

        Allure.step("Проверяем изменение результата при изменении координат пользователя", () -> assertNotEquals(responseFirstCoordinates.getData(0).getEta(), responseSecondCoordinates.getData(0).getEta(), "Поля eta равны для разных координат"));
    }

    @CaseId(9)
    @Story("Store ETA")
    @Test(description = "Отправка запроса с координатами клиента, соответствующими координатам магазина",
            groups = "ondemand-eta")
    public void getEtaForStoreWithSameCoordinates() {
        var request = getStoreUserEtaRequest(STORE_UUID, 55.7010f, 37.7280f);

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, STORE_UUID, 0, "Поле eta меньше или равно нулю", Eta.EstimateSource.FALLBACK);
    }

    @CaseId(5)
    @Story("Store ETA")
    @Test(description = "Отправка запроса с невалидным магазином",
            groups = "ondemand-eta",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation failed")
    public void getEtaWithInvalidStore() {
        var request = getStoreUserEtaRequest("test", 55.7006f, 37.7266f);

        clientEta.getStoreEta(request);
    }

    @CaseId(6)
    @Story("Store ETA")
    @Test(description = "Отправка запроса с невалидным координатами",
            groups = "ondemand-eta",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation failed")
    public void getEtaWithInvalidAddress() {
        var request = getStoreUserEtaRequest(STORE_UUID, 124f, 312f);

        clientEta.getStoreEta(request);
    }

    @CaseId(8)
    @Story("Store ETA")
    @Test(description = "Отправка запроса с валидным, но несуществующим в БД store_uuid",
            groups = "ondemand-eta")
    public void getEtaForNonExistentStore() {
        var request = getStoreUserEtaRequest(UUID.randomUUID().toString(), 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        compareTwoObjects(response.getDataCount(), 0);
    }

    @CaseId(39)
    @Story("Store ETA")
    @Test(description = "Получение пустого ответа при запросе в закрытый магазин (ETA_ENABLE_STORE_ON_DEMAND_CHECK=true)",
            groups = "ondemand-eta")
    public void getEtaForClosedStoreTrue() {

        if (!etaEnableOnDemandCheck) {
            throw new SkipException("Пропускапем, потому что ETA_ENABLE_STORE_ON_DEMAND_CHECK = false");
        }

        String openingDate = getZoneDbDate(LocalDateTime.now().minusMinutes(2));
        String closingDate = getZoneDbDate(LocalDateTime.now().minusMinutes(1));
        updateStoreWorkingTime(STORE_UUID, openingDate, closingDate, "00:00:00");

        var request = getStoreUserEtaRequest(STORE_UUID, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        compareTwoObjects(response.getDataCount(), 0);
    }


    @CaseId(44)
    @Story("Store ETA")
    @Test(description = "Получение пустого ответа при запросе в пределах работы параметра OnDemandClosingDelta (ETA_ENABLE_STORE_ON_DEMAND_CHECK=true)",
            groups = "ondemand-eta")
    public void getEtaForClosedStoreViaClosingDeltaTrue() {

        if (!etaEnableOnDemandCheck) {
            throw new SkipException("Пропускапем, потому что ETA_ENABLE_STORE_ON_DEMAND_CHECK = false");
        }

        String openingDate = getZoneDbDate(LocalDateTime.now().minusHours(1));
        String closingDate = getZoneDbDate(LocalDateTime.now().plusMinutes(30));
        updateStoreWorkingTime(STORE_UUID, openingDate, closingDate, "00:30:00");

        var request = getStoreUserEtaRequest(STORE_UUID, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        compareTwoObjects(response.getDataCount(), 0);
    }

    @CaseId(51)
    @Story("Store ETA")
    @Test(description = "Получение пустого ответа при запросе с OnDemandClosingDelta равным времени работы магазина (ETA_ENABLE_STORE_ON_DEMAND_CHECK=true)",
            groups = "ondemand-eta")
    public void getEtaForClosedStoreEqualClosingDeltaTrue() {

        if (!etaEnableOnDemandCheck) {
            throw new SkipException("Пропускапем, потому что ETA_ENABLE_STORE_ON_DEMAND_CHECK = false");
        }

        String openingDate = getZoneDbDate(LocalDateTime.now().minusHours(1));
        String closingDate = getZoneDbDate(LocalDateTime.now().plusHours(1));
        updateStoreWorkingTime(STORE_UUID, openingDate, closingDate, "02:00:00");

        var request = getStoreUserEtaRequest(STORE_UUID, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        compareTwoObjects(response.getDataCount(), 0);
    }

    @CaseId(247)
    @Story("Store ETA")
    @Test(description = "Получение ЕТА при запросе в закрытый магазин (ETA_ENABLE_STORE_ON_DEMAND_CHECK=false)",
            groups = "ondemand-eta")
    public void getEtaForClosedStoreFalse() {

        if (etaEnableOnDemandCheck) {
            throw new SkipException("Пропускапем, потому что ETA_ENABLE_STORE_ON_DEMAND_CHECK = true");
        }

        String openingDate = getZoneDbDate(LocalDateTime.now().minusMinutes(2));
        String closingDate = getZoneDbDate(LocalDateTime.now().minusMinutes(1));
        updateStoreWorkingTime(STORE_UUID, openingDate, closingDate, "00:00:00");

        var request = getStoreUserEtaRequest(STORE_UUID, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, STORE_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }


    @CaseId(249)
    @Story("Store ETA")
    @Test(description = "Получение ЕТА при запросе в пределах работы параметра OnDemandClosingDelta (ETA_ENABLE_STORE_ON_DEMAND_CHECK=false)",
            groups = "ondemand-eta")
    public void getEtaForClosedStoreViaClosingDeltaFalse() {

        if (etaEnableOnDemandCheck) {
            throw new SkipException("Пропускапем, потому что ETA_ENABLE_STORE_ON_DEMAND_CHECK = true");
        }

        String openingDate = getZoneDbDate(LocalDateTime.now().minusHours(1));
        String closingDate = getZoneDbDate(LocalDateTime.now().plusMinutes(30));
        updateStoreWorkingTime(STORE_UUID, openingDate, closingDate, "00:30:00");

        var request = getStoreUserEtaRequest(STORE_UUID, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, STORE_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @CaseId(250)
    @Story("Store ETA")
    @Test(description = "Получение ЕТА при запросе с OnDemandClosingDelta равным времени работы магазина (ETA_ENABLE_STORE_ON_DEMAND_CHECK=false)",
            groups = "ondemand-eta")
    public void getEtaForClosedStoreEqualClosingDeltaFalse() {

        if (etaEnableOnDemandCheck) {
            throw new SkipException("Пропускапем, потому что ETA_ENABLE_STORE_ON_DEMAND_CHECK = true");
        }

        String openingDate = getZoneDbDate(LocalDateTime.now().minusHours(1));
        String closingDate = getZoneDbDate(LocalDateTime.now().plusHours(1));
        updateStoreWorkingTime(STORE_UUID, openingDate, closingDate, "02:00:00");

        var request = getStoreUserEtaRequest(STORE_UUID, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, STORE_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @CaseId(54)
    @Story("Store ETA")
    @Test(description = "Отправка валидного запроса в магазин, который в одном часовом поясе открыт, а другом закрыт",
            groups = "ondemand-eta")
    public void getEtaForClosedStoreInDifferentTimezone() {
        String openingDate = getZoneDbDate(LocalDateTime.now().minusMinutes(5));
        String closingDate = getZoneDbDate(LocalDateTime.now().plusMinutes(5));
        updateStoreWorkingTime(STORE_UUID_WITH_DIFFERENT_TIMEZONE, openingDate, closingDate, "00:00:00");
        RedisService.del(RedisManager.getConnection(Redis.ETA), String.format("store_%s", STORE_UUID_WITH_DIFFERENT_TIMEZONE));

        var request = getStoreUserEtaRequest(STORE_UUID_WITH_DIFFERENT_TIMEZONE, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, STORE_UUID_WITH_DIFFERENT_TIMEZONE, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @CaseId(57)
    @Story("Store ETA")
    @Test(description = "Получение ответа от ML",
            groups = "ondemand-eta")
    public void getEtaWithML() {
        var request = getStoreUserEtaRequest(STORE_UUID_WITH_ML, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, STORE_UUID_WITH_ML, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.ML);
    }

    @CaseId(36)
    @Story("Store ETA")
    @Test(description = "Проверка, что рассчитывается фоллбэк, в случае если ML возвращает ноль",
            groups = "ondemand-eta")
    public void getEtaForZeroML() {
        var request = getStoreUserEtaRequest(STORE_UUID_UNKNOWN_FOR_ML, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, STORE_UUID_UNKNOWN_FOR_ML, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }
}
