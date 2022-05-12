package ru.instamart.test.api.dispatch.eta;

import eta.Eta;
import eta.PredEtaGrpc;
import io.grpc.StatusRuntimeException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.eta.ServiceParametersDao;
import ru.instamart.jdbc.dao.stf.PaymentMethodStoresDao;
import ru.instamart.jdbc.dao.stf.StoreConfigsDao;
import ru.instamart.jdbc.dao.stf.StoresDao;
import ru.instamart.jdbc.dao.stf.StoresTenantsDao;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.EtaCheckpoints.checkStoreEta;
import static ru.instamart.api.helper.EtaHelper.*;
import static ru.instamart.kraken.util.TimeUtil.getDbDate;

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
        address = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_SID);
        SessionFactory.makeSession(SessionType.API_V2);
        userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        storeUuid = StoresDao.INSTANCE.findById(EnvironmentProperties.DEFAULT_SID).get().getUuid();
        secondStoreUuid = StoresDao.INSTANCE.findById(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID).get().getUuid();
    }

    @CaseIDs(value = {@CaseId(1), @CaseId(58)})
    @Story("User ETA")
    @Test(description = "Отправка запроса с валидными store_uuid, lat, lon",
            groups = "dispatch-eta-smoke")
    public void getEta() {
        var request = Eta.StoreUserEtaRequest.newBuilder()
                .addStoreUuids(storeUuid)
                .setLat(StartPointsTenants.ETA.getLat().floatValue())
                .setLon(StartPointsTenants.ETA.getLon().floatValue())
                .build();

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, storeUuid, 0, "Поле eta меньше или равно нулю");
    }

    @CaseId(3)
    @Story("User ETA")
    @Test(description = "Отправка запроса на 2+ store_uuid",
            groups = "dispatch-eta-smoke")
    public void getEtaForTwoStores() {
        Eta.StoreUserEtaRequest request =  Eta.StoreUserEtaRequest.newBuilder()
                .addStoreUuids(storeUuid)
                .addStoreUuids(secondStoreUuid)
                .setLat(address.getLat().floatValue())
                .setLon(address.getLon().floatValue())
                .build();

        var response = clientEta.getStoreEta(request);
        List<String> storesUuids = Stream.of(storeUuid, secondStoreUuid).sorted().collect(Collectors.toList());
        List<String> storeUuidsFromResponse = Stream.of(response.getData(0).getStoreUuid(), response.getData(1).getStoreUuid()).sorted().collect(Collectors.toList());
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(response.getDataCount(), 2, softAssert);
        compareTwoObjects(response.getData(0).getEstimateSource(), Eta.EstimateSource.FALLBACK, softAssert);
        softAssert.assertTrue(response.getData(0).getEta() > 0, "Поле eta меньше или равно нулю");
        softAssert.assertTrue(response.getData(0).getSigma() > 0, "Поле sigma меньше или равно нулю");
        compareTwoObjects(storeUuidsFromResponse, storesUuids, softAssert);
        compareTwoObjects(response.getData(1).getEstimateSource(), Eta.EstimateSource.FALLBACK, softAssert);
        softAssert.assertTrue(response.getData(1).getEta() > 0, "Поле eta меньше или равно нулю");
        softAssert.assertTrue(response.getData(1).getSigma() > 0, "Поле sigma меньше или равно нулю");
        softAssert.assertAll();
    }

    @CaseId(9)
    @Story("User ETA")
    @Test(description = "Отправка запроса с координатами клиента, соответствующими координатам магазина",
            groups = "dispatch-eta-smoke")
    public void getEtaForStoreWithSameCoordinates() {
        Eta.StoreUserEtaRequest request = getStoreUserEtaRequest(storeUuid, address.getLat().floatValue(), address.getLon().floatValue());

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, storeUuid, 300, "Поле eta меньше 300 секунд");
    }

    @CaseId(5)
    @Story("User ETA")
    @Test(description = "Отправка запроса с невалидными store_uuid, lat, lon",
            groups = "dispatch-eta-smoke",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation failed")
    public void getEtaWithInvalidParams() {
        var request = getStoreUserEtaRequest("test", 124f, 312f);

        var response = clientEta.getStoreEta(request);
    }

    @CaseId(8)
    @Story("User ETA")
    @Test(description = "Отправка запроса с валидным, но несуществующим в БД store_uuid",
            groups = "dispatch-eta-smoke")
    public void getEtaForNonExistentStore() {
        Eta.StoreUserEtaRequest request = getStoreUserEtaRequest(UUID.randomUUID().toString(), address.getLat().floatValue(), address.getLon().floatValue());

        var response = clientEta.getStoreEta(request);
        compareTwoObjects(response.getDataCount(), 0);
    }

    @CaseId(39)
    @Story("User ETA")
    @Test(description = "Отправка валидного запроса в закрытый магазин",
            groups = "dispatch-eta-smoke")
    public void getEtaForClosedStore() {
        String openingDate = getDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(2)));
        String closingDate = getDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(1)));
        StoresEntity store = getStoreWithUpdatedSchedule(openingDate, closingDate, 0, "00:00:00");
        Eta.StoreUserEtaRequest request = getStoreUserEtaRequest(store.getUuid(), address.getLat().floatValue(), address.getLon().floatValue());

        var response = clientEta.getStoreEta(request);
        Assert.assertTrue(response.toString().isEmpty(), "Пришел не пустой ответ");
    }

    @CaseId(44)
    @Story("User ETA")
    @Test(description = "Отправка валидного запроса в пределах работы параметра OnDemandClosingDelta",
            groups = "dispatch-eta-smoke")
    public void getEtaForClosedStoreViaClosingDelta() {
        String openingDate = getDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().minusHours(1)));
        String closingDate = getDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        StoresEntity store = getStoreWithUpdatedSchedule(openingDate, closingDate, 30, "00:30:00");
        Eta.StoreUserEtaRequest request = getStoreUserEtaRequest(store.getUuid(), address.getLat().floatValue(), address.getLon().floatValue());

        var response = clientEta.getStoreEta(request);
        Assert.assertTrue(response.toString().isEmpty(), "Пришел не пустой ответ");
    }

    @CaseId(36)
    @Story("User ETA")
    @Test(description = "Проверка, что рассчитывается фоллбэк, в случае если ML возвращает ноль",
            groups = "dispatch-eta-smoke")
    public void getEtaForZeroML() {
        AddressV2 address = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_ON_DEMAND_SID);
        Eta.StoreUserEtaRequest request = getStoreUserEtaRequest(UUID.randomUUID().toString(), address.getLat().floatValue(), address.getLon().floatValue());

        var response = clientEta.getStoreEta(request);
        Assert.assertTrue(response.toString().isEmpty(), "Пришел не пустой ответ");
    }

    @CaseId(62)
    @Story("User ETA")
    @Test(description = "Проверка, что рассчитывается фоллбэк, в случае, если ML не возвращает ответ по таймауту",
            groups = "dispatch-eta-smoke")
    public void getEtaWithFallbackAfterMLTimeout() {
        String storeUuid = StoresDao.INSTANCE.findById(EnvironmentProperties.DEFAULT_ON_DEMAND_SID).get().getUuid();
        serviceParameters = ServiceParametersDao.INSTANCE.getServiceParameters();
        isMLTimeoutUpdated = checkMLTimeout(serviceParameters.getWaitMlTimeout());
        Eta.StoreUserEtaRequest request = getStoreUserEtaRequest(storeUuid, address.getLat().floatValue(), address.getLon().floatValue());

        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, storeUuid, 0, "Поле eta меньше или равно нулю");
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        if(isMLTimeoutUpdated) {
            ServiceParametersDao.INSTANCE.updateWaitMlTimeout(serviceParameters.getWaitMlTimeout());
        }
    }
}
