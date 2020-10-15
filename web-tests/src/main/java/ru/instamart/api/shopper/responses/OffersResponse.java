package instamart.api.shopper.responses;

import instamart.api.common.BaseResponseObject;
import instamart.api.shopper.objects.OfferData;

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
