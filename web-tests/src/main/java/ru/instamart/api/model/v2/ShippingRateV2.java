package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShippingRateV2 extends BaseObject {
    @JsonProperty(value = "total_value")
    private Double totalValue;
    private Double value;
    @JsonProperty(value = "is_boost")
    private Boolean isBoost;
    @JsonProperty(value = "is_drop")
    private Boolean isDrop;
    @JsonProperty(value = "is_free")
    private Boolean isFree;
    @JsonProperty(value = "delivery_window")
    private DeliveryWindowV2 deliveryWindow;
    @JsonProperty(value = "is_express_delivery")
    private Boolean isExpressDelivery;
}
