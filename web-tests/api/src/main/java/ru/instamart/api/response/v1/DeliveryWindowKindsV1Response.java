
package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.DeliveryWindowKindV1;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeliveryWindowKindsV1Response extends BaseResponseObject {

    @NotEmpty
    @JsonSchema(required = true)
    @JsonProperty("delivery_window_kinds")
    private List<DeliveryWindowKindV1> deliveryWindowKinds;
}
