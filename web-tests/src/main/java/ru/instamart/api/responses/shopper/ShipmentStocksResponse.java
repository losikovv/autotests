package instamart.api.responses.shopper;

import instamart.api.objects.shopper.Offer;
import instamart.api.responses.BaseResponseObject;

import java.util.List;

public class ShipmentStocksResponse extends BaseResponseObject {

    private List<Offer> offers = null;

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

}
