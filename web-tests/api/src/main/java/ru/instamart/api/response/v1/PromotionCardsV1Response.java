
package ru.instamart.api.response.v1;

import java.util.List;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.MetaV1;
import ru.instamart.api.model.v1.PromotionCardV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PromotionCardsV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    private MetaV1 meta;

    @NotEmpty
    @JsonSchema(required = true)
    @JsonProperty("promotion_cards")
    private List<PromotionCardV1> promotionCards;
}
