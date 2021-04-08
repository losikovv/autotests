package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.StringJoiner;

@Data
@EqualsAndHashCode(callSuper=false)
public class Store extends BaseObject {
    private Integer id;
    private String name;
    @JsonProperty(value = "next_delivery")
    private NextDelivery nextDelivery;
    private List<List<Zone>> zones = null;
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
    private Retailer retailer;
    private Address location;
    private List<Service> services = null;
    @JsonProperty(value = "operational_times")
    private List<OperationalTime> operationalTimes = null;

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
