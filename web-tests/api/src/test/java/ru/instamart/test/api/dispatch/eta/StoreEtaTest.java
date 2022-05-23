package ru.instamart.test.api.dispatch.eta;

import eta.Eta;
import eta.PredEtaGrpc;
import io.grpc.StatusRuntimeException;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.eta.ServiceParametersDao;
import ru.instamart.jdbc.dao.stf.StoresDao;
import ru.instamart.jdbc.entity.eta.ServiceParametersEntity;
import ru.instamart.jdbc.entity.stf.StoresEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.StartPointsTenants;
import ru.instamart.kraken.data.user.UserData;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.testng.Assert.assertNotEquals;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.EtaCheckpoints.checkMultipleStoreEta;
import static ru.instamart.api.checkpoint.EtaCheckpoints.checkStoreEta;
import static ru.instamart.api.helper.EtaHelper.*;
import static ru.instamart.kraken.util.TimeUtil.getZoneDbDate;

@Epic("On Demand")
@Feature("ETA")
public class StoreEtaTest extends RestBase {

    private PredEtaGrpc.PredEtaBlockingStub clientEta;
    private AddressV2 address;
    private UserData userData;
    private String storeUuid;
    private String secondStoreUuid;
    private ServiceParametersEntity serviceParameters;
    private boolean isMLTimeoutUpdated;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientEta = PredEtaGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_ETA));
        SessionFactory.makeSession(SessionType.API_V2);
        userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        storeUuid = StoresDao.INSTANCE.findById(EnvironmentProperties.DEFAULT_SID).get().getUuid();
        secondStoreUuid = StoresDao.INSTANCE.findById(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID).get().getUuid();
        address = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_SID);
    }

    @CaseIDs(value = {@CaseId(1), @CaseId(58)})
    @Story("Store ETA")
    @Test(description = "Отправка запроса с валидными store_uuid, lat, lon",
            groups = "dispatch-eta-smoke")
    public void getEta() {
        Eta.StoreUserEtaRequest request = getStoreUserEtaRequest(storeUuid, StartPointsTenants.ETA.getLat().floatValue(), StartPointsTenants.ETA.getLon().floatValue());

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, storeUuid, 0, "Поле eta меньше или равно нулю", Eta.EstimateSource.FALLBACK);
    }

    @CaseId(3)
    @Story("Store ETA")
    @Test(description = "Отправка запроса на 2+ store_uuid",
            groups = "dispatch-eta-smoke")
    public void getEtaForTwoStores() {
        Eta.StoreUserEtaRequest request = Eta.StoreUserEtaRequest.newBuilder()
                .addStoreUuids(storeUuid)
                .addStoreUuids(secondStoreUuid)
                .setLat(address.getLat().floatValue())
                .setLon(address.getLon().floatValue())
                .build();

        var response = clientEta.getStoreEta(request);
        checkMultipleStoreEta(response, storeUuid, secondStoreUuid);
    }

    @CaseId(4)
    @Story("Store ETA")
    @Test(description = "Изменение результата, при изменении координат пользователя",
            groups = "dispatch-eta-regress")
    public void getEtaForStoreWithDifferentCoordinates() {
        Eta.StoreUserEtaRequest requestFirstCoordinates = getStoreUserEtaRequest(storeUuid, StartPointsTenants.ETA.getLat().floatValue(), StartPointsTenants.ETA.getLon().floatValue());
        Eta.StoreUserEtaRequest requestSecondCoordinates = getStoreUserEtaRequest(storeUuid, StartPointsTenants.METRO_3.getLat().floatValue(), StartPointsTenants.METRO_3.getLon().floatValue());

        var responseFirstCoordinates = clientEta.getStoreEta(requestFirstCoordinates);
        var responseSecondCoordinates = clientEta.getStoreEta(requestSecondCoordinates);
        checkStoreEta(responseFirstCoordinates, storeUuid, 0, "Поле eta меньше или равно нулю", Eta.EstimateSource.FALLBACK);
        checkStoreEta(responseSecondCoordinates, storeUuid, 0, "Поле eta меньше или равно нулю", Eta.EstimateSource.FALLBACK);

        Allure.step("Проверяем изменение результата при изменении координат пользователя", () -> {
            assertNotEquals(responseFirstCoordinates.getData(0).getEta(), responseSecondCoordinates.getData(0).getEta(), "Поля eta равны для разных координат");
        });
    }

    @CaseId(9)
    @Story("Store ETA")
    @Test(description = "Отправка запроса с координатами клиента, соответствующими координатам магазина",
            groups = "dispatch-eta-smoke")
    public void getEtaForStoreWithSameCoordinates() {
        Eta.StoreUserEtaRequest request = getStoreUserEtaRequest(storeUuid, address.getLat().floatValue(), address.getLon().floatValue());

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, storeUuid, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @CaseId(5)
    @Story("Store ETA")
    @Test(description = "Отправка запроса с невалидным магазином",
            groups = "dispatch-eta-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation failed")
    public void getEtaWithInvalidStore() {
        Eta.StoreUserEtaRequest request = getStoreUserEtaRequest("test", address.getLat().floatValue(), address.getLon().floatValue());

        var response = clientEta.getStoreEta(request);
    }

    @CaseId(6)
    @Story("Store ETA")
    @Test(description = "Отправка запроса с невалидным координатами",
            groups = "dispatch-eta-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation failed")
    public void getEtaWithInvalidAddress() {
        Eta.StoreUserEtaRequest request = getStoreUserEtaRequest(storeUuid, 124f, 312f);

        var response = clientEta.getStoreEta(request);
    }

    @CaseId(8)
    @Story("Store ETA")
    @Test(description = "Отправка запроса с валидным, но несуществующим в БД store_uuid",
            groups = "dispatch-eta-smoke")
    public void getEtaForNonExistentStore() {
        Eta.StoreUserEtaRequest request = getStoreUserEtaRequest(UUID.randomUUID().toString(), address.getLat().floatValue(), address.getLon().floatValue());

        var response = clientEta.getStoreEta(request);
        compareTwoObjects(response.getDataCount(), 0);
    }

    @CaseId(39)
    @Story("Store ETA")
    @Test(description = "Отправка валидного запроса в закрытый магазин",
            groups = "dispatch-eta-smoke")
    public void getEtaForClosedStore() {
        String openingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(2)));
        String closingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(1)));
        StoresEntity store = getStoreWithUpdatedSchedule(openingDate, closingDate, "00:00:00");
        Eta.StoreUserEtaRequest request = getStoreUserEtaRequest(store.getUuid(), address.getLat().floatValue(), address.getLon().floatValue());

        var response = clientEta.getStoreEta(request);
        compareTwoObjects(response.getDataCount(), 0);
    }

    @CaseId(44)
    @Story("Store ETA")
    @Test(description = "Отправка валидного запроса в пределах работы параметра OnDemandClosingDelta",
            groups = "dispatch-eta-smoke")
    public void getEtaForClosedStoreViaClosingDelta() {
        String openingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().minusHours(1)));
        String closingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().plusMinutes(30)));
        StoresEntity store = getStoreWithUpdatedSchedule(openingDate, closingDate, "00:30:00");
        Eta.StoreUserEtaRequest request = getStoreUserEtaRequest(store.getUuid(), address.getLat().floatValue(), address.getLon().floatValue());

        var response = clientEta.getStoreEta(request);
        compareTwoObjects(response.getDataCount(), 0);
    }

    @CaseId(51)
    @Story("Store ETA")
    @Test(description = "Отправка запроса с OnDemandClosingDelta равным времени работы магазина",
            groups = "dispatch-eta-regress")
    public void getEtaForClosedStoreEqualClosingDelta() {
        String openingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().minusHours(1)));
        String closingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().plusHours(1)));
        StoresEntity store = getStoreWithUpdatedSchedule(openingDate, closingDate, "02:00:00");
        Eta.StoreUserEtaRequest request = getStoreUserEtaRequest(store.getUuid(), address.getLat().floatValue(), address.getLon().floatValue());

        var response = clientEta.getStoreEta(request);
        compareTwoObjects(response.getDataCount(), 0);
    }

    @CaseId(54)
    @Story("Store ETA")
    @Test(description = "Отправка валидного запроса в магазин, который в одном часовом поясе открыт, а другом закрыт",
            groups = "dispatch-eta-regress")
    public void getEtaForClosedStoreInDifferentTimezone() {
        String openingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(5)));
        String closingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().plusMinutes(5)));
        StoresEntity store = getStoreWithDifferentTimezone(openingDate, closingDate, "00:00:00", "Europe/Kaliningrad");
        Eta.StoreUserEtaRequest request = getStoreUserEtaRequest(store.getUuid(), address.getLat().floatValue(), address.getLon().floatValue());

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, store.getUuid(), 0, "Поле eta меньше или равно нулю", Eta.EstimateSource.FALLBACK);
    }

    @CaseId(57)
    @Story("Store ETA")
    @Test(description = "Получение ответа от ML",
            groups = "dispatch-eta-smoke")
    public void getEtaWithML() {
        String storeUuid = StoresDao.INSTANCE.findById(EnvironmentProperties.DEFAULT_ON_DEMAND_SID).get().getUuid();
        Eta.StoreUserEtaRequest request = getStoreUserEtaRequest(storeUuid, address.getLat().floatValue(), address.getLon().floatValue());

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, storeUuid, 0, "Поле eta меньше или равно нулю", Eta.EstimateSource.ML);
    }

    @CaseId(36)
    @Story("Store ETA")
    @Test(description = "Проверка, что рассчитывается фоллбэк, в случае если ML возвращает ноль",
            groups = "dispatch-eta-smoke")
    public void getEtaForZeroML() {
        //магазин, которого нет в ML, но есть в ETA
        String storeUuid = "7f6b0fa1-ec20-41f9-9246-bfa0d6529dad";
        Eta.StoreUserEtaRequest request = getStoreUserEtaRequest(storeUuid, address.getLat().floatValue(), address.getLon().floatValue());

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, storeUuid, 0, "Поле eta меньше или равно нулю", Eta.EstimateSource.FALLBACK);
    }

    @CaseId(62)
    @Story("Store ETA")
    @Test(description = "Проверка отсутствия ошибки, в случае, если ML не возвращает ответ по таймауту",
            groups = "dispatch-eta-smoke")
    public void getEtaWithFallbackAfterMLTimeout() {
        String storeUuid = StoresDao.INSTANCE.findById(EnvironmentProperties.DEFAULT_ON_DEMAND_SID).get().getUuid();
        serviceParameters = ServiceParametersDao.INSTANCE.getServiceParameters();
        isMLTimeoutUpdated = checkMLTimeout(serviceParameters.getWaitMlTimeout());
        Eta.StoreUserEtaRequest request = getStoreUserEtaRequest(storeUuid, address.getLat().floatValue(), address.getLon().floatValue());

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, storeUuid, 0, "Поле eta меньше или равно нулю", Eta.EstimateSource.FALLBACK);
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        if (isMLTimeoutUpdated) {
            ServiceParametersDao.INSTANCE.updateWaitMlTimeout(serviceParameters.getWaitMlTimeout());
        }
    }
}
