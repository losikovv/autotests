package ru.instamart.test.api.on_demand.orderservice;

import com.google.type.Money;
import io.grpc.StatusRuntimeException;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.model.v2.NextDeliveryV2;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import operations_order_service.OperationsOrderService;
import order.OrderChanged;
import order_redispatch.OrderRedispatch;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import protobuf.order_data.OrderOuterClass;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.DispatchDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v1.StoresV1Request;
import ru.instamart.api.response.shopper.admin.RouteRoutingSettings;
import ru.instamart.jdbc.dao.orders_service.*;
import ru.instamart.jdbc.entity.order_service.*;
import ru.instamart.kafka.enums.Pods;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.util.TimeUtil;
import ru.instamart.kraken.util.ThreadUtil;
import ru.sbermarket.qase.annotation.CaseId;
import com.google.protobuf.Timestamp;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.helper.OrderServiceHelper.*;

@Epic("On Demand")
@Feature("DISPATCH")
public class RTETest extends RestBase {
    private final String placeUUID = "6bc4dc40-37a0-45fe-ac7f-d4185c29da63";
    String assemblyTaskType = "EXTERNAL";
    Integer sla = 5;
    Integer factorCityCongestionMinute = 3;
    double deliverySlotMultiplier = 0.5;
    double latitude = 55.63076;
    double longitude = 37.62138;

    String retailerUuidOld = "";
    String assemblyTaskTypeOld = "";
    Integer slaOld = 0;
    Integer factorCityOld = 0;
    double slotMultiplierOld = 0;

    @AfterClass(alwaysRun = true,
            description = "Возвращяем настройки")
    public void returnSettings() {
        PlacesDao.INSTANCE.updatePlaces(retailerUuidOld,assemblyTaskTypeOld, placeUUID,slaOld);
        SettingsDao.INSTANCE.updateSettings(placeUUID,factorCityOld,slotMultiplierOld);
    }

    @BeforeClass(alwaysRun = true,
            description = "Меняем настройки")
    public void preconditions() {
        //Настройка магазина в тип "Рестора", установка SLA
        final var retailersEntity = RetailersDao.INSTANCE.findByVertical();
        final var placesEntity = PlacesDao.INSTANCE.findByPlaceUuid(placeUUID);
        retailerUuidOld = placesEntity.getRetailerUuid();
        assemblyTaskTypeOld = placesEntity.getAssemblyTaskType();
        slaOld = placesEntity.getSlaMin();
        PlacesDao.INSTANCE.updatePlaces(retailersEntity.getUuid(),assemblyTaskType, placeUUID,sla);

        //Нстройка альфы, дельты
        SettingsEntity settingsEntity = SettingsDao.INSTANCE.findByPlaceUUID(placeUUID);
        factorCityOld = settingsEntity.getRteFactorCityCongestionMinutes();
        slotMultiplierOld = settingsEntity.getRteDeliverySlotMultiplier();
        SettingsDao.INSTANCE.updateSettings(placeUUID,factorCityCongestionMinute,deliverySlotMultiplier);


    }

    @CaseId(160)
    @Test(description = "Расчет времени для экспресс доставки с ресторана",
            groups = "dispatch-orderservice-smoke")
    public void RTEForOndemandOrder() {

        String shipmentUuid = UUID.randomUUID().toString();
        String orderNumber = "Н123" + "" + (RandomUtils.nextInt(11111111, 99999999));
        publishOrderEvent(orderNumber, longitude, latitude, getDeliveryPromiseUpperDttmEndsAt(), getDeliveryPromiseId(), getDeliveryPromiseUpperDttmStartsAt(), getDeliveryPromiseId(), orderNumber, OrderOuterClass.Order.Shipment.ShipmentType.ON_DEMAND, shipmentUuid, ShippingMethodV2.BY_COURIER.getMethod(), OrderOuterClass.Order.Shipment.ShipmentState.READY, "on_demand");

        ThreadUtil.simplyAwait(1);
        List<OrderChanged.EventOrderChanged> orderChangedMessage = kafka.waitDataInKafkaTopicOrderStatusChangedShipmentUuid(shipmentUuid);

        long timeStart = orderChangedMessage.get(0).getJobs(1).getPlanStartedAt().getSeconds();
        long timeEnd = (int)timeStart+sla*60+factorCityCongestionMinute*60;
        assertEquals(timeEnd, orderChangedMessage.get(0).getJobs(1).getPlanEndedAt().getSeconds(), "Рассчитанове время планового окончания не верно");
    }

