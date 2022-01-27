package ru.instamart.api.response.v1.dictionaries;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class DictionariesShipmentCombinedStatesV1Response extends BaseResponseObject {
    @JsonSchema(required = true)
    @JsonProperty("shipment_combined_states")
    private List<ShipmentCombinedState> shipmentCombinedStates;

    @Data
    @EqualsAndHashCode(callSuper=false)
    public static class ShipmentCombinedState extends BaseObject {
        @JsonSchema(required = true)
        private String id;
        @JsonSchema(required = true)
        private String name;
        @JsonSchema(required = true)
        private String description;
        @JsonSchema(required = true)
        private Integer position;
    }
}
