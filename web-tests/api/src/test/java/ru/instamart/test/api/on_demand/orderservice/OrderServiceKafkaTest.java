package ru.instamart.test.api.on_demand.orderservice;

import candidates.StoreChangedOuterClass.AssemblyType;
import candidates.StoreChangedOuterClass.PlaceSettings.DeliveryType;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import operations_order_service.OperationsOrderService.EventOrder.RequestOrderType;
import operations_order_service.OperationsOrderService.EventOrder.ShipmentStatus;
import operations_order_service.OperationsOrderService.EventOrder.ShippingMethodKind;
import order.OrderChanged.EventOrderChanged.Job.JobStatus;
import order.OrderChanged.EventOrderChanged.OrderStatus;
import order.OrderChanged.EventOrderChanged.ShipmentType;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.jdbc.dao.orders_service.OrdersDao;
import ru.instamart.jdbc.dao.orders_service.PlacesDao;
import ru.instamart.jdbc.dao.orders_service.RetailersDao;
import ru.instamart.jdbc.dao.orders_service.SettingsDao;
import ru.instamart.jdbc.entity.order_service.RetailersEntity;
import ru.instamart.kraken.util.ThreadUtil;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.UUID;

import static protobuf.retail_onboarding_retailer_data.RetailOnboardingRetailerData.Retailer.RetailerVertical.*;
import static ru.instamart.api.helper.OrderServiceHelper.*;

@Epic("On Demand")
@Feature("DISPATCH")
public class OrderServiceKafkaTest extends RestBase {
    private final String placeUUID = "684609ad-6360-4bae-9556-03918c1e41c1";
    private final String multiOrderPlaceUUID = "0c479c59-f1a4-4214-9690-f0ade4568652";
    private final String retailerUuid = "1f7b042f-650f-46ef-9f4d-10aacf71a532";
    private final double latitude = 55.6512713;
    private final double longitude = 55.6512713;
    private final int orderPreparationSlaMinutes = RandomUtils.nextInt(10, 30);
    int RteFactorCityCongestionMinutes = 3;
    double RteDeliverySlotMultiplier = 0.5;

    @BeforeClass(alwaysRun = true,
            description = "Меняем настройки")
    public void preconditions() {
        SettingsDao.INSTANCE.updateSettings(placeUUID, RteFactorCityCongestionMinutes,RteDeliverySlotMultiplier);
    }

