package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class NextDeliveriesItemV2 extends BaseObject {
    private String summary;
    @JsonProperty("express_delivery")
    private boolean expressDelivery;
    @JsonProperty("starts_at")
    private String startsAt;
    private double price;
    private int id;
    @JsonProperty("ends_at")
    private String endsAt;
}