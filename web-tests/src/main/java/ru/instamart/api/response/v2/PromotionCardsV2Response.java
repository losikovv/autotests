package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.PromotionCardV2;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class PromotionCardsV2Response extends BaseResponseObject {
    @JsonProperty(value = "promotion_cards")
    private List<PromotionCardV2> promotionCards = null;
}