    @CaseId(160)
    @Test(description = "Расчет времени для плановой доставки с ресторана",
            dataProvider = "shopperUniversal",
            dataProviderClass = DispatchDataProvider.class,
            groups = "dispatch-orderservice-smoke")
    public void RTEForPlannedOrder(UserData[] userData) {
        Arrays.stream(userData).forEach(
                user -> {
                    shopperApp.authorisation(user);
                    shopperApp.createShopperShift(
                            5,
                            String.valueOf(LocalDateTime.now()),
                            String.valueOf(LocalDateTime.now().plus(1, ChronoUnit.HOURS)),
                            55.63071, 37.62449);
                }
        );

        String shipmentUuid = UUID.randomUUID().toString();
        String orderNumber = "Н123" + "" + (RandomUtils.nextInt(11111111, 99999999));
        publishOrderEvent(orderNumber, longitude, latitude, getDeliveryPromiseUpperDttmEndsAt(), getDeliveryPromiseId(), getDeliveryPromiseUpperDttmStartsAt(), getDeliveryPromiseId(), orderNumber, OrderOuterClass.Order.Shipment.ShipmentType.ON_DEMAND, shipmentUuid, ShippingMethodV2.BY_COURIER.getMethod(), OrderOuterClass.Order.Shipment.ShipmentState.READY, "on_demand");

        ThreadUtil.simplyAwait(1);
        List<OrderChanged.EventOrderChanged> orderChangedMessage = kafka.waitDataInKafkaTopicOrderStatusChangedShipmentUuid(shipmentUuid);

        OrdersEntity ordersEntity = OrdersDao.INSTANCE.findByShipmentUuid(shipmentUuid);
        long lower = TimeUtil.getTimestampFromStringDtdb(ordersEntity.getDeliveryPromiseLowerDttm()).getSeconds();
        long upper = TimeUtil.getTimestampFromStringDtdb(ordersEntity.getDeliveryPromiseUpperDttm()).getSeconds();

        var logs = kubeLog.awaitLogsPod(Pods.ORDER_SERVICE,shipmentUuid,"created jobs for order");
        String trace = logs.get(0).split("trace_id\":\"")[1].split("\"")[0];
        logs = kubeLog.awaitLogsPod(Pods.ROUTE_ESTIMATOR,trace,"PerformerEvaluation");//TODO перевести в объект

        String logsGet = logs.get(0);

        long receivingForDelivery = TimeUtil.getTimestampFromStringzdtkf(logsGet.split("RECEIVING_FOR_DELIVERY")[1].split("EndTime")[1].split("\\\\\"")[2]).getSeconds() - TimeUtil.getTimestampFromStringzdtkf(logsGet.split("RECEIVING_FOR_DELIVERY")[1].split("StartTime")[1].split("\\\\\"")[2]).getSeconds();
        long delivery = TimeUtil.getTimestampFromStringzdtkf(logsGet.split("\"DELIVERY")[1].split("EndTime")[1].split("\\\\\"")[2]).getSeconds() - TimeUtil.getTimestampFromStringzdtkf(logsGet.split("\"DELIVERY")[1].split("StartTime")[1].split("\\\\\"")[2]).getSeconds();
        long passToClient = TimeUtil.getTimestampFromStringzdtkf(logsGet.split("PASS_TO_CLIENT")[1].split("EndTime")[1].split("\\\\\"")[2]).getSeconds() - TimeUtil.getTimestampFromStringzdtkf(logsGet.split("PASS_TO_CLIENT")[1].split("StartTime")[1].split("\\\\\"")[2]).getSeconds();

        long timeEnd = Double.valueOf(lower+(upper-lower)*deliverySlotMultiplier-delivery-receivingForDelivery-passToClient+10800).longValue();
        assertEquals(timeEnd, orderChangedMessage.get(0).getJobs(1).getPlanEndedAt().getSeconds(), "Рассчитанове время планового окончания не верно");
    }

