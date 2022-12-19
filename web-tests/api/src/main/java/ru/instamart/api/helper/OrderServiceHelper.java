package ru.instamart.api.helper;

import candidates.StoreChangedOuterClass;
import com.google.protobuf.Timestamp;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import operations_order_service.OperationsOrderService;
import protobuf.retail_onboarding_retailer_data.RetailOnboardingRetailerData;
import protobuf.retail_onboarding_store_data.RetailOnboardingStoreData;
import ru.instamart.api.model.v2.NextDeliveryV2;
import ru.instamart.api.request.v1.StoresV1Request;
import ru.instamart.api.response.v2.NextDeliveriesV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;

import static ru.instamart.kafka.configs.KafkaConfigs.*;
import static ru.instamart.kraken.util.TimeUtil.getTimestamp;
import static ru.instamart.kraken.util.TimeUtil.getTimestampFromString;


public class OrderServiceHelper {
    private static final KafkaHelper kafka = new KafkaHelper();

    @Step("Создаем событие нового заказа для магазина")
    public static OperationsOrderService.EventOrder getOrderEvent(final double latitude, final double longitude, final Timestamp deliveryPromiseUpperDttmStartsAt,
                                                                  final Timestamp deliveryPromiseUpperDttmEndsAt, final int numberOfPositionsInOrder,
                                                                  final String orderUuid, final int orderWeightGramms, final OperationsOrderService.EventOrder.RequestOrderType requestOrderType,
                                                                  final OperationsOrderService.EventOrder.ShipmentStatus shipmentStatus, final String placeUUID,
                                                                  final String shipmentUuid, final String orderNumber, final float itemsTotalAmount,
                                                                  final boolean isNew, final OperationsOrderService.EventOrder.ShippingMethodKind shippingMethodKind) {
        return OperationsOrderService.EventOrder.newBuilder()
                .setClientLocation(OperationsOrderService.EventOrder.ClientLocation.newBuilder()
                        .setLatitude(latitude)
                        .setLongitude(longitude)
                        .build())
                .setCreateTime(getTimestamp())
                .setDeliveryPromiseUpperDttmEndsAt(deliveryPromiseUpperDttmStartsAt)
                .setDeliveryPromiseUpperDttmStartsAt(deliveryPromiseUpperDttmEndsAt)
                .setNumberOfPositionsInOrder(numberOfPositionsInOrder)
                .setOrderUuid(orderUuid)
                .setOrderWeightGramms(orderWeightGramms)
                .setShipmentType(requestOrderType)
                .setShipmentStatus(shipmentStatus)
                .setPlaceUuid(placeUUID)
                .setShipmentUuid(shipmentUuid)
                .setNumber(orderNumber)
                .setItemsTotalAmount(itemsTotalAmount)
                .setIsNew(isNew)
                .setShippingMethodKind(shippingMethodKind)
                .build();
    }

    @Step("Отправляем событие нового заказа для магазина в kafka")
    public static void publishOrderEvent(final double latitude, final double longitude, final Timestamp deliveryPromiseUpperDttmStartsAt,
                                         final Timestamp deliveryPromiseUpperDttmEndsAt, final int numberOfPositionsInOrder, final String orderUuid,
                                         final int orderWeightGramms, final OperationsOrderService.EventOrder.RequestOrderType requestOrderType,
                                         final OperationsOrderService.EventOrder.ShipmentStatus shipmentStatus, final String placeUUID,
                                         final String shipmentUuid, final String orderNumber, final float itemsTotalAmount, final boolean isNew,
                                         final OperationsOrderService.EventOrder.ShippingMethodKind shippingMethodKind) {
        final var orderEvent = getOrderEvent(latitude, longitude, deliveryPromiseUpperDttmStartsAt, deliveryPromiseUpperDttmEndsAt, numberOfPositionsInOrder, orderUuid, orderWeightGramms, requestOrderType, shipmentStatus, placeUUID, shipmentUuid, orderNumber, itemsTotalAmount, isNew, shippingMethodKind);
        kafka.publish(configFctOrderStf(), orderEvent);
    }

