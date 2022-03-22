
package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.RetailerPromoCardV1;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class RetailerPromoCardsV1Response extends BaseResponseObject {

    @NotEmpty
    @JsonSchema(required = true)
    @JsonProperty("promo_cards")
    private List<RetailerPromoCardV1> promoCards;
}
