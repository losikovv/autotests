package instamart.api.responses.shopper;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.shopper.ShopperData;
import instamart.api.objects.shopper.StoreOrRoleData;

import java.util.List;

public class ShopperResponse extends BaseResponseObject {

    private ShopperData data;
    private List<StoreOrRoleData> included = null;

    public ShopperData getData() {
        return data;
    }

    public void setData(ShopperData data) {
        this.data = data;
    }

    public List<StoreOrRoleData> getIncluded() {
        return included;
    }

    public void setIncluded(List<StoreOrRoleData> included) {
        this.included = included;
    }

}
