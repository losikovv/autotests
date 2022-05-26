package ru.instamart.api.response.v1.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.ShipmentReturnV1;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShipmentReturnsV1Response extends BaseResponseObject {
    @NotEmpty
    @JsonSchema(required = true)
    @JsonProperty(value = "shipment_returns")
    private List<ShipmentReturnV1> shipmentReturns;
}
