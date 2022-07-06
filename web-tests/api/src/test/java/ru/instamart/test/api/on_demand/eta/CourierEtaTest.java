package ru.instamart.test.api.on_demand.eta;

import eta.Eta;
import eta.PredEtaGrpc;
import io.grpc.StatusRuntimeException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.stf.SpreeShipmentsDao;
import ru.instamart.jdbc.dao.stf.StoresDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.StartPointsTenants;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static eta.Eta.CourierEtaEstimateSource.APP_FALLBACK;
import static eta.Eta.CourierEtaEstimateSource.RE_FALLBACK;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;

@Epic("On Demand")
@Feature("ETA")
public class CourierEtaTest extends RestBase {

    private PredEtaGrpc.PredEtaBlockingStub clientEta;
    private AddressV2 address;
    private UserData userData;
    private OrderV2 order;
    private String storeUuid;
    private String shipmentUuid;


    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientEta = PredEtaGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_ETA));
        address = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_ON_DEMAND_SID);
        SessionFactory.makeSession(SessionType.API_V2);
        userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        apiV2.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_ON_DEMAND_SID);
        order = apiV2.getOpenOrder();
        storeUuid = StoresDao.INSTANCE.findById(EnvironmentProperties.DEFAULT_ON_DEMAND_SID).get().getUuid();
        shipmentUuid = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(order.getShipments().get(0).getNumber()).getUuid();
        admin.auth();
        shopperApp.authorisation(UserManager.getKrakenUniversal());
    }

    @CaseIDs(value = {@CaseId(105), @CaseId(123)})
    @Story("Courier ETA")
    @Test(enabled = false,
            description = "Отправка запроса с валидными данными",
            groups = "dispatch-eta-smoke")
    public void getCourierEta() {
        shopperApp.sendCurrentLocator(StartPointsTenants.ETA.getLat(), StartPointsTenants.ETA.getLon(), 81.68728);

        var request = Eta.CourierEtaRequest.newBuilder()
                .setCourierUuid(UserManager.getKrakenUniversal().getUuid())
                .setStoreUuid(storeUuid)
                .setShipmentUuid(shipmentUuid)
                .setClientPoint(Eta.CourierEtaRequest.ClientPoint.newBuilder()
                        .setLat(56.7575624f)
                        .setLon(36.6569041f)
                        .build())
                .build();


        var response = clientEta.getCourierEta(request);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(response.getEta() > 0, "ETA меньше или равно нулю");
        compareTwoObjects(response.getEstimateSource(), APP_FALLBACK, softAssert);
        softAssert.assertAll();
    }

    @CaseId(121)
    @Story("Courier ETA")
    @Test(description = "Отправка запроса к courier_uuid, у которого отсутствуют координаты",
            groups = "dispatch-eta-smoke",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "NOT_FOUND: cannot get courier location: courier location not found")
    public void getCourierEtaForCourierWithoutCoordinates() {
        var request = Eta.CourierEtaRequest.newBuilder()
                .setCourierUuid(UserManager.getKrakenUniversal2().getUuid())
                .setStoreUuid(storeUuid)
                .setShipmentUuid(shipmentUuid)
                .setClientPoint(Eta.CourierEtaRequest.ClientPoint.newBuilder()
                        .setLat(56.7575624f)
                        .setLon(36.6569041f)
                        .build())
                .build();


        var response = clientEta.getCourierEta(request);
    }

    @CaseId(216)
    @Story("Courier ETA")
    @Test(enabled = false,
            description = "Получение установленного минимального времени расчета",
            groups = "dispatch-eta-smoke")
    public void getMinCourierEta() {
        shopperApp.sendCurrentLocator(StartPointsTenants.ETA.getLat(), StartPointsTenants.ETA.getLon(), 81.68728);

        var request = Eta.CourierEtaRequest.newBuilder()
                .setCourierUuid(UserManager.getKrakenUniversal().getUuid())
                .setStoreUuid(storeUuid)
                .setShipmentUuid(shipmentUuid)
                .setClientPoint(Eta.CourierEtaRequest.ClientPoint.newBuilder()
                        .setLat(StartPointsTenants.ETA.getLat().floatValue())
                        .setLon(StartPointsTenants.ETA.getLon().floatValue())
                        .build())
                .build();


        var response = clientEta.getCourierEta(request);
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(response.getEta(), 60, softAssert);
        compareTwoObjects(response.getEstimateSource(), RE_FALLBACK, softAssert);
        softAssert.assertAll();
    }
}
