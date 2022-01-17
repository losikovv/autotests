
package ru.instamart.api.response.v2;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.AvailableRetailerV2;
import ru.instamart.api.model.v2.MergeShipmentV2;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode(callSuper=false)
public class MergeStatusV2Response extends BaseResponseObject {
    @NotEmpty
    @JsonSchema(required = true)
    @JsonProperty("available_retailers")
    private List<AvailableRetailerV2> availableRetailers;

    @NotEmpty
    @JsonSchema(required = true)
    private List<MergeShipmentV2> shipments;
}
