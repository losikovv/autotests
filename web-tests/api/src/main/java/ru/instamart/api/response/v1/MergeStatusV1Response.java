
package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.AvailableRetailerV1;
import ru.instamart.api.model.v1.MergeShipmentV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class MergeStatusV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    @JsonProperty("available_retailers")
    private List<AvailableRetailerV1> availableRetailers;

    @JsonSchema(required = true)
    private List<MergeShipmentV1> shipments;
}