    @CaseId(160)
    @Test(description = "Расчет максимального времени для плановой доставки с ресторана",
            dataProvider = "shopperUniversal",
            dataProviderClass = DispatchDataProvider.class,
            groups = "dispatch-orderservice-smoke")
    public void maxRTEForPlannedOrOndemandOrder(UserData[] userData) {
        Arrays.stream(userData).forEach(
                user -> {
                    shopperApp.authorisation(user);
                    shopperApp.createShopperShift(
                            5,
                            String.valueOf(LocalDateTime.now()),
                            String.valueOf(LocalDateTime.now().plus(1, ChronoUnit.HOURS)),
                            55.63071, 37.62449);
                }
        );


        String shipmentUuid = UUID.randomUUID().toString();
        String orderNumber = "Н123" + "" + (RandomUtils.nextInt(11111111, 99999999));

        publishOrderEvent(orderNumber, longitude, latitude, getDeliveryPromiseUpperDttmEndsAt(), getDeliveryPromiseId(), getDeliveryPromiseUpperDttmStartsAt(), getDeliveryPromiseId(), orderNumber, OrderOuterClass.Order.Shipment.ShipmentType.ON_DEMAND, shipmentUuid, ShippingMethodV2.BY_COURIER.getMethod(), OrderOuterClass.Order.Shipment.ShipmentState.READY, "on_demand");

        ThreadUtil.simplyAwait(1);
        List<OrderChanged.EventOrderChanged> orderChangedMessage = kafka.waitDataInKafkaTopicOrderStatusChangedShipmentUuid(shipmentUuid);

        OrdersEntity ordersEntity = OrdersDao.INSTANCE.findByShipmentUuid(shipmentUuid);
        long lower = TimeUtil.getTimestampFromStringDtdb(ordersEntity.getDeliveryPromiseLowerDttm()).getSeconds();
        long upper = TimeUtil.getTimestampFromStringDtdb(ordersEntity.getDeliveryPromiseUpperDttm()).getSeconds();

        var logs = kubeLog.awaitLogsPod(Pods.ORDER_SERVICE,shipmentUuid,"created jobs for order");
        String trace = logs.get(0).split("trace_id\":\"")[1].split("\"")[0];
        logs = kubeLog.awaitLogsPod(Pods.ROUTE_ESTIMATOR,trace,"PerformerEvaluation");//TODO перевести в объект

        String logsGet = logs.get(0);

        long receivingForDelivery = TimeUtil.getTimestampFromStringzdtkf(logsGet.split("RECEIVING_FOR_DELIVERY")[1].split("EndTime")[1].split("\\\\\"")[2]).getSeconds() - TimeUtil.getTimestampFromStringzdtkf(logsGet.split("RECEIVING_FOR_DELIVERY")[1].split("StartTime")[1].split("\\\\\"")[2]).getSeconds();
        long delivery = TimeUtil.getTimestampFromStringzdtkf(logsGet.split("\"DELIVERY")[1].split("EndTime")[1].split("\\\\\"")[2]).getSeconds() - TimeUtil.getTimestampFromStringzdtkf(logsGet.split("\"DELIVERY")[1].split("StartTime")[1].split("\\\\\"")[2]).getSeconds();
        long passToClient = TimeUtil.getTimestampFromStringzdtkf(logsGet.split("PASS_TO_CLIENT")[1].split("EndTime")[1].split("\\\\\"")[2]).getSeconds() - TimeUtil.getTimestampFromStringzdtkf(logsGet.split("PASS_TO_CLIENT")[1].split("StartTime")[1].split("\\\\\"")[2]).getSeconds();

        long timeStart = orderChangedMessage.get(0).getJobs(1).getPlanStartedAt().getSeconds();
        long timeEndOnDemand = (int)timeStart+sla*60+factorCityCongestionMinute*60;

        long timeEndPlanned = Double.valueOf(lower+(upper-lower)*deliverySlotMultiplier-delivery-receivingForDelivery-passToClient+10800).longValue();

        if (timeEndOnDemand>timeEndPlanned){
              assertEquals(timeEndOnDemand, orderChangedMessage.get(0).getJobs(1).getPlanEndedAt().getSeconds(), "Рассчитанове время планового окончания не верно");

        } else {
                assertEquals(timeEndPlanned, orderChangedMessage.get(0).getJobs(1).getPlanEndedAt().getSeconds(), "Рассчитанове время планового окончания не верно");
            }
        }
}