    @Step("Создаем событие нового изменения ритейлера")
    public static RetailOnboardingRetailerData.Retailer getRetailerEvent(final String uuid, final RetailOnboardingRetailerData.Retailer.RetailerVertical retailerVertical) {
        return RetailOnboardingRetailerData.Retailer.newBuilder()
                .setAppearance(RetailOnboardingRetailerData.Retailer.Appearance.newBuilder()
                        .setBackgroundColor("#36367")
                        .setColor("#002d72")
                        .setImageColor("#ff0000")
                        .setLogoBackgroundColor("#36367b")
                        .setLogoImage(RetailOnboardingRetailerData.Retailer.Appearance.LogoImage.newBuilder()
                                .setContentType("image/png")
                                .setFileName("Metro.png")
                                .setFileSize(3563)
                                .setUpdatedAt(getTimestamp())
                                .setUrl("https://stf-6.k-stage.sbermarket.tech/statics/stf-6/public/retailer_banners/1/logo_images/Metro.png?1663335797")
                                .build())
                        .setMiniLogoImage(RetailOnboardingRetailerData.Retailer.Appearance.MiniLogoImage.newBuilder()
                                .setContentType("image/png")
                                .setFileName("Metro_(1).png")
                                .setFileSize(1552)
                                .setUpdatedAt(getTimestamp())
                                .setUrl("https://stf-6.k-stage.sbermarket.tech/statics/stf-6/public/retailer_banners/1/mini_logo_images/Metro_%281%29.png?1663335798")
                                .build())
                        .setSecondaryColor("#ffe401")
                        .setSideImage(RetailOnboardingRetailerData.Retailer.Appearance.SideImage.newBuilder()
                                .setContentType("image/png")
                                .setFileName("Img.jpeg")
                                .setFileSize(31251)
                                .setUpdatedAt(getTimestamp())
                                .setUrl("https://stf-6.k-stage.sbermarket.tech/statics/stf-6/public/retailer_banners/1/side_images/Img.jpeg?1663335798")
                                .build())
                        .build())
                .setContractType("agent")
                .setCreationTimestamp(getTimestamp())
                .setDescription("Во всех городах-миллионниках")
                .setImportKey("1")
                .setInn("7704218694")
                .setLegacyStfId(1)
                .setLegalAddress("1")
                .setLegalName("Метро Ритейлер")
                .setName("METRO")
                .setPhone("+79768687233")
                .setShortName("METRO")
                .setSlug("metro")
                .setUuid(uuid)
                .setVertical(retailerVertical)
                .build();
    }

    @Step("Отправляем событие изменения ритейлера в kafka")
    public static void publishRetailerChangedEvent(final String uuid, final RetailOnboardingRetailerData.Retailer.RetailerVertical retailerVertical) {
        final var retailerEvent = getRetailerEvent(uuid, retailerVertical);
        kafka.publish(configRetailerChanged(), retailerEvent);
    }


    @Step("Создаем событие нового изменения магазина в сервисе retail-onboarding")
    public static RetailOnboardingStoreData.Store getStoreEvent(final String uuid, final int orderPreparationSlaMinutes,
                                                                final String ordersApiIntegrationType, final String retailerUuid) {
        return RetailOnboardingStoreData.Store.newBuilder()
                .setActive(true)
                .setCreationTimestamp(getTimestamp())
                .setImportKey("1-19")
                .setLegacyStfId(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID)
                .setLegacyStfRetailerId(1)
                .setLocation(RetailOnboardingStoreData.Store.Location.newBuilder()
                        .setBuilding("2Б")
                        .setCityId(28)
                        .setCityName("Москва")
                        .setCoordinates(RetailOnboardingStoreData.Store.Location.Coordinates.newBuilder()
                                .setLat(55.700683)
                                .setLon(37.726683)
                                .build())
                        .setCountry("Российская Федерация")
                        .setFullAddress("Москва, Шоссейная, 2Б")
                        .setStreet("Шоссейная")
                        .setTimeZone("Europe/Moscow")
                        .build())
                .setOrderRules(RetailOnboardingStoreData.Store.OrderRules.newBuilder()
                        .setMinFirstOrderAmount(1000)
                        .setMinOrderAmount(1000)
                        .build())
                .setOrdersApiIntegrationType(ordersApiIntegrationType)
                .setOrdersApiSendCustomer(true)
                .setPaymentAtCheckout("bank_card")
                .setRetailerUuid(retailerUuid)
                .setSendCreatedHook("immediately")
                .setUuid(uuid)
                .setOrderPreparationSlaMinutes(orderPreparationSlaMinutes)
                .setStatus(RetailOnboardingStoreData.Store.Status.ACTIVE)
                .build();
    }

