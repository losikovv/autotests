package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShippingRate extends BaseObject {
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
    private DeliveryWindow deliveryWindow;
    @JsonProperty(value = "is_express_delivery")
    private Boolean isExpressDelivery;
}
