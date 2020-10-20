package instamart.api.responses.v2;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.v2.PromotionCard;

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
