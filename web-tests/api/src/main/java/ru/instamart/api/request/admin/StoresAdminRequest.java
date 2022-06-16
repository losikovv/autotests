package ru.instamart.api.request.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import ru.instamart.api.endpoint.AdminEndpoints;
import ru.instamart.api.enums.v1.ExternalAssemblyKind;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.request.AdminRequestBase;
import ru.instamart.jdbc.dao.stf.*;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.util.DoubleUtil;
import ru.sbermarket.common.Mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.instamart.kraken.helper.LegalEntityHelper.generateInnUL;
import static ru.instamart.kraken.util.TimeUtil.getDbDate;
import static ru.instamart.kraken.util.TimeUtil.getDeliveryDateFrom;

public class StoresAdminRequest extends AdminRequestBase {

    @Step("{method} /" + AdminEndpoints.STORES)
    public static Response POST(Store store) {
        return givenWithAuth()
                .formParams(Mapper.INSTANCE.objectToMap(store))
                .post(AdminEndpoints.STORES);
    }

    @Step("{method} /" + AdminEndpoints.STORE)
    public static Response PATCH(Store store, Integer storeId) {
        Map<String, Object> params = new HashMap<>();
        params.put("_method", "patch");
        params.putAll(Mapper.INSTANCE.objectToMap(store));
        return givenWithAuth()
                .formParams(Mapper.INSTANCE.objectToMap(params))
                .patch(AdminEndpoints.STORE, storeId);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class Store {
        @JsonProperty("store[retailer_id]")
        private Long retailerId;
        @JsonProperty("store[operational_zone_id]")
        private Long operationalZoneId;
        @JsonProperty("store[city_id]")
        private Long cityId;
        @JsonProperty("store[time_zone]")
        private String timeZone;
        @JsonProperty("store[config_attributes][inn]")
        private String inn;
        @JsonProperty("store[config_attributes][ogrn]")
        private String ogrn;
        @JsonProperty("store[config_attributes][legal_name]")
        private String legalName;
        @JsonProperty("store[config_attributes][email]")
        private String email;
        @JsonProperty("store[config_attributes][phone]")
        private String phone;
        @JsonProperty("store[tenant_ids][]")
        private Object tenantIds;
        @JsonProperty("store[config_attributes][lifepay_identifier]")
        private String lifepayIdentifier;
        @JsonProperty("store[config_attributes][import_key_postfix]")
        private Integer importKeyPostFix;
        @JsonProperty("store[config_attributes][shipment_base_kilos]")
        private Double shipmentBaseKilos;
        @JsonProperty("store[config_attributes][shipment_base_items_count]")
        private Integer shipmentBaseItemsCount;
        @JsonProperty("store[config_attributes][min_order_amount]")
        private Integer minOrderAmount;
        @JsonProperty("store[config_attributes][min_first_order_amount]")
        private Integer minFirstOrderAmount;
        @JsonProperty("store[config_attributes][min_order_amount_pickup]")
        private Integer minOrderAmountPickup;
        @JsonProperty("store[config_attributes][min_first_order_amount_pickup]")
        private Integer minFirstOrderAmountPickup;
        @JsonProperty("store[config_attributes][seconds_for_assembly_item]")
        private Integer secondsForAssemblyItem;
        @JsonProperty("store[config_attributes][additional_seconds_for_assembly]")
        private Integer additionalSecondsForAssembly;
        @JsonProperty("store[config_attributes][disallow_order_editing_hours]")
        private Integer disallowOrderEditingHours;
        @JsonProperty("store[config_attributes][hours_order_edit_locked]")
        private Integer hoursOrderEditLocked;
        @JsonProperty("store[config_attributes][orders_api_integration_type]")
        private String orderApiIntegrationType;
        @JsonProperty("store[config_attributes][send_created_hook]")
        private String sendCreatedHook;
        @JsonProperty("store[config_attributes][payment_at_checkout]")
        private String paymentAtCheckout;
        @JsonProperty("store[on_demand]")
        private Integer onDemand;
        @JsonProperty("store[opening_time]")
        private String openingTime;
        @JsonProperty("store[closing_time]")
        private String closingTime;
        @JsonProperty("store[on_demand_closing_delta]")
        private Integer onDemandClosingDelta;
        @JsonProperty("store[is_ml_enabled]")
        private Integer isMlEnabled;
        @JsonProperty("store[avg_positions_per_place]")
        private Integer avgPositionsPerPlace;
        @JsonProperty("store[to_place_sec]")
        private Integer toPlaceSec;
        @JsonProperty("store[collection_speed_sec_per_pos]")
        private Integer collectionSpeedSecPerPos;
        @JsonProperty("store[is_sigma_enabled]")
        private Integer isSigmaEnabled;
        @JsonProperty("store[has_conveyor]")
        private Integer hasConveyor;
        @JsonProperty("store[auto_routing]")
        private Integer autoRouting;
        @JsonProperty("store[express_delivery]")
        private Integer expressDelivery;
        @JsonProperty("store[external_assembly]")
        private Integer externalAssembly;
        @JsonProperty("store[box_scanning]")
        private Integer boxScanning;
        @JsonProperty("store[training]")
        private Integer training;
        @JsonProperty("store[config_attributes][use_work_schedule]")
        private Integer useWorkSchedule;
        @JsonProperty("store[work_schedules_attributes][0][week_day]")
        private String monday;
        @JsonProperty("store[work_schedules_attributes][0][day_off]")
        private Integer mondayDayOff;
        @JsonProperty("store[store_shipping_methods_attributes][0][shipping_method_id]")
        private Long shippingMethodId;
        @JsonProperty("store[store_shipping_methods_attributes][0][tenant_id]")
        private String tenantId;
        @JsonProperty("store[store_shipping_methods_attributes][0][available_on]")
        private String availableOn;
        @JsonProperty("store[payment_methods_stores_attributes][instamart__1][]")
        private Long paymentMethodInstamart1;
        @JsonProperty("store[payment_methods_stores_attributes][instamart__2][]")
        private Long paymentMethodInstamart2;
        @JsonProperty("store[payment_methods_stores_attributes][instamart__3][]")
        private Long paymentMethodInstamart3;
        @JsonProperty("store[payment_methods_stores_attributes][lenta__1][]")
        private Long paymentMethodLenta1;
        @JsonProperty("store[payment_methods_stores_attributes][lenta__2][]")
        private Long paymentMethodLenta2;
        @JsonProperty("store[payment_methods_stores_attributes][lenta__3][]")
        private Long paymentMethodLenta3;
        @JsonProperty("store[payment_methods_stores_attributes][metro__1][]")
        private Long paymentMethodMetro1;
        @JsonProperty("store[payment_methods_stores_attributes][metro__2][]")
        private Long paymentMethodMetro2;
        @JsonProperty("store[payment_methods_stores_attributes][metro__3][]")
        private Long paymentMethodMetro3;
        @JsonProperty("store[payment_methods_stores_attributes][okey__1][]")
        private Long paymentMethodOkey1;
        @JsonProperty("store[payment_methods_stores_attributes][okey__2][]")
        private Long paymentMethodOkey2;
        @JsonProperty("store[payment_methods_stores_attributes][okey__3][]")
        private Long paymentMethodOkey3;
        @JsonProperty("store[payment_methods_stores_attributes][sbermarket__1][]")
        private Long paymentMethodSbermarket1;
        @JsonProperty("store[payment_methods_stores_attributes][sbermarket__2][]")
        private Long paymentMethodSbermarket2;
        @JsonProperty("store[payment_methods_stores_attributes][sbermarket__3][]")
        private Long paymentMethodSbermarket3;
        @JsonProperty("store[payment_methods_stores_attributes][selgros__1][]")
        private Long paymentMethodSelgros1;
        @JsonProperty("store[payment_methods_stores_attributes][selgros__2][]")
        private Long paymentMethodSelgros2;
        @JsonProperty("store[payment_methods_stores_attributes][selgros__3][]")
        private Long paymentMethodSelgros3;
        @JsonProperty("store[payment_methods_stores_attributes][smbusiness__1][]")
        private Long paymentMethodSmbusiness1;
        @JsonProperty("store[payment_methods_stores_attributes][smbusiness__2][]")
        private Long paymentMethodSmbusiness2;
        @JsonProperty("store[payment_methods_stores_attributes][smbusiness__3][]")
        private Long paymentMethodSmbusiness3;
        @JsonProperty("store[location_attributes][country_id]")
        private Long countryId;
        @JsonProperty("store[location_attributes][full_address]")
        private String fullAddress;
        @JsonProperty("store[location_attributes][city]")
        private String city;
        @JsonProperty("store[location_attributes][street]")
        private String street;
        @JsonProperty("store[location_attributes][building]")
        private Integer building; //todo переделать на String
        @JsonProperty("store[location_attributes][lon]")
        private Double lon;
        @JsonProperty("store[location_attributes][lat]")
        private Double lat;
    }

    public static Store getStoreSelgrosMiklouhoMaclay() {
        List<Long> paymentMethodIds = SpreePaymentMethodsDao.INSTANCE.getActivePaymentMethodsIds();
        return Store.builder()
                .retailerId(SpreeRetailersDao.INSTANCE.getIdBySlug("selgros"))
                .operationalZoneId(OperationalZonesDao.INSTANCE.getIdByName("Москва"))
                .cityId(CitiesDao.INSTANCE.getCityByName("Москва").getId())
                .timeZone("Europe/Moscow")
                .inn(generateInnUL())
                .legalName("Autotest-" + Generate.literalString(6))
                .importKeyPostFix(RandomUtils.nextInt(1, 1000000))
                .shipmentBaseKilos((double) RandomUtils.nextInt(10, 1000))
                .shipmentBaseItemsCount(RandomUtils.nextInt(1, 100))
                .minOrderAmount(RandomUtils.nextInt(1000, 5000))
                .minFirstOrderAmount(0)
                .minOrderAmountPickup(0)
                .minFirstOrderAmountPickup(0)
                .disallowOrderEditingHours(RandomUtils.nextInt(1, 6))
                .hoursOrderEditLocked(0)
                .orderApiIntegrationType("shopper")
                .sendCreatedHook("immediately")
                .paymentAtCheckout("bank_card")
                .onDemand(0)
                .isMlEnabled(0)
                .avgPositionsPerPlace(10)
                .toPlaceSec(40)
                .collectionSpeedSecPerPos(20)
                .isSigmaEnabled(0)
                .hasConveyor(0)
                .autoRouting(0)
                .expressDelivery(0)
                .externalAssembly(0)
                .boxScanning(0)
                .training(0)
                .useWorkSchedule(0)
                .monday("monday")
                .mondayDayOff(0)
                .shippingMethodId(SpreeShippingMethodsDao.INSTANCE.getShippingMethodId(ShippingMethodV2.BY_COURIER.getMethod()))
                .tenantId("instamart")
                .openingTime("11:00")
                .closingTime("23:00")
                .availableOn(getDeliveryDateFrom())
                .paymentMethodInstamart1(paymentMethodIds.get(0))
                .paymentMethodInstamart2(paymentMethodIds.get(1))
                .paymentMethodInstamart3(paymentMethodIds.get(2))
                .paymentMethodLenta1(paymentMethodIds.get(3))
                .paymentMethodLenta2(paymentMethodIds.get(4))
                .paymentMethodLenta3(paymentMethodIds.get(5))
                .paymentMethodMetro1(paymentMethodIds.get(0))
                .paymentMethodMetro2(paymentMethodIds.get(1))
                .paymentMethodMetro3(paymentMethodIds.get(2))
                .paymentMethodOkey1(paymentMethodIds.get(3))
                .paymentMethodOkey2(paymentMethodIds.get(4))
                .paymentMethodOkey3(paymentMethodIds.get(5))
                .paymentMethodSbermarket1(paymentMethodIds.get(0))
                .paymentMethodSbermarket2(paymentMethodIds.get(1))
                .paymentMethodSbermarket3(paymentMethodIds.get(2))
                .paymentMethodSelgros1(paymentMethodIds.get(3))
                .paymentMethodSelgros2(paymentMethodIds.get(4))
                .paymentMethodSelgros3(paymentMethodIds.get(5))
                .paymentMethodSmbusiness1(paymentMethodIds.get(0))
                .paymentMethodSmbusiness2(paymentMethodIds.get(1))
                .paymentMethodSmbusiness3(paymentMethodIds.get(2))
                .countryId(1L)
                .fullAddress("Moscow, Miklouho-Maclay, 40")
                .city("Moscow")
                .street("Miklouho-Maclay")
                .building(40)
                .lat(55.638318)
                .lon(37.539509)
                .build();
    }

    public static Store getStoreForRetailerTests(final String retailerName, final String city) {
        List<Long> paymentMethodIds = SpreePaymentMethodsDao.INSTANCE.getActivePaymentMethodsIds();
        return Store.builder()
                .retailerId(SpreeRetailersDao.INSTANCE.getIdByName(retailerName))
                .operationalZoneId(OperationalZonesDao.INSTANCE.getIdByName(city))
                .cityId(CitiesDao.INSTANCE.getCityByName(city).getId())
                .timeZone("Europe/Moscow")
                .inn(generateInnUL())
                .legalName("Autotest-" + Generate.literalString(6))
                .importKeyPostFix(RandomUtils.nextInt(1, 1000000))
                .shipmentBaseKilos((double) RandomUtils.nextInt(10, 1000))
                .shipmentBaseItemsCount(RandomUtils.nextInt(1, 100))
                .minOrderAmount(RandomUtils.nextInt(1000, 5000))
                .minFirstOrderAmount(0)
                .minOrderAmountPickup(0)
                .minFirstOrderAmountPickup(0)
                .disallowOrderEditingHours(RandomUtils.nextInt(1, 6))
                .hoursOrderEditLocked(0)
                .orderApiIntegrationType("shopper")
                .sendCreatedHook("immediately")
                .paymentAtCheckout("bank_card")
                .onDemand(0)
                .isMlEnabled(0)
                .avgPositionsPerPlace(10)
                .toPlaceSec(40)
                .collectionSpeedSecPerPos(20)
                .isSigmaEnabled(0)
                .hasConveyor(0)
                .autoRouting(0)
                .expressDelivery(0)
                .externalAssembly(0)
                .boxScanning(0)
                .training(0)
                .useWorkSchedule(0)
                .monday("monday")
                .mondayDayOff(0)
                .shippingMethodId(SpreeShippingMethodsDao.INSTANCE.getShippingMethodId(ShippingMethodV2.BY_COURIER.getMethod()))
                .tenantId("instamart")
                .openingTime("11:00")
                .closingTime("23:00")
                .availableOn(getDeliveryDateFrom())
                .paymentMethodInstamart1(paymentMethodIds.get(0))
                .paymentMethodInstamart2(paymentMethodIds.get(1))
                .paymentMethodInstamart3(paymentMethodIds.get(2))
                .paymentMethodLenta1(paymentMethodIds.get(3))
                .paymentMethodLenta2(paymentMethodIds.get(4))
                .paymentMethodLenta3(paymentMethodIds.get(5))
                .paymentMethodMetro1(paymentMethodIds.get(0))
                .paymentMethodMetro2(paymentMethodIds.get(1))
                .paymentMethodMetro3(paymentMethodIds.get(2))
                .paymentMethodOkey1(paymentMethodIds.get(3))
                .paymentMethodOkey2(paymentMethodIds.get(4))
                .paymentMethodOkey3(paymentMethodIds.get(5))
                .paymentMethodSbermarket1(paymentMethodIds.get(0))
                .paymentMethodSbermarket2(paymentMethodIds.get(1))
                .paymentMethodSbermarket3(paymentMethodIds.get(2))
                .paymentMethodSelgros1(paymentMethodIds.get(3))
                .paymentMethodSelgros2(paymentMethodIds.get(4))
                .paymentMethodSelgros3(paymentMethodIds.get(5))
                .paymentMethodSmbusiness1(paymentMethodIds.get(0))
                .paymentMethodSmbusiness2(paymentMethodIds.get(1))
                .paymentMethodSmbusiness3(paymentMethodIds.get(2))
                .countryId(1L)
                .fullAddress(city + ", Mira av., 211")
                .city(city)
                .street("Mira")
                .building(211)
                .lat(DoubleUtil.roundAvoid(
                        DoubleUtil.getRandomDoubleBetweenRange(50.0, 55.0), 6))
                .lon(DoubleUtil.roundAvoid(
                        DoubleUtil.getRandomDoubleBetweenRange(30.0, 35.0), 6))
                .build();
    }

    public static Store getStoreLentaOrekhoviyBulvar() {
        List<Long> paymentMethodIds = SpreePaymentMethodsDao.INSTANCE.getActivePaymentMethodsIds();
        return Store.builder()
                .retailerId(SpreeRetailersDao.INSTANCE.getIdBySlug("lenta"))
                .operationalZoneId(OperationalZonesDao.INSTANCE.getIdByName("Москва"))
                .cityId(CitiesDao.INSTANCE.getCityByName("Москва").getId())
                .timeZone("Europe/Moscow")
                .inn(generateInnUL())
                .legalName("Orekhoviy autotest")
                .importKeyPostFix(1122)
                .shipmentBaseKilos(40.0)
                .shipmentBaseItemsCount(30)
                .minOrderAmount(100)
                .minFirstOrderAmount(1800)
                .minOrderAmountPickup(0)
                .minFirstOrderAmountPickup(0)
                .disallowOrderEditingHours(4)
                .hoursOrderEditLocked(0)
                .orderApiIntegrationType(ExternalAssemblyKind.DELIVERY_BY_SBERMARKET.getValue())
                .sendCreatedHook("immediately")
                .paymentAtCheckout("bank_card")
                .onDemand(0)
                .isMlEnabled(0)
                .avgPositionsPerPlace(10)
                .toPlaceSec(40)
                .collectionSpeedSecPerPos(20)
                .isSigmaEnabled(0)
                .hasConveyor(0)
                .autoRouting(1)
                .expressDelivery(0)
                .externalAssembly(0)
                .boxScanning(0)
                .training(0)
                .useWorkSchedule(0)
                .monday("monday")
                .mondayDayOff(0)
                .shippingMethodId(SpreeShippingMethodsDao.INSTANCE.getShippingMethodId(ShippingMethodV2.BY_COURIER.getMethod()))
                .tenantId("instamart")
                .openingTime("11:00")
                .closingTime("23:00")
                .availableOn(getDeliveryDateFrom())
                .paymentMethodInstamart1(6L)
                .paymentMethodInstamart2(paymentMethodIds.get(1))
                .paymentMethodInstamart3(paymentMethodIds.get(2))
                .paymentMethodLenta1(6L)
                .paymentMethodLenta2(paymentMethodIds.get(4))
                .paymentMethodLenta3(paymentMethodIds.get(5))
                .paymentMethodMetro1(6L)
                .paymentMethodMetro2(paymentMethodIds.get(1))
                .paymentMethodMetro3(paymentMethodIds.get(2))
                .paymentMethodOkey1(6L)
                .paymentMethodOkey2(paymentMethodIds.get(4))
                .paymentMethodOkey3(paymentMethodIds.get(5))
                .paymentMethodSbermarket1(6L)
                .paymentMethodSbermarket2(paymentMethodIds.get(1))
                .paymentMethodSbermarket3(paymentMethodIds.get(2))
                .paymentMethodSelgros1(6L)
                .paymentMethodSelgros2(paymentMethodIds.get(4))
                .paymentMethodSelgros3(paymentMethodIds.get(5))
                .paymentMethodSmbusiness1(6L)
                .paymentMethodSmbusiness2(paymentMethodIds.get(1))
                .paymentMethodSmbusiness3(paymentMethodIds.get(2))
                .countryId(1L)
                .fullAddress("Moscow, Orekhoviy bul'var")
                .city("Moscow")
                .street("Orekhoviy bul'var")
                .building(22)
                .lon(37.732718)
                .lat(55.612045)
                .build();
    }

    public static Store getStoreLentaElino() {
        List<Long> paymentMethodIds = SpreePaymentMethodsDao.INSTANCE.getActivePaymentMethodsIds();
        return Store.builder()
                .retailerId(SpreeRetailersDao.INSTANCE.getIdBySlug("lenta"))
                .operationalZoneId(OperationalZonesDao.INSTANCE.getIdByName("Москва"))
                .cityId(CitiesDao.INSTANCE.getCityByName("д.Елино").getId())
                .timeZone("Europe/Moscow")
                .inn(generateInnUL())
                .legalName("Elino autotest")
                .importKeyPostFix(116)
                .shipmentBaseKilos(40.0)
                .shipmentBaseItemsCount(30)
                .minOrderAmount(100)
                .minFirstOrderAmount(1800)
                .minOrderAmountPickup(0)
                .minFirstOrderAmountPickup(0)
                .disallowOrderEditingHours(4)
                .hoursOrderEditLocked(0)
                .orderApiIntegrationType(ExternalAssemblyKind.INTEGRATION_FOR_ACCOUNTING.getValue())
                .sendCreatedHook("immediately")
                .paymentAtCheckout("bank_card")
                .onDemand(0)
                .isMlEnabled(0)
                .avgPositionsPerPlace(10)
                .toPlaceSec(40)
                .collectionSpeedSecPerPos(20)
                .isSigmaEnabled(0)
                .hasConveyor(0)
                .autoRouting(1)
                .expressDelivery(0)
                .externalAssembly(0)
                .boxScanning(0)
                .training(0)
                .useWorkSchedule(0)
                .monday("monday")
                .mondayDayOff(0)
                .shippingMethodId(SpreeShippingMethodsDao.INSTANCE.getShippingMethodId(ShippingMethodV2.BY_COURIER.getMethod()))
                .tenantId("instamart")
                .openingTime("11:00")
                .closingTime("23:00")
                .availableOn(getDeliveryDateFrom())
                .paymentMethodInstamart1(6L)
                .paymentMethodInstamart2(paymentMethodIds.get(1))
                .paymentMethodInstamart3(paymentMethodIds.get(2))
                .paymentMethodLenta1(6L)
                .paymentMethodLenta2(paymentMethodIds.get(4))
                .paymentMethodLenta3(paymentMethodIds.get(5))
                .paymentMethodMetro1(6L)
                .paymentMethodMetro2(paymentMethodIds.get(1))
                .paymentMethodMetro3(paymentMethodIds.get(2))
                .paymentMethodOkey1(6L)
                .paymentMethodOkey2(paymentMethodIds.get(4))
                .paymentMethodOkey3(paymentMethodIds.get(5))
                .paymentMethodSbermarket1(6L)
                .paymentMethodSbermarket2(paymentMethodIds.get(1))
                .paymentMethodSbermarket3(paymentMethodIds.get(2))
                .paymentMethodSelgros1(6L)
                .paymentMethodSelgros2(paymentMethodIds.get(4))
                .paymentMethodSelgros3(paymentMethodIds.get(5))
                .paymentMethodSmbusiness1(6L)
                .paymentMethodSmbusiness2(paymentMethodIds.get(1))
                .paymentMethodSmbusiness3(paymentMethodIds.get(2))
                .countryId(1L)
                .fullAddress("Elino")
                .city("Elino")
                .street("Elino")
                .building(20)
                .lon(37.27379)
                .lat(55.99013)
                .build();
    }

    public static Store getStoreVictoriaTest() {
        List<Long> paymentMethodIds = SpreePaymentMethodsDao.INSTANCE.getActivePaymentMethodsIds();
        return Store.builder()
                .retailerId(SpreeRetailersDao.INSTANCE.getIdBySlug("victoria"))
                .operationalZoneId(OperationalZonesDao.INSTANCE.getIdByName("Санкт-Петербург"))
                .cityId(CitiesDao.INSTANCE.getCityByName("Санкт-Петербург").getId())
                .timeZone("Europe/Moscow")
                .inn(generateInnUL())
                .legalName("Test autotest")
                .importKeyPostFix(707)
                .shipmentBaseKilos(40.0)
                .shipmentBaseItemsCount(30)
                .minOrderAmount(100)
                .minFirstOrderAmount(1800)
                .minOrderAmountPickup(0)
                .minFirstOrderAmountPickup(0)
                .disallowOrderEditingHours(4)
                .hoursOrderEditLocked(0)
                .orderApiIntegrationType(ExternalAssemblyKind.DELIVERY_BY_RETAILER.getValue())
                .sendCreatedHook("immediately")
                .paymentAtCheckout("bank_card")
                .onDemand(0)
                .isMlEnabled(0)
                .avgPositionsPerPlace(10)
                .toPlaceSec(40)
                .collectionSpeedSecPerPos(20)
                .isSigmaEnabled(0)
                .hasConveyor(0)
                .autoRouting(1)
                .expressDelivery(0)
                .externalAssembly(0)
                .boxScanning(0)
                .training(0)
                .useWorkSchedule(0)
                .monday("monday")
                .mondayDayOff(0)
                .shippingMethodId(SpreeShippingMethodsDao.INSTANCE.getShippingMethodId(ShippingMethodV2.BY_COURIER.getMethod()))
                .tenantId("instamart")
                .openingTime("11:00")
                .closingTime("23:00")
                .availableOn(getDeliveryDateFrom())
                .paymentMethodInstamart1(6L)
                .paymentMethodInstamart2(paymentMethodIds.get(1))
                .paymentMethodInstamart3(paymentMethodIds.get(2))
                .paymentMethodLenta1(6L)
                .paymentMethodLenta2(paymentMethodIds.get(4))
                .paymentMethodLenta3(paymentMethodIds.get(5))
                .paymentMethodMetro1(6L)
                .paymentMethodMetro2(paymentMethodIds.get(1))
                .paymentMethodMetro3(paymentMethodIds.get(2))
                .paymentMethodOkey1(6L)
                .paymentMethodOkey2(paymentMethodIds.get(4))
                .paymentMethodOkey3(paymentMethodIds.get(5))
                .paymentMethodSbermarket1(6L)
                .paymentMethodSbermarket2(paymentMethodIds.get(1))
                .paymentMethodSbermarket3(paymentMethodIds.get(2))
                .paymentMethodSelgros1(6L)
                .paymentMethodSelgros2(paymentMethodIds.get(4))
                .paymentMethodSelgros3(paymentMethodIds.get(5))
                .paymentMethodSmbusiness1(6L)
                .paymentMethodSmbusiness2(paymentMethodIds.get(1))
                .paymentMethodSmbusiness3(paymentMethodIds.get(2))
                .countryId(1L)
                .fullAddress("Moscow, Test, 88")
                .city("Moscow")
                .street("Test")
                .building(88)
                .lon(0.0)
                .lat(90.0)
                .build();
    }

    public static Store getStoreKaliningradTest() {
        String openingDate = getDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().minusHours(4)));
        String closingDate = getDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().plusHours(3)));
        List<Long> paymentMethodIds = SpreePaymentMethodsDao.INSTANCE.getActivePaymentMethodsIds();
        return Store.builder()
                .retailerId(SpreeRetailersDao.INSTANCE.getIdBySlug("selgros"))
                .operationalZoneId(OperationalZonesDao.INSTANCE.getIdByName("Калининград"))
                .cityId(CitiesDao.INSTANCE.getCityByName("Калининград").getId())
                .timeZone("Europe/Kaliningrad")
                .inn(generateInnUL())
                .legalName("Test autotest")
                .importKeyPostFix(RandomUtils.nextInt(1, 1000000))
                .shipmentBaseKilos(40.0)
                .shipmentBaseItemsCount(30)
                .minOrderAmount(100)
                .minFirstOrderAmount(1800)
                .minOrderAmountPickup(0)
                .minFirstOrderAmountPickup(0)
                .disallowOrderEditingHours(4)
                .hoursOrderEditLocked(0)
                .orderApiIntegrationType(ExternalAssemblyKind.SHOPPER.getValue())
                .sendCreatedHook("immediately")
                .paymentAtCheckout("bank_card")
                .onDemand(1)
                .isMlEnabled(1)
                .avgPositionsPerPlace(10)
                .toPlaceSec(40)
                .collectionSpeedSecPerPos(20)
                .isSigmaEnabled(1)
                .hasConveyor(0)
                .autoRouting(1)
                .expressDelivery(0)
                .externalAssembly(0)
                .boxScanning(0)
                .training(0)
                .useWorkSchedule(0)
                .monday("monday")
                .mondayDayOff(0)
                .shippingMethodId(SpreeShippingMethodsDao.INSTANCE.getShippingMethodId(ShippingMethodV2.BY_COURIER.getMethod()))
                .tenantId("instamart")
                .openingTime(openingDate.substring(11, 16))
                .closingTime(closingDate.substring(11, 16))
                .availableOn(getDeliveryDateFrom())
                .paymentMethodInstamart1(6L)
                .paymentMethodInstamart2(paymentMethodIds.get(1))
                .paymentMethodInstamart3(paymentMethodIds.get(2))
                .paymentMethodLenta1(6L)
                .paymentMethodLenta2(paymentMethodIds.get(4))
                .paymentMethodLenta3(paymentMethodIds.get(5))
                .paymentMethodMetro1(6L)
                .paymentMethodMetro2(paymentMethodIds.get(1))
                .paymentMethodMetro3(paymentMethodIds.get(2))
                .paymentMethodOkey1(6L)
                .paymentMethodOkey2(paymentMethodIds.get(4))
                .paymentMethodOkey3(paymentMethodIds.get(5))
                .paymentMethodSbermarket1(6L)
                .paymentMethodSbermarket2(paymentMethodIds.get(1))
                .paymentMethodSbermarket3(paymentMethodIds.get(2))
                .paymentMethodSelgros1(6L)
                .paymentMethodSelgros2(paymentMethodIds.get(4))
                .paymentMethodSelgros3(paymentMethodIds.get(5))
                .paymentMethodSmbusiness1(6L)
                .paymentMethodSmbusiness2(paymentMethodIds.get(1))
                .paymentMethodSmbusiness3(paymentMethodIds.get(2))
                .countryId(1L)
                .fullAddress("Kaliningrad, Test, 88")
                .city("Kaliningrad")
                .street("Test")
                .building(88)
                .lon(20.548980)
                .lat(54.719269)
                .build();
    }
}
