package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class AdminOrderDeliveryWindowsV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("available_zone_ids")
    private Object availableZoneIds;

    @JsonSchema(required = true)
    @JsonProperty("shipping_method_kind")
    private String shippingMethodKind;

    @JsonSchema(required = true)
    private List<ShipmentV1> shipments;
}
