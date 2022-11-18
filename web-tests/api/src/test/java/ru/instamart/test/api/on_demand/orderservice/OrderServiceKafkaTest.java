package ru.instamart.test.api.on_demand.orderservice;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;

import operations_order_service.OperationsOrderService.EventOrder.ShipmentStatus;
import operations_order_service.OperationsOrderService.EventOrder.RequestOrderType;
import operations_order_service.OperationsOrderService.EventOrder.ShippingMethodKind;
import order.OrderChanged;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.jdbc.dao.orders_service.OrdersDao;
import ru.instamart.jdbc.dao.orders_service.PlacesDao;
import ru.instamart.jdbc.dao.orders_service.RetailersDao;
import ru.instamart.jdbc.entity.order_service.OrdersEntity;
import ru.instamart.jdbc.entity.order_service.PlacesEntity;
import ru.instamart.jdbc.entity.order_service.RetailersEntity;
import ru.instamart.kraken.util.ThreadUtil;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;
import java.util.UUID;

import static protobuf.retail_onboarding_retailer_data.RetailOnboardingRetailerData.Retailer.RetailerVertical.*;
import static ru.instamart.api.helper.OrderServiceHelper.*;

@Epic("Dispatch")
public class OrderServiceKafkaTest extends RestBase {
    private final String placeUUID = "684609ad-6360-4bae-9556-03918c1e41c1";
    private final String multiOrderPlaceUUID = "0c479c59-f1a4-4214-9690-f0ade4568652";
    private final String retailerUuid = "1f7b042f-650f-46ef-9f4d-10aacf71a532";
    double latitude = 55.6512713;
    double longitude = 55.6512713;
    int orderPreparationSlaMinutes = RandomUtils.nextInt(10, 30);


