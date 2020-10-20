package instamart.api.responses.shopper;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.shopper.OfferData;

import java.util.List;

public class OffersResponse extends BaseResponseObject {

    private List<OfferData> data = null;

    public List<OfferData> getData() {
        return data;
    }

    public void setData(List<OfferData> data) {
        this.data = data;
    }

}
