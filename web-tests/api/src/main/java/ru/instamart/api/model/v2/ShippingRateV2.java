package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShippingRateV2 extends BaseObject {
    @JsonSchema(required = true)
    @JsonProperty(value = "total_value")
    private Double totalValue;

    @JsonSchema(required = true)
    private Double value;

    @JsonSchema(required = true)
    @JsonProperty(value = "is_boost")
    private Boolean isBoost;

    @JsonSchema(required = true)
    @JsonProperty(value = "is_drop")
    private Boolean isDrop;

    @JsonSchema(required = true)
    @JsonProperty(value = "is_free")
    private Boolean isFree;

    @JsonSchema(required = true)
    @JsonProperty(value = "delivery_window")
    private DeliveryWindowV2 deliveryWindow;

    @JsonSchema(required = true)
    @JsonProperty(value = "is_express_delivery")
    private Boolean isExpressDelivery;
}