    @CaseId(207)
    @Test(description = "Получение информации о статусе джобов и заказа для нового планового заказа",
            groups = "kafka-instamart-regress")
    public void receiveNewPlannedOrderEvent() {
        String orderUuid = UUID.randomUUID().toString();
        String shipmentUuid = UUID.randomUUID().toString();
        String orderNumber = "Н123" + "" + (RandomUtils.nextInt(11111111, 99999999));
        publishOrderEvent(latitude, longitude, getDeliveryPromiseUpperDttmEndsAt(), getDeliveryPromiseUpperDttmStartsAt(), 1, orderUuid, 2000, RequestOrderType.PLANNED, ShipmentStatus.READY, placeUUID, shipmentUuid, orderNumber, 3599, false, ShippingMethodKind.BY_COURIER);

        ThreadUtil.simplyAwait(1);
        List<OrderChanged.EventOrderChanged> orderChangedMessage = kafka.waitDataInKafkaTopicOrderStatusChanged(orderUuid);

        Allure.step("Проверка полученного сообщения о плановом заказе в кафке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(orderChangedMessage.get(0).getOrderStatus(), OrderChanged.EventOrderChanged.OrderStatus.POSTPONED, "Вернулся не статус POSTPONED");
            softAssert.assertEquals(orderChangedMessage.get(0).getShipmentType(), OrderChanged.EventOrderChanged.ShipmentType.PLANNED, "У заказа изменился тип на ON_DEMAND");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobsCount(), 2, "У заказа нет одной из джоб, при этом заказ не самовывоз");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobs(1).getJobStatus(), OrderChanged.EventOrderChanged.Job.JobStatus.NEW, "У нового заказа статус джобы не NEW");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobs(0).getJobStatus(), OrderChanged.EventOrderChanged.Job.JobStatus.NEW, "У нового заказа статус джобы не NEW");
            softAssert.assertAll();
        });
    }

    @Test(description = "Получение информации о статусе джобов и заказа для нового ондеманд заказа",
            groups = "kafka-instamart-regress")
    public void receiveNewOnDemandOrderEvent() {
        String orderUuid = UUID.randomUUID().toString();
        String shipmentUuid = UUID.randomUUID().toString();
        String orderNumber = "Н123" + "" + (RandomUtils.nextInt(11111111, 99999999));
        publishOrderEvent(latitude, longitude, getDeliveryPromiseUpperDttmStartsAt(), getDeliveryPromiseUpperDttmEndsAt(), 1, orderUuid, 2000, RequestOrderType.ON_DEMAND, ShipmentStatus.READY, placeUUID, shipmentUuid, orderNumber, 3599, false, ShippingMethodKind.BY_COURIER);


        ThreadUtil.simplyAwait(1);
        List<OrderChanged.EventOrderChanged> orderChangedMessage = kafka.waitDataInKafkaTopicOrderStatusChanged(orderUuid);

        Allure.step("Проверка полученного сообщения об ондеманд заказе в кафке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(orderChangedMessage.get(0).getOrderStatus(), OrderChanged.EventOrderChanged.OrderStatus.POSTPONED, "Вернулся не статус POSTPONED");
            softAssert.assertEquals(orderChangedMessage.get(0).getShipmentType(), OrderChanged.EventOrderChanged.ShipmentType.ON_DEMAND, "У заказа изменился тип на PLANNED");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobsCount(), 2, "У заказа нет одной из джоб, при этом заказ не самовывоз");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobs(1).getJobStatus(), OrderChanged.EventOrderChanged.Job.JobStatus.NEW, "У нового заказа статус джобы не NEW");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobs(0).getJobStatus(), OrderChanged.EventOrderChanged.Job.JobStatus.NEW, "У нового заказа статус джобы не NEW");
            softAssert.assertAll();
        });
    }

    @Test(description = "Получение информации о статусе джобы и заказа для нового заказа самовывоза",
            groups = "kafka-instamart-regress")
    public void receiveNewPickupOrderEvent() {
        String orderUuid = UUID.randomUUID().toString();
        String shipmentUuid = UUID.randomUUID().toString();
        String orderNumber = "Н123" + "" + (RandomUtils.nextInt(11111111, 99999999));
        publishOrderEvent(latitude, longitude, getDeliveryPromiseUpperDttmStartsAt(), getDeliveryPromiseUpperDttmEndsAt(), 1, orderUuid, 2000, RequestOrderType.PLANNED, ShipmentStatus.READY, placeUUID, shipmentUuid, orderNumber, 3599, false, ShippingMethodKind.PICKUP);


        ThreadUtil.simplyAwait(1);
        List<OrderChanged.EventOrderChanged> orderChangedMessage = kafka.waitDataInKafkaTopicOrderStatusChanged(orderUuid);

        Allure.step("Проверка полученного сообщения о самовывозе в кафке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(orderChangedMessage.get(0).getOrderStatus(), OrderChanged.EventOrderChanged.OrderStatus.POSTPONED, "Вернулся не статус POSTPONED");
            softAssert.assertEquals(orderChangedMessage.get(0).getShipmentType(), OrderChanged.EventOrderChanged.ShipmentType.PLANNED, "У заказа изменился тип на ON_DEMAND");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobsCount(), 1, "У заказа не 1 джоба, при этом заказ - самовывоз");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobs(0).getJobStatus(), OrderChanged.EventOrderChanged.Job.JobStatus.NEW, "У нового заказа статус джобы не NEW");
            softAssert.assertAll();
        });
    }
    @Test(description = "Получение и сохранение двух заказов из мультизаказа",
            groups = "kafka-instamart-regress")
    public void receiveMultiOrder() {
        String orderUuid = UUID.randomUUID().toString();
        String firstShipmentUuid = UUID.randomUUID().toString();
        String secondShipmentUuid = UUID.randomUUID().toString();
        String firstOrderNumber = "Н123" + "" + (RandomUtils.nextInt(11111111, 99999999));
        String secondOrderNumber = "Н123" + "" + (RandomUtils.nextInt(11111111, 99999999));
        publishOrderEvent(latitude, longitude, getDeliveryPromiseUpperDttmStartsAt(), getDeliveryPromiseUpperDttmEndsAt(), 1, orderUuid, 2000, RequestOrderType.ON_DEMAND, ShipmentStatus.READY, placeUUID, firstShipmentUuid, firstOrderNumber, 3599, false, ShippingMethodKind.BY_COURIER);
        publishOrderEvent(latitude, longitude, getDeliveryPromiseUpperDttmStartsAt(), getDeliveryPromiseUpperDttmEndsAt(), 1, orderUuid, 2000, RequestOrderType.ON_DEMAND, ShipmentStatus.READY, multiOrderPlaceUUID, secondShipmentUuid, secondOrderNumber, 3599, false, ShippingMethodKind.BY_COURIER);

        ThreadUtil.simplyAwait(1);
        OrdersEntity firstOrderEntity = OrdersDao.INSTANCE.findByShipmentUuid(firstShipmentUuid);
        OrdersEntity secondOrderEntity = OrdersDao.INSTANCE.findByShipmentUuid(secondShipmentUuid);

        Allure.step("Проверка полученных сообщений о мультизаказах в кафке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(firstOrderEntity.getShipmentUuid(), firstShipmentUuid, "Первый заказ из мультизаказа не сохранился в БД");
            softAssert.assertEquals(secondOrderEntity.getShipmentUuid(), secondShipmentUuid, "Второй заказ из мультизаказа не сохранился в БД");
            softAssert.assertAll();
        });
    }
    @Test(description = "При получении события отмены заказа статусы джобов и заказа переходят в CANCELED",
            groups = "kafka-instamart-regress")
    public void receiveNewCancelledOrderEvent() {
        String orderUuid = UUID.randomUUID().toString();
        String shipmentUuid = UUID.randomUUID().toString();
        String orderNumber = "Н123" + "" + (RandomUtils.nextInt(11111111, 99999999));
        publishOrderEvent(latitude, longitude, getDeliveryPromiseUpperDttmEndsAt(), getDeliveryPromiseUpperDttmStartsAt(), 1, orderUuid, 2000, RequestOrderType.PLANNED, ShipmentStatus.READY, placeUUID, shipmentUuid, orderNumber, 3599, false, ShippingMethodKind.BY_COURIER);


        ThreadUtil.simplyAwait(1);
        publishOrderEvent(latitude, longitude, getDeliveryPromiseUpperDttmEndsAt(), getDeliveryPromiseUpperDttmStartsAt(), 1, orderUuid, 2000, RequestOrderType.PLANNED, ShipmentStatus.CANCELED, placeUUID, shipmentUuid, orderNumber, 3599, false, ShippingMethodKind.BY_COURIER);
        ThreadUtil.simplyAwait(1);
        List<OrderChanged.EventOrderChanged> orderChangedMessage = kafka.waitDataInKafkaTopicOrderStatusChangedByStatus(orderUuid, OrderChanged.EventOrderChanged.OrderStatus.CANCELED);

        Allure.step("Проверка полученного сообщения об отмене заказа в кафке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(orderChangedMessage.get(0).getOrderStatus(), OrderChanged.EventOrderChanged.OrderStatus.CANCELED, "У отменённого заказа статус не CANCELLED");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobs(1).getJobStatus(), OrderChanged.EventOrderChanged.Job.JobStatus.CANCELED, "У отменённого заказа статус джобы не CANCELLED");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobs(0).getJobStatus(), OrderChanged.EventOrderChanged.Job.JobStatus.CANCELED, "У отменённого заказа статус джобы не CANCELLED");
            softAssert.assertAll();
        });
    }

    @Test(description = "Обновление информации о вертикали ретейлера (вертикаль = магазин) в БД при получении сообщения в кафку",
            groups = "kafka-instamart-regress")
    public void receiveRetailerGroceryVerticalEvent() {
        publishRetailerChangedEvent(retailerUuid, GROCERY);
        ThreadUtil.simplyAwait(5);
        RetailersEntity retailersEntity = RetailersDao.INSTANCE.findByRetailerUuid(retailerUuid);

        Allure.step("Проверка сохранённых значений о ретейлере в базе", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(retailersEntity.getUuid(), retailerUuid, "В БД сохранился не отправленный UUID ретейлера");
            softAssert.assertEquals(retailersEntity.getVertical(), GROCERY.toString(), "У ретейлера не обновилась вертикаль на GROCERY");
            softAssert.assertAll();
        });
    }

    @Test(description = "Обновление информации о вертикали ретейлера (вертикаль = аптека) в БД при получении сообщения в кафку",
            groups = "kafka-instamart-regress")
    public void receiveRetailerPharmacyVerticalEvent() {
        publishRetailerChangedEvent(retailerUuid, PHARMACY);
        ThreadUtil.simplyAwait(5);
        RetailersEntity retailersEntity = RetailersDao.INSTANCE.findByRetailerUuid(retailerUuid);

        Allure.step("Проверка сохранённых значений о ретейлере в базе", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(retailersEntity.getUuid(), retailerUuid, "В БД сохранился не отправленный UUID ретейлера");
            softAssert.assertEquals(retailersEntity.getVertical(), "PHARM", "У ретейлера не обновилась вертикаль на PHARMACY");
            softAssert.assertAll();
        });
    }

    @Test(description = "Обновление информации о вертикали ретейлера (вертикаль = ресторан) в БД при получении сообщения в кафку",
            groups = "kafka-instamart-regress")
    public void receiveRetailerRestaurantVerticalEvent() {
        publishRetailerChangedEvent(retailerUuid, RESTAURANT);
        ThreadUtil.simplyAwait(5);
        RetailersEntity retailersEntity = RetailersDao.INSTANCE.findByRetailerUuid(retailerUuid);

        Allure.step("Проверка сохранённых значений о ретейлере в базе", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(retailersEntity.getUuid(), retailerUuid, "В БД сохранился другой UUID ретейлера");
            softAssert.assertEquals(retailersEntity.getVertical(), RESTAURANT.toString(), "У ретейлера не обновилась вертикаль на RESTAURANT");
            softAssert.assertAll();
        });
    }

    @Test(description = "Обновление информации о времени на приготовления для точки в БД при получении сообщения в кафку",
            groups = "kafka-instamart-regress")
    public void receiveOrderPreparationValue() {
        publishStoreChangedEvent(placeUUID, orderPreparationSlaMinutes, "delivery_by_sbermarket", retailerUuid);
        ThreadUtil.simplyAwait(5);
        PlacesEntity placesEntity = PlacesDao.INSTANCE.findByPlaceUuid(placeUUID);

        Allure.step("Проверка сохранённых значений для магазазина в базе", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(placesEntity.getRetailerUuid(), retailerUuid, "В БД сохранился другой UUID ретейлера");
            softAssert.assertEquals(placesEntity.getSlaMin(), orderPreparationSlaMinutes, "У ретейлера не сохранилось значение SLA В БД");
            softAssert.assertAll();
        });
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        publishRetailerChangedEvent(retailerUuid, GROCERY);
        publishStoreChangedEvent(placeUUID, 15, "shopper", retailerUuid);
    }

}
