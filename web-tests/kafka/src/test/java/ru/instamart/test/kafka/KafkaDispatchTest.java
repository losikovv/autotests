package ru.instamart.test.kafka;

import com.google.protobuf.Timestamp;
import enums.Enums;
import io.qameta.allure.Epic;
import order.Order;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.kafka.common.KafkaBase;
import ru.instamart.kafka.emum.Pods;
import ru.instamart.kafka.emum.StatusOrder;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertTrue;
import static ru.instamart.kafka.configs.KafkaConfigs.configCmdOrderEnrichment;
import static ru.instamart.kafka.configs.KafkaConfigs.configFctOrder;

@Epic("Dispatch")
public class KafkaDispatchTest extends KafkaBase {
    private final ApiHelper helper = new ApiHelper();
    private long epochCurrentTime = Instant.now().getEpochSecond();
    private long epochPlusHour = Instant.now().plusSeconds(3600).getEpochSecond();
    private String orderUuid, shipmentUuid;

    @BeforeMethod(alwaysRun = true)
    public void before() {
        orderUuid = UUID.randomUUID().toString();
        shipmentUuid = UUID.randomUUID().toString();

        //Step 1
        Order.EventOrder orderEvent = Order.EventOrder.newBuilder()
                .setClientLocation(Enums.Location.newBuilder()
                        .setLatitude(55.6512713)
                        .setLongitude(37.6753374).build())
                .setCreateTime(Timestamp.newBuilder()
                        .setSeconds(epochCurrentTime).build())
                .setDeliveryPromiseUpperDttmEndsAt(Timestamp.newBuilder()
                        .setSeconds(epochPlusHour).build())
                .setDeliveryPromiseUpperDttmStartsAt(Timestamp.newBuilder()
                        .setSeconds(epochCurrentTime).build())
                .setNumberOfPositionsInOrder(1)
                .setOrderUuid(orderUuid)
                .setOrderWeightGramms(5000)
                .setShipmentType(Order.EventOrder.RequestOrderType.ON_DEMAND)
                .setShipmentUuid(shipmentUuid)
                .build();
        kafka.publish(configFctOrder(), orderEvent.toByteArray());
    }

    @Test(groups = {"kafka-instamart-regress"},
            description = "Полный флоу (order + dispatch+сервис кандидатов+сервис оценки маршрутов) 1 исполнитель (универсал)")
    public void fulFlowTest() {
        kafka.waitDataInKafkaTopicFtcOrder(configFctOrder(), orderUuid);
        //Step 2
        //TODO: Логов нет. Раскомментировать после пересборки пода
        //kubeLog.awaitLogsPod(Pods.ORDER_SERVICE, orderUuid, StatusOrder.POSTPONED);

        //Step 3
        var orderEnrichmentList = kafka.waitDataInKafkaTopicStatusOrderRequest(configCmdOrderEnrichment(),
                shipmentUuid, StatusOrder.AUTOMATIC_ROUTING
        );

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
        softAssert.assertNotNull(orderEnrichment.getSettings().getSearchRadiusTransportPedestrian(), "SearchRadiusTransportPedestrian is null");
        softAssert.assertNotNull(orderEnrichment.getSettings().getSearchRadiusTransportAuto(), "SearchRadiusTransportAuto is null");
        softAssert.assertNotNull(orderEnrichment.getSettings().getSearchRadiusTransportBike(), "SearchRadiusTransportBike is null");
        softAssert.assertNotNull(orderEnrichment.getSettings().getLastPositionExpire(), "LastPositionExpire is null");
        softAssert.assertAll();

        //Step5
        var logs1 = kubeLog.awaitLogsPod(Pods.DISPATCH, orderEnrichment.getPlaceUuid(), "\"grpc.service\":\"candidates.Candidates\",\"method\":\"SelectCandidates\"");
        assertTrue(logs1.size()>0, "Логов по отправке запроса в сервис кандидатов для получения списка доступных исполнителей нет");
        var logs2 = kubeLog.awaitLogsPod(Pods.DISPATCH, orderEnrichment.getPlaceUuid(), "\"grpc.service\":\"estimator.RouteEstimator\",\"method\":\"GetRouteEstimation\"");
        assertTrue(logs2.size()>0, "Логи отправки в сервис оценки маршрутов для доступных исполнителей нет");

        List<String> performersUUID = kubeLog.getPerformersUUID(logs2);
        assertTrue(performersUUID.size()>0, "Сервис кандидатов вернул пустым исполнителей");

        var logs3 = kubeLog.awaitLogsPod(Pods.DISPATCH, orderEnrichment.getShipmentUuid(), "\"grpc.service\":\"dispatch_shoppers.WorkAssignments\",\"method\":\"Assign\"");
        assertTrue(logs3.size()>0, "Логи отправки в сервис оценки маршрутов для доступных исполнителей нет");
    }
}
