
package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class MergeShipmentV2 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("shipment_id")
    private Long shipmentId;

    @JsonSchema(required = true)
    private String state;
}
