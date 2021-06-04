package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.NextDeliveryV2;

import java.util.StringJoiner;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoreV1 extends BaseObject {
    private Integer id;
    private String name;
    private Boolean active;
    @JsonProperty("retailer_slug")
    private String retailerSlug;
    @JsonProperty("retailer_color")
    private String retailerColor;
    @JsonProperty("time_zone")
    private String timeZone;
    private String uuid;
    @JsonProperty("has_conveyor")
    private Boolean hasConveyor;
    @JsonProperty("auto_routing")
    private Boolean autoRouting;
    @JsonProperty("express_delivery")
    private Boolean expressDelivery;
    @JsonProperty("box_scanning")
    private Boolean boxScanning;
    private Boolean training;
    @JsonProperty("min_order_amount")
    private Double minOrderAmount;
    @JsonProperty("min_first_order_amount")
    private Double minFirstOrderAmount;
    @JsonProperty("min_first_order_amount_pickup")
    private Double minFirstOrderAmountPickup;
    @JsonProperty("min_order_amount_pickup")
    private Double minOrderAmountPickup;
    @JsonProperty("available_for_pickup")
    private Boolean availableForPickup;
    @JsonProperty("external_assembly")
    private Object externalAssembly;
    @JsonProperty("next_delivery")
    private NextDeliveryV2 nextDelivery;
    private AddressV1 location;

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
