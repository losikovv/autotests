package instamart.api.responses.shopper;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.shopper.ShopperData;
import instamart.api.objects.shopper.ShopperIncluded;

import java.util.List;

public class ShopperResponse extends BaseResponseObject {

    private ShopperData data;
    private List<ShopperIncluded> included = null;

    public ShopperData getData() {
        return data;
    }

    public void setData(ShopperData data) {
        this.data = data;
    }

    public List<ShopperIncluded> getIncluded() {
        return included;
    }

    public void setIncluded(List<ShopperIncluded> included) {
        this.included = included;
    }

}
