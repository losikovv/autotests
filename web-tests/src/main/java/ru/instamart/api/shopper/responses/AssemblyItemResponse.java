package instamart.api.shopper.responses;

import instamart.api.common.BaseResponseObject;
import instamart.api.shopper.objects.AssemblyItemData;

public class AssemblyItemResponse extends BaseResponseObject {

    private AssemblyItemData data;

    public AssemblyItemData getData() {
        return data;
    }

    public void setData(AssemblyItemData data) {
        this.data = data;
    }

}
