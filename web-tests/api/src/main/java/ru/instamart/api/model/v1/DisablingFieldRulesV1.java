
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class DisablingFieldRulesV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("order_line_item_ids")
    private OrderLineItemIdsV1 orderLineItemIds;

    @JsonSchema(required = true)
    @JsonProperty("promotion_id")
    private PromotionIdV1 promotionId;
}
