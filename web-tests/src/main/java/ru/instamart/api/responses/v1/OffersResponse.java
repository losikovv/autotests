package instamart.api.responses.v1;

import instamart.api.objects.v2.Meta;
import instamart.api.objects.v1.Offer;
import instamart.api.responses.BaseResponseObject;

import java.util.List;

public class OffersResponse extends BaseResponseObject {

    private List<Offer> offers = null;
    private Meta meta;

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}
