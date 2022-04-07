package ru.instamart.api.response.v1.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.ShipmentReturnV1;
import ru.instamart.api.response.BaseResponseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShipmentReturnV1Response extends BaseResponseObject {
    @JsonSchema(required = true)
    @JsonProperty(value = "shipment_return")
    private ShipmentReturnV1 shipmentReturn;
}
