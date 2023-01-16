package ru.instamart.test.api.on_demand.orderservice;

import candidates.StoreChangedOuterClass.AssemblyType;
import candidates.StoreChangedOuterClass.PlaceSettings.DeliveryType;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import order.OrderChanged.EventOrderChanged.Job.JobStatus;
import order.OrderChanged.EventOrderChanged.OrderStatus;
import order.OrderChanged.EventOrderChanged.ShipmentType;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import protobuf.order_data.OrderOuterClass;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.jdbc.dao.orders_service.publicScheme.*;
import ru.instamart.jdbc.entity.order_service.publicScheme.RetailersEntity;
import ru.instamart.kraken.util.ThreadUtil;

import java.util.UUID;

import static protobuf.retail_onboarding_retailer_data.RetailOnboardingRetailerData.Retailer.RetailerVertical.*;
import static ru.instamart.api.helper.OrderServiceHelper.*;

@Epic("On Demand")
@Feature("DISPATCH")
public class OrderServiceKafkaTest extends RestBase {
    private final String placeUUID = "684609ad-6360-4bae-9556-03918c1e41c1";
    private final String multiOrderPlaceUUID = "0c479c59-f1a4-4214-9690-f0ade4568652";
    private final String retailerUuid = "1f7b042f-650f-46ef-9f4d-10aacf71a532";
    private final String randomPlaceUUID = UUID.randomUUID().toString();
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