    @Step("Отправляем событие изменения магазина в kafka сервиса retail-onboarding")
    public static void publishStoreChangedEvent(final String uuid, final int orderPreparationSlaMinutes, final String ordersApiIntegrationType,
                                                final String retailerUuid) {
        final var storeEvent = getStoreEvent(uuid, orderPreparationSlaMinutes, ordersApiIntegrationType, retailerUuid);
        kafka.publish(configRetailerOnboardingStoreChanged(), storeEvent);
    }

    @Step("Создаем событие нового изменения магазина в топик store-changed")
    public static StoreChangedOuterClass.StoreChanged getStoreChangedEvent(final int additionalSecondsForAssembly, final boolean autoRouting,
                                                                           final int secondsForAssemblyItem, final String scheduleType, final StoreChangedOuterClass.AssemblyType assemblyTaskType,
                                                                           final StoreChangedOuterClass.PlaceSettings.DeliveryType deliveryTaskType, final String placeUUID) {
        return StoreChangedOuterClass.StoreChanged.newBuilder()
                .setId(17)
                .setUuid(placeUUID)
                .setName("METRO, Шоссейная (команда Vidar)")
                .setCreatedAt(getTimestamp())
                .setUpdatedAt(getTimestamp())
                .setTimeZone("Europe/Moscow")
                .setOperationalZoneId(1)
                .setRetailerId(1)
                .setImportKeyPostfix("1")
                .setLocation("{\"id\":96,\"lat\":55.700683,\"lon\":37.726683,\"area\":null,\"city\":\"Москва\",\"kind\":null,\"block\":\"\",\"floor\":null,\"phone\":null,\"region\":null,\"street\":\"Шоссейная (команда Vidar)\",\"building\":\"2Б\",\"comments\":null,\"entrance\":null,\"apartment\":null,\"door_phone\":null,\"settlement\":null,\"elevator\":null,\"delivery_to_door\":false,\"full_address\":\"Москва, Шоссейная (команда Vidar), 2Б\"}")
                .setHelpdeskeddyId(1240)
                .setHasConveyor(false)
                .setAutoRouting(autoRouting)
                .setFastPayment(true)
                .setFastPaymentMetroStoreDns("MOW11MPSU010001")
                .setFastPaymentMetroBarcodeCiphertext("qj8vlAVw2MVIuiDOh5wlPXKhprx7RUobw+j3tawts4XQsKJwktfiNC73b8vLozF+lfw=")
                .setExpressDelivery(true)
                .setSecondsForAssemblyItem(secondsForAssemblyItem)
                .setAdditionalSecondsForAssembly(additionalSecondsForAssembly)
                .setDeliveryAreaId(151)
                .setRetailerStoreId("19")
                .setBoxScanning(false)
                .setExternalAssembliesEnabled(false)
                .setTraining(false)
                .setScheduleType(scheduleType)
                .setStoreZones("[{\"id\":3,\"name\":\"Зона Шоссейной\",\"area\":[[[37.792968900000005,55.622277300000015],[37.835566699999994,55.65057209999999],[37.8403912,55.661889300000006],[37.8293224,55.69098759999999],[37.8292576,55.69410300000001],[37.830051,55.69857329999999],[37.8344982,55.7068726],[37.8386038,55.71517179999999],[37.840226,55.730922],[37.8417203,55.743033],[37.74430650000001,55.74759330000001],[37.731022,55.754385000000006],[37.699478,55.74840900000001],[37.692999,55.755304],[37.6744878,55.7212596],[37.629633,55.70518400000001],[37.628947,55.70151000000001],[37.6346352,55.684103000000015],[37.6453916,55.6846066],[37.6657497,55.68637699999999],[37.6852854,55.690934199999994],[37.695611,55.69145100000001],[37.70634,55.688694],[37.7125856,55.68495279999999],[37.7109019,55.68017930000001],[37.71454210000001,55.67231490000002],[37.7142177,55.6683341],[37.709593000000005,55.666827700000006],[37.7030751,55.67096320000001],[37.695009,55.67196579999999],[37.685913,55.670838],[37.676239,55.66829100000001],[37.672806,55.66441899999999],[37.6705744,55.65463840000001],[37.6765034,55.65041500000001],[37.699250600000006,55.64202499999999],[37.712984,55.63928900000001],[37.782668,55.64819500000001],[37.783009,55.636351],[37.792968900000005,55.622277300000015]]]}]")
                .setFastPaymentCashless(false)
                .setAvailableOn(getTimestamp())
                .setBaseStoreId(17)
                .setOpeningTime("01:00")
                .setClosingTime("23:00")
                .setDispatchSettings(StoreChangedOuterClass.DispatchSettings.newBuilder()
                        .setAdditionalFactorForStraightDistanceToClientMin(14)
                        .setAverageSpeedForStraightDistanceToClientMin(19)
                        .setAvgParkingMinVehicle(7)
                        .setAvgToPlaceMin(3)
                        .setAvgToPlaceMinExternal(16)
                        .setGapTaxiPunishMin(16)
                        .setLastPositionExpire(18000)
                        .setMaxCurrentOrderAssignQueue(5)
                        .setMaxOrderAssignRetryCount(4)
                        .setMaxWaitingTimeForCourierMin(17)
                        .setOfferSeenTimeoutSec(20)
                        .setOfferServerTimeoutSec(20)
                        .setOrderReceiveTimeFromAssemblyToDeliveryMin(15)
                        .setOrderTransferTimeFromAssemblyToDeliveryMin(5)
                        .setOrderTransferTimeFromDeliveryToClientMin(5)
                        .setOrderWeightThresholdToAssignToVehicleGramms(15000)
                        .setPlaceLocationCenter(false)
                        .setTaxiDeliveryOnly(false)
                        .setTaxiAvailable(false)
                        .setExternalAssembliersPresented(false)
                        .build())
                .setPlaceSettings(StoreChangedOuterClass.PlaceSettings.newBuilder()
                        .setPlaceType(StoreChangedOuterClass.PlaceType.SHOP)
                        .setAssemblyTaskType(assemblyTaskType)
                        .setDeliveryTaskType(deliveryTaskType)
                        .addPlaceAvailableTasksToBeAssigned(StoreChangedOuterClass.AvailableTasks.DELIVERY)
                        .addPlaceAvailableTasksToBeAssigned(StoreChangedOuterClass.AvailableTasks.ASSEMBLY)
                        .setPlaceLocation(StoreChangedOuterClass.LocationPoint.newBuilder()
                                .setLat(55.700683)
                                .setLon(37.726683)
                                .build())
                        .build())
                .build();
    }

