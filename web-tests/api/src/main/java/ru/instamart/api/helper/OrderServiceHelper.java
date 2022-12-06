package ru.instamart.api.helper;

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

    @Step("Отправляем событие изменения магазина в kafka")
    public static void publishStoreChangedEvent(final String uuid, final int orderPreparationSlaMinutes, final String ordersApiIntegrationType,
                                                final String retailerUuid) {
        final var storeEvent = getStoreEvent(uuid, orderPreparationSlaMinutes, ordersApiIntegrationType, retailerUuid);
        kafka.publish(configRetailerOnboardingStoreChanged(), storeEvent);
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