    @TmsLink("207")
    @Test(description = "Получение информации о статусе джобов и заказа для нового планового заказа",
            groups = "dispatch-orderservice-smoke")
    public void receiveNewPlannedOrderEvent() {
        final var orderUuid = UUID.randomUUID().toString();
        final var shipmentUuid = UUID.randomUUID().toString();
        final var orderNumber = "Н123" + "" + (RandomUtils.nextInt(11111111, 99999999));
        publishOrderEvent(orderNumber, longitude, latitude, getDeliveryPromiseUpperDttmEndsAt(), getDeliveryPromiseId(), getDeliveryPromiseUpperDttmStartsAt(),
                getDeliveryPromiseId(), orderNumber, OrderOuterClass.Order.Shipment.ShipmentType.PLANNED, shipmentUuid, ShippingMethodV2.BY_COURIER.getMethod(), OrderOuterClass.Order.Shipment.ShipmentState.READY, "courier");

        ThreadUtil.simplyAwait(1);
        final var orderChangedMessage = kafka.waitDataInKafkaTopicOrderStatusChangedShipmentUuid(shipmentUuid);

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

    @TmsLink("209")
    @Test(description = "Получение информации о статусе джобов и заказа для нового ондеманд заказа",
            groups = "dispatch-orderservice-smoke")
    public void receiveNewOnDemandOrderEvent() {
        final var orderUuid = UUID.randomUUID().toString();
        final var shipmentUuid = UUID.randomUUID().toString();
        final var orderNumber = "Н123" + "" + (RandomUtils.nextInt(11111111, 99999999));
        publishOrderEvent(orderNumber, longitude, latitude, getDeliveryPromiseUpperDttmEndsAt(), getDeliveryPromiseId(), getDeliveryPromiseUpperDttmStartsAt(),
                getDeliveryPromiseId(), orderNumber, OrderOuterClass.Order.Shipment.ShipmentType.ON_DEMAND, shipmentUuid, ShippingMethodV2.BY_COURIER.getMethod(), OrderOuterClass.Order.Shipment.ShipmentState.READY, "on_demand");

        ThreadUtil.simplyAwait(1);
        final var orderChangedMessage = kafka.waitDataInKafkaTopicOrderStatusChangedShipmentUuid(shipmentUuid);

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

    @TmsLink("211")
    @Test(description = "Получение информации о статусе джобы и заказа для нового заказа самовывоза",
            groups = "dispatch-orderservice-smoke")
    public void receiveNewPickupOrderEvent() {
        final var orderUuid = UUID.randomUUID().toString();
        final var shipmentUuid = UUID.randomUUID().toString();
        final var orderNumber = "Н123" + "" + (RandomUtils.nextInt(11111111, 99999999));
        publishOrderEvent(orderNumber, longitude, latitude, getDeliveryPromiseUpperDttmEndsAt(), getDeliveryPromiseId(), getDeliveryPromiseUpperDttmStartsAt(),
                getDeliveryPromiseId(), orderNumber, OrderOuterClass.Order.Shipment.ShipmentType.PLANNED, shipmentUuid, ShippingMethodV2.PICKUP.getMethod(), OrderOuterClass.Order.Shipment.ShipmentState.READY, "pickup");

        ThreadUtil.simplyAwait(1);
        final var orderChangedMessage = kafka.waitDataInKafkaTopicOrderStatusChangedShipmentUuid(shipmentUuid);

        Allure.step("Проверка полученного сообщения о самовывозе в кафке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(orderChangedMessage.get(0).getOrderStatus(), OrderStatus.POSTPONED, "Вернулся не статус POSTPONED");
            softAssert.assertEquals(orderChangedMessage.get(0).getShipmentType(), ShipmentType.PLANNED, "У заказа изменился тип на ON_DEMAND");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobsCount(), 1, "У заказа не 1 джоба, при этом заказ - самовывоз");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobs(0).getJobStatus(), JobStatus.NEW, "У нового заказа статус джобы не NEW");
            softAssert.assertAll();
        });
    }

    @TmsLink("212")
    @Test(description = "Получение и сохранение двух заказов из мультизаказа",
            groups = "dispatch-orderservice-smoke")
    public void receiveMultiOrder() {
        final var orderUuid = UUID.randomUUID().toString();
        final var firstShipmentUuid = UUID.randomUUID().toString();
        final var secondShipmentUuid = UUID.randomUUID().toString();
        final var firstOrderNumber = "Н123" + "" + (RandomUtils.nextInt(11111111, 99999999));
        final var secondOrderNumber = "Н123" + "" + (RandomUtils.nextInt(11111111, 99999999));
        publishOrderEvent(firstOrderNumber, longitude, latitude, getDeliveryPromiseUpperDttmEndsAt(), getDeliveryPromiseId(), getDeliveryPromiseUpperDttmStartsAt(),
                getDeliveryPromiseId(), firstOrderNumber, OrderOuterClass.Order.Shipment.ShipmentType.ON_DEMAND, firstShipmentUuid, ShippingMethodV2.BY_COURIER.getMethod(), OrderOuterClass.Order.Shipment.ShipmentState.READY, "on_demand");
        publishOrderEvent(secondOrderNumber, longitude, latitude, getDeliveryPromiseUpperDttmEndsAt(), getDeliveryPromiseId(), getDeliveryPromiseUpperDttmStartsAt(),
                getDeliveryPromiseId(), secondOrderNumber, OrderOuterClass.Order.Shipment.ShipmentType.ON_DEMAND, secondShipmentUuid, ShippingMethodV2.BY_COURIER.getMethod(), OrderOuterClass.Order.Shipment.ShipmentState.READY, "on_demand");;

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

    @TmsLink("195")
    @Test(description = "При получении события отмены заказа статусы джобов и заказа переходят в CANCELED",
            groups = "dispatch-orderservice-smoke")
    public void receiveNewCancelledOrderEvent() {
        final var orderUuid = UUID.randomUUID().toString();
        final var shipmentUuid = UUID.randomUUID().toString();
        final var orderNumber = "Н123" + "" + (RandomUtils.nextInt(11111111, 99999999));
        publishOrderEvent(orderNumber, longitude, latitude, getDeliveryPromiseUpperDttmEndsAt(), getDeliveryPromiseId(), getDeliveryPromiseUpperDttmStartsAt(),
                getDeliveryPromiseId(), orderNumber, OrderOuterClass.Order.Shipment.ShipmentType.PLANNED, shipmentUuid, ShippingMethodV2.BY_COURIER.getMethod(), OrderOuterClass.Order.Shipment.ShipmentState.READY, "courier");

        ThreadUtil.simplyAwait(1);
        publishOrderEvent(orderNumber, longitude, latitude, getDeliveryPromiseUpperDttmEndsAt(), getDeliveryPromiseId(), getDeliveryPromiseUpperDttmStartsAt(),
                getDeliveryPromiseId(), orderNumber, OrderOuterClass.Order.Shipment.ShipmentType.PLANNED, shipmentUuid, ShippingMethodV2.BY_COURIER.getMethod(), OrderOuterClass.Order.Shipment.ShipmentState.CANCELED, "courier");;
        ThreadUtil.simplyAwait(1);
        final var orderChangedMessage = kafka.waitDataInKafkaTopicOrderStatusChangedByStatusShipment(shipmentUuid, OrderStatus.CANCELED);

        Allure.step("Проверка полученного сообщения об отмене заказа в кафке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(orderChangedMessage.get(0).getOrderStatus(), OrderStatus.CANCELED, "У отменённого заказа статус не CANCELLED");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobs(1).getJobStatus(), JobStatus.CANCELED, "У отменённого заказа статус джобы не CANCELLED");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobs(0).getJobStatus(), JobStatus.CANCELED, "У отменённого заказа статус джобы не CANCELLED");
            softAssert.assertAll();
        });
    }

    @TmsLink("252")
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

    @TmsLink("251")
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

    @TmsLink("41")
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

    @TmsLink("42")
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
    @TmsLink("63")
    @Test(description = "При получении сообщения в топик store-changed не обнуляем коэффициенты по RTE",
            groups = "dispatch-orderservice-smoke")
    public void checkRteSettingsAfterStoreChangedMessage() {
        publishStoreChangedEvent(placeUUID, orderPreparationSlaMinutes, "delivery_by_sbermarket", retailerUuid);
        ThreadUtil.simplyAwait(5);
        publishShopperStoreChangedEvent(64, true, 120, "dispatch", AssemblyType.SM, DeliveryType.SM, placeUUID);

        final var placesEntity = PlacesDao.INSTANCE.findByPlaceUuid(placeUUID);
        final var settingsEntity = SettingsDao.INSTANCE.findByPlaceUUID(placeUUID);

        Allure.step("Проверка сохранённых значений для магазина в базе", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(placesEntity.getRetailerUuid(), retailerUuid, "В БД обнулился UUID ретейлера");
            softAssert.assertEquals(placesEntity.getSlaMin(), orderPreparationSlaMinutes, "У магазина обнулилось значение SLA В БД");
            softAssert.assertEquals(settingsEntity.getRteFactorCityCongestionMinutes(), RteFactorCityCongestionMinutes, "У магазина обнулился коэффициент FactorCityCongestion В БД");
            softAssert.assertEquals(settingsEntity.getRteDeliverySlotMultiplier(), RteDeliverySlotMultiplier, "У магазина обнулился коэффициент DeliverySlotMultiplier В БД");
            softAssert.assertAll();
        });
    }

    @TmsLink("254")
    @Test(description = "При создании магазина через топик yc.retail-onboarding.fct.stores active = false",
            groups = "dispatch-orderservice-smoke")
    public void checkNewStoreStatusAfterRetailerStoreChangedMessage() {
        publishStoreChangedEvent(randomPlaceUUID, orderPreparationSlaMinutes, "delivery_by_sbermarket", retailerUuid);
        ThreadUtil.simplyAwait(5);

        final var placesEntity = PlacesDao.INSTANCE.findByPlaceUuid(randomPlaceUUID);

        Allure.step("Проверка сохранённых значений для магазина в базе", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(placesEntity.getRetailerUuid(), retailerUuid, "В БД не сохранился UUID ретейлера");
            softAssert.assertEquals(placesEntity.isActive(), false, "После сообщения в топик yc.retail-onboarding.fct.stores.6 магазин создался активным");
            softAssert.assertAll();
        });
    }

    @TmsLink("255")
    @Test(description = "При создании магазина через топик yc.retail-onboarding.fct.stores и после получения информации о магазине из топика store-changed active = true",
            groups = "dispatch-orderservice-smoke")
    public void checkNewStoreStatusAfterRetailerAndShopperStoreChangedMessage() {
        publishStoreChangedEvent(randomPlaceUUID, orderPreparationSlaMinutes, "delivery_by_sbermarket", retailerUuid);
        publishShopperStoreChangedEvent(64, true, 120, "dispatch", AssemblyType.SM, DeliveryType.SM, randomPlaceUUID);
        ThreadUtil.simplyAwait(5);

        final var placesEntity = PlacesDao.INSTANCE.findByPlaceUuid(randomPlaceUUID);

        Allure.step("Проверка сохранённых значений для магазина в базе", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(placesEntity.getRetailerUuid(), retailerUuid, "В БД не сохранился UUID ретейлера");
            softAssert.assertEquals(placesEntity.isActive(), true, "После сообщения в топик store-changed магазин не стал активным");
            softAssert.assertAll();
        });
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        publishRetailerChangedEvent(retailerUuid, GROCERY);
        publishStoreChangedEvent(placeUUID, 15, "shopper", retailerUuid);
        PlacesDao.INSTANCE.deleteStore(randomPlaceUUID);
        PlaceSettingsDao.INSTANCE.deleteStoreFromPlaceSettings(randomPlaceUUID);
        SettingsDao.INSTANCE.deleteStoreFromSettings(randomPlaceUUID);
    }

}
