package instamart.api.shopper.responses;

import instamart.api.common.BaseResponseObject;
import instamart.api.shopper.objects.ShopperData;
import instamart.api.shopper.objects.StoreOrRoleData;

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
