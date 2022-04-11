package ru.instamart.test.api.dispatch.workflow;

import com.google.protobuf.Timestamp;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import operations_order_service.OperationsOrderService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v2.DeliveryWindowV2;
import ru.instamart.api.dataprovider.DispatchDataProvider;
import ru.instamart.kafka.emum.Pods;
import ru.instamart.kafka.emum.StatusOrder;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertTrue;
import static ru.instamart.kafka.configs.KafkaConfigs.configFctOrderStf;
import static ru.instamart.kraken.util.TimeUtil.getTimestampFromString;

@Epic("Dispatch")
public class KafkaDispatchTest extends RestBase {

    private long epochCurrentTime = Instant.now().getEpochSecond();
    private String placeUUID = "684609ad-6360-4bae-9556-03918c1e41c1";
    private String orderUuid, shipmentUuid;

    @BeforeMethod(alwaysRun = true)
    public void before() {
        DeliveryWindowV2 availableDeliveryWindowOnDemand = apiV2.getAvailableDeliveryWindowOnDemand(UserManager.getStf6ApiUser(), 3);
        orderUuid = UUID.randomUUID().toString();
        shipmentUuid = UUID.randomUUID().toString();

        Allure.step("orderUuid: " + orderUuid);
        Allure.step("shipmentUuid: " + shipmentUuid);
        Allure.step("placeUUID: " + placeUUID);
        Timestamp deliveryPromiseUpperDttmStartsAt = getTimestampFromString(availableDeliveryWindowOnDemand.getStartsAt());
        Timestamp deliveryPromiseUpperDttmEndsAt = getTimestampFromString(availableDeliveryWindowOnDemand.getEndsAt());

        //Step 1
        OperationsOrderService.EventOrder orderEvent = OperationsOrderService.EventOrder.newBuilder()
                .setClientLocation(OperationsOrderService.EventOrder.ClientLocation.newBuilder()
                        .setLatitude(55.6512713)
                        .setLongitude(37.6753374).build())
                .setCreateTime(Timestamp.newBuilder()
                        .setSeconds(epochCurrentTime).build())
                .setDeliveryPromiseUpperDttmEndsAt(deliveryPromiseUpperDttmEndsAt)
                .setDeliveryPromiseUpperDttmStartsAt(deliveryPromiseUpperDttmStartsAt)
                .setNumberOfPositionsInOrder(1)
                .setOrderUuid(orderUuid)
                .setOrderWeightGramms(2000)
                .setShipmentType(OperationsOrderService.EventOrder.RequestOrderType.ON_DEMAND)
                .setPlaceUuid(placeUUID)
                .setShipmentUuid(shipmentUuid)
                .build();
        //Отправка данных в топик yc.operations-order-service.fct.order.0
        kafka.publish(configFctOrderStf(), orderEvent.toByteArray());
    }

    @CaseIDs({
            @CaseId(99),
            @CaseId(109)
    })
    @Test(groups = {"kafka-instamart-regress"},
            dataProvider = "shopperUniversal",
            dataProviderClass = DispatchDataProvider.class,
            description = "Полный флоу (order + dispatch+сервис кандидатов+сервис оценки маршрутов) 1 исполнитель (универсал)")
    public void fullFlowTest(UserData[] userData) {
        //авторизация универсалов
        Arrays.stream(userData).forEach(
                user->{
                    shopperApp.authorisation(user);
                    shopperApp.createShopperShift(
                            5,
                            String.valueOf(LocalDateTime.now()),
                            String.valueOf(LocalDateTime.now().plus(1, ChronoUnit.DAYS)),
                            55.700289, 37.727431);
                    shopperApp.sendCurrentLocator(55.700289, 37.727431, 0D);
                }
        );

        //Step 2
        kafka.waitDataInKafkaTopicFtcOrder(configFctOrderStf(), orderUuid);
        kafka.waitDataInKafkaTopicStatusOrderRequest(shipmentUuid, StatusOrder.AUTOMATIC_ROUTING);

        //Step 3
        var orderEnrichmentList = kafka.waitDataInKafkaTopicConsumeOrderEnrichment(shipmentUuid, StatusOrder.AUTOMATIC_ROUTING);

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
        var logs1 = kubeLog.awaitLogsPod(Pods.DISPATCH, placeUUID, "\"grpc.service\":\"candidates.Candidates\",\"method\":\"SelectCandidates\"");
        assertTrue(logs1.size() > 0, "Логов по отправке запроса в сервис кандидатов для получения списка доступных исполнителей нет");
        //ThreadUtil.simplyAwait(30);
        var logs2 = kubeLog.awaitLogsPod(Pods.DISPATCH, placeUUID, "\"grpc.service\":\"estimator.RouteEstimator\",\"method\":\"GetRouteEstimation\"");
        assertTrue(logs2.size() > 0, "Логи отправки в сервис оценки маршрутов для доступных исполнителей нет");

        List<String> performersUUID = kubeLog.getPerformersUUID(logs2);
        assertTrue(performersUUID.size() > 0, "Сервис кандидатов вернул пустым исполнителей");

//        var logs3 = kubeLog.awaitLogsPod(Pods.DISPATCH, performersUUID.get(0), "\"grpc.service\":\"dispatch_shoppers.WorkAssignments\",\"method\":\"Assign\"");
//        assertTrue(logs3.size() > 0, "Логи отправки в сервис оценки маршрутов для доступных исполнителей нет");
    }
}
