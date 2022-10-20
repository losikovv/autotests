package ru.instamart.test.api.on_demand;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.DispatchDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.NextDeliveryV2;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.jdbc.dao.orders_service.OrdersDao;
import ru.instamart.jdbc.dao.stf.SpreeShipmentsDao;
import ru.instamart.jdbc.entity.order_service.OrdersEntity;
import ru.instamart.kafka.enums.Pods;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.util.ThreadUtil;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertTrue;
import static ru.instamart.kafka.configs.KafkaConfigs.configFctOrderStf;
import static ru.instamart.kafka.enums.StatusOrder.AUTOMATIC_ROUTING;

@Epic("Dispatch")
public class KafkaDispatchTest extends RestBase {

    private long epochCurrentTime = Instant.now().getEpochSecond();
    private String placeUUID = "684609ad-6360-4bae-9556-03918c1e41c1";
    private String shipmentUuid;
    private NextDeliveryV2 nextDeliveryV2;
    private OrderV2 order;


    @BeforeMethod(alwaysRun = true)
    public void before() {
        SessionFactory.makeSession(SessionType.API_V2);
        UserData userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        order = apiV2.orderOnDemand(userData, EnvironmentProperties.DEFAULT_SID);
        shipmentUuid = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(order.getShipments().get(0).getNumber()).getUuid();

        Allure.step("orderUuid: " + order.getUuid());
        Allure.step("shipmentUuid: " + shipmentUuid);
        Allure.step("placeUUID: " + placeUUID);

    }

