
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoreV1 extends BaseObject {

    @JsonSchema(required = true)
    private Boolean active;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("additional_seconds_for_assembly")
    private Object additionalSecondsForAssembly;

    @JsonSchema(required = true)
    @JsonProperty("auto_routing")
    private Boolean autoRouting;

    @JsonSchema(required = true)
    @JsonProperty("available_on")
    private String availableOn;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("box_scanning")
    private Object boxScanning;

    private CityV1 city;

    @JsonSchema(required = true)
    @JsonProperty("city_id")
    private Long cityId;

    private ConfigV1 config;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("delivery_forecast_text")
    private Object deliveryForecastText;

    @JsonSchema(required = true)
    @JsonProperty("express_delivery")
    private Boolean expressDelivery;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("external_assembly")
    private Object externalAssembly;

    @JsonSchema(required = true)
    @JsonProperty("has_conveyor")
    private Boolean hasConveyor;

    @JsonSchema(required = true)
    private Integer id;

    @JsonProperty("import_key_postfix")
    private String importKeyPostfix;

    private List<Object> licenses;

    @JsonSchema(required = true)
    private AddressV1 location;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    @JsonProperty("on_demand")
    private Boolean onDemand;

    @JsonSchema(required = true)
    @JsonProperty("operational_zone")
    private OperationalZoneV1 operationalZone;

    @JsonProperty("payment_methods_stores")
    private List<PaymentMethodsStoreV1> paymentMethodsStores;

    @Null
    @JsonProperty("pharmacy_legal_info")
    private Object pharmacyLegalInfo;

    @Null
    private Object phone;


    @JsonProperty("pickup_instruction")
    private String pickupInstruction;

    @JsonSchema(required = true)
    private RetailerV1 retailer;

    @JsonSchema(required = true)
    @JsonProperty("retailer_store_id")
    private String retailerStoreId;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("seconds_for_assembly_item")
    private Object secondsForAssemblyItem;

    @JsonProperty("store_schedule")
    private StoreScheduleV1 storeSchedule;

    @JsonProperty("store_shipping_methods")
    private List<StoreShippingMethodV1> storeShippingMethods;

    @JsonProperty("store_zones")
    private List<StoreZoneV1> storeZones;

    private List<StoreTenantV1> tenants;

    @JsonSchema(required = true)
    @JsonProperty("time_zone")
    private String timeZone;

    @Null
    @JsonSchema(required = true)
    private Object training;

    @JsonSchema(required = true)
    private String uuid;

    @JsonProperty("min_order_amount")
    private Double minOrderAmount;
}
