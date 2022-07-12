package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.StringJoiner;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoreV2 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private String name;

    private Boolean active;

    @JsonProperty("retailer_slug")
    private String retailerSlug;

    @JsonProperty("retailer_color")
    private String retailerColor;

    @JsonProperty("time_zone")
    private String timeZone;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "next_delivery")
    private NextDeliveryV2 nextDelivery;

    @JsonSchema(required = true)
    private List<List<ZoneV2>> zones = null;

    @JsonSchema(required = true)
    private String uuid;

    @JsonProperty("has_conveyor")
    private Boolean hasConveyor;

    @JsonProperty("auto_routing")
    private Boolean autoRouting;

    @JsonSchema(required = true)
    @JsonProperty(value = "express_delivery")
    private Boolean expressDelivery;

    @JsonProperty("box_scanning")
    private Boolean boxScanning;

    private Boolean training;

    @JsonSchema(required = true)
    @JsonProperty(value = "min_order_amount")
    private Double minOrderAmount;

    @JsonSchema(required = true)
    @JsonProperty(value = "min_first_order_amount")
    private Double minFirstOrderAmount;

    @JsonSchema(required = true)
    @JsonProperty(value = "min_order_amount_pickup")
    private Double minOrderAmountPickup;

    @JsonSchema(required = true)
    @JsonProperty(value = "min_first_order_amount_pickup")
    private Double minFirstOrderAmountPickup;

    @JsonSchema(required = true)
    @JsonProperty(value = "available_for_pickup")
    private Boolean availableForPickup;

    @JsonSchema(required = true)
    @JsonProperty(value = "pickup_starts_at")
    private String pickUpStartsAt;

    @JsonSchema(required = true)
    @JsonProperty(value = "pickup_ends_at")
    private String pickUpEndsAt;

    @JsonSchema(required = true)
    private RetailerV2 retailer;

    @JsonSchema(required = true)
    private AddressV2 location;

    @Null
    @JsonSchema(required = true)
    private List<ServiceV2> services = null;

    @JsonProperty(value = "operational_times")
    private List<OperationalTimeV2> operationalTimes = null;

    @JsonProperty(value = "label_uuids")
    private List<Object> labelUuids = null;

    @JsonProperty("external_assembly")
    private Object externalAssembly;

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(
                ", ",
                "",
                "");
        if (getRetailer() == null || getLocation() == null) {
            stringJoiner.add(getName());
        } else {
            stringJoiner.add(getRetailer().getName());
            stringJoiner
                    .add(getLocation().getCity())
                    .add(getLocation().getStreet());
        }
        return stringJoiner
                .add("sid: " + id)
                .toString();
    }
}
