package ru.instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.responses.BaseResponseObject;
import ru.instamart.api.objects.v2.PromotionCard;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class PromotionCardsResponse extends BaseResponseObject {
    @JsonProperty(value = "promotion_cards")
    private List<PromotionCard> promotionCards = null;
}
