package ru.instamart.api.request.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.*;
import org.apache.commons.lang3.RandomUtils;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;
import ru.instamart.jdbc.dao.stf.CitiesDao;
import ru.instamart.jdbc.dao.stf.OperationalZonesDao;
import ru.instamart.jdbc.dao.stf.SpreePaymentMethodsDao;
import ru.instamart.jdbc.dao.stf.SpreeRetailersDao;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.util.DoubleUtil;

import java.util.List;

import static ru.instamart.kraken.helper.LegalEntityHelper.generateInnUL;
import static ru.instamart.kraken.helper.LegalEntityHelper.generateOGRN;

public class StoresAdminRequest extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.Admin.STORES)
    public static Response POST(Stores store) {
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(store)
                .post(ApiV1Endpoints.Admin.STORES);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.STORE)
    public static Response PATCH(Stores store, String storeUuid) {
        return givenWithAuth()
                .body(store)
                .patch(ApiV1Endpoints.Admin.STORE, storeUuid);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class Stores {

        @JsonProperty("store")
        private Store store;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class Store {

        @JsonProperty("store_shipping_methods")
        @Singular
        private List<StoresAdminRequest.StoreShippingMethodsItem> storeShippingMethods;

        @JsonProperty("on_demand_closing_delta")
        private int onDemandClosingDelta;

        @JsonProperty("label_items")
        private List<Object> labelItems;

        @JsonProperty("opening_time")
        private Object openingTime;

        @JsonProperty("retailer_id")
        private Long retailerId;

        @JsonProperty("work_schedules")
        @Singular
        private List<WorkSchedulesItem> workSchedules;

        @JsonProperty("tenant_ids")
        @Singular
        private List<String> tenantIds;

        @JsonProperty("time_zone")
        private String timeZone;

        @JsonProperty("on_demand")
        private boolean onDemand;

        @JsonProperty("payment_methods_stores")
        @Singular
        private List<StoresAdminRequest.PaymentMethodsStoresItem> paymentMethodsStores;

        @JsonProperty("closing_time")
        private Object closingTime;

        @JsonProperty("location")
        private StoresAdminRequest.Location location;

        @JsonProperty("operational_zone_id")
        private Long operationalZoneId;

        @JsonProperty("config")
        private StoresAdminRequest.Config config;

        @JsonProperty("city_id")
        private Long cityId;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class Config {

        @JsonProperty("ogrn")
        private String ogrn;

        @JsonProperty("use_work_schedule")
        private boolean useWorkSchedule;

        @JsonProperty("eta_to")
        private int etaTo;

        @JsonProperty("min_first_order_amount")
        private int minFirstOrderAmount;

        @JsonProperty("shipment_base_items_count")
        private int shipmentBaseItemsCount;

        @JsonProperty("import_key_postfix")
        private int importKeyPostfix;

        @JsonProperty("min_first_order_amount_pickup")
        private int minFirstOrderAmountPickup;

        @JsonProperty("inn")
        private String inn;

        @JsonProperty("disallow_order_editing_hours")
        private int disallowOrderEditingHours;

        @JsonProperty("eta_from")
        private int etaFrom;

        @JsonProperty("lifepay_identifier")
        private String lifepayIdentifier;

        @JsonProperty("shipment_base_kilos")
        private Double shipmentBaseKilos;

        @JsonProperty("min_order_amount")
        private int minOrderAmount;

        @JsonProperty("send_created_hook")
        private String sendCreatedHook;

        @JsonProperty("phone")
        private String phone;

        @JsonProperty("payment_at_checkout")
        private String paymentAtCheckout;

        @JsonProperty("legal_name")
        private String legalName;

        @JsonProperty("min_order_amount_pickup")
        private int minOrderAmountPickup;

        @JsonProperty("orders_api_integration_type")
        private String ordersApiIntegrationType;

        @JsonProperty("email")
        private String email;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class Location {

        @JsonProperty("city")
        private String city;

        @JsonProperty("street")
        private String street;

        @JsonProperty("lon")
        private double lon;

        @JsonProperty("building")
        private String building;

        @JsonProperty("lat")
        private double lat;

        @JsonProperty("country_id")
        private int countryId;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class PaymentMethodsStoresItem {

        @JsonProperty("tenant_id")
        private String tenantId;

        @JsonProperty("payment_method_id")
        private Long paymentMethodId;

        @JsonProperty("shipping_method_kind_id")
        private int shippingMethodKindId;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class StoreShippingMethodsItem {

        @JsonProperty("tenant_id")
        private String tenantId;

        @JsonProperty("available_on")
        private String availableOn;

        @JsonProperty("shipping_method_id")
        private int shippingMethodId;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class WorkSchedulesItem {
        @JsonProperty("starts_at")
        private String startsAt;
        @JsonProperty("ends_at")
        private String endsAt;
        @JsonProperty("week_day")
        private String weekDay;
    }

    public static Stores getStoreSelgrosMiklouhoMaclay() {
        List<Long> paymentMethodIds = SpreePaymentMethodsDao.INSTANCE.getActivePaymentMethodsIds();
        return Stores.builder()
                .store(
                        Store.builder()
                                .onDemandClosingDelta(30)
                                .onDemand(false)
                                .cityId((CitiesDao.INSTANCE.getCityByName("Москва").getId()))
                                .operationalZoneId(OperationalZonesDao.INSTANCE.getIdByName("Москва"))
                                .timeZone("Europe/Moscow")
                                .tenantId("instamart")
                                .location(Location.builder()
                                        .city("Москва")
                                        .street("Miklouho-Maclay")
                                        .building("1")
                                        .lat(55.638318)
                                        .lon(37.539509)
                                        .countryId(1)
                                        .build()
                                )
                                .config(Config.builder()
                                        .importKeyPostfix(RandomUtils.nextInt(1, 1000000))
                                        .shipmentBaseKilos((double)RandomUtils.nextInt(10, 1000))
                                        .shipmentBaseItemsCount(RandomUtils.nextInt(1, 100))
                                        .minOrderAmount(RandomUtils.nextInt(1000, 5000))
                                        .minFirstOrderAmount(0)
                                        .minOrderAmountPickup(0)
                                        .minFirstOrderAmountPickup(0)
                                        .disallowOrderEditingHours(RandomUtils.nextInt(1, 6))
                                        .useWorkSchedule(false)
                                        .ordersApiIntegrationType("shopper")
                                        .sendCreatedHook("immediately")
                                        .paymentAtCheckout("bank_card")
                                        .etaFrom(0)
                                        .etaTo(0)
                                        .inn(generateInnUL())
                                        .legalName("Autotest-" + Generate.literalString(6))
                                        .build()
                                )
                                .openingTime("11:00")
                                .closingTime("23:00")
                                .retailerId(SpreeRetailersDao.INSTANCE.getIdBySlug("selgros"))
                                .paymentMethodsStore(
                                        PaymentMethodsStoresItem.builder()
                                                .tenantId("instamart")
                                                .shippingMethodKindId(6)
                                                .paymentMethodId(paymentMethodIds.get(0))
                                                .build()
                                )
                                .paymentMethodsStore(
                                        PaymentMethodsStoresItem.builder()
                                                .tenantId("instamart")
                                                .shippingMethodKindId(3)
                                                .paymentMethodId(paymentMethodIds.get(2))
                                                .build()
                                )
                                .paymentMethodsStore(
                                        PaymentMethodsStoresItem.builder()
                                                .tenantId("instamart")
                                                .shippingMethodKindId(16)
                                                .paymentMethodId(paymentMethodIds.get(3))
                                                .build()
                                )
                                .storeShippingMethod(
                                        StoreShippingMethodsItem.builder()
                                                .shippingMethodId(50)
                                                .tenantId("instamart")
                                                .availableOn("07.09.2022 00:00")
                                                .build()
                                )
                                .build()
                )
                .build();
    }

    public static Stores getStoreForRetailerTests(final String retailerName, final String city) {
        List<Long> paymentMethodIds = SpreePaymentMethodsDao.INSTANCE.getActivePaymentMethodsIds();

        return Stores.builder()
                .store(
                        Store.builder()
                                .onDemandClosingDelta(30)
                                .onDemand(false)
                                .cityId((CitiesDao.INSTANCE.getCityByName(city).getId()))
                                .operationalZoneId(OperationalZonesDao.INSTANCE.getIdByName(city))
                                .timeZone("Europe/Moscow")
                                .tenantId("instamart")
                                .location(Location.builder()
                                        .city(city)
                                        .street("Mira")
                                        .building("211")
                                        .lat(DoubleUtil.roundAvoid(
                                                DoubleUtil.getRandomDoubleBetweenRange(50.0, 55.0), 6))
                                        .lon(DoubleUtil.roundAvoid(
                                                DoubleUtil.getRandomDoubleBetweenRange(30.0, 35.0), 6))
                                        .countryId(1)
                                        .build()
                                )
                                .config(Config.builder()
                                        .importKeyPostfix(RandomUtils.nextInt(1, 1000000))
                                        .shipmentBaseKilos((double)RandomUtils.nextInt(10, 1000))
                                        .shipmentBaseItemsCount(RandomUtils.nextInt(1, 100))
                                        .minOrderAmount(RandomUtils.nextInt(1000, 5000))
                                        .minFirstOrderAmount(0)
                                        .minOrderAmountPickup(0)
                                        .minFirstOrderAmountPickup(0)
                                        .disallowOrderEditingHours(RandomUtils.nextInt(1, 6))
                                        .useWorkSchedule(false)
                                        .ordersApiIntegrationType("shopper")
                                        .sendCreatedHook("immediately")
                                        .paymentAtCheckout("bank_card")
                                        .etaFrom(0)
                                        .etaTo(0)
                                        .inn(generateInnUL())
                                        .ogrn(generateOGRN())
                                        .legalName("Autotest-" + Generate.literalString(6))
                                        .build()
                                )
                                .retailerId(SpreeRetailersDao.INSTANCE.getIdByName(retailerName))
                                .paymentMethodsStore(
                                        PaymentMethodsStoresItem.builder()
                                                .tenantId("instamart")
                                                .shippingMethodKindId(6)
                                                .paymentMethodId(paymentMethodIds.get(0))
                                                .build()
                                )
                                .paymentMethodsStore(
                                        PaymentMethodsStoresItem.builder()
                                                .tenantId("instamart")
                                                .shippingMethodKindId(3)
                                                .paymentMethodId(paymentMethodIds.get(2))
                                                .build()
                                )
                                .paymentMethodsStore(
                                        PaymentMethodsStoresItem.builder()
                                                .tenantId("instamart")
                                                .shippingMethodKindId(16)
                                                .paymentMethodId(paymentMethodIds.get(3))
                                                .build()
                                )
                                .storeShippingMethod(
                                        StoreShippingMethodsItem.builder()
                                                .shippingMethodId(50)
                                                .tenantId("instamart")
                                                .availableOn("07.09.2022 00:00")
                                                .build()
                                )
                                .labelItems(null)
                                .workSchedule(WorkSchedulesItem.builder()
                                        .startsAt("11:00")
                                        .endsAt("23:00")
                                        .weekDay("monday")
                                        .build())
                                .workSchedule(WorkSchedulesItem.builder()
                                        .startsAt("11:00")
                                        .endsAt("23:00")
                                        .weekDay("tuesday")
                                        .build())
                                .workSchedule(WorkSchedulesItem.builder()
                                        .startsAt("11:00")
                                        .endsAt("23:00")
                                        .weekDay("saturday")
                                        .build())
                                .workSchedule(WorkSchedulesItem.builder()
                                        .startsAt("11:00")
                                        .endsAt("23:00")
                                        .weekDay("sunday")
                                        .build())
                                .workSchedule(WorkSchedulesItem.builder()
                                        .startsAt("11:00")
                                        .endsAt("23:00")
                                        .weekDay("friday")
                                        .build())
                                .workSchedule(WorkSchedulesItem.builder()
                                        .startsAt("11:00")
                                        .endsAt("23:00")
                                        .weekDay("thursday")
                                        .build())
                                .workSchedule(WorkSchedulesItem.builder()
                                        .startsAt("11:00")
                                        .endsAt("23:00")
                                        .weekDay("wednesday")
                                        .build())
                                .build()
                )
                .build();
    }

    public static Stores getStoreLentaOrekhoviyBulvar() {
        List<Long> paymentMethodIds = SpreePaymentMethodsDao.INSTANCE.getActivePaymentMethodsIds();
        return Stores.builder()
                .store(
                        Store.builder()
                                .onDemandClosingDelta(30)
                                .onDemand(false)
                                .cityId((CitiesDao.INSTANCE.getCityByName("Москва").getId()))
                                .operationalZoneId(OperationalZonesDao.INSTANCE.getIdByName("Москва"))
                                .timeZone("Europe/Moscow")
                                .tenantId("instamart")
                                .location(Location.builder()
                                        .city("Москва")
                                        .street("Orekhoviy bul'var")
                                        .building("22")
                                        .lat(55.612045)
                                        .lon(37.732718)
                                        .countryId(1)
                                        .build()
                                )
                                .config(Config.builder()
                                        .importKeyPostfix(1122)
                                        .shipmentBaseKilos(40.0)
                                        .shipmentBaseItemsCount(30)
                                        .minOrderAmount(100)
                                        .minFirstOrderAmount(1800)
                                        .minOrderAmountPickup(0)
                                        .minFirstOrderAmountPickup(0)
                                        .disallowOrderEditingHours(4)
                                        .useWorkSchedule(false)
                                        .ordersApiIntegrationType("shopper")
                                        .sendCreatedHook("immediately")
                                        .paymentAtCheckout("bank_card")
                                        .etaFrom(0)
                                        .etaTo(0)
                                        .inn(generateInnUL())
                                        .legalName("Orekhoviy autotest")
                                        .build()
                                )
                                .openingTime("11:00")
                                .closingTime("23:00")
                                .retailerId(SpreeRetailersDao.INSTANCE.getIdBySlug("lents"))
                                .paymentMethodsStore(
                                        PaymentMethodsStoresItem.builder()
                                                .tenantId("instamart")
                                                .shippingMethodKindId(6)
                                                .paymentMethodId(paymentMethodIds.get(0))
                                                .build()
                                )
                                .paymentMethodsStore(
                                        PaymentMethodsStoresItem.builder()
                                                .tenantId("instamart")
                                                .shippingMethodKindId(3)
                                                .paymentMethodId(paymentMethodIds.get(2))
                                                .build()
                                )
                                .paymentMethodsStore(
                                        PaymentMethodsStoresItem.builder()
                                                .tenantId("instamart")
                                                .shippingMethodKindId(16)
                                                .paymentMethodId(paymentMethodIds.get(3))
                                                .build()
                                )
                                .storeShippingMethod(
                                        StoreShippingMethodsItem.builder()
                                                .shippingMethodId(50)
                                                .tenantId("instamart")
                                                .availableOn("07.09.2022 00:00")
                                                .build()
                                )
                                .build()
                )
                .build();
    }

    public static Stores getStoreLentaElino() {
        List<Long> paymentMethodIds = SpreePaymentMethodsDao.INSTANCE.getActivePaymentMethodsIds();
        return Stores.builder()
                .store(
                        Store.builder()
                                .onDemandClosingDelta(30)
                                .onDemand(false)
                                .cityId(CitiesDao.INSTANCE.getCityByName("д.Елино").getId())
                                .operationalZoneId(OperationalZonesDao.INSTANCE.getIdByName("Москва"))
                                .timeZone("Europe/Moscow")
                                .tenantId("instamart")
                                .location(Location.builder()
                                        .city("Elino")
                                        .street("Elino")
                                        .building("20")
                                        .lat(55.99013)
                                        .lon(37.27379)
                                        .countryId(1)
                                        .build()
                                )
                                .config(Config.builder()
                                        .importKeyPostfix(RandomUtils.nextInt(1, 1000000))
                                        .shipmentBaseKilos((double)RandomUtils.nextInt(10, 1000))
                                        .shipmentBaseItemsCount(RandomUtils.nextInt(1, 100))
                                        .minOrderAmount(RandomUtils.nextInt(1000, 5000))
                                        .minFirstOrderAmount(0)
                                        .minOrderAmountPickup(0)
                                        .minFirstOrderAmountPickup(0)
                                        .disallowOrderEditingHours(RandomUtils.nextInt(1, 6))
                                        .useWorkSchedule(false)
                                        .ordersApiIntegrationType("shopper")
                                        .sendCreatedHook("immediately")
                                        .paymentAtCheckout("bank_card")
                                        .etaFrom(0)
                                        .etaTo(0)
                                        .inn(generateInnUL())
                                        .legalName("Elino autotest")
                                        .build()
                                )
                                .openingTime("11:00")
                                .closingTime("23:00")
                                .retailerId(SpreeRetailersDao.INSTANCE.getIdBySlug("lenta"))
                                .paymentMethodsStore(
                                        PaymentMethodsStoresItem.builder()
                                                .tenantId("instamart")
                                                .shippingMethodKindId(6)
                                                .paymentMethodId(paymentMethodIds.get(0))
                                                .build()
                                )
                                .paymentMethodsStore(
                                        PaymentMethodsStoresItem.builder()
                                                .tenantId("instamart")
                                                .shippingMethodKindId(3)
                                                .paymentMethodId(paymentMethodIds.get(2))
                                                .build()
                                )
                                .paymentMethodsStore(
                                        PaymentMethodsStoresItem.builder()
                                                .tenantId("instamart")
                                                .shippingMethodKindId(16)
                                                .paymentMethodId(paymentMethodIds.get(3))
                                                .build()
                                )
                                .storeShippingMethod(
                                        StoreShippingMethodsItem.builder()
                                                .shippingMethodId(50)
                                                .tenantId("instamart")
                                                .availableOn("07.09.2022 00:00")
                                                .build()
                                )
                                .build()
                )
                .build();
    }

    public static Stores getStoreVictoriaTest() {
        List<Long> paymentMethodIds = SpreePaymentMethodsDao.INSTANCE.getActivePaymentMethodsIds();
        return Stores.builder()
                .store(
                        Store.builder()
                                .onDemandClosingDelta(30)
                                .onDemand(false)
                                .cityId(CitiesDao.INSTANCE.getCityByName("Санкт-Петербург").getId())
                                .operationalZoneId(OperationalZonesDao.INSTANCE.getIdByName("Санкт-Петербург"))
                                .timeZone("Europe/Moscow")
                                .tenantId("instamart")
                                .location(Location.builder()
                                        .city("Москва")
                                        .street("Test")
                                        .building("88")
                                        .lon(0.0)
                                        .lat(90.0)
                                        .countryId(1)
                                        .build()
                                )
                                .config(Config.builder()
                                        .importKeyPostfix(RandomUtils.nextInt(1, 1000000))
                                        .shipmentBaseKilos((double)RandomUtils.nextInt(10, 1000))
                                        .shipmentBaseItemsCount(RandomUtils.nextInt(1, 100))
                                        .minOrderAmount(RandomUtils.nextInt(1000, 5000))
                                        .minFirstOrderAmount(0)
                                        .minOrderAmountPickup(0)
                                        .minFirstOrderAmountPickup(0)
                                        .disallowOrderEditingHours(RandomUtils.nextInt(1, 6))
                                        .useWorkSchedule(false)
                                        .ordersApiIntegrationType("shopper")
                                        .sendCreatedHook("immediately")
                                        .paymentAtCheckout("bank_card")
                                        .etaFrom(0)
                                        .etaTo(0)
                                        .inn(generateInnUL())
                                        .legalName("Test autotest")
                                        .build()
                                )
                                .openingTime("11:00")
                                .closingTime("23:00")
                                .retailerId(SpreeRetailersDao.INSTANCE.getIdBySlug("victoria"))
                                .paymentMethodsStore(
                                        PaymentMethodsStoresItem.builder()
                                                .tenantId("instamart")
                                                .shippingMethodKindId(6)
                                                .paymentMethodId(paymentMethodIds.get(0))
                                                .build()
                                )
                                .paymentMethodsStore(
                                        PaymentMethodsStoresItem.builder()
                                                .tenantId("instamart")
                                                .shippingMethodKindId(3)
                                                .paymentMethodId(paymentMethodIds.get(2))
                                                .build()
                                )
                                .paymentMethodsStore(
                                        PaymentMethodsStoresItem.builder()
                                                .tenantId("instamart")
                                                .shippingMethodKindId(16)
                                                .paymentMethodId(paymentMethodIds.get(3))
                                                .build()
                                )
                                .storeShippingMethod(
                                        StoreShippingMethodsItem.builder()
                                                .shippingMethodId(50)
                                                .tenantId("instamart")
                                                .availableOn("07.09.2022 00:00")
                                                .build()
                                )
                                .build()
                )
                .build();

    }

}
