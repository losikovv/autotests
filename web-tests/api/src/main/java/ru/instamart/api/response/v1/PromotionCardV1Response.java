
package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.PromotionCardV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PromotionCardV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    @JsonProperty("promotion_card")
    private PromotionCardV1 promotionCard;
}
