package ru.instamart.test.api.on_demand.eta;

import eta.Eta;
import eta.PredEtaGrpc;
import io.grpc.StatusRuntimeException;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.*;
import ru.instamart.api.common.RestBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.eta.StoreParametersDao;
import ru.instamart.redis.*;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static org.testng.Assert.assertNotEquals;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.helper.EtaHelper.*;
import static ru.instamart.kraken.util.TimeUtil.getZoneDbDate;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Epic("On Demand")
@Feature("ETA")
public class StoreEtaTest extends RestBase {

    private PredEtaGrpc.PredEtaBlockingStub clientEta;
    private final String STORE_UUID = UUID.randomUUID().toString();
    private final String STORE_UUID_WITH_DIFFERENT_TIMEZONE = UUID.randomUUID().toString();
    //ML работает не со всеми магазинами на стейдже, с STORE_UUID_WITH_ML должно работать
    private final String STORE_UUID_WITH_ML = "684609ad-6360-4bae-9556-03918c1e41c1";

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientEta = PredEtaGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_ETA));
        addStore(STORE_UUID, 55.7010f, 37.7280f, "Europe/Moscow", false, "00:00:00", "00:00:00", "00:00:00", true);
        addStore(STORE_UUID_WITH_DIFFERENT_TIMEZONE, 55.7010f, 37.7280f, "Europe/Kaliningrad", false, "00:00:00", "00:00:00", "00:00:00", true);
    }

    @BeforeMethod(alwaysRun = true)
    public void clearCache() {
        updateStoreWorkingTime(STORE_UUID, "00:00:00", "00:00:00", "00:00:00");
        RedisService.del(RedisManager.getConnection(Redis.ETA), String.format("store_%s", STORE_UUID));
    }

    @CaseIDs(value = {@CaseId(1), @CaseId(58), @CaseId(48)})
    @Story("Store ETA")
    @Test(description = "Отправка запроса с валидными store_uuid, lat, lon",
            groups = "dispatch-eta-smoke")
    public void getEta() {
        var request = getStoreUserEtaRequest(STORE_UUID, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, STORE_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @CaseId(3)
    @Story("Store ETA")
    @Test(description = "Отправка запроса на 2+ store_uuid",
            groups = "dispatch-eta-smoke")
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
            groups = "dispatch-eta-regress")
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
            groups = "dispatch-eta-smoke")
    public void getEtaForStoreWithSameCoordinates() {
        var request = getStoreUserEtaRequest(STORE_UUID, 55.7010f, 37.7280f);

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, STORE_UUID, 0, "Поле eta меньше или равно нулю", Eta.EstimateSource.FALLBACK);
    }

    @CaseId(5)
    @Story("Store ETA")
    @Test(description = "Отправка запроса с невалидным магазином",
            groups = "dispatch-eta-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation failed")
    public void getEtaWithInvalidStore() {
        var request = getStoreUserEtaRequest("test", 55.7006f, 37.7266f);

        clientEta.getStoreEta(request);
    }

    @CaseId(6)
    @Story("Store ETA")
    @Test(description = "Отправка запроса с невалидным координатами",
            groups = "dispatch-eta-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation failed")
    public void getEtaWithInvalidAddress() {
        var request = getStoreUserEtaRequest(STORE_UUID, 124f, 312f);

        clientEta.getStoreEta(request);
    }

    @CaseId(8)
    @Story("Store ETA")
    @Test(description = "Отправка запроса с валидным, но несуществующим в БД store_uuid",
            groups = "dispatch-eta-smoke")
    public void getEtaForNonExistentStore() {
        var request = getStoreUserEtaRequest(UUID.randomUUID().toString(), 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        compareTwoObjects(response.getDataCount(), 0);
    }

    @CaseId(39)
    @Story("Store ETA")
    @Test(description = "Отправка валидного запроса в закрытый магазин",
            groups = "dispatch-eta-smoke")
    public void getEtaForClosedStore() {
        String openingDate = getZoneDbDate(LocalDateTime.now().minusMinutes(2));
        String closingDate = getZoneDbDate(LocalDateTime.now().minusMinutes(1));
        updateStoreWorkingTime(STORE_UUID, openingDate, closingDate, "00:00:00");

        var request = getStoreUserEtaRequest(STORE_UUID, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        compareTwoObjects(response.getDataCount(), 0);
    }

    @CaseId(44)
    @Story("Store ETA")
    @Test(description = "Отправка валидного запроса в пределах работы параметра OnDemandClosingDelta",
            groups = "dispatch-eta-smoke")
    public void getEtaForClosedStoreViaClosingDelta() {
        String openingDate = getZoneDbDate(LocalDateTime.now().minusHours(1));
        String closingDate = getZoneDbDate(LocalDateTime.now().plusMinutes(30));
        updateStoreWorkingTime(STORE_UUID, openingDate, closingDate, "00:30:00");

        var request = getStoreUserEtaRequest(STORE_UUID, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        compareTwoObjects(response.getDataCount(), 0);
    }

    @CaseId(51)
    @Story("Store ETA")
    @Test(description = "Отправка запроса с OnDemandClosingDelta равным времени работы магазина",
            groups = "dispatch-eta-regress")
    public void getEtaForClosedStoreEqualClosingDelta() {
        String openingDate = getZoneDbDate(LocalDateTime.now().minusHours(1));
        String closingDate = getZoneDbDate(LocalDateTime.now().plusHours(1));
        updateStoreWorkingTime(STORE_UUID, openingDate, closingDate, "02:00:00");

        var request = getStoreUserEtaRequest(STORE_UUID, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        compareTwoObjects(response.getDataCount(), 0);
    }

    @CaseId(54)
    @Story("Store ETA")
    @Test(description = "Отправка валидного запроса в магазин, который в одном часовом поясе открыт, а другом закрыт",
            groups = "dispatch-eta-regress")
    public void getEtaForClosedStoreInDifferentTimezone() {
        String openingDate = getZoneDbDate(LocalDateTime.now().minusMinutes(5));
        String closingDate = getZoneDbDate(LocalDateTime.now().plusMinutes(5));
        updateStoreWorkingTime(STORE_UUID_WITH_DIFFERENT_TIMEZONE, openingDate, closingDate, "00:00:00");

        var request = getStoreUserEtaRequest(STORE_UUID_WITH_DIFFERENT_TIMEZONE, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, STORE_UUID_WITH_DIFFERENT_TIMEZONE, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @CaseId(57)
    @Story("Store ETA")
    @Test(description = "Получение ответа от ML",
            groups = "dispatch-eta-smoke")
    public void getEtaWithML() {
        updateStoreMLStatus(STORE_UUID_WITH_DIFFERENT_TIMEZONE, true);
        RedisService.del(RedisManager.getConnection(Redis.ETA), String.format("store_%s", STORE_UUID_WITH_ML));

        var request = getStoreUserEtaRequest(STORE_UUID_WITH_ML, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, STORE_UUID_WITH_ML, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.ML);
    }

    @CaseId(36)
    @Story("Store ETA")
    @Test(description = "Проверка, что рассчитывается фоллбэк, в случае если ML возвращает ноль",
            groups = "dispatch-eta-smoke")
    public void getEtaForZeroML() {
        //магазин, которого нет в ML, но есть в ETA
        String storeUuid = "7f6b0fa1-ec20-41f9-9246-bfa0d6529dad";
        updateStoreMLStatus(storeUuid, true);

        var request = getStoreUserEtaRequest(storeUuid, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, storeUuid, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        if (Objects.nonNull(STORE_UUID) ) {
            StoreParametersDao.INSTANCE.delete(STORE_UUID);
        }
        if (Objects.nonNull(STORE_UUID_WITH_DIFFERENT_TIMEZONE) ) {
            StoreParametersDao.INSTANCE.delete(STORE_UUID_WITH_DIFFERENT_TIMEZONE);
        }
    }
}
