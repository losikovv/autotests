package ru.instamart.test.api.on_demand.eta;

import eta.Eta;
import eta.PredEtaGrpc;
import io.grpc.StatusRuntimeException;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.*;
import ru.instamart.api.common.EtaBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.redis.*;
import io.qameta.allure.TmsLinks;
import io.qameta.allure.TmsLink;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.helper.EtaHelper.*;
import static ru.instamart.kraken.util.TimeUtil.getZoneDbDate;

@Epic("ETA")
@Feature("Store Eta")
public class StoreEtaTest extends EtaBase {

    private PredEtaGrpc.PredEtaBlockingStub clientEta;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientEta = PredEtaGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_ETA));
        RedisService.del(RedisManager.getConnection(Redis.ETA), String.format("store_%s", STORE_UUID));
    }

    @BeforeMethod(alwaysRun = true)
    public void clearCache() {
        updateStoreWorkingTime(STORE_UUID, "00:00:00", "00:00:00", "00:00:00");
        RedisService.del(RedisManager.getConnection(Redis.ETA), String.format("store_%s", STORE_UUID));
    }

    @TmsLinks(value = {@TmsLink("1"), @TmsLink("58"), @TmsLink("48"), @TmsLink("30"), @TmsLink("275")})
    @Story("Store ETA")
    @Test(description = "Отправка запроса с валидными store_uuid, lat, lon",
            groups = "ondemand-eta")
    public void getEta() {
        var request = getStoreUserEtaRequest(STORE_UUID, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, STORE_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
        Allure.step("Проверяем наличие в ответе isEtaDisabled=false", () -> assertFalse(response.getData(0).getIsEtaDisabled(), "В ответе получили isEtaDisabled=true"));
    }

    @TmsLink("3")
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

    @TmsLink("4")
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

    @TmsLink("9")
    @Story("Store ETA")
    @Test(description = "Отправка запроса с координатами клиента, соответствующими координатам магазина",
            groups = "ondemand-eta")
    public void getEtaForStoreWithSameCoordinates() {
        var request = getStoreUserEtaRequest(STORE_UUID, 55.7010f, 37.7280f);

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, STORE_UUID, 0, "Поле eta меньше или равно нулю", Eta.EstimateSource.FALLBACK);
    }

    @TmsLink("5")
    @Story("Store ETA")
    @Test(description = "Отправка запроса с невалидным магазином",
            groups = "ondemand-eta",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation failed")
    public void getEtaWithInvalidStore() {
        var request = getStoreUserEtaRequest("test", 55.7006f, 37.7266f);

        clientEta.getStoreEta(request);
    }

    @TmsLink("6")
    @Story("Store ETA")
    @Test(description = "Отправка запроса с невалидным координатами",
            groups = "ondemand-eta",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation failed")
    public void getEtaWithInvalidAddress() {
        var request = getStoreUserEtaRequest(STORE_UUID, 124f, 312f);

        clientEta.getStoreEta(request);
    }

    @TmsLink("284")
    @Story("Store ETA")
    @Test(description = "Отправка запроса с нулевыми координатами",
            groups = "ondemand-eta",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation failed")
    public void getEtaWithZeroCoordinates() {
        var request = getStoreUserEtaRequest(STORE_UUID, 0, 0);

        clientEta.getStoreEta(request);
    }


    @TmsLink("8")
    @Story("Store ETA")
    @Test(description = "Отправка запроса с валидным, но несуществующим в БД store_uuid",
            groups = "ondemand-eta")
    public void getEtaForNonExistentStore() {
        var request = getStoreUserEtaRequest(UUID.randomUUID().toString(), 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        compareTwoObjects(response.getDataCount(), 0);
    }

    @TmsLink("247")
    @Story("Store ETA")
    @Test(description = "Получение ЕТА при запросе в закрытый магазин",
            groups = "ondemand-eta")
    public void getEtaForClosedStore() {
        String openingDate = getZoneDbDate(LocalDateTime.now().minusMinutes(2));
        String closingDate = getZoneDbDate(LocalDateTime.now().minusMinutes(1));
        updateStoreWorkingTime(STORE_UUID, openingDate, closingDate, "00:00:00");

        var request = getStoreUserEtaRequest(STORE_UUID, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, STORE_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }


    @TmsLink("249")
    @Story("Store ETA")
    @Test(description = "Получение ЕТА при запросе в пределах работы параметра OnDemandClosingDelta",
            groups = "ondemand-eta")
    public void getEtaForClosedStoreViaClosingDelta() {
        String openingDate = getZoneDbDate(LocalDateTime.now().minusHours(1));
        String closingDate = getZoneDbDate(LocalDateTime.now().plusMinutes(30));
        updateStoreWorkingTime(STORE_UUID, openingDate, closingDate, "00:30:00");

        var request = getStoreUserEtaRequest(STORE_UUID, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, STORE_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @TmsLink("250")
    @Story("Store ETA")
    @Test(description = "Получение ЕТА при запросе с OnDemandClosingDelta равным времени работы магазина",
            groups = "ondemand-eta")
    public void getEtaForClosedStoreEqualClosingDelta() {
        String openingDate = getZoneDbDate(LocalDateTime.now().minusHours(1));
        String closingDate = getZoneDbDate(LocalDateTime.now().plusHours(1));
        updateStoreWorkingTime(STORE_UUID, openingDate, closingDate, "02:00:00");

        var request = getStoreUserEtaRequest(STORE_UUID, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, STORE_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @TmsLink("54")
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

    @TmsLink("57")
    @Story("Store ETA")
    @Test(description = "Получение ответа от ML",
            groups = "ondemand-eta")
    public void getEtaWithML() {
        var request = getStoreUserEtaRequest(STORE_UUID_WITH_ML, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, STORE_UUID_WITH_ML, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.ML);
    }

    @TmsLink("36")
    @Story("Store ETA")
    @Test(description = "Проверка, что рассчитывается фоллбэк, в случае если ML возвращает ноль",
            groups = "ondemand-eta")
    public void getEtaForZeroML() {
        var request = getStoreUserEtaRequest(STORE_UUID_UNKNOWN_FOR_ML, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, STORE_UUID_UNKNOWN_FOR_ML, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @TmsLink("244")
    @Story("Store ETA")
    @Test(description = "Отправка запроса в магазин, у которого имеются интервалы DisableEta",
            groups = "ondemand-eta")
    public void getEtaForDisableIntervals() {
        var request = getStoreUserEtaRequest(STORE_UUID_DISABLED, 55.7006f, 37.7266f);

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, STORE_UUID_DISABLED, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
        Allure.step("Проверяем наличие в ответе isEtaDisabled=true", () -> assertTrue(response.getData(0).getIsEtaDisabled(), "В ответе получили isEtaDisabled=false"));
    }
}