    @Step("Отправляем событие изменения магазина в kafka топик store-changed")
    public static void publishShopperStoreChangedEvent (final int additionalSecondsForAssembly, final boolean autoRouting,
                                                        final int secondsForAssemblyItem, final String scheduleType, final StoreChangedOuterClass.AssemblyType assemblyTaskType,
                                                        final StoreChangedOuterClass.PlaceSettings.DeliveryType deliveryTaskType, final String placeUUID) {
        final var storeChanged  = getStoreChangedEvent(additionalSecondsForAssembly, autoRouting, secondsForAssemblyItem, scheduleType, assemblyTaskType, deliveryTaskType, placeUUID);
        kafka.publish(configStoreChanged(), storeChanged);
    }

    @Step("Получение левой границы слота")
    public static Timestamp getDeliveryPromiseUpperDttmStartsAt() {
        final var response = StoresV1Request.NextDeliveries.GET(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID, new StoresV1Request.NextDeliveriesParams());
        final var nextDeliveriesV2Response = response.as(NextDeliveriesV2Response.class);
        final var nextDeliveryV2 = nextDeliveriesV2Response.getNextDeliveries().get(0);
        return getTimestampFromString(nextDeliveryV2.getStartsAt());
    }

    @Step("Получение правой границы слота")
    public static Timestamp getDeliveryPromiseUpperDttmEndsAt() {
        final var response = StoresV1Request.NextDeliveries.GET(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID, new StoresV1Request.NextDeliveriesParams());
        final var nextDeliveriesV2Response = response.as(NextDeliveriesV2Response.class);
        final var nextDeliveryV2 = nextDeliveriesV2Response.getNextDeliveries().get(0);
        return getTimestampFromString(nextDeliveryV2.getEndsAt());
    }
}
