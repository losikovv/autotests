package instamart.api.responses.shopper;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.shopper.AssemblyItemData;

public class AssemblyItemResponse extends BaseResponseObject {

    private AssemblyItemData data;

    public AssemblyItemData getData() {
        return data;
    }

    public void setData(AssemblyItemData data) {
        this.data = data;
    }

}
