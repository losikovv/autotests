package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.NextDeliveryV2;

import javax.validation.constraints.Null;
import java.util.StringJoiner;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoresV1 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    private Boolean active;

    @JsonSchema(required = true)
    @JsonProperty("retailer_slug")
    private String retailerSlug;

    @JsonSchema(required = true)
    @JsonProperty("retailer_color")
    private String retailerColor;

    @JsonSchema(required = true)
    @JsonProperty("time_zone")
    private String timeZone;

    @JsonSchema(required = true)
    private String uuid;

    @JsonSchema(required = true)
    @JsonProperty("has_conveyor")
    private Boolean hasConveyor;

    @JsonSchema(required = true)
    @JsonProperty("auto_routing")
    private Boolean autoRouting;

    @JsonSchema(required = true)
    @JsonProperty("express_delivery")
    private Boolean expressDelivery;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("box_scanning")
    private Boolean boxScanning;

    @Null
    @JsonSchema(required = true)
    private Boolean training;

    @JsonSchema(required = true)
    @JsonProperty("min_order_amount")
    private Double minOrderAmount;

    @JsonSchema(required = true)
    @JsonProperty("min_first_order_amount")
    private Double minFirstOrderAmount;

    @JsonSchema(required = true)
    @JsonProperty("min_first_order_amount_pickup")
    private Double minFirstOrderAmountPickup;

    @JsonSchema(required = true)
    @JsonProperty("min_order_amount_pickup")
    private Double minOrderAmountPickup;

    @JsonSchema(required = true)
    @JsonProperty("available_for_pickup")
    private Boolean availableForPickup;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("external_assembly")
    private Boolean externalAssembly;

    @JsonSchema(required = true)
    @JsonProperty("on_demand")
    private Boolean onDemand;

    @Null
    @JsonProperty("seconds_for_assembly_item")
    private Object secondsForAssemblyItem;

    @Null
    @JsonProperty("additional_seconds_for_assembly")
    private Object additionalSecondsForAssembly;

    @Null
    @JsonProperty("city_id")
    private Integer cityId;

    @JsonProperty("retailer_store_id")
    private String retailerStoreId;

    @Null
    @JsonProperty("delivery_forecast_text")
    private String deliveryForecastText;

    @JsonProperty("estimate_source")
    private String estimateSource;

    @Null
    @JsonProperty("estimate_minutes_min")
    private Object estimateMinutesMin;

    @Null
    @JsonProperty("available_on")
    private String availableOn;

    @JsonProperty("external_assembly_kind")
    private String externalAssemblyKind;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("next_delivery")
    private NextDeliveryV2 nextDelivery;

    @JsonSchema(required = true)
    private AddressV1 location;

    private RetailerV1 retailer;

    @Null
    @JsonProperty("operational_zone")
    private OperationalZoneV1 operationalZone;

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(
                ", ",
                "",
                "");
        if (getRetailerSlug() == null || getLocation() == null) {
            stringJoiner.add(getName());
        } else {
            stringJoiner.add(getRetailerSlug());
            stringJoiner
                    .add(getLocation().getCity())
                    .add(getLocation().getStreet());
        }
        return stringJoiner
                .add("sid: " + id)
                .toString();
    }
}
