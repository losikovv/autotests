
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderLineItemIdsV1 extends BaseObject {

    @JsonSchema
    @JsonProperty("promo_ids")
    private List<Long> promoIds;

    @JsonSchema
    @JsonProperty("reason_ids")
    private List<Long> reasonIds;
}
