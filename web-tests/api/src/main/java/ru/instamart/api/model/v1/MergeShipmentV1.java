
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class MergeShipmentV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("shipment_id")
    private Long shipmentId;

    @JsonSchema(required = true)
    private String state;

    @JsonProperty("delivery_starts_at")
    private String deliveryStartsAt;

    @JsonProperty("delivery_ends_at")
    private String deliveryEndsAt;

    @JsonProperty("new_cost")
    private Double newCost;
}
