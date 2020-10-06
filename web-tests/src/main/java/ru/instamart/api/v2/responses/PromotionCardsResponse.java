package instamart.api.v2.responses;

import instamart.api.common.BaseResponseObject;
import instamart.api.v2.objects.PromotionCard;

import java.util.List;

public class PromotionCardsResponse extends BaseResponseObject {

    private List<PromotionCard> promotion_cards = null;

    public List<PromotionCard> getPromotion_cards() {
        return promotion_cards;
    }

    public void setPromotion_cards(List<PromotionCard> promotion_cards) {
        this.promotion_cards = promotion_cards;
    }

}