    @CaseId(207)
    @Test(description = "Получение информации о статусе джобов и заказа для нового планового заказа",
            groups = "dispatch-orderservice-smoke")
    public void receiveNewPlannedOrderEvent() {
        final var orderUuid = UUID.randomUUID().toString();
        final var shipmentUuid = UUID.randomUUID().toString();
        final var orderNumber = "Н123" + "" + (RandomUtils.nextInt(11111111, 99999999));
        publishOrderEvent(latitude, longitude, getDeliveryPromiseUpperDttmEndsAt(), getDeliveryPromiseUpperDttmStartsAt(),
                1, orderUuid, 2000, RequestOrderType.PLANNED,
                ShipmentStatus.READY, placeUUID, shipmentUuid, orderNumber, 3599, false, ShippingMethodKind.BY_COURIER);

        ThreadUtil.simplyAwait(1);
        final var orderChangedMessage = kafka.waitDataInKafkaTopicOrderStatusChanged(orderUuid);

        Allure.step("Проверка полученного сообщения о плановом заказе в кафке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(orderChangedMessage.get(0).getOrderStatus(), OrderStatus.POSTPONED, "Вернулся не статус POSTPONED");
            softAssert.assertEquals(orderChangedMessage.get(0).getShipmentType(), ShipmentType.PLANNED, "У заказа изменился тип на ON_DEMAND");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobsCount(), 2, "У заказа нет одной из джоб, при этом заказ не самовывоз");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobs(1).getJobStatus(), JobStatus.NEW, "У нового заказа статус джобы не NEW");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobs(0).getJobStatus(), JobStatus.NEW, "У нового заказа статус джобы не NEW");
            softAssert.assertAll();
        });
    }

    @CaseId(209)
    @Test(description = "Получение информации о статусе джобов и заказа для нового ондеманд заказа",
            groups = "dispatch-orderservice-smoke")
    public void receiveNewOnDemandOrderEvent() {
        final var orderUuid = UUID.randomUUID().toString();
        final var shipmentUuid = UUID.randomUUID().toString();
        final var orderNumber = "Н123" + "" + (RandomUtils.nextInt(11111111, 99999999));
        publishOrderEvent(latitude, longitude, getDeliveryPromiseUpperDttmStartsAt(), getDeliveryPromiseUpperDttmEndsAt(), 1, orderUuid, 2000, RequestOrderType.ON_DEMAND, ShipmentStatus.READY, placeUUID, shipmentUuid, orderNumber, 3599, false, ShippingMethodKind.BY_COURIER);

        ThreadUtil.simplyAwait(1);
        final var orderChangedMessage = kafka.waitDataInKafkaTopicOrderStatusChanged(orderUuid);

        Allure.step("Проверка полученного сообщения об ондеманд заказе в кафке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(orderChangedMessage.get(0).getOrderStatus(), OrderStatus.POSTPONED, "Вернулся не статус POSTPONED");
            softAssert.assertEquals(orderChangedMessage.get(0).getShipmentType(), ShipmentType.ON_DEMAND, "У заказа изменился тип на PLANNED");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobsCount(), 2, "У заказа нет одной из джоб, при этом заказ не самовывоз");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobs(1).getJobStatus(), JobStatus.NEW, "У нового заказа статус джобы не NEW");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobs(0).getJobStatus(), JobStatus.NEW, "У нового заказа статус джобы не NEW");
            softAssert.assertAll();
        });
    }

    @CaseId(211)
    @Test(description = "Получение информации о статусе джобы и заказа для нового заказа самовывоза",
            groups = "dispatch-orderservice-smoke")
    public void receiveNewPickupOrderEvent() {
        final var orderUuid = UUID.randomUUID().toString();
        final var shipmentUuid = UUID.randomUUID().toString();
        final var orderNumber = "Н123" + "" + (RandomUtils.nextInt(11111111, 99999999));
        publishOrderEvent(latitude, longitude, getDeliveryPromiseUpperDttmStartsAt(), getDeliveryPromiseUpperDttmEndsAt(), 1,
                orderUuid, 2000, RequestOrderType.PLANNED, ShipmentStatus.READY, placeUUID, shipmentUuid,
                orderNumber, 3599, false, ShippingMethodKind.PICKUP);

        ThreadUtil.simplyAwait(1);
        final var orderChangedMessage = kafka.waitDataInKafkaTopicOrderStatusChanged(orderUuid);

        Allure.step("Проверка полученного сообщения о самовывозе в кафке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(orderChangedMessage.get(0).getOrderStatus(), OrderStatus.POSTPONED, "Вернулся не статус POSTPONED");
            softAssert.assertEquals(orderChangedMessage.get(0).getShipmentType(), ShipmentType.PLANNED, "У заказа изменился тип на ON_DEMAND");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobsCount(), 1, "У заказа не 1 джоба, при этом заказ - самовывоз");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobs(0).getJobStatus(), JobStatus.NEW, "У нового заказа статус джобы не NEW");
            softAssert.assertAll();
        });
    }

    @CaseId(212)
    @Test(description = "Получение и сохранение двух заказов из мультизаказа",
            groups = "dispatch-orderservice-smoke")
    public void receiveMultiOrder() {
        final var orderUuid = UUID.randomUUID().toString();
        final var firstShipmentUuid = UUID.randomUUID().toString();
        final var secondShipmentUuid = UUID.randomUUID().toString();
        final var firstOrderNumber = "Н123" + "" + (RandomUtils.nextInt(11111111, 99999999));
        final var secondOrderNumber = "Н123" + "" + (RandomUtils.nextInt(11111111, 99999999));
        publishOrderEvent(latitude, longitude, getDeliveryPromiseUpperDttmStartsAt(), getDeliveryPromiseUpperDttmEndsAt(), 1,
                orderUuid, 2000, RequestOrderType.ON_DEMAND, ShipmentStatus.READY, placeUUID, firstShipmentUuid,
                firstOrderNumber, 3599, false, ShippingMethodKind.BY_COURIER);
        publishOrderEvent(latitude, longitude, getDeliveryPromiseUpperDttmStartsAt(), getDeliveryPromiseUpperDttmEndsAt(), 1,
                orderUuid, 2000, RequestOrderType.ON_DEMAND, ShipmentStatus.READY, multiOrderPlaceUUID, secondShipmentUuid,
                secondOrderNumber, 3599, false, ShippingMethodKind.BY_COURIER);

        ThreadUtil.simplyAwait(1);
        final var firstOrderEntity = OrdersDao.INSTANCE.findByShipmentUuid(firstShipmentUuid);
        final var secondOrderEntity = OrdersDao.INSTANCE.findByShipmentUuid(secondShipmentUuid);

        Allure.step("Проверка полученных сообщений о мультизаказах в кафке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(firstOrderEntity.getShipmentUuid(), firstShipmentUuid, "Первый заказ из мультизаказа не сохранился в БД");
            softAssert.assertEquals(secondOrderEntity.getShipmentUuid(), secondShipmentUuid, "Второй заказ из мультизаказа не сохранился в БД");
            softAssert.assertAll();
        });
    }

    @CaseId(195)
    @Test(description = "При получении события отмены заказа статусы джобов и заказа переходят в CANCELED",
            groups = "dispatch-orderservice-smoke")
    public void receiveNewCancelledOrderEvent() {
        final var orderUuid = UUID.randomUUID().toString();
        final var shipmentUuid = UUID.randomUUID().toString();
        final var orderNumber = "Н123" + "" + (RandomUtils.nextInt(11111111, 99999999));
        publishOrderEvent(latitude, longitude, getDeliveryPromiseUpperDttmEndsAt(), getDeliveryPromiseUpperDttmStartsAt(), 1,
                orderUuid, 2000, RequestOrderType.PLANNED, ShipmentStatus.READY, placeUUID, shipmentUuid,
                orderNumber, 3599, false, ShippingMethodKind.BY_COURIER);

        ThreadUtil.simplyAwait(1);
        publishOrderEvent(latitude, longitude, getDeliveryPromiseUpperDttmEndsAt(), getDeliveryPromiseUpperDttmStartsAt(), 1,
                orderUuid, 2000, RequestOrderType.PLANNED, ShipmentStatus.CANCELED, placeUUID, shipmentUuid,
                orderNumber, 3599, false, ShippingMethodKind.BY_COURIER);
        ThreadUtil.simplyAwait(1);
        final var orderChangedMessage = kafka.waitDataInKafkaTopicOrderStatusChangedByStatus(orderUuid, OrderStatus.CANCELED);

        Allure.step("Проверка полученного сообщения об отмене заказа в кафке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(orderChangedMessage.get(0).getOrderStatus(), OrderStatus.CANCELED, "У отменённого заказа статус не CANCELLED");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobs(1).getJobStatus(), JobStatus.CANCELED, "У отменённого заказа статус джобы не CANCELLED");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobs(0).getJobStatus(), JobStatus.CANCELED, "У отменённого заказа статус джобы не CANCELLED");
            softAssert.assertAll();
        });
    }

    @CaseId(252)
    @Test(description = "Обновление информации о вертикали ретейлера (вертикаль = магазин) в БД при получении сообщения в кафку",
            groups = "dispatch-orderservice-smoke")
    public void receiveRetailerGroceryVerticalEvent() {
        publishRetailerChangedEvent(retailerUuid, GROCERY);
        ThreadUtil.simplyAwait(5);
        final var retailersEntity = RetailersDao.INSTANCE.findByRetailerUuid(retailerUuid);

        Allure.step("Проверка сохранённых значений о ретейлере в базе", () -> {
            final var softAssert = new SoftAssert();
            softAssert.assertEquals(retailersEntity.getUuid(), retailerUuid, "В БД сохранился не отправленный UUID ретейлера");
            softAssert.assertEquals(retailersEntity.getVertical(), GROCERY.toString(), "У ретейлера не обновилась вертикаль на GROCERY");
            softAssert.assertAll();
        });
    }

    @CaseId(251)
    @Test(description = "Обновление информации о вертикали ретейлера (вертикаль = аптека) в БД при получении сообщения в кафку",
            groups = "dispatch-orderservice-smoke")
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

    @CaseId(41)
    @Test(description = "Обновление информации о вертикали ретейлера (вертикаль = ресторан) в БД при получении сообщения в кафку",
            groups = "dispatch-orderservice-smoke")
    public void receiveRetailerRestaurantVerticalEvent() {
        publishRetailerChangedEvent(retailerUuid, RESTAURANT);
        ThreadUtil.simplyAwait(5);
        final var retailersEntity = RetailersDao.INSTANCE.findByRetailerUuid(retailerUuid);

        Allure.step("Проверка сохранённых значений о ретейлере в базе", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(retailersEntity.getUuid(), retailerUuid, "В БД сохранился другой UUID ретейлера");
            softAssert.assertEquals(retailersEntity.getVertical(), RESTAURANT.toString(), "У ретейлера не обновилась вертикаль на RESTAURANT");
            softAssert.assertAll();
        });
    }

    @CaseId(42)
    @Test(description = "Обновление информации о времени на приготовления для точки в БД при получении сообщения в кафку",
            groups = "dispatch-orderservice-smoke")
    public void receiveOrderPreparationValue() {
        publishStoreChangedEvent(placeUUID, orderPreparationSlaMinutes, "delivery_by_sbermarket", retailerUuid);
        ThreadUtil.simplyAwait(5);
        final var placesEntity = PlacesDao.INSTANCE.findByPlaceUuid(placeUUID);

        Allure.step("Проверка сохранённых значений для магазазина в базе", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(placesEntity.getRetailerUuid(), retailerUuid, "В БД сохранился другой UUID ретейлера");
            softAssert.assertEquals(placesEntity.getSlaMin(), orderPreparationSlaMinutes, "У ретейлера не сохранилось значение SLA В БД");
            softAssert.assertAll();
        });
    }
    @CaseId(63)
    @Test(description = "При получении сообщения в топик store-changed не обнуляем коэффициенты по RTE",
            groups = "dispatch-orderservice-smoke")
    public void checkRteSettingsAfterStoreChangedMessage() {
        publishStoreChangedEvent(placeUUID, orderPreparationSlaMinutes, "delivery_by_sbermarket", retailerUuid);
        ThreadUtil.simplyAwait(5);
        publishShopperStoreChangedEvent(64, true, 120, "dispatch", AssemblyType.SM, DeliveryType.SM, placeUUID);

        final var placesEntity = PlacesDao.INSTANCE.findByPlaceUuid(placeUUID);
        final var settingsEntity = SettingsDao.INSTANCE.findByPlaceUUID(placeUUID);

        Allure.step("Проверка сохранённых значений для магазазина в базе", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(placesEntity.getRetailerUuid(), retailerUuid, "В БД обнулился UUID ретейлера");
            softAssert.assertEquals(placesEntity.getSlaMin(), orderPreparationSlaMinutes, "У магазина обнулилось значение SLA В БД");
            softAssert.assertEquals(settingsEntity.getRteFactorCityCongestionMinutes(), RteFactorCityCongestionMinutes, "У магазина обнулился коэффициент FactorCityCongestion В БД");
            softAssert.assertEquals(settingsEntity.getRteDeliverySlotMultiplier(), RteDeliverySlotMultiplier, "У магазина обнулился коэффициент DeliverySlotMultiplier В БД");
            softAssert.assertAll();
        });
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        publishRetailerChangedEvent(retailerUuid, GROCERY);
        publishStoreChangedEvent(placeUUID, 15, "shopper", retailerUuid);
    }

}
