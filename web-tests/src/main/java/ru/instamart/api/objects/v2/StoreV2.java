package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

import java.util.List;
import java.util.StringJoiner;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoreV2 extends BaseObject {
    private Integer id;
    private String name;
    @JsonProperty(value = "next_delivery")
    private NextDeliveryV2 nextDelivery;
    private List<List<ZoneV2>> zones = null;
    private String uuid;
    @JsonProperty(value = "express_delivery")
    private Boolean expressDelivery;
    @JsonProperty(value = "min_order_amount")
    private Double minOrderAmount;
    @JsonProperty(value = "min_first_order_amount")
    private Double minFirstOrderAmount;
    @JsonProperty(value = "min_order_amount_pickup")
    private Double minOrderAmountPickup;
    @JsonProperty(value = "min_first_order_amount_pickup")
    private Double minFirstOrderAmountPickup;
    @JsonProperty(value = "available_for_pickup")
    private Boolean availableForPickup;
    private RetailerV2 retailer;
    private AddressV2 location;
    private List<ServiceV2> services = null;
    @JsonProperty(value = "operational_times")
    private List<OperationalTimeV2> operationalTimes = null;

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(
                ", ",
                "",
                "");
        if (getRetailer() == null || getLocation() == null) {
            stringJoiner.add(getName());
        } else {
            if (getRetailer() != null) {
                stringJoiner.add(getRetailer().getName());
            }
            if (getLocation() != null) {
                stringJoiner
                        .add(getLocation().getCity())
                        .add(getLocation().getStreet());
            }
        }
        return stringJoiner
                .add("sid: " + id)
                .toString();
    }
}