    @CaseIDs({
            @CaseId(99),
            @CaseId(109)
    })
    @Test(groups = {"dispatch-orderservice-smoke"},
            dataProvider = "shopperUniversal",
            dataProviderClass = DispatchDataProvider.class,
            description = "Полный флоу (order + dispatch+сервис кандидатов+сервис оценки маршрутов) 1 исполнитель (универсал)")
    public void fullFlowTest(UserData[] userData) {
        //авторизация универсалов
        Arrays.stream(userData).forEach(
                user -> {
                    shopperApp.authorisation(user);
                    shopperApp.createShopperShift(
                            5,
                            String.valueOf(LocalDateTime.now()),
                            String.valueOf(LocalDateTime.now().plus(1, ChronoUnit.DAYS)),
                            55.700289, 37.727431);
                }
        );

        //Step 2
        kafka.waitDataInKafkaTopicFtcOrder(configFctOrderStf(), order.getUuid());

        kafka.waitDataInKafkaTopicStatusOrderRequest(shipmentUuid, AUTOMATIC_ROUTING);

        //Step 3
        var orderEnrichmentList = kafka.waitDataInKafkaTopicConsumeOrderEnrichment(shipmentUuid, AUTOMATIC_ROUTING);

        //Step 4
        var orderEnrichment = orderEnrichmentList.get(0);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(orderEnrichment.getPlaceLocation().getLatitude(), "placeLocation.latitude is null");
        softAssert.assertNotNull(orderEnrichment.getPlaceLocation().getLongitude(), "placeLocation.longitude is null");
        softAssert.assertNotNull(orderEnrichment.getAssemblyTimeMin(), "assemblyTimeMin is null");
        softAssert.assertNotNull(orderEnrichment.getSettings().getAvgToPlaceMin(), "settings.avgToPlaceMin is null");
        softAssert.assertNotNull(orderEnrichment.getPlaceUuid(), "placeUuid is null");
        softAssert.assertNotNull(orderEnrichment.getSettings().getMaxOrderAssignRetryCount(), "MaxOrderAssignRetryCount is null");
        softAssert.assertNotNull(orderEnrichment.getSettings().getAvgParkingMinVehicle(), "AvgParkingMinVehicle is null");
        softAssert.assertNotNull(orderEnrichment.getSettings().getMaxCurrentOrderAssignQueue(), "MaxCurrentOrderAssignQueue is null");
        softAssert.assertNotNull(orderEnrichment.getSettings().getOrderWeightThresholdToAssignToVehicleGramms(), "OrderWeightThresholdToAssignToVehicleGramms is null");
        softAssert.assertNotNull(orderEnrichment.getSettings().getAverageSpeedForStraightDistanceToClientMin(), "AverageSpeedForStraightDistanceToClientMin is null");
        softAssert.assertNotNull(orderEnrichment.getSettings().getAdditionalFactorForStraightDistanceToClientMin(), "AdditionalFactorForStraightDistanceToClientMin is null");
        softAssert.assertNotNull(orderEnrichment.getSettings().getOrderTransferTimeFromAssemblyToDeliveryMin(), "OrderTransferTimeFromAssemblyToDeliveryMin is null");
        softAssert.assertNotNull(orderEnrichment.getSettings().getAvgToPlaceMinExternal(), "AvgToPlaceMinExternal is null");
        softAssert.assertNotNull(orderEnrichment.getSettings().getAvgToPlaceMin(), "AvgToPlaceMin is null");
        softAssert.assertNotNull(orderEnrichment.getSettings().getOfferServerTimeoutSec(), "OfferServerTimeoutSec is null");
        softAssert.assertNotNull(orderEnrichment.getSettings().getPlaceLocationCenter(), "PlaceLocationCenter is null");
        softAssert.assertNotNull(orderEnrichment.getSettings().getLastPositionExpire(), "LastPositionExpire is null");
        softAssert.assertAll();

        //Step5
        var logs1 = kubeLog.awaitLogsPod(Pods.DISPATCH, placeUUID, "grpc.service: candidates.Candidates\\nmethod: SelectCandidates");
        assertTrue(logs1.size() > 0, "Логов по отправке запроса в сервис кандидатов для получения списка доступных исполнителей нет");
        //ThreadUtil.simplyAwait(30);
        var logs2 = kubeLog.awaitLogsPod(Pods.DISPATCH, placeUUID, "grpc.service: estimator.RouteEstimator\\nmethod: GetRouteEstimation");
        assertTrue(logs2.size() > 0, "Логи отправки в сервис оценки маршрутов для доступных исполнителей нет");

        List<String> performersUUID = kubeLog.getPerformersUUID(logs2);
        assertTrue(performersUUID.size() > 0, "Сервис кандидатов вернул пустым исполнителей");

//        var logs3 = kubeLog.awaitLogsPod(Pods.DISPATCH, performersUUID.get(0), "\"grpc.service\":\"dispatch_shoppers.WorkAssignments\",\"method\":\"Assign\"");
//        assertTrue(logs3.size() > 0, "Логи отправки в сервис оценки маршрутов для доступных исполнителей нет");
    }


    @CaseId(46)
    @Test(groups = {"dispatch-orderservice-smoke"},
            description = "Получение данных on-demand заказа")
    public void getOnDemandOrder() {
        ThreadUtil.simplyAwait(10);
        OrdersEntity orderEntity = OrdersDao.INSTANCE.findByShipmentUuid(shipmentUuid);
        Allure.step("", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(orderEntity.getPlaceUuid(), placeUUID, "placeUUID не совпадает");
            softAssert.assertEquals(orderEntity.getWeight(), Integer.valueOf(2000), "weight не совпадает");
            softAssert.assertEquals(orderEntity.getClientLocation(), "(55.6512713,37.6753374)", "clientLocation не совпадает");
            softAssert.assertEquals(orderEntity.getType(), "ON_DEMAND", "type не совпадает");
            softAssert.assertEquals(orderEntity.getDeliveryPromiseUpperDttm(), nextDeliveryV2.getEndsAt(), "delivery_promise_upper_dttm не совпадает");
            softAssert.assertEquals(orderEntity.getDeliveryPromiseLowerDttm(), nextDeliveryV2.getStartsAt(), "delivery_promise_upper_dttm не совпадает");
            softAssert.assertEquals(orderEntity.getOrderStatus(), "ROUTING", "order_status не совпадает");
            softAssert.assertNotNull(orderEntity.getCreatedAt(), "create_at is NULL");
        });
    }
}
