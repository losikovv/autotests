package instamart.api.responses.shopper;

import instamart.api.objects.shopper.ReceiptsData;
import instamart.api.responses.BaseResponseObject;

public class ReceiptsResponse extends BaseResponseObject {
    private ReceiptsData data;

    public ReceiptsData getData() {
        return data;
    }

    public void setData(ReceiptsData data) {
        this.data = data;
    }

}
